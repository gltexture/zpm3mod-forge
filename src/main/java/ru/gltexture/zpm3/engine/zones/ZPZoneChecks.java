package ru.gltexture.zpm3.engine.zones;

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

    private boolean checkFlag(ServerLevel level, BlockPos blockPos, ZPFlagZones.Zone.AvailableFlags flag) {
        Collection<ZPFlagZones.Zone> zones = ZPFlagZones.INSTANCE.getZonesInChunk(level, blockPos);
        if (zones == null) {
            return false;
        }
        for (ZPFlagZones.Zone zone : zones) {
            if (this.isInside(zone, blockPos)) {
                if (zone.flags().contains(flag)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkFlag(ServerLevel level, int x, int y, int z, ZPFlagZones.Zone.AvailableFlags flag) {
        return this.checkFlag(level, new BlockPos(x, y, z), flag);
    }

    private boolean isInside(ZPFlagZones.Zone zone, BlockPos blockPos) {
        return
                blockPos.getX() >= zone.min().x &&
                blockPos.getX() <= zone.max().x &&
                blockPos.getY() >= zone.min().y &&
                blockPos.getY() <= zone.max().y &&
                blockPos.getZ() >= zone.min().z &&
                blockPos.getZ() <= zone.max().z;
    }
}