package ru.gltexture.zpm3.modules.misc_items;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.recipes.IZPRecipeSpec;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.debug.events.ZPRenderStuffEvent;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;

import java.util.*;

public class ZPMiscItemsModule extends ZPModule {
    public ZPMiscItemsModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPMiscItemsModule() {
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
        moduleEntry.addRecipesRegistry(new ZPMiscItemsModule.ZPMiscItemsRecipeRegistry());
        moduleEntry.addMinecraftRegistryClass(ZPMiscItems.class);
        ZPUtility.sides().onlyClient(() -> {
        });
    }

    @Override
    public void preInitialize() {

    }

    @Override
    public void postInitialize() {

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
                        .define('S', ZPMiscItems.table_material.get())
                        .unlockedBy("has_rf", IZPRecipeSpec.has(ZPMiscItems.table_material.get()))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "shelves_material"));
            }));

            recipeToAdd.add((writer -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZPMiscItems.table_material.get())
                        .pattern("SS").pattern("SS")
                        .define('S', Items.STICK)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.STICK))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "table_material"));
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
