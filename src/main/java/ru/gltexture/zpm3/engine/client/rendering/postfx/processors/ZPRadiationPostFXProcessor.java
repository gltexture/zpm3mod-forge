package ru.gltexture.zpm3.engine.client.rendering.postfx.processors;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.client.rendering.postfx.ZPPostFXChain;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPShaderLoader;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;
import ru.gltexture.zpm3.modules.debug.imgui.DearUIDebugInterface;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPLivingEntityExt;

import java.util.Objects;

public class ZPRadiationPostFXProcessor extends ZPPostFXProcessor{
    public ZPRadiationPostFXProcessor(int chainOrder) {
        super(chainOrder);
    }

    @Override
    public void renderTextureInFBO(int screenTexture_GL_ID) {
        ShaderInstance shader = this.getPostFXShader().getShaderInstance();
        Objects.requireNonNull(shader).apply();
        Window window = Minecraft.getInstance().getWindow();
        GL46.glViewport(0, 0, window.getWidth(), window.getHeight());
        {
            final float radLevel = ((IZPLivingEntityExt) Objects.requireNonNull(Minecraft.getInstance().player)).zpm3forge$getRadiationLevel();
            GL46.glActiveTexture(GL46.GL_TEXTURE0);
            GL46.glBindTexture(GL46.GL_TEXTURE_2D, screenTexture_GL_ID);
            shader.safeGetUniform("texture_map").set(0);
            shader.safeGetUniform("timer").set(ZPPostFXChain.TIMER);
            shader.safeGetUniform("value").set((DearUIDebugInterface.FORCE_ENABLE_RADIATION_POST_FX_SHADER ? DearUIDebugInterface.PARAM_RAD_POSTFX[0] : radLevel) / 100.0f);
            ZPRenderHelper.INSTANCE.renderZpScreenMesh();
        }
        Objects.requireNonNull(shader).clear();
    }

    @Override
    protected ZPShaderLoader.ZPShaderInstance getPostFXShader() {
        return ZPDefaultShaders.post_fx_radiation;
    }

    @Override
    public boolean bypass() {
        if (Minecraft.getInstance().player != null) {
            if (((IZPLivingEntityExt) Objects.requireNonNull(Minecraft.getInstance().player)).zpm3forge$getRadiationLevel() > 0) {
                return false;
            }
        }
        return !DearUIDebugInterface.FORCE_ENABLE_RADIATION_POST_FX_SHADER;
    }
}
