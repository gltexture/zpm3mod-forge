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

package ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public abstract class ZPLootNbtValue {
    public static final String TYPE_INT = "int";
    public static final String TYPE_LONG = "long";
    public static final String TYPE_FLOAT = "float";
    public static final String TYPE_DOUBLE = "double";
    public static final String TYPE_BOOLEAN = "boolean";
    public static final String TYPE_STRING = "string";
    public static final String TYPE_COMPOUNDTAG = "compound_tag";
    public static final String TYPE_LISTTAG = "list_tag";

    public static final String TYPE_RANDOM_INT = "random_int";
    public static final String TYPE_RANDOM_LONG = "random_long";
    public static final String TYPE_RANDOM_FLOAT = "random_float";
    public static final String TYPE_RANDOM_DOUBLE = "random_double";
    public static final String TYPE_RANDOM_BOOLEAN = "random_boolean";

    private final String type;

    public ZPLootNbtValue(String type) {
        this.type = type;
    }

    public abstract void writeValue(@NotNull CompoundTag nbt, @NotNull String key);

    public String getType() {
        return this.type;
    }
}