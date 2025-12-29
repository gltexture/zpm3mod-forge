package ru.gltexture.zpm3.engine.helpers.gen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.helpers.ZPBlockTagsHelper;
import ru.gltexture.zpm3.engine.helpers.ZPFluidTagsHelper;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ZPFluidTagsProvider extends FluidTagsProvider {
    public ZPFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        for (Map.Entry<TagKey<Fluid>, Set<Supplier<Fluid>>> entry : ZPFluidTagsHelper.getTagsToAddFluid().entrySet()) {
            for (Supplier<Fluid> registryObject : entry.getValue()) {
                this.tag(entry.getKey()).add(registryObject.get());
            }
        }
        ZPFluidTagsHelper.clear();
    }
}
