package ru.gltexture.zpm3.engine.client.callbacking;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ZPCallbacks {
    @FunctionalInterface
    public interface ZPCharCallback {
        void onCharAction(long descriptor, int c);
    }

    @FunctionalInterface
    public interface ZPWindowResizeCallback {
        void onWindowResizeAction(long descriptor, int width, int height);
    }

    @FunctionalInterface
    public interface ZPMouseClickCallback {
        void onMouseClickAction(long descriptor, int key);
    }

    @FunctionalInterface
    public interface ZPMouseHoldCallback {
        void onMouseHoldAction(long descriptor, int key);
    }

    @FunctionalInterface
    public interface ZPMouseReleaseCallback {
        void onMouseReleaseAction(long descriptor, int key);
    }

    @FunctionalInterface
    public interface ZPMouseScrollCallback {
        void onMouseScrollAction(long descriptor, int x, int y);
    }

    @FunctionalInterface
    public interface ZPKeyboardClickCallback {
        void onKeyboardClickAction(long descriptor, int key, int scanCode, int mods);
    }

    @FunctionalInterface
    public interface ZPKeyboardHoldCallback {
        void onKeyBoardHoldAction(long descriptor, int key, int scanCode, int mods);
    }

    @FunctionalInterface
    public interface ZPKeyboardReleaseCallback {
        void onKeyboardReleaseAction(long descriptor, int key, int scanCode, int mods);
    }
}
