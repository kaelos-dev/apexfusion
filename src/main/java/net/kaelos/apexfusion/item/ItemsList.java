package net.kaelos.apexfusion.item;

import net.kaelos.apexfusion.item.tool.MultistructureComposerItem;
import net.minecraft.world.item.Item;

import java.util.Locale;
import java.util.function.Function;

public enum ItemsList {
    MULTISTRUCTURE_COMPOSER(MultistructureComposerItem::new);

    private final Function<Item.Properties, Item> blockFactory;

    ItemsList(Function<Item.Properties, Item> blockFactory) {
        this.blockFactory = blockFactory;
    }

    public String getRegistryName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public Item create(Item.Properties properties) {
        return this.blockFactory.apply(properties);
    }
}
