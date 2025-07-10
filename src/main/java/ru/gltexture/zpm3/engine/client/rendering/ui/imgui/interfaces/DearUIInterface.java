package ru.gltexture.zpm3.engine.client.rendering.ui.imgui.interfaces;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.MouseHandler;
import org.jetbrains.annotations.NotNull;

public interface DearUIInterface {
    void drawGui(@NotNull Window window, @NotNull MouseHandler mouseHandler);
}
