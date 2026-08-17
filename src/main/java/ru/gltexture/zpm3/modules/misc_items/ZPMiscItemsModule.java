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

package ru.gltexture.zpm3.modules.misc_items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModule;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModuleData;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePostInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePreInitContext;
import ru.gltexture.zpm3.engine.instances.blocks.IHotLiquid;
import ru.gltexture.zpm3.engine.recipes.IZPRecipeSpec;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.modules.debug.events.ZPRenderStuffEvent;
import ru.gltexture.zpm3.modules.entity.init.ZPEntities;
import ru.gltexture.zpm3.modules.entity.instances.throwables.ZPRottenFleshEntity;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.ZPMeleeThrowableToolsItems;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;

import java.util.*;

public class ZPMiscItemsModule extends ZPModule {
    public ZPMiscItemsModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPMiscItemsModule() {
    }

    @Override
    public void commonSetup() {
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
        context.addRecipesRegistry(new ZPMiscItemsModule.ZPMiscItemsRecipeRegistry());
        context.addCommonZp3RegistryClass(ZPMiscItems.class);
        ZPUtility.sides().onlyClient(() -> {
        });
    }

    @Override
    public void preInitialize(@NotNull IModulePreInitContext context) {
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
        context.registerDispenserBehaviour(() -> Items.ROTTEN_FLESH, new AbstractProjectileDispenseBehavior() {
            @Override
            protected @NotNull Projectile getProjectile(@NotNull Level level, @NotNull Position position, @NotNull ItemStack itemStack) {
                return new ZPRottenFleshEntity(ZPEntities.rotten_flesh_entity.get(), position.x(), position.y(), position.z(), level);
            }

            @Override
            protected float getUncertainty() {
                return 6.0f;
            }

            @Override
            protected float getPower() {
                return 1.0f;
            }
        });
        context.registerDispenserBehaviour(() -> ZPMeleeThrowableToolsItems.toxicwater_bucket.get(), defaultLiqDispense);
        context.registerDispenserBehaviour(() -> ZPMeleeThrowableToolsItems.acid_bucket.get(), defaultLiqDispense);
        context.registerDispenserBehaviour(() -> Items.LAVA_BUCKET, defaultLiqDispense);
        context.registerDispenserBehaviour(() -> Items.BUCKET, new DefaultDispenseItemBehavior() {
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

    @Override
    public void postInitialize(@NotNull IModulePostInitContext context) {

    }

    private static class ZPMiscItemsRecipeRegistry extends ZPRecipesRegistry {
        private static final List<IZPRecipeSpec> recipeToAdd = new ArrayList<>();
        private static final List<ZPRecipesController.RecipeToRemove> toRemove = new ArrayList<>();

        static {
            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPMiscItems.chisel_material.get())
                        .pattern("S").pattern("T")
                        .define('S', Items.STICK)
                        .define('T', Items.FLINT)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.STICK))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "chisel_material"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPMiscItems.shelves_material.get())
                        .pattern("SS").pattern("SS")
                        .define('S', ItemTags.PLANKS)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ItemTags.PLANKS))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "shelves_material"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPMiscItems.scrap_stack_material.get())
                        .pattern("SSS").pattern("SSS").pattern("SSS")
                        .define('S', ZPMiscItems.scrap_material.get())
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ZPMiscItems.scrap_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "scrap_stack_material"));
            }));

            recipeToAdd.add((writer -> {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(ZPMiscItems.scrap_stack_material.get()), RecipeCategory.MISC, Items.IRON_INGOT, 0.5f, 300)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ZPMiscItems.scrap_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "iron_scrap_smelting"));
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
