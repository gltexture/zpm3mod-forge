package ru.gltexture.zpm3.modules.ui.screen.maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.modules.ui.screen.maps.meta.ZPMapMetaData;

import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class ZPArchivedMapList extends ObjectSelectionList<ZPArchivedMapList.MapEntry> {
    private MapEntry previous;
    private final Consumer<MapEntry> callback;

    public ZPArchivedMapList(Minecraft mc, int width, int height, int top, int bottom, int itemHeight, @NotNull Consumer<MapEntry> callback) {
        super(mc, width, height, top, bottom, itemHeight);
        this.callback = callback;
    }

    @Override
    public int addEntry(@NotNull MapEntry pEntry) {
        return super.addEntry(pEntry);
    }

    @Override
    public void addEntryToTop(@NotNull MapEntry pEntry) {
        super.addEntryToTop(pEntry);
    }

    @Override
    public int getRowWidth() {
        return width - 40;
    }

    @Override
    protected int getScrollbarPosition() {
        return width - 8;
    }

    @Override
    public void setSelected(@Nullable MapEntry entry) {
        if (entry == this.previous) {
            return;
        }
        if (this.previous != null) {
            this.previous.getData().mapDataResourcesManager().lazyClear();
        }
        super.setSelected(entry);
        if (entry != null) {
            entry.getData().mapDataResourcesManager().lazyCreate();
        }
        this.callback.accept(entry);
        this.previous = entry;
    }

    public static String trimText(Font font, String text, int maxWidth) {
        return font.plainSubstrByWidth(text, maxWidth);
    }

    public static class MapEntry extends ObjectSelectionList.Entry<MapEntry> {
        private final ZPMapMetaData data;
        private final ZPArchivedMapList owner;

        public MapEntry(ZPArchivedMapList owner, ZPMapMetaData data) {
            this.owner = owner;
            this.data = data;
        }

        public ZPMapMetaData getData() {
            return data;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            this.owner.setSelected(this);
            return true;
        }

        @Override
        public void render(@NotNull GuiGraphics graphics, int index, int top, int left, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean hovered, float partialTick) {
            final Font font = Minecraft.getInstance().font;
            int maxTextWidth = 180;

            graphics.drawString(font, trimText(font, data.mapName() + "(" + data.version() + ")", maxTextWidth), left + 8, top + 8, 0xffffff);
            graphics.drawString(font, trimText(font, "ZP3 Ver. " + data.modVersion(), maxTextWidth), left + 8, top + 22, 0xAAAAAA);
            graphics.drawString(font, trimText(font, String.join(", ", data.authors()), maxTextWidth), left + 8, top + 36, 0x88FF88);
        }

        @Override
        public @NotNull Component getNarration() {
            return Component.literal(data.mapName());
        }
    }
}