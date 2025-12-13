package ru.gltexture.zpm3.assets.commands.zones.events;

import net.minecraft.client.telemetry.events.WorldLoadEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.commands.zones.ZPFlagZones;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

public class ZPLevelSaveReadEvents implements ZPEventClass {
    public ZPLevelSaveReadEvents() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel level) {
            ZPFlagZones.INSTANCE.loadFromJSON(level);
        }
    }

    @SubscribeEvent
    public static void onWorldSave(LevelEvent.Save event) {
        if (event.getLevel() instanceof ServerLevel level) {
            ZPFlagZones.INSTANCE.writeToJSON(level);
        }
    }
}