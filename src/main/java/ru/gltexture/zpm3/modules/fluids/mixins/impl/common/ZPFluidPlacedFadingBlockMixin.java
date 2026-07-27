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

package ru.gltexture.zpm3.modules.fluids.mixins.impl.common;

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

import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlocks;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPFadingBlockEntity;

@Mixin(BucketItem.class)
public class ZPFluidPlacedFadingBlockMixin {
    @Inject(method = "playEmptySound", at = @At("TAIL"))
    protected void playEmptySound(Player pPlayer, LevelAccessor pLevel, BlockPos pPos, CallbackInfo ci) {
        if (pLevel instanceof ServerLevel) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be != null) {
                BlockState blockState = pLevel.getBlockState(pPos);
                if (pPlayer != null && !(ZPWorldConfig.SKIP_FADE_TICKING_LAVA_ACID_PLACED_IN_CREATIVE.getVar() && pPlayer.isCreative()) && (blockState.getBlock() == Blocks.LAVA || blockState.getBlock() == ZPBlocks.acid_block.get()) && be instanceof ZPFadingBlockEntity zpFadingBlock) {
                    zpFadingBlock.setActive(true);
                }
            }
        }
    }
}