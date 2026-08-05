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

package ru.gltexture.zpm3.modules.entity.util;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPEntityExt;

public enum ZPEntityStat {
    ACID {
        @Override
        public int get(@NotNull Entity entity) {
            return ((IZPEntityExt) entity).zpm3forge$getAcidLevel();
        }

        @Override
        public void set(@NotNull Entity entity, int value) {
            ((IZPEntityExt) entity).zpm3forge$setAcidLevel(value);
        }
    };

    public abstract int get(@NotNull Entity entity);

    public abstract void set(@NotNull Entity entity, int value);

    public final void add(@NotNull Entity entity, int value) {
        this.set(entity, this.get(entity) + value);
    }

    public final void decrease(@NotNull Entity entity, int value) {
        this.set(entity, this.get(entity) - value);
    }
}