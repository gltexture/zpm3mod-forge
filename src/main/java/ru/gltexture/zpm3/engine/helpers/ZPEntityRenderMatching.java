package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public abstract class ZPEntityRenderMatching {
    private static final Set<RenderPair<? extends Entity>> entityRendererPairs = new HashSet<>();

    public static <T extends Entity> void match(@NotNull RegistryObject<EntityType<T>> registryObject, @NotNull EntityRendererProvider<T> entityRenderer) {
        ZPEntityRenderMatching.entityRendererPairs.add(new RenderPair<>(registryObject, entityRenderer));
    }

    public static Set<RenderPair<? extends Entity>> getEntityRendererPairs() {
        return ZPEntityRenderMatching.entityRendererPairs;
    }

    public static void clear() {
        ZPEntityRenderMatching.entityRendererPairs.clear();
    }

    public record RenderPair<T extends Entity> (RegistryObject<EntityType<T>> registryObject, EntityRendererProvider<T> provider) {}
}
