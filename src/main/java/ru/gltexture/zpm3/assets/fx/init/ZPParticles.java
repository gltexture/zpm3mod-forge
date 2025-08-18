package ru.gltexture.zpm3.assets.fx.init;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.fx.particles.ParticleColoredCloud;
import ru.gltexture.zpm3.assets.fx.particles.ParticleGunShell;
import ru.gltexture.zpm3.assets.fx.particles.types.ColoredSmokeType;
import ru.gltexture.zpm3.assets.fx.particles.types.GunShellType;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public class ZPParticles extends ZPRegistry<ParticleType<?>> {
    public static RegistryObject<ColoredSmokeType> colored_cloud;
    public static RegistryObject<GunShellType> gun_shell;

    public ZPParticles() {
        super(ForgeRegistries.PARTICLE_TYPES, ZPRegistryConveyor.Target.FX);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<ParticleType<? extends ParticleOptions>> regSupplier) {
        ZPParticles.colored_cloud = regSupplier.register("colored_cloud", () -> new ColoredSmokeType(false)).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.particles().matchParticleRenderingSet(e, ParticleColoredCloud.ColoredSmokeParticleProvider::new);
            utils.particles().addParticlesTexturesData(e, "minecraft:generic_", 8);
        }).registryObject();

        ZPParticles.gun_shell = regSupplier.register("gun_shell", () -> new GunShellType(false)).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.particles().matchParticleRenderingSet(e, ParticleGunShell.GunShellParticleProvider::new);
            utils.particles().addParticlesTexturesData(e, "zpm3:gun_shell", 1);
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