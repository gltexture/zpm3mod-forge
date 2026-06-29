package ru.gltexture.zpm3.engine.client.rendering.postfx.processors;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPShaderLoader;

@OnlyIn(Dist.CLIENT)
public abstract class ZPPostFXProcessor {
    private final int chainOrder;

    public ZPPostFXProcessor(int chainOrder) {
        this.chainOrder = chainOrder;
    }

    public abstract void renderTextureInFBO(int screenTexture_GL_ID);

    protected abstract ZPShaderLoader.ZPShaderInstance getPostFXShader();

    public abstract boolean bypass();

    public int getChainOrder() {
        return this.chainOrder;
    }
}
