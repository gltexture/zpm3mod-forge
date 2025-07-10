package ru.gltexture.zpm3.engine.client.rendering.gl.programs.fbo.attachments;

import java.util.ArrayList;
import java.util.List;

public class T2DAttachmentContainer {
    private final List<T2DAttachment> t2DAttachmentSet;

    public T2DAttachmentContainer(T2DAttachment attachment) {
        this();
        this.add(attachment);
    }

    public T2DAttachmentContainer(int attachment, int textureFormat, int internalFormat) {
        this();
        this.add(attachment, textureFormat, internalFormat);
    }

    public T2DAttachmentContainer() {
        this.t2DAttachmentSet = new ArrayList<>();
    }

    public void add(int attachment, int textureFormat, int internalFormat) {
        this.getT2DAttachmentSet().add(T2DAttachment.create(attachment, textureFormat, internalFormat));
    }

    public void add(T2DAttachment attachment) {
        this.getT2DAttachmentSet().add(attachment);
    }

    public List<T2DAttachment> getT2DAttachmentSet() {
        return this.t2DAttachmentSet;
    }
}
