package ru.gltexture.zpm3.assets.mob_effects.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.mob_effects.instances.*;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public class ZPMobEffects extends ZPRegistry<MobEffect> {
    public static RegistryObject<ZPBleedingEffect> bleeding;
    public static RegistryObject<ZPZombiePlagueEffect> zombie_plague;
    public static RegistryObject<ZPFractureEffect> fracture;
    public static RegistryObject<ZPAdrenalineEffect> adrenaline;
    public static RegistryObject<ZPBetterVisionEffect> better_vision;

    public ZPMobEffects() {
        super(ZPRegistryConveyor.Target.MOB_EFFECT);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<MobEffect> regSupplier) {
        ZPMobEffects.bleeding = regSupplier.register("bleeding", () -> new ZPBleedingEffect(MobEffectCategory.HARMFUL, 0xff0000)).end();

        ZPMobEffects.zombie_plague = regSupplier.register("zombie_plague", () -> new ZPZombiePlagueEffect(MobEffectCategory.HARMFUL, 0x3cff11)).end();

        ZPMobEffects.fracture = regSupplier.register("fracture", () -> new ZPFractureEffect(MobEffectCategory.HARMFUL, 0xffffff)).end();

        ZPMobEffects.adrenaline = regSupplier.register("adrenaline", () -> new ZPAdrenalineEffect(MobEffectCategory.BENEFICIAL, 0xff00ff)).end();

        ZPMobEffects.better_vision = regSupplier.register("better_vision", () -> new ZPBetterVisionEffect(MobEffectCategory.BENEFICIAL, 0x00ffff)).end();
    }

    @Override
    protected void postRegister(String name, RegistryObject<MobEffect> object) {
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