package ru.gltexture.zpm3.assets.debug.mixins.impl.client;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.debug.events.ZPFreeCameraEvents;

@OnlyIn(Dist.CLIENT)
@Mixin(Camera.class)
public abstract class ZPCameraMixin {
    @Shadow
    protected abstract void setRotation(float pYRot, float pXRot);
    @Shadow protected abstract void setPosition(Vec3 pPos);

    @Inject(method = "setup", at = @At("HEAD"), cancellable = true)
    private void setup(BlockGetter pLevel, Entity pEntity, boolean pDetached, boolean pThirdPersonReverse, float pPartialTick, CallbackInfo ci) {
        if (ZPFreeCameraEvents.enabled) {
            this.setPosition(ZPFreeCameraEvents.freecamPos);
            this.setRotation(ZPFreeCameraEvents.yaw, ZPFreeCameraEvents.pitch);
            ci.cancel();
        }
    }
}
