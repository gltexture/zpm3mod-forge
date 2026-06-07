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
import ru.gltexture.zpm3.modules.common.global.ZPConstants;
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
        int maxDuration = ZPConstants.ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS;
        return duration <= maxDuration / 2;
    }
}