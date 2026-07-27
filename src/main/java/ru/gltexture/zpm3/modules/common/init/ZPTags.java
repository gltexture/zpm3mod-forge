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

package ru.gltexture.zpm3.modules.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public abstract class ZPTags {
    public static final TagKey<Block> B_ACTIVE_TORCH_BLOCK = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_active_torch"));
    public static final TagKey<Block> B_ACTIVE_TORCH_WALL_BLOCK = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_active_wall_torch"));

    public static final TagKey<Block> B_MINEABLE_WITH_METAL_CUTTERS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_mineable_with_mcutters"));
    public static final TagKey<Block> B_MINEABLE_WITH_WRENCH = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_mineable_with_wrench"));
    public static final TagKey<Block> B_MINEABLE_WITH_CROWBAR = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_mineable_with_crowbar"));
    public static final TagKey<Block> B_IGNORE_ACID = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_ignore_acid"));

    public static final TagKey<Block> B_BOOST_ZOMBIE_MINE_SPEED = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "b_boost_zombie_mine_speed"));

    public static final TagKey<Fluid> F_ACID_COSTUME_BREATHABLE = TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "f_acid_costume_breathable"));
    public static final TagKey<Fluid> F_AQUALUNG_COSTUME_BREATHABLE = TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "f_aqualung_breathable"));

    public static final TagKey<Fluid> F_ACID_PROPERTIES = TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "f_acid_properties"));
    public static final TagKey<Fluid> F_TOXIC_PROPERTIES = TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "f_toxic_properties"));

    public static final TagKey<Item> I_CAN_MINE_SCRAP = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_mineable_scrap"));
    public static final TagKey<Item> I_CAN_MINE_BARBARED_WIRE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_mineable_barbared_wire"));
    public static final TagKey<Item> I_CAN_MINE_CHAIN_LINK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_mineable_chani_link"));

    public static final TagKey<Item> I_AQUALUNG_O2_ITEM = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_aqualung_o2_item"));
    public static final TagKey<Item> I_ARMOR_AQUALUNG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_aqualung"));
    public static final TagKey<Item> I_ARMOR_ACID_PROTECTION = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_acid_protection"));
    public static final TagKey<Item> I_ARMOR_RADIOPROTECTION = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_radioprotection"));

    public static final TagKey<Item> I_ARMOR_VIGNETTE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_armor_vignette_screen"));

    public static final TagKey<Item> I_ARMOR_CAMO_FOREST = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_camo_forest"));
    public static final TagKey<Item> I_ARMOR_CAMO_WINTER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_camo_winter"));
    public static final TagKey<Item> I_ARMOR_CAMO_SAND = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "i_camo_sand"));

    public static final TagKey<Biome> FOREST_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "forest_biomes"));
    public static final TagKey<Biome> WINTER_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "winter_biomes"));
    public static final TagKey<Biome> SAND_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "sand_biomes"));
}