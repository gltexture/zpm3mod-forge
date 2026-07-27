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

package ru.gltexture.zpm3.modules.ui.screen.instances;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ZPLabeledEditBox extends AbstractWidget {
    private final StringWidget label;
    private final ZPConfigEditBox editBox;

    private @Nullable Consumer<EditBox> onUpdate;

    public ZPLabeledEditBox(Font font, int x, int y, int width, int height, Component name, ZPConfigEditBox editBox) {
        super(x, y, width, height, name);

        this.label = new StringWidget(name, font);
        this.editBox = editBox;
    }

    public ZPLabeledEditBox(Font font, Component name, ZPConfigEditBox editBox) {
        super(0, 0, font.width(name), 30, name);

        this.label = new StringWidget(name, font);
        this.editBox = editBox;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.label.setX(this.getX());
        this.label.setY(this.getY());

        this.editBox.setX((int) (this.getX() + this.width * 0.5f - this.editBox.getWidth() * 0.5f));
        this.editBox.setY(this.getY() + 10);

        if (this.getOnUpdate() != null) {
            this.getOnUpdate().accept(this.editBox);
        }

        this.label.render(graphics, mouseX, mouseY, partialTick);
        this.editBox.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {
        pNarrationElementOutput.add(NarratedElementType.TITLE, Component.literal("Config field"));
        pNarrationElementOutput.add(NarratedElementType.USAGE, Component.literal("Editable value"));
    }

    public StringWidget getLabel() {
        return this.label;
    }

    public EditBox getEditBox() {
        return this.editBox;
    }

    public @Nullable Consumer<EditBox> getOnUpdate() {
        return this.onUpdate;
    }

    public ZPLabeledEditBox setOnUpdate(@Nullable Consumer<EditBox> onUpdate) {
        this.onUpdate = onUpdate;
        return this;
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        this.editBox.setX(x + 80);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        this.editBox.setY(y);
    }
}