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

package ru.gltexture.zpm3.modules.entity.mixins.ext;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;

import java.util.Deque;
import java.util.Objects;

public interface IZPEntityExt {
    int zpm3forge$getAcidLevel();
    void zpm3forge$setAcidLevel(int acidLevel);

    void zpm3forge$defineZPSyncData();

    Deque<Snapshot> zpm3forge$getAabbDeque();

    default AABB getAABBWithLagCompensation(@NotNull Entity entity, @NotNull ServerPlayer serverPlayer) {
        int ping;
        if (serverPlayer instanceof IZPPlayerMixinExt ext) {
            ping = ext.zpm3forge$getPing();
        } else {
            ping = serverPlayer.connection.getPlayer().latency;
        }
        long targetTime = System.currentTimeMillis() - ping / 2L;
        for (Snapshot s : this.zpm3forge$getAabbDeque()) {
            if (s.timeMillis <= targetTime) {
                return s.box();
            }
        }
        return this.zpm3forge$getAabbDeque().isEmpty() ? entity.getBoundingBox() : Objects.requireNonNull(this.zpm3forge$getAabbDeque().peekLast()).box();
    }

    default void addAcidLevel(int acidLevel) {
        this.zpm3forge$setAcidLevel(this.zpm3forge$getAcidLevel() + acidLevel);
    }

    record Snapshot(long timeMillis, AABB box) {}
}
