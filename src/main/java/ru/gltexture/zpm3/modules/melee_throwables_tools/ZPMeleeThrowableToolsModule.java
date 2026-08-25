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

package ru.gltexture.zpm3.modules.melee_throwables_tools;

import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModule;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModuleData;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePostInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePreInitContext;
import ru.gltexture.zpm3.engine.recipes.IZPRecipeSpec;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.debug.events.ZPRenderStuffEvent;
import ru.gltexture.zpm3.modules.melee_throwables_tools.events.client.ZPPlayerClientTickBroomEvent;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.ZPMeleeThrowableToolsItems;
import ru.gltexture.zpm3.modules.melee_throwables_tools.tiers.ZPCommonToolMeleeTiers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ZPMeleeThrowableToolsModule extends ZPModule {
    public ZPMeleeThrowableToolsModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPMeleeThrowableToolsModule() {
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
        context.getClientRenderHooksManager().addItemRendering3PersonHook(() -> ZPMeleeThrowableToolsItems.broom.get(), ((itemInHandRenderer, deltaTicks, entityModel, pLivingEntity, pItemStack, pDisplayContext, pArm, pPoseStack, pBuffer, pPackedLight) -> {
                    final float t = pLivingEntity.getTicksUsingItem() + deltaTicks;
                    final float swing = Mth.sin(t * 0.5F) * 0.58F;
                    pPoseStack.pushPose();
                    ((ArmedModel) entityModel).translateToHand(pArm, pPoseStack);
                    pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                    pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                    pPoseStack.mulPose(Axis.ZP.rotation(swing));
                    boolean flag = pArm == HumanoidArm.LEFT;
                    pPoseStack.translate((float) (flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
                    itemInHandRenderer.renderItem(pLivingEntity, pItemStack, pDisplayContext, flag, pPoseStack, pBuffer, pPackedLight);
                    pPoseStack.popPose();
                })
        );
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
        ZPUtility.sides().onlyClient(() -> {
            context.registerForgeEventHandlerClass(ZPPlayerClientTickBroomEvent.class);
        });
        context.addRecipesRegistry(new ZPMeleeThrowableToolsModule.ZPMeleeThrowablesToolsRecipeRegistry());
        context.addTier(ZPCommonToolMeleeTiers.values());
        context.addCommonZp3RegistryClass(ZPMeleeThrowableToolsItems.class);
    }

    @Override
    public void preInitialize(@NotNull IModulePreInitContext context) {

    }

    @Override
    public void postInitialize(@NotNull IModulePostInitContext context) {
    }

    private static class ZPMeleeThrowablesToolsRecipeRegistry extends ZPRecipesRegistry {
        private static final List<IZPRecipeSpec> recipeToAdd = new ArrayList<>();
        private static final List<ZPRecipesController.RecipeToRemove> toRemove = new ArrayList<>();

        static {
            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ZPMeleeThrowableToolsItems.wrench.get())
                        .pattern("I I").pattern(" I ").pattern(" I ")
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.IRON_INGOT))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "wrench"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ZPMeleeThrowableToolsItems.metal_cutters.get())
                        .pattern(" I ").pattern("I I").pattern("C C")
                        .define('I', Items.IRON_INGOT)
                        .define('C', Items.IRON_NUGGET)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.IRON_INGOT))
                        .unlockedBy("has_rf2", IZPRecipeSpec.has(Items.IRON_NUGGET))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "metal_cutters"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ZPMeleeThrowableToolsItems.matches.get())
                        .pattern("CS").pattern("PP")
                        .define('C', Items.COAL)
                        .define('S', Items.STICK)
                        .define('P', Items.PAPER)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.COAL))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "matches"));
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
