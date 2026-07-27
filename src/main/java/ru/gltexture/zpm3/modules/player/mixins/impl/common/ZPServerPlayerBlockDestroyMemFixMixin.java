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

package ru.gltexture.zpm3.modules.player.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3i;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelExt;

@Mixin(ServerPlayerGameMode.class)
public abstract class ZPServerPlayerBlockDestroyMemFixMixin {
    @Shadow protected ServerLevel level;
    @Shadow @Final protected ServerPlayer player;
    @Shadow private int gameTicks;
    @Shadow private int lastSentState = -1;

    @Inject(method = "incrementDestroyProgress", at = @At("HEAD"), cancellable = true)
    private void incrementDestroyProgress(BlockState pState, BlockPos pPos, int pStartTick, CallbackInfoReturnable<Float> cir) {
        float blockProgressFromGlobal = 0.0f;
        if (this.level instanceof IZPLevelExt ext) {
            final Vector3i pos = new Vector3i(pPos.getX(), pPos.getY(), pPos.getZ());
            float f = ext.zpm3forge$getGlobalBlocksDestroyMemory().getBlockProgressFromMemOrNegative(pos);
            if (f > 0.0f) {
                blockProgressFromGlobal = f / (pState.getDestroySpeed(this.level, pPos));
                ext.zpm3forge$getGlobalBlocksDestroyMemory().resetTicks(this.level, pos);
            }
        }

        int i = this.gameTicks - pStartTick;
        float f = pState.getDestroyProgress(this.player, this.player.level(), pPos) * (float) (i + 1) + blockProgressFromGlobal;
        int j = (int)(f * 10.0F);
        if (j != this.lastSentState) {
            this.level.destroyBlockProgress(this.player.getId(), pPos, j);
            this.lastSentState = j;
        }

        cir.setReturnValue(f);
    }
}
