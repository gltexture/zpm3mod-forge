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

package ru.gltexture.zpm3.engine.instances;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class ZPBlockItemsRegistry {
    private static final Map<@NotNull RegistryObject<? extends Block>, @NotNull RegistryObject<BlockItem>> registryObjectMap = new HashMap<>();

    public static RegistryObject<BlockItem> getBlockItem(@NotNull RegistryObject<? extends Block> registryObject) {
        return ZPBlockItemsRegistry.registryObjectMap.get(registryObject);
    }

    public static void putNewEntry(@NotNull RegistryObject<? extends Block> a, @NotNull RegistryObject<BlockItem> b) {
        ZPBlockItemsRegistry.registryObjectMap.put(a, b);
    }
}