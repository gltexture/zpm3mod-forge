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
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.recipes.IZPRecipeSpec;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.debug.events.ZPRenderStuffEvent;
import ru.gltexture.zpm3.modules.melee_throwables_tools.events.client.ZPPlayerClientTickBroomEvent;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.ZPMeleeThrowableToolsItems;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.melee.ZPBroomSword;
import ru.gltexture.zpm3.modules.melee_throwables_tools.tiers.ZPCommonToolMeleeTiers;
import ru.gltexture.zpm3.modules.player.events.client.*;

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
    public void fml_commonSetupEvent() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void fml_clientSetupEvent() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {
    }

    public static void addNewLineToDraw(@NotNull ZPRenderStuffEvent.LineRequest lineRequest) {
        ZPRenderStuffEvent.addNewLineToDraw(lineRequest);
    }

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        ZPUtility.sides().onlyClient(() -> {
            moduleEntry.addMinecraftEventClass(ZPPlayerClientTickBroomEvent.class);
        });
        moduleEntry.addRecipesRegistry(new ZPMeleeThrowableToolsModule.ZPMeleeThrowablesToolsRecipeRegistry());
        moduleEntry.addTier(ZPCommonToolMeleeTiers.values());
        moduleEntry.addMinecraftRegistryClass(ZPMeleeThrowableToolsItems.class);
    }

    @Override
    public void preInitialize() {

    }

    @Override
    public void postInitialize() {
        ZPUtility.sides().onlyClient(() -> {
            ZPRenderHooksManager.INSTANCE.addItemRendering3PersonHook(() -> ZPMeleeThrowableToolsItems.broom.get(), ((itemInHandRenderer, deltaTicks, entityModel, pLivingEntity, pItemStack, pDisplayContext, pArm, pPoseStack, pBuffer, pPackedLight) -> {
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
        });
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
