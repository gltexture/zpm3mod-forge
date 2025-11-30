package ru.gltexture.zpm3.assets.mob_effects.mixins.impl.common;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.assets.mob_effects.init.ZPMobEffects;

@Mixin(Entity.class)
public abstract class ZPEntitySprintMixin {
    @Inject(method = "isSprinting", at = @At("HEAD"), cancellable = true)
    private void isSprinting(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity self) {
            if (self.hasEffect(ZPMobEffects.fracture.get())) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "setSprinting", at = @At("HEAD"))
    private void setSprinting(boolean pSprinting, CallbackInfo ci) {
        if ((Object) this instanceof LivingEntity self) {
            if (self.hasEffect(ZPMobEffects.fracture.get())) {
                this.setSharedFlag(3, false);
            }
        }
    }

    @Shadow protected abstract void setSharedFlag(int i, boolean pSprinting);
}