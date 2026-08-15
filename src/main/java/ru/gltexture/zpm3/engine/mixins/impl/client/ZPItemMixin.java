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

package ru.gltexture.zpm3.engine.mixins.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.client.rendering.IZPClientManager;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.core.ZP_EventsManager;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventDef;
import ru.gltexture.zpm3.engine.core.api.events.client.ZPEventBus_ClientRendering;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemInHandRenderer.class)
public class ZPItemMixin {
    @Shadow @Final private ItemRenderer itemRenderer;

    //@Deprecated(forRemoval = true)
    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void renderArmWithItem(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        Item itemToRender = pStack.getItem();
        ZPRenderHooks.ZPItemRendering1PersonHook itemRenderingHook = ZPRenderHooksManager.INSTANCE.getItemRendering1PersonHooks().get(itemToRender);

        {
            if (itemRenderingHook != null) {
                itemRenderingHook.onRenderItem1Person(pPlayer, IZPClientManager.DELTA_TIME(), pPartialTicks, pPitch, pHand, pSwingProgress, pStack, pEquippedProgress, pPoseStack, pBuffer, pCombinedLight);
                ci.cancel();
            }
        }

        ZPEventBus_ClientRendering.ItemRenderFirstPersonEvent event = new ZPEventBus_ClientRendering.ItemRenderFirstPersonEvent(pPlayer, IZPClientManager.DELTA_TIME(), pPartialTicks, pPitch, pHand, pSwingProgress, pStack, pEquippedProgress, pPoseStack, pBuffer, pCombinedLight);
        ZP_EventsManager.pushEvent(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    //@Deprecated(forRemoval = true)
    @Inject(method = "renderHandsWithItems", at = @At("HEAD"))
    private void renderHandsWithItems1(float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight, CallbackInfo ci) {
        ZPRenderHooksManager.INSTANCE.getItemSceneRendering1PersonHooksPre().forEach(e -> e.onPreRender1Person(IZPClientManager.DELTA_TIME(), pPartialTicks, pPoseStack, pBuffer, pPlayerEntity, pCombinedLight));
        ZP_EventsManager.pushEvent(new ZPEventBus_ClientRendering.ItemSceneRenderFirstPersonEvent(ZPEventDef.Run.PRE, IZPClientManager.DELTA_TIME(), pPartialTicks, pPoseStack, pBuffer, pPlayerEntity, pCombinedLight));
    }

    //@Deprecated(forRemoval = true)
    @Inject(method = "renderHandsWithItems", at = @At("TAIL"))
    private void renderHandsWithItems2(float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight, CallbackInfo ci) {
        ZPRenderHooksManager.INSTANCE.getItemSceneRendering1PersonHooksPost().forEach(e -> e.onPostRender1Person(IZPClientManager.DELTA_TIME(), pPartialTicks, pPoseStack, pBuffer, pPlayerEntity, pCombinedLight));
        ZP_EventsManager.pushEvent(new ZPEventBus_ClientRendering.ItemSceneRenderFirstPersonEvent(ZPEventDef.Run.POST, IZPClientManager.DELTA_TIME(), pPartialTicks, pPoseStack, pBuffer, pPlayerEntity, pCombinedLight));
    }
}