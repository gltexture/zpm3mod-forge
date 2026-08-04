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

package ru.gltexture.zpm3.modules.net_pack.data.data_ent;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;

import java.util.Optional;

public interface IZPNetEntDataSyncer {
    <E> void setVar(@NotNull Entity entity, @NotNull ZPNetDataAccessor<E> dataAccessor, @NotNull ZPNetDataVar<E> value);
    <S> Optional<ZPNetDataVar<S>> getVar(@NotNull Entity entity, @NotNull ZPNetDataAccessor<S> dataAccessor);

    default @NotNull <S> ZPNetDataVar<S> getVarOfDefault(@NotNull Entity entity, @NotNull ZPNetDataAccessor<S> dataAccessor) {
        return this.getVar(entity, dataAccessor).orElse(dataAccessor.createDefault().get());
    }

    void defineAccessorOnEntity(@NotNull Class<? extends Entity> clazz, @NotNull ZPNetDataAccessor<?> dataAccessor);
    ZPNetEntityData getEntityDataVars(@NotNull Entity entity);

    record ZPNetEntityData(Int2ObjectMap<ZPNetDataVar<?>> vars) { ; }
}
