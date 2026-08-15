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

package ru.gltexture.zpm3.engine.client.rendering.gl.fbo.attachments;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
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
