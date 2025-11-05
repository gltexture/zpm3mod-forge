package ru.gltexture.zpm3.assets.player.events.common;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.player.ZPPlayerAsset;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPSimpleEventClass;

public class ZPPlayerTickEvent implements ZPSimpleEventClass<TickEvent.PlayerTickEvent> {
    public ZPPlayerTickEvent() {
    }

    @Override
    public void exec(TickEvent.@NotNull PlayerTickEvent event) {
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
    public @NotNull Class<TickEvent.PlayerTickEvent> getEventType() {
        return TickEvent.PlayerTickEvent.class;
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public Mod.EventBusSubscriber.@NotNull Bus getBus() {
        return Mod.EventBusSubscriber.Bus.MOD;
    }
}
