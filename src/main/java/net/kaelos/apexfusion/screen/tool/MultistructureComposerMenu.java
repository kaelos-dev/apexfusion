package net.kaelos.apexfusion.screen.tool;

import net.kaelos.apexfusion.item.custom.BlueprintItem;
import net.kaelos.apexfusion.screen.AFMenuTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MultistructureComposerMenu extends AbstractContainerMenu {
    private final ItemStack itemInHand;

    public MultistructureComposerMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ItemStack.EMPTY);
    }

    public MultistructureComposerMenu(int containerId, Inventory inventory, ItemStack itemStack) {
        super(AFMenuTypes.MULTISTRUCTURE_COMPOSER_MENU.get(), containerId);
        this.itemInHand = itemStack;

        ItemStackHandler itemHandler = new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                if (!itemInHand.isEmpty()) {
                    CustomData.update(DataComponents.CUSTOM_DATA, itemInHand, nbt ->
                        nbt.put("inventory", serializeNBT(inventory.player.registryAccess()))
                    );
                }
            }
        };

        if (!itemStack.isEmpty()) {
            CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

            if (customData.contains("inventory")) {
                CompoundTag tag = customData.copyTag();
                itemHandler.deserializeNBT(inventory.player.registryAccess(), tag.getCompound("inventory"));
            }
        }

        this.addSlot(new SlotItemHandler(itemHandler, 0, 80, 35) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof BlueprintItem;
            }
        });

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemInSlot = slot.getItem();
            stack = itemInSlot.copy();

            if (index == 0) {
                if (!this.moveItemStackTo(itemInSlot, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.slots.getFirst().mayPlace(itemInSlot)) {
                    if (!this.moveItemStackTo(itemInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.moveItemStackTo(itemInSlot, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37) {
                    if (!this.moveItemStackTo(itemInSlot, 1, 28, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (itemInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemInSlot.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemInSlot);
        }

        return stack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return player.getMainHandItem() == this.itemInHand || player.getOffhandItem() == this.itemInHand;
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }
}
