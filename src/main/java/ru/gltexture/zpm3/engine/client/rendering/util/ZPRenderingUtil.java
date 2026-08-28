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

package ru.gltexture.zpm3.engine.client.rendering.util;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.rendering.gl.base.ITexture2DProgram;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPClientConfig;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.items.ZPItemMedicine;
import ru.gltexture.zpm3.engine.mixins.impl.client.render.ZPGameRendererFovAccessor;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.List;
import java.util.function.Consumer;

public abstract class ZPRenderingUtil {
    public static double fovItemOffset(Camera camera, float partialTicks, PoseStack poseStack) {
        if (ZPClientConfig.FIRST_PERSON_RENDER_SCALE_TYPE.getVar() == 1) {
            return 0.0f;
        }
        final double def = 70.0f;
        final double maxAbs = 110.0f - def;
        double fov = ((ZPGameRendererFovAccessor) Minecraft.getInstance().gameRenderer).invokeGetFov(camera, partialTicks, true);
        double absFov = (fov - def) / maxAbs;
        return Math.pow(Math.abs(absFov), 1.25f) * Math.signum(absFov);
    }

    @SuppressWarnings("all")
    public static boolean isPlayerModelSlim() {
        PlayerInfo info = Minecraft.getInstance().getConnection().getPlayerInfo(Minecraft.getInstance().player.getUUID());
        if (info != null) {
            boolean slim = info.getModelName().equals("slim");
        }
        return false;
    }

    public static void blockAnimation(AbstractClientPlayer pPlayer, float pPartialTicks, InteractionHand pHand, ItemStack pStack, PoseStack pPoseStack) {
        if (pStack.getItem() instanceof ZPItemMedicine) {
            if (pPlayer.isUsingItem() && pPlayer.getUseItemRemainingTicks() > 0 && pPlayer.getUsedItemHand() == pHand) {
                if (pStack.getUseAnimation() == UseAnim.BLOCK) {
                    applyMedicineTransform(pPlayer, pPoseStack, pPartialTicks, (pHand.equals(InteractionHand.OFF_HAND) ? HumanoidArm.LEFT : HumanoidArm.RIGHT), pStack);
                }
            }
        }
    }

