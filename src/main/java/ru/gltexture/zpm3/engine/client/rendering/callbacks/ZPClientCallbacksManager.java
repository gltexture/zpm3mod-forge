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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.*;
import ru.gltexture.zpm3.engine.core.ZP_EventsManager;
import ru.gltexture.zpm3.engine.core.api.events.client.ZPEventBus_ClientInput;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.modules.guns.processing.logic.ZPDefaultGunLogicFunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public final class ZPClientCallbacksManager implements IZPClientCallbacksManager {
    private final List<ZPClientCallbacks.@NotNull ZPCharCallback> onCharCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPMouseButtonCallback> onMouseButtonCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPMouseScrollCallback> onMouseScrollCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPKeyboardCallback> onKeyboardCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPWindowResizeCallback> onWindowResizeCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPSetupResourcesCallback> onSetupResourcesCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPDestroyResourcesCallback> onDestroyResourcesCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPReloadResourcesCallback> ononReloadResourcess;

    //@Deprecated(forRemoval = true)
    private final List<ZPClientCallbacks.@NotNull ZPClientTickCallback> onClientTickCallbacks;

    //@Deprecated(forRemoval = true)
    private final List<ZPClientCallbacks.@NotNull ZPGunShotCallback> onGunShotCallbacks;

    //@Deprecated(forRemoval = true)
    private final List<ZPClientCallbacks.@NotNull ZPGunReloadStartCallback> onGunReloadStartCallbacks;

    private GLFWWindowSizeCallback windowCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWScrollCallback scrollCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWCharCallback charCallback;

    private GLFWWindowSizeCallback originalWindowCallback;
    private GLFWMouseButtonCallback originalMouseButtonCallback;
    private GLFWScrollCallback originalScrollCallback;
    private GLFWKeyCallback originalKeyCallback;
    private GLFWCharCallback originalCharCallback;

    public ZPClientCallbacksManager() {
        this.onCharCallbacks = new ArrayList<>();
        this.onMouseButtonCallbacks = new ArrayList<>();
        this.onMouseScrollCallbacks = new ArrayList<>();
        this.onKeyboardCallbacks = new ArrayList<>();
        this.onWindowResizeCallbacks = new ArrayList<>();
        this.ononReloadResourcess = new ArrayList<>();
        this.onSetupResourcesCallbacks = new ArrayList<>();
        this.onDestroyResourcesCallbacks = new ArrayList<>();

        this.onGunShotCallbacks = new ArrayList<>();
        this.onGunReloadStartCallbacks = new ArrayList<>();
        this.onClientTickCallbacks = new ArrayList<>();
    }

    public void setup(@NotNull Window window) {
        this.windowCallback = this.setupWindowResizeCallback(window);
        this.mouseButtonCallback = this.setupMouseButtonCallback(window);
        this.scrollCallback = this.setupScrollCallback(window);
        this.keyCallback = this.setupKeyboardCallback(window);
        this.charCallback = this.setupCharCallback(window);
    }

    public void destroy(@NotNull Window window) {
        if (this.windowCallback != null) {
            this.windowCallback.free();
            this.windowCallback = null;
        }

        if (this.mouseButtonCallback != null) {
            this.mouseButtonCallback.free();
            this.mouseButtonCallback = null;
        }

        if (this.scrollCallback != null) {
            this.scrollCallback.free();
            this.scrollCallback = null;
        }

        if (this.keyCallback != null) {
            this.keyCallback.free();
            this.keyCallback = null;
        }

        if (this.charCallback != null) {
            this.charCallback.free();
            this.charCallback = null;
        }

        this.onCharCallbacks.clear();
        this.onMouseButtonCallbacks.clear();
        this.onMouseScrollCallbacks.clear();
        this.onKeyboardCallbacks.clear();
        this.onWindowResizeCallbacks.clear();
        this.ononReloadResourcess.clear();
        this.onSetupResourcesCallbacks.clear();
        this.onDestroyResourcesCallbacks.clear();

        this.onClientTickCallbacks.clear();
        this.onGunShotCallbacks.clear();
        this.onGunReloadStartCallbacks.clear();
    }

    @Override
    public void addCharCallback(@NotNull ZPClientCallbacks.ZPCharCallback callback) {
        this.onCharCallbacks.add(callback);
    }

    @Override
    public void addWindowResizeCallback(@NotNull ZPClientCallbacks.ZPWindowResizeCallback callback) {
        this.onWindowResizeCallbacks.add(callback);
    }

    @Override
    public void addMouseButtonCallback(@NotNull ZPClientCallbacks.ZPMouseButtonCallback callback) {
        this.onMouseButtonCallbacks.add(callback);
    }

    @Override
    public void addMouseScrollCallback(@NotNull ZPClientCallbacks.ZPMouseScrollCallback callback) {
        this.onMouseScrollCallbacks.add(callback);
    }

    @Override
    public void addKeyboardCallback(@NotNull ZPClientCallbacks.ZPKeyboardCallback callback) {
        this.onKeyboardCallbacks.add(callback);
    }

    @Override
    public void addResourcesSetupCallback(@NotNull ZPClientCallbacks.ZPSetupResourcesCallback cb) {
        this.onSetupResourcesCallbacks.add(cb);
    }

    @Override
    public void addResourcesDestroyCallback(@NotNull ZPClientCallbacks.ZPDestroyResourcesCallback cb) {
        this.onDestroyResourcesCallbacks.add(cb);
    }

    @Override
    public void addReloadResourcesCallback(@NotNull ZPClientCallbacks.ZPReloadResourcesCallback cb) {
        this.ononReloadResourcess.add(cb);
    }

    public void forceReloadResources(@NotNull Window w) {
        this.ononReloadResourcess.forEach(e -> e.onReloadResources(w));
    }

    //@Deprecated(forRemoval = true)
    public void tickClientCallbacks(@NotNull TickEvent.Phase phase) {
        this.onClientTickCallbacks.forEach(e -> e.onTick(phase));
    }

    //@Deprecated(forRemoval = true)
    public void triggerGunShots(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, @NotNull ZPDefaultGunLogicFunctions.GunFXData_Shot gunFXData) {
        this.onGunShotCallbacks.forEach(e -> e.onShot(player, baseGun, itemStack, gunFXData));
    }

    //@Deprecated(forRemoval = true)
    public void triggerReloadingStart(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, @NotNull ZPDefaultGunLogicFunctions.GunFXData_Reload gunFXData) {
        this.onGunReloadStartCallbacks.forEach(e -> e.onReloadStart(player, baseGun, itemStack, gunFXData));
    }

    //@Deprecated(forRemoval = true)
    @Override
    public void addClientTickCallback(@NotNull ZPClientCallbacks.ZPClientTickCallback cb) {
        this.onClientTickCallbacks.add(cb);
    }

    //@Deprecated(forRemoval = true)
    @Override
    public void addGunShotCallback(@NotNull ZPClientCallbacks.ZPGunShotCallback cb) {
        this.onGunShotCallbacks.add(cb);
    }

    //@Deprecated(forRemoval = true)
    @Override
    public void addGunReloadStartCallback(@NotNull ZPClientCallbacks.ZPGunReloadStartCallback cb) {
        this.onGunReloadStartCallbacks.add(cb);
    }

    private GLFWWindowSizeCallback setupWindowResizeCallback(@NotNull Window window) {
        final long handle = window.getWindow();
        this.originalWindowCallback = GLFW.glfwSetWindowSizeCallback(handle, null);
        return GLFW.glfwSetWindowSizeCallback(handle, (win, width, height) -> {
            ZP_EventsManager.pushEvent(new ZPEventBus_ClientInput.WindowResizeEvent(win, width, height));
            for (ZPClientCallbacks.ZPWindowResizeCallback callback : this.onWindowResizeCallbacks) {
                callback.onWindowResized(win, width, height);
            }
            if (this.originalWindowCallback != null) {
                this.originalWindowCallback.invoke(win, width, height);
            }
        });
    }

    private GLFWMouseButtonCallback setupMouseButtonCallback(@NotNull Window window) {
        final long handle = window.getWindow();
        this.originalMouseButtonCallback = GLFW.glfwSetMouseButtonCallback(handle, null);
        return GLFW.glfwSetMouseButtonCallback(handle, (win, button, action, mods) -> {
            ZP_EventsManager.pushEvent(new ZPEventBus_ClientInput.MouseButtonEvent(win, button, action, mods));
            for (ZPClientCallbacks.ZPMouseButtonCallback callback : this.onMouseButtonCallbacks) {
                callback.onMouseButtonAction(win, button, action, mods);
            }
            if (this.originalMouseButtonCallback != null) {
                this.originalMouseButtonCallback.invoke(win, button, action, mods);
            }
        });
    }

    private GLFWScrollCallback setupScrollCallback(@NotNull Window window) {
        final long handle = window.getWindow();
        this.originalScrollCallback = GLFW.glfwSetScrollCallback(handle, null);
        return GLFW.glfwSetScrollCallback(handle, (win, xOffset, yOffset) -> {
            ZP_EventsManager.pushEvent(new ZPEventBus_ClientInput.MouseScrollEvent(win, xOffset, yOffset));
            for (ZPClientCallbacks.ZPMouseScrollCallback callback : this.onMouseScrollCallbacks) {
                callback.onMouseScrollAction(win, (int) xOffset, (int) yOffset);
            }
            if (this.originalScrollCallback != null) {
                this.originalScrollCallback.invoke(win, xOffset, yOffset);
            }
        });
    }

    private GLFWKeyCallback setupKeyboardCallback(@NotNull Window window) {
        final long handle = window.getWindow();
        this.originalKeyCallback = GLFW.glfwSetKeyCallback(handle, null);
        return GLFW.glfwSetKeyCallback(handle, (win, key, scanCode, action, mods) -> {
            ZP_EventsManager.pushEvent(new ZPEventBus_ClientInput.KeyboardEvent(win, key, scanCode, action, mods));
            for (ZPClientCallbacks.ZPKeyboardCallback callback : this.onKeyboardCallbacks) {
                callback.onKeyboardAction(win, key, scanCode, action, mods);
            }
            if (this.originalKeyCallback != null) {
                this.originalKeyCallback.invoke(win, key, scanCode, action, mods);
            }
        });
    }

    private GLFWCharCallback setupCharCallback(@NotNull Window window) {
        final long handle = window.getWindow();
        this.originalCharCallback = GLFW.glfwSetCharCallback(handle, null);
        return GLFW.glfwSetCharCallback(handle, (win, codepoint) -> {
            ZP_EventsManager.pushEvent(new ZPEventBus_ClientInput.CharEvent(win, codepoint));
            for (ZPClientCallbacks.ZPCharCallback callback : this.onCharCallbacks) {
                callback.onCharAction(win, codepoint);
            }
            if (this.originalCharCallback != null) {
                this.originalCharCallback.invoke(win, codepoint);
            }
        });
    }

    public @NotNull List<ZPClientCallbacks.ZPCharCallback> getOnCharCallbacks() {
        return Collections.unmodifiableList(this.onCharCallbacks);
    }

    public @NotNull List<ZPClientCallbacks.ZPMouseButtonCallback> getOnMouseButtonCallbacks() {
        return Collections.unmodifiableList(this.onMouseButtonCallbacks);
    }

    public @NotNull List<ZPClientCallbacks.ZPMouseScrollCallback> getOnMouseScrollCallbacks() {
        return Collections.unmodifiableList(this.onMouseScrollCallbacks);
    }

    public @NotNull List<ZPClientCallbacks.ZPKeyboardCallback> getOnKeyboardCallbacks() {
        return Collections.unmodifiableList(this.onKeyboardCallbacks);
    }

    public @NotNull List<ZPClientCallbacks.ZPWindowResizeCallback> getOnWindowResizeCallbacks() {
        return Collections.unmodifiableList(this.onWindowResizeCallbacks);
    }

    public @NotNull List<ZPClientCallbacks.ZPSetupResourcesCallback> getOnSetupResourcesCallbacks() {
        return Collections.unmodifiableList(this.onSetupResourcesCallbacks);
    }

    public @NotNull List<ZPClientCallbacks.ZPDestroyResourcesCallback> getOnDestroyResourcesCallbacks() {
        return Collections.unmodifiableList(this.onDestroyResourcesCallbacks);
    }

    public @NotNull List<ZPClientCallbacks.ZPReloadResourcesCallback> getOnReloadResourcesCallbacks() {
        return Collections.unmodifiableList(this.ononReloadResourcess);
    }
}