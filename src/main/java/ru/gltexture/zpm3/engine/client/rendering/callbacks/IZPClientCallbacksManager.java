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

package ru.gltexture.zpm3.engine.client.rendering.callbacks;

import com.mojang.blaze3d.platform.Window;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public interface IZPClientCallbacksManager {
    void addKeyboardCallback(@NotNull ZPClientCallbacks.ZPKeyboardCallback callback);
    void addMouseButtonCallback(@NotNull ZPClientCallbacks.ZPMouseButtonCallback callback);
    void addCharCallback(@NotNull ZPClientCallbacks.ZPCharCallback callback);
    void addWindowResizeCallback(@NotNull ZPClientCallbacks.ZPWindowResizeCallback callback);
    void addMouseScrollCallback(@NotNull ZPClientCallbacks.ZPMouseScrollCallback callback);

    void addResourcesSetupCallback(@NotNull ZPClientCallbacks.ZPSetupResourcesCallback cb);
    void addResourcesDestroyCallback(@NotNull ZPClientCallbacks.ZPDestroyResourcesCallback cb);
    void addReloadResourcesCallback(@NotNull ZPClientCallbacks.ZPReloadResourcesCallback cb);


    //@Deprecated(forRemoval = true)
    void addClientTickCallback(@NotNull ZPClientCallbacks.ZPClientTickCallback cb);

    //@Deprecated(forRemoval = true)
    void addGunShotCallback(@NotNull ZPClientCallbacks.ZPGunShotCallback cb);

    //@Deprecated(forRemoval = true)
    void addGunReloadStartCallback(@NotNull ZPClientCallbacks.ZPGunReloadStartCallback cb);

    void setup(@NotNull Window window);
    void destroy(@NotNull Window window);

    @Unmodifiable @NotNull List<ZPClientCallbacks.ZPCharCallback> getOnCharCallbacks();
    @Unmodifiable @NotNull List<ZPClientCallbacks.ZPMouseButtonCallback> getOnMouseButtonCallbacks();
    @Unmodifiable @NotNull List<ZPClientCallbacks.ZPMouseScrollCallback> getOnMouseScrollCallbacks();
    @Unmodifiable @NotNull List<ZPClientCallbacks.ZPKeyboardCallback> getOnKeyboardCallbacks();
    @Unmodifiable @NotNull List<ZPClientCallbacks.ZPWindowResizeCallback> getOnWindowResizeCallbacks();
    @Unmodifiable @NotNull List<ZPClientCallbacks.ZPSetupResourcesCallback> getOnSetupResourcesCallbacks();
    @Unmodifiable @NotNull List<ZPClientCallbacks.ZPDestroyResourcesCallback> getOnDestroyResourcesCallbacks();
    @Unmodifiable @NotNull List<ZPClientCallbacks.ZPReloadResourcesCallback> getOnReloadResourcesCallbacks();
}