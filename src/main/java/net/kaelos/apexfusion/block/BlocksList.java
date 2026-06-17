package net.kaelos.apexfusion.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.Locale;
import java.util.function.Function;

public enum BlocksList {
    CREOSOTE_PLANKS(properties -> new Block(properties
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F, 3.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
    )),

    COKE_OVEN_CORE(Block::new),
    COKE_OVEN_CONSTRUCTION(Block::new);

    private final Function<BlockBehaviour.Properties, Block> blockFactory;

    BlocksList(Function<BlockBehaviour.Properties, Block> blockFactory) {
        this.blockFactory = blockFactory;
    }

    public String getRegistryName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public Block create(BlockBehaviour.Properties properties) {
        return this.blockFactory.apply(properties);
    }
}
