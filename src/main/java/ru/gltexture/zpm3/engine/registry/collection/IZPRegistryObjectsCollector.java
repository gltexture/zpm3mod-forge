package ru.gltexture.zpm3.engine.registry.collection;

import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.exceptions.ZPNullException;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public interface IZPRegistryObjectsCollector<T> {
    default LinkedHashSet<RegistryObject<T>> getCollection(@NotNull String id) throws ZPRuntimeException {
        if (!this.getObjectsToCollect().containsKey(id)) {
            throw new ZPNullException("Couldn't find collection: " + id);
        }
        return this.getObjectsToCollect().get(id);
    }

    @NotNull Map<String, LinkedHashSet<RegistryObject<T>>> getObjectsToCollect();
    void startCollectingInto(@NotNull String id);
    void stopCollecting();
}
