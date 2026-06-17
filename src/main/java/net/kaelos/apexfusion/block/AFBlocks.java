package net.kaelos.apexfusion.block;

import net.kaelos.apexfusion.AF;
import net.kaelos.apexfusion.item.AFItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class AFBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AF.MOD_ID);

    public static final Map<BlocksList, RegistryObject<Block>> BLOCKS_LIST = new EnumMap<>(BlocksList.class);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> object = BLOCKS.register(name, block);
        registerBlockItem(name, object);
        return object;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        AFItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    static {
        for (BlocksList list : BlocksList.values()) {
            BlockBehaviour.Properties properties = BlockBehaviour.Properties.of();
            BLOCKS_LIST.put(list, registerBlock(list.getRegistryName(),
                    () -> list.create(properties)
            ));
        }
    }
}
