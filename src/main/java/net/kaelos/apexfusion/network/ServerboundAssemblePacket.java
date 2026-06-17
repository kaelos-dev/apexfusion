package net.kaelos.apexfusion.network;

import net.kaelos.apexfusion.AF;
import net.kaelos.apexfusion.blueprint.BlueprintData;
import net.kaelos.apexfusion.blueprint.BlueprintManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class ServerboundAssemblePacket {
    public final ResourceLocation blueprintId;
    public final BlockPos targetPos;

    public ServerboundAssemblePacket(ResourceLocation blueprintId, BlockPos targetPos) {
        this.blueprintId = blueprintId;
        this.targetPos = targetPos;
    }

    public ServerboundAssemblePacket(FriendlyByteBuf buffer) {
        this.blueprintId = buffer.readResourceLocation();
        this.targetPos = buffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(this.blueprintId);
        buffer.writeBlockPos(this.targetPos);
    }

    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            Player player = context.getSender();
            if (player == null || player.level().isClientSide()) return;

            BlueprintData data = BlueprintManager.BLUEPRINTS.get(this.blueprintId);
            if (data == null) return;

            boolean hasAllResources = true;
            for (Map.Entry<String, Integer> entry : data.requiredMaterials().entrySet()) {
                Item requiredItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(entry.getKey()));
                int requiredAmount = entry.getValue();
                int currentAmount = 0;

                for (ItemStack stack : player.getInventory().items) {
                    if (stack.getItem() == requiredItem) {
                        currentAmount += stack.getCount();
                    }
                }

                if (currentAmount < requiredAmount) {
                    hasAllResources = false;
                    break;
                }
            }

            if (hasAllResources) {
                for (Map.Entry<String, Integer> entry : data.requiredMaterials().entrySet()) {
                    Item requiredItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(entry.getKey()));
                    int amountToTake = entry.getValue();

                    for (ItemStack stack : player.getInventory().items) {
                        if (stack.getItem() == requiredItem) {
                            int taken = Math.min(stack.getCount(), amountToTake);
                            stack.shrink(taken);
                            amountToTake -= taken;

                            if (amountToTake <= 0) break;
                        }
                    }
                }

                Block resultBlock = ForgeRegistries.BLOCKS.getValue(data.result());
                assert resultBlock != null;
                player.level().setBlockAndUpdate(this.targetPos, resultBlock.defaultBlockState());

                player.level().playSound(null, this.targetPos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);

                player.displayClientMessage(Component.translatable(AF.MOD_ID + ".gui.message.successfully_assembled"), true);
            } else {
                player.displayClientMessage(Component.translatable(AF.MOD_ID + ".gui.message.unsuccessfully_assembled"), true);
            }
        });

        context.setPacketHandled(true);
    }
}
