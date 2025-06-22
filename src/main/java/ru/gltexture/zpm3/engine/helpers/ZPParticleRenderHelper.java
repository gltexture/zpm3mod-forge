package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
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
    private static final Set<ZPParticleRenderHelper.ParticleRenderPair<? extends ParticleOptions>> particleRendererPairs = new HashSet<>();

    public static <T extends ParticleOptions> void matchParticleRendering(@NotNull RegistryObject<? extends ParticleType<T>> type, @NotNull Function<SpriteSet, @NotNull ParticleProvider<T>> particleProvider) {
        ZPParticleRenderHelper.particleRendererPairs.add(new ZPParticleRenderHelper.ParticleRenderPair<>(type, particleProvider));
    }

    public static Set<ZPParticleRenderHelper.ParticleRenderPair<? extends ParticleOptions>> getParticleRendererPairs() {
        return ZPParticleRenderHelper.particleRendererPairs;
    }

    public static void clear() {
        ZPParticleRenderHelper.particleRendererPairs.clear();
    }

    public record ParticleRenderPair <T extends ParticleOptions> (@NotNull RegistryObject<? extends ParticleType<T>> type, @NotNull Function<SpriteSet, @NotNull ParticleProvider<T>> particleProvider) {}
}
