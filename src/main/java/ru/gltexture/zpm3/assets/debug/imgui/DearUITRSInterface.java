package ru.gltexture.zpm3.assets.debug.imgui;

import com.mojang.blaze3d.platform.Window;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import net.minecraft.client.MouseHandler;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.rendering.ui.imgui.interfaces.DearUIInterface;

public class DearUITRSInterface implements DearUIInterface {
    public static boolean ENABLE_TRS_DEBUG = false;

    public static float[] trsPosX = new float[] {0.0f};
    public static float[] trsPosY = new float[] {0.0f};
    public static float[] trsPosZ = new float[] {0.0f};

    public static float[] trsAngleX = new float[] {0.0f};
    public static float[] trsAngleY = new float[] {0.0f};
    public static float[] trsAngleZ = new float[] {0.0f};

    public static float[] trsScaleX = new float[] {1.0f};
    public static float[] trsScaleY = new float[] {1.0f};
    public static float[] trsScaleZ = new float[] {1.0f};

    public void drawGui(@NotNull Window window, @NotNull MouseHandler mouseHandler) {
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Once);
        ImGui.setNextWindowSize(400, 600, ImGuiCond.Once);
        ImGui.begin("debug");
        if (ImGui.checkbox("TRS", DearUITRSInterface.ENABLE_TRS_DEBUG)) {
            DearUITRSInterface.ENABLE_TRS_DEBUG = !DearUITRSInterface.ENABLE_TRS_DEBUG;
        }
        if (ImGui.treeNode("TRS")) {
            ImGui.dragFloat("Translation X", DearUITRSInterface.trsPosX, 0.01f);
            ImGui.dragFloat("Translation Y", DearUITRSInterface.trsPosY, 0.01f);
            ImGui.dragFloat("Translation Z", DearUITRSInterface.trsPosZ, 0.01f);
            ImGui.separator();
            ImGui.dragFloat("Angle X", DearUITRSInterface.trsAngleX, 0.01f);
            ImGui.dragFloat("Angle Y", DearUITRSInterface.trsAngleY, 0.01f);
            ImGui.dragFloat("Angle Z", DearUITRSInterface.trsAngleZ, 0.01f);
            ImGui.separator();
            ImGui.dragFloat("Scale X", DearUITRSInterface.trsScaleX, 0.01f);
            ImGui.dragFloat("Scale Y", DearUITRSInterface.trsScaleY, 0.01f);
            ImGui.dragFloat("Scale Z", DearUITRSInterface.trsScaleZ, 0.01f);
            ImGui.treePop();
        }
        ImGui.end();
    }
}
