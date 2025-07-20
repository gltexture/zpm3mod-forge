package ru.gltexture.zpm3.engine.client.callbacking;

import com.mojang.blaze3d.platform.Window;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.*;
import ru.gltexture.zpm3.engine.client.rendering.resources.IZPResourceInit;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ZPCallbacksManager implements IZPResourceInit {
    public static final ZPCallbacksManager INSTANCE = new ZPCallbacksManager();

    private final List<ZPCallbacks.ZPCharCallback> onChar;
    private final List<ZPCallbacks.ZPWindowResizeCallback> onResize;
    private final List<ZPCallbacks.ZPMouseClickCallback> onMouseClick;
    private final List<ZPCallbacks.ZPMouseHoldCallback> onMouseHold;
    private final List<ZPCallbacks.ZPMouseReleaseCallback> onMouseRelease;
    private final List<ZPCallbacks.ZPMouseScrollCallback> onMouseScroll;
    private final List<ZPCallbacks.ZPKeyboardClickCallback> onKeyClick;
    private final List<ZPCallbacks.ZPKeyboardHoldCallback> onKeyHold;
    private final List<ZPCallbacks.ZPKeyboardReleaseCallback> onKeyRelease;

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

    private ZPCallbacksManager() {
        this.onResize = new ArrayList<>();
        this.onMouseClick = new ArrayList<>();
        this.onMouseHold = new ArrayList<>();
        this.onMouseRelease = new ArrayList<>();
        this.onMouseScroll = new ArrayList<>();
        this.onKeyClick = new ArrayList<>();
        this.onKeyHold = new ArrayList<>();
        this.onKeyRelease = new ArrayList<>();
        this.onChar = new ArrayList<>();
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
    }

    public void addCharCallback(ZPCallbacks.ZPCharCallback cb) {
        this.onChar.add(cb);
    }

    public void addWindowResizeCallback(ZPCallbacks.ZPWindowResizeCallback cb) {
        this.onResize.add(cb);
    }

    public void addMouseClickCallback(ZPCallbacks.ZPMouseClickCallback cb) {
        this.onMouseClick.add(cb);
    }

    public void addMouseHoldCallback(ZPCallbacks.ZPMouseHoldCallback cb) {
        this.onMouseHold.add(cb);
    }

    public void addMouseReleaseCallback(ZPCallbacks.ZPMouseReleaseCallback cb) {
        this.onMouseRelease.add(cb);
    }

    public void addMouseScrollCallback(ZPCallbacks.ZPMouseScrollCallback cb) {
        this.onMouseScroll.add(cb);
    }

    public void addKeyboardClickCallback(ZPCallbacks.ZPKeyboardClickCallback cb) {
        this.onKeyClick.add(cb);
    }

    public void addKeyboardHoldCallback(ZPCallbacks.ZPKeyboardHoldCallback cb) {
        this.onKeyHold.add(cb);
    }

    public void addKeyboardReleaseCallback(ZPCallbacks.ZPKeyboardReleaseCallback cb) {
        this.onKeyRelease.add(cb);
    }

    private GLFWCharCallback setupCharCallback(@NotNull Window window) {
        long handle = window.getWindow();
        this.originalCharCallback = GLFW.glfwSetCharCallback(handle, null);

        return GLFW.glfwSetCharCallback(handle, (win, c) -> {
            for (ZPCallbacks.ZPCharCallback cb : this.onChar) {
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
            for (ZPCallbacks.ZPWindowResizeCallback cb : this.onResize) {
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
                for (ZPCallbacks.ZPMouseClickCallback cb : this.onMouseClick) {
                    cb.onMouseClickAction(win, button);
                }
            } else if (action == GLFW.GLFW_REPEAT) {
                for (ZPCallbacks.ZPMouseHoldCallback cb : this.onMouseHold) {
                    cb.onMouseHoldAction(win, button);
                }
            } else if (action == GLFW.GLFW_RELEASE) {
                for (ZPCallbacks.ZPMouseReleaseCallback cb : this.onMouseRelease) {
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
            for (ZPCallbacks.ZPMouseScrollCallback cb : this.onMouseScroll) {
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
                for (ZPCallbacks.ZPKeyboardClickCallback cb : this.onKeyClick) {
                    cb.onKeyboardClickAction(win, key, scanCode, mods);
                }
            } else if (action == GLFW.GLFW_REPEAT) {
                for (ZPCallbacks.ZPKeyboardHoldCallback cb : this.onKeyHold) {
                    cb.onKeyBoardHoldAction(win, key, scanCode, mods);
                }
            } else if (action == GLFW.GLFW_RELEASE) {
                for (ZPCallbacks.ZPKeyboardReleaseCallback cb : this.onKeyRelease) {
                    cb.onKeyboardReleaseAction(win, key, scanCode, mods);
                }
            }

            if (this.originalKeyCallback != null) {
                this.originalKeyCallback.invoke(win, key, scanCode, action, mods);
            }
        });
    }
}