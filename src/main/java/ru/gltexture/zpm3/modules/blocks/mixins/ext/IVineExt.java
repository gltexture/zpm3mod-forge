package ru.gltexture.zpm3.modules.blocks.mixins.ext;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public interface IVineExt {
    boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity);
}
