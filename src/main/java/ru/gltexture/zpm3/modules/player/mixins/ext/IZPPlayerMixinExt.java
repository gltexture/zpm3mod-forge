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

package ru.gltexture.zpm3.modules.player.mixins.ext;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.net_pack.data.ZPNetSyncDataPack;

public interface IZPPlayerMixinExt {
    ZPNetSyncDataPack zpm3forge$zpNetDataPack_fromClient();

    int zpm3forge$getSeasicknessLevel();
    void zpm3forge$setSeasicknessLevel(int level);

    void zpm3forge$defineZPSyncData();

    void zpm3forge$getResponseNetCheckFromServer();
    void zpm3forge$getResponseNetCheckFromClient();
    int zpm3forge$getPing();

    boolean zpm3forge$isLying();
    void zpm3forge$setLying(boolean value);

    static boolean checkIfPlayerCanLieOnGround(@NotNull Player player) {
       // if (!player.onGround()) {
       //     return false;
       // }
        if (player.getDeltaMovement().y > 0.01f) {
            return false;
        }
        if (player.isSwimming()) {
            return false;
        }
        if (player.isSleeping()) {
            return false;
        }
        if (player.isFallFlying()) {
            return false;
        }
        if (player.isPassenger()) {
            return false;
        }
        return player.getPose() == Pose.STANDING || player.getPose() == Pose.CROUCHING || player.getPose() == Pose.SWIMMING;
    }
}
