package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public abstract class ZPParticleRenderHelper {
    private static final Set<ParticleRenderPairSet<? extends ParticleOptions>> particleRendererPairSets = new HashSet<>();

    public static <T extends ParticleOptions> void matchParticleRenderingSet(@NotNull RegistryObject<? extends ParticleType<T>> type, @NotNull Function<SpriteSet, @NotNull ParticleProvider<T>> particleProvider) {
        ZPParticleRenderHelper.particleRendererPairSets.add(new ParticleRenderPairSet<>(type, particleProvider));
    }

    public static Set<ParticleRenderPairSet<? extends ParticleOptions>> getParticleRendererPairSets() {
        return ZPParticleRenderHelper.particleRendererPairSets;
    }

    public static void clear() {
        ZPParticleRenderHelper.particleRendererPairSets.clear();
    }

    public record ParticleRenderPairSet<T extends ParticleOptions> (@NotNull RegistryObject<? extends ParticleType<T>> type, @NotNull Function<SpriteSet, @NotNull ParticleProvider<T>> particleProvider) {}
}
