/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.loot_cases;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePostInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePreInitContext;
import ru.gltexture.zpm3.engine.nbt.ZPTagID;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.loot_cases.events.ZPLootTablesGatherDataEvent;
import ru.gltexture.zpm3.modules.loot_cases.events.ZPLootTablesReloadDataEvent;
import ru.gltexture.zpm3.modules.loot_cases.init.ZPBlockLootCaseEntities;
import ru.gltexture.zpm3.modules.loot_cases.init.ZPLootCaseBlockItems;
import ru.gltexture.zpm3.modules.loot_cases.init.ZPLootCases;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.container.IZPLootNbtContainer;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.random.ZPRandomization;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModule;
import ru.gltexture.zpm3.engine.core.api.modules.ZPModuleData;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.synthetic.ZPSyntheticLootCaseDescription;

import java.util.List;

public class ZPLootCasesModule extends ZPModule {
    public ZPLootCasesModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPLootCasesModule() {
    }

    @Override
    public void commonSetup() {
    }

    @Override
    public void commonShutdown() {
        
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup(@NotNull IModuleClientSetupContext context) {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {

    }

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    //mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("debug", "ru.gltexture.zpm3.modules.debug.mixins.impl"),
    //    //        new ZombiePlague3.IMixinEntry.MixinClass("client.ZPCameraMixin", ZPSide.CLIENT),
    //    //        new ZombiePlague3.IMixinEntry.MixinClass("client.ZPInputMixin", ZPSide.CLIENT));
    //}

    @Override
    public void initialize(@NotNull IModuleInitContext context) {
        //assetEntry.addEventClass(ZPFreeCameraEvents.class);
        ZPUtility.sides().onlyClient(() -> {
            if (ZPUtility.isDataGen()) {
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_debris);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__debris);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_village_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_village_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_village_tier2);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_village_tier2);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_village_tier3);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_village_tier3);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_city_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_city_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_city_tier2);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_city_tier2);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_city_tier3);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_city_tier3);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_garage_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_garage_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_kitchen_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_kitchen_tier1);

                context.registerSyntheticLootCase(ZPLootCasesModule.Case__sample);
                context.registerSyntheticLootTable(ZPLootCasesModule.sample);
                context.registerSyntheticLootTable(ZPLootCasesModule.sampleExtension);
                context.registerSyntheticLootTableExtension("zpm3:sample", List.of(new ZPLootTable.TableExtension("zpm3:sampleExtension", new ZPLootTable.RollRules(0.5f, 1, 2, ZPRandomization.uniform()))));
            }
            context.registerForgeEventHandlerClass(ZPLootTablesGatherDataEvent.class);
        });
        context.registerForgeEventHandlerClass(ZPLootTablesReloadDataEvent.class);

        context.addCommonZp3RegistryClass(ZPLootCases.class);
        context.addCommonZp3RegistryClass(ZPBlockLootCaseEntities.class);
        context.addCommonZp3RegistryClass(ZPLootCaseBlockItems.class);
    }

    @Override
    public void preInitialize(@NotNull IModulePreInitContext context) {

    }

    @Override
    public void postInitialize(@NotNull IModulePostInitContext context) {

    }
    //36000

    public static final ZPSyntheticLootCaseDescription Case__debris = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_debris"),
            "n_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_village_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_village_tier1"),
            "n_tier2",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_village_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_village_tier2"),
            "n_tier3",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_village_tier3 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_village_tier3"),
            "n_tier4",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_city_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_city_tier1"),
            "n_tier5",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_city_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_city_tier2"),
            "n_tier6",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_city_tier3 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_city_tier3"),
            "n_tier7",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_garage_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_garage_tier1"),
            "n_tier8",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_kitchen_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_kitchen_tier1"),
            "n_tier9",
            "tier1",
            -1.0f,
            0
    );

    public static final ZPLootTable loot_kitchen_tier1 = ZPLootTable.builder("loot_kitchen_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_village_tier1"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension("zpm3:loot_city_tier1", new ZPLootTable.RollRules(0.10f, 1, 1, ZPRandomization.uniform()))
            )
            .commonGroup("food", 70, new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.5f)), (g) -> g
                    .addNonBreakable("zpm3:water",                 10, 1)
                    .addNonBreakable("minecraft:bread",             8, 1, 2)
                    .addNonBreakable("minecraft:carrot",            8, 1, 3)
                    .addNonBreakable("minecraft:potato",            8, 1, 3)
                    .addNonBreakable("minecraft:apple",             7, 1, 2)
                    .addNonBreakable("minecraft:beetroot",          6, 1, 3)
                    .addNonBreakable("minecraft:egg",               7, 1, 3)
                    .addNonBreakable("minecraft:chicken",            6, 1)
                    .addNonBreakable("minecraft:rabbit",             5, 1)
                    .addNonBreakable("minecraft:mutton",             7, 1)
                    .addNonBreakable("minecraft:beef",               6, 1)
                    .addNonBreakable("minecraft:porkchop",           6, 1)
                    .addNonBreakable("minecraft:tropical_fish",      4, 1)
                    .addNonBreakable("minecraft:pufferfish",         2, 1)
                    .addNonBreakable("minecraft:kelp",               6, 1, 3)
                    .addNonBreakable("minecraft:dried_kelp",         6, 1, 3)
                    .addNonBreakable("minecraft:sweet_berries",      5, 1, 3)
                    .addNonBreakable("minecraft:glow_berries",       4, 1, 3)
                    .addNonBreakable("minecraft:poisonous_potato",   2, 1)
                    .addNonBreakable("minecraft:cookie",             5, 1, 2)
                    .addNonBreakable("zpm3:chocolate",                5, 1)
                    .addNonBreakable("zpm3:peaches",                  5, 1)
                    .addNonBreakable("zpm3:sprats",                   5, 1)
                    .addNonBreakable("zpm3:bean",                     5, 1)
                    .addNonBreakable("zpm3:jam",                      4, 1)
                    .addNonBreakable("zpm3:soda",                     4, 1)
                    .addNonBreakable("zpm3:mysterious_can",           5, 1)
                    .addNonBreakable("zpm3:minecake",                 2, 1)
                    .addNonBreakable("zpm3:rotten_apple",             2, 1))
            .commonGroup("stew", 20, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("minecraft:rabbit_stew",    4, 1)
                    .addNonBreakable("minecraft:beetroot_soup",  5, 1)
                    .addNonBreakable("minecraft:mushroom_stew",  5, 1)
                    .addNonBreakable("minecraft:suspicious_stew", 3, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 19);
                                        tag.putInt("EffectDuration", 100);
                                    })
                            ))
                    )
                    .addNonBreakable("minecraft:suspicious_stew", 2, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 17);
                                        tag.putInt("EffectDuration", 300);
                                    })
                            ))
                    )
                    .addNonBreakable("minecraft:suspicious_stew", 2, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 18);
                                        tag.putInt("EffectDuration", 600);
                                    })
                            ))
                    )
                    .addNonBreakable("minecraft:suspicious_stew", 1, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 8);
                                        tag.putInt("EffectDuration", 100);
                                    })
                            ))
                    ))
            .commonGroup("kitchen_tools", 35, (g) -> g
                    .addBreakable("minecraft:wooden_axe", 8, 0.30f, 0.70f)
                    .addBreakable("minecraft:stone_axe", 7, 0.20f, 0.50f)
                    .addBreakable("minecraft:golden_axe", 4, 0.20f, 0.60f)
                    .addBreakable("minecraft:iron_axe", 3, 0.40f, 0.80f)
                    .addBreakable("zpm3:cleaver", 6, 0.30f, 0.70f)
                    .addBreakable("minecraft:shears", 5, 0.20f, 0.60f)
                    .addBreakable("minecraft:flint_and_steel", 3, 0.20f, 0.50f)
                    .addBreakable("zpm3:matches", 4, 0.05f, 0.20f))
            .commonGroup("kitchen_supplies", 30, new ZPLootTable.RollRules(1.0f, 2, 5, ZPRandomization.power(1.5f)), (g) -> g
                    .addNonBreakable("minecraft:bowl",          10, 1, 4)
                    .addNonBreakable("minecraft:glass_bottle",   7, 1, 3)
                    .addNonBreakable("minecraft:glass",          5, 1, 4)
                    .addNonBreakable("minecraft:wheat",          5, 1, 4)
                    .addNonBreakable("minecraft:sugar",          5, 1, 3)
                    .addNonBreakable("minecraft:flint",           4, 1, 2)
                    .addNonBreakable("minecraft:coal",            5, 1, 3)
                    .addNonBreakable("minecraft:charcoal",        4, 1, 3)
                    .addNonBreakable("zpm3:plate",                 8, 8, 16))
            .commonGroup("kitchen_blocks", 12, (g) -> g
                    .addNonBreakable("minecraft:campfire",  3, 1)
                    .addNonBreakable("minecraft:barrel",    2, 1)
                    .addNonBreakable("minecraft:chest",     1, 1)
                    .addNonBreakable("minecraft:furnace",   2, 1)
                    .addNonBreakable("minecraft:smoker",    1, 1))
            .bonusGroup("kitchen_bonus", 0.005f, (g) -> g
                    .addNonBreakable("minecraft:golden_carrot",  2, 1)
                    .addNonBreakable("minecraft:gold_ingot",     1, 1)
                    .addNonBreakable("minecraft:emerald",        1, 1)
                    .addNonBreakable("minecraft:iron_ingot",     2, 1)
                    .addNonBreakable("minecraft:cake",            2, 1)
                    .addNonBreakable("minecraft:honey_bottle",   2, 1))
            .build(new ZPLootTable.RollRules(0.85f, 1, 3, ZPRandomization.power(2.0f)));

    public static final ZPLootTable loot_garage_tier1 = ZPLootTable.builder("loot_garage_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_city_tier1"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension("zpm3:loot_village_tier1",
                            new ZPLootTable.RollRules(0.05f, 1, 1, ZPRandomization.uniform())),
                    new ZPLootTable.TableExtension("zpm3:loot_village_tier2",
                            new ZPLootTable.RollRules(0.05f, 1, 1, ZPRandomization.uniform())),
                    new ZPLootTable.TableExtension("zpm3:loot_village_tier3",
                            new ZPLootTable.RollRules(0.05f, 1, 1, ZPRandomization.uniform())),
                    new ZPLootTable.TableExtension("zpm3:loot_city_tier2",
                            new ZPLootTable.RollRules(0.03f, 1, 1, ZPRandomization.uniform()))
            )
            .commonGroup("garage_tools", 40, (g) -> g
                    .addBreakable("zpm3:sledgehammer",      4, 0.15f, 0.50f)
                    .addBreakable("zpm3:iron_club",          5, 0.30f, 0.70f)
                    .addBreakable("zpm3:pipe",               7, 0.40f, 0.80f)
                    .addBreakable("zpm3:crowbar",            5, 0.20f, 0.60f)
                    .addBreakable("zpm3:broom",              5, 0.30f, 0.70f)
                    .addBreakable("minecraft:shears",        4, 0.30f, 0.70f)
                    .addBreakable("minecraft:fishing_rod",   3, 0.20f, 0.60f))
            .commonGroup("survival", 15, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(3.25f)), (g) -> g
                    .addBreakable("zpm3:bandage",             3, 0.0f, 0.5f)
                    .addNonBreakable("zpm3:vodka_medicine",   1, 1)
                    .addNonBreakable("minecraft:bucket",      5, 1)
                    .addNonBreakable("minecraft:campfire",   2, 1)
                    .addNonBreakable("minecraft:lead",        4, 1)
                    .addNonBreakable("minecraft:firework_rocket", 3, 1,
                            nbt -> nbt.add("Fireworks", IZPLootNbtContainer.compound(tag -> {
                                tag.putByte("Flight", (byte) 3);
                                tag.put("Explosions", IZPLootNbtContainer.list(
                                        IZPLootNbtContainer.compound(explosion -> {
                                            explosion.putByte("Type", (byte) 0);
                                            explosion.putByte("Flicker", (byte) 0);
                                            explosion.putByte("Trail", (byte) 0);
                                            explosion.putIntArray("Colors", new int[]{0xff0000});
                                        })
                                ));
                            }))
                    )
                    .addBreakable("zpm3:matches",             4, 0.05f, 0.20f))
            .commonGroup("garage_utility", 20, new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(1.25f)), (g) -> g
                    .addNonBreakable("minecraft:leather_horse_armor", 3, 1)
                    .addNonBreakable("minecraft:iron_horse_armor",     1, 1)
                    .addNonBreakable("minecraft:writable_book",  3, 1)
                    .addNonBreakable("minecraft:map",             3, 1)
                    .addNonBreakable("minecraft:clock",           2, 1)
                    .addNonBreakable("minecraft:compass",         2, 1)
                    .addNonBreakable("minecraft:name_tag",        2, 1)
                    .addNonBreakable("minecraft:brush",            2, 1))
            .commonGroup("technical", 12, new ZPLootTable.RollRules(1.0f, 3, 6, ZPRandomization.power(1.5f)), (g) -> g
                    .addNonBreakable("minecraft:redstone",             5, 1, 5, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:redstone_torch",       4, 1, 3)
                    .addNonBreakable("minecraft:repeater",             2, 1)
                    .addNonBreakable("minecraft:tripwire_hook",        3, 1, 3)
                    .addNonBreakable("minecraft:lever",                3, 1, 2)
                    .addNonBreakable("minecraft:stone_button",         3, 1, 3)
                    .addNonBreakable("minecraft:stone_pressure_plate", 2, 1, 2)
                    .addNonBreakable("minecraft:piston",               2, 1)
                    .addNonBreakable("minecraft:dropper",              2, 1)
                    .addNonBreakable("minecraft:dispenser",            2, 1)
                    .addNonBreakable("minecraft:hopper",               1, 1))
            .commonGroup("blocks", 18, new ZPLootTable.RollRules(1.0f, 3, 6, ZPRandomization.power(1.5f)), (g) -> g
                    .addNonBreakable("minecraft:bricks",              4, 1, 3)
                    .addNonBreakable("minecraft:stone_bricks",       4, 1, 3)
                    .addNonBreakable("minecraft:cobblestone",         5, 1, 4)
                    .addNonBreakable("minecraft:stone",               5, 1, 4)
                    .addNonBreakable("minecraft:oak_planks",          4, 1, 4)
                    .addNonBreakable("minecraft:oak_log",             3, 1, 3)
                    .addNonBreakable("minecraft:glass",               3, 1, 4)
                    .addNonBreakable("minecraft:iron_bars",           3, 1, 5, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:chain",               4, 1, 4)
                    .addNonBreakable("zpm3:chain_link",               3, 1, 6)
                    .addNonBreakable("zpm3:scrap_block",              2, 1)
                    .addNonBreakable("zpm3:scrap_slab",               3, 1, 2)
                    .addNonBreakable("zpm3:scrap_stairs",             2, 1, 2)
                    .addNonBreakable("zpm3:scrap_bars",               3, 1, 4)
                    .addNonBreakable("minecraft:torch",               8, 1, 6)
                    .addNonBreakable("minecraft:redstone_torch",      4, 1, 3)
                    .addNonBreakable("minecraft:lantern",             2, 1, 2)
                    .addNonBreakable("minecraft:barrel",              1, 1)
                    .addNonBreakable("minecraft:campfire",            1, 1))
            .commonGroup("materials", 15, (g) -> g
                    .addNonBreakable("minecraft:iron_ingot",       3, 1, 2, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:iron_nugget",      5, 1, 5, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:copper_ingot",     5, 1, 3, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:coal",              4, 1, 4, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:string",            3, 1, 4)
                    .addNonBreakable("zpm3:scrap_material",         5, 1, 3, ZPRandomization.power(2.0f)))
            .commonGroup("transport", 6, (g) -> g
                    .addNonBreakable("minecraft:rail",      5, 2, 8)
                    .addNonBreakable("minecraft:minecart",  2, 1))
            .commonGroup("rare_technical", 4, (g) -> g
                    .addNonBreakable("minecraft:chest",          1, 1)
                    .addNonBreakable("minecraft:barrel",         2, 1)
                    .addNonBreakable("minecraft:anvil",          1, 1)
                    .addNonBreakable("minecraft:damaged_anvil",  2, 1)
                    .addNonBreakable("minecraft:stonecutter",    1, 1))
            .commonGroup("ammunition", 8, (g) -> g
                    .addNonBreakable("zpm3:_shotgun",          2, 1, 6, ZPRandomization.power(2.0f))
                    .addNonBreakable("zpm3:_handmade_pistol",  3, 1, 6, ZPRandomization.power(2.0f)))
            .bonusGroup("garage_bonus", 0.01f, (g) -> g
                    .addNonBreakable("minecraft:iron_ingot",       3, 1, 3)
                    .addNonBreakable("minecraft:gold_ingot",       2, 1)
                    .addNonBreakable("minecraft:emerald",          1, 1)
                    .addNonBreakable("minecraft:hopper",            1, 1)
                    .addNonBreakable("minecraft:minecart",          1, 1)
                    .addNonBreakable("zpm3:scrap_block",             2, 1)
                    .addNonBreakable("zpm3:scrap_slab",              2, 1, 2)
                    .addNonBreakable("zpm3:scrap_stairs",            2, 1, 2)
                    .addNonBreakable("zpm3:scrap_bars",              3, 1, 4))
            .build(new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)));

    // =============================================

    public static final ZPLootTable loot_city_tier3 = ZPLootTable.builder("loot_city_tier3")
            .extendBy(
                    new ZPLootTable.TableExtension("zpm3:loot_village_tier3",
                            new ZPLootTable.RollRules(0.35f, 1, 1, ZPRandomization.uniform())),
                    new ZPLootTable.TableExtension("zpm3:loot_city_tier1",
                            new ZPLootTable.RollRules(0.50f, 1, 1, ZPRandomization.uniform())),
                    new ZPLootTable.TableExtension("zpm3:loot_city_tier2",
                            new ZPLootTable.RollRules(0.75f, 1, 1, ZPRandomization.uniform())),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("melee", 30, (g) -> g
                    .addBreakable("minecraft:iron_sword", 8, 0.20f, 0.80f)
                    .addBreakable("minecraft:iron_axe",   7, 0.20f, 0.80f)
                    .addBreakable("zpm3:iron_club",        7, 0.30f, 0.70f)
                    .addBreakable("zpm3:crowbar",          4, 0.20f, 0.60f)
                    .addBreakable("zpm3:sledgehammer",     2, 0.15f, 0.50f)
                    .addBreakable("zpm3:pipe",              5, 0.40f, 0.80f)
                    .addBreakable("zpm3:golf_club",         4, 0.40f, 0.80f))
            .commonGroup("iron_tools", 25, (g) -> g
                    .addBreakable("minecraft:iron_axe",        8, 0.20f, 0.80f)
                    .addBreakable("minecraft:iron_pickaxe",    8, 0.20f, 0.80f)
                    .addBreakable("minecraft:iron_shovel",     5, 0.20f, 0.80f)
                    .addBreakable("minecraft:iron_hoe",        4, 0.20f, 0.80f))
            .commonGroup("iron_armor", 22, (g) -> g
                    .addBreakable("minecraft:iron_helmet",     1, 0.20f, 0.80f)
                    .addBreakable("minecraft:iron_chestplate", 1, 0.20f, 0.80f)
                    .addBreakable("minecraft:iron_leggings",   1, 0.20f, 0.80f)
                    .addBreakable("minecraft:iron_boots",      1, 0.20f, 0.80f))
            .commonGroup("food", 35, (g) -> g
                    .addNonBreakable("minecraft:golden_carrot",     2, 1)
                    .addNonBreakable("minecraft:cooked_chicken",    8, 1, 2)
                    .addNonBreakable("minecraft:cooked_rabbit",     7, 1, 2)
                    .addNonBreakable("minecraft:cooked_mutton",     6, 1, 3)
                    .addNonBreakable("minecraft:carrot",            8, 1, 3)
                    .addNonBreakable("minecraft:cookie",            7, 1, 3)
                    .addNonBreakable("zpm3:fried_egg",              7, 1, 3)
                    .addNonBreakable("zpm3:peaches",                5, 1, 2)
                    .addNonBreakable("zpm3:sprats",                 5, 1, 2)
                    .addNonBreakable("zpm3:mysterious_can",         5, 1, 2)
                    .addNonBreakable("zpm3:bean",                   6, 1, 3)
                    .addNonBreakable("zpm3:jam",                    4, 1, 2)
                    .addNonBreakable("zpm3:soda",                   4, 1, 2)
                    .addNonBreakable("zpm3:chocolate",              5, 1, 2)
                    .addNonBreakable("zpm3:minecake",               2, 1)
                    .addNonBreakable("zpm3:rotten_apple",           2, 1, 4))
            .commonGroup("medical", 20, (g) -> g
                    .addBreakable("zpm3:bandage",              7, 0.25f, 1.00f)
                    .addNonBreakable("zpm3:anti_poison_pill",  3, 1)
                    .addNonBreakable("zpm3:anti_hunger_pill",  1, 1)
                    .addNonBreakable("zpm3:anti_headache_pill", 6, 1)
                    .addNonBreakable("zpm3:splint",            4, 1)
                    .addNonBreakable("zpm3:aid_kit",           1, 1)
                    .addNonBreakable("zpm3:vodka_medicine",    2, 1)
                    .addNonBreakable("zpm3:whiskey_medicine",  2, 1))
            .commonGroup("materials", 25, (g) -> g
                    .addNonBreakable("minecraft:iron_ingot",    6, 1, 3, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:gold_ingot",    2, 1, 2, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:iron_nugget",  7, 1, 6, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:gold_nugget",  4, 1, 4, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:copper_ingot",  6, 1, 4, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:coal",          5, 1, 4, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:string",        4, 1, 4)
                    .addNonBreakable("zpm3:scrap_material",    5, 1, 4, ZPRandomization.power(2.0f)))
            .commonGroup("firearms_ammo", 22, new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(1.25f)), (g) -> g
                    .addNonBreakable("zpm3:_usp",             4, 12, 24, ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_m1911",           4, 14, 28, ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_colt",            2, 9,  18,  ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_handmade_pistol", 2, 9,  18,  ZPRandomization.power(1.25f))
                    .addNonBreakable("zpm3:_makarov",         5, 14, 32, ZPRandomization.power(1.5f)))
            .commonGroup("tools", 15, (g) -> g
                    .addBreakable("zpm3:wrench",        5, 0.20f, 0.50f)
                    .addBreakable("zpm3:metal_cutters", 4, 0.20f, 0.50f)
                    .addBreakable("minecraft:flint_and_steel", 2, 0.20f, 0.60f)
                    .addBreakable("minecraft:fishing_rod", 2, 0.20f, 0.60f))
            .commonGroup("survival", 18, (g) -> g
                    .addNonBreakable("zpm3:cement",              2, 1)
                    .addNonBreakable("zpm3:plate",              5, 1, 6)
                    .addNonBreakable("zpm3:water",              8, 1)
                    .addNonBreakable("minecraft:chain",         5, 1, 4)
                    .addNonBreakable("minecraft:lead",          3, 1)
                    .addNonBreakable("minecraft:torch",         6, 1, 6)
                    .addNonBreakable("minecraft:redstone_torch", 3, 1, 4))
            .commonGroup("blocks", 15, new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.25f)), (g) -> g
                    .addNonBreakable("minecraft:iron_bars",      3, 1, 4)
                    .addNonBreakable("minecraft:lantern",        2, 1, 2)
                    .addNonBreakable("minecraft:barrel",         1, 1)
                    .addNonBreakable("minecraft:chest",          1, 1)
                    .addNonBreakable("minecraft:crafting_table", 1, 1)
                    .addNonBreakable("minecraft:furnace",        1, 1)
                    .addNonBreakable("minecraft:stonecutter",    1, 1)
                    .addNonBreakable("minecraft:bookshelf",       2, 1, 2))
            .commonGroup("lighting", 4, (g) -> g
                    .addNonBreakable("zpm3:torch2",      4, 1)
                    .addNonBreakable("minecraft:torch",   5, 1, 6)
                    .addNonBreakable("minecraft:redstone_torch", 2, 1, 3)
                    .addNonBreakable("zpm3:block_lamp",  1, 1)
                    .addNonBreakable("zpm3:wall_lamp",   1, 1))
            .bonusGroup("city_tier3_bonus", new ZPLootTable.RollRules(0.02f, 1, 1, ZPRandomization.power(2.0f)), (g) -> g
                    .addNonBreakable("minecraft:emerald",       3, 1)
                    .addNonBreakable("minecraft:gold_ingot",    2, 1)
                    .addNonBreakable("minecraft:diamond",       1, 1)
                    .addNonBreakable("minecraft:lapis_lazuli",  3, 1, 4)
                    .addNonBreakable("minecraft:map",           3, 1)
                    .addNonBreakable("minecraft:compass",       2, 1)
                    .addNonBreakable("minecraft:clock",         1, 1)
                    .addNonBreakable("minecraft:moss_block",    3, 1, 2)
                    .addNonBreakable("minecraft:mossy_cobblestone", 3, 1, 3)
                    .addNonBreakable("minecraft:flower_pot",    2, 1)
                    .addNonBreakable("minecraft:azalea",        2, 1)
                    .addNonBreakable("minecraft:flowering_azalea", 2, 1))
            .build(new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(3.0f)));

    // =====================================================

    public static final ZPLootTable loot_city_tier2 = ZPLootTable.builder("loot_city_tier2")
            .extendBy(
                    new ZPLootTable.TableExtension("zpm3:loot_city_tier1",
                            new ZPLootTable.RollRules(0.75f, 1, 1, ZPRandomization.uniform())),
                    new ZPLootTable.TableExtension("zpm3:loot_village_tier3",
                            new ZPLootTable.RollRules(0.5f, 1, 1, ZPRandomization.uniform())),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("armor_iron", 10, (g) -> g
                    .addBreakable("minecraft:iron_helmet",        1, 0.05f, 0.10f)
                    .addBreakable("minecraft:iron_chestplate",    1, 0.05f, 0.10f)
                    .addBreakable("minecraft:iron_leggings",      1, 0.05f, 0.10f)
                    .addBreakable("minecraft:iron_boots",         1, 0.05f, 0.10f))
            .commonGroup("armor_chainmail", 15, (g) -> g
                    .addBreakable("minecraft:chainmail_helmet",     1, 0.20f, 0.40f)
                    .addBreakable("minecraft:chainmail_chestplate", 1, 0.20f, 0.40f)
                    .addBreakable("minecraft:chainmail_leggings",   1, 0.20f, 0.40f)
                    .addBreakable("minecraft:chainmail_boots",      1, 0.20f, 0.40f))
            .commonGroup("armor_leather", 20, (g) -> g
                    .addBreakable("minecraft:leather_helmet",        1, 0.70f, 1.00f)
                    .addBreakable("minecraft:leather_chestplate",    1, 0.70f, 1.00f)
                    .addBreakable("minecraft:leather_leggings",      1, 0.70f, 1.00f)
                    .addBreakable("minecraft:leather_boots",         1, 0.70f, 1.00f))
            .commonGroup("armor_golden", 8, (g) -> g
                    .addBreakable("minecraft:golden_helmet",        1, 0.30f, 0.70f)
                    .addBreakable("minecraft:golden_chestplate",    1, 0.30f, 0.70f)
                    .addBreakable("minecraft:golden_leggings",      1, 0.30f, 0.70f)
                    .addBreakable("minecraft:golden_boots",         1, 0.30f, 0.70f))
            .commonGroup("melee", 24, (g) -> g
                    .addBreakable("minecraft:stone_sword", 10, 0.30f, 0.70f)
                    .addBreakable("minecraft:iron_sword",   3, 0.05f, 0.35f)
                    .addBreakable("zpm3:bat",              10, 0.40f, 0.80f)
                    .addBreakable("zpm3:pipe",              8, 0.40f, 0.80f)
                    .addBreakable("zpm3:golf_club",         6, 0.40f, 0.80f)
                    .addBreakable("zpm3:iron_club",         4, 0.30f, 0.70f)
                    .addBreakable("zpm3:crowbar",           4, 0.30f, 0.70f)
                    .addBreakable("zpm3:cleaver",           4, 0.30f, 0.70f))
            .commonGroup("firearms_makarov", 8, (g) -> g
                    .addBreakable("zpm3:makarov", 1, 0.60f, 0.95f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 6,
                                    ZPRandomization.power(2.0f))))
            .commonGroup("firearms_usp", 4, (g) -> g
                    .addBreakable("zpm3:usp", 1, 0.05f, 0.10f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 8,
                                    ZPRandomization.power(2.0f))))
            .commonGroup("firearms_m1911", 5, (g) -> g
                    .addBreakable("zpm3:m1911", 1, 0.40f, 0.70f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 7,
                                    ZPRandomization.power(2.0f))))
            .commonGroup("firearms_colt", 2, (g) -> g
                    .addBreakable("zpm3:colt", 1, 0.10f, 0.30f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 3,
                                    ZPRandomization.power(2.0f))))
            .commonGroup("firearms_handmade", 3, (g) -> g
                    .addBreakable("zpm3:handmade_pistol", 1, 0.05f, 0.20f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 1,
                                    ZPRandomization.power(2.0f))))
            .commonGroup("firearms_ammo", 25, new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.25f)), (g) -> g
                    .addNonBreakable("zpm3:_usp",             4, 8, 16, ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_m1911",           4, 8, 16, ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_colt",            2, 6, 12,  ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_handmade_pistol", 2, 4, 9,  ZPRandomization.power(1.25f))
                    .addNonBreakable("zpm3:_makarov",         5, 8, 26, ZPRandomization.power(1.5f)))
            .commonGroup("medical", 10, (g) -> g
                    .addBreakable("zpm3:bandage", 8, 0.25f, 1.00f)
                    .addNonBreakable("zpm3:splint", 4, 1)
                    .addNonBreakable("zpm3:anti_headache_pill", 2, 1))
            .commonGroup("food", 45, (g) -> g
                    .addNonBreakable("minecraft:cooked_chicken", 8, 1)
                    .addNonBreakable("minecraft:cooked_rabbit",  6, 1)
                    .addNonBreakable("minecraft:bread",          8, 1, 3)
                    .addNonBreakable("minecraft:tropical_fish",  5, 1, 2)
                    .addNonBreakable("minecraft:salmon",         6, 1, 2)
                    .addNonBreakable("minecraft:cod",            6, 1, 2)
                    .addNonBreakable("minecraft:melon_slice",    6, 1, 3)
                    .addNonBreakable("minecraft:carrot",         6, 1, 3)
                    .addNonBreakable("minecraft:apple",          6, 1, 2)
                    .addNonBreakable("minecraft:sweet_berries",  6, 1, 3)
                    .addNonBreakable("minecraft:glow_berries",   5, 1, 3)
                    .addNonBreakable("minecraft:dried_kelp",     5, 1, 3)
                    .addNonBreakable("zpm3:peaches",             5, 1, 2)
                    .addNonBreakable("zpm3:mysterious_can",      5, 1, 2)
                    .addNonBreakable("zpm3:sprats",              4, 1, 2)
                    .addNonBreakable("zpm3:chocolate",           5, 1, 2)
                    .addNonBreakable("zpm3:minecake",            2, 1)
                    .addNonBreakable("zpm3:jam",                 4, 1, 2)
                    .addNonBreakable("zpm3:bean",                5, 1, 3)
                    .addNonBreakable("zpm3:soda",                4, 1, 2)
                    .addNonBreakable("zpm3:rotten_apple",        2, 1, 4))
            .commonGroup("blocks", 15, new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(1.25f)), (g) -> g
                    .addNonBreakable("minecraft:bricks",             5, 1, 3)
                    .addNonBreakable("minecraft:stone_bricks",      5, 1, 3)
                    .addNonBreakable("minecraft:glass",             5, 1, 4)
                    .addNonBreakable("minecraft:oak_planks",        5, 1, 4)
                    .addNonBreakable("minecraft:oak_log",           4, 1, 3)
                    .addNonBreakable("minecraft:cobblestone",       5, 1, 4)
                    .addNonBreakable("minecraft:stone",             5, 1, 4)
                    .addNonBreakable("minecraft:mossy_cobblestone", 3, 1, 3)
                    .addNonBreakable("minecraft:terracotta",        3, 1, 3)
                    .addNonBreakable("zpm3:chain_link",             2, 1, 4)
                    .addNonBreakable("minecraft:crafting_table",     1, 1)
                    .addNonBreakable("minecraft:furnace",            1, 1)
                    .addNonBreakable("minecraft:barrel",             1, 1)
                    .addNonBreakable("minecraft:chest",              1, 1)
                    .addNonBreakable("minecraft:bookshelf",          1, 1)
                    .addNonBreakable("minecraft:campfire",           1, 1))
            .commonGroup("materials", 25, (g) -> g
                    .addNonBreakable("minecraft:iron_ingot",       4, 1, 2, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:gold_ingot",        2, 1)
                    .addNonBreakable("minecraft:iron_nugget",      6, 1, 5, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:gold_nugget",      4, 1, 3, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:leather",          5, 1, 3)
                    .addNonBreakable("minecraft:rabbit_hide",      4, 1, 3)
                    .addNonBreakable("minecraft:copper_ingot",     6, 1, 3)
                    .addNonBreakable("zpm3:scrap_material",        5, 1, 3, ZPRandomization.power(2.0f))
                    .addNonBreakable("zpm3:chisel_material",       4, 1)
                    .addNonBreakable("zpm3:shelves_material",      4, 1))
            .commonGroup("tools", 12, (g) -> g
                    .addBreakable("zpm3:wrench",        5, 0.20f, 0.50f)
                    .addBreakable("zpm3:metal_cutters", 4, 0.20f, 0.50f)
                    .addBreakable("zpm3:matches",       3, 0.05f, 0.20f))
            .commonGroup("survival", 20, (g) -> g
                    .addNonBreakable("minecraft:coal",         4, 1, 4)
                    .addNonBreakable("zpm3:plate", 5, 1, 6)
                    .addNonBreakable("minecraft:torch", 8, 1, 6)
                    .addNonBreakable("zpm3:torch2", 5, 1)
                    .addNonBreakable("zpm3:torch3", 4, 1, 6)
                    .addNonBreakable("zpm3:torch4", 3, 1, 6)
                    .addNonBreakable("zpm3:lantern3", 4, 1, 2)
                    .addNonBreakable("zpm3:lantern4", 3, 1, 2))
            .commonGroup("lighting", 5, (g) -> g
                    .addNonBreakable("zpm3:wall_lamp", 1, 1)
                    .addNonBreakable("zpm3:lantern3", 8, 1, 2)
                    .addNonBreakable("zpm3:lantern4", 6, 1, 2)
                    .addNonBreakable("zpm3:torch2", 8, 1, 6)
                    .addNonBreakable("zpm3:torch3", 6, 1, 6)
                    .addNonBreakable("zpm3:torch4", 4, 1, 6))
            .bonusGroup("city_bonus", 0.008f, (g) -> g
                    .addNonBreakable("minecraft:iron_nugget",    3, 1, 6)
                    .addNonBreakable("minecraft:iron_ingot",     2, 1, 2)
                    .addNonBreakable("minecraft:map",             3, 1)
                    .addNonBreakable("minecraft:compass",         2, 1)
                    .addNonBreakable("minecraft:clock",           1, 1)
                    .addNonBreakable("minecraft:gold_nugget",   3, 1, 3)
                    .addNonBreakable("minecraft:pottery_sherd", 4, 1))
            .bonusGroup("city_bonus2", 0.003f, (g) -> g
                    .addNonBreakable("zpm3:cement",              1, 1)
                    .addNonBreakable("minecraft:gold_ingot",    1, 1)
                    .addNonBreakable("minecraft:emerald",       2, 1)
                    .addNonBreakable("minecraft:diamond",       1, 1))
            .build(new ZPLootTable.RollRules(0.80f, 1, 2, ZPRandomization.power(2.5f)));

    // =======================================================

    public static final ZPLootTable loot_city_tier1 = ZPLootTable.builder("loot_city_tier1")
            .extendBy(
                    new ZPLootTable.TableExtension("zpm3:loot_village_tier2",
                            new ZPLootTable.RollRules(0.10f, 1, 1, ZPRandomization.uniform())),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("armor_leather", 30, (g) -> g
                    .addBreakable("minecraft:leather_helmet",     1, 0.15f, 0.30f)
                    .addBreakable("minecraft:leather_chestplate", 1, 0.15f, 0.30f)
                    .addBreakable("minecraft:leather_leggings",   1, 0.15f, 0.30f)
                    .addBreakable("minecraft:leather_boots",      1, 0.15f, 0.30f))
            .commonGroup("armor_chainmail", 10, (g) -> g
                    .addBreakable("minecraft:chainmail_helmet",     1, 0.05f, 0.10f)
                    .addBreakable("minecraft:chainmail_chestplate", 1, 0.05f, 0.10f)
                    .addBreakable("minecraft:chainmail_leggings",   1, 0.05f, 0.10f)
                    .addBreakable("minecraft:chainmail_boots",      1, 0.05f, 0.10f))
            .commonGroup("firearms_makarov",3,
                    (g) -> g
                            .addBreakable("zpm3:makarov", 1, 0.25f, 0.50f,
                                    nbt -> nbt.addRandom(
                                            "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                            0, 4,
                                            ZPRandomization.power(2.5f))))
            .commonGroup("firearms_handmade_pistol", 3,
                    (g) -> g
                            .addBreakable("zpm3:handmade_pistol", 1, 0.15f, 0.40f,
                                    nbt -> nbt.addRandom(
                                            "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                            0, 1,
                                            ZPRandomization.power(2.5f))))
            .commonGroup("firearms_m1911", 3,
                    (g) -> g
                            .addBreakable("zpm3:m1911", 1, 0.20f, 0.50f,
                                    nbt -> nbt.addRandom(
                                            "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                            0, 4,
                                            ZPRandomization.power(2.5f))))
            .commonGroup("firearms_colt", 1,
                    (g) -> g
                            .addBreakable("zpm3:colt", 1, 0.05f, 0.15f,
                                    nbt -> nbt.addRandom(
                                            "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                            0, 2,
                                            ZPRandomization.power(2.5f))))
            .commonGroup("firearms_usp", 1,
                    (g) -> g
                            .addBreakable("zpm3:usp", 1, 0.01f, 0.05f,
                                    nbt -> nbt.addRandom(
                                            "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                            0, 4,
                                            ZPRandomization.power(2.5f))))
            .commonGroup("firearms_ammo", 12, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.5f)), (g) -> g
                    .addNonBreakable("zpm3:_makarov",        5, 1, 8, ZPRandomization.power(2.5f))
                    .addNonBreakable("zpm3:_handmade_pistol", 3, 1, 6, ZPRandomization.power(2.5f))
                    .addNonBreakable("zpm3:_colt",           2, 1, 4, ZPRandomization.power(2.5f))
                    .addNonBreakable("zpm3:_m1911",          2, 1, 8, ZPRandomization.power(2.5f))
                    .addNonBreakable("zpm3:_usp",            1, 1, 6, ZPRandomization.power(2.5f)))
            .commonGroup("blocks", 10, new ZPLootTable.RollRules(1.0f, 2, 6, ZPRandomization.power(1.25f)), (g) -> g
                    .addNonBreakable("minecraft:oak_log",             5, 1, 3)
                    .addNonBreakable("minecraft:oak_planks",          6, 1, 4)
                    .addNonBreakable("minecraft:oak_stairs",          4, 1, 3)
                    .addNonBreakable("minecraft:oak_slab",            4, 1, 3)
                    .addNonBreakable("minecraft:oak_fence",            4, 1, 3)
                    .addNonBreakable("minecraft:oak_fence_gate",      3, 1, 2)
                    .addNonBreakable("minecraft:oak_door",            3, 1)
                    .addNonBreakable("minecraft:oak_trapdoor",        3, 1, 2)
                    .addNonBreakable("minecraft:oak_pressure_plate",   3, 1, 2)
                    .addNonBreakable("minecraft:cobblestone",          5, 1, 4)
                    .addNonBreakable("minecraft:stone",                5, 1, 4)
                    .addNonBreakable("minecraft:stone_slab",           3, 1, 3)
                    .addNonBreakable("minecraft:stone_stairs",         3, 1, 3)
                    .addNonBreakable("minecraft:cobblestone_wall",     3, 1, 3)
                    .addNonBreakable("minecraft:glass",                4, 1, 4)
                    .addNonBreakable("minecraft:glass_pane",           4, 1, 6)
                    .addNonBreakable("minecraft:bricks",               3, 1, 3)
                    .addNonBreakable("minecraft:brick_slab",           2, 1, 3)
                    .addNonBreakable("minecraft:brick_stairs",         2, 1, 3)
                    .addNonBreakable("minecraft:brick_wall",           2, 1, 3))
            .commonGroup("melee", 25, (g) -> g
                    .addBreakable("zpm3:bat",                10, 0.15f, 0.45f)
                    .addBreakable("zpm3:broom",               8, 0.65f, 0.85f)
                    .addBreakable("zpm3:golf_club",           5, 0.20f, 0.50f)
                    .addBreakable("minecraft:shears",      5, 0.30f, 0.50f)
                    .addBreakable("minecraft:wooden_sword",   8, 0.40f, 0.90f)
                    .addBreakable("minecraft:wooden_axe",     8, 0.40f, 0.90f)
                    .addBreakable("minecraft:wooden_pickaxe", 8, 0.40f, 0.90f)
                    .addBreakable("minecraft:wooden_shovel",  8, 0.40f, 0.90f)
                    .addBreakable("minecraft:wooden_hoe",     8, 0.40f, 0.90f)
                    .addBreakable("minecraft:stone_sword",    5, 0.10f, 0.30f)
                    .addBreakable("minecraft:stone_axe",      5, 0.10f, 0.30f)
                    .addBreakable("minecraft:stone_pickaxe",  5, 0.10f, 0.30f)
                    .addBreakable("minecraft:stone_shovel",   5, 0.10f, 0.30f)
                    .addBreakable("minecraft:stone_hoe",      5, 0.10f, 0.30f))
            .commonGroup("ranged", 6, (g) -> g
                    .addBreakable("minecraft:bow", 1, 0.05f, 0.1f)
                    .addNonBreakable("minecraft:arrow", 4, 1, 4))
            .commonGroup("food", 45, (g) -> g
                    .addNonBreakable("zpm3:mysterious_can",  8, 1)
                    .addNonBreakable("zpm3:sprats",          6, 1)
                    .addNonBreakable("zpm3:chocolate",       6, 1)
                    .addNonBreakable("zpm3:rotten_apple",    3, 1)
                    .addNonBreakable("zpm3:jam",             4, 1)
                    .addNonBreakable("zpm3:soda",            5, 1)
                    .addNonBreakable("minecraft:sweet_berries", 6, 1, 2)
                    .addNonBreakable("minecraft:dried_kelp",    6, 1)
                    .addNonBreakable("minecraft:potato",        8, 1)
                    .addNonBreakable("minecraft:apple",         7, 1)
                    .addNonBreakable("minecraft:melon_slice",   7, 1)
                    .addNonBreakable("minecraft:glow_berries",  5, 1, 2)
                    .addNonBreakable("minecraft:suspicious_stew", 2, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 19);
                                        tag.putInt("EffectDuration", 100);
                                    })
                            )))
                    .addNonBreakable("minecraft:suspicious_stew", 1, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 17);
                                        tag.putInt("EffectDuration", 300);
                                    })
                            ))))
            .commonGroup("survival", 25, (g) -> g
                    .addNonBreakable("zpm3:plate",             6, 1, 5)
                    .addNonBreakable("zpm3:shelves_material", 5, 1)
                    .addNonBreakable("zpm3:chisel_material",  5, 1)
                    .addNonBreakable("minecraft:copper_ingot", 4, 1, 2)
                    .addNonBreakable("minecraft:iron_nugget",  5, 1, 3)
                    .addNonBreakable("minecraft:brick",        4, 1, 3)
                    .addNonBreakable("minecraft:leather",        4, 1, 3)
                    .addNonBreakable("minecraft:glass_bottle", 5, 1, 2))
            .commonGroup("medical", 10, (g) -> g
                    .addBreakable("zpm3:bandage", 8, 0.25f, 0.75f)
                    .addNonBreakable("zpm3:anti_headache_pill", 2, 1))
            .bonusGroup("city_bonus", 0.01f, (g) -> g
                    .addNonBreakable("minecraft:iron_nugget",  6, 1, 4)
                    .addNonBreakable("minecraft:glass_bottle", 5, 1, 2)
                    .addNonBreakable("minecraft:paper",         5, 1, 3)
                    .addNonBreakable("minecraft:book",          3, 1)
                    .addNonBreakable("minecraft:gold_nugget",   2, 1, 2))
            .bonusGroup("city_bonus2", 0.003f, (g) -> g
                    .addNonBreakable("minecraft:emerald",      2, 1)
                    .addNonBreakable("minecraft:iron_ingot",   2, 1)
                    .addNonBreakable("minecraft:coal",         4, 1, 3))
            .bonusGroup("city_bonus_blocks", new ZPLootTable.RollRules(0.1f, 1, 4, ZPRandomization.power(3.0f)), (g) -> g
                    .addNonBreakable("minecraft:moss_block",             3, 1, 2)
                    .addNonBreakable("minecraft:mossy_cobblestone",     3, 1, 3)
                    .addNonBreakable("minecraft:azalea",                 2, 1)
                    .addNonBreakable("minecraft:flowering_azalea",      2, 1)
                    .addNonBreakable("minecraft:fern",                   4, 1, 2)
                    .addNonBreakable("minecraft:large_fern",             3, 1)
                    .addNonBreakable("minecraft:grass",                  4, 1, 2)
                    .addNonBreakable("minecraft:short_grass",             5, 1, 3)
                    .addNonBreakable("minecraft:dandelion",              4, 1, 2)
                    .addNonBreakable("minecraft:poppy",                  4, 1, 2)
                    .addNonBreakable("minecraft:blue_orchid",             3, 1, 2)
                    .addNonBreakable("minecraft:allium",                  3, 1, 2)
                    .addNonBreakable("minecraft:azure_bluet",             3, 1, 2)
                    .addNonBreakable("minecraft:red_tulip",               3, 1, 2)
                    .addNonBreakable("minecraft:orange_tulip",             3, 1, 2)
                    .addNonBreakable("minecraft:white_tulip",              3, 1, 2)
                    .addNonBreakable("minecraft:pink_tulip",               3, 1, 2)
                    .addNonBreakable("minecraft:oxeye_daisy",              3, 1, 2)
                    .addNonBreakable("minecraft:cornflower",               3, 1, 2)
                    .addNonBreakable("minecraft:lily_of_the_valley",      2, 1)
                    .addNonBreakable("minecraft:flower_pot",               3, 1)
                    .addNonBreakable("minecraft:hanging_roots",             2, 1, 2)
                    .addNonBreakable("minecraft:vine",                    3, 1, 3))
            .build(new ZPLootTable.RollRules(0.75f, 1, 1, ZPRandomization.uniform()));

    // ===========================================================

    public static final ZPLootTable loot_village_tier3 = ZPLootTable.builder("loot_village_tier3")
            .extendBy(
                    new ZPLootTable.TableExtension("zpm3:loot_village_tier1",
                            new ZPLootTable.RollRules(0.20f, 1, 1, ZPRandomization.uniform())),
                    new ZPLootTable.TableExtension("zpm3:loot_village_tier2",
                            new ZPLootTable.RollRules(0.50f, 1, 1, ZPRandomization.uniform())),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("iron_tools", 35, (g) -> g
                    .addBreakable("minecraft:iron_axe",        10, 0.40f, 0.80f)
                    .addBreakable("minecraft:iron_shovel",     10, 0.40f, 0.80f)
                    .addBreakable("minecraft:iron_pickaxe",    10, 0.40f, 0.80f)
                    .addBreakable("minecraft:iron_hoe",        10, 0.40f, 0.80f))
            .commonGroup("firearms_m1911", 8, (g) -> g
                    .addBreakable("zpm3:m1911", 1, 0.40f, 0.80f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 7, ZPRandomization.power(1.5f))))
            .commonGroup("firearms_makarov", 8, (g) -> g
                    .addBreakable("zpm3:makarov", 1, 0.40f, 1.00f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 6, ZPRandomization.power(1.5f))))
            .commonGroup("firearms_colt", 2, (g) -> g
                    .addBreakable("zpm3:colt", 1, 0.05f, 0.10f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 3, ZPRandomization.power(1.5f))))
            .commonGroup("firearms_ammo", 18, new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(3.25f)), (g) -> g
                    .addNonBreakable("zpm3:_m1911",      4, 1, 16, ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_makarov",    6, 1, 16, ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_colt",       2, 1, 6, ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_handmade_pistol", 2, 1, 6, ZPRandomization.power(1.2f)))
            .commonGroup("food", 45, (g) -> g
                    .addNonBreakable("minecraft:beetroot",          10, 1)
                    .addNonBreakable("minecraft:carrot",            10, 1)
                    .addNonBreakable("minecraft:apple",             10, 1)
                    .addNonBreakable("zpm3:rotten_apple",            3, 1)
                    .addNonBreakable("minecraft:beetroot_soup",      4, 1)
                    .addNonBreakable("minecraft:rabbit_stew",        3, 1)
                    .addNonBreakable("minecraft:glow_berries",      6, 1, 2)
                    .addNonBreakable("minecraft:sweet_berries",     6, 1, 2)
                    .addNonBreakable("minecraft:dried_kelp",         6, 1)
                    .addNonBreakable("zpm3:soda",                    5, 1)
                    .addNonBreakable("zpm3:chocolate",               5, 1)
                    .addNonBreakable("zpm3:minecake",                2, 1)
                    .addNonBreakable("zpm3:jam",                     4, 1)
                    .addNonBreakable("zpm3:water",                   8, 1)
                    .addNonBreakable("minecraft:milk_bucket",        3, 1))
            .commonGroup("melee", 25, (g) -> g
                    .addBreakable("zpm3:bat",             10, 0.30f, 0.70f)
                    .addBreakable("zpm3:broom",            8, 0.80f, 0.90f)
                    .addBreakable("zpm3:pipe",             10, 0.40f, 0.80f)
                    .addBreakable("zpm3:golf_club",         6, 0.30f, 0.70f))
            .commonGroup("medical", 20, (g) -> g
                    .addBreakable("zpm3:bandage", 8, 0.0f, 0.75f))
            .commonGroup("tools", 18, (g) -> g
                    .addBreakable("zpm3:wrench",           6, 0.10f, 0.30f)
                    .addNonBreakable("zpm3:chisel_material", 4, 1)
                    .addNonBreakable("zpm3:shelves_material", 4, 1))
            .commonGroup("materials", 15, (g) -> g
                    .addNonBreakable("zpm3:scrap_material", 5, 1, 4, ZPRandomization.power(2.0f))
                    .addNonBreakable("minecraft:iron_nugget", 5, 1, 4, ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:oak_button", 4, 1, 3))
            .commonGroup("fishing", 8, (g) -> g
                    .addBreakable("minecraft:fishing_rod", 5, 0.20f, 0.50f))
            .bonusGroup("village_bonus", 0.008f, (g) -> g
                    .addNonBreakable("minecraft:leather",        4, 1, 2)
                    .addNonBreakable("minecraft:emerald",             3, 1)
                    .addNonBreakable("minecraft:iron_ingot",           2, 1)
                    .addNonBreakable("minecraft:angler_pottery_sherd", 3, 1)
                    .addNonBreakable("minecraft:archer_pottery_sherd", 3, 1)
                    .addNonBreakable("minecraft:prize_pottery_sherd",  2, 1)
                    .addNonBreakable("minecraft:heart_pottery_sherd",  2, 1)
                    .addNonBreakable("minecraft:blade_pottery_sherd",  2, 1))
            .bonusGroup("village_bonus2", 0.003f, (g) -> g
                    .addNonBreakable("minecraft:map",             3, 1)
                    .addNonBreakable("minecraft:compass",         2, 1)
                    .addNonBreakable("minecraft:clock",           1, 1)
                    .addNonBreakable("minecraft:gold_nugget", 3, 1, 3)
                    .addNonBreakable("minecraft:emerald",     2, 1)
                    .addNonBreakable("minecraft:iron_ingot",  2, 1, 2))
            .build(new ZPLootTable.RollRules(0.85f, 1, 2, ZPRandomization.power(3.0f)));

    // =============================================================

    public static final ZPLootTable loot_village_tier2 = ZPLootTable.builder("loot_village_tier2")
            .extendBy(
                    new ZPLootTable.TableExtension("zpm3:loot_village_tier1", new ZPLootTable.RollRules(0.4f, 1, 1, ZPRandomization.uniform())),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("armor_leather", 50, (g) -> g
                    .addBreakable("minecraft:leather_helmet",        1, 0.30f, 0.70f)
                    .addBreakable("minecraft:leather_chestplate",    1, 0.30f, 0.70f)
                    .addBreakable("minecraft:leather_leggings",      1, 0.30f, 0.70f)
                    .addBreakable("minecraft:leather_boots",         1, 0.30f, 0.70f))
            .commonGroup("armor_chainmail", 12, (g) -> g
                    .addBreakable("minecraft:chainmail_helmet",     1, 0.10f, 0.40f)
                    .addBreakable("minecraft:chainmail_chestplate", 1, 0.10f, 0.40f)
                    .addBreakable("minecraft:chainmail_leggings",   1, 0.10f, 0.40f)
                    .addBreakable("minecraft:chainmail_boots",      1, 0.10f, 0.40f))
            .commonGroup("armor_golden", 8, (g) -> g
                    .addBreakable("minecraft:golden_helmet",        1, 0.10f, 0.40f)
                    .addBreakable("minecraft:golden_chestplate",    1, 0.10f, 0.40f)
                    .addBreakable("minecraft:golden_leggings",      1, 0.10f, 0.40f)
                    .addBreakable("minecraft:golden_boots",         1, 0.10f, 0.40f))
            .commonGroup("stone_tools", 70, (g) -> g
                    .addBreakable("minecraft:fishing_rod",      5, 0.30f, 0.50f)
                    .addBreakable("minecraft:shears",      5, 0.10f, 0.30f)
                    .addBreakable("minecraft:stone_sword",              10, 0.40f, 0.70f)
                    .addBreakable("minecraft:stone_axe",                10, 0.40f, 0.70f)
                    .addBreakable("minecraft:stone_pickaxe",            10, 0.40f, 0.70f)
                    .addBreakable("minecraft:stone_shovel",             10, 0.40f, 0.70f)
                    .addBreakable("zpm3:matches",                       4, 0.05f, 0.25f))
            .commonGroup("golden_tools", 20, (g) -> g
                    .addBreakable("minecraft:golden_sword",             10, 0.30f, 1.00f)
                    .addBreakable("minecraft:golden_hoe",               10, 0.30f, 1.00f)
                    .addBreakable("minecraft:golden_axe",               10, 0.30f, 1.00f)
                    .addBreakable("minecraft:golden_pickaxe",           10, 0.30f, 1.00f)
                    .addBreakable("minecraft:golden_shovel",            10, 0.30f, 1.00f))
            .commonGroup("firearms_makarov", 4, (g) -> g
                    .addBreakable("zpm3:makarov",                     1, 0.30f, 0.70f,nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 6, ZPRandomization.power(1.5f))))
            .commonGroup("firearms_colt", 1, (g) -> g
                    .addBreakable("zpm3:colt",                        1, 0.0f, 0.02f,nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 3, ZPRandomization.power(1.5f))))
            .commonGroup("firearms_ammo", 8, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(4.0f)), (g) -> g
                    .addNonBreakable("zpm3:_m1911",      4, 1, 12, ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_makarov",    6, 1, 12, ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_colt",       2, 1, 4, ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_handmade_pistol", 2, 1, 4, ZPRandomization.power(1.2f)))
            .commonGroup("food", 40, (g) -> g
                    .addNonBreakable("minecraft:carrot",               10, 1)
                    .addNonBreakable("minecraft:potato",               10, 1)
                    .addNonBreakable("minecraft:sweet_berries",        10, 1, 2)
                    .addNonBreakable("minecraft:beef",                 10, 1)
                    .addNonBreakable("minecraft:rabbit",               10, 1)
                    .addNonBreakable("minecraft:chicken",              10, 1)
                    .addNonBreakable("minecraft:bread",                10, 1)
                    .addNonBreakable("minecraft:egg",                  10, 1)
                    .addNonBreakable("minecraft:wheat",                10, 1)
                    .addNonBreakable("minecraft:suspicious_stew", 3, 1, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 19);
                                        tag.putInt("EffectDuration", 100);
                                    })
                            ))
                    )
                    .addNonBreakable("minecraft:suspicious_stew", 2, 1, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 17);
                                        tag.putInt("EffectDuration", 300);
                                    })
                            ))
                    )
                    .addNonBreakable("minecraft:suspicious_stew", 2, 1, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 18);
                                        tag.putInt("EffectDuration", 600);
                                    })
                            ))
                    )
                    .addNonBreakable("minecraft:suspicious_stew", 1, 1, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 8);
                                        tag.putInt("EffectDuration", 100);
                                    })
                            ))
                    )
                    .addNonBreakable("minecraft:suspicious_stew", 1, 1, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 9);
                                        tag.putInt("EffectDuration", 100);
                                    })
                            ))
                    ))
            .commonGroup("survival", 30, (g) -> g
                    .addNonBreakable("zpm3:campfire2",                      6, 1)
                    .addNonBreakable("zpm3:water",                      10, 1)
                    .addNonBreakable("zpm3:chocolate",                  8, 1)
                    .addNonBreakable("zpm3:mysterious_can",             5, 1)
                    .addNonBreakable("zpm3:plate",                      5, 1, 3)
                    .addNonBreakable("minecraft:arrow",                 8, 1, 3)
                    .addNonBreakable("minecraft:bone",                  5, 1, 2)
                    .addNonBreakable("minecraft:bone_meal",             5, 1, 2)
                    .addNonBreakable("minecraft:torch", 8, 4)
                    .addNonBreakable("zpm3:lantern3", 8, 1)
                    .addNonBreakable("zpm3:lantern4", 6, 1)
                    .addNonBreakable("zpm3:torch2", 8, 1, 4)
                    .addNonBreakable("zpm3:torch3", 6, 1, 4)
                    .addNonBreakable("zpm3:torch4", 4, 1, 4))
            .commonGroup("melee", 25, (g) -> g
                    .addBreakable("zpm3:bat",                            10, 0.20f, 0.60f)
                    .addBreakable("zpm3:broom",                         10, 0.20f, 0.80f)
                    .addBreakable("zpm3:pipe",                          10, 0.30f, 0.70f))
            .commonGroup("materials", 10, (g) -> g
                    .addNonBreakable("zpm3:scrap_material",              3, 1, 2, ZPRandomization.power(2.0f))
                    .addNonBreakable("minecraft:leather",        3, 1))
            .bonusGroup("village_bonus", 0.01f, (g) -> g
                    .addNonBreakable("minecraft:angler_pottery_sherd",   4, 1)
                    .addNonBreakable("minecraft:archer_pottery_sherd",   4, 1)
                    .addNonBreakable("minecraft:arms_up_pottery_sherd",  4, 1)
                    .addNonBreakable("minecraft:blade_pottery_sherd",    4, 1)
                    .addNonBreakable("minecraft:brewer_pottery_sherd",   4, 1)
                    .addNonBreakable("minecraft:burn_pottery_sherd",     4, 1)
                    .addNonBreakable("minecraft:danger_pottery_sherd",   4, 1)
                    .addNonBreakable("minecraft:explorer_pottery_sherd", 4, 1)
                    .addNonBreakable("minecraft:friend_pottery_sherd",   4, 1)
                    .addNonBreakable("minecraft:heart_pottery_sherd",    4, 1)
                    .addNonBreakable("minecraft:heartbreak_pottery_sherd", 3, 1)
                    .addNonBreakable("minecraft:howl_pottery_sherd",     4, 1)
                    .addNonBreakable("minecraft:miner_pottery_sherd",    4, 1)
                    .addNonBreakable("minecraft:mourner_pottery_sherd",  3, 1)
                    .addNonBreakable("minecraft:plenty_pottery_sherd",   4, 1)
                    .addNonBreakable("minecraft:prize_pottery_sherd",    3, 1)
                    .addNonBreakable("minecraft:sheaf_pottery_sherd",    4, 1)
                    .addNonBreakable("minecraft:shelter_pottery_sherd",  4, 1)
                    .addNonBreakable("minecraft:skull_pottery_sherd",    3, 1)
                    .addNonBreakable("minecraft:snort_pottery_sherd",    3, 1))
            .bonusGroup("village_bonus2", 0.01f, (g) -> g
                    .addNonBreakable("minecraft:pottery_sherd", 8, 1)
                    .addNonBreakable("minecraft:brick",               6, 1, 3)
                    .addNonBreakable("minecraft:bone",                5, 1, 3)
                    .addNonBreakable("minecraft:emerald",             2, 1)
                    .addNonBreakable("minecraft:iron_nugget",         4, 1, 4))
            .build(new ZPLootTable.RollRules(0.7f, 1, 2, ZPRandomization.power(4.0f)));

    // ==================================================

    public static final ZPLootTable loot_village_tier1 = ZPLootTable.builder("loot_village_tier1")
            .extendBy(ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"))
            .commonGroup("building", 12, (g) -> g
                    .addNonBreakable("minecraft:oak_log",              3, 1)
                    .addNonBreakable("minecraft:oak_planks",           5, 1, 4)
                    .addNonBreakable("minecraft:oak_stairs",           3, 1, 2)
                    .addNonBreakable("minecraft:oak_slab",             4, 1, 3)
                    .addNonBreakable("minecraft:oak_fence",            3, 1, 2)
                    .addNonBreakable("minecraft:oak_fence_gate",       2, 1)
                    .addNonBreakable("minecraft:oak_door",             2, 1)
                    .addNonBreakable("minecraft:oak_trapdoor",         2, 1)
                    .addNonBreakable("minecraft:oak_pressure_plate",   2, 1, 2)
                    .addNonBreakable("minecraft:coarse_dirt",           3, 1, 2)
                    .addNonBreakable("minecraft:moss_block",            2, 1, 2)
                    .addNonBreakable("minecraft:cobblestone",           4, 1, 3)
                    .addNonBreakable("minecraft:stone",                 3, 1, 3)
                    .addNonBreakable("minecraft:oak_sapling",           2, 1)
                    .addNonBreakable("zpm3:torch3",                     3, 1, 3)
                    .addNonBreakable("minecraft:flower_pot",             1, 1)
                    .addNonBreakable("minecraft:hay_block",              1, 1)
            )
            .commonGroup("food", 10, (g) -> g
                    .addNonBreakable("minecraft:bread",          4, 1)
                    .addNonBreakable("minecraft:cookie",         8, 1, 2)
                    .addNonBreakable("minecraft:beetroot",       5, 1)
                    .addNonBreakable("minecraft:egg",             5, 1)
                    .addNonBreakable("minecraft:sweet_berries",  5, 1)
                    .addNonBreakable("minecraft:glow_berries",   4, 1)
                    .addNonBreakable("minecraft:dried_kelp",     4, 1)
                    .addNonBreakable("minecraft:chicken",        3, 1)
                    .addNonBreakable("minecraft:rabbit",         2, 1)
                    .addNonBreakable("minecraft:honeycomb",      2, 1)
                    .addNonBreakable("zpm3:rotten_apple",         3, 1)
                    .addNonBreakable("minecraft:suspicious_stew", 1, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId", (byte) 19);
                                        tag.putInt("EffectDuration", 100);
                                    })
                            ))
                    )
                    .addNonBreakable("minecraft:suspicious_stew", 1, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId",  (byte) 17);
                                        tag.putInt("EffectDuration", 300);
                                    })
                            ))
                    )
                    .addNonBreakable("minecraft:suspicious_stew", 1, 1,
                            nbt -> nbt.add("Effects", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putByte("EffectId",  (byte) 18);
                                        tag.putInt("EffectDuration", 600);
                                    })
                            ))
                    )
            )
            .commonGroup("household", 6, (g) -> g
                    .addNonBreakable("minecraft:bone",         3, 1)
                    .addNonBreakable("minecraft:rabbit_hide",  2, 1)
                    .addNonBreakable("minecraft:leather",      2, 1)
            )
            .commonGroup("materials", 4, (g) -> g
                    .addNonBreakable("zpm3:plate",             5, 1, 3)
                    .addNonBreakable("zpm3:scrap_material",    3, 1)
                    .addNonBreakable("zpm3:shelves_material",  1, 1)
                    .addNonBreakable("zpm3:chisel_material",   1, 1)
            )
            .commonGroup("tools_armor", 5, (g) -> g
                    .addBreakable("minecraft:stone_shovel",    4, 0.0f, 0.30f)
                    .addBreakable("minecraft:stone_pickaxe",   4, 0.0f, 0.30f)
                    .addBreakable("minecraft:stone_axe",       4, 0.0f, 0.30f)
                    .addBreakable("minecraft:stone_hoe",       3, 0.0f, 0.30f)
                    .addBreakable("minecraft:leather_helmet",       2, 0.0f, 0.30f)
                    .addBreakable("minecraft:leather_chestplate",   2, 0.0f, 0.30f)
                    .addBreakable("minecraft:leather_leggings",     2, 0.0f, 0.30f)
                    .addBreakable("minecraft:leather_boots",        2, 0.0f, 0.30f)
            )
            .commonGroup("weapons", 3, (g) -> g
                    .addBreakable("minecraft:bow", 8, 0.0f, 0.20f)
                    .addNonBreakable("minecraft:arrow", 10, 1, 3)
                    .addBreakable("zpm3:makarov", 1, 0.0f, 0.08f)
                    .addNonBreakable("zpm3:_makarov", 2, 1, 3)
                    .addNonBreakable("zpm3:_handmade_pistol", 2, 1, 3)
            )
            .commonGroup("survival", 20, (g) -> g
                    .addNonBreakable("minecraft:campfire",                      6, 1)
                    .addNonBreakable("zpm3:lantern3", 8, 1)
                    .addNonBreakable("zpm3:lantern4", 6, 1)
                    .addNonBreakable("minecraft:torch", 8, 1, 4)
                    .addNonBreakable("zpm3:torch2", 8, 1, 4)
                    .addNonBreakable("zpm3:torch3", 6, 1, 4)
                    .addNonBreakable("zpm3:torch4", 4, 1, 4))
            .bonusGroup("useful_scrap", 0.015f, (g) -> g
                    .addNonBreakable("minecraft:coal",          4, 1, 2)
                    .addNonBreakable("minecraft:iron_nugget",   3, 1, 2)
                    .addNonBreakable("minecraft:flint",         3, 1, 2)
                    .addNonBreakable("minecraft:lead",          2, 1)
                    .addNonBreakable("minecraft:gold_nugget",   1, 1)
                    .addNonBreakable("minecraft:compass",       1, 1)
                    .addNonBreakable("minecraft:clock",         1, 1)
                    .addNonBreakable("minecraft:name_tag",      1, 1)
            )

            .build(new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(3.0f)));

    // ================================================================

    public static final ZPLootTable loot_debris = ZPLootTable.builder("loot_debris")
            .commonGroup("garbage", 100, (g) -> g
                    .addNonBreakable("minecraft:rotten_flesh", 20, 1)
                    .addNonBreakable("minecraft:dead_bush", 45, 1)
                    .addNonBreakable("minecraft:stick", 15, 1)
                    .addNonBreakable("zpm3:torch4", 20, 1)
                    .addNonBreakable("zpm3:torch5", 25, 1)
                    .addNonBreakable("minecraft:clay_ball", 2, 1)
                    .addNonBreakable("minecraft:glass_bottle", 8, 1)
                    .addNonBreakable("minecraft:wheat_seeds", 2, 1)
            )
            .commonGroup("small_trash", 15, (g) -> g
                    .addNonBreakable("minecraft:feather", 10, 1)
                    .addNonBreakable("minecraft:bone", 10, 1)
                    .addNonBreakable("minecraft:string", 10, 1)
                    .addNonBreakable("minecraft:leather", 6, 1)
                    .addNonBreakable("minecraft:rabbit_hide", 6, 1)
                    .addNonBreakable("minecraft:paper", 8, 1)
                    .addNonBreakable("minecraft:book", 5, 1)
                    .addNonBreakable("minecraft:egg", 5, 1)
                    .addNonBreakable("minecraft:flint", 4, 1)
                    .addNonBreakable("minecraft:ink_sac", 3, 1)
                    .addNonBreakable("minecraft:brick", 2, 1)
                    .addNonBreakable("minecraft:nether_brick", 2, 1)
                    .addNonBreakable("minecraft:clay_ball", 4, 1, 2)
                    .addNonBreakable("minecraft:feather", 3, 1)
                    .addNonBreakable("zpm3:plate", 2, 1, 2)
            )
            .commonGroup("tools_armor", 5, (g) -> g
                    .addBreakable("minecraft:wooden_sword", 5, 0.0f, 0.30f)
                    .addBreakable("minecraft:wooden_pickaxe", 5, 0.0f, 0.30f)
                    .addBreakable("minecraft:wooden_axe", 5, 0.0f, 0.30f)
                    .addBreakable("minecraft:wooden_shovel", 5, 0.0f, 0.30f)
                    .addBreakable("minecraft:wooden_hoe", 5, 0.0f, 0.30f)
                    .addBreakable("minecraft:stone_sword", 3, 0.0f, 0.03f)
                    .addBreakable("minecraft:stone_pickaxe", 3, 0.0f, 0.03f)
                    .addBreakable("minecraft:stone_axe", 3, 0.0f, 0.03f)
                    .addBreakable("minecraft:stone_shovel", 3, 0.0f, 0.03f)
                    .addBreakable("minecraft:stone_hoe", 3, 0.0f, 0.03f)
                    .addBreakable("minecraft:bow", 2, 0.0f, 0.03f)
                    .addBreakable("minecraft:fishing_rod", 2, 0.0f, 0.03f)
                    .addBreakable("minecraft:leather_helmet",      1, 0.0f, 0.01f)
                    .addBreakable("minecraft:leather_chestplate",   1, 0.0f, 0.01f)
                    .addBreakable("minecraft:leather_leggings",     1, 0.0f, 0.01f)
                    .addBreakable("minecraft:leather_boots",        1, 0.0f, 0.01f)
                    .addBreakable("zpm3:broom", 2, 0.0f, 0.10f)
            )
            .bonusGroup("dyes", 0.015f, (g) -> g
                    .addNonBreakable("minecraft:white_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:orange_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:magenta_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:light_blue_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:yellow_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:lime_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:pink_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:gray_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:light_gray_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:cyan_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:purple_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:blue_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:brown_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:green_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:red_dye", 1, 1, 2)
                    .addNonBreakable("minecraft:black_dye", 1, 1, 2)
            )
            .bonusGroup("rare_scrap", 0.0075f, (g) -> g
                    .addNonBreakable("minecraft:iron_nugget",            1, 1)
                    .addNonBreakable("minecraft:gold_nugget",             1, 1)
                    .addNonBreakable("minecraft:slime_ball",              1, 1)
                    .addNonBreakable("minecraft:glow_ink_sac",            2, 1)
                    .addNonBreakable("minecraft:spider_eye",              2, 1)
                    .addNonBreakable("minecraft:fermented_spider_eye",    2, 1)
                    .addNonBreakable("minecraft:kelp",                    2, 1)
                    .addNonBreakable("minecraft:honeycomb",               2, 1)
                    .addNonBreakable("minecraft:nautilus_shell",          1, 1)
                    .addNonBreakable("minecraft:scute",                   1, 1)
                    .addNonBreakable("minecraft:prismarine_shard",        1, 1)
                    .addNonBreakable("minecraft:phantom_membrane",        1, 1)
                    .addNonBreakable("minecraft:goat_horn",               1, 1)
                    .addNonBreakable("minecraft:glow_berries",             2, 1)
                    .addNonBreakable("minecraft:sea_pickle",               2, 1)
                    .addNonBreakable("minecraft:amethyst_shard",           1, 1)
                    .addNonBreakable("minecraft:prismarine_crystals", 1, 1)
                    .addNonBreakable("minecraft:prismarine_crystals", 1, 1)
                    .addNonBreakable("minecraft:quartz", 2, 1)
            )
            .bonusGroup("rare_scrap2", 0.0025f, (g) -> g
                    .addNonBreakable("zpm3:cracked_crafting_table", 1, 1)
                    .addNonBreakable("zpm3:empty_bookshelf1", 1, 1)
                    .addNonBreakable("zpm3:empty_bookshelf2", 1, 1)
                    .addNonBreakable("zpm3:empty_bookshelf3", 1, 1)
                    .addNonBreakable("zpm3:wall_lamp_off", 1, 1)
                    .addNonBreakable("zpm3:block_lamp_off", 1, 1)
            )
            .build(new ZPLootTable.RollRules(1.0f, 4, 10, ZPRandomization.power(0.75f)));







    public static final ZPSyntheticLootCaseDescription Case__sample = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "sample"),
            "sample",
            "tier1",
            -1.0f,
            1200
    );
    public static final ZPLootTable sample = ZPLootTable.builder("sample")
            .extendBy(ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"))
            .commonGroup("sample1", 20, (g) -> g
                    .addNonBreakable("minecraft:dead_bush",           1,  1, 32)
                    .addBreakable("minecraft:wooden_sword",           1, 0.0f, 1.0f)
            )
            .commonGroup("sample2", 4, new ZPLootTable.RollRules(0.5f, 2, 4, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("minecraft:diamond",           1,  1, 32)
                    .addBreakable("minecraft:diamond_sword",           1, 0.0f, 1.0f, nbt -> nbt
                            .add("foo", 10)
                            .add("bar", true)
                            .add("longValue", 100L)
                            .add("floatValue", 0.5f)
                            .add("doubleValue", 0.75d)
                            .add("stringValue", "text")
                            .addRandom("randomInt", 10, 100, new ZPRandomization(ZPRandomization.Type.UNIFORM, 1.0f))
                            .addRandom("randBool", 0.5f)
                            .addRandom("randFloat", 10.0f, 100.0f, new ZPRandomization(ZPRandomization.Type.EXPONENTIAL, 1.4f))
                            .addRandom("randDouble", 10.0f, 100.0f, new ZPRandomization(ZPRandomization.Type.POWER, 0.4f))
                            .addRandom("randLong", 10, 100, new ZPRandomization(ZPRandomization.Type.POWER, 0.5f))
                            .add("TagList", List.of(
                                    IZPLootNbtContainer.compound(tag -> {
                                        tag.putString("EffectId", "minecraft:poison");
                                        tag.putInt("EffectDuration", 100);
                                    })
                            ))
                            .add("CompoundTag", IZPLootNbtContainer.compound(tag -> {
                                tag.putString("test", "test_str");
                            })))
            )
            .bonusGroup("bonus", new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("minecraft:bone",                    1, 1, 12, nbt -> nbt.add("TestFloat", 0.1f))
            )
            .build(new ZPLootTable.RollRules(0.9f, 1, 12, ZPRandomization.power(2.0f)));

    public static final ZPLootTable sampleExtension = ZPLootTable.builder("sampleExtension")
            .bonusGroup("bonus", new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("minecraft:emerald", 1, 1, 64, nbt -> nbt.add("TestFloat", 0.1f))
            )
            .build(new ZPLootTable.RollRules(1.0f, 1, 4, ZPRandomization.power(2.0f)));
}
