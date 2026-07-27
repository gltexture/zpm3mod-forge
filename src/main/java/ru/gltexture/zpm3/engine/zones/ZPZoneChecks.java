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

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.zones.vars.ZPZoneIntVar;

import java.util.Collection;

public final class ZPZoneChecks {
    public static final ZPZoneChecks INSTANCE = new ZPZoneChecks();

    private ZPZoneChecks() {
    }

    public boolean isZombieErasing(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZonesRegistry.zombieErasing);
    }

    public boolean isNoPlayersPvp(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZonesRegistry.noPlayersPvp);
    }

    public boolean isNoPlayersDamage(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZonesRegistry.noPlayersDamage);
    }

    public boolean isInRadLVL1(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZonesRegistry.radiationLevel1);
    }

    public boolean isInRadLVL2(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZonesRegistry.radiationLevel2);
    }

    public boolean isInAcidCloud(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZonesRegistry.acidCloud);
    }

    public boolean isInToxicCloud(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZonesRegistry.toxicCloud);
    }

    public boolean isNoBlocksDestruction(@NotNull Level level, @NotNull BlockPos pos) {
        return this.checkFlag(level, pos.getX(), pos.getY(), pos.getZ(), ZPZonesRegistry.noBlocksDestruction);
    }

    public boolean isBarbaredWiresDisabled(@NotNull Level level, @NotNull BlockPos pos) {
        return this.checkFlag(level, pos.getX(), pos.getY(), pos.getZ(), ZPZonesRegistry.disableBarbaredWires);
    }


    public boolean isZombieBlockSpawn(@NotNull Level level, @NotNull Entity entity) {
        return this.checkFlag(level, Mth.floor(entity.getX()), Mth.floor(entity.getY()), Mth.floor(entity.getZ()), ZPZonesRegistry.zombieSpawnBlocking);
    }

    public boolean isZombieBlockSpawn(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZonesRegistry.zombieSpawnBlocking);
    }

    public boolean isNoToxicAffection(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZonesRegistry.noToxicAffection);
    }

    public boolean isNoRadiationAffection(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZonesRegistry.noRadiationAffection);
    }

    public boolean isNoAcidAffection(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZonesRegistry.noAcidAffection);
    }

    public boolean isNoAcidBlockDestruction(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZonesRegistry.noAcidBlockDestruction);
    }

    public boolean isNoZombieMining(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZonesRegistry.noZombieMining);
    }

    public boolean isNoThrowableBlockDamage(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZonesRegistry.noThrowableBlockDamage);
    }

    public boolean isNoBulletBlockDmg(@NotNull Level level, @NotNull BlockPos blockPos) {
        return this.checkFlag(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ZPZonesRegistry.noBulletBlockDmg);
    }

    public int getZombieScaleInt_ADDFUNC(@NotNull Level level, @NotNull BlockPos blockPos, int defaultValue) {
        return this.intVars_ADDFUNC(level, blockPos, ZPZonesRegistry.zombiesSpawnPercentageReduction, defaultValue);
    }

    private int intVars_ADDFUNC(Level level, BlockPos blockPos, @NotNull ZPZoneIntVar var, int defaultValue) {
        Collection<ZPZoneManager.Zone> zones = ZPZoneManager.INSTANCE.getZonesInChunk(level, blockPos);
        if (zones == null) {
            return defaultValue;
        }
        int i = 0;
        for (ZPZoneManager.Zone zone : zones) {
            if (zone.int_vars() != null && zone.int_vars().containsKey(var.getVariableId())) {
                if (this.isInside(zone, blockPos)) {
                    i += zone.int_vars().get(var.getVariableId()).getValue();
                }
            }
        }
        return i;
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