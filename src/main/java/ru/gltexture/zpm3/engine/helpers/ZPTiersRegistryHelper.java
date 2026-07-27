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

import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.instances.items.tier.ZPTier;

import java.util.HashSet;
import java.util.Set;

public abstract class ZPTiersRegistryHelper {
    public static Set<ZPTier[]> tierSet = new HashSet<>();

    public static void addToRegister(@NotNull ZPTier[] tier) {
        ZPTiersRegistryHelper.tierSet.add(tier);
    }

    public static void clear() {
        ZPTiersRegistryHelper.tierSet = null;
    }
}
