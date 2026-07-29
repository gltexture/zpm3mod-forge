/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.mob_effects.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.modules.mob_effects.instances.*;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;

public class ZPMobEffects extends ZPCommonRegistry<MobEffect> {
    public static RegistryObject<ZPBleedingEffect> bleeding;
    public static RegistryObject<ZPZombiePlagueEffect> zombie_plague;
    public static RegistryObject<ZPFractureEffect> fracture;
    public static RegistryObject<ZPAdrenalineEffect> adrenaline;
    public static RegistryObject<ZPBetterVisionEffect> better_vision;
    public static RegistryObject<ZPAntiRadiationEffect> radiation_protection;
    public static RegistryObject<ZPImmuneEffect> immune;

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
        ZPMobEffects.radiation_protection = regSupplier.register("radiation_protection", () -> new ZPAntiRadiationEffect(MobEffectCategory.BENEFICIAL, 0xffff00)).end();
        ZPMobEffects.immune = regSupplier.register("immune", () -> new ZPImmuneEffect(MobEffectCategory.BENEFICIAL, 0x44ff44)).end();
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