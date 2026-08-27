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

package ru.gltexture.zpm3.modules.guns;

import net.minecraft.client.Minecraft;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePostInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePreInitContext;
import ru.gltexture.zpm3.engine.recipes.IZPRecipeSpec;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.modules.guns.events.client.ZPGunPostRenderEvent;
import ru.gltexture.zpm3.modules.guns.events.client.ZPGunTossEvent;
import ru.gltexture.zpm3.modules.guns.events.client.ZPGunsUIEvent;
import ru.gltexture.zpm3.modules.guns.init.ZPGunItems;
import ru.gltexture.zpm3.modules.guns.keybind.ZPGunKeyBindings;
import ru.gltexture.zpm3.modules.guns.processing.input.ZPClientGunClientTickProcessing;
import ru.gltexture.zpm3.modules.guns.rendering.ZPAbstractGunRenderer;
import ru.gltexture.zpm3.modules.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.modules.guns.rendering.fx.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModule;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.*;

public class ZPGunsModule extends ZPModule {
    public ZPGunsModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPGunsModule() {
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
        ZPDefaultGunRenderers.init();
        context.getClientRenderHooksManager().addItemSceneRendering1PersonHooks((ZPRenderHooks.ZPItemSceneRendering1PersonHooks) ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        context.getClientRenderHooksManager().addItemSceneRendering3PersonHooks((ZPRenderHooks.ZPItemSceneRendering3PersonHooks) ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        context.getClientCallbacksManager().addReloadResourcesCallback(((ZPDefaultGunMuzzleflashFX) ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal).onReloadResources());
        context.getClientCallbacksManager().addClientTickCallback(e -> {
            ZPClientGunClientTickProcessing.INSTANCE.tick(Minecraft.getInstance(), e);
        });
        context.getClientRenderHooksManager().addItemSceneRendering1PersonHookPre(((deltaTicks, pPartialTicks, pPoseStack, pBuffer, pPlayerEntity, pCombinedLight) -> {
            ZPAbstractGunRenderer.breathEffect(pPartialTicks, pPoseStack);
        }));
        context.getClientRenderHooksManager().addSceneRenderingHook(((renderStage, partialTicks, deltaTime, pNanoTime, pRenderLevel) -> {
            if (renderStage == ZPRenderHooks.RenderStage.PRE) {
                ZPClientGunClientTickProcessing.INSTANCE.process(Minecraft.getInstance());
            }
        }));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {

    }

    @Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("guns", "ru.gltexture.zpm3.modules.guns.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPReanimateModelMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPPlayerClientDataMuzzleflash3PMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPClientPacketStackNBTCopyMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPItemMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPItemStackClDataGunSyncMixin", ZPSide.CLIENT)
    //    );
    //}

   // @Override
    public void initialize(@NotNull IModuleInitContext context) {
        context.addRecipesRegistry(new ZPGunsRecipeRegistry());
        context.addCommonZp3RegistryClass(ZPGunItems.class);
        ZPUtility.sides().onlyClient(() -> {
            context.registerForgeEventHandlerClass(ZPGunsUIEvent.class);
            context.registerForgeEventHandlerClass(ZPGunPostRenderEvent.class);
        });
        context.registerForgeEventHandlerClass(ZPGunTossEvent.class);
    }

    @Override
    public void preInitialize(@NotNull IModulePreInitContext context) {
        ZPUtility.sides().onlyClient(() -> {
            context.registerKeyBindings(new ZPGunKeyBindings());
        });
    }

    @Override
    public void postInitialize(@NotNull IModulePostInitContext context) {

    }

    private static class ZPGunsRecipeRegistry extends ZPRecipesRegistry {
        private static final List<IZPRecipeSpec> recipeToAdd = new ArrayList<>();
        private static final List<ZPRecipesController.RecipeToRemove> toRemove = new ArrayList<>();

        static {
            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPGunItems._handmade_pistol.get(), 8)
                        .pattern("F").pattern("P").pattern("I")
                        .define('F', Items.FLINT)
                        .define('P', Items.GUNPOWDER)
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.IRON_INGOT))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "_handmade_pistol"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ZPGunItems.handmade_pistol.get())
                        .pattern("III").pattern("FFP")
                        .define('F', ItemTags.LOGS)
                        .define('P', Blocks.LEVER.asItem())
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.IRON_INGOT))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "handmade_pistol"));
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
