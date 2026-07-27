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
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class ZPItemTagsHelper {
    private static final Map<TagKey<Item>, Set<Supplier<Item>>> tagsToAddItem = new HashMap<>();

    public static void addTagToItem(@NotNull Supplier<Item> registryObject, @NotNull TagKey<Item> tagKey) {
        if (!ZPItemTagsHelper.tagsToAddItem.containsKey(tagKey)) {
            ZPItemTagsHelper.tagsToAddItem.put(tagKey, new HashSet<>());
        }
        ZPItemTagsHelper.tagsToAddItem.get(tagKey).add(registryObject);
    }

    public static void clear() {
        ZPItemTagsHelper.tagsToAddItem.clear();
    }

    public static @NotNull Map<TagKey<Item>, Set<Supplier<Item>>> getTagsToAddItem() {
        return ZPItemTagsHelper.tagsToAddItem;
    }
}
