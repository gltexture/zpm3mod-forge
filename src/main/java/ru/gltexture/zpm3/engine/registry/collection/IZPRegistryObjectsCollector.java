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
