package ru.gltexture.zpm3.engine.registry.collection;

import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.*;

public final class ZPRegistryObjectsCollector<T> implements IZPRegistryObjectsCollector<T> {
    private final Map<String, LinkedHashSet<RegistryObject<T>>> objectsToCollect;
    private String currentActiveCollectionId;

    public ZPRegistryObjectsCollector() {
        this.objectsToCollect = new HashMap<>();
        this.currentActiveCollectionId = null;
    }

    @Override
    public void startCollectingInto(@NotNull String id) {
        this.currentActiveCollectionId = id;
        if (!this.objectsToCollect.containsKey(id)) {
            this.objectsToCollect.put(id, new LinkedHashSet<>());
        }
    }

    @Override
    public void stopCollecting() {
        this.currentActiveCollectionId = null;
    }

    public boolean canCollect() {
        return this.currentActiveCollectionId != null;
    }

    public void add(RegistryObject<T> t) {
        if (!this.canCollect()) {
            return;
        }
        this.objectsToCollect.get(this.currentActiveCollectionId).add(t);
    }

    @Override
    public @NotNull Map<String, LinkedHashSet<RegistryObject<T>>> getObjectsToCollect() {
        return new HashMap<>(this.objectsToCollect);
    }
}