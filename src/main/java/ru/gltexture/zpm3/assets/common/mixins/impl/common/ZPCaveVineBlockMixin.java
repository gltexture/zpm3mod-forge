package ru.gltexture.zpm3.assets.common.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import ru.gltexture.zpm3.assets.common.mixins.ext.IVineExt;

@Mixin(CaveVinesBlock.class)
public class ZPCaveVineBlockMixin implements IVineExt {
    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return false;
    }
}