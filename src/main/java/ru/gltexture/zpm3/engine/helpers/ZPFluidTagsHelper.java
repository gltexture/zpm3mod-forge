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

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class ZPFluidTagsHelper {
    private static final Map<TagKey<Fluid>, Set<Supplier<Fluid>>> tagsToAddFluid = new HashMap<>();

    public static void addTagToFluid(@NotNull Supplier<Fluid> registryObject, @NotNull TagKey<Fluid> tagKey) {
        if (!ZPFluidTagsHelper.tagsToAddFluid.containsKey(tagKey)) {
            ZPFluidTagsHelper.tagsToAddFluid.put(tagKey, new HashSet<>());
        }
        ZPFluidTagsHelper.tagsToAddFluid.get(tagKey).add(registryObject);
    }

    public static void clear() {
        ZPFluidTagsHelper.tagsToAddFluid.clear();
    }

    public static @NotNull Map<TagKey<Fluid>, Set<Supplier<Fluid>>> getTagsToAddFluid() {
        return ZPFluidTagsHelper.tagsToAddFluid;
    }
}
