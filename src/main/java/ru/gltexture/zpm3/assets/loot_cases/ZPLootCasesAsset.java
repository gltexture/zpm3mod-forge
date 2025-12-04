package ru.gltexture.zpm3.assets.loot_cases;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.loot_cases.events.ZPLootTablesGatherDataEvent;
import ru.gltexture.zpm3.assets.loot_cases.init.ZPBlockLootCaseEntities;
import ru.gltexture.zpm3.assets.loot_cases.init.ZPLootCaseBlockItems;
import ru.gltexture.zpm3.assets.loot_cases.init.ZPLootCases;
import ru.gltexture.zpm3.assets.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.assets.loot_cases.registry.ZPLootTablesRegistry;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;

public class ZPLootCasesAsset extends ZPAsset {
    public ZPLootCasesAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPLootCasesAsset() {
    }

    @Override
    public void commonSetup() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientDestroy() {

    }

    @Override
    public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
        //mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("debug", "ru.gltexture.zpm3.assets.debug.mixins.impl"),
        //        new ZombiePlague3.IMixinEntry.MixinClass("client.ZPCameraMixin", ZPSide.CLIENT),
        //        new ZombiePlague3.IMixinEntry.MixinClass("client.ZPInputMixin", ZPSide.CLIENT));
    }

    @Override
    public void initializeAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        //assetEntry.addEventClass(ZPFreeCameraEvents.class);
        assetEntry.addEventClass(ZPLootTablesGatherDataEvent.class);

        assetEntry.setLootTablesRegistry(new ZPDefaultLootTables());

        assetEntry.addZP3RegistryClass(ZPLootCases.class);
        assetEntry.addZP3RegistryClass(ZPBlockLootCaseEntities.class);
        assetEntry.addZP3RegistryClass(ZPLootCaseBlockItems.class);
    }

    @Override
    public void preCommonInitializeAsset() {

    }

    public static class ZPDefaultLootTables extends ZPLootTablesRegistry {
        private static ZPLootTable loot_debris = ZPLootTable.builder("loot_debris")
                .commonGroup("trash", 20, (g) -> g
                        .addNonBreakable("minecraft:dead_bush",           8, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:stick",               6, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:bowl",                4, 1, 2, 1.0f)
                        .addNonBreakable("minecraft:string",              5, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:cobweb",              3, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:bone",                4, 1, 2, 1.0f)
                        .addNonBreakable("minecraft:rotten_flesh",        7, 1, 4, 1.0f)
                        .addNonBreakable("minecraft:leather",             3, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:cod",                 2, 1, 1, 1.0f)
                        .addBreakable("minecraft:wooden_sword",           5, 0.8f, 1.0f, 1.0f)
                        .addBreakable("minecraft:wooden_pickaxe",         5, 0.8f, 1.0f, 1.0f)
                        .addBreakable("minecraft:wooden_axe",             5, 0.8f, 1.0f, 1.0f)
                        .addBreakable("minecraft:wooden_shovel",          5, 0.8f, 1.0f, 1.0f)
                        .addBreakable("minecraft:wooden_hoe",             5, 0.8f, 1.0f, 1.0f)
                )
                .build(3, 8, 0.8f);

        private static ZPLootTable loot_case_tier_low = ZPLootTable.builder("loot_case_tier_low")
                .lootCase("tier1", true, 12000)
                .extendBy("loot_debris")
                .commonGroup("swords", 10, (g) -> g
                        .addBreakable("minecraft:iron_sword",             1, 0.0f, 1.0f, 2.0f)
                        .addBreakable("minecraft:wooden_sword",           5, 0.0f, 1.0f, 1.2f)
                )
                .bonusGroup("bonus", 0.5f, (g) -> g
                        .addNonBreakable("minecraft:diamond",             1, 1, 6, 3.0f)
                )
                .build(1, 0.8f);

        @Override
        public void init() {
            this.register(ZPDefaultLootTables.loot_case_tier_low);
            this.register(ZPDefaultLootTables.loot_debris);
        }
    }
}
