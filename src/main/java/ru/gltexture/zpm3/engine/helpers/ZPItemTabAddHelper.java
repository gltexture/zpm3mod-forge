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

package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class ZPItemTabAddHelper {
    private static final Map<RegistryObject<CreativeModeTab>, Set<RegistryObject<? extends Item>>> itemMap = new HashMap<>();

    public static void addItemInTab(@NotNull RegistryObject<? extends Item> item, @NotNull RegistryObject<CreativeModeTab> creativeModeTab) {
        ZPItemTabAddHelper.itemMap.computeIfAbsent(creativeModeTab, k -> new LinkedHashSet<>()).add(item);
    }

    public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
        for (Map.Entry<RegistryObject<CreativeModeTab>, Set<RegistryObject<? extends Item>>> entry : ZPItemTabAddHelper.getItemMap().entrySet()) {
            if (entry.getKey() != null && entry.getKey().isPresent() && entry.getKey().get() == event.getTab()) {
                for (RegistryObject<? extends Item> item : entry.getValue()) {
                    event.accept(item.get());
                }
            }
        }
    }

    public static Map<RegistryObject<CreativeModeTab>, Set<RegistryObject<? extends Item>>> getItemMap() {
        return ZPItemTabAddHelper.itemMap;
    }
}