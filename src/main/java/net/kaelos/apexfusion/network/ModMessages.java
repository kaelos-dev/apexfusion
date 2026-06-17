package net.kaelos.apexfusion.network;

import net.kaelos.apexfusion.AF;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public class ModMessages {
    public static final SimpleChannel INSTANCE = ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath(AF.MOD_ID, "messages"))
            .networkProtocolVersion(1)
            .simpleChannel();

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.messageBuilder(ServerboundAssemblePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerboundAssemblePacket::encode)
                .decoder(ServerboundAssemblePacket::new)
                .consumerMainThread(ServerboundAssemblePacket::handle)
                .add();

        INSTANCE.messageBuilder(OpenComposerPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(OpenComposerPacket::encode)
                .decoder(OpenComposerPacket::new)
                .consumerMainThread(OpenComposerPacket::handle)
                .add();
    }
}
