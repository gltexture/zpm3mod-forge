package ru.gltexture.zpm3.engine.registry.collection;

import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

public final class ZPRegistryObjectsCollector<T> implements IZPRegistryObjectsCollector<T> {
    private final Set<RegistryObject<T>> objectsToCollect;
    private boolean flagToCollect;

    public ZPRegistryObjectsCollector() {
        this.objectsToCollect = new HashSet<>();
        this.flagToCollect = true;
    }

    public void continueCollecting() {
        this.flagToCollect = true;
    }

    public void stopCollecting() {
        this.flagToCollect = false;
    }

    public boolean canCollect() {
        return this.flagToCollect;
    }

    public void add(RegistryObject<T> t) {
        if (!this.canCollect()) {
            return;
        }
        this.objectsToCollect.add(t);
    }

    public Set<RegistryObject<T>> getObjectsToCollect() {
        return new HashSet<>(this.objectsToCollect);
    }
}