package ru.gltexture.zpm3.modules.ui.screen.instances;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class LabeledEditBox extends AbstractWidget {
    private final StringWidget label;
    private final ZpConfigEditBox editBox;

    private @Nullable Consumer<EditBox> onUpdate;

    public LabeledEditBox(Font font, int x, int y, int width, int height, Component name, ZpConfigEditBox editBox) {
        super(x, y, width, height, name);

        this.label = new StringWidget(name, font);
        this.editBox = editBox;
    }

    public LabeledEditBox(Font font, Component name, ZpConfigEditBox editBox) {
        super(0, 0, font.width(name), 30, name);

        this.label = new StringWidget(name, font);
        this.editBox = editBox;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.label.setX(this.getX());
        this.label.setY(this.getY() + 6);

        this.editBox.setX((int) (this.getX() + this.width * 0.5f - this.editBox.getWidth() * 0.5f));
        this.editBox.setY(this.getY() + 16);

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

    public LabeledEditBox setOnUpdate(@Nullable Consumer<EditBox> onUpdate) {
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