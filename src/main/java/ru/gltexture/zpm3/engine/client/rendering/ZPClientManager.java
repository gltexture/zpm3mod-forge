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

package ru.gltexture.zpm3.engine.client.rendering;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.IZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.gl.meshes.ZPScreenMesh;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.client.rendering.imgui.manager.IZPImGuiInterfacesManager;
import ru.gltexture.zpm3.engine.client.rendering.imgui.manager.ZPImGuiInterfacesManager;
import ru.gltexture.zpm3.engine.client.rendering.imgui.renderer.ZPImGuiInterfacesRenderer;
import ru.gltexture.zpm3.engine.client.rendering.postfx.ZPPostFXChain;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.ZP_EventsManager;
import ru.gltexture.zpm3.engine.core.api.events.client.ZPEventBus_ClientResources;
import ru.gltexture.zpm3.modules.armor.events.client.ZPPlayerArmorSoundOnClientEvent;

import java.util.Objects;

public class ZPClientManager implements IZPClientManager, IZPClientManager.ResourceLifecycleListener, IZPClientManager.ResourceReloadListener {
    private final IZPClientCallbacksManager clientCallbacksManager;
    private final ZPPostFXChain postFXChain;
    private ZPScreenMesh screenMesh;
    private @Nullable ZPImGuiInterfacesRenderer imGuiRenderer;
    private @Nullable ZPImGuiInterfacesManager imGuiManager;

    public ZPClientManager() {
        this.clientCallbacksManager = new ZPClientCallbacksManager();
        this.postFXChain = new ZPPostFXChain();
        {
            this.callbackItSelf();
        }
        {
            this.checkAndSetImGui();
        }
    }

    public void renderImGui(float deltaTime) {
        if (!this.isImGuiValid()) {
            return;
        }
        if (!Minecraft.getInstance().isPaused()) {
            Objects.requireNonNull(this.imGuiRenderer).onRender(Minecraft.getInstance().getWindow(), deltaTime);
        }
    }

    private void callbackItSelf() {
        this.registerResourceLifecycleListener(this);
        this.registerResourceReloadListener(this);
    }

    private void checkAndSetImGui() {
        try {
            Class.forName("imgui.ImGui", false, ZPClientManager.class.getClassLoader());
            this.imGuiManager = new ZPImGuiInterfacesManager();
            this.imGuiRenderer = new ZPImGuiInterfacesRenderer(this.clientCallbacksManager, this.imGuiManager);
            ZPLogger.info("IMGUI Ready.");
        } catch (ClassNotFoundException e) {
            ZPLogger.error("Couldn't find IMGUI library in your client! IMGUI Disabled.");
        }
    }

    @Override
    public void onSetupResources(@NotNull Window window) {
        {
            ZPRenderHooksManager.INSTANCE.onSetupResources(window);
        }
        {
            this.getCallbacksManager().addReloadResourcesCallback((w) -> {
                ZPPlayerArmorSoundOnClientEvent.clear();
            });
        }
        this.getPostFXChain().onSetupResources(window);
        if (this.isImGuiValid()) {
            Objects.requireNonNull(this.imGuiRenderer).onSetupResources(window);
        }
        this.screenMesh = new ZPScreenMesh();
        Minecraft.getInstance().getMainRenderTarget().enableStencil();
    }

    @Override
    public void onDestroyResources(@NotNull Window window) {
        {
            ZPRenderHooksManager.INSTANCE.onDestroyResources(window);
        }
        this.getPostFXChain().onDestroyResources(window);
        if (this.isImGuiValid()) {
            Objects.requireNonNull(this.imGuiRenderer).onDestroyResources(window);
        }
        this.screenMesh.clear();
    }

    @Override
    public void onWindowResized(long descriptor, int width, int height) {
        this.getPostFXChain().onWindowResized(descriptor, width, height);
        if (this.isImGuiValid()) {
            Objects.requireNonNull(this.imGuiRenderer).onWindowResized(descriptor, width, height);
        }
    }

    @Override
    public void onReloadResources(@NotNull Window window) {
        this.getPostFXChain().onReloadResources(window);
    }

    public void renderScreenMesh() {
        GL46.glBindVertexArray(this.getScreenMesh().getVao());
        GL46.glEnableVertexAttribArray(0);
        GL46.glEnableVertexAttribArray(1);
        GL46.glDrawElements(GL46.GL_TRIANGLES, 6, GL46.GL_UNSIGNED_INT, 0);
        GL46.glDisableVertexAttribArray(0);
        GL46.glDisableVertexAttribArray(1);
    }

    public void forceReloadAllCallbacks() {
        final Window window = Minecraft.getInstance().getWindow();
        ZP_EventsManager.pushEvent(new ZPEventBus_ClientResources.ReloadGameResourcesEvent(window));
        ((ZPClientCallbacksManager) this.getCallbacksManager()).forceReloadResources(window);
    }

    public boolean isImGuiValid() {
        return this.imGuiRenderer != null;
    }

    @Override
    public @NotNull IZPClientCallbacksManager getCallbacksManager() {
        return this.clientCallbacksManager;
    }

    public @Nullable IZPImGuiInterfacesManager getImGuiInterfacesManager() {
        return this.imGuiManager;
    }

    public @NotNull ZPPostFXChain getPostFXChain() {
        return this.postFXChain;
    }

    public @NotNull ZPScreenMesh getScreenMesh() {
        return this.screenMesh;
    }
}