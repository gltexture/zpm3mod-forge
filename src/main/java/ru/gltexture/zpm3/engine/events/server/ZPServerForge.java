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
import ru.gltexture.zpm3.engine.events.common.ZPCommonMod;
import ru.gltexture.zpm3.engine.helpers.ZPLootTableHelper;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public final class ZPServerForge {
    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();
        long seed = server.getWorldData().worldGenOptions().seed();
        ZPServerForge.clearLootTableMaps();
        CompletableFuture.runAsync(() -> {
            ZPRandom.instance.init(seed);
        });
    }

    private static void clearLootTableMaps() {
        ZPLootTableHelper.clearPoolMapToCreate();
        ZPLootTableHelper.clearExistingPoolMap();
    }

    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation id = event.getName();
        Set<Supplier<LootPool>> lootPools = ZPLootTableHelper.getExistingLootPoolsToChange(id);
        if (lootPools != null) {
            LootTable table = LootTable.lootTable().build();
            lootPools.forEach(e -> table.addPool(e.get()));
            event.setTable(table);
        }
    }
}
