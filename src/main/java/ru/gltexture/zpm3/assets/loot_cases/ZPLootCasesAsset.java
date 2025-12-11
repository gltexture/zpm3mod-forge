package ru.gltexture.zpm3.assets.loot_cases;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
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
import ru.gltexture.zpm3.engine.instances.items.ZPItemFood;

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

    @Override
    public void postCommonInitializeAsset() {

    }

    public static class ZPDefaultLootTables extends ZPLootTablesRegistry {
        private static ZPLootTable loot_debris = ZPLootTable.builder("loot_debris")
                .commonGroup("trash", 20, (g) -> g
                        .addNonBreakable("minecraft:dead_bush",           24,  1, 1, 1.0f)
                        .addNonBreakable("minecraft:stick",               1,  1, 1, 1.0f)
                        .addNonBreakable("minecraft:bowl",                2,  1, 1, 1.0f)
                        .addNonBreakable("minecraft:string",              1,  1, 1, 1.0f)
                        .addNonBreakable("minecraft:cobweb",              1,  1, 1, 1.0f)
                        .addNonBreakable("minecraft:bone",                1,  1, 1, 1.0f)
                        .addNonBreakable("minecraft:rotten_flesh",        8, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:leather",             1,  1, 1, 1.0f)
                        .addNonBreakable("zpm3:torch5",                   26, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:torch4",                   12, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:paper",               3, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:brick",               4, 1, 1, 1.0f)
                        .addBreakable("minecraft:wooden_sword",           2, 0.8f, 1.0f, 1.0f)
                        .addBreakable("minecraft:wooden_pickaxe",         2, 0.8f, 1.0f, 1.0f)
                        .addBreakable("minecraft:wooden_axe",             2, 0.8f, 1.0f, 1.0f)
                        .addBreakable("minecraft:wooden_shovel",          2, 0.8f, 1.0f, 1.0f)
                        .addBreakable("minecraft:wooden_hoe",             2, 0.8f, 1.0f, 1.0f)
                        .addBreakable("zpm3:bat",                         1, 0.8f, 1.0f, 1.0f)
                        .addBreakable("zpm3:pipe",                        1, 0.8f, 1.0f, 1.0f)
                )
                .bonusGroup("bonus", 0.03f, (g) -> g
                        .addNonBreakable("zpm3:scrap_material",           1,  1, 1, 1.0f)
                        .addNonBreakable("zpm3:table_material",           1,  1, 1, 1.0f)
                        .addNonBreakable("zpm3:shelves_material",         1,  1, 1, 1.0f)
                        .addBreakable("minecraft:stone_sword",            1, 0.6f, 1.0f, 1.0f)
                        .addBreakable("minecraft:stone_pickaxe",          1, 0.6f, 1.0f, 1.0f)
                        .addBreakable("minecraft:stone_axe",              1, 0.6f, 1.0f, 1.0f)
                        .addBreakable("minecraft:stone_shovel",           1, 0.6f, 1.0f, 1.0f)
                        .addBreakable("minecraft:stone_hoe",              1, 0.6f, 1.0f, 1.0f)
                        .addNonBreakable("minecraft:slime_ball",         1,   1, 1, 1.0f)
                        .addNonBreakable("minecraft:feather",                 2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:egg",                     2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:bone",                    2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:ink_sac",                 2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:glow_ink_sac",            2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:spider_eye",              2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:fermented_spider_eye",    2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:kelp",                    2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:honeycomb",               2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:paper",                   2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:flower_pot",              2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:blue_dye",                2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:red_dye",                 2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:green_dye",               2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:yellow_dye",              2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:white_dye",               2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:black_dye",               2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:purple_dye",              2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:pink_dye",                2, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:music_disc_13",           1, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:music_disc_chirp",        1, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:wall_lamp_off",        3, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:block_lamp_off",        3, 1, 1, 1.0f)
                )
                .build(3, 8, 1.0f, 0.8f);

        private static ZPLootTable loot_case_tier_low = ZPLootTable.builder("loot_case_tier_low")
                .lootCase("tier1", true, 12000)
                .extendBy("loot_debris")
                .commonGroup("armor", 10, (g) -> g
                        .addBreakable("minecraft:stone_sword",      20, 0.70f, 0.90f, 1.0f)
                        .addBreakable("minecraft:stone_pickaxe",    25, 0.65f, 0.90f, 1.0f)
                        .addBreakable("minecraft:stone_axe",        22, 0.65f, 0.95f, 1.0f)
                        .addBreakable("minecraft:stone_shovel",     18, 0.60f, 0.90f, 1.0f)
                        .addBreakable("minecraft:iron_sword",        6, 0.85f, 0.95f, 1.25f)
                        .addBreakable("minecraft:iron_pickaxe",      5, 0.85f, 0.95f, 1.25f)
                        .addBreakable("minecraft:iron_axe",          5, 0.85f, 0.95f, 1.25f)
                        .addBreakable("minecraft:iron_shovel",       7, 0.80f, 0.95f, 1.25f)
                        .addBreakable("minecraft:leather_helmet",   14, 0.60f, 0.85f, 1.0f)
                        .addBreakable("minecraft:leather_chestplate",12,0.60f, 0.85f, 1.0f)
                        .addBreakable("minecraft:leather_leggings", 12, 0.60f, 0.85f, 1.0f)
                        .addBreakable("minecraft:leather_boots",    14, 0.60f, 0.85f, 1.0f)
                        .addBreakable("minecraft:iron_helmet",        3, 0.80f, 0.97f, 1.25f)
                        .addBreakable("minecraft:iron_boots",         3, 0.80f, 0.97f, 1.25f)
                )
                .commonGroup("res", 50, (g) -> g
                        .addNonBreakable("minecraft:coal",         25, 1, 4, 1.0f)
                        .addNonBreakable("minecraft:stick",        30, 2, 6, 1.0f)
                        .addNonBreakable("minecraft:string",       20, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:feather",      16, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:flint",        18, 1, 2, 1.0f)
                        .addNonBreakable("minecraft:cooked_mutton",10, 1, 2, 1.0f)
                        .addNonBreakable("minecraft:leather",      14, 1, 2, 1.0f)
                        .addNonBreakable("minecraft:bones",        12, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:paper",        10, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:iron_nugget",  20, 2, 5, 1.2f)
                        .addNonBreakable("minecraft:copper_ingot", 10, 1, 1, 1.2f)
                        .addNonBreakable("minecraft:torch",        22, 2, 5, 1.0f)
                        .addNonBreakable("minecraft:arrow",        16, 2, 6, 1.0f)
                        .addNonBreakable("minecraft:bucket",        5, 1, 1, 1.3f)
                        .addNonBreakable("minecraft:campfire",      3, 1, 1, 1.4f)
                        .addNonBreakable("minecraft:lapis_lazuli",  6, 1, 2, 1.3f)
                )
                .commonGroup("weapons", 4, g -> g
                        .addBreakable("zpm3:handmade_pistol", 1, 0.1f, 1.0f, 1.2f)
                        .addBreakable("zpm3:makarov", 1, 0.1f, 1.0f, 1.2f)
                        .addBreakable("zpm3:m1911", 1, 0.1f, 1.0f, 1.2f)
                        .addBreakable("zpm3:bat", 1, 0.2f, 1.0f, 1.2f)
                        .addBreakable("zpm3:pipe", 1, 0.2f, 1.0f, 1.2f)
                        .addBreakable("minecraft:stone_sword", 5, 0.2f, 0.9f, 1.2f)
                        .addBreakable("minecraft:stone_axe", 4, 0.2f, 0.9f, 1.2f)
                        .addBreakable("minecraft:stone_pickaxe", 3, 0.2f, 0.9f, 1.2f)
                        .addBreakable("zpm3:golf_club", 1, 0.2f, 1.0f, 1.0f)
                        .addBreakable("zpm3:cleaver", 1, 0.2f, 1.0f, 0.9f)
                        .addBreakable("zpm3:hatchet", 1, 0.2f, 1.0f, 1.0f)
                )
                .commonGroup("ammo", 4, g -> g
                        .addNonBreakable("minecraft:arrow", 6, 2, 6, 1.0f)
                        .addNonBreakable("zpm3:_makarov", 12, 1, 8, 1.0f)
                        .addNonBreakable("zpm3:_usp", 6, 2, 16, 1.0f)
                        .addNonBreakable("zpm3:_shotgun", 2, 1, 4, 1.2f)
                        .addNonBreakable("zpm3:_deagle", 3, 1, 4, 1.0f)
                        .addNonBreakable("zpm3:_uzi", 3, 1, 16, 1.0f)
                        .addNonBreakable("zpm3:_colt", 3, 1, 4, 1.0f)
                        .addNonBreakable("zpm3:_handmade_pistol", 4, 2, 8, 0.9f)
                        .addNonBreakable("minecraft:gunpowder", 4, 1, 3, 1.0f)
                )
                .commonGroup("tools", 18, g -> g
                        .addBreakable("zpm3:crowbar", 1, 0.0f, 0.75f, 1.0f)
                        .addBreakable("zpm3:pipe", 1, 0.0f, 0.75f, 1.0f)
                        .addBreakable("minecraft:stone_pickaxe", 5, 0.0f, 0.9f, 1.0f)
                        .addBreakable("minecraft:stone_shovel", 4, 0.0f, 0.9f, 1.0f)
                        .addBreakable("zpm3:matches", 1, 0.0f, 0.9f, 1.0f)
                        .addNonBreakable("minecraft:flint", 6, 1, 2, 1.0f)
                        .addNonBreakable("minecraft:bucket", 3, 1, 1, 1.0f)
                )
                .commonGroup("meds", 3, g -> g
                        .addNonBreakable("zpm3:bandage", 2, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:anti_headache_pill", 1, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:anti_hunger_pill", 1, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:anti_poison_pill", 1, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:tire", 1, 1, 1, 1.0f)
                )
                .commonGroup("food", 8, g -> g
                        .addNonBreakable("zpm3:peaches", 1, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:soda", 1, 1, 2, 1.0f)
                        .addNonBreakable("minecraft:bread", 2, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:jam", 1, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:bean", 1, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:sprats", 1, 1, 2, 1.0f)
                )
                .commonGroup("blocks", 25, g -> g
                        .addNonBreakable("minecraft:stone", 12, 2, 8, 1.0f)
                        .addNonBreakable("minecraft:gravel", 10, 2, 6, 1.0f)
                        .addNonBreakable("minecraft:sand", 8, 2, 6, 1.0f)
                        .addNonBreakable("minecraft:glass", 6, 1, 4, 1.0f)
                        .addNonBreakable("minecraft:torch", 12, 2, 5, 1.0f)
                        .addNonBreakable("minecraft:fence", 8, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:slab", 10, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:stairs", 10, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:planks", 10, 2, 5, 1.0f)
                        .addNonBreakable("minecraft:cobblestone", 10, 2, 6, 1.0f)
                        .addNonBreakable("minecraft:dirt", 10, 2, 6, 1.0f)
                )
                .bonusGroup("bonus", 0.025f, (g) -> g
                        .addNonBreakable("zpm3:plate", 6, 4, 8, 1.1f)
                        .addNonBreakable("zpm3:rock", 1, 1, 4, 1.0f)
                        .addNonBreakable("minecraft:paper", 8, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:string", 8, 1, 3, 1.0f)
                        .addBreakable("zpm3:matches", 4, 0.0f, 1.0f, 0.5f)
                        .addNonBreakable("zpm3:scrap_material", 3, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:scrap_stack_material", 2, 1, 2, 1.0f)
                        .addNonBreakable("minecraft:coal", 5, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:aid_kit", 1, 1, 1, 0.3f)
                        .addNonBreakable("zpm3:better_vision_pill", 1, 1, 1, 0.25f)
                        .addNonBreakable("zpm3:meth_pill", 1, 1, 1, 0.15f)
                )
                .build(1, 3, 0.5f, 0.25f);

        private static ZPLootTable loot_case_tier_mid = ZPLootTable.builder("loot_case_tier_mid")
                .lootCase("tier2", true, 12000)
                .extendBy("loot_debris")
                .commonGroup("blocks", 50, g -> g
                        .addNonBreakable("minecraft:planks", 12, 3, 8, 1.0f)
                        .addNonBreakable("minecraft:cobblestone", 12, 3, 8, 1.0f)
                        .addNonBreakable("minecraft:stone", 10, 2, 6, 1.0f)
                        .addNonBreakable("minecraft:glass", 6, 1, 4, 1.0f)
                        .addNonBreakable("minecraft:slab", 10, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:stairs", 10, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:torch", 12, 2, 6, 1.0f)
                        .addNonBreakable("minecraft:fence", 8, 1, 4, 1.0f)
                        .addNonBreakable("minecraft:dirt", 6, 2, 6, 1.0f)
                        .addNonBreakable("minecraft:gravel", 6, 2, 5, 1.0f)
                        .addNonBreakable("minecraft:sand", 6, 2, 5, 1.0f)
                        .addNonBreakable("minecraft:bricks", 4, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:stone_bricks", 4, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:chest", 2, 1, 1, 1.0f)
                )
                .commonGroup("weapons", 12, g -> g
                        .addBreakable("zpm3:akm", 3, 0.1f, 0.85f, 1.1f)
                        .addBreakable("zpm3:deagle", 3, 0.1f, 0.85f, 1.1f)
                        .addBreakable("zpm3:m1911", 3, 0.1f, 0.85f, 1.1f)
                        .addBreakable("zpm3:makarov", 2, 0.1f, 0.9f, 1.1f)
                        .addBreakable("zpm3:uzi", 2, 0.1f, 0.9f, 1.1f)
                        .addBreakable("zpm3:colt", 2, 0.1f, 0.9f, 1.1f)
                        .addBreakable("minecraft:iron_sword", 4, 0.1f, 0.95f, 1.1f)
                        .addBreakable("minecraft:iron_axe", 3, 0.1f, 0.95f, 1.1f)
                        .addBreakable("minecraft:bow", 3, 0.1f, 0.9f, 1.1f)
                        .addBreakable("zpm3:golf_club", 2, 0.1f, 1.0f, 1.0f)
                        .addBreakable("zpm3:cleaver", 2, 0.1f, 1.0f, 0.9f)
                        .addBreakable("zpm3:bat", 2, 0.1f, 1.0f, 1.0f)
                        .addBreakable("zpm3:pipe", 2, 0.1f, 1.0f, 1.0f)
                )
                .commonGroup("ammo", 2, 0.3f, 12, g -> g
                        .addNonBreakable("zpm3:_makarov", 12, 8, 32, 1.0f)
                        .addNonBreakable("zpm3:_akm", 10, 12, 28, 1.0f)
                        .addNonBreakable("zpm3:_shotgun", 6, 2, 8, 1.0f)
                        .addNonBreakable("minecraft:arrow", 12, 4, 16, 1.0f)
                        .addNonBreakable("zpm3:_uzi", 8, 20, 30, 1.0f)
                        .addNonBreakable("zpm3:_usp", 6, 12, 32, 1.0f)
                        .addNonBreakable("zpm3:_deagle", 6, 8, 15, 1.0f)
                        .addNonBreakable("zpm3:_colt", 6, 8, 20, 1.0f)
                        .addNonBreakable("zpm3:_handmade_pistol", 4, 6, 12, 1.0f)
                        .addNonBreakable("minecraft:gunpowder", 5, 1, 3, 1.0f)
                )
                .commonGroup("armor", 8, g -> g
                        .addBreakable("minecraft:leather_helmet", 6, 0.65f, 0.9f, 1.0f)
                        .addBreakable("minecraft:leather_chestplate", 5, 0.65f, 0.9f, 1.0f)
                        .addBreakable("minecraft:leather_leggings", 5, 0.65f, 0.9f, 1.0f)
                        .addBreakable("minecraft:leather_boots", 6, 0.65f, 0.9f, 1.0f)
                        .addBreakable("minecraft:iron_helmet", 3, 0.8f, 0.97f, 1.2f)
                        .addBreakable("minecraft:iron_chestplate", 3, 0.8f, 0.95f, 1.2f)
                        .addBreakable("minecraft:iron_leggings", 3, 0.8f, 0.95f, 1.2f)
                        .addBreakable("minecraft:iron_boots", 3, 0.8f, 0.97f, 1.2f)
                )
                .commonGroup("tools", 10, g -> g
                        .addBreakable("zpm3:crowbar", 3, 0.3f, 0.9f, 1.0f)
                        .addBreakable("zpm3:pipe", 3, 0.3f, 0.9f, 1.0f)
                        .addBreakable("minecraft:iron_pickaxe", 4, 0.8f, 0.95f, 1.2f)
                        .addBreakable("minecraft:iron_shovel", 4, 0.8f, 0.95f, 1.2f)
                        .addNonBreakable("minecraft:bucket", 3, 1, 1, 1.2f)
                        .addNonBreakable("minecraft:flint", 6, 1, 3, 1.0f)
                        .addBreakable("zpm3:sledgehammer", 2, 0.3f, 0.9f, 1.0f)
                        .addBreakable("zpm3:hatchet", 2, 0.3f, 0.9f, 1.0f)
                        .addBreakable("zpm3:wrench", 2, 0.3f, 0.9f, 1.0f)
                )
                .commonGroup("food", 10, g -> g
                        .addNonBreakable("minecraft:bread", 4, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:peaches", 3, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:soda", 3, 1, 2, 1.0f)
                        .addNonBreakable("minecraft:cooked_beef", 4, 1, 3, 2.0f)
                        .addNonBreakable("zpm3:jam", 2, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:bean", 2, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:mysterious_can", 2, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:water", 2, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:sprats", 2, 1, 2, 1.0f)
                )
                .commonGroup("meds", 6, g -> g
                        .addNonBreakable("zpm3:bandage", 4, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:adrenaline_syringe", 2, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:anti_headache_pill", 2, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:anti_hunger_pill", 2, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:anti_poison_pill", 2, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:better_vision_pill", 1, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:morphine_syringe", 1, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:tire", 2, 1, 1, 1.0f)
                )
                .bonusGroup("bonus", 0.15f, g -> g
                        .addNonBreakable("zpm3:plate", 12, 4, 8, 1.1f)
                        .addNonBreakable("zpm3:acid_bottle", 2, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:rock", 1, 1, 1, 1.0f)
                        .addNonBreakable("minecraft:gold_ingot", 8, 1, 3, 1.3f)
                        .addNonBreakable("minecraft:iron_ingot", 8, 1, 2, 1.3f)
                        .addNonBreakable("minecraft:lapis_lazuli", 8, 2, 5, 1.2f)
                        .addNonBreakable("zpm3:meth_pill", 3, 1, 1, 0.3f)
                        .addNonBreakable("zpm3:cement_material", 8, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:scrap_material", 12, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:better_vision_pill", 3, 1, 1, 0.5f)
                        .addNonBreakable("zpm3:adrenaline_syringe", 3, 1, 1, 0.5f)
                )
                .build(1, 4, 0.65f, 0.6f);

        private static ZPLootTable loot_case_tier_hi = ZPLootTable.builder("loot_case_tier_hi")
                .lootCase("tier3", true, 12000)
                .extendBy("loot_debris")
                .commonGroup("blocks", 120, g -> g
                        .addNonBreakable("minecraft:planks", 20, 3, 12, 1.0f)
                        .addNonBreakable("minecraft:cobblestone", 20, 3, 12, 1.0f)
                        .addNonBreakable("minecraft:stone", 15, 2, 10, 1.0f)
                        .addNonBreakable("minecraft:glass", 10, 1, 5, 1.0f)
                        .addNonBreakable("minecraft:slab", 15, 1, 5, 1.0f)
                        .addNonBreakable("minecraft:stairs", 15, 1, 5, 1.0f)
                        .addNonBreakable("minecraft:torch", 20, 2, 8, 1.0f)
                        .addNonBreakable("minecraft:fence", 12, 1, 5, 1.0f)
                        .addNonBreakable("minecraft:dirt", 10, 2, 8, 1.0f)
                        .addNonBreakable("minecraft:gravel", 10, 2, 6, 1.0f)
                        .addNonBreakable("minecraft:sand", 10, 2, 6, 1.0f)
                        .addNonBreakable("zpm3:asphalt", 8, 1, 5, 1.0f)
                        .addNonBreakable("zpm3:asphalt_stairs", 6, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:asphalt_slab", 6, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:cement_material", 8, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:scrap", 8, 1, 4, 1.0f)
                        .addNonBreakable("zpm3:table_material", 5, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:wall_lamp", 3, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:block_lamp", 3, 1, 1, 1.0f)
                )
                .commonGroup("weapons", 30, g -> g
                        .addBreakable("zpm3:akm", 5, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:deagle", 5, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:colt", 4, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:handmade_pistol", 5, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:m1911", 5, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:makarov", 5, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:mosin", 4, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:shotgun", 4, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:usp", 4, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:uzi", 4, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:bat", 3, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:pipe", 3, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:golf_club", 2, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:hatchet", 2, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:cleaver", 2, 0.0f, 0.6f, 0.9f)
                        .addBreakable("zpm3:sledgehammer", 2, 0.0f, 0.6f, 1.0f)
                        .addBreakable("zpm3:hatchet", 2, 0.0f, 0.6f, 1.0f)
                )
                .commonGroup("ammo", 3, 0.7f, 30, g -> g
                        .addNonBreakable("zpm3:_akm", 10, 16, 32, 1.0f)
                        .addNonBreakable("zpm3:_deagle", 10, 16, 32, 1.0f)
                        .addNonBreakable("zpm3:_colt", 10, 16, 32, 1.0f)
                        .addNonBreakable("zpm3:_handmade_pistol", 10, 16, 32, 1.0f)
                        .addNonBreakable("zpm3:_m1911", 10, 16, 32, 1.0f)
                        .addNonBreakable("zpm3:_makarov", 10, 16, 32, 1.0f)
                        .addNonBreakable("zpm3:_mosin", 10, 8, 16, 1.0f)
                        .addNonBreakable("zpm3:_shotgun", 10, 8, 16, 1.0f)
                        .addNonBreakable("zpm3:_usp", 10, 16, 32, 1.0f)
                        .addNonBreakable("zpm3:_uzi", 10, 16, 32, 1.0f)
                        .addNonBreakable("minecraft:arrow", 5, 6, 24, 1.0f)
                )
                .commonGroup("tools", 20, g -> g
                        .addBreakable("zpm3:crowbar", 3, 0.3f, 1.0f, 1.0f)
                        .addBreakable("zpm3:pipe", 3, 0.3f, 1.0f, 1.0f)
                        .addBreakable("minecraft:iron_pickaxe", 5, 0.0f, 0.98f, 1.0f)
                        .addBreakable("minecraft:iron_shovel", 5, 0.0f, 0.98f, 1.0f)
                        .addBreakable("minecraft:diamond_pickaxe", 5, 0.15f, 0.98f, 1.25f)
                        .addBreakable("minecraft:diamond_shovel", 5, 0.15f, 0.98f, 1.25f)
                        .addNonBreakable("minecraft:bucket", 4, 1, 1, 1.2f)
                        .addNonBreakable("minecraft:flint", 5, 1, 3, 1.0f)
                        .addBreakable("zpm3:wrench", 2, 0.0f, 0.2f, 1.0f)
                )
                .commonGroup("meds", 30, g -> g
                        .addNonBreakable("zpm3:bandage", 5, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:military_bandage", 4, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:adrenaline_syringe", 4, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:anti_headache_pill", 4, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:anti_hunger_pill", 4, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:anti_poison_pill", 4, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:better_vision_pill", 4, 1, 2, 0.8f)
                        .addNonBreakable("zpm3:morphine_syringe", 4, 1, 2, 0.7f)
                        .addNonBreakable("zpm3:antibiotics_syringe", 3, 1, 2, 0.6f)
                        .addNonBreakable("zpm3:zplague_syringe", 2, 1, 2, 0.5f)
                        .addNonBreakable("zpm3:anti_zplague_syringe", 2, 1, 2, 0.5f)
                )
                .commonGroup("food", 20, g -> g
                        .addNonBreakable("minecraft:bread", 4, 1, 4, 1.0f)
                        .addNonBreakable("zpm3:peaches", 3, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:soda", 3, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:cooked_beef", 4, 1, 4, 1.0f)
                        .addNonBreakable("zpm3:jam", 3, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:sprats", 3, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:bean", 3, 1, 4, 1.0f)
                        .addNonBreakable("zpm3:hotdog", 2, 1, 3, 1.0f)
                        .addNonBreakable("zpm3:cheese", 2, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:mysterious_can", 2, 1, 2, 1.0f)
                )
                .bonusGroup("bonus", 0.35f, g -> g
                        .addNonBreakable("zpm3:plate", 3, 4, 16, 1.1f)
                        .addNonBreakable("zpm3:acid_bottle", 2, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:rock", 1, 1, 2, 1.0f)
                        .addNonBreakable("zpm3:acid_bucket", 1, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:toxicwater_bucket", 1, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:scrap_material", 4, 1, 4, 1.0f)
                        .addNonBreakable("zpm3:scrap_stack_material", 4, 1, 3, 1.0f)
                        .addNonBreakable("minecraft:diamond", 6, 1, 3, 1.5f)
                        .addNonBreakable("minecraft:emerald", 5, 1, 3, 1.4f)
                        .addNonBreakable("minecraft:gold_ingot", 6, 1, 4, 1.4f)
                        .addNonBreakable("minecraft:lapis_lazuli", 6, 3, 8, 1.3f)
                        .addNonBreakable("minecraft:redstone", 6, 4, 12, 1.2f)
                        .addNonBreakable("zpm3:aid_kit", 5, 1, 3, 0.5f)
                        .addNonBreakable("zpm3:better_vision_pill", 4, 1, 2, 0.5f)
                        .addNonBreakable("zpm3:meth_pill", 3, 1, 2, 0.3f)
                        .addNonBreakable("zpm3:adrenaline_syringe", 3, 1, 2, 0.4f)
                        .addNonBreakable("zpm3:scrap_material", 5, 1, 4, 1.0f)
                        .addNonBreakable("zpm3:cement_material", 10, 1, 1, 1.0f)
                        .addNonBreakable("zpm3:armor_black", 2, 1, 1, 1.2f)
                        .addNonBreakable("zpm3:armor_green", 2, 1, 1, 1.2f)
                )
                .build(1, 3, 0.8f, 0.8f);

        @Override
        public void init() {
            this.register(ZPDefaultLootTables.loot_case_tier_low);
            this.register(ZPDefaultLootTables.loot_case_tier_mid);
            this.register(ZPDefaultLootTables.loot_case_tier_hi);
            this.register(ZPDefaultLootTables.loot_debris);
        }
    }
}
