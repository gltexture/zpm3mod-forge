/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

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