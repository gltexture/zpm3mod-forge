package ru.gltexture.zpm3.engine.registry;

import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPNullException;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.registry.collection.IZPRegistryObjectsCollector;

import java.util.*;

public abstract class ZPRegistryCollections {
    private static final Map<Class<? extends ZPRegistry<?>>, IZPRegistryObjectsCollector<?>> zpRegistryObjectsCollectorMap = new HashMap<>();

    static void addNewEntry(@NotNull Class<? extends ZPRegistry<?>> clazz, @NotNull IZPRegistryObjectsCollector<?> collector) {
        ZPRegistryCollections.zpRegistryObjectsCollectorMap.put(clazz, collector);
    }

    public static void clearAll() {
        ZPLogger.info("Cleared ZP Registration");
        ZPRegistryCollections.zpRegistryObjectsCollectorMap.clear();
    }

    @SuppressWarnings("unchecked")
    public static <T> IZPRegistryObjectsCollector<T> getCollector(@NotNull Class<? extends ZPRegistry<T>> clazz) {
        try {
            if (!ZPRegistryCollections.zpRegistryObjectsCollectorMap.containsKey(clazz)) {
                throw new ZPNullException("Couldn't find collection for: " + clazz);
            }
            return (IZPRegistryObjectsCollector<T>) ZPRegistryCollections.zpRegistryObjectsCollectorMap.get(clazz);
        } catch (ZPNullException | ClassCastException e) {
            throw new ZPRuntimeException(e);
        }
    }

    public static <T> LinkedHashSet<RegistryObject<T>> getCollectionById(@NotNull Class<? extends ZPRegistry<T>> clazz, @NotNull String id) {
        try {
            return ZPRegistryCollections.getCollector(clazz).getCollection(id);
        } catch (ZPNullException | ClassCastException e) {
            throw new ZPRuntimeException(e);
        }
    }
}
