package ru.gltexture.zpm3.engine.client.callbacking;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ZPCallbacks {
    @FunctionalInterface
    public interface ZPCharCallback {
        void onAction(long descriptor, int c);
    }

    @FunctionalInterface
    public interface ZPWindowResizeCallback {
        void onAction(long descriptor, int width, int height);
    }

    @FunctionalInterface
    public interface ZPMouseClickCallback {
        void onAction(long descriptor, int key);
    }

    @FunctionalInterface
    public interface ZPMouseHoldCallback {
        void onAction(long descriptor, int key);
    }

    @FunctionalInterface
    public interface ZPMouseReleaseCallback {
        void onAction(long descriptor, int key);
    }

    @FunctionalInterface
    public interface ZPMouseScrollCallback {
        void onAction(long descriptor, int x, int y);
    }

    @FunctionalInterface
    public interface ZPKeyboardClickCallback {
        void onAction(long descriptor, int key, int scanCode, int mods);
    }

    @FunctionalInterface
    public interface ZPKeyboardHoldCallback {
        void onAction(long descriptor, int key, int scanCode, int mods);
    }

    @FunctionalInterface
    public interface ZPKeyboardReleaseCallback {
        void onAction(long descriptor, int key, int scanCode, int mods);
    }
}
