package ru.gltexture.zpm3.engine.helpers.gen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.helpers.ZPBlockTagsHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTagsHelper;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ZPItemTagsProvider extends ItemTagsProvider {
    public ZPItemTagsProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        for (Map.Entry<TagKey<Item>, Set<RegistryObject<? extends Item>>> entry : ZPItemTagsHelper.getTagsToAddItem().entrySet()) {
            for (RegistryObject<? extends Item> registryObject : entry.getValue()) {
                this.tag(entry.getKey()).add(registryObject.get());
            }
        }
        ZPItemTagsHelper.clear();
    }
}
