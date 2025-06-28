package ru.gltexture.zpm3.engine.registry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.registry.collection.IZPRegistryObjectsCollector;
import ru.gltexture.zpm3.engine.registry.collection.ZPRegistryObjectsCollector;

import java.util.*;

public abstract class ZPRegistryCollections {
    private static final Map<Class<? extends ZPRegistry<?>>, IZPRegistryObjectsCollector<?>> zpRegistryObjectsCollectorMap = new HashMap<>();

    static void addNewEntry(@NotNull Class<? extends ZPRegistry<?>> clazz, @NotNull IZPRegistryObjectsCollector<?> collector) {
        ZPRegistryCollections.zpRegistryObjectsCollectorMap.put(clazz, collector);
    }

    @SuppressWarnings("unchecked")
    public static @Nullable <T> IZPRegistryObjectsCollector<T> getCollector(@NotNull Class<? extends ZPRegistry<T>> clazz) {
        try {
            return (IZPRegistryObjectsCollector<T>) ZPRegistryCollections.zpRegistryObjectsCollectorMap.get(clazz);
        } catch (ClassCastException e) {
            throw new ZPRuntimeException(e);
        }
    }
}
