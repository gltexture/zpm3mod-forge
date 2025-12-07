package ru.gltexture.zpm3.engine.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public final class ZPRecipesController {
    private final Set<ZPRecipesRegistry> registries;

    public ZPRecipesController() {
        this.registries = new HashSet<>();
    }

    public Set<ZPRecipesRegistry> getRegistries() {
        return this.registries;
    }

    public record RecipeToRemove(@NotNull RecipeType<?> recipeType, @NotNull ResourceLocation resourceLocation) { ; }
}
