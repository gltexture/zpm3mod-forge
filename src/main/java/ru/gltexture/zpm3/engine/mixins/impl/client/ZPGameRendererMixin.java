package ru.gltexture.zpm3.engine.mixins.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.client.rendering.crosshair.ZPClientCrosshairRecoilManager;

@Mixin(GameRenderer.class)
public class ZPGameRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private float oldFov;

    @Shadow
    private float fov;

    @Shadow
    private boolean panoramicMode;

    @Inject(method = "bobHurt", at = @At("HEAD"))
    private void bobHurt(PoseStack pPoseStack, float pPartialTicks, CallbackInfo ci) {
        final Vector3f cameraTransform = new Vector3f(ZPClientCrosshairRecoilManager.getCameraTransformPrev()).lerp(ZPClientCrosshairRecoilManager.getCameraTransform(), pPartialTicks);

        pPoseStack.mulPose(Axis.XP.rotationDegrees(cameraTransform.x * 0.25f));
        pPoseStack.mulPose(Axis.YP.rotationDegrees(cameraTransform.y));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(cameraTransform.z));
    }

    @Inject(method = "renderItemInHand", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER))
    private void renderItemInHand(PoseStack pPoseStack, Camera pActiveRenderInfo, float pPartialTicks, CallbackInfo ci) {
        if (ZPConstants.FIRST_PERSON_RENDER_SPACE_SCALE_BY_FOV) {
            double f1 = ZPRenderHelper.fovItemOffset(Minecraft.getInstance().gameRenderer.getMainCamera(), pPartialTicks, pPoseStack) * 0.5f;
            pPoseStack.translate(0.0f, f1 * -0.0625f, f1 * 0.25f);
        }
    }

    @Inject(method = "getFov", at = @At("HEAD"), cancellable = true)
    private void getFov(Camera pActiveRenderInfo, float pPartialTicks, boolean pUseFOVSetting, CallbackInfoReturnable<Double> cir) {
        if (ZPConstants.FIRST_PERSON_RENDER_SPACE_SCALE_BY_FOV) {
            if (this.panoramicMode) {
                cir.setReturnValue(90.0D);
            } else {
                double d0 = 70.0D;
                d0 = this.minecraft.options.fov().get().intValue();
                d0 *= Mth.lerp(pPartialTicks, this.oldFov, this.fov);

                if (pActiveRenderInfo.getEntity() instanceof LivingEntity && ((LivingEntity) pActiveRenderInfo.getEntity()).isDeadOrDying()) {
                    float f = Math.min((float) ((LivingEntity) pActiveRenderInfo.getEntity()).deathTime + pPartialTicks, 20.0F);
                    d0 /= (double) ((1.0F - 500.0F / (f + 500.0F)) * 2.0F + 1.0F);
                }

                FogType fogtype = pActiveRenderInfo.getFluidInCamera();
                if (fogtype == FogType.LAVA || fogtype == FogType.WATER) {
                    d0 *= Mth.lerp(this.minecraft.options.fovEffectScale().get(), 1.0D, 0.85714287F);
                }

                cir.setReturnValue(net.minecraftforge.client.ForgeHooksClient.getFieldOfView((GameRenderer) (Object) this, pActiveRenderInfo, pPartialTicks, d0, true));
            }
        }
    }
}