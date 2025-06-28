package ru.gltexture.zpm3.engine.registry.collection;

import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface IZPRegistryObjectsCollector<T> {
    @NotNull Set<RegistryObject<T>> getObjectsToCollect();
}
