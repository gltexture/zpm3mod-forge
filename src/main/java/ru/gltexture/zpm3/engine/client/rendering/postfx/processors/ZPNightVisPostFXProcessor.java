package ru.gltexture.zpm3.engine.client.rendering.postfx.processors;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.client.rendering.postfx.ZPPostFXChain;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPShaderLoader;
import ru.gltexture.zpm3.modules.debug.imgui.DearUIDebugInterface;

import java.util.Objects;

public class ZPNightVisPostFXProcessor extends ZPPostFXProcessor{
    public ZPNightVisPostFXProcessor(int chainOrder) {
        super(chainOrder);
    }

    @Override
    public void renderTextureInFBO(int screenTexture_GL_ID) {
        ShaderInstance shader = this.getPostFXShader().getShaderInstance();
        Objects.requireNonNull(shader).apply();
        Window window = Minecraft.getInstance().getWindow();
        GL46.glViewport(0, 0, window.getWidth(), window.getHeight());
        {
            GL46.glActiveTexture(GL46.GL_TEXTURE0);
            GL46.glBindTexture(GL46.GL_TEXTURE_2D, screenTexture_GL_ID);
            shader.safeGetUniform("texture_map").set(0);
            shader.safeGetUniform("timer").set(ZPPostFXChain.TIMER);
            ZPRenderHelper.INSTANCE.renderZpScreenMesh();
        }
        Objects.requireNonNull(shader).clear();
    }

    @Override
    protected ZPShaderLoader.ZPShaderInstance getPostFXShader() {
        return ZPDefaultShaders.post_fx_nightvis;
    }

    @Override
    public boolean bypass() {
        return !DearUIDebugInterface.FORCE_ENABLE_NIGHTVIS_POST_FX_SHADER;
    }
}
