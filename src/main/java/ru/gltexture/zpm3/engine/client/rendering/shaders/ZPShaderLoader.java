package ru.gltexture.zpm3.engine.client.rendering.shaders;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.HashSet;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public abstract class ZPShaderLoader {
    static final Set<Pair<ZPShaderInstance, ShaderData>> shaderDataSet = new HashSet<>();

    public static void createNewShader(@NotNull ShaderData shaderData, @NotNull ZPShaderInstance shaderInstance) {
        ZPShaderLoader.shaderDataSet.add(new Pair<>(shaderInstance, shaderData));
    }

    static void setShaderInstance(@NotNull ZPShaderInstance zpShaderInstance, @NotNull ShaderInstance shaderInstance) {
        zpShaderInstance.setShaderInstance(shaderInstance);
    }

    public record ShaderData(@NotNull ResourceLocation resourceLocation, @NotNull VertexFormat vertexFormat) { ; }

    public static class ZPShaderInstance {
        private ShaderInstance shaderInstance;

        public ZPShaderInstance() {
            this.shaderInstance = null;
        }

        public @Nullable ShaderInstance getShaderInstance() {
            return this.shaderInstance;
        }

        private void setShaderInstance(ShaderInstance shaderInstance) {
            this.shaderInstance = shaderInstance;
        }
    }
}
