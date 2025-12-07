package ru.gltexture.zpm3.engine.mixins.ext;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;

import java.util.Collection;
import java.util.Map;

public interface IZPRecipesManagerExt {
    Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> getRecipes();
    void removeRecipes(@NotNull Collection<ZPRecipesController.RecipeToRemove> toRemove);
}
