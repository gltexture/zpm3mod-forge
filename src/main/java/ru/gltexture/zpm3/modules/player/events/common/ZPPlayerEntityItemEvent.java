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

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.modules.net_pack.ZPNetPackModule;
import ru.gltexture.zpm3.modules.net_pack.data.vars.ZPNetDataBoolean;

public class ZPPlayerEntityItemEvent implements ZPForgeEventHandlerClass {
    @SubscribeEvent
    public static void exec(@NotNull EntityItemPickupEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            final boolean pickUpOnKey = ZPWorldConfig.ALLOW_ITEMS_PICKUP_ON_KEY.getVar() && ZombiePlague3.netServer().getNetStaticDataSyncer().getPack(player).flatMap(pack -> pack.getVar(ZPNetPackModule.CtoS__PICK_UP_ON_KEY)).orElse(new ZPNetDataBoolean(ZPWorldConfig.ALLOW_ITEMS_PICKUP_ON_KEY.getVar())).getValue();
            if (ZPWorldConfig.ALLOW_ITEMS_PICKUP_ON_KEY.getVar() && pickUpOnKey) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDropItem(ItemTossEvent event) {
        Player player = event.getPlayer();
        ItemEntity droppedItem = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer) {
            final boolean pickUpOnKey = ZPWorldConfig.ALLOW_ITEMS_PICKUP_ON_KEY.getVar() && ZombiePlague3.netServer().getNetStaticDataSyncer().getPack(serverPlayer).flatMap(pack -> pack.getVar(ZPNetPackModule.CtoS__PICK_UP_ON_KEY)).orElse(new ZPNetDataBoolean(ZPWorldConfig.ALLOW_ITEMS_PICKUP_ON_KEY.getVar())).getValue();
            droppedItem.setPickUpDelay(pickUpOnKey ? 10 : 20);
            float dot = (player.getLookAngle().toVector3f().dot(new Vector3f(0.0f, 1.0f, 0.0f)) + 1.0f) / 2.0f;
            dot = Math.max(dot, 0.25f);
            Vec3 vecMovement = new Vec3(new Vector3f(player.getLookAngle().toVector3f()).mul(0.75f * dot));
            droppedItem.setDeltaMovement(vecMovement);
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
