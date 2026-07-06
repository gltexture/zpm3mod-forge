package ru.gltexture.zpm3.engine.zones;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

public final class ZPZoneFlag {
    private final String id;

    private ZPZoneFlag(String id) {
        this.id = id;
    }

    private static final Map<String, ZPZoneFlag> SELF_REG_MAP = new LinkedHashMap<>();

    public static ZPZoneFlag register(@NotNull String id) {
        ZPZoneFlag zpZoneFlag = new ZPZoneFlag(id);
        ZPZoneFlag.SELF_REG_MAP.put(id, zpZoneFlag);
        return zpZoneFlag;
    }

    public static final ZPZoneFlag noPlayersPvp = ZPZoneFlag.register("noPlayersPvp");
    public static final ZPZoneFlag noPlayersDamage = ZPZoneFlag.register("noPlayersDamage");
    public static final ZPZoneFlag noBlocksDestruction = ZPZoneFlag.register("noBlocksDestruction");
    public static final ZPZoneFlag disableBarbaredWires = ZPZoneFlag.register("disableBarbaredWires");
    public static final ZPZoneFlag noAcidAffection = ZPZoneFlag.register("noAcidAffection");
    public static final ZPZoneFlag noToxicAffection = ZPZoneFlag.register("noToxicAffection");
    public static final ZPZoneFlag noAcidBlockDestruction = ZPZoneFlag.register("noAcidBlockDestruction");
    public static final ZPZoneFlag noZombieMining = ZPZoneFlag.register("noZombieMining");
    public static final ZPZoneFlag noThrowableBlockDamage = ZPZoneFlag.register("noThrowableBlockDamage");
    public static final ZPZoneFlag noBulletBlockDmg = ZPZoneFlag.register("noBulletBlockDmg");
    public static final ZPZoneFlag zombieErasing = ZPZoneFlag.register("zombieErasing");
    public static final ZPZoneFlag zombieSpawnBlocking = ZPZoneFlag.register("zombieSpawnBlocking");
    public static final ZPZoneFlag radiationLevel1 = ZPZoneFlag.register("radiationLevel1");
    public static final ZPZoneFlag radiationLevel2 = ZPZoneFlag.register("radiationLevel2");
    public static final ZPZoneFlag acidCloud = ZPZoneFlag.register("acidCloud");
    public static final ZPZoneFlag toxicCloud = ZPZoneFlag.register("toxicCloud");

    @Override
    public boolean equals(Object userObject) {
        if (!(userObject instanceof ZPZoneFlag that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String id() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id();
    }

    public static boolean exists(String id) {
        return ZPZoneFlag.SELF_REG_MAP.containsKey(id);
    }

    public static Collection<ZPZoneFlag> values() {
        return Collections.unmodifiableCollection(ZPZoneFlag.SELF_REG_MAP.values());
    }

    public static Stream<ZPZoneFlag> stream() {
        return ZPZoneFlag.SELF_REG_MAP.values().stream();
    }

    public static @Nullable ZPZoneFlag valueOf(String name) {
        return ZPZoneFlag.SELF_REG_MAP.get(name);
    }
}
