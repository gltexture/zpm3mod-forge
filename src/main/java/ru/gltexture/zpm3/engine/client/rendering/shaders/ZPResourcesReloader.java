package ru.gltexture.zpm3.engine.client.rendering.shaders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@OnlyIn(Dist.CLIENT)
public class ZPResourcesReloader implements PreparableReloadListener {
    @Override
    public @NotNull CompletableFuture<Void> reload(@NotNull PreparationBarrier pPreparationBarrier, @NotNull ResourceManager pResourceManager, @NotNull ProfilerFiller pPreparationsProfiler, @NotNull ProfilerFiller pReloadProfiler, @NotNull Executor pBackgroundExecutor, @NotNull Executor pGameExecutor) {
        CompletableFuture<Void> prepare = CompletableFuture.runAsync(() -> {
            // ....
        }, pBackgroundExecutor);
        return prepare.thenCompose(pPreparationBarrier::wait).thenRunAsync(() -> {
            ZPClientCallbacksManager.reloadResources(Minecraft.getInstance().getWindow());
        }, pGameExecutor);
    }
}