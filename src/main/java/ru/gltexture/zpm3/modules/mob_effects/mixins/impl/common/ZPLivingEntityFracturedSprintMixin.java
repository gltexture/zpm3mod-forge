package ru.gltexture.zpm3.modules.mob_effects.mixins.impl.common;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;

@Mixin(LivingEntity.class)
public abstract class ZPLivingEntityFracturedSprintMixin {
    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    private void setSprinting(boolean pSprinting, CallbackInfo ci) {
        if ((Object) this instanceof LivingEntity self) {
            if (self.hasEffect(ZPMobEffects.fracture.get())) {
                ci.cancel();
            }
        }
    }
}