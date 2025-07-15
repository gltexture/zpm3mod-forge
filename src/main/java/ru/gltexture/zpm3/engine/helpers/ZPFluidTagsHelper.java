package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ZPFluidTagsHelper {
    private static final Map<TagKey<Fluid>, Set<RegistryObject<? extends Fluid>>> tagsToAddFluid = new HashMap<>();

    public static void addTagToFluid(@NotNull RegistryObject<? extends Fluid> registryObject, @NotNull TagKey<Fluid> tagKey) {
        if (!ZPFluidTagsHelper.tagsToAddFluid.containsKey(tagKey)) {
            ZPFluidTagsHelper.tagsToAddFluid.put(tagKey, new HashSet<>());
        }
        ZPFluidTagsHelper.tagsToAddFluid.get(tagKey).add(registryObject);
    }

    public static void clear() {
        ZPFluidTagsHelper.tagsToAddFluid.clear();
    }

    public static @NotNull Map<TagKey<Fluid>, Set<RegistryObject<? extends Fluid>>> getTagsToAddFluid() {
        return ZPFluidTagsHelper.tagsToAddFluid;
    }
}
