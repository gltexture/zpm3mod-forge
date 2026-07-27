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

public interface IZPLivingEntityExt {
    int zpm3forge$getRadiationLevel();
    void zpm3forge$setRadiationLevel(int radiationLevel);

    default void zpm3forge$addRadiationLevel(int amount) {
        this.zpm3forge$setRadiationLevel(this.zpm3forge$getRadiationLevel() + amount);
    }

    default void zpm3forge$decreaseRadiationLevel(int amount) {
        this.zpm3forge$setRadiationLevel(this.zpm3forge$getRadiationLevel() - amount);
    }

    int zpm3forge$getIntoxicationLevel();
    void zpm3forge$setIntoxicationLevel(int intoxicationLevel);
    void zpm3forge$setIntoxicationLevelForce(int intoxicationLevel);

    default void zpm3forge$addIntoxicationLevel(int intoxicationLevel) {
        this.zpm3forge$setIntoxicationLevel(this.zpm3forge$getIntoxicationLevel() + intoxicationLevel);
    }
  //  void zpm3forge$defineZPSyncData();
}