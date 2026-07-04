package ru.gltexture.zpm3.modules.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public abstract class ZPTags {
    public static final TagKey<Block> B_MINEABLE_WITH_WRENCH = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "bmineablewithwrench"));
    public static final TagKey<Item> I_MINEABLE_WITH_WRENCH = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "imineablewithwrench"));
    public static final TagKey<Biome> FOREST_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "forest_biomes"));
    public static final TagKey<Biome> WINTER_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "winter_biomes"));
    public static final TagKey<Biome> SAND_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "sand_biomes"));
}