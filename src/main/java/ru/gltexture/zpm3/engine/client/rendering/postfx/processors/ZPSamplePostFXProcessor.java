package ru.gltexture.zpm3.engine.client.rendering.postfx.processors;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPShaderLoader;
import ru.gltexture.zpm3.modules.debug.imgui.DearUIDebugInterface;

import java.util.Objects;

public class ZPSamplePostFXProcessor extends ZPPostFXProcessor{
    public ZPSamplePostFXProcessor(int chainOrder) {
        super(chainOrder);
    }

    @Override
    public void renderTextureInFBO(int screenTexture_GL_ID) {
        ShaderInstance shader = this.getPostFXShader().getShaderInstance();
        Objects.requireNonNull(shader).apply();
        Window window = Minecraft.getInstance().getWindow();
        GL46.glViewport(0, 0, window.getWidth(), window.getHeight());
        GL46.glActiveTexture(GL46.GL_TEXTURE0);
        ZPRenderHelper.INSTANCE.renderZpScreenMesh();
        Objects.requireNonNull(shader).clear();
    }

    @Override
    protected ZPShaderLoader.ZPShaderInstance getPostFXShader() {
        return ZPDefaultShaders.post_fx_sample;
    }

    @Override
    public boolean bypass() {
        return !DearUIDebugInterface.FORCE_ENABLE_SAMPLE_POST_FX_SHADER;
    }
}
