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

package ru.gltexture.zpm3.modules.mob_effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModule;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePostInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePreInitContext;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityStat;
import ru.gltexture.zpm3.modules.entity.util.ZPLivingStat;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPFakeClientEffect;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPLocalPlayerFakeEffectsManager;
import ru.gltexture.zpm3.modules.mob_effects.events.client.ZPBetterVisionLightMap;
import ru.gltexture.zpm3.modules.mob_effects.events.client.ZPFakeEffectsTickEvent;
import ru.gltexture.zpm3.modules.mob_effects.events.common.ZPEntityEffectActionsEvent;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModuleData;
import ru.gltexture.zpm3.modules.player.util.ZPPlayerStat;

import java.util.Optional;

public class ZPMobEffectsModule extends ZPModule {
    public ZPMobEffectsModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPMobEffectsModule() {
    }

    @Override
    public void commonSetup() {
    }

    @Override
    public void commonShutdown() {
        
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup(@NotNull IModuleClientSetupContext context) {
        context.createConditionToApplyFakeEffect(ZPMobEffects.fakeRadiation, (localPlayer -> {
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

        context.createConditionToApplyFakeEffect(ZPMobEffects.fakeAcid, localPlayer -> {
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

        context.createConditionToApplyFakeEffect(ZPMobEffects.fakeIntoxication, localPlayer -> {
                    final int level = ZPLivingStat.INTOXICATION.get(localPlayer);
                    if (level <= 0) {
                        return Optional.empty();
                    }
                    int amplifier = 0;
                    if (level >= 260) {
                        amplifier = 1;
                    }
                    if (level >= 360) {
                        amplifier = 2;
                    }
                    return Optional.of(new ZPLocalPlayerFakeEffectsManager.ZPFakeEffectSetOnPlayerCondition.Data(amplifier));
                });

        context.createConditionToApplyFakeEffect(ZPMobEffects.fakeSeasickness, localPlayer -> {
                    final int level = ZPPlayerStat.SEASICKNESS.get(localPlayer);
                    if (level < 10) {
                        return Optional.empty();
                    }
                    int amplifier = 0;
                    if (level >= 240) {
                        amplifier = 1;
                    }
                    if (level >= 300) {
                        amplifier = 2;
                    }
                    if (level >= 340) {
                        amplifier = 3;
                    }
                    return Optional.of(new ZPLocalPlayerFakeEffectsManager.ZPFakeEffectSetOnPlayerCondition.Data(amplifier));
                });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {
    }

    @Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("mob_effects", "ru.gltexture.zpm3.modules.mob_effects.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPEntityFracturedSprintMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPLivingEntityFracturedSprintMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPPlayerSoundsThenPlaguedMixin", ZPSide.COMMON)
    //    );
    //}

    @Override
    public void initialize(@NotNull IModuleInitContext context) {
        ZPUtility.sides().onlyClient(() -> {
            context.registerForgeEventHandlerClass(ZPFakeEffectsTickEvent.class);
            context.registerZP3EventHandlerClass(ZPBetterVisionLightMap.class);
        });
        context.addCommonZp3RegistryClass(ZPMobEffects.class);
        context.registerForgeEventHandlerClass(ZPEntityEffectActionsEvent.class);
    }

    @Override
    public void preInitialize(@NotNull IModulePreInitContext context) {

    }

    @Override
    public void postInitialize(@NotNull IModulePostInitContext context) {

    }
}
