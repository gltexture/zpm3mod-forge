package ru.gltexture.zpm3.engine.objects.items.tier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record ZPTierData(@NotNull String name, @NotNull Tier tier, @NotNull List<Object> after, @NotNull List<Object> before) {
}
