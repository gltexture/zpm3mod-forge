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
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.config.ZPConfigManager;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPClientConfig;
import ru.gltexture.zpm3.engine.core.config.vars.*;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.modules.ui.screen.ZPScreen;
import ru.gltexture.zpm3.modules.ui.screen.instances.ZPConfigEditBox;
import ru.gltexture.zpm3.modules.ui.screen.instances.ZPLabeledEditBox;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)

public class ZPClientConfigOptionsScreen extends ZPScreen {
    private final ConfigVarUIWithEditCallback[] configVarWrappedObjects;

    public ZPClientConfigOptionsScreen(Screen parent, ConfigVarUIWithEditCallback... c) {
        super(Component.translatable("ui.zpm3.configSettings"), parent);
        this.configVarWrappedObjects = c;
    }

    @Override
    protected void init() {
        GridLayout gridlayout = new GridLayout();
        gridlayout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
        GridLayout.RowHelper rowHelper = gridlayout.createRowHelper(2);

        Objects.requireNonNull(Arrays.stream(this.configVarWrappedObjects)).forEach(zpConfigVar -> {
            this.drawUiElementFor(rowHelper, zpConfigVar);
        });

        gridlayout.arrangeElements();
        FrameLayout.alignInRectangle(gridlayout, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
        gridlayout.visitWidgets(this::addRenderableWidget);

        this.addRenderableWidget(
                Button.builder(Component.translatable("ui.zpm3.exit"), button -> this.onClose()).bounds(this.width / 2 - 50, gridlayout.getY() + gridlayout.getHeight() + 20, 100, 20).build()
        );
    }

    private static Component buttonMsg(ZPConfigManager.ConfigVarWrappedObject configVar) {
        return Component.translatable("ui.zpm3.config." + configVar.varName(), ((ZPConfig_BOOL) configVar.get()).getVar());
    }

    protected void drawUiElementFor(GridLayout.RowHelper rowHelper, ConfigVarUIWithEditCallback varObj) {
        final BiConsumer<EditBox, String> onUpdateText = ((editBox, string) -> editBox.setValue(varObj.toString()));
        if (varObj.configVar().get() instanceof ZPConfig_BOOL configBool) {
            rowHelper.addChild(Button.builder(buttonMsg(varObj.configVar()), b -> {
                configBool.setVar(!configBool.getVar());
                b.setMessage(ZPClientConfigOptionsScreen.buttonMsg(varObj.configVar()));
                if (varObj.callback() != null) {
                    varObj.callback().accept(configBool);
                }
            }).tooltip(Tooltip.create(Component.literal(varObj.configVar().varDescription()))).build());
        } else if (varObj.configVar().get() instanceof ZPConfig_INT) {
            ZPConfigEditBox box = this.getEditBoxFor(varObj,
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
            this.createEditBoxWidget(rowHelper, box, Component.translatable("ui.zpm3.config." + varObj.configVar().varName()));
        } else if (varObj.configVar().get() instanceof ZPConfig_DOUBLE) {
            ZPConfigEditBox box = this.getEditBoxFor(varObj,
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
            this.createEditBoxWidget(rowHelper, box, Component.translatable("ui.zpm3.config." + varObj.configVar().varName()));
        } else if (varObj.configVar().get() instanceof ZPConfig_FLOAT) {
            ZPConfigEditBox box = this.getEditBoxFor(varObj,
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
            this.createEditBoxWidget(rowHelper, box, Component.translatable("ui.zpm3.config." + varObj.configVar().varName()));
        } else if (varObj.configVar().get() instanceof ZPConfig_STRING configString) {
            ZPConfigEditBox box = this.getEditBoxFor(varObj,
                    s -> true,
                    (s) -> s).setOnUpdateText(onUpdateText);
            this.createEditBoxWidget(rowHelper, box, Component.translatable("ui.zpm3.config." + varObj.configVar().varName()));
        }
    }

    protected ZPLabeledEditBox createEditBoxWidget(GridLayout.RowHelper rowHelper, ZPConfigEditBox editBox, Component name) {
        ZPLabeledEditBox ZPLabeledEditBox = new ZPLabeledEditBox(this.font, name, editBox);
        rowHelper.addChild(ZPLabeledEditBox);
        this.addWidget(ZPLabeledEditBox.getEditBox());
        this.addWidget(ZPLabeledEditBox.getLabel());
        return ZPLabeledEditBox;
    }

    protected @NotNull <T extends Serializable> ZPConfigEditBox getEditBoxFor(ConfigVarUIWithEditCallback configVarUI, Predicate<String> filter, Function<String, T> converter) {
        ZPConfigEditBox box = new ZPConfigEditBox(this.font, 0, 0, 100, 20, Component.literal(configVarUI.configVar().varName()));
        box.setValue(String.valueOf(configVarUI.configVar().get()));
        box.setFilter(filter);
        box.setResponder(s -> {
            try {
                if (!s.isEmpty()) {
                    configVarUI.configVar().getVarUnsafe().setVar(converter.apply(s));
                    if (configVarUI.callback() != null) {
                        configVarUI.callback().accept(configVarUI.configVar().get());
                    }
                }
            } catch (NumberFormatException ignored) {}
        });
        box.setTooltip(Tooltip.create(Component.literal(configVarUI.configVar().varDescription())));
        return box;
    }

    private void save() {
        ZombiePlague3.getZpConfigManager().rewriteConfigClass(ZPClientConfig.class);
        if (this.minecraft != null && this.minecraft.player != null) {
            ZombiePlague3.netClient().getNetStaticDataSyncer().broadcastAll();
        }
    }

    public void removed() {
        this.save();
    }

    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        pGuiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public record ConfigVarUIWithEditCallback(@NotNull ZPConfigManager.ConfigVarWrappedObject configVar, @Nullable Consumer<ZPConfigVar<?>> callback) {}
}