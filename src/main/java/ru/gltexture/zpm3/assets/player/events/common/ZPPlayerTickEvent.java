package ru.gltexture.zpm3.assets.player.events.common;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.player.ZPPlayerAsset;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

public class ZPPlayerTickEvent implements ZPEventClass {
    public ZPPlayerTickEvent() {
    }

    @SubscribeEvent
    public static void exec(TickEvent.@NotNull PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (event.side == LogicalSide.SERVER) {
            ZPPlayerAsset.serverSideLogic.onTickPlayer(event.phase, event.player);
        }
        if (event.side == LogicalSide.CLIENT) {
            ZPPlayerAsset.clientSideLogic.onTickPlayer(event.phase, event.player);
        }
        ZPPlayerAsset.bothSidesLogic.onTickPlayer(event.phase, event.player);
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
