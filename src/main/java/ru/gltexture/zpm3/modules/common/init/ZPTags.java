package ru.gltexture.zpm3.modules.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public abstract class ZPTags {
    public static final TagKey<Block> B_ACTIVE_TORCH_BLOCK = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_active_torch"));
    public static final TagKey<Block> B_ACTIVE_TORCH_WALL_BLOCK = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_active_wall_torch"));

    public static final TagKey<Block> B_MINEABLE_WITH_METAL_CUTTERS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_mineable_with_mcutters"));
    public static final TagKey<Block> B_MINEABLE_WITH_WRENCH = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_mineable_with_wrench"));
    public static final TagKey<Block> B_MINEABLE_WITH_CROWBAR = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_mineable_with_vrowbar"));

    public static final TagKey<Item> I_CAN_MINE_SCRAP = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_mineable_scrap"));
    public static final TagKey<Item> I_CAN_MINE_BARBARED_WIRE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_mineable_barbared_wire"));

    public static final TagKey<Biome> FOREST_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "forest_biomes"));
    public static final TagKey<Biome> WINTER_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "winter_biomes"));
    public static final TagKey<Biome> SAND_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "sand_biomes"));
}