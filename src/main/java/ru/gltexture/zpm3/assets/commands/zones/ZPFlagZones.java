package ru.gltexture.zpm3.assets.commands.zones;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public final class ZPFlagZones {
    private static final String jsonName = "zp_zones.json";
    private final Map<Level, HashMap<String, Zone>> map;
    public static ZPFlagZones INSTANCE = new ZPFlagZones();

    private ZPFlagZones() {
        this.map = new HashMap<>();
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
            if (this.map.containsKey(level) && this.map.get(level) != null) {
                gson.toJson(this.map.get(level), writer);
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
                this.map.remove(level);
                this.map.put(level, hashMap);
            }
            ZPLogger.info("Read: " + file.toString());
        } catch (IOException e) {
            throw new ZPIOException("Couldn't write zones json", e);
        }
    }

    public boolean addNewZone(ServerLevel level, Zone zone) {
        this.map.computeIfAbsent(level, k -> new HashMap<>());
        this.map.get(level).put(zone.uniqueId, zone);
        this.writeToJSON(level);
        return true;
    }

    public boolean removeZone(ServerLevel level, String uniqueId) {
        final HashMap<String, Zone> flagsMap = this.map.get(level);
        if (flagsMap == null || !flagsMap.containsKey(uniqueId)) {
            return false;
        }
        this.writeToJSON(level);
        return flagsMap.remove(uniqueId) != null;
    }

    public Collection<Zone> getZonesInfo(ServerLevel level) {
        final HashMap<String, Zone> flagsMap = this.map.get(level);
        if (flagsMap == null) {
            return null;
        }
        return flagsMap.values();
    }

    public boolean replaceFlags(ServerLevel level, String uniqueId, Set<Zone.AvailableFlags> flags) {
        final HashMap<String, Zone> flagsMap = this.map.get(level);
        if (flagsMap == null || !flagsMap.containsKey(uniqueId)) {
            return false;
        }
        flagsMap.get(uniqueId).flags.clear();
        flagsMap.get(uniqueId).flags.addAll(flags);
        this.writeToJSON(level);
        return true;
    }

    public Set<Zone.AvailableFlags> getFlags(ServerLevel level, String uniqueId) {
        final HashMap<String, Zone> flagsMap = this.map.get(level);
        if (flagsMap == null || !flagsMap.containsKey(uniqueId)) {
            return null;
        }
        return flagsMap.get(uniqueId).flags;
    }

    public record Zone(String uniqueId, int startX, int startY, int startZ, int endX, int endY, int endZ, Set<AvailableFlags> flags) {
        public enum AvailableFlags {
            noAcidAffection,
            noAcidBlockDestruction,
            noZombieMining,
            noThrowableBlockDamage,
            noBulletBlockDmg,
            zombieErasing,
            zombieSpawnBlocking
        }
    }
}
