package ru.gltexture.zpm3.modules.guns.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.modules.guns.rendering.ZPGunLayersProcessing;
import ru.gltexture.zpm3.modules.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.modules.guns.rendering.tracer.ZPBulletTracerManager;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

@OnlyIn(Dist.CLIENT)
public class ZPGunPostRender implements ZPEventClass {
    @SubscribeEvent
    public static void exec(@NotNull RenderLevelStageEvent renderLevelStageEvent) {
        if (renderLevelStageEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            ZPGunLayersProcessing.postRender((ZPDefaultGunMuzzleflashFX) ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        } else if (renderLevelStageEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            ZPBulletTracerManager.INSTANCE.renderAll(renderLevelStageEvent.getPoseStack(), renderLevelStageEvent.getPartialTick(), ZPRenderHelper.DELTA_TIME());
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
