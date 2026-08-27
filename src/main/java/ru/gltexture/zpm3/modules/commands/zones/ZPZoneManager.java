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

package ru.gltexture.zpm3.modules.commands.zones;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.network.handler.ZPNetworkHandlerServer;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.modules.commands.zones.vars.ZPZoneIntVar;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public final class ZPZoneManager {
    public static final ZPZonesRegistry ZP_ZONES_REGISTRY = new ZPZonesRegistry();

    private static final String jsonName = "zp3_zones.json";
    private final Map<Level, ZonesContainer> zonesPerLevelMap;
    public static ZPZoneManager INSTANCE = new ZPZoneManager();

    private ZPZoneManager() {
        this.zonesPerLevelMap = new HashMap<>();
    }

    private static Vector2i blockChunk(@NotNull BlockPos pos) {
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;
        return new Vector2i(chunkX, chunkZ);
    }

    private static List<Vector2i> forEachChunkAABB(@NotNull Vector3f min, @NotNull Vector3f max) {
        final List<Vector2i> vector2is = new ArrayList<>();
        final int minChunkX = Mth.floor(min.x) >> 4;
        final int maxChunkX = Mth.floor(max.x) >> 4;
        final int minChunkZ = Mth.floor(min.z) >> 4;
        final int maxChunkZ = Mth.floor(max.z) >> 4;

        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
            for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                vector2is.add(new Vector2i(cx, cz));
            }
        }

        return vector2is;
    }

    private File getWorldSaveDir(@NotNull ServerLevel level) {
        File f = new File(level.getServer().getWorldPath(LevelResource.ROOT).toFile(), level.dimensionTypeId().location().getPath());
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;
    }

    @SuppressWarnings("all")
    public void writeToJSON(@NotNull ServerLevel level) {
        File file = new File(this.getWorldSaveDir(level), ZPZoneManager.jsonName);
        try {
            if (!file.exists()) {
                file.createNewFile();
                ZPLogger.info("Created: " + file.toString());
                return;
            }
        } catch (Exception e) {
            throw new ZPIOException(e);
        }
        final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
        try (Writer writer = new FileWriter(file)) {
            if (this.zonesPerLevelMap.containsKey(level) && this.zonesPerLevelMap.get(level) != null) {
                gson.toJson(this.zonesPerLevelMap.get(level).getIdAccessMap(), writer);
                ZPLogger.info("Wrote: " + file.toString());
            }
        } catch (IOException e) {
            throw new ZPIOException("Couldn't write zones json", e);
        }
    }

    @SuppressWarnings("all")
    public void loadFromJSON(@NotNull ServerLevel level) {
        File file = new File(this.getWorldSaveDir(level), ZPZoneManager.jsonName);
        try {
            if (!file.exists()) {
                file.createNewFile();
                ZPLogger.info("Created: " + file.toString());
                return;
            }
        } catch (Exception e) {
            throw new ZPIOException(e);
        }
        final Gson gson = (new GsonBuilder()).create();
        try (Reader reader = new FileReader(file)) {
            final Type type = new TypeToken<HashMap<String, Zone>>() {}.getType();
            final HashMap<String, ZPZoneManager.Zone> hashMap = gson.fromJson(reader, type);
            if (hashMap != null) {
                this.zonesPerLevelMap.remove(level);
                this.zonesPerLevelMap.put(level, new ZonesContainer().setIdAccessMap(hashMap));
                this.zonesPerLevelMap.get(level).buildFastPerChunkAccessMap(hashMap.values());
            }
            ZPLogger.info("Read: " + file.toString());
        } catch (IOException e) {
            throw new ZPIOException("Couldn't write zones json", e);
        }
    }

    public void newZoneBounds(@NotNull ServerLevel level, @NotNull String uniqueId, @NotNull Vector3i start, @NotNull Vector3i end) {
        this.zonesPerLevelMap.computeIfAbsent(level, k -> new ZonesContainer());
        final Zone zone = this.zonesPerLevelMap.get(level).getIdAccessMap().get(uniqueId);
        zone.start().set(start);
        zone.end().set(end);
        this.zonesPerLevelMap.get(level).buildFastPerChunkAccessMap(this.zonesPerLevelMap.get(level).getIdAccessMap().values());
        ZPNetworkHandlerServer.sendZoneToAll(zone, level, false);
        this.writeToJSON(level);
    }

    public void addNewZone(@NotNull ServerLevel level, @NotNull Zone zone) {
        this.zonesPerLevelMap.computeIfAbsent(level, k -> new ZonesContainer());
        this.zonesPerLevelMap.get(level).getIdAccessMap().put(zone.uniqueId(), zone);
        this.zonesPerLevelMap.get(level).buildFastPerChunkAccessMap(this.zonesPerLevelMap.get(level).getIdAccessMap().values());
        ZPNetworkHandlerServer.sendZoneToAll(zone, level, false);
        this.writeToJSON(level);
    }

    public @Nullable Collection<Zone> getZonesInChunk(@NotNull Level level, @NotNull BlockPos pos) {
        Vector2i chunkId = ZPZoneManager.blockChunk(pos);
        if (this.zonesPerLevelMap.containsKey(level) && this.zonesPerLevelMap.get(level) != null) {
            return this.zonesPerLevelMap.get(level).getFast_ChunkLookupTable().get(chunkId);
        }
        return null;
    }

    public boolean removeZone(@NotNull ServerLevel level, @NotNull String uniqueId) {
        final Map<String, Zone> flagsMap = this.zonesPerLevelMap.get(level).getIdAccessMap();
        if (this.zonesPerLevelMap.containsKey(level) && flagsMap.containsKey(uniqueId)) {
            final Zone zone = flagsMap.remove(uniqueId);
            this.zonesPerLevelMap.get(level).buildFastPerChunkAccessMap(flagsMap.values());
            ZPNetworkHandlerServer.sendZoneToAll(zone, level, true);
            this.writeToJSON(level);
            return true;
        }
        return false;
    }

    public boolean replaceFlags(@NotNull ServerLevel level, @NotNull String uniqueId, @NotNull Set<ZPZoneFlag> flags) {
        Zone zone = this.getZoneById(level, uniqueId);
        if (zone != null) {
            zone.flags().clear();
            zone.flags().addAll(flags);
            ZPNetworkHandlerServer.sendZoneToAll(zone, level, false);
            this.writeToJSON(level);
            return true;
        }
        return false;
    }

    public boolean addFlag(@NotNull ServerLevel level, @NotNull String uniqueId, @NotNull ZPZoneFlag flag) {
        Zone zone = this.getZoneById(level, uniqueId);
        if (zone != null) {
            if (!zone.flags().add(flag)) {
                return false;
            }
            ZPNetworkHandlerServer.sendZoneToAll(zone, level, false);
            this.writeToJSON(level);
            return true;
        }
        return false;
    }

    public boolean removeFlag(@NotNull ServerLevel level, @NotNull String uniqueId, @NotNull ZPZoneFlag flag) {
        Zone zone = this.getZoneById(level, uniqueId);
        if (zone != null) {
            if (!zone.flags().remove(flag)) {
                return false;
            }
            ZPNetworkHandlerServer.sendZoneToAll(zone, level, false);
            this.writeToJSON(level);
            return true;
        }
        return false;
    }

    public @Nullable Collection<Zone> getAllZonesOnLevel(@NotNull Level level) {
        if (!this.zonesPerLevelMap.containsKey(level)) {
            return null;
        }
        final Map<String, Zone> flagsMap = this.zonesPerLevelMap.get(level).getIdAccessMap();
        if (flagsMap == null) {
            return null;
        }
        return flagsMap.values();
    }

    public @Nullable Zone getZoneById(@NotNull Level level, @NotNull String id) {
        if (!this.zonesPerLevelMap.containsKey(level)) {
            return null;
        }
        final Map<String, Zone> flagsMap = this.zonesPerLevelMap.get(level).getIdAccessMap();
        if (flagsMap == null || !flagsMap.containsKey(id)) {
            return null;
        }
        return flagsMap.get(id);
    }

    public @Nullable Set<ZPZoneFlag> getFlags(@NotNull Level level, @NotNull String uniqueId) {
        Zone zone = this.getZoneById(level, uniqueId);
        if (zone != null) {
            return zone.flags();
        }
        return null;
    }

    public @Nullable Collection<ZPZoneIntVar> getAllZoneIntVariables(@NotNull Level level, @NotNull String uniqueId) {
        Zone zone = this.getZoneById(level, uniqueId);
        if (zone != null) {
            return zone.int_vars().values();
        }
        return null;
    }

    public @Nullable ZPZoneIntVar getZoneIntVariableByID(@NotNull Level level, @NotNull String uniqueId, @NotNull String variableId) {
        Zone zone = this.getZoneById(level, uniqueId);
        if (zone != null) {
            return zone.int_vars().get(variableId);
        }
        return null;
    }

    public boolean setZoneIntVariable(@NotNull ServerLevel level, @NotNull String uniqueId, @NotNull ZPZoneIntVar variable) {
        Zone zone = this.getZoneById(level, uniqueId);
        if (zone != null) {
            if (zone.int_vars().put(variable.getVariableId(), variable) == null) {
                return false;
            }
            ZPNetworkHandlerServer.sendZoneToAll(zone, level, false);
            this.writeToJSON(level);
            return true;
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void REPLACE_CLIENT_MAP(@NotNull ClientLevel level, @NotNull Collection<Zone> zones) {
        this.zonesPerLevelMap.remove(level);
        this.zonesPerLevelMap.put(level, new ZonesContainer().setIdAccessMap(zones));
        this.zonesPerLevelMap.get(level).buildFastPerChunkAccessMap(zones);
    }

    @OnlyIn(Dist.CLIENT)
    public void ADD_ZONE_IN_CLIENT_MAP(@NotNull ClientLevel level, @NotNull Zone zone) {
        if (!this.zonesPerLevelMap.containsKey(level)) {
            this.zonesPerLevelMap.put(level, new ZonesContainer());
        }
        if (this.zonesPerLevelMap.get(level).getIdAccessMap().put(zone.uniqueId(), zone) != null) {
            this.zonesPerLevelMap.get(level).buildFastPerChunkAccessMap(this.zonesPerLevelMap.get(level).getIdAccessMap().values());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void REMOVE_ZONE_FROM_CLIENT_MAP(@NotNull ClientLevel level, @NotNull String uniqueId) {
        if (this.zonesPerLevelMap.containsKey(level)) {
            if (this.zonesPerLevelMap.get(level).getIdAccessMap().remove(uniqueId) != null) {
                this.zonesPerLevelMap.get(level).buildFastPerChunkAccessMap(this.zonesPerLevelMap.get(level).getIdAccessMap().values());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public @Nullable ZonesContainer ZONES_CONTAINER(@NotNull ClientLevel level) {
        return this.zonesPerLevelMap.get(level);
    }

    public static Zone CREATE_DEFAULT_ZONE(@NotNull String uniqueId, @NotNull Vector3i start, @NotNull Vector3i end) {
        final Map<String, ZPZoneIntVar> intVarMap = new HashMap<>();
        ZPZonesRegistry.int_variablesStream().forEach(v -> intVarMap.put(v.getVariableId(), v));
        return new Zone(uniqueId, start, end, new HashSet<>(), intVarMap);
    }

    public record Zone(String uniqueId, Vector3i start, Vector3i end, Set<ZPZoneFlag> flags, Map<String, ZPZoneIntVar> int_vars) {
        public static Pair<Vector3f, Vector3f> min_max(@NotNull Vector3i start, @NotNull Vector3i end) {
            return min_max(new Vector3f(start), new Vector3f(end));
        }

        public static Pair<Vector3f, Vector3f> min_max(@NotNull Vector3f start, @NotNull Vector3f end) {
            final float minX = Math.min(start.x, end.x);
            final float maxX = Math.max(start.x, end.x);
            final float minY = Math.min(start.y, end.y);
            final float maxY = Math.max(start.y, end.y);
            final float minZ = Math.min(start.z, end.z);
            final float maxZ = Math.max(start.z, end.z);
            return Pair.of(new Vector3f(minX, minY, minZ), new Vector3f(maxX, maxY, maxZ));
        }

        @Override
        public String toString() {
            return "Zone{" +
                    "uniqueId='" + uniqueId + '\'' +
                    ", start=" + start +
                    ", end=" + end +
                    ", flags=" + flags +
                    ", int_vars=" + int_vars +
                    '}';
        }
    }

    public final static class ZonesContainer {
        private Map<String, Zone> idAccessMap;
        private final Map<Vector2i, List<Zone>> fast_ChunkLookupTable;

        public ZonesContainer() {
            this.idAccessMap = new HashMap<>();
            this.fast_ChunkLookupTable = new HashMap<>();
        }

        private void addFastPerChunkAccessMapOnZone(@NotNull Zone zone) {
            Pair<Vector3f, Vector3f> pair = ZPZoneManager.Zone.min_max(zone.start(), zone.end());
            final Vector3f min = pair.first();
            final Vector3f max = pair.second();
            ZPZoneManager.forEachChunkAABB(min, max).forEach((e -> {
                if (!this.fast_ChunkLookupTable.containsKey(e)) {
                    this.fast_ChunkLookupTable.put(e, new ArrayList<>());
                }
                this.fast_ChunkLookupTable.get(e).add(zone);
            }));
        }

        private void buildFastPerChunkAccessMap(@NotNull Collection<Zone> allZones) {
            this.fast_ChunkLookupTable.clear();
            allZones.forEach(this::addFastPerChunkAccessMapOnZone);
        }

        public ZonesContainer setIdAccessMap(@NotNull Collection<Zone> idAccessMap) {
            idAccessMap.forEach(e -> this.idAccessMap.put(e.uniqueId(), e));
            return this;
        }
        public ZonesContainer setIdAccessMap(@NotNull Map<String, Zone> idAccessMap) {
            this.idAccessMap = idAccessMap;
            return this;
        }

        public Map<String, Zone> getIdAccessMap() {
            return this.idAccessMap;
        }

        public Map<Vector2i, List<Zone>> getFast_ChunkLookupTable() {
            return this.fast_ChunkLookupTable;
        }
    }
}
