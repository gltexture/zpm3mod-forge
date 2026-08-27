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

package ru.gltexture.zpm3.modules.armor;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModule;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModuleData;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePostInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePreInitContext;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.armor.events.client.ZPAdjustNightVisionGogglesLightMap;
import ru.gltexture.zpm3.modules.armor.events.client.ZPPlayerArmorSoundOnClientEvent;
import ru.gltexture.zpm3.modules.armor.init.ZPArmorItems;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;
import ru.gltexture.zpm3.modules.common.init.ZPSounds;
import ru.gltexture.zpm3.modules.debug.events.ZPRenderStuffEvent;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class ZPArmorModule extends ZPModule {
    public ZPArmorModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPArmorModule() {
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
        context.registerArmorSound(new ZPPlayerArmorSoundOnClientEvent.TrackedSoundLauncher() {
            @Override
            public @NotNull Supplier<SoundEvent> getSoundEvent() {
                return () -> ZPSounds.nv_goggles.get();
            }

            @Override
            public float pitch() {
                return 1.f;
            }

            @Override
            public float volume() {
                return 1.f;
            }

            @Override
            public @NotNull Predicate<LivingEntity> getEntityPredicate() {
                return ZPArmorUtil::isEntityHasNightVisionGoggles;
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {
    }

    public static void addNewLineToDraw(@NotNull ZPRenderStuffEvent.LineRequest lineRequest) {
        ZPRenderStuffEvent.addNewLineToDraw(lineRequest);
    }

    @Override
    public void initialize(@NotNull IModuleInitContext context) {
        context.addCommonZp3RegistryClass(ZPArmorItems.class);

        ZPUtility.sides().onlyClient(() -> {
            context.registerZP3EventHandlerClass(ZPAdjustNightVisionGogglesLightMap.class);
            context.registerForgeEventHandlerClass(ZPPlayerArmorSoundOnClientEvent.class);
            /*
            ZPPlayerArmorSoundOnClientEvent.registerArmorSound(new ZPPlayerArmorSoundOnClientEvent.TrackedSoundLauncher() {
                @Override
                public @NotNull Supplier<SoundEvent> getSoundEvent() {
                    return () -> ZPSounds.breath.get();
                }

                @Override
                public float pitch() {
                    return 0.5f;
                }

                @Override
                public float volume() {
                    return 0.8f;
                }

                @Override
                public @NotNull Predicate<LivingEntity> getEntityPredicate() {
                    return e -> (e instanceof Player) && (ZPArmorUtil.isEntityHasSpecialMaskForBreathEffect(e));
                }
            });
            */
        });
    }

    @Override
    public void preInitialize(@NotNull IModulePreInitContext context) {

    }

    @Override
    public void postInitialize(@NotNull IModulePostInitContext context) {

    }
}
