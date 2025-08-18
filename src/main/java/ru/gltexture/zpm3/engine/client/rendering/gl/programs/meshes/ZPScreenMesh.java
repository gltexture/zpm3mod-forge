package ru.gltexture.zpm3.engine.client.rendering.gl.programs.meshes;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL46;

@OnlyIn(Dist.CLIENT)
public class ZPScreenMesh {
    private final int vao;
    private final int indicesEbo;
    private final int positionsVbo;
    private final int textureUvVbo;

    private static final int[] indices = {
            0, 1, 2,
            2, 3, 0
    };

    private static final float[] positions = {
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f
    };

    private static final float[] uvs = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f
    };

    public ZPScreenMesh() {
        this.vao = GL46.glGenVertexArrays();
        this.indicesEbo = GL46.glGenBuffers();
        this.positionsVbo = GL46.glGenBuffers();
        this.textureUvVbo = GL46.glGenBuffers();

        GL46.glBindVertexArray(this.vao);

        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, this.indicesEbo);
        GL46.glBufferData(GL46.GL_ELEMENT_ARRAY_BUFFER, ZPScreenMesh.indices, GL46.GL_STATIC_DRAW);

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, this.positionsVbo);
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, ZPScreenMesh.positions, GL46.GL_STATIC_DRAW);
        GL46.glEnableVertexAttribArray(0);
        GL46.glVertexAttribPointer(0, 3, GL46.GL_FLOAT, false, 0, 0);

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, this.textureUvVbo);
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, ZPScreenMesh.uvs, GL46.GL_STATIC_DRAW);
        GL46.glEnableVertexAttribArray(1);
        GL46.glVertexAttribPointer(1, 2, GL46.GL_FLOAT, false, 0, 0);

        GL46.glBindVertexArray(0);
    }

    public int getVao() {
        return this.vao;
    }

    public void clear() {
        GL46.glDeleteBuffers(this.indicesEbo);
        GL46.glDeleteBuffers(this.positionsVbo);
        GL46.glDeleteBuffers(this.textureUvVbo);
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);
        GL46.glBindVertexArray(0);
        GL46.glDeleteVertexArrays(this.vao);
    }
}
