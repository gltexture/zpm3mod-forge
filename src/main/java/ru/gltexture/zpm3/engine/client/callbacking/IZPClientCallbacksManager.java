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

package ru.gltexture.zpm3.engine.client.callbacking;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public interface IZPClientCallbacksManager extends ZPClientCallbacks.ZPSetupResourcesCallback, ZPClientCallbacks.ZPDestroyResourcesCallback {
    void addCharCallback(@NotNull ZPClientCallbacks.ZPCharCallback cb);

    void addWindowResizeCallback(@NotNull ZPClientCallbacks.ZPWindowResizeCallback cb);

    void addMouseClickCallback(@NotNull ZPClientCallbacks.ZPMouseClickCallback cb);

    void addMouseHoldCallback(@NotNull ZPClientCallbacks.ZPMouseHoldCallback cb);

    void addMouseReleaseCallback(@NotNull ZPClientCallbacks.ZPMouseReleaseCallback cb);

    void addMouseScrollCallback(@NotNull ZPClientCallbacks.ZPMouseScrollCallback cb);

    void addKeyboardClickCallback(@NotNull ZPClientCallbacks.ZPKeyboardClickCallback cb);

    void addKeyboardHoldCallback(@NotNull ZPClientCallbacks.ZPKeyboardHoldCallback cb);

    void addKeyboardReleaseCallback(@NotNull ZPClientCallbacks.ZPKeyboardReleaseCallback cb);

    void addResourcesSetupCallback(@NotNull ZPClientCallbacks.ZPSetupResourcesCallback cb);

    void addResourceDependentObjectCallback(@NotNull ZPClientCallbacks.ZPClientResourceDependentObject cb);

    void addResourcesDestroyCallback(@NotNull ZPClientCallbacks.ZPDestroyResourcesCallback cb);

    void addClientTickCallback(@NotNull ZPClientCallbacks.ZPClientTickCallback cb);

    void addGunShotCallback(@NotNull ZPClientCallbacks.ZPGunShotCallback cb);

    void addGunReloadStartCallback(@NotNull ZPClientCallbacks.ZPGunReloadStartCallback cb);

    void addReloadGameResourcesCallback(@NotNull ZPClientCallbacks.ZPReloadGameResourcesCallback cb);
}
