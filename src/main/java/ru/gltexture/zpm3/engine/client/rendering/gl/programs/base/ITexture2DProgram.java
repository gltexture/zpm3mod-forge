package ru.gltexture.zpm3.engine.client.rendering.gl.programs.base;

import org.joml.Vector2i;

public interface ITexture2DProgram extends ITextureProgram {
    Vector2i getSize();
}