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

package ru.gltexture.zpm3.modules.net_pack.data.accessors;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;

import java.util.*;

public final class ZPGlobalAccessorsRegistry {
    public static final ZPGlobalAccessorsRegistry INSTANCE = new ZPGlobalAccessorsRegistry();
    private final Map<ResourceLocation, ZPNetDataAccessor<?>> accessors;

    ZPGlobalAccessorsRegistry() {
        this.accessors = new HashMap<>();
    }

    public void register(@NotNull ZPNetDataAccessor<?> accessor) {
        ZPNetDataAccessor<?> old = this.accessors.putIfAbsent(accessor.getResourceId(), accessor);
        if (old != null) {
            throw new ZPRuntimeException("Duplicate accessor: " + accessor.getResourceId());
        }
    }

    public @NotNull List<ZPNetDataAccessor<?>> buildIdAssignations() {
        ArrayList<ZPNetDataAccessor<?>> result = new ArrayList<>(this.accessors.values());
        result.sort(Comparator.comparing(accessor -> accessor.getResourceId().toString()));
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setGlobalId(i);
        }
        return result;
    }

    public @Nullable ZPNetDataAccessor<?> get(@NotNull ResourceLocation id) {
        return this.accessors.get(id);
    }

    public int size() {
        return this.accessors.size();
    }
}