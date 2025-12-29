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
