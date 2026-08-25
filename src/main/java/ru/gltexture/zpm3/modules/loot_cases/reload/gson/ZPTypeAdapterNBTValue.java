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

package ru.gltexture.zpm3.modules.loot_cases.reload.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.TagParser;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.ZPLootNbtValue;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.values.*;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.random.ZPRandomization;

import java.io.IOException;
import java.util.*;

import static ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.ZPLootNbtValue.TYPE_COMPOUNDTAG;
import static ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.ZPLootNbtValue.TYPE_LISTTAG;

public final class ZPTypeAdapterNBTValue extends TypeAdapter<ZPLootNbtValue> {
    @Override
    public void write(JsonWriter out, ZPLootNbtValue value) throws IOException {
    }

    @Override
    public ZPLootNbtValue read(JsonReader in) throws IOException {
        final JsonObject object = JsonParser.parseReader(in).getAsJsonObject();
        final String type = object.get("type").getAsString();
        return switch (type) {
            case ZPLootNbtValue.TYPE_INT -> new ZPLootNbtInt(object.get("value").getAsInt());
            case ZPLootNbtValue.TYPE_LONG -> new ZPLootNbtLong(object.get("value").getAsLong());
            case ZPLootNbtValue.TYPE_FLOAT -> new ZPLootNbtFloat(object.get("value").getAsFloat());
            case ZPLootNbtValue.TYPE_DOUBLE -> new ZPLootNbtDouble(object.get("value").getAsDouble());
            case ZPLootNbtValue.TYPE_BOOLEAN -> new ZPLootNbtBoolean(object.get("value").getAsBoolean());
            case ZPLootNbtValue.TYPE_STRING -> new ZPLootNbtString(object.get("value").getAsString());
            case ZPLootNbtValue.TYPE_COMPOUNDTAG -> {
                try {
                    yield new ZPLootNbtCompoundTag(TagParser.parseTag(object.get("value").getAsString()));
                } catch (CommandSyntaxException e) {
                    throw new ZPIOException(e);
                }
            }
            case ZPLootNbtValue.TYPE_LISTTAG -> this.parseListTag(object);
            case ZPLootNbtValue.TYPE_RANDOM_INT -> new ZPLootNbtRandomInt(object.get("min").getAsInt(), object.get("max").getAsInt(), this.parse(object.get("randomization").getAsJsonObject()));
            case ZPLootNbtValue.TYPE_RANDOM_LONG -> new ZPLootNbtRandomLong(object.get("min").getAsLong(), object.get("max").getAsLong(), this.parse(object.get("randomization").getAsJsonObject()));
            case ZPLootNbtValue.TYPE_RANDOM_FLOAT -> new ZPLootNbtRandomFloat(object.get("min").getAsFloat(), object.get("max").getAsFloat(), this.parse(object.get("randomization").getAsJsonObject()));
            case ZPLootNbtValue.TYPE_RANDOM_DOUBLE -> new ZPLootNbtRandomDouble(object.get("min").getAsDouble(), object.get("max").getAsDouble(), this.parse(object.get("randomization").getAsJsonObject()));
            case ZPLootNbtValue.TYPE_RANDOM_BOOLEAN -> new ZPLootNbtRandomBoolean(object.get("chance").getAsFloat());
            default -> throw new ZPIOException("Unknown NBT value type: " + type);
        };
    }

    private ZPLootNbtListTag parseListTag(@NotNull JsonObject jsonObject) {
        final List<CompoundTag> values = new ArrayList<>();
        JsonArray array = jsonObject.getAsJsonArray("value");
        array.forEach(e -> {
            try {
                values.add(TagParser.parseTag(e.getAsJsonObject().get("value").getAsString()));
            } catch (CommandSyntaxException ex) {
                throw new ZPIOException(ex);
            }
        });
        return new ZPLootNbtListTag(values);
    }

    private ZPRandomization parse(@NotNull JsonObject jsonObject) {
        final String typeName = jsonObject.get("type").getAsString();
        final ZPRandomization.Type type;
        try {
            type = ZPRandomization.Type.valueOf(typeName.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new ZPIOException("Unknown randomization type: " + typeName, e);
        }
        return new ZPRandomization(type, jsonObject.get("parameter").getAsFloat());
    }
}
