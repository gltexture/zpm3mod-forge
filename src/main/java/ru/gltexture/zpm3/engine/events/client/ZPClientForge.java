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
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.engine.client.rendering.ZPClientManager;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.crosshair.ZPClientCrosshairRecoilManager;
import ru.gltexture.zpm3.engine.client.rendering.postfx.ZPPostFXChain;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public final class ZPClientForge {
    private static float LAST_RENDER_DELTA_TIME;
    public static float RENDER_DELTA_TIME;

    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
    }

    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
    }

    @SubscribeEvent
    public void onRenderWorld(RenderLevelStageEvent event) {
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ((ZPClientCallbacksManager) ZombiePlague3.getClientManager().getCallbacksManager()).tickClientCallbacks(event.phase);
        if (event.phase == TickEvent.Phase.START) {
            ZPClientCrosshairRecoilManager.onClientTick(Minecraft.getInstance());
            ((ZPPostFXChain) ZombiePlague3.getClientManager().getPostFXChain()).clientPreTick();
        }
        if (event.phase == TickEvent.Phase.END) {
            ((ZPPostFXChain) ZombiePlague3.getClientManager().getPostFXChain()).clientPostTick();
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            double currentTime = GLFW.glfwGetTime();
            ZPClientForge.RENDER_DELTA_TIME = (float) (currentTime - ZPClientForge.LAST_RENDER_DELTA_TIME);
            ZPClientForge.LAST_RENDER_DELTA_TIME = (float) currentTime;
        }
    }
}
