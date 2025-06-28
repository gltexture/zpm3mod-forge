package ru.gltexture.zpm3.engine.helpers.gen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.helpers.ZPBlockTagsHelper;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ZPBlockTagsProvider extends BlockTagsProvider {
    public ZPBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        for (Map.Entry<TagKey<Block>, Set<RegistryObject<? extends Block>>> entry : ZPBlockTagsHelper.getTagsToAddBlock().entrySet()) {
            for (RegistryObject<? extends Block> registryObject : entry.getValue()) {
                this.tag(entry.getKey()).add(registryObject.get());
            }
        }
        ZPBlockTagsHelper.clear();
    }
}
