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

package ru.gltexture.zpm3.engine.client.rendering.gl.textures;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.rendering.gl.base.ITexture2DProgram;
import ru.gltexture.zpm3.engine.client.rendering.gl.textures.properties.TextureProperties;

import java.nio.FloatBuffer;

@OnlyIn(Dist.CLIENT)
public class Texture2DProgram implements ITexture2DProgram {
    private int textureId;
    private int samplerId;
    private Vector2i size;

    public Texture2DProgram() {
        this.size = null;
        this.textureId = 0;
        this.samplerId = 0;
    }

    public void createTexture(Vector2i size, @NotNull TextureProperties properties, FloatBuffer pixels) {
        this.textureId = GL46.glGenTextures();
        this.bindTexture();
        this.size = size;
        GL46.glTexImage2D(this.getTextureAttachment(), 0, properties.textureFormat(), this.getSize().x, this.getSize().y, 0, properties.internalFormat(), GL46.GL_FLOAT, pixels);
        this.unBindTexture();
        this.createSampler(properties);
    }

    protected void createSampler(@NotNull TextureProperties properties) {
        if (this.getSamplerId() != 0) {
            GL46.glDeleteSamplers(this.getSamplerId());
        }
        this.samplerId = GL46.glGenSamplers();
        GL46.glSamplerParameteri(this.getSamplerId(), GL46.GL_TEXTURE_MAG_FILTER, properties.filteringMag());
        GL46.glSamplerParameteri(this.getSamplerId(), GL46.GL_TEXTURE_MIN_FILTER, properties.filteringMin());
        GL46.glSamplerParameteri(this.getSamplerId(), GL46.GL_TEXTURE_COMPARE_MODE, properties.compareMode());
        GL46.glSamplerParameteri(this.getSamplerId(), GL46.GL_TEXTURE_COMPARE_FUNC, properties.compareFunc());
        GL46.glSamplerParameteri(this.getSamplerId(), GL46.GL_TEXTURE_WRAP_S, properties.clampS());
        GL46.glSamplerParameteri(this.getSamplerId(), GL46.GL_TEXTURE_WRAP_T, properties.clampT());

        if (properties.borderColor() != null) {
            GL46.glSamplerParameterfv(this.getSamplerId(), GL46.GL_TEXTURE_BORDER_COLOR, properties.borderColor());
        }
    }

    @Override
    public void clear() {
        this.size = null;
        GL46.glDeleteTextures(this.getTextureId());
        GL46.glDeleteSamplers(this.getSamplerId());
        this.textureId = 0;
        this.samplerId = 0;
    }

    public Vector2i getSize() {
        return this.size;
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
        return this.samplerId;
    }
}
