package ru.gltexture.zpm3.engine.instances.blocks;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ZPStairsBlock extends StairBlock {
    public ZPStairsBlock(@NotNull Supplier<BlockState> state, @NotNull Properties properties) {
        super(state, properties);
    }
}