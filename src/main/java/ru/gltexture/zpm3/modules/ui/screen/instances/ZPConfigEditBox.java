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
