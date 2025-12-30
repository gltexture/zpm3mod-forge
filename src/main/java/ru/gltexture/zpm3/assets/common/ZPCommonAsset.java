package ru.gltexture.zpm3.assets.common;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ConditionReference;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.*;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.assets.common.tiers.ZPCommonTiers;
import ru.gltexture.zpm3.assets.player.misc.ZPDefaultItemsHandReach;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPLootTableProvider;
import ru.gltexture.zpm3.engine.instances.blocks.IHotLiquid;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.recipes.IZPRecipeSpec;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

public class ZPCommonAsset extends ZPAsset {
    public ZPCommonAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPCommonAsset() {
    }

    @Override
    public void commonSetup() {
        {
            Blocks.CYAN_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.WHITE_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.ORANGE_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.MAGENTA_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.LIGHT_BLUE_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.YELLOW_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.LIME_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.PINK_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.GRAY_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.LIGHT_GRAY_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.CYAN_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.PURPLE_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.BLUE_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.BROWN_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.GREEN_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.RED_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.BLACK_CONCRETE.explosionResistance = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.OBSIDIAN.explosionResistance = 4.0f;

            Blocks.CYAN_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.WHITE_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.ORANGE_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.MAGENTA_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.YELLOW_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.LIME_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.PINK_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.GRAY_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.CYAN_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.PURPLE_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.BLUE_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.BROWN_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.GREEN_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.RED_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.BLACK_CONCRETE.defaultBlockState().destroySpeed = ZPConstants.ZP_VANILLA_CONCRETE_DESTROY_SPEED;
            Blocks.OBSIDIAN.defaultBlockState().destroySpeed = 6.0f;

            Blocks.BRICK_WALL.defaultBlockState().destroySpeed = 12.0F;
            Blocks.BRICK_WALL.explosionResistance = 8.0f;

            Blocks.BRICK_STAIRS.defaultBlockState().destroySpeed = 12.0F;
            Blocks.BRICK_STAIRS.explosionResistance = 8.0f;

            Blocks.BRICK_SLAB.defaultBlockState().destroySpeed = 8.0F;
            Blocks.BRICK_SLAB.explosionResistance = 8.0f;

            Blocks.BRICKS.defaultBlockState().destroySpeed = 12.0F;
            Blocks.BRICKS.explosionResistance = 8.0f;

            Blocks.IRON_BLOCK.explosionResistance = 9.0f;
            Blocks.IRON_DOOR.explosionResistance = 16.0f;

            Blocks.IRON_BARS.getStateDefinition().getPossibleStates().forEach((e) -> {
                e.destroySpeed = 12.0F;
            });
            Blocks.IRON_BARS.explosionResistance = 8.0f;
        }

        final DefaultDispenseItemBehavior defaultLiqDispense = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            public @NotNull ItemStack execute(@NotNull BlockSource p_123561_, @NotNull ItemStack p_123562_) {
                DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem) p_123562_.getItem();
                BlockPos blockpos = p_123561_.getPos().relative(p_123561_.getBlockState().getValue(DispenserBlock.FACING));
                Level level = p_123561_.getLevel();
                if (dispensiblecontaineritem.emptyContents(null, level, blockpos, null, p_123562_)) {
                    dispensiblecontaineritem.checkExtraContent(null, level, p_123562_, blockpos);
                    if (level instanceof ServerLevel) {
                        BlockEntity be = level.getBlockEntity(blockpos);
                        if (be != null) {
                            if (be instanceof ZPFadingBlockEntity zpFadingBlock) {
                                zpFadingBlock.setActive(true);
                            }
                        }
                    }
                    return new ItemStack(Items.BUCKET);
                } else {
                    return this.defaultDispenseItemBehavior.dispense(p_123561_, p_123562_);
                }
            }
        };
        DispenserBlock.registerBehavior(ZPItems.toxicwater_bucket.get(), defaultLiqDispense);
        DispenserBlock.registerBehavior(ZPItems.acid_bucket.get(), defaultLiqDispense);
        DispenserBlock.registerBehavior(Items.LAVA_BUCKET, defaultLiqDispense);
        DispenserBlock.registerBehavior(Items.BUCKET, new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            public @NotNull ItemStack execute(@NotNull BlockSource pSource, @NotNull ItemStack pStack) {
                LevelAccessor levelaccessor = pSource.getLevel();
                BlockPos blockpos = pSource.getPos().relative(pSource.getBlockState().getValue(DispenserBlock.FACING));
                BlockState blockstate = levelaccessor.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                if (block instanceof IHotLiquid iHotLiquid && iHotLiquid.bucketFillingChance() < 1.0f) {
                    return super.execute(pSource, pStack);
                }
                if (block instanceof BucketPickup) {
                    ItemStack itemstack = ((BucketPickup) block).pickupBlock(levelaccessor, blockpos, blockstate);
                    if (itemstack.isEmpty()) {
                        return super.execute(pSource, pStack);
                    } else {
                        levelaccessor.gameEvent(null, GameEvent.FLUID_PICKUP, blockpos);
                        Item item = itemstack.getItem();
                        pStack.shrink(1);
                        if (pStack.isEmpty()) {
                            return new ItemStack(item);
                        } else {
                            if (pSource.<DispenserBlockEntity>getEntity().addItem(new ItemStack(item)) < 0) {
                                this.defaultDispenseItemBehavior.dispense(pSource, new ItemStack(item));
                            }
                            return pStack;
                        }
                    }
                } else {
                    return super.execute(pSource, pStack);
                }
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientDestroy() {

    }

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("common", "ru.gltexture.zpm3.assets.common.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPTorchMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPWallTorchMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPPumpkinMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPLavaMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPFluidPlacedMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPMobCategoryMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPMilkMixin", ZPSide.COMMON)
    //    );
    //}

    @Override
    public void initializeAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
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

        assetEntry.addTier(ZPCommonTiers.values());
        assetEntry.setRecipesRegistry(new ZPCommonRecipeRegistry());
        assetEntry.addZP3RegistryClass(ZPSounds.class);
        assetEntry.addZP3RegistryClass(ZPItems.class);
        assetEntry.addZP3RegistryClass(ZPBlockItems.class);
        assetEntry.addZP3RegistryClass(ZPBlocks.class);
        assetEntry.addZP3RegistryClass(ZPTorchBlocks.class);
        assetEntry.addZP3RegistryClass(ZPEntityAttributes.class);
        assetEntry.addZP3RegistryClass(ZPEntities.class);
        assetEntry.addZP3RegistryClass(ZPBlockEntities.class);
        assetEntry.addZP3RegistryClass(ZPFluids.class);
        assetEntry.addZP3RegistryClass(ZPFluidTypes.class);
        assetEntry.addZP3RegistryClass(ZPDamageTypes.class);

        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addZP3RegistryClass(ZPTabs.class);
        });
    }

    @Override
    public void preCommonInitializeAsset() {
        ZombiePlague3.registerConfigClass(new ZPConstants());
    }

    @Override
    public void postCommonInitializeAsset() {
    }

    private static class ZPCommonRecipeRegistry extends ZPRecipesRegistry {
        private static List<IZPRecipeSpec> recipeToAdd = new ArrayList<>();
        private static List<ZPRecipesController.RecipeToRemove> toRemove = new ArrayList<>();

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

        private static Map<String, Block> concretePowderMap = new HashMap<>() {{
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
            toRemove.add(new ZPRecipesController.RecipeToRemove(RecipeType.CRAFTING, new ResourceLocation("minecraft", "crafting_table")));

            for (DyeColor color : DyeColor.values()) {
                toRemove.add(new ZPRecipesController.RecipeToRemove(
                        RecipeType.CRAFTING,
                        new ResourceLocation("minecraft", color.getName() + "_concrete_powder")
                ));
            }

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPItems.chisel_material.get())
                        .pattern("S").pattern("T")
                        .define('S', Items.STICK)
                        .define('T', Items.FLINT)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.STICK))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "chisel_material"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPItems.shelves_material.get())
                        .pattern("SS").pattern("SS")
                        .define('S', ZPItems.table_material.get())
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ZPItems.table_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "shelves_material"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPItems.table_material.get())
                        .pattern("SS").pattern("SS")
                        .define('S', Items.STICK)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.STICK))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "table_material"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.CRAFTING_TABLE.asItem())
                        .pattern("CS").pattern("LL")
                        .define('C', ZPItems.chisel_material.get())
                        .define('S', ZPItems.shelves_material.get())
                        .define('L', ItemTags.LOGS)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ItemTags.LOGS))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "crafting_table"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPItems.scrap_stack_material.get())
                        .pattern("SSS").pattern("SSS").pattern("SSS")
                        .define('S', ZPItems.scrap_material.get())
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ZPItems.scrap_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "scrap_stack_material"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPBlocks.sandbag.get(), 1)
                        .pattern("FSF").pattern("FSF").pattern("FSF")
                        .define('F', Blocks.SAND)
                        .define('S', Items.LEATHER)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.LEATHER))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "sandbag_block"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPBlocks.scrap.get(), 1)
                        .pattern("FFF").pattern("FFF").pattern("FFF")
                        .define('F', ZPItems.scrap_material.get())
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ZPItems.scrap_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "scrap_block"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPBlocks.barbared_wire.get(), 4)
                        .pattern("F F").pattern(" F ").pattern("F F")
                        .define('F', Items.IRON_INGOT)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.IRON_INGOT))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "barbared_wire"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPItems._handmade_pistol.get(), 8)
                        .pattern("F").pattern("P").pattern("I")
                        .define('F', Items.FLINT)
                        .define('P', Items.GUNPOWDER)
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.IRON_INGOT))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "_handmade_pistol"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ZPItems.handmade_pistol.get())
                        .pattern("III").pattern("FFP")
                        .define('F', ItemTags.LOGS)
                        .define('P', Blocks.LEVER.asItem())
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.IRON_INGOT))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "handmade_pistol"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ZPItems.wrench.get())
                        .pattern("I I").pattern(" I ").pattern(" I ")
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.IRON_INGOT))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "wrench"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ZPItems.matches.get())
                        .pattern("CS").pattern("PP")
                        .define('C', Items.COAL)
                        .define('S', Items.STICK)
                        .define('P', Items.PAPER)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.COAL))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "matches"));
            }));

            recipeToAdd.add((writer -> {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(ZPItems.scrap_stack_material.get()), RecipeCategory.MISC, Items.IRON_INGOT, 0.5f, 300)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ZPItems.scrap_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "iron_scrap_smelting"));
            }));

            for (String color : dyeMap.keySet()) {
                Item dye = dyeMap.get(color);
                Block concrete = concretePowderMap.get(color);

                recipeToAdd.add((writer -> {
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, concrete.asItem(), 16)
                            .pattern("CDS")
                            .pattern("SSS")
                            .pattern("FFF")
                            .define('C', ZPItems.cement_material.get())
                            .define('S', Blocks.SAND.asItem())
                            .define('F', Blocks.GRAVEL.asItem())
                            .define('D', dye)
                            .unlockedBy("has_cm", IZPRecipeSpec.has(ZPItems.cement_material.get()))
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
