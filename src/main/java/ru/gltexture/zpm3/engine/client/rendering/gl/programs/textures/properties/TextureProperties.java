package ru.gltexture.zpm3.engine.client.rendering.gl.programs.textures.properties;

public record TextureProperties(int textureFormat, int internalFormat, int filteringMag, int filteringMin, int compareMode, int compareFunc, int clampS, int clampT, float[] borderColor) implements ITextureProperties {
}