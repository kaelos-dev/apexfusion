package net.kaelos.apexfusion;

import net.kaelos.apexfusion.block.AFBlocks;
import net.kaelos.apexfusion.item.AFCreativeModeTabs;
import net.kaelos.apexfusion.item.AFItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AF.MOD_ID)
public class AF {
    public static final String MOD_ID = "apexfusion";

    public AF(final FMLJavaModLoadingContext context) {
        IEventBus eventBus = context.getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        AFItems.register(eventBus);
        AFBlocks.register(eventBus);
        AFCreativeModeTabs.register(eventBus);
    }
}
