package ru.gltexture.zpm3.engine.client.rendering.ui.imgui;

import com.mojang.blaze3d.platform.Window;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.ZPGunLayersProcessing;
import ru.gltexture.zpm3.engine.client.rendering.ui.imgui.interfaces.DearUIInterface;

import java.util.HashSet;
import java.util.Set;

public class ZPDearUIInterfacesManager {
    private final Set<DearUIInterface> dearUIInterfaceSet;
    private final ZPDearUIRenderer dearUIRenderer;

    public ZPDearUIInterfacesManager(@NotNull ZPDearUIRenderer zpDearUIRenderer) {
        this.dearUIInterfaceSet = new HashSet<>();
        this.dearUIRenderer = zpDearUIRenderer;
    }

    public void renderAll(@NotNull Window window, float renderTicking) {
        this.getDearUIInterfaceSet().forEach(e -> {
            this.getDearUIRenderer().onRender(window, e, renderTicking);
        });
    }

    public void removeActiveInterface(@NotNull DearUIInterface dearUIInterface) {
        this.getDearUIInterfaceSet().remove(dearUIInterface);
    }

    public void addActiveInterface(@NotNull DearUIInterface dearUIInterface) {
        this.getDearUIInterfaceSet().add(dearUIInterface);
    }

    public ZPDearUIRenderer getDearUIRenderer() {
        return this.dearUIRenderer;
    }

    public void clear() {
        this.getDearUIInterfaceSet().clear();
    }

    public Set<DearUIInterface> getDearUIInterfaceSet() {
        return this.dearUIInterfaceSet;
    }
}
