package ru.gltexture.zpm3.engine.client.rendering.gl.programs.fbo.attachments;

public final class T2DAttachment {
    private final int attachment;
    private final int textureFormat;
    private final int internalFormat;

    private T2DAttachment(int attachment, int textureFormat, int internalFormat) {
        this.attachment = attachment;
        this.textureFormat = textureFormat;
        this.internalFormat = internalFormat;
    }

    public static T2DAttachment create(int attachment, int textureFormat, int internalFormat) {
        return new T2DAttachment(attachment, textureFormat, internalFormat);
    }

    public int getAttachment() {
        return this.attachment;
    }

    public int getTextureFormat() {
        return this.textureFormat;
    }

    public int getInternalFormat() {
        return this.internalFormat;
    }
}
