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

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.instances.ZPBlockItemsRegistry;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class ZPItemFuelsHelper {
    private static Set<Pair<RegistryObject<? extends Item>, Integer>> tempFuelsSet = new HashSet<>();
    private static Set<Pair<RegistryObject<? extends Block>, Integer>> tempFuelsSetBlocks = new HashSet<>();

    private static final Map<Item, Integer> fuels = new HashMap<>();

    public static void addTempItem(@NotNull Pair<RegistryObject<? extends Item>, Integer> integerPair) {
        if (ZPItemFuelsHelper.tempFuelsSet == null) {
            return;
        }
        ZPItemFuelsHelper.tempFuelsSet.add(integerPair);
    }

    public static void addTempBlock(@NotNull Pair<RegistryObject<? extends Block>, Integer> integerPair) {
        if (ZPItemFuelsHelper.tempFuelsSetBlocks == null) {
            return;
        }
        ZPItemFuelsHelper.tempFuelsSetBlocks.add(integerPair);
    }

    public static void convert() {
        ZPItemFuelsHelper.tempFuelsSet.forEach(e -> ZPItemFuelsHelper.fuels.put(e.first().get(), e.second()));
        ZPItemFuelsHelper.tempFuelsSetBlocks.forEach(e -> ZPItemFuelsHelper.fuels.put(ZPBlockItemsRegistry.getBlockItem(e.first()).get(), e.second()));
        ZPItemFuelsHelper.tempFuelsSet = null;
        ZPItemFuelsHelper.tempFuelsSetBlocks = null;
    }

    public static void clear() {
        ZPItemFuelsHelper.fuels.clear();
    }

    public static int getFuelTime(final Item fuelItem) {
        return ZPItemFuelsHelper.fuels.getOrDefault(fuelItem, -1);
    }
}
