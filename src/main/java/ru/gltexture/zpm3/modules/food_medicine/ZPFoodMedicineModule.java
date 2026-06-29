package ru.gltexture.zpm3.modules.food_medicine;

import net.minecraft.data.recipes.RecipeCategory;
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
import ru.gltexture.zpm3.modules.food_medicine.init.ZPFoodMedicineItems;

import java.util.*;

public class ZPFoodMedicineModule extends ZPModule {
    public ZPFoodMedicineModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPFoodMedicineModule() {
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
        moduleEntry.addRecipesRegistry(new ZPFoodMedicineRecipeRegistry());
        moduleEntry.addRegistryClass(ZPFoodMedicineItems.class);
        ZPUtility.sides().onlyClient(() -> {
        });
    }

    @Override
    public void preInitialize() {

    }

    @Override
    public void postInitialize() {

    }

    private static class ZPFoodMedicineRecipeRegistry extends ZPRecipesRegistry {
        private static final List<IZPRecipeSpec> recipeToAdd = new ArrayList<>();
        private static final List<ZPRecipesController.RecipeToRemove> toRemove = new ArrayList<>();

        static {
            recipeToAdd.add((writer -> {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.EGG), RecipeCategory.FOOD, ZPFoodMedicineItems.fried_egg.get(), 0.25f, 300)
                        .unlockedBy("has_rf", IZPRecipeSpec.has(Items.EGG))
                        .save(writer, ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "egg_smelting"));
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
