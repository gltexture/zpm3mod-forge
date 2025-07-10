package ru.gltexture.zpm3.engine.client.rendering.gl.programs.textures;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.base.ITexture2DProgram;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.textures.properties.TextureProperties;

import java.nio.ByteBuffer;

public class TextureSimple2DProgram implements ITexture2DProgram {
    private int textureId;
    private Vector2i size;

    public TextureSimple2DProgram() {
        this.textureId = 0;
        this.size = null;
    }

    public void createTexture(Vector2i size, @NotNull TextureProperties properties, @NotNull ByteBuffer pixels) {
        this.size = size;
        this.textureId = GL46.glGenTextures();
        this.bindTexture();
        GL46.glPixelStorei(GL46.GL_UNPACK_ALIGNMENT, 1);
        GL46.glTexImage2D(this.getTextureAttachment(), 0, properties.textureFormat(), size.x, size.y, 0, properties.internalFormat(), GL46.GL_UNSIGNED_BYTE, pixels);
        GL46.glTexParameteri(this.getTextureAttachment(), GL46.GL_TEXTURE_MAG_FILTER, properties.filteringMag());
        GL46.glTexParameteri(this.getTextureAttachment(), GL46.GL_TEXTURE_MIN_FILTER, properties.filteringMin());
        GL46.glTexParameteri(this.getTextureAttachment(), GL46.GL_TEXTURE_COMPARE_MODE, properties.compareMode());
        GL46.glTexParameteri(this.getTextureAttachment(), GL46.GL_TEXTURE_COMPARE_FUNC, properties.compareFunc());
        GL46.glTexParameteri(this.getTextureAttachment(), GL46.GL_TEXTURE_WRAP_S, properties.clampS());
        GL46.glTexParameteri(this.getTextureAttachment(), GL46.GL_TEXTURE_WRAP_T, properties.clampT());
        this.unBindTexture();
    }

    @Override
    public void clear() {
        GL46.glDeleteTextures(this.getTextureId());
        this.textureId = 0;
    }

    public int getTextureId() {
        return this.textureId;
    }

    @Override
    public int getTextureAttachment() {
        return GL46.GL_TEXTURE_2D;
    }

    @Override
    public int getSamplerId() {
        return 0;
    }

    @Override
    public Vector2i getSize() {
        return this.size;
    }
}
