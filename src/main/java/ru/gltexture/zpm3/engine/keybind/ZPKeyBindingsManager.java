package ru.gltexture.zpm3.engine.keybind;

import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ZPKeyBindingsManager {
    private final List<KeyMapping> keyMappingList;

    public ZPKeyBindingsManager() {
        this.keyMappingList = new ArrayList<>();
    }

    public abstract void init();

    public List<KeyMapping> getKeyMappingList() {
        return this.keyMappingList;
    }

    protected KeyMapping addKeyBinding(@NotNull KeyMapping keyMapping) {
        this.getKeyMappingList().add(keyMapping);
        return keyMapping;
    }
}
