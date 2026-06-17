package net.kaelos.apexfusion.screen;

import net.kaelos.apexfusion.AF;
import net.kaelos.apexfusion.screen.tool.MultistructureComposerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AFMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, AF.MOD_ID);

    public static final RegistryObject<MenuType<MultistructureComposerMenu>> MULTISTRUCTURE_COMPOSER_MENU =
            MENUS.register("multistructure_composer_name", () -> IForgeMenuType.create((containerId, inventory, extraData) -> new MultistructureComposerMenu(containerId, inventory)));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
