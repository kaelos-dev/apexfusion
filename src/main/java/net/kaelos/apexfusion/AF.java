package net.kaelos.apexfusion;

import net.kaelos.apexfusion.block.AFBlocks;
import net.kaelos.apexfusion.item.AFCreativeModeTabs;
import net.kaelos.apexfusion.item.AFItems;
import net.kaelos.apexfusion.screen.AFMenuTypes;
import net.kaelos.apexfusion.screen.tool.MultistructureComposerScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
        AFMenuTypes.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(AFMenuTypes.MULTISTRUCTURE_COMPOSER_MENU.get(), MultistructureComposerScreen::new);
        }
    }
}
