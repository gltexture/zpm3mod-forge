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

package ru.gltexture.zpm3.modules.ui.mixins.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPFakeClientEffectInstance;
import ru.gltexture.zpm3.modules.mob_effects.client.ZPLocalPlayerFakeEffectsManager;

import java.util.*;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class ZPEffectRenderingInventoryScreenMixin {
    @Shadow
    protected abstract Component getEffectName(MobEffectInstance pEffect);


    @Inject(method = "renderEffects", at = @At("HEAD"), cancellable = true)
    private void renderEffects(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, CallbackInfo ci) {
        final EffectRenderingInventoryScreen<?> effectRenderingInventoryScreen = (EffectRenderingInventoryScreen<?>) (Object) this;
        int i = effectRenderingInventoryScreen.getGuiLeft() + effectRenderingInventoryScreen.getXSize() + 2;
        int j = effectRenderingInventoryScreen.width - i;
        final Collection<MobEffectInstance> collection = Minecraft.getInstance().player.getActiveEffects();
        final List<MobEffectInstance> result = new ArrayList<>(collection.size() + ZPLocalPlayerFakeEffectsManager.INSTANCE.getEffects().size());
        result.addAll(ZPLocalPlayerFakeEffectsManager.INSTANCE.getEffects().values());
        result.addAll(collection);
        if (!result.isEmpty() && j >= 32) {
            boolean flag = j >= 120;
            var event = net.minecraftforge.client.ForgeHooksClient.onScreenPotionSize(effectRenderingInventoryScreen, j, !flag, i);
            if (event.isCanceled()) return;
            flag = !event.isCompact();
            i = event.getHorizontalOffset();
            int k = 33;
            if (result.size() > 5) {
                k = 132 / (result.size() - 1);
            }

            Iterable<MobEffectInstance> iterable = result.stream().filter(net.minecraftforge.client.ForgeHooksClient::shouldRenderEffect).sorted().sorted(Comparator.comparingInt(e -> e instanceof ZPFakeClientEffectInstance ? 0 : 1)).collect(java.util.stream.Collectors.toList());
            this.zpm3forge$renderAll(pGuiGraphics, i, k, iterable, flag, pMouseX, pMouseY);

            if (pMouseX >= i && pMouseX <= i + 33) {
                int l = effectRenderingInventoryScreen.getGuiTop();
                MobEffectInstance mobeffectinstance = null;
                for(MobEffectInstance mobeffectinstance1 : iterable) {
                    if (pMouseY >= l && pMouseY <= l + k) {
                        mobeffectinstance = mobeffectinstance1;
                    }
                    l += k;
                }
                if (mobeffectinstance != null) {
                    List<Component> list = List.of(this.getEffectName(mobeffectinstance), (mobeffectinstance instanceof  ZPFakeClientEffectInstance) ? Component.literal("*") : MobEffectUtil.formatDuration(mobeffectinstance, 1.0F));
                    pGuiGraphics.renderTooltip(effectRenderingInventoryScreen.getMinecraft().font, list, Optional.empty(), pMouseX, pMouseY);
                }
            }
        }
        ci.cancel();
    }

    @Unique private void zpm3forge$renderAll(GuiGraphics pGuiGraphics, int pRenderX, int pYOffset, Iterable<MobEffectInstance> pEffects, boolean pIsSmall, int pMouseX, int pMouseY) {
        final EffectRenderingInventoryScreen<?> effectRenderingInventoryScreen = (EffectRenderingInventoryScreen<?>) (Object) this;
        final MobEffectTextureManager mobeffecttexturemanager = Minecraft.getInstance().getMobEffectTextures();
        int i = effectRenderingInventoryScreen.getGuiTop();

        int layer = 0;
        for (MobEffectInstance mobeffectinstance : pEffects) {
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(0, 0, layer++);
            {
                final IClientMobEffectExtensions renderer = net.minecraftforge.client.extensions.common.IClientMobEffectExtensions.of(mobeffectinstance);
                {
                    if (pIsSmall) {
                        pGuiGraphics.blit(AbstractContainerScreen.INVENTORY_LOCATION, pRenderX, i, 0, 166, 120, 32);
                    } else {
                        pGuiGraphics.blit(AbstractContainerScreen.INVENTORY_LOCATION, pRenderX, i, 0, 198, 32, 32);
                    }
                }
                if (pIsSmall) {
                    if (!(mobeffectinstance instanceof ZPFakeClientEffectInstance clientEffect)) {
                        final Component component = this.getEffectName(mobeffectinstance);
                        pGuiGraphics.drawString(effectRenderingInventoryScreen.getMinecraft().font, component, pRenderX + 10 + 18, i + 6, 16777215);
                        final Component component1 = MobEffectUtil.formatDuration(mobeffectinstance, 1.0F);
                        pGuiGraphics.drawString(effectRenderingInventoryScreen.getMinecraft().font, component1, pRenderX + 10 + 18, i + 6 + 10, 0xc8c8ff);
                    } else {
                        final Component component = this.getEffectName(mobeffectinstance);
                        pGuiGraphics.drawString(effectRenderingInventoryScreen.getMinecraft().font, component, pRenderX + 10 + 18, i + 6, clientEffect.getEffect().getColor());
                    }
                }
                {
                    if (renderer.renderInventoryIcon(mobeffectinstance, effectRenderingInventoryScreen, pGuiGraphics, pRenderX + (pIsSmall ? 6 : 7), i + 7, 0)) {
                        i += pYOffset;
                        pGuiGraphics.pose().popPose();
                        continue;
                    }
                    MobEffect mobeffect = mobeffectinstance.getEffect();
                    TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.get(mobeffect);
                    pGuiGraphics.blit(pRenderX + (pIsSmall ? 6 : 7), i + 7, 0, 18, 18, textureatlassprite);
                }
                i += pYOffset;
                pGuiGraphics.pose().popPose();
            }
        }
    }

    @Inject(method = "renderIcons", at = @At("HEAD"), cancellable = true)
    private void renderIcons(GuiGraphics pGuiGraphics, int pRenderX, int pYOffset, Iterable<MobEffectInstance> pEffects, boolean pIsSmall, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderLabels", at = @At("HEAD"), cancellable = true)
    private void renderLabels(GuiGraphics pGuiGraphics, int pRenderX, int pYOffset, Iterable<MobEffectInstance> pEffects, CallbackInfo ci) {
        final EffectRenderingInventoryScreen<?> effectRenderingInventoryScreen = (EffectRenderingInventoryScreen<?>)(Object)this;
        int i = effectRenderingInventoryScreen.getGuiTop();

        for (MobEffectInstance mobeffectinstance : pEffects) {
            var renderer = net.minecraftforge.client.extensions.common.IClientMobEffectExtensions.of(mobeffectinstance);
            if (renderer.renderInventoryText(mobeffectinstance, effectRenderingInventoryScreen, pGuiGraphics, pRenderX, i, 0)) {
                i += pYOffset;
                continue;
            }

            if (!(mobeffectinstance instanceof ZPFakeClientEffectInstance clientEffect)) {
                Component component = this.getEffectName(mobeffectinstance);
                pGuiGraphics.drawString(effectRenderingInventoryScreen.getMinecraft().font, component, pRenderX + 10 + 18, i + 6, 16777215);
                Component component1 = MobEffectUtil.formatDuration(mobeffectinstance, 1.0F);
                pGuiGraphics.drawString(effectRenderingInventoryScreen.getMinecraft().font, component1, pRenderX + 10 + 18, i + 6 + 10, 0xc8c8ff);

                /*
               final Component component = this.getEffectName(mobeffectinstance);
                final Component component1 = MobEffectUtil.formatDuration(mobeffectinstance, 1.0F);
                MutableComponent mutablecomponent = component.copy();
                mutablecomponent.append(" (").append(component1).append(")");
                pGuiGraphics.drawString(effectRenderingInventoryScreen.getMinecraft().font, mutablecomponent, pRenderX + 10 + 18, i + 6, 16777215);
                 */
            } else {
                Component component = this.getEffectName(mobeffectinstance);
                pGuiGraphics.drawString(effectRenderingInventoryScreen.getMinecraft().font, component, pRenderX + 10 + 18, i + 10, clientEffect.getEffect().getColor());
            }
            i += pYOffset;
        }
        ci.cancel();
    }
}