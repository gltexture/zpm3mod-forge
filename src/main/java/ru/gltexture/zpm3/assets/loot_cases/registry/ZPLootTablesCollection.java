package ru.gltexture.zpm3.assets.loot_cases.registry;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.loot_cases.loot_tables.ZPLootTable;
import java.util.*;

public class ZPLootTablesCollection {
    public static ZPLootTablesCollection INSTANCE = new ZPLootTablesCollection();

    private final Map<String, ZPLootTable> lootTableMap;

    private ZPLootTablesCollection() {
        this.lootTableMap = new HashMap<>();
    }

    public ZPLootTable get(String id) {
        return this.lootTableMap.get(id);
    }

    public Collection<ZPLootTable> getAllLootTables() {
        return this.lootTableMap.values();
    }

    public void putInMap(@NotNull ZPLootTable lootTable) {
        this.lootTableMap.put(lootTable.getUniqueId(), lootTable);
    }
}