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

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ZPFloatSlider extends AbstractSliderButton {
    protected final float min;
    protected final float max;
    protected final Consumer<ZPFloatSlider> onUpdate;

    public ZPFloatSlider(float min, float max, @Nullable Consumer<ZPFloatSlider> callback, int pX, int pY, int pWidth, int pHeight, Component pMessage, double pValue) {
        super(pX, pY, pWidth, pHeight, pMessage, pValue);
        this.min = min;
        this.max = max;
        this.onUpdate = callback;
    }

    public float getValue() {
        return (float) (this.min + (this.value) * (this.max - this.min));
    }

    @Override
    public void setMessage(@NotNull Component pMessage) {
        super.setMessage(pMessage);
    }

    @Override
    protected void updateMessage() {
    }

    @Override
    protected void applyValue() {
        if (this.onUpdate != null) {
            this.onUpdate.accept(this);
        }
    }
}
