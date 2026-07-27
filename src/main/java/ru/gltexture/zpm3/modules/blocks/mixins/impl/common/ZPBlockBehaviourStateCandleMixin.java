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

package ru.gltexture.zpm3.modules.blocks.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class ZPBlockBehaviourStateCandleMixin {
    @Shadow
    public abstract Block getBlock();

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void randomTick(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        if (this.getBlock() instanceof AbstractCandleBlock abstractCandleBlock && pLevel.getBlockState(pPos).getValue(AbstractCandleBlock.LIT)) {
            float chance = ZPWorldConfig.CANDLE_EACH_TICK_RANDOM_EXTINGUISH_CONST.getVar();
            if(pLevel.isRainingAt(pPos)) {
                chance *= 10.0f;
            }
            if (ZPRandom.getRandom().nextFloat() <= chance) {
                AbstractCandleBlock.extinguish(null, pLevel.getBlockState(pPos), pLevel, pPos);
            }
        }
    }

    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    public void randTickBool(CallbackInfoReturnable<Boolean> cir) {
        if (ZPWorldConfig.CANDLE_EACH_TICK_RANDOM_EXTINGUISH_CONST.getVar() > 0.0f) {
            if (this.getBlock() instanceof AbstractCandleBlock abstractCandleBlock) {
                cir.setReturnValue(true);
            }
        }
    }
}