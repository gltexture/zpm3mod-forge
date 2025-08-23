package ru.gltexture.zpm3.engine.client.callbacking;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.*;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public final class ZPClientCallbacksManager implements IZPClientCallbacksManager {
    public static final ZPClientCallbacksManager INSTANCE = new ZPClientCallbacksManager();

    private final List<ZPClientCallbacks.@NotNull ZPCharCallback> onCharCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPWindowResizeCallback> onResizeCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPMouseClickCallback> onMouseClickCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPMouseHoldCallback> onMouseHoldCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPMouseReleaseCallback> onMouseReleaseCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPMouseScrollCallback> onMouseScrollCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPKeyboardClickCallback> onKeyClickCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPKeyboardHoldCallback> onKeyHoldCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPKeyboardReleaseCallback> onKeyReleaseCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPSetupResourcesCallback> onSetupResourcesCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPDestroyResourcesCallback> onDestroyResourcesCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPClientTickCallback> onClientTickCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPGunShotCallback> onGunShotCallbacks;
    private final List<ZPClientCallbacks.@NotNull ZPGunReloadStartCallback> onGunReloadStartCallbacks;

    private GLFWWindowSizeCallback windowCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWScrollCallback scrollCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWCharCallback charCallBack;

    private GLFWWindowSizeCallback originalWindowCallback;
    private GLFWMouseButtonCallback originalMouseButtonCallback;
    private GLFWScrollCallback originalScrollCallback;
    private GLFWKeyCallback originalKeyCallback;
    private GLFWCharCallback originalCharCallback;

    private ZPClientCallbacksManager() {
        this.onResizeCallbacks = new ArrayList<>();
        this.onMouseClickCallbacks = new ArrayList<>();
        this.onMouseHoldCallbacks = new ArrayList<>();
        this.onMouseReleaseCallbacks = new ArrayList<>();
        this.onMouseScrollCallbacks = new ArrayList<>();
        this.onKeyClickCallbacks = new ArrayList<>();
        this.onKeyHoldCallbacks = new ArrayList<>();
        this.onKeyReleaseCallbacks = new ArrayList<>();
        this.onCharCallbacks = new ArrayList<>();
        this.onGunShotCallbacks = new ArrayList<>();
        this.onGunReloadStartCallbacks = new ArrayList<>();

        this.onSetupResourcesCallbacks = new ArrayList<>();
        this.onDestroyResourcesCallbacks = new ArrayList<>();
        this.onClientTickCallbacks = new ArrayList<>();
    }

    @Override
    public void setupResources(@NotNull Window window) {
        this.windowCallback = this.setupWindowResizeCallback(window);
        this.mouseButtonCallback = this.setupMouseButtonCallback(window);
        this.scrollCallback = this.setupScrollCallback(window);
        this.keyCallback = this.setupKeyboardCallback(window);
        this.charCallBack = this.setupCharCallback(window);
    }

    @Override
    public void destroyResources(@NotNull Window window) {
        if (this.windowCallback != null) {
            this.windowCallback.free();
        }

        if (this.mouseButtonCallback != null) {
            this.mouseButtonCallback.free();
        }

        if (this.scrollCallback != null) {
            this.scrollCallback.free();
        }

        if (this.keyCallback != null) {
            this.keyCallback.free();
        }

        if (this.charCallBack != null) {
            this.charCallBack.free();
        }

        this.onCharCallbacks.clear();
        this.onResizeCallbacks.clear();
        this.onMouseClickCallbacks.clear();
        this.onMouseHoldCallbacks.clear();
        this.onMouseReleaseCallbacks.clear();
        this.onMouseScrollCallbacks.clear();
        this.onKeyClickCallbacks.clear();
        this.onKeyHoldCallbacks.clear();
        this.onKeyReleaseCallbacks.clear();
        this.onSetupResourcesCallbacks.clear();
        this.onDestroyResourcesCallbacks.clear();
        this.onClientTickCallbacks.clear();
        this.onGunShotCallbacks.clear();
        this.onGunReloadStartCallbacks.clear();
    }

    @Override
    public void addCharCallback(@NotNull ZPClientCallbacks.ZPCharCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onCharCallbacks.add(cb);
    }

    @Override
    public void addWindowResizeCallback(@NotNull ZPClientCallbacks.ZPWindowResizeCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onResizeCallbacks.add(cb);
    }

    @Override
    public void addMouseClickCallback(@NotNull ZPClientCallbacks.ZPMouseClickCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onMouseClickCallbacks.add(cb);
    }

    @Override
    public void addMouseHoldCallback(@NotNull ZPClientCallbacks.ZPMouseHoldCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onMouseHoldCallbacks.add(cb);
    }

    @Override
    public void addMouseReleaseCallback(@NotNull ZPClientCallbacks.ZPMouseReleaseCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onMouseReleaseCallbacks.add(cb);
    }

    @Override
    public void addMouseScrollCallback(@NotNull ZPClientCallbacks.ZPMouseScrollCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onMouseScrollCallbacks.add(cb);
    }

    @Override
    public void addKeyboardClickCallback(@NotNull ZPClientCallbacks.ZPKeyboardClickCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onKeyClickCallbacks.add(cb);
    }

    @Override
    public void addKeyboardHoldCallback(@NotNull ZPClientCallbacks.ZPKeyboardHoldCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onKeyHoldCallbacks.add(cb);
    }

    @Override
    public void addKeyboardReleaseCallback(@NotNull ZPClientCallbacks.ZPKeyboardReleaseCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onKeyReleaseCallbacks.add(cb);
    }

    @Override
    public void addResourcesSetupCallback(@NotNull ZPClientCallbacks.ZPSetupResourcesCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onSetupResourcesCallbacks.add(cb);
    }

    @Override
    public void addResourceDependentObjectCallback(@NotNull ZPClientCallbacks.ZPClientResourceDependentObject cb) {
        ZombiePlague3.clientInitValidation();
        this.onSetupResourcesCallbacks.add(cb);
        this.onDestroyResourcesCallbacks.add(cb);
        this.onResizeCallbacks.add(cb);
    }

    @Override
    public void addResourcesDestroyCallback(@NotNull ZPClientCallbacks.ZPDestroyResourcesCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onDestroyResourcesCallbacks.add(cb);
    }

    @Override
    public void addClientTickCallback(@NotNull ZPClientCallbacks.ZPClientTickCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onClientTickCallbacks.add(cb);
    }

    @Override
    public void addGunShotCallback(@NotNull ZPClientCallbacks.ZPGunShotCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onGunShotCallbacks.add(cb);
    }

    @Override
    public void addGunReloadStartCallback(@NotNull ZPClientCallbacks.ZPGunReloadStartCallback cb) {
        ZombiePlague3.clientInitValidation();
        this.onGunReloadStartCallbacks.add(cb);
    }


    public void tickClientCallbacks(@NotNull TickEvent.Phase phase) {
        this.onClientTickCallbacks.forEach(e -> e.onTick(phase));
    }

    public void triggerGunShots(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, @NotNull ZPClientCallbacks.ZPGunShotCallback.GunFXData gunFXData) {
        this.onGunShotCallbacks.forEach(e -> e.onShot(player, baseGun, itemStack, gunFXData));
    }

    public void triggerReloadingStart(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, @NotNull ZPClientCallbacks.ZPGunReloadStartCallback.GunFXData gunFXData) {
        this.onGunReloadStartCallbacks.forEach(e -> e.onReloadStart(player, baseGun, itemStack, gunFXData));
    }

    private GLFWCharCallback setupCharCallback(@NotNull Window window) {
        long handle = window.getWindow();
        this.originalCharCallback = GLFW.glfwSetCharCallback(handle, null);

        return GLFW.glfwSetCharCallback(handle, (win, c) -> {
            for (ZPClientCallbacks.ZPCharCallback cb : this.onCharCallbacks) {
                cb.onCharAction(win, c);
            }

            if (this.originalCharCallback != null) {
                this.originalCharCallback.invoke(win, c);
            }
        });
    }

    private GLFWWindowSizeCallback setupWindowResizeCallback(@NotNull Window window) {
        long handle = window.getWindow();
        this.originalWindowCallback = GLFW.glfwSetWindowSizeCallback(handle, null);

        return GLFW.glfwSetWindowSizeCallback(handle, (win, width, height) -> {
            for (ZPClientCallbacks.ZPWindowResizeCallback cb : this.onResizeCallbacks) {
                cb.onWindowResizeAction(win, width, height);
            }

            if (this.originalWindowCallback != null) {
                this.originalWindowCallback.invoke(win, width, height);
            }
        });
    }

    private GLFWMouseButtonCallback setupMouseButtonCallback(@NotNull Window window) {
        long handle = window.getWindow();
        this.originalMouseButtonCallback = GLFW.glfwSetMouseButtonCallback(handle, null);

        return GLFW.glfwSetMouseButtonCallback(handle, (win, button, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                for (ZPClientCallbacks.ZPMouseClickCallback cb : this.onMouseClickCallbacks) {
                    cb.onMouseClickAction(win, button);
                }
            } else if (action == GLFW.GLFW_REPEAT) {
                for (ZPClientCallbacks.ZPMouseHoldCallback cb : this.onMouseHoldCallbacks) {
                    cb.onMouseHoldAction(win, button);
                }
            } else if (action == GLFW.GLFW_RELEASE) {
                for (ZPClientCallbacks.ZPMouseReleaseCallback cb : this.onMouseReleaseCallbacks) {
                    cb.onMouseReleaseAction(win, button);
                }
            }

            if (this.originalMouseButtonCallback != null) {
                this.originalMouseButtonCallback.invoke(win, button, action, mods);
            }
        });
    }

    private GLFWScrollCallback setupScrollCallback(@NotNull Window window) {
        long handle = window.getWindow();
        this.originalScrollCallback = GLFW.glfwSetScrollCallback(handle, null);

        return GLFW.glfwSetScrollCallback(handle, (win, xOffset, yOffset) -> {
            for (ZPClientCallbacks.ZPMouseScrollCallback cb : this.onMouseScrollCallbacks) {
                cb.onMouseScrollAction(win, (int) xOffset, (int) yOffset);
            }

            if (this.originalScrollCallback != null) {
                this.originalScrollCallback.invoke(win, xOffset, yOffset);
            }
        });
    }

    private GLFWKeyCallback setupKeyboardCallback(@NotNull Window window) {
        long handle = window.getWindow();
        this.originalKeyCallback = GLFW.glfwSetKeyCallback(handle, null);

        return GLFW.glfwSetKeyCallback(handle, (win, key, scanCode, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                for (ZPClientCallbacks.ZPKeyboardClickCallback cb : this.onKeyClickCallbacks) {
                    cb.onKeyboardClickAction(win, key, scanCode, mods);
                }
            } else if (action == GLFW.GLFW_REPEAT) {
                for (ZPClientCallbacks.ZPKeyboardHoldCallback cb : this.onKeyHoldCallbacks) {
                    cb.onKeyBoardHoldAction(win, key, scanCode, mods);
                }
            } else if (action == GLFW.GLFW_RELEASE) {
                for (ZPClientCallbacks.ZPKeyboardReleaseCallback cb : this.onKeyReleaseCallbacks) {
                    cb.onKeyboardReleaseAction(win, key, scanCode, mods);
                }
            }

            if (this.originalKeyCallback != null) {
                this.originalKeyCallback.invoke(win, key, scanCode, action, mods);
            }
        });
    }

    public List<ZPClientCallbacks.@NotNull ZPCharCallback> getOnCharCallbacks() {
        return this.onCharCallbacks;
    }

    public List<ZPClientCallbacks.@NotNull ZPWindowResizeCallback> getOnResizeCallbacks() {
        return this.onResizeCallbacks;
    }

    public List<ZPClientCallbacks.@NotNull ZPMouseClickCallback> getOnMouseClickCallbacks() {
        return this.onMouseClickCallbacks;
    }

    public List<ZPClientCallbacks.@NotNull ZPMouseHoldCallback> getOnMouseHoldCallbacks() {
        return this.onMouseHoldCallbacks;
    }

    public List<ZPClientCallbacks.@NotNull ZPMouseReleaseCallback> getOnMouseReleaseCallbacks() {
        return this.onMouseReleaseCallbacks;
    }

    public List<ZPClientCallbacks.@NotNull ZPMouseScrollCallback> getOnMouseScrollCallbacks() {
        return this.onMouseScrollCallbacks;
    }

    public List<ZPClientCallbacks.@NotNull ZPKeyboardClickCallback> getOnKeyClickCallbacks() {
        return this.onKeyClickCallbacks;
    }

    public List<ZPClientCallbacks.@NotNull ZPKeyboardHoldCallback> getOnKeyHoldCallbacks() {
        return this.onKeyHoldCallbacks;
    }

    public List<ZPClientCallbacks.@NotNull ZPKeyboardReleaseCallback> getOnKeyReleaseCallbacks() {
        return this.onKeyReleaseCallbacks;
    }

    public List<ZPClientCallbacks.@NotNull ZPSetupResourcesCallback> getOnSetupResourcesCallbacks() {
        return this.onSetupResourcesCallbacks;
    }

    public List<ZPClientCallbacks.@NotNull ZPDestroyResourcesCallback> getOnDestroyResourcesCallbacks() {
        return this.onDestroyResourcesCallbacks;
    }

    public List<ZPClientCallbacks.@NotNull ZPClientTickCallback> getOnClientTickCallbacks() {
        return this.onClientTickCallbacks;
    }
}