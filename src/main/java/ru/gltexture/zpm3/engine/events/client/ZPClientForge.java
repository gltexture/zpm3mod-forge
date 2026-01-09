package ru.gltexture.zpm3.engine.events.client;

import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.mixins.impl.client.ZPItemRenderLayerMixin;

public final class ZPClientForge {
    private static float LAST_RENDER_DELTA_TIME;
    public static float RENDER_DELTA_TIME;

    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
    }

    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
    }

    @SubscribeEvent
    public void onRenderWorld(RenderLevelStageEvent event) {
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ZPClientCallbacksManager.INSTANCE.tickClientCallbacks(event.phase);
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            double currentTime = GLFW.glfwGetTime();
            ZPClientForge.RENDER_DELTA_TIME = (float) (currentTime - ZPClientForge.LAST_RENDER_DELTA_TIME);
            ZPClientForge.LAST_RENDER_DELTA_TIME = (float) currentTime;
        }
    }
}
