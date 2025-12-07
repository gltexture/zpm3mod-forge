package ru.gltexture.zpm3.engine.helpers.gen.providers;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.BiomeData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ZPRecipeDataProvider extends RecipeProvider {
    public ZPRecipeDataProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        ZombiePlague3.getRecipesController().getRegistries().forEach(e -> e.getRecipesToRegister().forEach(s -> s.writeJsonRecipe(writer)));
    }
}
