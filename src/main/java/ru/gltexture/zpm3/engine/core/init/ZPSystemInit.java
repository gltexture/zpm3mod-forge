package ru.gltexture.zpm3.engine.core.init;

import com.mojang.blaze3d.platform.Window;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public abstract class ZPSystemInit {
    @OnlyIn(Dist.CLIENT)
    public static void client() {
        ZPClientInitManager.INSTANCE.setupRunner((w) -> ZPRenderHelper.INSTANCE.init());
        ZPSystemInit.callbackRuns();
        ZPClientInitManager.INSTANCE.setupRunner((w) -> {
            try {
                ZPClientCallbacksManager.INSTANCE.getOnSetupResourcesCallbacks().forEach(e -> e.setupResources(w));
            } catch (ConcurrentModificationException e) {
                throw new ZPRuntimeException("Tried to create setup callback, during setup processing");
            }
        });

        ZPClientInitManager.INSTANCE.destroyRunner((w) -> {
            try {
                ZPClientCallbacksManager.INSTANCE.getOnDestroyResourcesCallbacks().forEach(e -> e.destroyResources(w));
            } catch (ConcurrentModificationException e) {
                throw new ZPRuntimeException("Tried to create destroy callback, during destroy processing");
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    private static void callbackRuns() {
        ZPClientInitManager.INSTANCE.setupRunner(ZPClientCallbacksManager.INSTANCE::setupResources);
        ZPClientInitManager.INSTANCE.destroyRunner(ZPClientCallbacksManager.INSTANCE::destroyResources);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientRunSetup(@NotNull Window window) {
        ZPClientInitManager.INSTANCE.runSetup(window);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientRunDestroy(@NotNull Window window) {
        ZPClientInitManager.INSTANCE.runDestroy(window);
    }
}