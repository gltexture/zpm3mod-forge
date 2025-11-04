package ru.gltexture.zpm3.assets.guns.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.assets.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.assets.guns.rendering.ZPGunLayersProcessing;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.assets.guns.rendering.tracer.ZPBulletTracerManager;
import ru.gltexture.zpm3.engine.client.utils.ClientRenderFunctions;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPSimpleEventClass;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ZPGunPostRender implements ZPSimpleEventClass<RenderLevelStageEvent> {
    private static double lastFrameTime = 0.0f;
    private static double deltaTime = -1.0f;

    public static List<Consumer<Void>> laterRenderMflashes = new ArrayList<>();

    @Override
    public void exec(@NotNull RenderLevelStageEvent renderLevelStageEvent) {
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
    public @NotNull Class<RenderLevelStageEvent> getEventType() {
        return RenderLevelStageEvent.class;
    }

    @Override
    public Mod.EventBusSubscriber.@NotNull Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }
}
