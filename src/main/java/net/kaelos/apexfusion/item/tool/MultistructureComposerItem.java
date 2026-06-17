package net.kaelos.apexfusion.item.tool;

import net.kaelos.apexfusion.screen.tool.MultistructureComposerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MultistructureComposerItem extends Item {
    public MultistructureComposerItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide() && player.isCrouching()) {
            player.openMenu(new SimpleMenuProvider((containerId, inventory, entity) ->
                    new MultistructureComposerMenu(containerId, inventory, itemStack), Component.literal("Multistructure Composer")));
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
}
