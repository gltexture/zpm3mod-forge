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

package ru.gltexture.zpm3.modules.blocks;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePostInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePreInitContext;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModule;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModuleData;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.recipes.IZPRecipeSpec;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.blocks.init.*;
import ru.gltexture.zpm3.modules.common.init.ZPTags;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;
import ru.gltexture.zpm3.modules.debug.events.ZPRenderStuffEvent;

import java.util.*;

public class ZPBlocksModule extends ZPModule {
    public ZPBlocksModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPBlocksModule() {
    }

    @Override
    public void commonSetup() {
        {
            Blocks.CYAN_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.WHITE_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.ORANGE_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.MAGENTA_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.LIGHT_BLUE_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.YELLOW_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.LIME_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.PINK_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.GRAY_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.LIGHT_GRAY_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.CYAN_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.PURPLE_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.BLUE_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.BROWN_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.GREEN_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.RED_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.BLACK_CONCRETE.explosionResistance = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.OBSIDIAN.explosionResistance = 4.0f;

            Blocks.CYAN_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.WHITE_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.ORANGE_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.MAGENTA_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.YELLOW_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.LIME_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.PINK_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.GRAY_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.CYAN_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.PURPLE_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.BLUE_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.BROWN_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.GREEN_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.RED_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.BLACK_CONCRETE.defaultBlockState().destroySpeed = ZPWorldConfig.ZP_VANILLA_CONCRETE_DESTROY_SPEED.getVar();
            Blocks.OBSIDIAN.defaultBlockState().destroySpeed = 6.0f;

            Blocks.BRICK_WALL.getStateDefinition().getPossibleStates().forEach((e) -> e.destroySpeed = 12.0F);
            Blocks.BRICK_WALL.explosionResistance = 8.0f;

            Blocks.BRICK_STAIRS.getStateDefinition().getPossibleStates().forEach((e) -> e.destroySpeed = 12.0F);
            Blocks.BRICK_STAIRS.explosionResistance = 8.0f;

            Blocks.BRICK_SLAB.getStateDefinition().getPossibleStates().forEach((e) -> e.destroySpeed = 12.0F);
            Blocks.BRICK_SLAB.explosionResistance = 8.0f;

            Blocks.BRICKS.defaultBlockState().destroySpeed = 12.0F;
            Blocks.BRICKS.explosionResistance = 8.0f;

            Blocks.IRON_BLOCK.explosionResistance = 9.0f;
            Blocks.IRON_DOOR.explosionResistance = 16.0f;

            Blocks.IRON_BARS.getStateDefinition().getPossibleStates().forEach((e) -> e.destroySpeed = 12.0F);
            Blocks.IRON_BARS.explosionResistance = 8.0f;
        }
    }

