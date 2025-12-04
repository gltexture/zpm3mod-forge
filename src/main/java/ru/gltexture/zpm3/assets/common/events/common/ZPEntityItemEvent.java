package ru.gltexture.zpm3.assets.common.events.common;

import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.mixins.ext.IZPPlayerMixinExt;

public class ZPEntityItemEvent implements ZPEventClass {
    @SubscribeEvent
    public static void exec(@NotNull EntityItemPickupEvent event) {
        if (event.getEntity() instanceof IZPPlayerMixinExt ext) {
            if (ext.enabledPickUpOnF()) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
