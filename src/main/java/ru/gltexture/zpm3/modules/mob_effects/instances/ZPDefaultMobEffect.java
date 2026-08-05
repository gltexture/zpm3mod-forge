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

package ru.gltexture.zpm3.modules.mob_effects.instances;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public abstract class ZPDefaultMobEffect extends MobEffect {
    @OnlyIn(Dist.CLIENT) private IClientMobEffectExtensions clientMobEffectExtensions;

    public ZPDefaultMobEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @OnlyIn(Dist.CLIENT)
    public ZPDefaultMobEffect setClientMobExtension(@NotNull IClientMobEffectExtensions extension) {
        this.clientMobEffectExtensions = extension;
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public void initializeClient(java.util.function.@NotNull Consumer<net.minecraftforge.client.extensions.common.IClientMobEffectExtensions> consumer) {
        if (this.clientMobEffectExtensions != null) {
            consumer.accept(this.clientMobEffectExtensions);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class DefaultZPEffectClientExtension implements IClientMobEffectExtensions {
        private final ResourceLocation ICON;
        private final boolean renderInUi;

        public DefaultZPEffectClientExtension(boolean renderInUi, ResourceLocation iconId) {
            this.renderInUi = renderInUi;
            this.ICON = iconId;
        }

        @Override
        public boolean renderInventoryIcon(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
            if (this.renderInUi) {
                guiGraphics.blit(this.ICON, x + 2, y + 6, blitOffset, 0, 0, 18, 18, 18, 18);
                return true;
            }
            return false;
        }

        @Override
        public boolean renderGuiIcon(MobEffectInstance instance, Gui gui, GuiGraphics guiGraphics, int x, int y, float z, float alpha) {
            if (this.renderInUi) {
                RenderSystem.setShaderColor(1F, 1F, 1F, alpha);
                guiGraphics.blit(this.ICON, x + 3, y + 3, 0, 0, 18, 18, 18, 18);
                RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
                return true;
            }
            return false;
        }
    }
}