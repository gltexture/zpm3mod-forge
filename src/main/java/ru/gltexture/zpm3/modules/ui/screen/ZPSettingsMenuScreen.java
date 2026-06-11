package ru.gltexture.zpm3.modules.ui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPClientConfig;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)

public class ZPSettingsMenuScreen extends ZPScreen {
    public ZPSettingsMenuScreen(Screen parent) {
        super(Component.translatable("ui.zpm3.settings"), parent);
    }

    @Override
    protected void init() {
        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("ui.zpm3.mcSettings"), button -> Objects.requireNonNull(this.minecraft).setScreen(new OptionsScreen(this, this.minecraft.options))
                ).bounds(this.width / 2 - 50, this.height / 2 - 30, 100, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("ui.zpm3.modSettings")
                                .withStyle(style -> style.withColor(0xf8a3ff37)), button -> Objects.requireNonNull(this.minecraft).setScreen(new ZPClientConfigOptionsScreen(ZPClientConfig.class, this))
                ).bounds(this.width / 2 - 50, this.height / 2, 100, 20).build()
        );

        this.addRenderableWidget(
                Button.builder(
                        Component.translatable("ui.zpm3.exit"), button -> this.onClose()
                ).bounds(this.width / 2 - 50, this.height / 2 + 30, 100, 20).build()
        );
    }

    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        pGuiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}
