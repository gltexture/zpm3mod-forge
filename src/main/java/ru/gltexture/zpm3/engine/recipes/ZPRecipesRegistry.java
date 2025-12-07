package ru.gltexture.zpm3.engine.recipes;

import java.util.Collection;

public abstract class ZPRecipesRegistry {
    public abstract Collection<IZPRecipeSpec> getRecipesToRegister();
    public abstract Collection<ZPRecipesController.RecipeToRemove> getRecipesToRemove();
}