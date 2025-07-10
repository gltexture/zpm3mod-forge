package ru.gltexture.zpm3.engine.client.rendering.ui.imgui.interfaces;

import com.mojang.blaze3d.platform.Window;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import net.minecraft.client.MouseHandler;
import org.jetbrains.annotations.NotNull;

public class DearUIMenuInterface implements DearUIInterface {
    public void drawGui(@NotNull Window window, @NotNull MouseHandler mouseHandler) {
        ImGui.setNextWindowPos(20, 20, ImGuiCond.Always);
        ImGui.showDemoWindow();
    }
}
