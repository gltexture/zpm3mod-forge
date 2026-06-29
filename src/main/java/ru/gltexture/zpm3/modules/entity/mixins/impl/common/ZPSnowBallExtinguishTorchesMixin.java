package ru.gltexture.zpm3.modules.entity.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.blocks.init.ZPTorchBlocks;
import ru.gltexture.zpm3.engine.service.ZPUtility;

@Mixin(Snowball.class)
public class ZPSnowBallExtinguishTorchesMixin {
    @Inject(method = "onHit", at = @At("HEAD"))
    protected void onHit(HitResult pResult, CallbackInfo ci) {
        Level level = ((Entity) (Object) this).level();
        if (!level.isClientSide) {
            if (pResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) pResult).getBlockPos();
                BlockState state = level.getBlockState(blockPos);
                BlockState newState = null;
                if (state.getBlock() == Blocks.TORCH || state.getBlock() == ZPTorchBlocks.torch2.get() || state.getBlock() == ZPTorchBlocks.torch3.get() || state.getBlock() == ZPTorchBlocks.torch4.get()) {
                    newState = ZPTorchBlocks.torch5.get().defaultBlockState();
                    newState = ZPUtility.blocks().copyProperties(state, newState);
                    level.setBlock(blockPos, newState, Block.UPDATE_ALL);
                }
                if (state.getBlock() == Blocks.WALL_TORCH || state.getBlock() == ZPTorchBlocks.torch2_wall.get() || state.getBlock() == ZPTorchBlocks.torch3_wall.get() || state.getBlock() == ZPTorchBlocks.torch4_wall.get()) {
                    newState = ZPTorchBlocks.torch5_wall.get().defaultBlockState();
                    newState = ZPUtility.blocks().copyProperties(state, newState);
                }
                if (newState != null) {
                    level.playSound(null, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(blockPos, newState, Block.UPDATE_ALL);
                }
            }
        }
    }
}
