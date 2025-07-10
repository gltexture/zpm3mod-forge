package ru.gltexture.zpm3.engine.client.rendering.resources;

import com.mojang.blaze3d.platform.Window;
import org.jetbrains.annotations.NotNull;

public interface IZPResourceInit {
    void setupResources(@NotNull final Window window);
    void destroyResources(@NotNull final Window window);
}