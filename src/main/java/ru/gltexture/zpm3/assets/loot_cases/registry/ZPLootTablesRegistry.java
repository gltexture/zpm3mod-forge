package ru.gltexture.zpm3.assets.loot_cases.registry;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.loot_cases.loot_tables.ZPLootTable;

import java.util.ArrayList;
import java.util.List;

public abstract class ZPLootTablesRegistry {
    private static List<ZPLootTablesRegistry> allRegistries = new ArrayList<>();

    public static void REG(@NotNull ZPLootTablesRegistry registry) {
        ZPLootTablesRegistry.allRegistries.add(registry);
    }

    public static List<ZPLootTablesRegistry> ALL_REG() {
        return ZPLootTablesRegistry.allRegistries;
    }

    public static void CLEAR_REG() {
        ZPLootTablesRegistry.allRegistries = null;
    }


    private final List<ZPLootTable> zpLootTableList;

    public ZPLootTablesRegistry() {
        this.zpLootTableList = new ArrayList<>();
    }

    public List<ZPLootTable> getZpLootTableList() {
        return this.zpLootTableList;
    }

    public abstract void init();

    protected void register(@NotNull ZPLootTable lootTable) {
        this.zpLootTableList.add(lootTable);
    }
}
