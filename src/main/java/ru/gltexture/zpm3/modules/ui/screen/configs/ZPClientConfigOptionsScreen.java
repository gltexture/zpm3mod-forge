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
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;
import ru.gltexture.zpm3.engine.core.config.ZPConfigManager;
import ru.gltexture.zpm3.engine.core.config.vars.*;
import ru.gltexture.zpm3.modules.ui.screen.ZPScreen;
import ru.gltexture.zpm3.modules.ui.screen.instances.ZPConfigEditBox;
import ru.gltexture.zpm3.modules.ui.screen.instances.ZPLabeledEditBox;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)

public class ZPClientConfigOptionsScreen extends ZPScreen {
    private final Class<? extends ZPConfigConstantsClass> configClass;

    public ZPClientConfigOptionsScreen(@NotNull Class<? extends ZPConfigConstantsClass> configClass, Screen parent) {
        super(Component.translatable("ui.zpm3.configSettings"), parent);
        this.configClass = configClass;
    }

    @Override
    protected void init() {
        GridLayout gridlayout = new GridLayout();
        gridlayout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
        GridLayout.RowHelper rowHelper = gridlayout.createRowHelper(2);

        Objects.requireNonNull(ZombiePlague3.getZpConfigManager().configVarObjectsForUI(this.configClass)).forEach(zpConfigVar -> {
            this.drawUiElementFor(rowHelper, zpConfigVar);
        });

        gridlayout.arrangeElements();
        FrameLayout.alignInRectangle(gridlayout, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
        gridlayout.visitWidgets(this::addRenderableWidget);

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("ui.zpm3.exit"), button -> this.onClose()
                ).bounds(this.width / 2 - 50, gridlayout.getY() + gridlayout.getHeight() + 20, 100, 20).build()
        );
    }

    private static Component buttonMsg(ZPConfigManager.ConfigVarObjectForUI configVar) {
        return Component.translatable("ui.zpm3.config." + configVar.varName(), ((ZPConfig_BOOL) configVar.var()).getVar());
    }

    protected void drawUiElementFor(GridLayout.RowHelper rowHelper, ZPConfigManager.ConfigVarObjectForUI configVar) {
        final BiConsumer<EditBox, String> onUpdateText = ((editBox, string) -> {
            editBox.setValue(configVar.toString());
        });
        if (configVar.var() instanceof ZPConfig_BOOL configBool) {
            rowHelper.addChild(Button.builder(buttonMsg(configVar), b -> {
                configBool.setVar(!configBool.getVar());
                b.setMessage(buttonMsg(configVar));
            }).tooltip(Tooltip.create(Component.literal(configVar.varDescription()))).build());
        } else if (configVar.var() instanceof ZPConfig_INT) {
            ZPConfigEditBox box = this.getEditBoxFor(configVar,
                    s -> {
                        if (s.isEmpty()) {
                            return true;
                        }
                        try {
                            Integer.parseInt(s);
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    },
                    Integer::parseInt).setOnUpdateText(onUpdateText);
            this.createEditBoxWidget(rowHelper, box, Component.translatable("ui.zpm3.config." + configVar.varName()));
        } else if (configVar.var() instanceof ZPConfig_DOUBLE) {
            ZPConfigEditBox box = this.getEditBoxFor(configVar,
                    s -> {
                        if (s.isEmpty()) {
                            return true;
                        }
                        try {
                            Double.parseDouble(s);
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    },
                    Double::parseDouble).setOnUpdateText(onUpdateText);
            this.createEditBoxWidget(rowHelper, box, Component.translatable("ui.zpm3.config." + configVar.varName()));
        } else if (configVar.var() instanceof ZPConfig_FLOAT) {
            ZPConfigEditBox box = this.getEditBoxFor(configVar,
                    s -> {
                        if (s.isEmpty()) {
                            return true;
                        }
                        try {
                            Float.parseFloat(s);
                            return true;
                        } catch (NumberFormatException ignored) {
                            return false;
                        }
                    },
                    Float::parseFloat).setOnUpdateText(onUpdateText);
            this.createEditBoxWidget(rowHelper, box, Component.translatable("ui.zpm3.config." + configVar.varName()));
        } else if (configVar.var() instanceof ZPConfig_STRING configString) {
            ZPConfigEditBox box = this.getEditBoxFor(configVar,
                    s -> true,
                    (s) -> s).setOnUpdateText(onUpdateText);
            this.createEditBoxWidget(rowHelper, box, Component.translatable("ui.zpm3.config." + configVar.varName()));
        }
    }

    protected ZPLabeledEditBox createEditBoxWidget(GridLayout.RowHelper rowHelper, ZPConfigEditBox editBox, Component name) {
        ZPLabeledEditBox ZPLabeledEditBox = new ZPLabeledEditBox(this.font, name, editBox);
        rowHelper.addChild(ZPLabeledEditBox);
        this.addWidget(ZPLabeledEditBox.getEditBox());
        this.addWidget(ZPLabeledEditBox.getLabel());
        return ZPLabeledEditBox;
    }

    protected @NotNull <T extends Serializable> ZPConfigEditBox getEditBoxFor(ZPConfigManager.ConfigVarObjectForUI configVarUI, Predicate<String> filter, Function<String, T> converter) {
        ZPConfigEditBox box = new ZPConfigEditBox(this.font, 0, 0, 100, 20, Component.literal(configVarUI.varName()));
        box.setValue(String.valueOf(configVarUI.var()));
        box.setFilter(filter);
        box.setResponder(s -> {
            try {
                if (!s.isEmpty()) {
                    configVarUI.getVarUnsafe().setVar(converter.apply(s));
                }
            } catch (NumberFormatException ignored) {}
        });
        box.setTooltip(Tooltip.create(Component.literal(configVarUI.varDescription())));
        return box;
    }

    private void save() {
        ZombiePlague3.getZpConfigManager().rewriteConfigClass(this.configClass);
    }

    public void removed() {
        this.save();
    }

    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        pGuiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}