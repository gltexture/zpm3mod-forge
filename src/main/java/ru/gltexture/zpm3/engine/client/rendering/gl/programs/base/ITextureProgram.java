package ru.gltexture.zpm3.engine.client.rendering.gl.programs.base;

import org.lwjgl.opengl.GL46;

public interface ITextureProgram {
    default void bindSampler(int unit) {
        GL46.glBindSampler(unit, this.getSamplerId());
    }

    default void unBindSampler(int unit) {
        GL46.glBindSampler(unit, 0);
    }

    default void bindTexture() {
        GL46.glBindTexture(this.getTextureAttachment(), this.getTextureId());
    }

    default void unBindTexture() {
        GL46.glBindTexture(this.getTextureAttachment(), 0);
    }

    int getSamplerId();
    int getTextureId();
    int getTextureAttachment();

    default boolean isSamplerValid() {
        return this.getSamplerId() > 0;
    }

    default boolean isValid() {
        return this.getTextureId() > 0;
    }

    void clear();
}
