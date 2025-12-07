package ru.gltexture.zpm3.engine.mixins.impl.common;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.mixins.ext.IZPRecipesManagerExt;
import ru.gltexture.zpm3.engine.recipes.ZPRecipesController;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(RecipeManager.class)
public abstract class ZPRecipeManagerMixin implements IZPRecipesManagerExt {
    @Shadow private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;
    @Shadow private Map<ResourceLocation, Recipe<?>> byName;
    @Shadow private boolean hasErrors;

    @Override
    public Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> getRecipes() {
        return this.recipes;
    }

    public void removeRecipes(@NotNull Collection<ZPRecipesController.RecipeToRemove> toRemove) {
        this.hasErrors = false;
        Set<ResourceLocation> removeKeys = toRemove.stream().map(ZPRecipesController.RecipeToRemove::resourceLocation).collect(Collectors.toSet());
        Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> newRecipes = new HashMap<>();
        Map<ResourceLocation, Recipe<?>> newByName = new HashMap<>();
        for (Map.Entry<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> entry : this.recipes.entrySet()) {
            Map<ResourceLocation, Recipe<?>> filtered = entry.getValue().entrySet().stream().filter(e -> !removeKeys.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            newRecipes.put(entry.getKey(), filtered);
        }
        this.byName.entrySet().stream().filter(e -> !removeKeys.contains(e.getKey())).forEach(e -> newByName.put(e.getKey(), e.getValue()));
        this.recipes = ImmutableMap.copyOf(newRecipes);
        this.byName = ImmutableMap.copyOf(newByName);
    }
}
