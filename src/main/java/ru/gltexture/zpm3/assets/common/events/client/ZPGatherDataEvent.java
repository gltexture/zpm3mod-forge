package ru.gltexture.zpm3.assets.common.events.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.events.ZPEvent;

public class ZPGatherDataEvent implements ZPEvent<GatherDataEvent> {
    @Override
    public void exec(@NotNull GatherDataEvent gatherDataEvent) {

    }

    @Override
    public @NotNull Class<GatherDataEvent> getEventType() {
        return GatherDataEvent.class;
    }

    @Override
    public @NotNull Dist getDist() {
        return Dist.CLIENT;
    }
}