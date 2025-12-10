package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public abstract class ZPTags {
    public static final TagKey<Block> B_MINEABLE_WITH_WRENCH = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "bmineablewithwrench"));
    public static final TagKey<Item> I_MINEABLE_WITH_WRENCH = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "imineablewithwrench"));
}
