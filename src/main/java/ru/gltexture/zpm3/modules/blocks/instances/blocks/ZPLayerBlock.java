package ru.gltexture.zpm3.modules.blocks.instances.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ZPLayerBlock extends SnowLayerBlock {
    public ZPLayerBlock(Properties pProperties) {
        super(pProperties);
    }

    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
    }
}