    @Override
    public void commonShutdown() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup(@NotNull IModuleClientSetupContext context) {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {
    }

    public static void addNewLineToDraw(@NotNull ZPRenderStuffEvent.LineRequest lineRequest) {
        ZPRenderStuffEvent.addNewLineToDraw(lineRequest);
    }

    @Override
    public void initialize(@NotNull IModuleInitContext context) {
        for (Block b : new Block[] {
                Blocks.CYAN_CONCRETE,
                Blocks.WHITE_CONCRETE,
                Blocks.ORANGE_CONCRETE,
                Blocks.MAGENTA_CONCRETE,
                Blocks.LIGHT_BLUE_CONCRETE,
                Blocks.YELLOW_CONCRETE,
                Blocks.LIME_CONCRETE,
                Blocks.PINK_CONCRETE,
                Blocks.GRAY_CONCRETE,
                Blocks.LIGHT_GRAY_CONCRETE,
                Blocks.CYAN_CONCRETE,
                Blocks.PURPLE_CONCRETE,
                Blocks.BLUE_CONCRETE,
                Blocks.BROWN_CONCRETE,
                Blocks.GREEN_CONCRETE,
                Blocks.RED_CONCRETE,
                Blocks.BLACK_CONCRETE
        })
        {
            ZPDataGenHelper.addBlockLootTable(() -> b, () -> new LootPool.Builder()
                    .add(LootItem.lootTableItem(Blocks.COBBLESTONE))
                    .when(ExplosionCondition.survivesExplosion())
            );
            ZPDataGenHelper.addBlockLootTable(() -> Blocks.CAMPFIRE, () -> new LootPool.Builder()
                    .add(LootItem.lootTableItem(Items.STICK))
                    .setRolls(UniformGenerator.between(8, 16))
                    .when(ExplosionCondition.survivesExplosion())
            );
            ZPDataGenHelper.addBlockLootTable(() -> Blocks.CRAFTING_TABLE, () -> new LootPool.Builder()
                    .add(AlternativesEntry.alternatives(
                                    LootItem.lootTableItem(Blocks.CRAFTING_TABLE).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ZPTags.I_CAN_MINE_CRAFTING_TABLE))),
                                    LootItem.lootTableItem(ZPBlocks.cracked_crafting_table.get()).when(ExplosionCondition.survivesExplosion())
                            )
                    )
            );
            ZPDataGenHelper.addTagToBlock(() -> Blocks.CRAFTING_TABLE, ZPTags.B_MINEABLE_WITH_WRENCH);
        }

        context.addRecipesRegistry(new ZPBlocksRecipeRegistry());
        context.addCommonZp3RegistryClass(ZPBlockItems.class);
        context.addCommonZp3RegistryClass(ZPBlocks.class);
        context.addCommonZp3RegistryClass(ZPTorchBlocks.class);
        context.addCommonZp3RegistryClass(ZPLanternBlocks.class);
        context.addCommonZp3RegistryClass(ZPCampfireBlocks.class);
        context.addCommonZp3RegistryClass(ZPBlockEntities.class);
        ZPUtility.sides().onlyClient(() -> {
        });

        ZPDataGenHelper.addTagToBlock(() -> Blocks.LANTERN, ZPTags.B_CRASH_BY_BULLET);
        ZPDataGenHelper.addTagToBlock(() -> Blocks.IRON_BARS, ZPTags.B_BULLET_50PRC_IGNORE);
    }

    @Override
    public void preInitialize(@NotNull IModulePreInitContext context) {
    }

    @Override
    public void postInitialize(@NotNull IModulePostInitContext context) {

    }

    private static class ZPBlocksRecipeRegistry extends ZPRecipesRegistry {
        private static final List<IZPRecipeSpec> recipeToAdd = new ArrayList<>();
        private static final List<ZPRecipesController.RecipeToRemove> toRemove = new ArrayList<>();

        private static final Map<String, Item> dyeMap = new HashMap<>() {{
            put("white", Items.WHITE_DYE);
            put("orange", Items.ORANGE_DYE);
            put("magenta", Items.MAGENTA_DYE);
            put("light_blue", Items.LIGHT_BLUE_DYE);
            put("yellow", Items.YELLOW_DYE);
            put("lime", Items.LIME_DYE);
            put("pink", Items.PINK_DYE);
            put("gray", Items.GRAY_DYE);
            put("light_gray", Items.LIGHT_GRAY_DYE);
            put("cyan", Items.CYAN_DYE);
            put("purple", Items.PURPLE_DYE);
            put("blue", Items.BLUE_DYE);
            put("brown", Items.BROWN_DYE);
            put("green", Items.GREEN_DYE);
            put("red", Items.RED_DYE);
            put("black", Items.BLACK_DYE);
        }};

        private static final Map<String, Block> concretePowderMap = new HashMap<>() {{
            put("white", Blocks.WHITE_CONCRETE_POWDER);
            put("orange", Blocks.ORANGE_CONCRETE_POWDER);
            put("magenta", Blocks.MAGENTA_CONCRETE_POWDER);
            put("light_blue", Blocks.LIGHT_BLUE_CONCRETE_POWDER);
            put("yellow", Blocks.YELLOW_CONCRETE_POWDER);
            put("lime", Blocks.LIME_CONCRETE_POWDER);
            put("pink", Blocks.PINK_CONCRETE_POWDER);
            put("gray", Blocks.GRAY_CONCRETE_POWDER);
            put("light_gray", Blocks.LIGHT_GRAY_CONCRETE_POWDER);
            put("cyan", Blocks.CYAN_CONCRETE_POWDER);
            put("purple", Blocks.PURPLE_CONCRETE_POWDER);
            put("blue", Blocks.BLUE_CONCRETE_POWDER);
            put("brown", Blocks.BROWN_CONCRETE_POWDER);
            put("green", Blocks.GREEN_CONCRETE_POWDER);
            put("red", Blocks.RED_CONCRETE_POWDER);
            put("black", Blocks.BLACK_CONCRETE_POWDER);
        }};

        static {
            toRemove.add(new ZPRecipesController.RecipeToRemove(RecipeType.CRAFTING, ResourceLocation.fromNamespaceAndPath("minecraft", "crafting_table")));

            for (DyeColor color : DyeColor.values()) {
                toRemove.add(new ZPRecipesController.RecipeToRemove(
                        RecipeType.CRAFTING,
                        ResourceLocation.fromNamespaceAndPath("minecraft", color.getName() + "_concrete_powder")
                ));
            }

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.CRAFTING_TABLE.asItem())
                        .pattern("CL").pattern("SS")
                        .define('C', ZPMiscItems.chisel_material.get())
                        .define('S', ZPMiscItems.shelves_material.get())
                        .define('L', ItemTags.LOGS)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ItemTags.LOGS))
                        .group("crafting_table")
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "crafting_table"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.CRAFTING_TABLE.asItem())
                        .pattern("CS").pattern("LW")
                        .define('C', ZPMiscItems.chisel_material.get())
                        .define('S', ZPMiscItems.shelves_material.get())
                        .define('L', ItemTags.PLANKS)
                        .define('W', ZPBlocks.cracked_crafting_table.get())
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ZPBlocks.cracked_crafting_table.get()))
                        .unlockedBy("has_rf2", IZPRecipeSpec.has(ItemTags.PLANKS))
                        .group("crafting_table")
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "crafting_table2"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPBlocks.sandbag.get(), 8)
                        .pattern("FSF").pattern("FSF").pattern("FSF")
                        .define('F', ItemTags.SAND)
                        .define('S', Items.LEATHER)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.LEATHER))
                        .unlockedBy("has_rf2", IZPRecipeSpec.has(ItemTags.SAND))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "sandbag_block"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.scrap_bars.get(), 6)
                        .pattern("FFF")
                        .pattern("FFF")
                        .define('F', ZPMiscItems.scrap_stack_material.get())
                        .unlockedBy("has_scrap", IZPRecipeSpec.has(ZPMiscItems.scrap_stack_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "scrap_bars"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.scrap_block.get(), 3)
                        .pattern("FF")
                        .pattern("FF")
                        .define('F', ZPMiscItems.scrap_stack_material.get())
                        .unlockedBy("has_scrap", IZPRecipeSpec.has(ZPMiscItems.scrap_stack_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "scrap_block"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.scrap_slab.get(), 6)
                        .pattern("FFF")
                        .define('F', ZPBlocks.scrap_block.get())
                        .unlockedBy("has_scrap", IZPRecipeSpec.has(ZPMiscItems.scrap_stack_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "scrap_slab"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.scrap_stairs.get(), 6)
                        .pattern("F  ")
                        .pattern("FF ")
                        .pattern("FFF")
                        .define('F', ZPBlocks.scrap_block.get())
                        .unlockedBy("has_scrap", IZPRecipeSpec.has(ZPMiscItems.scrap_stack_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "scrap_stairs"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.scrap_door.get(), 3)
                        .pattern("FF")
                        .pattern("FF")
                        .pattern("FF")
                        .define('F', ZPMiscItems.scrap_stack_material.get())
                        .unlockedBy("has_scrap", IZPRecipeSpec.has(ZPMiscItems.scrap_stack_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "scrap_door"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.scrap_trapDoor.get(), 2)
                        .pattern("FFF")
                        .pattern("FFF")
                        .define('F', ZPMiscItems.scrap_stack_material.get())
                        .unlockedBy("has_scrap", IZPRecipeSpec.has(ZPMiscItems.scrap_stack_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "scrap_trapdoor"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPBlocks.barbared_wire.get(), 2)
                        .pattern("F F").pattern(" F ").pattern("F F")
                        .define('F', Items.IRON_NUGGET)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.IRON_NUGGET))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "barbared_wire"));
            }));

            for (String color : dyeMap.keySet()) {
                Item dye = dyeMap.get(color);
                Block concrete = concretePowderMap.get(color);

                recipeToAdd.add((writer -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, concrete.asItem(), 16)
                            .pattern("CDS")
                            .pattern("SSS")
                            .pattern("FFF")
                            .define('C', ZPMiscItems.cement_material.get())
                            .define('S', Blocks.SAND.asItem())
                            .define('F', Blocks.GRAVEL.asItem())
                            .define('D', dye)
                            .unlockedBy("has_cm", IZPRecipeSpec.has(ZPMiscItems.cement_material.get()))
                            .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), color + "_concrete_powder"));
                }));
            }

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_slab_black.get(), 6)
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_black.get())
                        .unlockedBy("has_steel_black", IZPRecipeSpec.has(ZPBlocks.steel_black.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_slab_black"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_stairs_black.get(), 4)
                        .pattern("F  ")
                        .pattern("FF ")
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_black.get())
                        .unlockedBy("has_steel_black", IZPRecipeSpec.has(ZPBlocks.steel_black.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_stairs_black"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_slab_gray.get(), 6)
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_gray.get())
                        .unlockedBy("has_steel_gray", IZPRecipeSpec.has(ZPBlocks.steel_gray.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_slab_gray"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_stairs_gray.get(), 4)
                        .pattern("F  ")
                        .pattern("FF ")
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_gray.get())
                        .unlockedBy("has_steel_gray", IZPRecipeSpec.has(ZPBlocks.steel_gray.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_stairs_gray"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_slab_green.get(), 6)
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_green.get())
                        .unlockedBy("has_steel_green", IZPRecipeSpec.has(ZPBlocks.steel_green.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_slab_green"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_stairs_green.get(), 4)
                        .pattern("F  ")
                        .pattern("FF ")
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_green.get())
                        .unlockedBy("has_steel_green", IZPRecipeSpec.has(ZPBlocks.steel_green.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_stairs_green"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_slab_hazard.get(), 6)
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_hazard.get())
                        .unlockedBy("has_steel_hazard", IZPRecipeSpec.has(ZPBlocks.steel_hazard.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_slab_hazard"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_stairs_hazard.get(), 4)
                        .pattern("F  ")
                        .pattern("FF ")
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_hazard.get())
                        .unlockedBy("has_steel_hazard", IZPRecipeSpec.has(ZPBlocks.steel_hazard.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_stairs_hazard"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_slab_orange.get(), 6)
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_orange.get())
                        .unlockedBy("has_steel_orange", IZPRecipeSpec.has(ZPBlocks.steel_orange.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_slab_orange"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_stairs_orange.get(), 4)
                        .pattern("F  ")
                        .pattern("FF ")
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_orange.get())
                        .unlockedBy("has_steel_orange", IZPRecipeSpec.has(ZPBlocks.steel_orange.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_stairs_orange"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_slab_white.get(), 6)
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_white.get())
                        .unlockedBy("has_steel_white", IZPRecipeSpec.has(ZPBlocks.steel_white.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_slab_white"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.steel_stairs_white.get(), 4)
                        .pattern("F  ")
                        .pattern("FF ")
                        .pattern("FFF")
                        .define('F', ZPBlocks.steel_white.get())
                        .unlockedBy("has_steel_white", IZPRecipeSpec.has(ZPBlocks.steel_white.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "steel_stairs_white"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.camo_slab_forest.get(), 6)
                        .pattern("FFF")
                        .define('F', ZPBlocks.camo_forest.get())
                        .unlockedBy("has_camo_forest", IZPRecipeSpec.has(ZPBlocks.camo_forest.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "camo_slab_forest"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.camo_slab_snow.get(), 6)
                        .pattern("FFF")
                        .define('F', ZPBlocks.camo_snow.get())
                        .unlockedBy("has_camo_snow", IZPRecipeSpec.has(ZPBlocks.camo_snow.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "camo_slab_snow"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.camo_slab_sand.get(), 6)
                        .pattern("FFF")
                        .define('F', ZPBlocks.camo_sand.get())
                        .unlockedBy("has_camo_sand", IZPRecipeSpec.has(ZPBlocks.camo_sand.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "camo_slab_sand"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ZPBlocks.chain_link.get(), 4)
                        .pattern("FFF")
                        .pattern("FFF")
                        .define('F', Items.IRON_NUGGET)
                        .unlockedBy("has_steel_orange", IZPRecipeSpec.has(Items.IRON_NUGGET))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "chain_link"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.asphalt.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.asphalt_slab.get(), 2)
                        .unlockedBy("has_asphalt", IZPRecipeSpec.has(ZPBlocks.asphalt.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "asphalt_slab_from_asphalt"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.asphalt.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.asphalt_stairs.get(), 1)
                        .unlockedBy("has_asphalt", IZPRecipeSpec.has(ZPBlocks.asphalt.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "asphalt_stairs_from_asphalt"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.asphalt_marking.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.asphalt_marking_slab.get(), 2)
                        .unlockedBy("has_asphalt_marking", IZPRecipeSpec.has(ZPBlocks.asphalt_marking.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "asphalt_marking_slab_from_asphalt_marking"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.asphalt_marking.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.asphalt_marking_stairs.get(), 1)
                        .unlockedBy("has_asphalt_marking", IZPRecipeSpec.has(ZPBlocks.asphalt_marking.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "asphalt_marking_stairs_from_asphalt_marking"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_white.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_white.object().get(), 1)
                        .unlockedBy("has_stone_white", IZPRecipeSpec.has(ZPBlocks.stone_white.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_white_from_stone_white"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_white.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_white.object().get(), 2)
                        .unlockedBy("has_stone_white", IZPRecipeSpec.has(ZPBlocks.stone_white.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_white_from_stone_white"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_white.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_white.object().get(), 1)
                        .unlockedBy("has_stone_white", IZPRecipeSpec.has(ZPBlocks.stone_white.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_white_from_stone_white"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_black.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_black.object().get(), 1)
                        .unlockedBy("has_stone_black", IZPRecipeSpec.has(ZPBlocks.stone_black.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_black_from_stone_black"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_black.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_black.object().get(), 2)
                        .unlockedBy("has_stone_black", IZPRecipeSpec.has(ZPBlocks.stone_black.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_black_from_stone_black"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_black.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_black.object().get(), 1)
                        .unlockedBy("has_stone_black", IZPRecipeSpec.has(ZPBlocks.stone_black.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_black_from_stone_black"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_blue.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_blue.object().get(), 1)
                        .unlockedBy("has_stone_blue", IZPRecipeSpec.has(ZPBlocks.stone_blue.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_blue_from_stone_blue"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_blue.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_blue.object().get(), 2)
                        .unlockedBy("has_stone_blue", IZPRecipeSpec.has(ZPBlocks.stone_blue.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_blue_from_stone_blue"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_blue.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_blue.object().get(), 1)
                        .unlockedBy("has_stone_blue", IZPRecipeSpec.has(ZPBlocks.stone_blue.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_blue_from_stone_blue"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_brown.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_brown.object().get(), 1)
                        .unlockedBy("has_stone_brown", IZPRecipeSpec.has(ZPBlocks.stone_brown.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_brown_from_stone_brown"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_brown.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_brown.object().get(), 2)
                        .unlockedBy("has_stone_brown", IZPRecipeSpec.has(ZPBlocks.stone_brown.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_brown_from_stone_brown"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_brown.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_brown.object().get(), 1)
                        .unlockedBy("has_stone_brown", IZPRecipeSpec.has(ZPBlocks.stone_brown.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_brown_from_stone_brown"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_cyan.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_cyan.object().get(), 1)
                        .unlockedBy("has_stone_cyan", IZPRecipeSpec.has(ZPBlocks.stone_cyan.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_cyan_from_stone_cyan"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_cyan.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_cyan.object().get(), 2)
                        .unlockedBy("has_stone_cyan", IZPRecipeSpec.has(ZPBlocks.stone_cyan.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_cyan_from_stone_cyan"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_cyan.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_cyan.object().get(), 1)
                        .unlockedBy("has_stone_cyan", IZPRecipeSpec.has(ZPBlocks.stone_cyan.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_cyan_from_stone_cyan"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_gray.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_gray.object().get(), 1)
                        .unlockedBy("has_stone_gray", IZPRecipeSpec.has(ZPBlocks.stone_gray.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_gray_from_stone_gray"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_gray.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_gray.object().get(), 2)
                        .unlockedBy("has_stone_gray", IZPRecipeSpec.has(ZPBlocks.stone_gray.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_gray_from_stone_gray"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_gray.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_gray.object().get(), 1)
                        .unlockedBy("has_stone_gray", IZPRecipeSpec.has(ZPBlocks.stone_gray.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_gray_from_stone_gray"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_green.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_green.object().get(), 1)
                        .unlockedBy("has_stone_green", IZPRecipeSpec.has(ZPBlocks.stone_green.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_green_from_stone_green"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_green.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_green.object().get(), 2)
                        .unlockedBy("has_stone_green", IZPRecipeSpec.has(ZPBlocks.stone_green.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_green_from_stone_green"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_green.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_green.object().get(), 1)
                        .unlockedBy("has_stone_green", IZPRecipeSpec.has(ZPBlocks.stone_green.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_green_from_stone_green"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_light_blue.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_light_blue.object().get(), 1)
                        .unlockedBy("has_stone_light_blue", IZPRecipeSpec.has(ZPBlocks.stone_light_blue.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_light_blue_from_stone_light_blue"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_light_blue.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_light_blue.object().get(), 2)
                        .unlockedBy("has_stone_light_blue", IZPRecipeSpec.has(ZPBlocks.stone_light_blue.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_light_blue_from_stone_light_blue"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_light_blue.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_light_blue.object().get(), 1)
                        .unlockedBy("has_stone_light_blue", IZPRecipeSpec.has(ZPBlocks.stone_light_blue.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_light_blue_from_stone_light_blue"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_light_gray.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_light_gray.object().get(), 1)
                        .unlockedBy("has_stone_light_gray", IZPRecipeSpec.has(ZPBlocks.stone_light_gray.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_light_gray_from_stone_light_gray"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_light_gray.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_light_gray.object().get(), 2)
                        .unlockedBy("has_stone_light_gray", IZPRecipeSpec.has(ZPBlocks.stone_light_gray.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_light_gray_from_stone_light_gray"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_light_gray.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_light_gray.object().get(), 1)
                        .unlockedBy("has_stone_light_gray", IZPRecipeSpec.has(ZPBlocks.stone_light_gray.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_light_gray_from_stone_light_gray"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_lime.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_lime.object().get(), 1)
                        .unlockedBy("has_stone_lime", IZPRecipeSpec.has(ZPBlocks.stone_lime.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_lime_from_stone_lime"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_lime.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_lime.object().get(), 2)
                        .unlockedBy("has_stone_lime", IZPRecipeSpec.has(ZPBlocks.stone_lime.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_lime_from_stone_lime"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_lime.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_lime.object().get(), 1)
                        .unlockedBy("has_stone_lime", IZPRecipeSpec.has(ZPBlocks.stone_lime.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_lime_from_stone_lime"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_magenta.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_magenta.object().get(), 1)
                        .unlockedBy("has_stone_magenta", IZPRecipeSpec.has(ZPBlocks.stone_magenta.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_magenta_from_stone_magenta"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_magenta.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_magenta.object().get(), 2)
                        .unlockedBy("has_stone_magenta", IZPRecipeSpec.has(ZPBlocks.stone_magenta.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_magenta_from_stone_magenta"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_magenta.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_magenta.object().get(), 1)
                        .unlockedBy("has_stone_magenta", IZPRecipeSpec.has(ZPBlocks.stone_magenta.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_magenta_from_stone_magenta"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_orange.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_orange.object().get(), 1)
                        .unlockedBy("has_stone_orange", IZPRecipeSpec.has(ZPBlocks.stone_orange.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_orange_from_stone_orange"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_orange.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_orange.object().get(), 2)
                        .unlockedBy("has_stone_orange", IZPRecipeSpec.has(ZPBlocks.stone_orange.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_orange_from_stone_orange"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_orange.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_orange.object().get(), 1)
                        .unlockedBy("has_stone_orange", IZPRecipeSpec.has(ZPBlocks.stone_orange.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_orange_from_stone_orange"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_pink.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_pink.object().get(), 1)
                        .unlockedBy("has_stone_pink", IZPRecipeSpec.has(ZPBlocks.stone_pink.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_pink_from_stone_pink"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_pink.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_pink.object().get(), 2)
                        .unlockedBy("has_stone_pink", IZPRecipeSpec.has(ZPBlocks.stone_pink.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_pink_from_stone_pink"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_pink.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_pink.object().get(), 1)
                        .unlockedBy("has_stone_pink", IZPRecipeSpec.has(ZPBlocks.stone_pink.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_pink_from_stone_pink"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_purple.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_purple.object().get(), 1)
                        .unlockedBy("has_stone_purple", IZPRecipeSpec.has(ZPBlocks.stone_purple.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_purple_from_stone_purple"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_purple.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_purple.object().get(), 2)
                        .unlockedBy("has_stone_purple", IZPRecipeSpec.has(ZPBlocks.stone_purple.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_purple_from_stone_purple"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_purple.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_purple.object().get(), 1)
                        .unlockedBy("has_stone_purple", IZPRecipeSpec.has(ZPBlocks.stone_purple.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_purple_from_stone_purple"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_red.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_red.object().get(), 1)
                        .unlockedBy("has_stone_red", IZPRecipeSpec.has(ZPBlocks.stone_red.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_red_from_stone_red"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_red.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_red.object().get(), 2)
                        .unlockedBy("has_stone_red", IZPRecipeSpec.has(ZPBlocks.stone_red.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_red_from_stone_red"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_red.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_red.object().get(), 1)
                        .unlockedBy("has_stone_red", IZPRecipeSpec.has(ZPBlocks.stone_red.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_red_from_stone_red"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_yellow.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_stairs_yellow.object().get(), 1)
                        .unlockedBy("has_stone_yellow", IZPRecipeSpec.has(ZPBlocks.stone_yellow.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_stairs_yellow_from_stone_yellow"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_yellow.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_slab_yellow.object().get(), 2)
                        .unlockedBy("has_stone_yellow", IZPRecipeSpec.has(ZPBlocks.stone_yellow.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_slab_yellow_from_stone_yellow"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.stone_yellow.object().get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.stone_wall_yellow.object().get(), 1)
                        .unlockedBy("has_stone_yellow", IZPRecipeSpec.has(ZPBlocks.stone_yellow.object().get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "stone_wall_yellow_from_stone_yellow"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.black_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.black_stairs_bricks.get(), 1)
                        .unlockedBy("has_black_bricks", IZPRecipeSpec.has(ZPBlocks.black_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "black_stairs_bricks_from_black_bricks"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.black_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.black_slab_bricks.get(), 2)
                        .unlockedBy("has_black_bricks", IZPRecipeSpec.has(ZPBlocks.black_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "black_slab_bricks_from_black_bricks"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.black_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.black_wall_bricks.get(), 1)
                        .unlockedBy("has_black_bricks", IZPRecipeSpec.has(ZPBlocks.black_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "black_wall_bricks_from_black_bricks"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.gray_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.gray_stairs_bricks.get(), 1)
                        .unlockedBy("has_gray_bricks", IZPRecipeSpec.has(ZPBlocks.gray_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "gray_stairs_bricks_from_gray_bricks"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.gray_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.gray_slab_bricks.get(), 2)
                        .unlockedBy("has_gray_bricks", IZPRecipeSpec.has(ZPBlocks.gray_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "gray_slab_bricks_from_gray_bricks"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.gray_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.gray_wall_bricks.get(), 1)
                        .unlockedBy("has_gray_bricks", IZPRecipeSpec.has(ZPBlocks.gray_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "gray_wall_bricks_from_gray_bricks"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.green_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.green_stairs_bricks.get(), 1)
                        .unlockedBy("has_green_bricks", IZPRecipeSpec.has(ZPBlocks.green_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "green_stairs_bricks_from_green_bricks"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.green_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.green_slab_bricks.get(), 2)
                        .unlockedBy("has_green_bricks", IZPRecipeSpec.has(ZPBlocks.green_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "green_slab_bricks_from_green_bricks"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.green_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.green_wall_bricks.get(), 1)
                        .unlockedBy("has_green_bricks", IZPRecipeSpec.has(ZPBlocks.green_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "green_wall_bricks_from_green_bricks"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.ancient_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.ancient_stairs_bricks.get(), 1)
                        .unlockedBy("has_ancient_bricks", IZPRecipeSpec.has(ZPBlocks.ancient_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "ancient_stairs_bricks_from_ancient_bricks"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.ancient_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.ancient_slab_bricks.get(), 2)
                        .unlockedBy("has_ancient_bricks", IZPRecipeSpec.has(ZPBlocks.ancient_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "ancient_slab_bricks_from_ancient_bricks"));
            }));

            recipeToAdd.add((writer -> {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(ZPBlocks.ancient_bricks.get()), RecipeCategory.BUILDING_BLOCKS, ZPBlocks.ancient_wall_bricks.get(), 1)
                        .unlockedBy("has_ancient_bricks", IZPRecipeSpec.has(ZPBlocks.ancient_bricks.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "ancient_wall_bricks_from_ancient_bricks"));
            }));


        }

        @Override
        public Collection<IZPRecipeSpec> getRecipesToRegister() {
            return recipeToAdd;
        }

        @Override
        public Collection<ZPRecipesController.RecipeToRemove> getRecipesToRemove() {
            return toRemove;
        }
    }
}
