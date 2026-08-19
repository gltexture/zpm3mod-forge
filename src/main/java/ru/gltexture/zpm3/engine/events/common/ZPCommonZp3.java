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

package ru.gltexture.zpm3.engine.events.common;

import ru.gltexture.zpm3.engine.client.rendering.callbacks.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.events.ZombiePlagueEvent;
import ru.gltexture.zpm3.engine.core.api.events.common.ZPEventBus_Guns;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.modules.guns.processing.logic.ZPDefaultGunLogicFunctions;

public class ZPCommonZp3 {
    /*
    @Deprecated(forRemoval = true)
    @ZombiePlagueEvent
    public static void gunShot(ZPEventBus_Guns.ClientGunShotEvent event) {
        ZPDefaultGunLogicFunctions.GunClientData_Shot data = event.getGunFXData();
        ((ZPClientCallbacksManager) ZombiePlague3.getClientManager().getCallbacksManager()).triggerGunShots(event.getPlayer(), (ZPBaseGun) event.getItem(), event.getItemStack(), data);
    }

    @Deprecated(forRemoval = true)
    @ZombiePlagueEvent
    public static void gunReloadStart(ZPEventBus_Guns.GunReloadStartEvent event) {
        ZPDefaultGunLogicFunctions.GunActionData_Reload data = event.getGunFXData();
        ((ZPClientCallbacksManager) ZombiePlague3.getClientManager().getCallbacksManager()).triggerReloadingStart(event.getPlayer(), (ZPBaseGun) event.getItem(), event.getItemStack(), data);
    }
     */
}
