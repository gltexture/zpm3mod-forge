package ru.gltexture.zpm3.engine.zones;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.Collection;

public final class ZPZoneChecks {
    public static final ZPZoneChecks INSTANCE = new ZPZoneChecks();

    private ZPZoneChecks() {
    }

    public boolean isZombieErasing(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZoneFlag.zombieErasing);
    }

    public boolean isNoPlayersPvp(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZoneFlag.noPlayersPvp);
    }

    public boolean isNoPlayersDamage(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZoneFlag.noPlayersDamage);
    }

    public boolean isInRadLVL1(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZoneFlag.radiationLevel1);
    }

    public boolean isInRadLVL2(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZoneFlag.radiationLevel2);
    }

    public boolean isInAcidCloud(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZoneFlag.acidCloud);
    }

    public boolean isInToxicCloud(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZoneFlag.toxicCloud);
    }

    public boolean isNoBlocksDestruction(@NotNull Level level, @NotNull BlockPos pos) {
        return this.checkFlag(level, pos.getX(), pos.getY(), pos.getZ(), ZPZoneFlag.noBlocksDestruction);
    }

    public boolean isBarbaredWiresDisabled(@NotNull Level level, @NotNull BlockPos pos) {
        return this.checkFlag(level, pos.getX(), pos.getY(), pos.getZ(), ZPZoneFlag.disableBarbaredWires);
    }


    public boolean isZombieBlockSpawn(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZoneFlag.zombieSpawnBlocking);
    }

    public boolean isZombieBlockSpawn(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZoneFlag.zombieSpawnBlocking);
    }

    public boolean isNoToxicAffection(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZoneFlag.noAcidAffection);
    }

    public boolean isNoAcidAffection(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZoneFlag.noAcidAffection);
    }

    public boolean isNoAcidBlockDestruction(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZoneFlag.noAcidBlockDestruction);
    }

    public boolean isNoZombieMining(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZoneFlag.noZombieMining);
    }

    public boolean isNoThrowableBlockDamage(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZoneFlag.noThrowableBlockDamage);
    }

    public boolean isNoBulletBlockDmg(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZoneFlag.noBulletBlockDmg);
    }

    private boolean checkFlag(Level level, BlockPos blockPos, ZPZoneFlag flag) {
        Collection<ZPZoneManager.Zone> zones = ZPZoneManager.INSTANCE.getZonesInChunk(level, blockPos);
        if (zones == null) {
            return false;
        }
        for (ZPZoneManager.Zone zone : zones) {
            if (this.isInside(zone, blockPos)) {
                if (zone.flags().contains(flag)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkFlag(Level level, int x, int y, int z, ZPZoneFlag flag) {
        return this.checkFlag(level, new BlockPos(x, y, z), flag);
    }

    private boolean isInside(ZPZoneManager.Zone zone, BlockPos blockPos) {
        Pair<Vector3f, Vector3f> pair = ZPZoneManager.Zone.min_max(zone.start(), zone.end());
        final Vector3f min = pair.first();
        final Vector3f max = pair.second();
        return
                blockPos.getX() >= min.x &&
                blockPos.getX() <= max.x &&
                blockPos.getY() >= min.y &&
                blockPos.getY() <= max.y &&
                blockPos.getZ() >= min.z &&
                blockPos.getZ() <= max.z;
    }
}