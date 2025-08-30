package ru.gltexture.zpm3.assets.guns.rendering;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.fbo.FBOTexture2DProgram;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.client.utils.ClientRenderFunctions;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.List;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public abstract class ZPGunLayersProcessing {

    @SuppressWarnings("unchecked")
    public static void postRenderMflash1Person(ZPDefaultGunMuzzleflashFX defaultGunMuzzleflashFX) {
        if (!defaultGunMuzzleflashFX.useFbo() || ZPDefaultGunMuzzleflashFX.muzzleflashFBO.getTexturePrograms().isEmpty()) {
            return;
        }

        final int w = ClientRenderFunctions.getWindowSize().x;
        final int h = ClientRenderFunctions.getWindowSize().y;
        final Matrix4f orthographic2D = new Matrix4f().setOrtho2D(0, w, h, 0);
        final Matrix4f fullMatrix = new Matrix4f().identity().translate(new Vector3f(0, h, 0f)).scale(w, -h, 1.0F);
        final Matrix4f halfMatrix = (new Matrix4f().identity().translate(new Vector3f(0, h, 0f)).scaleXY(w * ZPDefaultGunMuzzleflashFX.MFLASH_FBO_SCALE, -h * ZPDefaultGunMuzzleflashFX.MFLASH_FBO_SCALE));

        final int iterations = DearUITRSInterface.muzzleflashFboPingPongOperations;
        FBOTexture2DProgram mainFbo = ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBO;
        FBOTexture2DProgram secondFbo = ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBOPingPong;
        FBOTexture2DProgram temp = null;

        GL46.glEnable(GL46.GL_BLEND);
        GL46.glBlendEquation(GL46.GL_FUNC_ADD);
        GL46.glBlendFuncSeparate(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA, GL46.GL_ONE, GL46.GL_ONE_MINUS_SRC_ALPHA);
        mainFbo.bindFBO();
        GL46.glClearBufferfv(GL46.GL_COLOR, 0, new float[]{1.0f, 0.9f, 0.8f, 0.0f});
        ClientRenderFunctions.renderTextureIDScreenOverlayFromFBO(Objects.requireNonNull(ZPDefaultShaders.image.getShaderInstance()), (shaderToRender) -> {
            Uniform mod = shaderToRender.getUniform("sModelViewMat");
            Uniform proj = shaderToRender.getUniform("sProjMat");
            Objects.requireNonNull(proj).set(orthographic2D);
            Objects.requireNonNull(mod).set(halfMatrix);
        }, List.of(Pair.of("texture_map", ZPDefaultGunMuzzleflashFX.muzzleflashFBO.getTextureByIndex(2))));
        mainFbo.unBindFBO();

        { //BLOOM PING-PONG BLUR(BOX)
            for (int i = 0; i < iterations; i++) {
                mainFbo.bindFBO();
                ClientRenderFunctions.renderTextureIDScreenOverlayFromFBO(Objects.requireNonNull(ZPDefaultShaders.blur_box.getShaderInstance()), (shaderToRender) -> {
                    Uniform mod = shaderToRender.getUniform("sModelViewMat");
                    Uniform proj = shaderToRender.getUniform("sProjMat");
                    Uniform blur_radius = shaderToRender.getUniform("blur_radius");
                    Objects.requireNonNull(blur_radius).set(DearUITRSInterface.muzzleFlashBlurring);
                    Objects.requireNonNull(proj).set(orthographic2D);
                    Objects.requireNonNull(mod).set(halfMatrix);
                }, List.of(Pair.of("texture_map", mainFbo.getTextureByIndex(0))));
                mainFbo.unBindFBO();

                mainFbo.copyFBOtoFBOColor(secondFbo.getFrameBufferId(), new Pair[]{Pair.of(GL46.GL_COLOR_ATTACHMENT0, GL46.GL_COLOR_ATTACHMENT0)}
                        , new Vector2i((int) (w * ZPDefaultGunMuzzleflashFX.MFLASH_FBO_SCALE), (int) (h * ZPDefaultGunMuzzleflashFX.MFLASH_FBO_SCALE))
                        , new Vector2i((int) (w * ZPDefaultGunMuzzleflashFX.MFLASH_FBO_SCALE), (int) (h * ZPDefaultGunMuzzleflashFX.MFLASH_FBO_SCALE)));
                temp = mainFbo;
                mainFbo = secondFbo;
                secondFbo = temp;
            }
        }

      //  ZPDefaultGunMuzzleflashFX.muzzleflashFBO.bindFBO();
      //  ZPDefaultGunMuzzleflashFX.muzzleflashFBO.connectTextureToBuffer(GL46.GL_COLOR_ATTACHMENT1, 1);
      //  if (true) {
      //      ClientRenderFunctions.renderTextureIDScreenOverlayFromFBO(Objects.requireNonNull(ZPDefaultShaders.image.getShaderInstance()), (shaderToRender) -> {
      //          Uniform mod = shaderToRender.getUniform("sModelViewMat");
      //          Uniform proj = shaderToRender.getUniform("sProjMat");
      //          Objects.requireNonNull(proj).set(orthographic2D);
      //          Objects.requireNonNull(mod).set(fullMatrix);
      //      }, List.of(Pair.of("texture_map", ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBO.getTextureByIndex(0))));
      //  }
      //  ZPDefaultGunMuzzleflashFX.muzzleflashFBO.unBindFBO();

        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        GL46.glBlendFuncSeparate(GL46.GL_ONE, GL46.GL_ONE_MINUS_SRC_ALPHA, GL46.GL_ONE, GL46.GL_ONE_MINUS_SRC_ALPHA);
        ClientRenderFunctions.renderTextureIDScreenOverlayFromFBO(Objects.requireNonNull(ZPDefaultShaders.gun_gluing.getShaderInstance()), (shaderToRender) -> {
            Uniform mod = shaderToRender.getUniform("sModelViewMat");
            Uniform proj = shaderToRender.getUniform("sProjMat");

            Uniform mflash_posRight = shaderToRender.getUniform("mflash_posRight");
            Uniform mflash_posLeft = shaderToRender.getUniform("mflash_posLeft");
            Uniform mflash_scissorRight = shaderToRender.getUniform("mflash_scissorRight");
            Uniform mflash_scissorLeft = shaderToRender.getUniform("mflash_scissorLeft");

            Vector3f pos1NDC = new Vector3f();
            Vector3f pos2NDC = new Vector3f();

            defaultGunMuzzleflashFX.getMuzzleflashFinal1PersonTransformation()[0].getTranslation(pos1NDC);
            defaultGunMuzzleflashFX.getMuzzleflashFinal1PersonTransformation()[1].getTranslation(pos2NDC);

            Vector4f pos1ScreenSpace = new Vector4f(pos1NDC, 1.0f).mul(RenderSystem.getProjectionMatrix());
            Vector4f pos2ScreenSpace = new Vector4f(pos2NDC, 1.0f).mul(RenderSystem.getProjectionMatrix());

            pos1NDC.set(pos1ScreenSpace.x / pos1ScreenSpace.w, pos1ScreenSpace.y / pos1ScreenSpace.w, pos1ScreenSpace.z / pos1ScreenSpace.w);
            pos2NDC.set(pos2ScreenSpace.x / pos2ScreenSpace.w, pos2ScreenSpace.y / pos2ScreenSpace.w, pos2ScreenSpace.z / pos2ScreenSpace.w);

            if (mflash_posRight != null) {
                mflash_posRight.set(pos2NDC.x * 0.5f + 0.5f, pos2NDC.y * 0.5f + 0.5f, pos2NDC.z);
            }
            if (mflash_posLeft != null) {
                mflash_posLeft.set(pos1NDC.x * 0.5f + 0.5f, pos1NDC.y * 0.5f + 0.5f, pos1NDC.z);
            }

            if (mflash_scissorRight != null) {
                mflash_scissorRight.set(defaultGunMuzzleflashFX.getMuzzleflashScissor1Person()[1]);
            }
            if (mflash_scissorLeft != null) {
                mflash_scissorLeft.set(defaultGunMuzzleflashFX.getMuzzleflashScissor1Person()[0]);
            }

            Objects.requireNonNull(proj).set(orthographic2D);
            Objects.requireNonNull(mod).set(fullMatrix);
        }, List.of(Pair.of("mflash_map", ZPDefaultGunMuzzleflashFX.muzzleflashFBO.getTextureByIndex(1)), Pair.of("texture_map", ZPDefaultGunMuzzleflashFX.muzzleflashFBO.getTextureByIndex(0)), Pair.of("mfash_bloom_map", ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBO.getTextureByIndex(0))));
        GL46.glBlendFuncSeparate(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA, GL46.GL_ONE, GL46.GL_ONE_MINUS_SRC_ALPHA);
    }

    @SuppressWarnings("unchecked")
    public static void postRenderMflash3Person(ZPDefaultGunMuzzleflashFX defaultGunMuzzleflashFX) {
        if (ZPDefaultGunMuzzleflashFX.muzzleflash3dpFBO.getTexturePrograms().isEmpty() || ZPDefaultShaders.image.getShaderInstance() == null) {
            return;
        }
        final Vector2i size = ClientRenderFunctions.getWindowSize();
        final Matrix4f orthographic2D = new Matrix4f().setOrtho2D(0, size.x, size.y, 0);
        final Matrix4f fullMatrix = new Matrix4f().identity().translate(new Vector3f(0, size.y, 0f)).scale(size.x, -size.y, 1.0F);

        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        ClientRenderFunctions.renderTextureIDScreenOverlayFromFBO(Objects.requireNonNull(ZPDefaultShaders.image.getShaderInstance()), (shaderToRender) -> {
            Uniform mod = shaderToRender.getUniform("sModelViewMat");
            Uniform proj = shaderToRender.getUniform("sProjMat");
            Objects.requireNonNull(proj).set(orthographic2D);
            Objects.requireNonNull(mod).set(fullMatrix);
        }, List.of(Pair.of("texture_map", ZPDefaultGunMuzzleflashFX.muzzleflash3dpFBO.getTextureByIndex(0))));
    }

    public static void postRender(ZPDefaultGunMuzzleflashFX defaultMuzzleflashFXUniversal) {
        @NotNull Minecraft minecraft = Minecraft.getInstance();
        boolean flag = minecraft.getCameraEntity() instanceof LivingEntity && ((LivingEntity)minecraft.getCameraEntity()).isSleeping();
        if (minecraft.options.getCameraType().isFirstPerson() && !flag && !minecraft.options.hideGui && Objects.requireNonNull(minecraft.gameMode).getPlayerMode() != GameType.SPECTATOR) {
            ZPGunLayersProcessing.postRenderMflash1Person(defaultMuzzleflashFXUniversal);
        }
        ZPGunLayersProcessing.postRenderMflash3Person(defaultMuzzleflashFXUniversal);
    }
}
