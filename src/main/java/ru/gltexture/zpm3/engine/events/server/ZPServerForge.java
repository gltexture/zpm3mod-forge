package ru.gltexture.zpm3.engine.events.server;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.events.ZPAbstractEventMod;
import ru.gltexture.zpm3.engine.helpers.ZPLootTableHelper;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ZombiePlague3.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public final class ZPServerForge extends ZPAbstractEventMod {
    public ZPServerForge() {
        super();
    }

    @Override
    @SubscribeEvent
    public void onAnyZPEvent(Event event) {
        super.defaultExec(event);
    }
}
