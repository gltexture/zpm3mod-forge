package ru.gltexture.zpm3.assets.player.events.both;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.player.ZPPlayerAsset;
import ru.gltexture.zpm3.engine.events.ZPEvent;

public class ZPPlayerTickEvent implements ZPEvent<TickEvent.PlayerTickEvent> {
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
    public @NotNull Side getSide() {
        return Side.BOTH;
    }
}
