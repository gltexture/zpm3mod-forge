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

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
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
import ru.gltexture.zpm3.engine.instances.blocks.IHotLiquid;
import ru.gltexture.zpm3.engine.recipes.IZPRecipeSpec;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.blocks.init.*;
import ru.gltexture.zpm3.modules.common.init.ZPTags;
import ru.gltexture.zpm3.modules.entity.init.ZPEntities;
import ru.gltexture.zpm3.modules.entity.instances.throwables.ZPRottenFleshEntity;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.ZPMeleeThrowableToolsItems;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPFadingBlockEntity;
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
        }) {
            ZPDataGenHelper.addBlockLootTable(() -> b, () -> new LootPool.Builder()
                    .add(LootItem.lootTableItem(Blocks.COBBLESTONE))
                    .when(ExplosionCondition.survivesExplosion())
            );
        }

        ZPDataGenHelper.addBlockLootTable(() -> Blocks.CAMPFIRE, () -> new LootPool.Builder()
                .add(LootItem.lootTableItem(Items.STICK))
                .setRolls(UniformGenerator.between(8, 16))
                .when(ExplosionCondition.survivesExplosion())
        );

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
                        .pattern("CS").pattern("LL")
                        .define('C', ZPMiscItems.chisel_material.get())
                        .define('S', ZPMiscItems.shelves_material.get())
                        .define('L', ItemTags.LOGS)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ItemTags.LOGS))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "crafting_table"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPBlocks.sandbag.get(), 8)
                        .pattern("FSF").pattern("FSF").pattern("FSF")
                        .define('F', Blocks.SAND)
                        .define('S', Items.LEATHER)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.LEATHER))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "sandbag_block"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPBlocks.scrap_block.get(), 1)
                        .pattern("FFF").pattern("FFF").pattern("FFF")
                        .define('F', ZPMiscItems.scrap_material.get())
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ZPMiscItems.scrap_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "scrap_block"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPBlocks.barbared_wire.get(), 4)
                        .pattern("F F").pattern(" F ").pattern("F F")
                        .define('F', Items.IRON_INGOT)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.IRON_INGOT))
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
