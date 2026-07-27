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

package ru.gltexture.zpm3.modules.mob_effects.mixins.impl.common;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;

@Mixin(Entity.class)
public abstract class ZPEntityFracturedSprintMixin {
    @Inject(method = "isSprinting", at = @At("HEAD"), cancellable = true)
    private void isSprinting(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity self) {
            if (self.hasEffect(ZPMobEffects.fracture.get())) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    private void setSprinting(boolean pSprinting, CallbackInfo ci) {
        if ((Object) this instanceof LivingEntity self) {
            if (self.hasEffect(ZPMobEffects.fracture.get())) {
                this.setSharedFlag(3, false);
                ci.cancel();
            }
        }
    }

    @Shadow protected abstract void setSharedFlag(int i, boolean pSprinting);
}