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
