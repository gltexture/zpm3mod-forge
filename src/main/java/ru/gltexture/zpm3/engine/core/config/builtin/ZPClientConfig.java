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

public class ZPClientConfig implements ZPConfigConstantsClass {

    @ZPVarDefinition(description = "(CLIENT) Show version info on screen.")
    public static final ZPConfig_BOOL SHOW_VERSION_INFO_ON_SCREEN =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) Render muzzle flashes. Disabling may fix rendering issues with other mods.")
    public static final ZPConfig_BOOL RENDER_MUZZLE_FLASHES =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) Render bullet tracers.")
    public static final ZPConfig_BOOL RENDER_BULLET_TRACERS =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) Render armor layer on hands.")
    public static final ZPConfig_BOOL RENDER_ARMOR_LAYERS_ON_HANDS =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) Fancy ZP item-entity animation.")
    public static final ZPConfig_BOOL FANCY_ITEM_ENTITIES =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) First-person FOV scaling.")
    public static final ZPConfig_BOOL FIRST_PERSON_RENDER_SPACE_SCALE_BY_FOV =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) Armor looped sounds.")
    public static final ZPConfig_BOOL ARMOR_LOOPED_SOUNDS =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(description = "(CLIENT) First-person FOV scaling type: 0 = progressive (projection-based), 1 = static.")
    public static final ZPConfig_INT FIRST_PERSON_RENDER_SCALE_TYPE =
            new ZPConfig_INT(0, 0, 1);

   @ZPVarDefinition(description = "(CLIENT) TEST2.")
   //public static final ZPConfig_BOOL TEST2 =
   //        new ZPConfig_BOOL(true);

   @ZPVarDefinition(description = "(CLIENT) TEST4.")
   //public static final ZPConfig_BOOL TEST4 =
   //        new ZPConfig_BOOL(true);

   @ZPVarDefinition(description = "(CLIENT) TEST23.")
   //public static final ZPConfig_BOOL TEST3 =
   //        new ZPConfig_BOOL(true);
}