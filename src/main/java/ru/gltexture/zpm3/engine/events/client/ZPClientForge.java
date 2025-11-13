package ru.gltexture.zpm3.engine.events.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

public final class ZPClientForge {
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
}
