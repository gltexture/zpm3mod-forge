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

package ru.gltexture.zpm3.engine.nbt;

import java.util.ArrayList;
import java.util.List;

public record ZPTagID(String id) {
    // ITEMS
    public static ZPTagID GUN_SHOOT_COOLDOWN_TAG = new ZPTagID("GUN_SHOOT_COOLDOWN_TAG");
    public static ZPTagID GUN_RELOAD_COOLDOWN_TAG = new ZPTagID("GUN_RELOAD_COOLDOWN_TAG");
    public static ZPTagID GUN_AMMO_INSIDE_TAG = new ZPTagID("GUN_AMMO_INSIDE_TAG");
    public static ZPTagID GUN_IS_UNLOADING_TAG = new ZPTagID("GUN_IS_UNLOADING_TAG");
    public static ZPTagID GUN_IS_RELOADING_TAG = new ZPTagID("GUN_IS_RELOADING_TAG");
    public static ZPTagID GUN_IS_JAMMED_TAG = new ZPTagID("GUN_IS_JAMMED_TAG");
    public static ZPTagID GUN_AMMO_BEFORE_RELOAD = new ZPTagID("GUN_CL_AMMO_BEFORE_RELOAD");

    public static ZPTagID GUN_CL_TIME_BEFORE_SHOOT = new ZPTagID("GUN_CL_TIME_BEFORE_SHOOT");
    public static ZPTagID GUN_CL_TIME_BEFORE_RELOAD = new ZPTagID("GUN_CL_TIME_BEFORE_RELOAD");
    public static ZPTagID GUN_CL_SYNC_COOLDOWN = new ZPTagID("GUN_CL_SYNC");

    // ENTITIES
    public static List<ZPTagID> ENTITY_TAGS_TO_DECREMENT_EACH_TICK = new ArrayList<>();
}
