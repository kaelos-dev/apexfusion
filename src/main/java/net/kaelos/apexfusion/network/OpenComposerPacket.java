package net.kaelos.apexfusion.network;

import net.kaelos.apexfusion.item.tool.MultistructureComposerItem;
import net.kaelos.apexfusion.screen.tool.MultistructureComposerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class OpenComposerPacket {
    public OpenComposerPacket() {}

    public OpenComposerPacket(FriendlyByteBuf buffer) {}

    public void encode(FriendlyByteBuf buffer) {}

    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.getSender();
            if (player == null) return;

            ItemStack itemInHand = player.getMainHandItem();
            if (itemInHand.getItem() instanceof MultistructureComposerItem) {
                player.openMenu(new SimpleMenuProvider((containerId, inventory, entity) ->
                        new MultistructureComposerMenu(containerId, inventory, itemInHand),
                        Component.literal("Multistructure Composer")));
            }
        });

        context.setPacketHandled(true);
    }
}
