package ru.gltexture.zpm3.assets.loot_cases.events.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.assets.loot_cases.registry.ZPLootTablesRegistry;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ZPLootTableProvider implements DataProvider {
    public static final String indexFile = "index.json";

    private final PackOutput output;
    private final Gson gson;

    public ZPLootTableProvider(PackOutput output) {
        this.output = output;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        Path folder = this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(ZombiePlague3.MOD_ID()).resolve("zp_loot_tables");

        List<String> allIds = new ArrayList<>();
        List<ZPLootTable> allTables = new ArrayList<>();

        List<ZPLootTablesRegistry> regs = ZPLootTablesRegistry.ALL_REG();
        regs.forEach(reg -> {
            reg.init();
            allTables.addAll(reg.getZpLootTableList());
        });

        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (ZPLootTable table : allTables) {
            String id = table.getUniqueId();
            allIds.add(id);

            Path file = folder.resolve(id + ".json");

            String json = gson.toJson(table);
            futures.add(DataProvider.saveStable(cache, JsonParser.parseString(json), file));
        }

        {
            Path index = folder.resolve(ZPLootTableProvider.indexFile);
            String jsonIndex = gson.toJson(allIds);
            futures.add(DataProvider.saveStable(cache, JsonParser.parseString(jsonIndex), index));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public @NotNull String getName() {
        return "ZP LootTables Provider";
    }
}
