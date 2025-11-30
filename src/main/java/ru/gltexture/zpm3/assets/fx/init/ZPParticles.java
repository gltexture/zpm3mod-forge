package ru.gltexture.zpm3.assets.fx.init;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.fx.particles.ZPDefaultColoredParticle;
import ru.gltexture.zpm3.assets.fx.particles.ParticleGunShell;
import ru.gltexture.zpm3.assets.fx.particles.options.GunShellOptions;
import ru.gltexture.zpm3.assets.fx.particles.types.ColoredDefaultParticleType;
import ru.gltexture.zpm3.assets.fx.particles.types.GunShellType;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

import java.util.Objects;

public class ZPParticles extends ZPRegistry<ParticleType<?>> {
    public static RegistryObject<ColoredDefaultParticleType> colored_cloud;
    public static RegistryObject<ColoredDefaultParticleType> blood_fx;
    public static RegistryObject<GunShellType> gun_shell;

    public ZPParticles() {
        super(ZPRegistryConveyor.Target.PARTICLE_TYPE);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<ParticleType<? extends ParticleOptions>> regSupplier) {
        ZPParticles.blood_fx = regSupplier.register("blood_fx", () -> new ColoredDefaultParticleType(false)).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.particles().matchParticleRenderingSet(e, ZPDefaultColoredParticle.ColoredDefaultParticleProvider::new);
            utils.particles().addParticlesTexturesData(e, "zpm3:blood", 4);
        }).end();

        ZPParticles.colored_cloud = regSupplier.register("colored_cloud", () -> new ColoredDefaultParticleType(false)).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.particles().matchParticleRenderingSet(e, ZPDefaultColoredParticle.ColoredDefaultParticleProvider::new);
            utils.particles().addParticlesTexturesData(e, "minecraft:generic_", 8);
        }).end();

        ZPParticles.gun_shell = regSupplier.register("gun_shell", () -> new GunShellType(false)).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.particles().matchParticleRenderingSet(e, ParticleGunShell.GunShellParticleProvider::new);
            utils.particles().addParticlesTexturesData(e, "zpm3:gun_shell", 1);
        }).end();
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