package ru.gltexture.zpm3.assets.common.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;

@Mixin(BucketItem.class)
public class ZPFluidPlacedMixin {
    @Inject(method = "playEmptySound", at = @At("TAIL"))
    protected void playEmptySound(Player pPlayer, LevelAccessor pLevel, BlockPos pPos, CallbackInfo ci) {
        if (pLevel instanceof ServerLevel) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be != null) {
                BlockState blockState = pLevel.getBlockState(pPos);
                if (pPlayer != null && blockState.getBlock() == Blocks.LAVA && be instanceof ZPFadingBlockEntity zpFadingBlock) {
                    zpFadingBlock.setActive(true);
                }
            }
        }
    }
}