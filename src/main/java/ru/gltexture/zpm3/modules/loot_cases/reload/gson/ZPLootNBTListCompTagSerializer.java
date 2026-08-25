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
import com.google.gson.TypeAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.nbt.CompoundTag;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.ZPLootNbtValue;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.values.ZPLootNbtCompoundTag;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.values.ZPLootNbtListTag;

import java.io.IOException;
import java.util.List;

import static ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.ZPLootNbtValue.TYPE_COMPOUNDTAG;
import static ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.ZPLootNbtValue.TYPE_LISTTAG;

public class ZPLootNBTListCompTagSerializer extends TypeAdapter<ZPLootNbtValue> {
    @Override
    public void write(JsonWriter out, ZPLootNbtValue value) throws IOException {
        JsonObject object = new JsonObject();
        final List<CompoundTag> tags = ((ZPLootNbtListTag) value).value();
        object.addProperty("type", TYPE_LISTTAG);
        final JsonArray array = new JsonArray();
        for (CompoundTag tag : tags) {
            JsonObject element = new JsonObject();
            element.addProperty("value", tag.toString());
            array.add(element);
        }
        object.add("value", array);
        Streams.write(object, out);
    }

    @Override
    public ZPLootNbtValue read(JsonReader in) throws IOException {
        return null;
    }
}
