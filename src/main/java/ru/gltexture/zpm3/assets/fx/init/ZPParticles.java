package ru.gltexture.zpm3.assets.fx.init;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.fx.particles.ParticleColoredCloud;
import ru.gltexture.zpm3.assets.fx.particles.types.ColoredSmokeType;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.helpers.ZPParticleRenderHelper;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;

public class ZPParticles extends ZPRegistry<ParticleType<?>> {
    public static RegistryObject<ColoredSmokeType> colored_cloud;

    public ZPParticles() {
        super(ForgeRegistries.PARTICLE_TYPES, ZPRegistryConveyor.Target.FX);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<ParticleType<? extends ParticleOptions>> regSupplier) {
        ZPParticles.colored_cloud = regSupplier.register("colored_cloud", () -> new ColoredSmokeType(false)).postConsume(Dist.CLIENT, (e) -> {
            ZPParticleRenderHelper.matchParticleRendering(e, ParticleColoredCloud.ColoredSmokeParticleProvider::new);
        }).registryObject();
    }

    @Override
    protected void postRegister(String name, RegistryObject<ParticleType<?>> object) {
        super.postRegister(name, object);
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}