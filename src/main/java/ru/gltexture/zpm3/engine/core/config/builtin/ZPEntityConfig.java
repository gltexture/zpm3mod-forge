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

package ru.gltexture.zpm3.engine.core.config.builtin;

import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.vars.*;

public class ZPEntityConfig implements ZPConfigConstantsClass {

    @ZPVarDefinition(description = "Randomized poison effect, then fry food has been eaten.")
    public static final ZPConfig_FLOAT RANDOM_FRY_FOOD_POSIONING =
            new ZPConfig_FLOAT(1.0f);

    @ZPVarDefinition(description = "If enabled, only players can receive bleeding debuffs from attacks")
    public static final ZPConfig_BOOL BLEEDING_ONLY_FOR_PLAYERS =
            new ZPConfig_BOOL(false);

    @ZPVarDefinition(description = "Number of ticks entity AABB hitbox data is stored for anti-lag memory. Higher values improve lag compensation at the cost of memory.")
    public static final ZPConfig_INT ENTITY_MAX_AABB_MEMORY_ANTILAG =
            new ZPConfig_INT(60);

    @ZPVarDefinition(description = "Radiation point will be added on entity each N tick.")
    public static final ZPConfig_INT ADD_RAD_PER_TICK =
            new ZPConfig_INT(16);

    @ZPVarDefinition(description = "Acid point-factor will be added on entity each N tick.")
    public static final ZPConfig_INT ADD_ACID_FACTOR_PER_TICK =
            new ZPConfig_INT(4);

    @ZPVarDefinition(description = "Toxic point-factor will be added on entity each N tick.")
    public static final ZPConfig_INT ADD_TOXIC_FACTOR_PER_TICK =
            new ZPConfig_INT(4);

    @ZPVarDefinition(description = "Seasickness inc each N tick, if PLAYER underwater. (ONLY PLAYER). 0 = disable")
    public static final ZPConfig_INT ADD_SEASICKNESS_FACTOR_PER_TICK =
            new ZPConfig_INT(6);
}