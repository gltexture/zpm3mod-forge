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

package ru.gltexture.zpm3.engine.registry;

import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPNullException;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.registry.collection.IZPRegistryObjectsCollector;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.*;

public abstract class ZPRegistryCollections {
    private static final Map<Class<? extends ZPCommonRegistry<?>>, IZPRegistryObjectsCollector<?>> zpRegistryObjectsCollectorMap = new HashMap<>();

    static void addNewEntry(@NotNull Class<? extends ZPCommonRegistry<?>> clazz, @NotNull IZPRegistryObjectsCollector<?> collector) {
        ZPRegistryCollections.zpRegistryObjectsCollectorMap.put(clazz, collector);
    }

    public static void clearAll() {
        ZPLogger.info("Cleared ZP Registration");
        ZPRegistryCollections.zpRegistryObjectsCollectorMap.clear();
    }

    @SuppressWarnings("unchecked")
    public static <T> IZPRegistryObjectsCollector<T> getCollector(@NotNull Class<? extends ZPCommonRegistry<T>> clazz) {
        try {
            if (!ZPRegistryCollections.zpRegistryObjectsCollectorMap.containsKey(clazz)) {
                throw new ZPNullException("Couldn't find collection for: " + clazz);
            }
            return (IZPRegistryObjectsCollector<T>) ZPRegistryCollections.zpRegistryObjectsCollectorMap.get(clazz);
        } catch (ZPNullException | ClassCastException e) {
            throw new ZPRuntimeException(e);
        }
    }

    public static <T> LinkedHashSet<RegistryObject<T>> getCollectionById(@NotNull Class<? extends ZPCommonRegistry<T>> clazz, @NotNull String id) {
        try {
            return ZPRegistryCollections.getCollector(clazz).getCollection(id);
        } catch (ZPNullException | ClassCastException e) {
            throw new ZPRuntimeException(e);
        }
    }

    public static <T> LinkedHashSet<RegistryObject<T>> getCollectionById(Pair<@NotNull Class<? extends ZPCommonRegistry<T>>, @NotNull String>... stringPair) {
        try {
            final LinkedHashSet<RegistryObject<T>> registryObjects = new LinkedHashSet<>();
            Arrays.stream(stringPair).forEach(e -> {
                registryObjects.addAll(ZPRegistryCollections.getCollector(e.first()).getCollection(e.second()));
            });
            return registryObjects;
        } catch (ZPNullException | ClassCastException e) {
            throw new ZPRuntimeException(e);
        }
    }
}
