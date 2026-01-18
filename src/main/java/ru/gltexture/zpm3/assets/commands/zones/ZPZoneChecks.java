package ru.gltexture.zpm3.assets.commands.zones;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

public final class ZPZoneChecks {
    public static final ZPZoneChecks INSTANCE = new ZPZoneChecks();

    private ZPZoneChecks() {
    }

    public boolean isZombieErasing(ServerLevel level, LivingEntity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPFlagZones.Zone.AvailableFlags.zombieErasing);
    }

    public boolean isNoPlayersPvp(ServerLevel level, LivingEntity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPFlagZones.Zone.AvailableFlags.noPlayersPvp);
    }

    public boolean isNoPlayersDamage(ServerLevel level, LivingEntity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPFlagZones.Zone.AvailableFlags.noPlayersDamage);
    }

    public boolean isNoBlocksDestruction(ServerLevel level, BlockPos pos) {
        return this.checkFlag(level, pos.getX(), pos.getY(), pos.getZ(), ZPFlagZones.Zone.AvailableFlags.noBlocksDestruction);
    }

    public boolean isBarbaredWiresDisabled(ServerLevel level, BlockPos pos) {
        return this.checkFlag(level, pos.getX(), pos.getY(), pos.getZ(), ZPFlagZones.Zone.AvailableFlags.disableBarbaredWires);
    }


    public boolean isZombieBlockSpawn(ServerLevel level, LivingEntity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPFlagZones.Zone.AvailableFlags.zombieSpawnBlocking);
    }

    public boolean isZombieBlockSpawn(ServerLevel level, BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPFlagZones.Zone.AvailableFlags.zombieSpawnBlocking);
    }

    public boolean isNoAcidAffection(ServerLevel level, BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPFlagZones.Zone.AvailableFlags.noAcidAffection);
    }

    public boolean isNoAcidBlockDestruction(ServerLevel level, BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPFlagZones.Zone.AvailableFlags.noAcidBlockDestruction);
    }

    public boolean isNoZombieMining(ServerLevel level, BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPFlagZones.Zone.AvailableFlags.noZombieMining);
    }

    public boolean isNoThrowableBlockDamage(ServerLevel level, BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPFlagZones.Zone.AvailableFlags.noThrowableBlockDamage);
    }

    public boolean isNoBulletBlockDmg(ServerLevel level, BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPFlagZones.Zone.AvailableFlags.noBulletBlockDmg);
    }

    private boolean checkFlag(ServerLevel level, int x, int y, int z, ZPFlagZones.Zone.AvailableFlags flag) {
        Collection<ZPFlagZones.Zone> zones = ZPFlagZones.INSTANCE.getZonesInfo(level);
        if (zones == null) {
            return false;
        }
        for (ZPFlagZones.Zone zone : zones) {
            final boolean flag1 = this.isInside(zone, x, y, z);
            final boolean flag2 = zone.flags().contains(flag);
            if (flag1 && flag2) {
                return true;
            }
        }
        return false;
    }

    private boolean isInside(ZPFlagZones.Zone zone, int x, int y, int z) {
        int minX = Math.min(zone.startX(), zone.endX());
        int maxX = Math.max(zone.startX(), zone.endX());
        int minY = Math.min(zone.startY(), zone.endY());
        int maxY = Math.max(zone.startY(), zone.endY());
        int minZ = Math.min(zone.startZ(), zone.endZ());
        int maxZ = Math.max(zone.startZ(), zone.endZ());
        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }
}