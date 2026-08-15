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

package ru.gltexture.zpm3.engine.mixins.impl.client.render;

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

import ru.gltexture.zpm3.engine.client.rendering.crosshair.ZPClientCrosshairRecoilManager;
import ru.gltexture.zpm3.engine.client.rendering.util.ZPRenderingUtil;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPClientConfig;

@Mixin(GameRenderer.class)
public class ZPGameRendererFovFixMixin {
    @Shadow
    @Final Minecraft minecraft;

    @Shadow
    private float oldFov;

    @Shadow
    private float fov;

    @Shadow
    private boolean panoramicMode;

    @Inject(method = "bobHurt", at = @At("HEAD"))
    private void bobHurt(PoseStack pPoseStack, float pPartialTicks, CallbackInfo ci) {
        final Vector3f cameraTransform = new Vector3f(ZPClientCrosshairRecoilManager.getCameraTranslatePrev()).lerp(ZPClientCrosshairRecoilManager.getCameraTranslate(), pPartialTicks);

        pPoseStack.mulPose(Axis.XP.rotationDegrees(cameraTransform.x * 0.25f));
        pPoseStack.mulPose(Axis.YP.rotationDegrees(cameraTransform.y));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(cameraTransform.z));
    }

    @Inject(method = "renderItemInHand", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER))
    private void renderItemInHand(PoseStack pPoseStack, Camera pActiveRenderInfo, float pPartialTicks, CallbackInfo ci) {
        if (ZPClientConfig.FIRST_PERSON_RENDER_SPACE_SCALE_BY_FOV.getVar()) {
            double f1 = ZPRenderingUtil.fovItemOffset(Minecraft.getInstance().gameRenderer.getMainCamera(), pPartialTicks, pPoseStack) * 0.5f;
            pPoseStack.translate(0.0f, f1 * -0.0625f, f1 * 0.25f);
        }
    }

    @Inject(method = "getFov", at = @At("HEAD"), cancellable = true)
    private void getFov(Camera pActiveRenderInfo, float pPartialTicks, boolean pUseFOVSetting, CallbackInfoReturnable<Double> cir) {
        if (ZPClientConfig.FIRST_PERSON_RENDER_SPACE_SCALE_BY_FOV.getVar()) {
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