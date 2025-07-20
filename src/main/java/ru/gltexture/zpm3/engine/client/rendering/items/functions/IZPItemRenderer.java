package ru.gltexture.zpm3.engine.client.rendering.items.functions;

import ru.gltexture.zpm3.engine.client.callbacking.ZPCallbacks;

public interface IZPItemRenderer extends ZPCallbacks.ZPWindowResizeCallback {
    void setupResources();
    void destroyResources();

    void onClientTicking();

    default void onPreRender(double partialTicks) {
    }

    void onRender(double partialTicks);

    default void onPostRender(double partialTicks) {
    }
}
