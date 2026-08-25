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

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.engine.nbt.ZPTagID;
import ru.gltexture.zpm3.engine.nbt.entity.ZPEntityNBT;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPLivingEntityExt;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityUtil;
import ru.gltexture.zpm3.modules.entity.util.ZPLivingStat;

import java.util.Objects;

public class ZPEntityLivingRadiationTickEvent implements ZPForgeEventHandlerClass {
    public ZPEntityLivingRadiationTickEvent() {
    }

    @SubscribeEvent
    public static void exec(LivingEvent.@NotNull LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide()) {
            if (entity.tickCount % 20 == 0) {
                ZPEntityUtil.applyRadiationEffects(entity, ZPLivingStat.RADIATION.get(entity));
            }
            final int radTickRate = ZPEntityUtil.getEntityRadAffectionTickRate(entity);
            if (radTickRate > 0) {
                if (ZPLivingStat.RADIATION.get(entity) < 100) {
                    if (entity.tickCount % radTickRate == 0) {
                        ZPLivingStat.RADIATION.add(entity, 1);
                        //System.out.println(izpLivingEntityExt.zpm3forge$getRadiationLevel());
                    }
                }
            } else {
                if (ZPLivingStat.RADIATION.get(entity) > 0) {
                    if (entity.tickCount % 10 == 0) {
                        ZPLivingStat.RADIATION.decrease(entity, 1);
                        //System.out.println(izpLivingEntityExt.zpm3forge$getRadiationLevel());
                    }
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
