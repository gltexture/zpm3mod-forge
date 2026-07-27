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

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPZombieConfig;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.modules.mob_effects.utils.ZPEffectUtils;

@Mixin(Player.class)
public abstract class ZPPlayerSoundsThenPlaguedMixin {
    @Inject(method = "getHurtSound", at = @At("RETURN"), cancellable = true)
    private void getHurtSound(DamageSource pDamageSource, CallbackInfoReturnable<SoundEvent> cir) {
        if (ZPPlayerSoundsThenPlaguedMixin.zpm3forge$isHalfwayOrMorePlagued((LivingEntity) (Object) this) && ZPEffectUtils.isZombiePlagued((LivingEntity) (Object) this)) {
            cir.setReturnValue(SoundEvents.ZOMBIE_HURT);
        }
    }

    @Inject(method = "getDeathSound", at = @At("RETURN"), cancellable = true)
    private void getDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (ZPPlayerSoundsThenPlaguedMixin.zpm3forge$isHalfwayOrMorePlagued((LivingEntity) (Object) this) && ZPEffectUtils.isZombiePlagued((LivingEntity) (Object) this)) {
            cir.setReturnValue(SoundEvents.ZOMBIE_DEATH);
        }
    }

    @Unique
    private static boolean zpm3forge$isHalfwayOrMorePlagued(LivingEntity entity) {
        MobEffectInstance effect = entity.getEffect(ZPMobEffects.zombie_plague.get());
        if (effect == null) {
            return false;
        }
        int duration = effect.getDuration();
        int maxDuration = ZPZombieConfig.ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS.getVar();
        return duration <= maxDuration / 2;
    }
}