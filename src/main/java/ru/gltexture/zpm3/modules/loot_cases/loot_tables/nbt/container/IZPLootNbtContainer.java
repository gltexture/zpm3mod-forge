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

package ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.container;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.ZPLootNbtValue;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.values.*;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.random.ZPRandomization;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface IZPLootNbtContainer {
    static CompoundTag compound(Consumer<CompoundTag> consumer) {
        CompoundTag tag = new CompoundTag();
        consumer.accept(tag);
        return tag;
    }

    static Tag list(CompoundTag... compound) {
        final ListTag tag = new ListTag();
        tag.addAll(Arrays.asList(compound));
        return tag;
    }

    IZPLootNbtContainer add(@NotNull String id, @NotNull ZPLootNbtValue value);

    default IZPLootNbtContainer add(@NotNull String key, @NotNull CompoundTag value) {
        return this.add(key, new ZPLootNbtCompoundTag(value));
    }

    default IZPLootNbtContainer add(@NotNull String key, List<CompoundTag> value) {
        return this.add(key, new ZPLootNbtListTag(value));
    }

    default IZPLootNbtContainer add(@NotNull String key, int value) {
        return this.add(key, new ZPLootNbtInt(value));
    }

    default IZPLootNbtContainer add(@NotNull String key, long value) {
        return this.add(key, new ZPLootNbtLong(value));
    }

    default IZPLootNbtContainer add(@NotNull String key, float value) {
        return this.add(key, new ZPLootNbtFloat(value));
    }

    default IZPLootNbtContainer add(@NotNull String key, String value) {
        return this.add(key, new ZPLootNbtString(value));
    }

    default IZPLootNbtContainer add(@NotNull String key, double value) {
        return this.add(key, new ZPLootNbtDouble(value));
    }

    default IZPLootNbtContainer add(@NotNull String key, boolean value) {
        return this.add(key, new ZPLootNbtBoolean(value));
    }

    default IZPLootNbtContainer addRandom(@NotNull String key, int min, int max, @NotNull ZPRandomization randomization) {
        return this.add(key, new ZPLootNbtRandomInt(min, max, randomization));
    }

    default IZPLootNbtContainer addRandom(@NotNull String key, long min, long max, @NotNull ZPRandomization randomization) {
        return this.add(key, new ZPLootNbtRandomLong(min, max, randomization));
    }

    default IZPLootNbtContainer addRandom(@NotNull String key, float min, float max, @NotNull ZPRandomization randomization) {
        return this.add(key, new ZPLootNbtRandomFloat(min, max, randomization));
    }

    default IZPLootNbtContainer addRandom(@NotNull String key, double min, double max, @NotNull ZPRandomization randomization) {
        return this.add(key, new ZPLootNbtRandomDouble(min, max, randomization));
    }

    default IZPLootNbtContainer addRandom(@NotNull String key, float trueChance) {
        return this.add(key, new ZPLootNbtRandomBoolean(trueChance));
    }

    @NotNull @Unmodifiable Map<String, ZPLootNbtValue> getValues();
}
