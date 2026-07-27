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

package ru.gltexture.zpm3.engine.client.rendering.ui.imgui;

import imgui.ImDrawData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL46;

@OnlyIn(Dist.CLIENT)
public class DearUIMesh {
    private final int indicesVbo;
    private final int vaoId;
    private final int verticesVbo;

    public DearUIMesh() {
        this.vaoId = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(this.vaoId);

        this.verticesVbo = GL46.glGenBuffers();
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, this.verticesVbo);

        GL46.glEnableVertexAttribArray(0);
        GL46.glVertexAttribPointer(0, 2, GL46.GL_FLOAT, false, ImDrawData.SIZEOF_IM_DRAW_VERT, 0);

        GL46.glEnableVertexAttribArray(1);
        GL46.glVertexAttribPointer(1, 2, GL46.GL_FLOAT, false, ImDrawData.SIZEOF_IM_DRAW_VERT, 8);

        GL46.glEnableVertexAttribArray(2);
        GL46.glVertexAttribPointer(2, 4, GL46.GL_UNSIGNED_BYTE, true, ImDrawData.SIZEOF_IM_DRAW_VERT, 16);

        this.indicesVbo = GL46.glGenBuffers();

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);
        GL46.glBindVertexArray(0);
    }

    public void clear() {
        GL46.glDeleteBuffers(this.indicesVbo);
        GL46.glDeleteBuffers(this.verticesVbo);
        GL46.glDeleteVertexArrays(this.vaoId);
    }

    public int getIndicesVbo() {
        return this.indicesVbo;
    }

    public int getVaoId() {
        return this.vaoId;
    }

    public int getVerticesVbo() {
        return this.verticesVbo;
    }
}
