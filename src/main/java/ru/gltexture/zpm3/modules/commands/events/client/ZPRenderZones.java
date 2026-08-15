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

package ru.gltexture.zpm3.modules.commands.events.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.engine.zones.ZPZoneManager;
import ru.gltexture.zpm3.modules.commands.imgui.ZPImGuiCreativeUtilityUI;
import ru.gltexture.zpm3.modules.debug.render.ZPRenderLines;

import java.util.Collection;

public class ZPRenderZones implements ZPForgeEventHandlerClass {
    public ZPRenderZones() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (Minecraft.getInstance().player != null) {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS && ZPImGuiCreativeUtilityUI.ENABLE_UTILITY) {
                //GL46.glDisable(GL46.GL_DEPTH_TEST);
                final Collection<ZPZoneManager.Zone> zones = ZPZoneManager.INSTANCE.getAllZonesOnLevel(Minecraft.getInstance().player.level());
                {
                    final Vector3f stVec = new Vector3f(ZPImGuiCreativeUtilityUI.inputStart[0], ZPImGuiCreativeUtilityUI.inputStart[1], ZPImGuiCreativeUtilityUI.inputStart[2]);
                    final Vector3f ndVec = new Vector3f(ZPImGuiCreativeUtilityUI.inputEnd[0], ZPImGuiCreativeUtilityUI.inputEnd[1], ZPImGuiCreativeUtilityUI.inputEnd[2]);
                    final Vector3f min = new Vector3f(Math.min(stVec.x(), ndVec.x()), Math.min(stVec.y(), ndVec.y()), Math.min(stVec.z(), ndVec.z()));
                    final Vector3f max = new Vector3f(Math.max(stVec.x(), ndVec.x()), Math.max(stVec.y(), ndVec.y()), Math.max(stVec.z(), ndVec.z()));
                    {
                        ZPRenderLines.drawAABB(event.getPoseStack(), min.add(0.5f, 0.0f, 0.5f), max.add(0.5f, 0.5f, 0.5f), 0.0f, 1.0f, 1.0f, 1.0f);
                    }
                }
                if (zones == null || zones.isEmpty()) {
                    return;
                }
                zones.forEach(e -> {
                    final Vector3f color = ZPImGuiCreativeUtilityUI.currentSelectedZoneID != null && ZPImGuiCreativeUtilityUI.currentSelectedZoneID.equals(e.uniqueId()) ? new Vector3f(1.0f, 0.0f, 0.0f) : new Vector3f(1.0f);
                    ZPRenderLines.drawAABB(event.getPoseStack(), new Vector3f(e.start()).add(0.5f, 0.0f, 0.5f), new Vector3f(e.end()).add(0.5f, 0.5f, 0.5f), color.x, color.y, color.z, 1.0f);
                });
                //GL46.glEnable(GL46.GL_DEPTH_TEST);
            }
        }
    }
}