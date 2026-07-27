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
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class ZPConfigEditBox extends EditBox {
    private @Nullable BiConsumer<EditBox, String> onUpdateText;
 //   private @Nullable ZPConfigVar<?> linkedConfigVar;

    public ZPConfigEditBox(Font pFont, int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pFont, pX, pY, pWidth, pHeight, pMessage);
    }

    public ZPConfigEditBox(Font pFont, int pX, int pY, int pWidth, int pHeight, @Nullable EditBox pEditBox, Component pMessage) {
        super(pFont, pX, pY, pWidth, pHeight, pEditBox, pMessage);
    }

    public @Nullable BiConsumer<EditBox, String> getOnUpdateText() {
        return this.onUpdateText;
    }

   //public @Nullable ZPConfigVar<?> getLinkedConfigVar() {
   //    return this.linkedConfigVar;
   //}

   //public ZPConfigEditBox setLinkedConfigVar(@Nullable ZPConfigVar<?> linkedConfigVar) {
   //    this.linkedConfigVar = linkedConfigVar;
   //    return this;
   //}

    public ZPConfigEditBox setOnUpdateText(@Nullable BiConsumer<EditBox, String> onUpdateText) {
        this.onUpdateText = onUpdateText;
        return this;
    }

    @Override
    public void insertText(@NotNull String pTextToWrite) {
        super.insertText(pTextToWrite);
        if (this.onUpdateText != null) {
            this.onUpdateText.accept(this, this.getValue());
        }
    }

   // @Override
   // public @NotNull String getValue() {
   //     if (this.getLinkedConfigVar() != null) {
   //         return this.getLinkedConfigVar().getVar().toString();
   //     }
   //     return super.getValue();
   // }
}
