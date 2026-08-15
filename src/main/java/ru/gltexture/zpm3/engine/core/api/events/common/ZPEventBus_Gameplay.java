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

package ru.gltexture.zpm3.engine.core.api.events.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventDef;
import ru.gltexture.zpm3.modules.guns.processing.logic.ZPDefaultGunLogicFunctions;

public abstract class ZPEventBus_Gameplay {
    public static final class GunShotEvent implements ZPEventDef.IEvent {
        private final Player player;
        private final Item item;
        private final ItemStack itemStack;
        private final ZPDefaultGunLogicFunctions.GunFXData_Shot gunFXData;

        public GunShotEvent(@NotNull Player player, @NotNull Item item, @NotNull ItemStack itemStack, @NotNull ZPDefaultGunLogicFunctions.GunFXData_Shot gunFXData) {
            this.player = player;
            this.item = item;
            this.itemStack = itemStack;
            this.gunFXData = gunFXData;
        }

        public @NotNull Player getPlayer() {
            return this.player;
        }

        public @NotNull Item getItem() {
            return this.item;
        }

        public @NotNull ItemStack getItemStack() {
            return this.itemStack;
        }

        public @NotNull ZPDefaultGunLogicFunctions.GunFXData_Shot getGunFXData() {
            return this.gunFXData;
        }
    }

    public static final class GunReloadStartEvent implements ZPEventDef.IEvent {
        private final Player player;
        private final Item item;
        private final ItemStack itemStack;
        private final ZPDefaultGunLogicFunctions.GunFXData_Reload gunFXData;

        public GunReloadStartEvent(@NotNull Player player, @NotNull Item item, @NotNull ItemStack itemStack, @NotNull ZPDefaultGunLogicFunctions.GunFXData_Reload gunFXData) {
            this.player = player;
            this.item = item;
            this.itemStack = itemStack;
            this.gunFXData = gunFXData;
        }

        public @NotNull Player getPlayer() {
            return this.player;
        }

        public @NotNull Item getItem() {
            return this.item;
        }

        public @NotNull ItemStack getItemStack() {
            return this.itemStack;
        }

        public @NotNull ZPDefaultGunLogicFunctions.GunFXData_Reload getGunFXData() {
            return this.gunFXData;
        }
    }
}
/*
EventLauncher.pushEvent(new ZPEventBus_ClientRendering.RenderOGLSceneEvent(this, frameTicking, ZPEventBus_ClientRendering.Run.POST, toRenderObjects, toRenderLiquids), TODO);

    public static final class Class123 implements IEvent {

    }
 */