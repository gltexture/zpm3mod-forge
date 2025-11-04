package ru.gltexture.zpm3.engine.client.utils;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.base.ITexture2DProgram;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.items.ZPItemMedicine;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.List;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public abstract class ClientRenderFunctions {
    public static void blockAnimation(AbstractClientPlayer pPlayer, float pPartialTicks, InteractionHand pHand, ItemStack pStack, PoseStack pPoseStack) {
        if (pStack.getItem() instanceof ZPItemMedicine) {
            if (pPlayer.isUsingItem() && pPlayer.getUseItemRemainingTicks() > 0 && pPlayer.getUsedItemHand() == pHand) {
                if (pStack.getUseAnimation() == UseAnim.BLOCK) {
                    ClientRenderFunctions.applyMedicineTransform(pPlayer, pPoseStack, pPartialTicks, (pHand.equals(InteractionHand.OFF_HAND) ? HumanoidArm.LEFT : HumanoidArm.RIGHT), pStack);
                }
            }
        }
    }

    private static void applyMedicineTransform(AbstractClientPlayer pPlayer, PoseStack pPoseStack, float pPartialTicks, HumanoidArm pHand, ItemStack pStack) {
        float f = (float) pPlayer.getUseItemRemainingTicks() - pPartialTicks + 1.0F;
        float f1 = f / (float) pStack.getUseDuration();

        float f3 = 1.0F - (float) Math.pow(f1, 27.0D);
        int i = pHand == HumanoidArm.RIGHT ? 1 : -1;
        pPoseStack.translate(f3 * 1.0F * (float) i, f3 * -0.2F, f3 * 0.1F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees((float) i * f3 * 80.0F));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(f3 * 10.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees((float) i * f3 * 10.0F));
    }

    public static void addAcidParticles(Entity entity) {
        int maxParticles = 1 + (int) Math.floor(entity.getBbWidth() * entity.getBbHeight());
        maxParticles = Math.min(maxParticles, 6);

        for (int i = 0; i < maxParticles; ++i) {
            final Vector3f randomVector = ZPRandom.instance.randomVector3f(0.15f, new Vector3f(0.3f, 0.1f, 0.3f));
            final Vector3f position = entity.position().toVector3f().add(0.0f, ZPRandom.instance.randomFloat(entity.getBbHeight()), 0.0f);
            position.add(ZPRandom.instance.randomVector3f(0.3f, new Vector3f(0.6f, 0.3f, 0.6f)));
            ZPCommonClientUtils.emmitAcidParticle(2.2f + ZPRandom.getRandom().nextFloat(0.3f), position, new Vector3f(randomVector.x, (randomVector.y * 0.1f) + 0.05f, randomVector.z));
        }
    }

    public static void renderTextureIDScreenOverlayFromFBO(@NotNull ShaderInstance shaderToRender, @NotNull Consumer<ShaderInstance> doUniforms, @NotNull List<Pair<String, ITexture2DProgram>> texturesWithUniforms) {
        shaderToRender.apply();

        GL46.glDisable(GL46.GL_CULL_FACE);
        GL46.glEnable(GL46.GL_BLEND);
        GL46.glDisable(GL46.GL_DEPTH_TEST);

        int texUnit = 0;
        for (Pair<String, ITexture2DProgram> pair : texturesWithUniforms) {
            String uniformName = pair.first();
            ITexture2DProgram textureProgram = pair.second();

            Uniform uniform = shaderToRender.getUniform(uniformName);
            if (uniform != null) {
                uniform.set(texUnit);
            }

            GL46.glActiveTexture(GL46.GL_TEXTURE0 + texUnit);
            textureProgram.bindSampler(texUnit);
            textureProgram.bindTexture();

            texUnit++;
        }

        doUniforms.accept(shaderToRender);

        ZPRenderHelper.INSTANCE.renderZpScreenMesh();

        texUnit = 0;
        for (Pair<String, ITexture2DProgram> pair : texturesWithUniforms) {
            pair.second().unBindSampler(texUnit);
            texUnit++;
        }

        GL46.glEnable(GL46.GL_DEPTH_TEST);
        GL46.glActiveTexture(GL46.GL_TEXTURE0);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
    }

    //TEMP
    @Deprecated
    public static void renderTextureIDScreenOverlayFromFBO2(@NotNull ShaderInstance shaderToRender, @NotNull Consumer<ShaderInstance> doUniforms, @Nullable List<Pair<String, ITexture2DProgram>> texturesWithUniforms, @Nullable List<Pair<String, Integer>> texturesWithUniforms2) {
        shaderToRender.apply();

        GL46.glDisable(GL46.GL_CULL_FACE);
        GL46.glEnable(GL46.GL_BLEND);
        GL46.glDisable(GL46.GL_DEPTH_TEST);

        int texUnit = 0;
        if (texturesWithUniforms != null) {
            for (Pair<String, ITexture2DProgram> pair : texturesWithUniforms) {
                String uniformName = pair.first();
                ITexture2DProgram textureProgram = pair.second();

                Uniform uniform = shaderToRender.getUniform(uniformName);
                if (uniform != null) {
                    uniform.set(texUnit);
                }

                GL46.glActiveTexture(GL46.GL_TEXTURE0 + texUnit);
                textureProgram.bindSampler(texUnit);
                textureProgram.bindTexture();
                texUnit++;
            }
        }

        if (texturesWithUniforms2 != null) {
            int samplerDef = GL46.glGenSamplers();
            GL46.glSamplerParameteri(samplerDef, GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_NEAREST);
            GL46.glSamplerParameteri(samplerDef, GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_NEAREST);
            GL46.glSamplerParameteri(samplerDef, GL46.GL_TEXTURE_WRAP_S, GL46.GL_CLAMP_TO_EDGE);
            GL46.glSamplerParameteri(samplerDef, GL46.GL_TEXTURE_WRAP_T, GL46.GL_CLAMP_TO_EDGE);
            for (Pair<String, Integer> pair : texturesWithUniforms2) {
                String uniformName = pair.first();
                Uniform uniform = shaderToRender.getUniform(uniformName);
                if (uniform != null) {
                    uniform.set(texUnit);
                }

                GL46.glActiveTexture(GL46.GL_TEXTURE0 + texUnit);
                GL46.glBindSampler(texUnit, samplerDef);
                GL46.glBindTexture(GL46.GL_TEXTURE_2D, pair.second());
                texUnit++;
            }
            GL46.glDeleteSamplers(samplerDef);
        }

        doUniforms.accept(shaderToRender);
        ZPRenderHelper.INSTANCE.renderZpScreenMesh();

        if (texturesWithUniforms != null) {
            texUnit = 0;
            for (Pair<String, ITexture2DProgram> pair : texturesWithUniforms) {
                pair.second().unBindSampler(texUnit);
                texUnit++;
            }
        }

        GL46.glEnable(GL46.GL_DEPTH_TEST);
        GL46.glActiveTexture(GL46.GL_TEXTURE0);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
    }

    public static Vector2i getWindowSize() {
        final Minecraft mc = Minecraft.getInstance();
        final int w = mc.getWindow().getWidth();
        final int h = mc.getWindow().getHeight();
        return new Vector2i(w, h);
    }
}