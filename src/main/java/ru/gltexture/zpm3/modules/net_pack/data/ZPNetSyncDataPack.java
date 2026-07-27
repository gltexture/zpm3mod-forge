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

package ru.gltexture.zpm3.modules.net_pack.data;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPLogger;

import java.util.*;
import java.util.function.Consumer;

public record ZPNetSyncDataPack(@NotNull Map<String, Object> dataPack) {
    public ZPNetSyncDataPack() {
        this(new HashMap<>());
    }

    public ZPNetSyncDataPack(@NotNull Map<String, Object> dataPack) {
        this.dataPack = new HashMap<>();
        dataPack.forEach(this::set);
    }

    public ZPNetSyncDataPack(@NotNull Consumer<ZPNetSyncDataPack> setDefaultValues) {
        this();
        setDefaultValues.accept(this);
    }

    public ZPNetSyncDataPack(ZPNetSyncDataPack... merge) {
        this();
        Arrays.stream(merge).forEach(e -> {
            this.dataPack.putAll(e.dataPack());
        });
    }

    public ZPNetSyncDataPack(Collection<ZPNetSyncDataPack> merge) {
        this();
        merge.forEach(e -> {
            this.dataPack.putAll(e.dataPack());
        });
    }

    public boolean hasKey(String key) {
        return this.dataPack.containsKey(key);
    }

    public ZPNetSyncDataPack set(String key, Object value) {
        if (!(value instanceof Integer) && !(value instanceof Float) && !(value instanceof Double) && !(value instanceof Boolean)) {
            ZPLogger.error("Unsupported value type: " + value.getClass().getName());
            return this;
        }
        this.dataPack.put(key, value);
        return this;
    }

    public ZPNetSyncDataPack setInt(String key, int value) {
        this.dataPack.put(key, value);
        return this;
    }

    public int getInt(String key, int defaultValue) {
        Object value = this.dataPack.get(key);
        if (value instanceof Integer integer) {
            return integer;
        }
        return defaultValue;
    }

    public ZPNetSyncDataPack setDouble(String key, double value) {
        this.dataPack.put(key, value);
        return this;
    }

    public double getDouble(String key, double defaultValue) {
        Object value = this.dataPack.get(key);
        if (value instanceof Double doubleValue) {
            return doubleValue;
        }
        return defaultValue;
    }

    public ZPNetSyncDataPack setFloat(String key, float value) {
        this.dataPack.put(key, value);
        return this;
    }

    public float getFloat(String key, float defaultValue) {
        Object value = this.dataPack.get(key);
        if (value instanceof Float floatValue) {
            return floatValue;
        }
        return defaultValue;
    }

    public ZPNetSyncDataPack setBoolean(String key, boolean value) {
        this.dataPack.put(key, value);
        return this;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Object value = this.dataPack.get(key);
        if (value instanceof Boolean booleanValue) {
            return booleanValue;
        }
        return defaultValue;
    }

    public void replace(ZPNetSyncDataPack other) {
        this.dataPack.clear();
        this.dataPack.putAll(other.dataPack());
    }

    @Override
    public Map<String, Object> dataPack() {
        return new HashMap<>(this.dataPack);
    }
}
