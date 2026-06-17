package net.kaelos.apexfusion.item;

import net.kaelos.apexfusion.AF;
import net.kaelos.apexfusion.block.AFBlocks;
import net.kaelos.apexfusion.block.BlocksList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AFCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AF.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MATERIALS = TABS.register("materials",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + AF.MOD_ID + ".materials"))
                    .icon(() -> new ItemStack(AFBlocks.BLOCKS_LIST.get(BlocksList.CREOSOTE_PLANKS).get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(AFBlocks.BLOCKS_LIST.get(BlocksList.CREOSOTE_PLANKS).get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> TOOLS_ARMORS = TABS.register("tools_armors",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + AF.MOD_ID + ".tools_armors"))
                    .icon(() -> new ItemStack(AFItems.ITEMS_LIST.get(ItemsList.MULTISTRUCTURE_COMPOSER).get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(AFItems.ITEMS_LIST.get(ItemsList.MULTISTRUCTURE_COMPOSER).get());
                    }).build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
