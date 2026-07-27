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

package ru.gltexture.zpm3.modules.melee_throwables_tools.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class ZPDefaultItemsHandReach {
    private static final Map<Class<?>, Float> itemsHandReachBonusC = new HashMap<>();
    private static final Map<String, Float> itemsHandReachBonusI = new HashMap<>();

    //DEFAULT
    static {
        ZPDefaultItemsHandReach.SET(AxeItem.class, 0.1f);
        ZPDefaultItemsHandReach.SET(PickaxeItem.class, -0.1f);
        ZPDefaultItemsHandReach.SET(ShovelItem.class, 0.2f);
    }

    // o == Class || o == <Item>
    public static void SET(@NotNull Object o, Float f) {
        if (o instanceof Class<?> c) {
            ZPDefaultItemsHandReach.itemsHandReachBonusC.put(c, f);
        } else if (o instanceof ResourceLocation i) {
            ZPDefaultItemsHandReach.itemsHandReachBonusI.put(i.getPath(), f);
        }
    }

    public static float get(@Nullable Item o) {
        if (o == null) {
            return 0.0f;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(o);
        if (id != null && ZPDefaultItemsHandReach.itemsHandReachBonusI.containsKey(id.getPath())) {
            return ZPDefaultItemsHandReach.itemsHandReachBonusI.get(id.getPath());
        }
        return ZPDefaultItemsHandReach.itemsHandReachBonusC.entrySet().stream()
                .filter(e -> e.getKey().isAssignableFrom(o.getClass()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(0.0f);
    }
}