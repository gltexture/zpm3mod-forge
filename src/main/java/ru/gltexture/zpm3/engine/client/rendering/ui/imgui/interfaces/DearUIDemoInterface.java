package ru.gltexture.zpm3.engine.client.rendering.ui.imgui.interfaces;

import com.mojang.blaze3d.platform.Window;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import net.minecraft.client.MouseHandler;
import org.jetbrains.annotations.NotNull;

public class DearUIDemoInterface implements DearUIInterface {
    public void drawGui(@NotNull Window window, @NotNull MouseHandler mouseHandler) {
        ImGui.showDemoWindow();
    }
}
