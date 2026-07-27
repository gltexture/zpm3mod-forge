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

package ru.gltexture.zpm3.modules.ui.screen.configs;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPClientConfig;
import ru.gltexture.zpm3.modules.ui.screen.ZPScreen;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class ZPSettingsMenuScreen extends ZPScreen {
    public ZPSettingsMenuScreen(Screen parent) {
        super(Component.translatable("ui.zpm3.settings"), parent);
    }

    @Override
    protected void init() {
        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("ui.zpm3.mcSettings"), button -> Objects.requireNonNull(this.minecraft).setScreen(new OptionsScreen(this, this.minecraft.options))
                ).bounds(this.width / 2 - 50, this.height / 2 - 30, 100, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("ui.zpm3.modSettings")
                                .withStyle(style -> style.withColor(0xf8a3ff37)), button -> Objects.requireNonNull(this.minecraft).setScreen(new ZPClientConfigOptionsScreen(ZPClientConfig.class, this))
                ).bounds(this.width / 2 - 50, this.height / 2, 100, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("ui.zpm3.exit"), button -> this.onClose()
                ).bounds(this.width / 2 - 50, this.height / 2 + 30, 100, 20).build()
        );
    }

    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        pGuiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}
