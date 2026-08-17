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

import ru.gltexture.zpm3.engine.zones.vars.ZPZoneIntVar;

public abstract class ZPDefaultZones {
    // DEFAULT
    public static final ZPZoneFlag noPlayersPvp = ZPZonesRegistry.RegisterFlag("noPlayersPvp");
    public static final ZPZoneFlag noPlayersDamage = ZPZonesRegistry.RegisterFlag("noPlayersDamage");
    public static final ZPZoneFlag noBlocksDestruction = ZPZonesRegistry.RegisterFlag("noBlocksDestruction");
    public static final ZPZoneFlag disableBarbaredWires = ZPZonesRegistry.RegisterFlag("disableBarbaredWires");
    public static final ZPZoneFlag noAcidAffection = ZPZonesRegistry.RegisterFlag("noAcidAffection");
    public static final ZPZoneFlag noToxicAffection = ZPZonesRegistry.RegisterFlag("noToxicAffection");
    public static final ZPZoneFlag noRadiationAffection = ZPZonesRegistry.RegisterFlag("noRadiationAffection");
    public static final ZPZoneFlag noAcidBlockDestruction = ZPZonesRegistry.RegisterFlag("noAcidBlockDestruction");
    public static final ZPZoneFlag noZombieMining = ZPZonesRegistry.RegisterFlag("noZombieMining");
    public static final ZPZoneFlag noThrowableBlockDamage = ZPZonesRegistry.RegisterFlag("noThrowableBlockDamage");
    public static final ZPZoneFlag noBulletBlockDmg = ZPZonesRegistry.RegisterFlag("noBulletBlockDmg");
    public static final ZPZoneFlag zombieErasing = ZPZonesRegistry.RegisterFlag("zombieErasing");
    public static final ZPZoneFlag zombieSpawnBlocking = ZPZonesRegistry.RegisterFlag("zombieSpawnBlocking");
    public static final ZPZoneFlag radiationLevel1 = ZPZonesRegistry.RegisterFlag("radiationLevel1");
    public static final ZPZoneFlag radiationLevel2 = ZPZonesRegistry.RegisterFlag("radiationLevel2");
    public static final ZPZoneFlag acidCloud = ZPZonesRegistry.RegisterFlag("acidCloud");
    public static final ZPZoneFlag toxicCloud = ZPZonesRegistry.RegisterFlag("toxicCloud");
    public static final ZPZoneIntVar zombiesSpawnPercentageReduction = ZPZonesRegistry.RegisterIntVar("zombiesSpawnPercentageReduction", 0, 0, 100);
}
