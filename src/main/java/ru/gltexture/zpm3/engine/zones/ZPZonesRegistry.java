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

package ru.gltexture.zpm3.engine.zones;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import ru.gltexture.zpm3.engine.zones.vars.ZPZoneIntVar;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class ZPZonesRegistry {
    private static final Map<String, ZPZoneFlag> ZONE_FLAGS = new LinkedHashMap<>();
    private static final Map<String, ZPZoneIntVar> ZONE_INT_VARIABLES = new LinkedHashMap<>();

    ZPZonesRegistry() {
    }

    public static ZPZoneIntVar RegisterIntVar(@NotNull String variableId, @NotNull Integer t, @NotNull Integer min, @NotNull Integer max) {
        return ZPZoneManager.ZP_ZONES_REGISTRY.registerIntVar(new ZPZoneIntVar(variableId, t, min, max));
    }

    public static ZPZoneFlag RegisterFlag(@NotNull String id) {
        return ZPZoneManager.ZP_ZONES_REGISTRY.registerFlag(id);
    }
    
    ZPZoneIntVar registerIntVar(@NotNull ZPZoneIntVar variable) {
        String id = variable.getVariableId();
        if (ZPZonesRegistry.ZONE_INT_VARIABLES.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate zone variable: " + id);
        }
        ZPZonesRegistry.ZONE_INT_VARIABLES.put(id, variable);
        return variable;
    }

    ZPZoneFlag registerFlag(@NotNull String id) {
        if (ZPZonesRegistry.ZONE_FLAGS.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate zone flag: " + id);
        }

        ZPZoneFlag flag = new ZPZoneFlag(id);
        ZPZonesRegistry.ZONE_FLAGS.put(id, flag);
        return flag;
    }

    public static boolean ifFlagExists(String id) {
        return ZPZonesRegistry.ZONE_FLAGS.containsKey(id);
    }

    public static @Nullable ZPZoneFlag flagValueOf(String id) {
        return ZPZonesRegistry.ZONE_FLAGS.get(id);
    }

    public static @Unmodifiable Collection<ZPZoneFlag> flagValues() {
        return Collections.unmodifiableCollection(ZPZonesRegistry.ZONE_FLAGS.values());
    }

    public static Stream<ZPZoneFlag> flagsStream() {
        return ZPZonesRegistry.ZONE_FLAGS.values().stream();
    }

    public static boolean ifVariableExists(String id) {
        return ZPZonesRegistry.ZONE_INT_VARIABLES.containsKey(id);
    }

    public static @Nullable ZPZoneIntVar int_variableValueOf(String id) {
        return ZPZonesRegistry.ZONE_INT_VARIABLES.get(id);
    }

    public static @Unmodifiable Collection<ZPZoneIntVar> int_variableValues() {
        return Collections.unmodifiableCollection(ZPZonesRegistry.ZONE_INT_VARIABLES.values());
    }

    public static Stream<ZPZoneIntVar> int_variablesStream() {
        return ZPZonesRegistry.ZONE_INT_VARIABLES.values().stream();
    }

    public static void clear() {
        ZPZonesRegistry.ZONE_FLAGS.clear();
        ZPZonesRegistry.ZONE_INT_VARIABLES.clear();
    }
}