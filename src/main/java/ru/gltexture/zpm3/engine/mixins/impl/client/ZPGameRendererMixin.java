package ru.gltexture.zpm3.engine.mixins.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.client.rendering.crosshair.recoil.ZPClientCrosshairRecoilManager;

@Mixin(GameRenderer.class)
public class ZPGameRendererMixin {
    @Inject(method = "bobHurt", at = @At("HEAD"))
    private void bobHurt(PoseStack pPoseStack, float pPartialTicks, CallbackInfo ci) {
        final Vector3f cameraTransform = new Vector3f(
                Mth.lerp(pPartialTicks, ZPClientCrosshairRecoilManager.getCameraTransformPrev().x, ZPClientCrosshairRecoilManager.getCameraTransform().x),
                Mth.lerp(pPartialTicks, ZPClientCrosshairRecoilManager.getCameraTransformPrev().y, ZPClientCrosshairRecoilManager.getCameraTransform().y),
                Mth.lerp(pPartialTicks, ZPClientCrosshairRecoilManager.getCameraTransformPrev().z, ZPClientCrosshairRecoilManager.getCameraTransform().z)
        );

        //pPoseStack.mulPose(Axis.XP.rotationDegrees(cameraTransform.x));
        pPoseStack.mulPose(Axis.YP.rotationDegrees(cameraTransform.y));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(cameraTransform.z));
    }
}
