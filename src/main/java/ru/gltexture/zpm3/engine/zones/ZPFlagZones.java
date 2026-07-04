package ru.gltexture.zpm3.engine.zones;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.joml.Vector3i;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.modules.net_pack.data.ZPClientZonesHelper;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public final class ZPFlagZones {
    private static final String jsonName = "zp_zones.json";
    private final Map<Level, ZonesContainer> zonesPerLevelMap;
    public static ZPFlagZones INSTANCE = new ZPFlagZones();

    private ZPFlagZones() {
        this.zonesPerLevelMap = new HashMap<>();
    }

    private static Vector2i blockChunk(BlockPos pos) {
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;
        return new Vector2i(chunkX, chunkZ);
    }

    private static List<Vector2i> forEachChunkAABB(Vector3i min, Vector3i max) {
        List<Vector2i> vector2is = new ArrayList<>();
        int minChunkX = Mth.floor(min.x) >> 4;
        int maxChunkX = Mth.floor(max.x) >> 4;

        int minChunkZ = Mth.floor(min.z) >> 4;
        int maxChunkZ = Mth.floor(max.z) >> 4;

        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
            for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                vector2is.add(new Vector2i(cx, cz));
            }
        }

        return vector2is;
    }

    private File getWorldSaveDir(ServerLevel level) {
        File f = new File(level.getServer().getWorldPath(LevelResource.ROOT).toFile(), level.dimensionTypeId().location().getPath());
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;
    }

    @SuppressWarnings("all")
    public void writeToJSON(ServerLevel level) {
        File file = new File(this.getWorldSaveDir(level), ZPFlagZones.jsonName);
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
    public void loadFromJSON(ServerLevel level) {
        File file = new File(this.getWorldSaveDir(level), ZPFlagZones.jsonName);
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
            final HashMap<String, ZPFlagZones.Zone> hashMap = gson.fromJson(reader, type);
            if (hashMap != null) {
                this.zonesPerLevelMap.remove(level);
                this.zonesPerLevelMap.put(level, new ZonesContainer().setIdAccessMap(hashMap));
                this.zonesPerLevelMap.get(level).buildFastPerChunkAccessMap(hashMap);
            }
            ZPLogger.info("Read: " + file.toString());
        } catch (IOException e) {
            throw new ZPIOException("Couldn't write zones json", e);
        }
    }

    public void newZoneBounds(ServerLevel level, String uniqueId, Vector3i min, Vector3i max) {
        this.zonesPerLevelMap.computeIfAbsent(level, k -> new ZonesContainer());
        final Zone zone = this.zonesPerLevelMap.get(level).getIdAccessMap().get(uniqueId);
        zone.min().set(min);
        zone.max().set(max);
        this.zonesPerLevelMap.get(level).buildFastPerChunkAccessMap(this.zonesPerLevelMap.get(level).getIdAccessMap());
        ZPClientZonesHelper.sendZoneToAll(zone, level, false);
        this.writeToJSON(level);
    }

    public void addNewZone(ServerLevel level, Zone zone) {
        this.zonesPerLevelMap.computeIfAbsent(level, k -> new ZonesContainer());
        this.zonesPerLevelMap.get(level).getIdAccessMap().put(zone.uniqueId(), zone);
        this.zonesPerLevelMap.get(level).buildFastPerChunkAccessMap(this.zonesPerLevelMap.get(level).getIdAccessMap());
        ZPClientZonesHelper.sendZoneToAll(zone, level, false);
        this.writeToJSON(level);
    }

    public @Nullable Collection<Zone> getZonesInChunk(ServerLevel level, BlockPos pos) {
        Vector2i chunkId = ZPFlagZones.blockChunk(pos);
        if (this.zonesPerLevelMap.containsKey(level) && this.zonesPerLevelMap.get(level) != null) {
            return this.zonesPerLevelMap.get(level).getFastPerChunkAccessMap().get(chunkId);
        }
        return null;
    }

    public boolean removeZone(ServerLevel level, String uniqueId) {
        final Map<String, Zone> flagsMap = this.zonesPerLevelMap.get(level).getIdAccessMap();
        if (this.zonesPerLevelMap.containsKey(level) && flagsMap.containsKey(uniqueId)) {
            final Zone zone = flagsMap.remove(uniqueId);
            this.zonesPerLevelMap.get(level).buildFastPerChunkAccessMap(flagsMap);
            ZPClientZonesHelper.sendZoneToAll(zone, level, true);
            this.writeToJSON(level);
            return true;
        }
        return false;
    }

    public @Nullable Collection<Zone> getAllZonesOnLevel(ServerLevel level) {
        final Map<String, Zone> flagsMap = this.zonesPerLevelMap.get(level).getIdAccessMap();
        if (flagsMap == null) {
            return null;
        }
        return flagsMap.values();
    }

    public boolean replaceFlags(ServerLevel level, String uniqueId, Set<Zone.AvailableFlags> flags) {
        Zone zone = this.getZoneById(level, uniqueId);
        if (zone != null) {
            zone.flags().clear();
            zone.flags().addAll(flags);
            ZPClientZonesHelper.sendZoneToAll(zone, level, false);
            this.writeToJSON(level);
            return true;
        }
        return false;
    }

    public @Nullable Zone getZoneById(ServerLevel level, String id) {
        final Map<String, Zone> flagsMap = this.zonesPerLevelMap.get(level).getIdAccessMap();
        if (flagsMap == null || !flagsMap.containsKey(id)) {
            return null;
        }
        return flagsMap.get(id);
    }

    public @Nullable Set<Zone.AvailableFlags> getFlags(ServerLevel level, String uniqueId) {
        Zone zone = this.getZoneById(level, uniqueId);
        if (zone != null) {
            return zone.flags();
        }
        return null;
    }

    public record Zone(String uniqueId, Vector3i min, Vector3i max, Set<AvailableFlags> flags) {
        public enum AvailableFlags {
            noPlayersPvp,
            noPlayersDamage,
            noBlocksDestruction,
            disableBarbaredWires,

            noAcidAffection,
            noAcidBlockDestruction,
            noZombieMining,
            noThrowableBlockDamage,
            noBulletBlockDmg,
            zombieErasing,
            zombieSpawnBlocking
        }
    }

    public static class ZonesContainer {
        private Map<String, Zone> idAccessMap;
        private final Map<Vector2i, List<Zone>> fastPerChunkAccessMap;

        public ZonesContainer() {
            this.idAccessMap = new HashMap<>();
            this.fastPerChunkAccessMap = new HashMap<>();
        }

        private void addFastPerChunkAccessMapOnZone(Zone zone) {
            ZPFlagZones.forEachChunkAABB(zone.min(), zone.max()).forEach((e -> {
                if (!this.fastPerChunkAccessMap.containsKey(e)) {
                    this.fastPerChunkAccessMap.put(e, new ArrayList<>());
                }
                this.fastPerChunkAccessMap.get(e).add(zone);
            }));
        }

        private void buildFastPerChunkAccessMap(Map<String, Zone> allZones) {
            this.fastPerChunkAccessMap.clear();
            allZones.forEach((key, zone) -> {
                this.addFastPerChunkAccessMapOnZone(zone);
            });
        }

        public ZonesContainer setIdAccessMap(Map<String, Zone> idAccessMap) {
            this.idAccessMap = idAccessMap;
            return this;
        }

        public Map<String, Zone> getIdAccessMap() {
            return this.idAccessMap;
        }

        public Map<Vector2i, List<Zone>> getFastPerChunkAccessMap() {
            return this.fastPerChunkAccessMap;
        }
    }
}
