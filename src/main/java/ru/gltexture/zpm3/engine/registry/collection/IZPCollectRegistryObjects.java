package ru.gltexture.zpm3.engine.registry.collection;

import org.jetbrains.annotations.Nullable;

public interface IZPCollectRegistryObjects {
    @Nullable ZPRegistryObjectsCollector<?> getObjectsCollector();
}
