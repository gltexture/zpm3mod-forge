package ru.gltexture.zpm3.engine.client.rendering;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.crosshair.ZPClientCrosshairRecoilManager;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.meshes.ZPScreenMesh;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.assets.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.client.rendering.ui.imgui.ZPDearUIRenderer;
import ru.gltexture.zpm3.assets.guns.processing.input.ZPClientGunInputProcessing;

public final class ZPRenderHelper implements ZPClientCallbacks.ZPClientResourceDependentObject {
    public static ZPRenderHelper INSTANCE = new ZPRenderHelper();
    private final ZPDearUIRenderer dearUIRenderer;
    private ZPScreenMesh screenMesh;

    private ZPRenderHelper() {
        this.dearUIRenderer = new ZPDearUIRenderer(ZPDefaultShaders.imgui::getShaderInstance);
    }

    public void init() {
        ZPClientCallbacksManager.INSTANCE.addResourceDependentObjectCallback(this);
        ZPClientCallbacksManager.INSTANCE.addResourceDependentObjectCallback(ZPRenderHelper.INSTANCE.getDearUIRenderer());
        ZPClientCallbacksManager.INSTANCE.addResourceDependentObjectCallback(ZPRenderHooksManager.INSTANCE);

        ZPRenderHooksManager.INSTANCE.addSceneRenderingHook(((renderStage, partialTicks, deltaTime, pNanoTime, pRenderLevel) -> {
            if (renderStage == RenderStage.POST) {
                if (!Minecraft.getInstance().isPaused()) {
                    this.getDearUIRenderer().getInterfacesManager().renderAll(Minecraft.getInstance().getWindow(), deltaTime);
                }
            }
        }));

        ZPRenderHooksManager.INSTANCE.addSceneRenderingHook((renderStage, partialTicks, deltaTime, pNanoTime, pRenderLevel) -> {
            if (renderStage == RenderStage.PRE) {
                ZPClientCrosshairRecoilManager.onRenderTick(deltaTime, Minecraft.getInstance());
            }
        });

        ZPClientCallbacksManager.INSTANCE.addClientTickCallback(e -> {
            if (e == TickEvent.Phase.START) {
                ZPClientCrosshairRecoilManager.onClientTick(Minecraft.getInstance());
            }
        });
    }

    public ZPDearUIRenderer getDearUIRenderer() {
        return this.dearUIRenderer;
    }

    @Override
    public void destroyResources(@NotNull Window window) {
        this.screenMesh.clear();
    }

    @Override
    public void setupResources(@NotNull Window window) {
        this.screenMesh = new ZPScreenMesh();
    }

    @Override
    public void onWindowResizeAction(long descriptor, int width, int height) {
    }

    public void renderZpScreenMesh() {
        GL46.glBindVertexArray(this.getScreenMesh().getVao());
        GL46.glEnableVertexAttribArray(0);
        GL46.glEnableVertexAttribArray(1);
        GL46.glDrawElements(GL46.GL_TRIANGLES, 6, GL46.GL_UNSIGNED_INT, 0);
        GL46.glDisableVertexAttribArray(0);
        GL46.glDisableVertexAttribArray(1);
    }

    public ZPScreenMesh getScreenMesh() {
        return this.screenMesh;
    }

    public enum RenderStage {
        PRE,
        POST
    }
}