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

package ru.gltexture.zpm3.engine.mixins.impl.client.render;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.client.rendering.IZPClientManager;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.core.ZP_EventsManager;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventDef;
import ru.gltexture.zpm3.engine.core.api.events.client.ZPEventBus_ClientRendering;

@Mixin(GameRenderer.class)
@OnlyIn(Dist.CLIENT)
public class ZPRenderMixin {
    //@Deprecated(forRemoval = true)
    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderTail2(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        ZPRenderHooksManager.INSTANCE.getSceneRenderingHooks().forEach((e) -> e.onRender(ZPRenderHooks.RenderStage.PRE, pPartialTicks, IZPClientManager.DELTA_TIME(), pNanoTime, pRenderLevel));
        ZP_EventsManager.pushEvent(new ZPEventBus_ClientRendering.SceneRenderEvent(ZPEventDef.Run.PRE, pPartialTicks, IZPClientManager.DELTA_TIME(), pNanoTime, pRenderLevel));
    }

    //@Deprecated(forRemoval = true)
    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderTail1(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        ZPRenderHooksManager.INSTANCE.getSceneRenderingHooks().forEach((e) -> e.onRender(ZPRenderHooks.RenderStage.POST, pPartialTicks, IZPClientManager.DELTA_TIME(), pNanoTime, pRenderLevel));
        ZP_EventsManager.pushEvent(new ZPEventBus_ClientRendering.SceneRenderEvent(ZPEventDef.Run.POST, pPartialTicks, IZPClientManager.DELTA_TIME(), pNanoTime, pRenderLevel));
    }
}