    private static void applyMedicineTransform(AbstractClientPlayer pPlayer, PoseStack pPoseStack, float pPartialTicks, HumanoidArm pHand, ItemStack pStack) {
        float f = (float) pPlayer.getUseItemRemainingTicks() - pPartialTicks + 1.0F;
        float f1 = f / (float) pStack.getUseDuration();

        float f3 = 1.0F - (float) Math.pow(f1, 4.0D);

        if (pHand == HumanoidArm.RIGHT) {
            pPoseStack.translate(f3 * 0.32f, f3 * 0.28f, f3 * -0.15f);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(f3 * 98.0f));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(f3 * -67.0f));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(f3 * -10.0f));
        } else { //TODO
            pPoseStack.translate(-f3 * 0.32f, f3 * 0.28f, f3 * -0.15f);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(f3 * -98.0f));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(f3 * -67.0f));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(f3 * 10.0f));
        }
    }

    public static void addAcidParticles(Entity entity) {
        addAcidParticles(20, entity);
    }

    public static void addAcidParticles(int acidLevel, Entity entity) {
        final float scaleMul = entity.getBbWidth() * entity.getBbHeight();
        final float scaling = Mth.clamp((acidLevel * scaleMul) / 20.0f, 0.175f, 1.0f);
        int maxParticles = 1 + (int) Math.floor(scaleMul * 2);
        maxParticles = Math.min(maxParticles, 1);

        for (int i = 0; i < maxParticles; ++i) {
            final Vector3f randomVector = ZPRandom.instance.randomVector3f(0.15f, new Vector3f(0.3f, 0.1f, 0.3f)).mul(scaling * scaleMul);
            final Vector3f position = entity.position().toVector3f().add(0.0f, (ZPRandom.instance.randomFloat(entity.getBbHeight()) * 0.8f) + (entity.getBbHeight() * 0.1f), 0.0f);
            position.add(ZPRandom.instance.randomVector3f(0.3f, new Vector3f(0.6f, 0.3f, 0.6f)));
            ZPCommonClientUtils.emmitAcidParticle((2.0f * scaling) + 0.2f + ZPRandom.getRandom().nextFloat(0.4f), position, new Vector3f(randomVector.x, (randomVector.y * 0.1f) + 0.05f, randomVector.z));
        }
    }

    @Deprecated(forRemoval = true)
    public static void renderTextureIDScreenOverlayFromFBO(@NotNull ShaderInstance shaderToRender, @NotNull Consumer<ShaderInstance> doUniforms, @NotNull List<Pair<String, ITexture2DProgram>> texturesWithUniforms) {
        final boolean blendEnabled = GL46.glIsEnabled(GL46.GL_BLEND);
        final boolean depthTestEnabled = GL46.glIsEnabled(GL46.GL_DEPTH_TEST);
        final boolean cullFaceEnabled = GL46.glIsEnabled(GL46.GL_CULL_FACE);
        final boolean depthWriteEnabled = GL46.glGetBoolean(GL46.GL_DEPTH_WRITEMASK);
        final int blendSrcRgb = GL46.glGetInteger(GL46.GL_BLEND_SRC_RGB);
        final int blendDstRgb = GL46.glGetInteger(GL46.GL_BLEND_DST_RGB);
        final int blendSrcAlpha = GL46.glGetInteger(GL46.GL_BLEND_SRC_ALPHA);
        final int blendDstAlpha = GL46.glGetInteger(GL46.GL_BLEND_DST_ALPHA);
        final int activeTexture = GL46.glGetInteger(GL46.GL_ACTIVE_TEXTURE);
        final int[] viewport = new int[4];
        GL46.glGetIntegerv(GL46.GL_VIEWPORT, viewport);

        final int textureCount = texturesWithUniforms.size();
        final int[] textureBindings = new int[textureCount];
        final int[] samplerBindings = new int[textureCount];
        for (int i = 0; i < textureCount; i++) {
            final int textureUnit = GL46.GL_TEXTURE0 + i;
            GL46.glActiveTexture(textureUnit);
            textureBindings[i] = GL46.glGetInteger(GL46.GL_TEXTURE_BINDING_2D);
            samplerBindings[i] = GL46.glGetInteger(GL46.GL_SAMPLER_BINDING);
        }

        try {
            shaderToRender.apply();
            GL46.glEnable(GL46.GL_BLEND);
            GL46.glDisable(GL46.GL_DEPTH_TEST);
            int texUnit = 0;
            for (Pair<String, ITexture2DProgram> pair : texturesWithUniforms) {
                final String uniformName = pair.first();
                final ITexture2DProgram textureProgram = pair.second();
                final Uniform uniform = shaderToRender.getUniform(uniformName);
                if (uniform != null) {
                    uniform.set(texUnit);
                }
                GL46.glActiveTexture(GL46.GL_TEXTURE0 + texUnit);
                textureProgram.bindSampler(texUnit);
                textureProgram.bindTexture();
                texUnit++;
            }
            doUniforms.accept(shaderToRender);
            ZombiePlague3.getClientManager().renderScreenMesh();
        } finally {
            for (int i = 0; i < textureCount; i++) {
                final int textureUnit = GL46.GL_TEXTURE0 + i;
                GL46.glActiveTexture(textureUnit);
                texturesWithUniforms.get(i).second().unBindSampler(i);
                GL46.glBindTexture(GL46.GL_TEXTURE_2D, textureBindings[i]);
                GL46.glBindSampler(i, samplerBindings[i]);
            }

            GL46.glActiveTexture(activeTexture);
            if (blendEnabled) {
                GL46.glEnable(GL46.GL_BLEND);
            } else {
                GL46.glDisable(GL46.GL_BLEND);
            }
            GL46.glBlendFuncSeparate(blendSrcRgb, blendDstRgb, blendSrcAlpha, blendDstAlpha);
            if (depthTestEnabled) {
                GL46.glEnable(GL46.GL_DEPTH_TEST);
            } else {
                GL46.glDisable(GL46.GL_DEPTH_TEST);
            }
            GL46.glDepthMask(depthWriteEnabled);
            if (cullFaceEnabled) {
                GL46.glEnable(GL46.GL_CULL_FACE);
            } else {
                GL46.glDisable(GL46.GL_CULL_FACE);
            }
            GL46.glViewport(viewport[0], viewport[1], viewport[2], viewport[3]);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
        }
    }

    public static Vector2i getWindowSize() {
        final Minecraft mc = Minecraft.getInstance();
        final int w = mc.getWindow().getWidth();
        final int h = mc.getWindow().getHeight();
        return new Vector2i(w, h);
    }
}
