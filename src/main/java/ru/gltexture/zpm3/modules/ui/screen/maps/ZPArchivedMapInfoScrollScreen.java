package ru.gltexture.zpm3.modules.ui.screen.maps;

import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.modules.ui.screen.maps.meta.ZPMapMetaData;

public class ZPArchivedMapInfoScrollScreen extends ScrollPanel {
    private static final int PIC_Y = 220;
    private static final int PADDING = 10;
    private final Font font;
    private ZPMapMetaData data;

    public ZPArchivedMapInfoScrollScreen(Minecraft minecraft, int width, int height, int top, int left, @Nullable ZPMapMetaData data) {
        super(minecraft, width, height, top, left);
        this.font = minecraft.font;
        this.data = data;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (this.getContentHeight() <= height) {
            return false;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    protected int getContentHeight() {
        if (this.data == null) {
            return 0;
        }
        int height = PADDING;
        if (this.data.mapDataResourcesManager().getImageResourceLocation() != null) {
            height += this.calcScaledImageHeight(this.getMaxImageWidth(), ZPArchivedMapInfoScrollScreen.PIC_Y, this.data.mapDataResourcesManager().imageWidth(), this.data.mapDataResourcesManager().imageHeight()) + PADDING;
        }
        height += font.lineHeight * 4;
        height += PADDING * 4;
        height += 6;
        height += this.font.split(Component.literal(this.data.description()), this.width - PADDING * 2).size() * this.font.lineHeight;
        return height + PADDING;
    }

    public ZPArchivedMapInfoScrollScreen setData(@Nullable ZPMapMetaData data) {
        this.data = data;
        this.scrollDistance = 0;
        return this;
    }

    public ZPMapMetaData getData() {
        return this.data;
    }

    private int getMaxImageWidth() {
        return this.width - PADDING * 2;
    }

    private int calcScaledImageHeight(int boundsWidth, int boundsHeight, int rectWidth, int rectHeight) {
        if (rectWidth * boundsHeight > rectHeight * boundsWidth) {
            boundsHeight = (int) (boundsWidth * ((double) rectHeight / rectWidth));
        }
        return boundsHeight;
    }

    @Override
    protected void drawPanel(GuiGraphics graphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        if (this.data == null) {
            return;
        }
        int X = this.left;
        int y = relativeY + PADDING;
        int imageWidth = this.data.mapDataResourcesManager().imageWidth();
        int imageHeight = this.data.mapDataResourcesManager().imageHeight();
        final int maxWidth = this.getMaxImageWidth();
        if (this.data.mapDataResourcesManager().getImageResourceLocation() != null) {
            graphics.blitInscribed(this.data.mapDataResourcesManager().getImageResourceLocation(), X + PADDING, y, maxWidth, ZPArchivedMapInfoScrollScreen.PIC_Y, imageWidth, imageHeight, false, false);
            y += this.calcScaledImageHeight(this.getMaxImageWidth(), ZPArchivedMapInfoScrollScreen.PIC_Y, this.data.mapDataResourcesManager().imageWidth(), this.data.mapDataResourcesManager().imageHeight()) + PADDING;
        }
        graphics.drawString(this.font, this.data.mapName() + " (" + this.data.version() + ")", X + PADDING, y, 0xFFFFFF);
        y += PADDING;
        graphics.drawString(this.font, "By: " + String.join(", ", this.data.authors()), X + PADDING, y, 0x88FF88);
        y += PADDING + 4;
        graphics.drawString(this.font, "ZP3 Version: " + this.data.modVersion(), X + PADDING, y, 0xCCCCCC);
        y += PADDING;
        graphics.drawString(this.font, "Recommended Players: " + this.data.recommendedPlayers(), X + PADDING, y, 0xCCCCCC);
        y += PADDING + 6;
        for (FormattedCharSequence line : this.font.split(Component.literal(this.data.description()), maxWidth)) {
            graphics.drawString(font, line, X + PADDING, y, 0xE8D0A6);
            y += this.font.lineHeight;
        }
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput narration) {
    }
}
