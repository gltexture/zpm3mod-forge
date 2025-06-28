package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class ZPBlockTagsHelper {
    private static final Map<TagKey<Block>, Set<RegistryObject<? extends Block>>> tagsToAddBlock = new HashMap<>();

    public static void addTagToBlock(@NotNull RegistryObject<? extends Block> registryObject, @NotNull TagKey<Block> tagKey) {
        if (!ZPBlockTagsHelper.tagsToAddBlock.containsKey(tagKey)) {
            ZPBlockTagsHelper.tagsToAddBlock.put(tagKey, new HashSet<>());
        }
        ZPBlockTagsHelper.tagsToAddBlock.get(tagKey).add(registryObject);
    }

    public static void clear() {
        ZPBlockTagsHelper.tagsToAddBlock.clear();
    }

    public static @NotNull Map<TagKey<Block>, Set<RegistryObject<? extends Block>>> getTagsToAddBlock() {
        return ZPBlockTagsHelper.tagsToAddBlock;
    }
}
