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

package ru.gltexture.zpm3.engine.events.client;

import net.minecraft.client.Minecraft;
import ru.gltexture.zpm3.engine.client.rendering.ZPClientManager;
import ru.gltexture.zpm3.engine.client.rendering.crosshair.ZPClientCrosshairRecoilManager;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventDef;
import ru.gltexture.zpm3.engine.core.api.events.ZombiePlagueEvent;
import ru.gltexture.zpm3.engine.core.api.events.client.ZPEventBus_ClientRendering;

public class ZPClientZp3 {
    @ZombiePlagueEvent
    public static void sceneRendering(ZPEventBus_ClientRendering.SceneRenderEvent event) {
        if (event.getRenderStage() == ZPEventDef.Run.POST) {
            ((ZPClientManager) ZombiePlague3.getClientManager()).renderImGui(event.getDeltaTime());
        }
        if (event.getRenderStage() == ZPEventDef.Run.PRE) {
            ZPClientCrosshairRecoilManager.onRenderTick(event.getDeltaTime(), Minecraft.getInstance());
        }
    }
}