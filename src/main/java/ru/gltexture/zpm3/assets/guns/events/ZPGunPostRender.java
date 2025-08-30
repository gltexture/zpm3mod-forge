package ru.gltexture.zpm3.assets.guns.events;

import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.assets.guns.rendering.ZPGunLayersProcessing;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPSimpleEventClass;

public class ZPGunPostRender implements ZPSimpleEventClass<RenderLevelStageEvent> {
    @Override
    public void exec(@NotNull RenderLevelStageEvent renderLevelStageEvent) {
        if (renderLevelStageEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            ZPGunLayersProcessing.postRender((ZPDefaultGunMuzzleflashFX) ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
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
