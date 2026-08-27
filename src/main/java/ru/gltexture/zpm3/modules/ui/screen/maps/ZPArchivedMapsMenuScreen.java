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

package ru.gltexture.zpm3.modules.ui.screen.maps;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.ui.screen.ZPScreen;
import ru.gltexture.zpm3.modules.worldgen.archiver.ZPMapArchivedRegistry;
import ru.gltexture.zpm3.modules.worldgen.archiver.ZPMapInstaller;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@OnlyIn(Dist.CLIENT)
public class ZPArchivedMapsMenuScreen extends ZPScreen {
    private final Screen parent;
    private ZPArchivedMapList mapList;
    private ZPArchivedMapInfoScrollScreen archivedMapInfoScrollScreen;
    private Button launchButton;

    public ZPArchivedMapsMenuScreen(Screen parent) {
        super(Component.literal("Archived Maps"), parent);
        this.parent = parent;
    }

    @Override
    protected void init() {
        final int listWidth = 200;
        {
            final int panelWidth = (int) (this.width * 0.7f);
            final int panelLeft = this.width - panelWidth + 10;
            this.archivedMapInfoScrollScreen = new ZPArchivedMapInfoScrollScreen(this.minecraft, this.width - listWidth - (20), this.height - 35, 0, listWidth + 10, null);
        }

        this.mapList = new ZPArchivedMapList(this.minecraft, listWidth, this.height - 50, 0, this.height - 35, 56, (mapEntry -> {
            this.archivedMapInfoScrollScreen.setData(mapEntry == null ? null : mapEntry.getData());
        }));
        this.addRenderableWidget(this.mapList);
        this.addRenderableWidget(this.archivedMapInfoScrollScreen);

        ZPMapArchivedRegistry.streamMaps().forEach(map -> {
            this.mapList.addEntry(new ZPArchivedMapList.MapEntry(mapList, map));
        });

        this.launchButton = Button.builder(Component.translatable("ui.zpm3.launch"), b -> {
            if (this.mapList.getSelected() != null) {
                CompletableFuture.runAsync(() -> {
                    final String mapName = ZPMapInstaller.installMap(this.mapList.getSelected().getData());
                    Minecraft.getInstance().execute(() -> {
                        Minecraft.getInstance().createWorldOpenFlows().loadLevel(this, mapName);
                    });
                }).exceptionally(e -> {
                    e.printStackTrace(System.err);
                    return null;
                });;
            }
        }).bounds(10, this.height - 28, 90, 20).build();
        this.addRenderableWidget(this.launchButton);

        this.addRenderableWidget(Button.builder(Component.translatable("ui.zpm3.folder"), b -> {
            if (ZPMapArchivedRegistry.getMapsFolder().toFile().exists()) {
                Util.getPlatform().openFile(ZPMapArchivedRegistry.getMapsFolder().toFile());
            } else {
                ZPMapArchivedRegistry.createMapsFolder();
            }
        }).bounds(110, this.height - 28, 120, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("ui.zpm3.exit"), b -> onClose()).bounds(this.width - 100, this.height - 28, 90, 20).build());
    }

    public ZPArchivedMapInfoScrollScreen getArchivedMapInfoScrollScreen() {
        return this.archivedMapInfoScrollScreen;
    }

    @Override
    public void onClose() {
        Objects.requireNonNull(this.minecraft).setScreen(parent);
        ZPMapArchivedRegistry.streamMaps().forEach(map -> {
            map.mapDataResourcesManager().lazyClear();
        });
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.launchButton.active = this.mapList.getSelected() != null;

        this.renderDirtBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);

       // graphics.drawCenteredString(font, title, width / 2, 8, 0xffffff);
    }
}
