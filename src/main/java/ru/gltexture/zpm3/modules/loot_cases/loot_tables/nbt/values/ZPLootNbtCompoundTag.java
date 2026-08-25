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

package ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.values;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.ZPLootNbtValue;

public final class ZPLootNbtCompoundTag extends ZPLootNbtValue {
    private final CompoundTag value;

    public ZPLootNbtCompoundTag(CompoundTag value) {
        super(ZPLootNbtValue.TYPE_COMPOUNDTAG);
        this.value = value;
    }

    @Override
    public void writeValue(@NotNull CompoundTag nbt, @NotNull String key) {
        nbt.put(key, this.value);
    }

    public CompoundTag value() {
        return this.value;
    }
}