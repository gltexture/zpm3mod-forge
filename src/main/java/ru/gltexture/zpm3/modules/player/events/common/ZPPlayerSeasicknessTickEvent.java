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

package ru.gltexture.zpm3.modules.player.events.common;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPEntityConfig;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityUtil;
import ru.gltexture.zpm3.modules.entity.util.ZPLivingStat;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;
import ru.gltexture.zpm3.modules.player.util.ZPPlayerStat;

public class ZPPlayerSeasicknessTickEvent implements ZPForgeEventHandlerClass {
    public ZPPlayerSeasicknessTickEvent() {
    }


    @SubscribeEvent
    public static void exec(TickEvent.@NotNull PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.START) {
            if (!event.player.level().isClientSide()) {
                {
                    final FluidState fluidState = player.level().getFluidState(BlockPos.containing(player.getEyePosition()));
                    {
                        ZPEntityUtil.applySeasicknessEffectsOnPlayer(player, ZPPlayerStat.SEASICKNESS.get(event.player));
                        if (!event.player.isCreative() && fluidState.is(FluidTags.WATER)) {
                            if (ZPEntityConfig.ADD_SEASICKNESS_FACTOR_PER_TICK.getVar() > 0) {
                                if (!ZPArmorUtil.isFullAqualungBreathingRightNow(player) && event.player.tickCount % ZPEntityConfig.ADD_SEASICKNESS_FACTOR_PER_TICK.getVar() == 0) {
                                    ZPPlayerStat.SEASICKNESS.add(event.player, 1);
                                    return;
                                }
                            }
                        }
                    }
                }
                 {
                    if (ZPPlayerStat.SEASICKNESS.get(event.player) > 0) {
                        if (event.player.tickCount % 10 == 0) {
                            ZPPlayerStat.SEASICKNESS.decrease(event.player, 1);
                            //System.out.println(izpLivingEntityExt.zpm3forge$getRadiationLevel());
                        }
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
