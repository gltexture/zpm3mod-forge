package ru.gltexture.zpm3.engine.core;

import ru.gltexture.zpm3.engine.client.callbacking.ZPCallbacksManager;
import ru.gltexture.zpm3.engine.client.init.ZPClientInitManager;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;

abstract class ZPDefaultSysInit {
    static void initZPSystems() {
        ZPClientInitManager.setupRunner(ZPCallbacksManager.INSTANCE::setupResources);
        ZPClientInitManager.setupRunner(ZPRenderHelper.INSTANCE::setupResources);
    }

    static void destroyZPSystems() {
        ZPClientInitManager.destroyRunner(ZPCallbacksManager.INSTANCE::destroyResources);
        ZPClientInitManager.destroyRunner(ZPRenderHelper.INSTANCE::destroyResources);
    }
}