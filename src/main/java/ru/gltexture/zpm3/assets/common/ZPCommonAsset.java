package ru.gltexture.zpm3.assets.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.events.client.ZPRenderWorldEventWithPickUpCheck;
import ru.gltexture.zpm3.assets.common.events.common.ZPEntityItemEvent;
import ru.gltexture.zpm3.assets.common.events.common.ZPLivingEvents;
import ru.gltexture.zpm3.assets.common.events.common.ZPMobAttributes;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.*;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.common.keybind.ZPCommonKeyBindings;
import ru.gltexture.zpm3.assets.common.population.ZPSetupPopulation;
import ru.gltexture.zpm3.assets.common.rendering.entities.misc.ZPRenderEntityItem;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.helpers.ZPEntityRenderMatchHelper;
import ru.gltexture.zpm3.engine.instances.blocks.IHotLiquid;
import ru.gltexture.zpm3.engine.population.ZPPopulationController;
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
        }

        DispenserBlock.registerBehavior(Items.LAVA_BUCKET, new DefaultDispenseItemBehavior() {
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
        });
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

    @Override
    public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
        mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("common", "ru.gltexture.zpm3.assets.common.mixins.impl"),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPTorchMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPWallTorchMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPPumpkinMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPLavaMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPFluidPlacedMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPMobCategoryMixin", ZPSide.COMMON),
                new ZombiePlague3.IMixinEntry.MixinClass("common.ZPForgeItemMixin", ZPSide.COMMON)
        );
    }

    @Override
    public void initializeAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.setPopulationSetup(new ZPCommonPopulationSetup());
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
            assetEntry.addEventClass(ZPRenderWorldEventWithPickUpCheck.class);
        });
        assetEntry.addEventClass(ZPLivingEvents.class);
        assetEntry.addEventClass(ZPMobAttributes.class);
        assetEntry.addEventClass(ZPEntityItemEvent.class);

        ZPUtility.sides().onlyClient(() -> {
            assetEntry.addZP3RegistryClass(ZPTabs.class);
        });
    }

    @Override
    public void preCommonInitializeAsset() {
        ZombiePlague3.registerConfigClass(new ZPConstants());
        ZPUtility.sides().onlyClient(() -> {
            ZPEntityRenderMatchHelper.matchEntityRendering(() -> EntityType.ITEM, ZPRenderEntityItem::new);
            ZombiePlague3.registerKeyBindings(new ZPCommonKeyBindings());;
        });
    }

    private static class ZPCommonPopulationSetup extends ZPSetupPopulation {
        public static void caveSpawns(MobSpawnSettings.Builder pBuilder) {
            pBuilder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
            pBuilder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6));
        }

        @Override
        public void setup(@NotNull ZPPopulationController controller) {
            {
                controller.getVanillaBiomePopulationManager().setCancelVanilla_monsters_Method(true);
                controller.getVanillaBiomePopulationManager().addMonster_Consumer(((pBuilder, pZombieWeight, pZombieVillageWeight,pSkeletonWeight,pIsUnderwater) -> {
                    pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ZPEntities.zp_common_zombie_entity.get(), 100, 1, 4));
                    pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ZPEntities.zp_miner_zombie_entity.get(), 80, 1, 2));
                    pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ZPEntities.zp_dog_zombie_entity.get(), 20, 2, 5));
                    return null;
                }));

                controller.getVanillaBiomePopulationManager().setCancelVanilla_oceanSpawns_Method(true);
                controller.getVanillaBiomePopulationManager().addOceanSpawn_Consumer((pBuilder, pSquidWeight, pSquidMaxCount, pCodWeight) -> {
                    pBuilder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, pSquidWeight, 1, pSquidMaxCount));
                    pBuilder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, pCodWeight, 3, 6));
                    return null;
                });

                controller.getVanillaBiomePopulationManager().setCancelVanilla_warmOceanSpawns_Method(true);
                controller.getVanillaBiomePopulationManager().addWarmOceanSpawn_Consumer((pBuilder, pSquidWeight, pSquidMinCount) -> {
                    pBuilder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, pSquidWeight, pSquidMinCount, 4));
                    pBuilder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 8, 8));
                    pBuilder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 2, 1, 2));
                    return null;
                });

                controller.getVanillaBiomePopulationManager().setCancelVanilla_snowySpawns_Method(true);
                controller.getVanillaBiomePopulationManager().addSnowySpawn_Consumer((pBuilder) -> {
                    pBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 2, 3));
                    pBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2));
                    caveSpawns(pBuilder);
                });

                controller.getVanillaBiomePopulationManager().setCancelVanilla_desertSpawns_Method(true);
                controller.getVanillaBiomePopulationManager().addDesertSpawn_Consumer((pBuilder) -> {
                    pBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
                    caveSpawns(pBuilder);
                });

                controller.getVanillaBiomePopulationManager().setCancelVanilla_dripstoneCavesSpawns_Method(true);
                controller.getVanillaBiomePopulationManager().addDripstoneCaveSpawn_Consumer((pBuilder) -> {
                    caveSpawns(pBuilder);
                });

                controller.addREPLACE_Rule(() -> ZPEntities.zp_common_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZPAbstractZombie::checkZombieSpawnRules);
                controller.addREPLACE_Rule(() -> ZPEntities.zp_miner_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, level, spawnType, pos, random) -> (level.getBlockState(pos).is(Blocks.CAVE_AIR) || ZPRandom.getRandom().nextFloat() <= 0.01f) && ZPAbstractZombie.checkZombieSpawnRules(entityType, level, spawnType, pos, random));
                controller.addREPLACE_Rule(() -> ZPEntities.zp_dog_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, level, spawnType, pos, random) -> (!level.getBlockState(pos).is(Blocks.CAVE_AIR)) && ZPAbstractZombie.checkZombieSpawnRules(entityType, level, spawnType, pos, random));
            }
        }
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
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ZPItems.chisel_material.get())
                        .pattern("S").pattern("T")
                        .define('S', Items.STICK)
                        .define('T', Items.FLINT)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.STICK))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "chisel_material"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ZPItems.shelves_material.get())
                        .pattern("SS").pattern("SS")
                        .define('S', ZPItems.table_material.get())
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ZPItems.table_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "shelves_material"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ZPItems.table_material.get())
                        .pattern("SS").pattern("SS")
                        .define('S', Items.STICK)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.STICK))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "table_material"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Blocks.CRAFTING_TABLE.asItem())
                        .pattern("CS").pattern("LL")
                        .define('C', ZPItems.chisel_material.get())
                        .define('S', ZPItems.shelves_material.get())
                        .define('L', ItemTags.LOGS)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ItemTags.LOGS))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "crafting_table"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ZPItems.scrap_stack_material.get())
                        .pattern("SSS").pattern("SSS").pattern("SSS")
                        .define('S', ZPItems.scrap_material.get())
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ZPItems.scrap_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "scrap_stack_material"));
            }));

            recipeToAdd.add((writer -> {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(ZPItems.scrap_stack_material.get()), RecipeCategory.TOOLS, Items.IRON_INGOT, 0.5f, 300)
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
