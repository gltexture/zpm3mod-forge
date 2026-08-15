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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.IZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.rendering.gl.meshes.ZPScreenMesh;
import ru.gltexture.zpm3.engine.client.rendering.imgui.manager.IZPImGuiInterfacesManager;
import ru.gltexture.zpm3.engine.client.rendering.postfx.IZPPostFXChain;
import ru.gltexture.zpm3.engine.events.client.ZPClientForge;

public interface IZPClientManager {
    @NotNull IZPClientCallbacksManager getCallbacksManager();
    @Nullable IZPImGuiInterfacesManager getImGuiInterfacesManager();
    @NotNull IZPPostFXChain getPostFXChain();
    @NotNull ZPScreenMesh getScreenMesh();
    void renderScreenMesh();
    boolean isImGuiValid();

    static float DELTA_TIME() {
        return ZPClientForge.RENDER_DELTA_TIME;
    }

    default void registerResourceLifecycleListener(@NotNull ResourceLifecycleListener listener) {
        this.getCallbacksManager().addResourcesSetupCallback(listener);
        this.getCallbacksManager().addResourcesDestroyCallback(listener);
        this.getCallbacksManager().addWindowResizeCallback(listener);
    }

    default void registerResourceReloadListener(@NotNull IZPClientManager.ResourceReloadListener listener) {
        this.getCallbacksManager().addReloadResourcesCallback(listener);
    }

    interface ResourceLifecycleListener extends ZPClientCallbacks.ZPDestroyResourcesCallback, ZPClientCallbacks.ZPSetupResourcesCallback, ZPClientCallbacks.ZPWindowResizeCallback {
    }

    interface ResourceReloadListener extends ZPClientCallbacks.ZPReloadResourcesCallback {
    }
}
