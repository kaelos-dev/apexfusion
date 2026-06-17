package net.kaelos.apexfusion.item;

import net.kaelos.apexfusion.AF;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Map;

public class AFItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AF.MOD_ID);

    public static final Map<ItemsList, RegistryObject<Item>> ITEMS_LIST = new EnumMap<>(ItemsList.class);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    static {
        for (ItemsList list : ItemsList.values()) {
            Item.Properties properties = new Item.Properties();
            ITEMS_LIST.put(list, ITEMS.register(list.getRegistryName(),
                    () -> list.create(properties)
            ));
        }
    }
}
