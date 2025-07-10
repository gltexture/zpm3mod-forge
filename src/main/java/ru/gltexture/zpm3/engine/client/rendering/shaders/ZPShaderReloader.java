package ru.gltexture.zpm3.engine.client.rendering.shaders;

import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
public class ZPShaderReloader extends SimplePreparableReloadListener<Void> {
    @Override
    protected @NotNull Void prepare(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
        return null;
    }

    @Override
    protected void apply(@NotNull Void unused, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
        ZPShaderLoader.shaderDataSet.forEach(e -> {
            try {
                ZPShaderLoader.setShaderInstance(e.first(), new ShaderInstance(resourceManager, e.second().resourceLocation(), e.second().vertexFormat()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}