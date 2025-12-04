package ru.gltexture.zpm3.engine.helpers;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.keybind.ZPKeyBindingsManager;

import java.util.HashSet;
import java.util.Set;

public abstract class ZPKeyBindingsRegistryHelper {
    private static Set<ZPKeyBindingsManager> keyBindings = new HashSet<>();

    public static void clear() {
        ZPKeyBindingsRegistryHelper.keyBindings = null;
    }

    public static void addNewKeybinding(@NotNull ZPKeyBindingsManager zpBaseKeyBindings) {
        ZPKeyBindingsRegistryHelper.keyBindings.add(zpBaseKeyBindings);
    }

    public static Set<ZPKeyBindingsManager> getAllKeyBindings() {
        return ZPKeyBindingsRegistryHelper.keyBindings;
    }
}
