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
