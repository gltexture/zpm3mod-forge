package ru.gltexture.zpm3.assets.guns.events;

import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.assets.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.assets.guns.rendering.ZPGunLayersProcessing;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.assets.guns.rendering.tracer.ZPBulletTracerManager;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

public class ZPGunPostRender implements ZPEventClass {
    private static double lastFrameTime = 0.0f;
    private static double deltaTime = -1.0f;

    @SubscribeEvent
    public static void exec(@NotNull RenderLevelStageEvent renderLevelStageEvent) {
        if (renderLevelStageEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            ZPGunLayersProcessing.postRender((ZPDefaultGunMuzzleflashFX) ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        } else if (renderLevelStageEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            double currentTime = GLFW.glfwGetTime();
            ZPGunPostRender.deltaTime = currentTime - lastFrameTime;
            ZPGunPostRender.lastFrameTime = currentTime;
            ZPBulletTracerManager.INSTANCE.renderAll(renderLevelStageEvent.getPoseStack(), renderLevelStageEvent.getPartialTick(), (float) ZPGunPostRender.deltaTime);
        }
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
