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

import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class ZPDispenseRegHelper {
    private static final Map<Supplier<ItemLike>, DefaultDispenseItemBehavior> dispenserMap = new HashMap<>();

    public static void addDispenserData(@NotNull Supplier<ItemLike> itemLikeSupplier, @NotNull DefaultDispenseItemBehavior dispenseItemBehavior) {
        ZPDispenseRegHelper.dispenserMap.put(itemLikeSupplier, dispenseItemBehavior);
    }

    public static Map<Supplier<ItemLike>, DefaultDispenseItemBehavior> getDispenserMap() {
        return ZPDispenseRegHelper.dispenserMap;
    }

    public static void clear() {
        ZPDispenseRegHelper.dispenserMap.clear();
    }
}