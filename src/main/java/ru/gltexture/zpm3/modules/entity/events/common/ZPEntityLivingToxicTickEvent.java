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

package ru.gltexture.zpm3.modules.entity.events.common;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPLivingEntityExt;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityUtil;

public class ZPEntityLivingToxicTickEvent implements ZPEventClass {
    @SubscribeEvent
    public static void entityTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide()) {
            if (entity instanceof IZPLivingEntityExt izpLivingEntityExt) {
                final int toxicTickRate = ZPEntityUtil.getEntityToxicAffectionTickRate(entity);
                if (toxicTickRate > 0) {
                    if (entity.tickCount % toxicTickRate == 0) {
                        izpLivingEntityExt.zpm3forge$addIntoxicationLevel(1);
                    }
                } else {
                    if (entity.tickCount % 10 == 0 && izpLivingEntityExt.zpm3forge$getIntoxicationLevel() > 0) {
                        izpLivingEntityExt.zpm3forge$addIntoxicationLevel(-1);
                    }
                }
                if (entity.tickCount % 20 == 0 && izpLivingEntityExt.zpm3forge$getIntoxicationLevel() > 260) {
                    entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 1200, izpLivingEntityExt.zpm3forge$getIntoxicationLevel() > 360 ? 1 : 0, false, true));
                    entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1200, 0, false, true));
                }
            }
        }
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
