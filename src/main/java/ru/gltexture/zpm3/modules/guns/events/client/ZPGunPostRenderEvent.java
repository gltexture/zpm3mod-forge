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

package ru.gltexture.zpm3.modules.guns.events.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.rendering.IZPClientManager;
import ru.gltexture.zpm3.modules.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.modules.guns.rendering.ZPGunLayersProcessing;
import ru.gltexture.zpm3.modules.guns.rendering.fx.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.modules.guns.rendering.tracer.ZPBulletTracerManager;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;

@OnlyIn(Dist.CLIENT)
public class ZPGunPostRenderEvent implements ZPForgeEventHandlerClass {

    @SubscribeEvent
    public static void exec(@NotNull RenderLevelStageEvent renderLevelStageEvent) {
        if (renderLevelStageEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            ZPGunLayersProcessing.postRender((ZPDefaultGunMuzzleflashFX) ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        } else if (renderLevelStageEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            ZPBulletTracerManager.INSTANCE.renderAll(renderLevelStageEvent.getPoseStack(), renderLevelStageEvent.getPartialTick(), IZPClientManager.DELTA_TIME());
        }
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
