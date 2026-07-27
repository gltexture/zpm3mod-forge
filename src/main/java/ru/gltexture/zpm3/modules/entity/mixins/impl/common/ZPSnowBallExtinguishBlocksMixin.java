/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.entity.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.instances.blocks.ZPTorchBlock;
import ru.gltexture.zpm3.engine.instances.blocks.ZPWallTorchBlock;
import ru.gltexture.zpm3.modules.blocks.init.ZPCampfireBlocks;
import ru.gltexture.zpm3.modules.blocks.init.ZPTorchBlocks;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.common.init.ZPTags;

@Mixin(Snowball.class)
public class ZPSnowBallExtinguishBlocksMixin {
    @Inject(method = "onHit", at = @At("HEAD"))
    protected void onHit(HitResult pResult, CallbackInfo ci) {
        Level level = ((Entity) (Object) this).level();
        if (!level.isClientSide) {
            if (pResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) pResult).getBlockPos();
                BlockState state = level.getBlockState(blockPos);
                BlockState newState = null;
                if (state.getBlock() == Blocks.TORCH || (state.is(ZPTags.B_ACTIVE_TORCH_BLOCK))) {
                    newState = ZPTorchBlocks.torch5.get().defaultBlockState();
                    newState = ZPUtility.blocks().copyProperties(state, newState);
                    level.setBlock(blockPos, newState, Block.UPDATE_ALL);
                } else if (state.getBlock() == Blocks.WALL_TORCH || (state.is(ZPTags.B_ACTIVE_TORCH_WALL_BLOCK))) {
                    newState = ZPTorchBlocks.torch5_wall.get().defaultBlockState();
                    newState = ZPUtility.blocks().copyProperties(state, newState);
                }
                if (newState != null) {
                    level.playSound(null, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(blockPos, newState, Block.UPDATE_ALL);
                }
                if (state.getBlock() == Blocks.CAMPFIRE || state.getBlock() == ZPCampfireBlocks.campfire2.get()) {
                    boolean flag = state.getValue(CampfireBlock.LIT);
                    if (flag) {
                        level.playSound(null, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        state.setValue(CampfireBlock.LIT, false);
                    }
                }
            }
        }
    }
}
