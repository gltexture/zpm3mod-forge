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

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;

import java.util.Optional;

public interface IZPNetStaticDataSyncerClient extends IZPNetStaticDataSyncer {
    default <E> ZPNetDataVar<E> getVarOrDefault(@NotNull ZPNetDataAccessor<E> accessor) {
        return this.getVar(accessor).orElse(accessor.createDefault().get());
    }

    <E> Optional<ZPNetDataVar<E>> getVar(@NotNull ZPNetDataAccessor<E> accessor);
    <E> void setValue(@NotNull ZPNetDataAccessor<E> accessor, @NotNull ZPNetDataVar<E> var);
}