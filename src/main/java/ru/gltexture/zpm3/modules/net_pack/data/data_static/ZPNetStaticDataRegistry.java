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

package ru.gltexture.zpm3.modules.net_pack.data.data_static;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ZPNetStaticDataRegistry {
    private final Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> defaultVarsRegistry;
    private final Int2ObjectMap<ZPNetDataAccessor<?>> dataAccessors_idMap;

    ZPNetStaticDataRegistry() {
        this.defaultVarsRegistry = new HashMap<>();
        this.dataAccessors_idMap = new Int2ObjectOpenHashMap<>();
    }

    public <E> void defineStaticAccessor(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> defaultValue) {
        if (this.defaultVarsRegistry.containsKey(accessor)) {
            throw new ZPRuntimeException("Data accessor registered twice: " + accessor);
        }
        this.defaultVarsRegistry.put(accessor, defaultValue);
        this.dataAccessors_idMap.put(accessor.getGlobalId(), accessor);
    }

    @SuppressWarnings("all")
    protected <T> ZPNetDataAccessor<T> getAccessorUnsafe(int id) {
        return (ZPNetDataAccessor<T>) this.getAccessor(id).orElseThrow(() -> new ZPRuntimeException("Unknown accessor id: " + id));
    }

    public Optional<ZPNetDataAccessor<?>> getAccessor(int id) {
        return Optional.ofNullable(this.dataAccessors_idMap.get(id));
    }

    public @Unmodifiable Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> getDefaultVarsRegistry() {
        return Collections.unmodifiableMap(this.defaultVarsRegistry);
    }

    public String buildAccessorsHash() {
        return ZPNetDataAccessor.buildAccessorsHash(this.dataAccessors_idMap.values());
    }
}