package ru.gltexture.zpm3.modules.ui.screen;

import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Objects;

public abstract class ZPScreen extends Screen {
    private final Screen parent;

    public ZPScreen(Component pTitle, Screen parent) {
        super(pTitle);
        this.parent = parent;
    }

    @Override
    public void onClose() {
        Objects.requireNonNull(this.minecraft).setScreen(this.parent);
    }

    public Screen getParent() {
        return this.parent;
    }
}
