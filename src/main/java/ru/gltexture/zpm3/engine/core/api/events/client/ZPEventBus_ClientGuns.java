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

package ru.gltexture.zpm3.engine.core.api.events.client;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventDef;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.modules.guns.processing.logic.ZPDefaultGunLogicFunctions;

public abstract class ZPEventBus_ClientGuns {
    @OnlyIn(Dist.CLIENT)
    public static final class ClientGunEmptyShotEvent extends ZPEventDef.Cancellable implements ZPEventDef.IEvent {
        private final Player player;
        private final ZPBaseGun item;
        private final ItemStack itemStack;
        private final boolean isRightHand;

        public ClientGunEmptyShotEvent(@NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
            this.player = player;
            this.item = item;
            this.itemStack = itemStack;
            this.isRightHand = isRightHand;
        }

        public @NotNull Player getPlayer() {
            return this.player;
        }

        public @NotNull ZPBaseGun getItem() {
            return this.item;
        }

        public @NotNull ItemStack getItemStack() {
            return this.itemStack;
        }

        public boolean isRightHand() {
            return this.isRightHand;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Deprecated(forRemoval = true)
    public static final class ClientGunShotEvent extends ZPEventDef.Cancellable implements ZPEventDef.IEvent {
        private final Player player;
        private final ZPBaseGun item;
        private final ItemStack itemStack;
        private final ZPDefaultGunLogicFunctions.GunClientData_Shot gunFXData;

        public ClientGunShotEvent(@NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, @NotNull ZPDefaultGunLogicFunctions.GunClientData_Shot gunFXData) {
            this.player = player;
            this.item = item;
            this.itemStack = itemStack;
            this.gunFXData = gunFXData;
        }

        public @NotNull Player getPlayer() {
            return this.player;
        }

        public @NotNull ZPBaseGun getItem() {
            return this.item;
        }

        public @NotNull ItemStack getItemStack() {
            return this.itemStack;
        }

        public @NotNull ZPDefaultGunLogicFunctions.GunClientData_Shot getGunFXData() {
            return this.gunFXData;
        }
    }
}
/*
EventLauncher.pushEvent(new ZPEventBus_ClientRendering.RenderOGLSceneEvent(this, frameTicking, ZPEventBus_ClientRendering.Run.POST, toRenderObjects, toRenderLiquids), TODO);

    public static final class Class123 implements IEvent {

    }
 */