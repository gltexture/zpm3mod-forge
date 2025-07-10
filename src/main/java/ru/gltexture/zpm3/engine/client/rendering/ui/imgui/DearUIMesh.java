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
