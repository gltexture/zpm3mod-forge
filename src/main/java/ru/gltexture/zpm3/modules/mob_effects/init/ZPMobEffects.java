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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPLivingEntityExt;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityStat;
import ru.gltexture.zpm3.modules.entity.util.ZPLivingStat;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPFakeClientEffect;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPLocalPlayerFakeEffectsManager;
import ru.gltexture.zpm3.modules.mob_effects.events.client.ZPFakeEffectsTickEvent;
import ru.gltexture.zpm3.modules.mob_effects.instances.*;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.modules.player.util.ZPPlayerStat;

import java.util.Optional;

public class ZPMobEffects extends ZPCommonRegistry<MobEffect> {
    public static @OnlyIn(Dist.CLIENT) ZPFakeClientEffect fakeRadiation = new ZPFakeClientEffect(
            MobEffectCategory.HARMFUL,
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "fake_rad"),
            () -> ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/mob_effects/fake/radiation.png"),
            0xffc965);

    public static @OnlyIn(Dist.CLIENT) ZPFakeClientEffect fakeAcid = new ZPFakeClientEffect(
            MobEffectCategory.HARMFUL,
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "fake_acid"),
            () -> ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/mob_effects/fake/acid.png"),
            0xffc965);

    public static @OnlyIn(Dist.CLIENT) ZPFakeClientEffect fakeIntoxication = new ZPFakeClientEffect(
            MobEffectCategory.HARMFUL,
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "fake_intoxicaton"),
            () -> ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/mob_effects/fake/intoxication.png"),
            0xffc965);

    public static @OnlyIn(Dist.CLIENT) ZPFakeClientEffect fakeSeasickness = new ZPFakeClientEffect(
            MobEffectCategory.HARMFUL,
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "fake_seasickness"),
            () -> ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/mob_effects/fake/seasickness.png"),
            0xffc965);

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
        ZPUtility.sides().onlyClient(() -> {
            ZPLocalPlayerFakeEffectsManager.INSTANCE.createConditionToApplyFakeEffect(ZPMobEffects.fakeRadiation, (localPlayer -> {
                {
                    final int rad = ZPLivingStat.RADIATION.get(localPlayer);
                    if (rad <= 0) {
                        return Optional.empty();
                    }
                    int amplifier = 0;
                    if (rad >= 20) {
                        amplifier = 1;
                    }
                    if (rad >= 40) {
                        amplifier = 2;
                    }
                    if (rad >= 60) {
                        amplifier = 3;
                    }
                    if (rad >= 80) {
                        amplifier = 4;
                    }
                    if (rad >= 95) {
                        amplifier = 5;
                    }
                    return Optional.of(new ZPLocalPlayerFakeEffectsManager.ZPFakeEffectSetOnPlayerCondition.Data(amplifier));
                }
            }));

            ZPLocalPlayerFakeEffectsManager.INSTANCE.createConditionToApplyFakeEffect(
                    ZPMobEffects.fakeAcid,
                    localPlayer -> {
                        final int level = ZPEntityStat.ACID.get(localPlayer);
                        if (level <= 0) {
                            return Optional.empty();
                        }
                        int amplifier = 0;
                        if (level > 120) {
                            amplifier = 1;
                        }
                        return Optional.of(new ZPLocalPlayerFakeEffectsManager.ZPFakeEffectSetOnPlayerCondition.Data(amplifier));
                    });

            ZPLocalPlayerFakeEffectsManager.INSTANCE.createConditionToApplyFakeEffect(
                    ZPMobEffects.fakeIntoxication,
                    localPlayer -> {
                        final int level = ZPLivingStat.INTOXICATION.get(localPlayer);
                        if (level < 260) {
                            return Optional.empty();
                        }
                        int amplifier = 0;
                        if (level >= 360) {
                            amplifier = 1;
                        }
                        return Optional.of(new ZPLocalPlayerFakeEffectsManager.ZPFakeEffectSetOnPlayerCondition.Data(amplifier));
                    });

            ZPLocalPlayerFakeEffectsManager.INSTANCE.createConditionToApplyFakeEffect(
                    ZPMobEffects.fakeSeasickness,
                    localPlayer -> {
                        final int level = ZPPlayerStat.SEASICKNESS.get(localPlayer);
                        if (level < 240) {
                            return Optional.empty();
                        }
                        int amplifier = 0;
                        if (level >= 300) {
                            amplifier = 2;
                        }
                        if (level >= 340) {
                            amplifier = 3;
                        }
                        return Optional.of(new ZPLocalPlayerFakeEffectsManager.ZPFakeEffectSetOnPlayerCondition.Data(amplifier));
                    });
        });

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