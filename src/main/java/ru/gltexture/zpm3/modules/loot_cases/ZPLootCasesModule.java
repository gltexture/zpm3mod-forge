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
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_restaurant_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_restaurant_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_bar_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_bar_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_building_store_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_building_store_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_building_store_tier2);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_building_store_tier2);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_home_stash);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_home_stash);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_construction_site);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_construction_site);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_firefighter_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_firefighter_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_firefighter_tier2);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_firefighter_tier2);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_police_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_police_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_police_tier2);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_police_tier2);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_police_tier3);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_police_tier3);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_fishing_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_fishing_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_fishing_tier2);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_fishing_tier2);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_pharmacy_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_pharmacy_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_pharmacy_tier2);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_pharmacy_tier2);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_hospital_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_hospital_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_hospital_tier2);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_hospital_tier2);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_ammunition_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_ammunition_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_ammunition_tier2);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_ammunition_tier2);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_ammunition_tier3);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_ammunition_tier3);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_submarine);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_submarine);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_military_tier1);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_military_tier1);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_military_tier2);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_military_tier2);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_military_tier3);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_military_tier3);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_radiation_zone);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_radiation_zone);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_toxic_zone);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_toxic_zone);
                context.registerSyntheticLootTable(ZPLootCasesModule.loot_chemzone_endgame);
                context.registerSyntheticLootCase(ZPLootCasesModule.Case__loot_chemzone_endgame);

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
            "loot_debris",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_village_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_village_tier1"),
            "loot_village_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_village_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_village_tier2"),
            "loot_village_tier2",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_village_tier3 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_village_tier3"),
            "loot_village_tier3",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_city_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_city_tier1"),
            "loot_city_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_city_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_city_tier2"),
            "loot_city_tier2",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_city_tier3 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_city_tier3"),
            "loot_city_tier3",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_garage_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_garage_tier1"),
            "loot_garage_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_kitchen_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_kitchen_tier1"),
            "loot_kitchen_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_restaurant_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_restaurant_tier1"),
            "loot_restaurant_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_bar_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_bar_tier1"),
            "loot_bar_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_building_store_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_building_store_tier1"),
            "loot_building_store_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_building_store_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_building_store_tier2"),
            "loot_building_store_tier2",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_home_stash = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_home_stash"),
            "loot_home_stash",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_construction_site = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_construction_site"),
            "loot_construction_site",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_firefighter_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_firefighter_tier1"),
            "loot_firefighter_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_firefighter_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_firefighter_tier2"),
            "loot_firefighter_tier2",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_police_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_police_tier1"),
            "loot_police_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_police_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_police_tier2"),
            "loot_police_tier2",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_police_tier3 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_police_tier3"),
            "loot_police_tier3",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_fishing_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_fishing_tier1"),
            "loot_fishing_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_fishing_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_fishing_tier2"),
            "loot_fishing_tier2",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_pharmacy_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_pharmacy_tier1"),
            "loot_pharmacy_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_pharmacy_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_pharmacy_tier2"),
            "loot_pharmacy_tier2",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_hospital_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_hospital_tier1"),
            "loot_hospital_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_hospital_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_hospital_tier2"),
            "loot_hospital_tier2",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_ammunition_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_ammunition_tier1"),
            "loot_ammunition_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_ammunition_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_ammunition_tier2"),
            "loot_ammunition_tier2",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_ammunition_tier3 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_ammunition_tier3"),
            "loot_ammunition_tier3",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_submarine = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_submarine"),
            "loot_submarine",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_military_tier1 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_military_tier1"),
            "loot_military_tier1",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_military_tier2 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_military_tier2"),
            "loot_military_tier2",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_military_tier3 = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_military_tier3"),
            "loot_military_tier3",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_radiation_zone = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_radiation_zone"),
            "loot_radiation_zone",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_toxic_zone = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_toxic_zone"),
            "loot_toxic_zone",
            "tier1",
            -1.0f,
            0
    );
    public static final ZPSyntheticLootCaseDescription Case__loot_chemzone_endgame = new ZPSyntheticLootCaseDescription(
            ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "loot_chemzone_endgame"),
            "loot_chemzone_endgame",
            "tier1",
            -1.0f,
            0
    );
    // ====================================================
    // ====================================================
    // ====================================================
    // ====================================================

    public static final ZPLootTable loot_chemzone_endgame = ZPLootTable.builder("loot_chemzone_endgame")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension("zpm3:loot_military_tier3",
                            new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(6.0f))),
                    new ZPLootTable.TableExtension("zpm3:loot_radiation",
                            new ZPLootTable.RollRules(0.30f, 1, 2, ZPRandomization.power(5.0f))),
                    new ZPLootTable.TableExtension("zpm3:loot_toxic_zone",
                            new ZPLootTable.RollRules(0.20f, 1, 2, ZPRandomization.power(5.0f))),
                    new ZPLootTable.TableExtension("zpm3:loot_submarine",
                            new ZPLootTable.RollRules(0.10f, 1, 1, ZPRandomization.uniform())),
                    new ZPLootTable.TableExtension("zpm3:loot_hospital_tier2",
                            new ZPLootTable.RollRules(0.50f, 1, 2, ZPRandomization.power(4.0f)))
            )
            .commonGroup("chemical_protection", 24,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(10.0f)), (g) -> g
                            .addBreakable("zpm3:radiation_costume_helmet",      15, 0.70f, 1.0f)
                            .addBreakable("zpm3:radiation_costume_chestplate",  15, 0.70f, 1.0f)
                            .addBreakable("zpm3:radiation_costume_leggings",    15, 0.70f, 1.0f)
                            .addBreakable("zpm3:radiation_costume_boots",       15, 0.70f, 1.0f)
                            .addBreakable("zpm3:aqualung_costume_helmet",        4, 0.60f, 1.0f)
                            .addBreakable("zpm3:aqualung_costume_chestplate",    4, 0.60f, 1.0f)
                            .addBreakable("zpm3:aqualung_costume_leggings",      4, 0.60f, 1.0f)
                            .addBreakable("zpm3:aqualung_costume_boots",         4, 0.60f, 1.0f)
                            .addBreakable("zpm3:oxygen",                         5, 0.50f, 1.0f)
            )
            .commonGroup("firearms", 18, (g) -> g
                    .addBreakable("zpm3:machinegun", 15, 0.90f, 1.0f)
                    .addBreakable("zpm3:m16",        3, 0.75f, 1.0f)
                    .addBreakable("zpm3:akm",        3, 0.75f, 1.0f)
                    .addBreakable("zpm3:mp5",        2, 0.75f, 1.0f)
                    .addBreakable("zpm3:deagle",     2, 0.75f, 1.0f)
            )
            .commonGroup("ammunition", 32,
                    new ZPLootTable.RollRules(1.0f, 3, 6, ZPRandomization.power(1.25f)), (g) -> g
                            .addNonBreakable("zpm3:_machinegun", 10, 16, 32, ZPRandomization.power(1.15f))
                            .addNonBreakable("zpm3:_m16",          5, 16, 32, ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_akm",          5, 16, 32, ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_mp5",          4, 16, 32, ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_deagle",       2, 8, 16, ZPRandomization.power(1.25f))
            )
            .commonGroup("endgame_medicine", 18,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.5f)), (g) -> g
                            .addNonBreakable("zpm3:anti_zplague_syringe",     4, 1)
                            .addNonBreakable("zpm3:antibiotics_syringe",      5, 1, 4)
                            .addNonBreakable("zpm3:adrenaline_syringe",       5, 1, 4)
                            .addNonBreakable("zpm3:aid_kit",                   4, 1)
                            .addBreakable("zpm3:bandage",                      6, 0.70f, 1.0f)
                            .addNonBreakable("zpm3:radiation_protection_pill", 5, 1, 2)
                            .addNonBreakable("zpm3:anti_hunger_pill",          3, 1, 2)
                            .addNonBreakable("zpm3:anti_poison_pill",          3, 1, 2)
                            .addNonBreakable("zpm3:anti_headache_pill",        3, 1, 2)
                            .addNonBreakable("zpm3:vitamin_pill",               3, 1, 2)
                            .addNonBreakable("zpm3:whiskey_medicine",           2, 1, 2)
                            .addNonBreakable("zpm3:vodka_medicine",             2, 1, 2)
            )
            .commonGroup("hazard_materials", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:acid_bottle",       5, 2, 6)
                            .addNonBreakable("zpm3:acid_bucket",       3, 1, 2)
                            .addNonBreakable("zpm3:toxicwater_bucket", 2, 1)
                            .addNonBreakable("zpm3:cement_material",   5, 1, 3)
                            .addNonBreakable("zpm3:steel_black",       3, 4, 12)
                            .addNonBreakable("zpm3:steel_gray",        3, 4, 12)
                            .addNonBreakable("zpm3:steel_hazard",      3, 4, 12)
                            .addNonBreakable("zpm3:steel_green",       2, 4, 12)
                            .addNonBreakable("zpm3:steel_orange",      2, 4, 12)
                            .addNonBreakable("zpm3:steel_white",       2, 4, 12)
            )
            .commonGroup("endgame_resources", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(3.0f)), (g) -> g
                            .addNonBreakable("minecraft:diamond",        6, 2, 9, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:emerald",        4, 1, 6, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:gold_ingot",     3, 1, 4, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:iron_ingot",     4, 2, 8, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:netherite_scrap", 2, 1, 2)
                            .addNonBreakable("minecraft:ancient_debris", 1, 1)
                            .addNonBreakable("minecraft:redstone",       3, 4, 12, ZPRandomization.power(1.5f))
            )
            .commonGroup("construction", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:armored_glass",       4, 4, 12)
                            .addNonBreakable("zpm3:armored_glasspane",   4, 4, 12)
                            .addNonBreakable("zpm3:block_lamp",          3, 1, 2)
                            .addNonBreakable("zpm3:wall_lamp",           3, 1, 2)
                            .addNonBreakable("zpm3:barbared_wire",       3, 2, 8)
                            .addNonBreakable("minecraft:iron_bars",      3, 4, 12)
                            .addNonBreakable("minecraft:chain",          3, 4, 12)
            )
            .bonusGroup("radiation_endgame_bonus", new ZPLootTable.RollRules(
                    0.015f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("minecraft:netherite_scrap", 5, 1, 2)
                    .addNonBreakable("minecraft:ancient_debris", 2, 1)
                    .addNonBreakable("minecraft:diamond",          8, 4, 8)
                    .addNonBreakable("zpm3:_machinegun",            8, 24, 32)
                    .addBreakable("zpm3:machinegun",                 5, 0.95f, 1.0f)
                    .addNonBreakable("zpm3:anti_zplague_syringe",    4, 1)
                    .addNonBreakable("zpm3:radiation_protection_pill", 3, 1, 2))
            .build(new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(3.0f)));

    // ====================================================

    public static final ZPLootTable loot_toxic_zone = ZPLootTable.builder("loot_toxic_zone")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_city_tier2")
            )
            .commonGroup("aqualung", 20,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(8.0f)), (g) -> g
                            .addBreakable("zpm3:aqualung_costume_helmet",      8, 0.20f, 0.55f)
                            .addBreakable("zpm3:aqualung_costume_chestplate",  8, 0.20f, 0.55f)
                            .addBreakable("zpm3:aqualung_costume_leggings",    8, 0.20f, 0.55f)
                            .addBreakable("zpm3:aqualung_costume_boots",       8, 0.20f, 0.55f)
                            .addBreakable("zpm3:oxygen",                       5, 0.20f, 0.60f)
            )
            .commonGroup("toxic_materials", 25,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.75f)), (g) -> g
                            .addNonBreakable("zpm3:acid_bottle",       1, 1, 2, ZPRandomization.power(2.5f))
                            .addNonBreakable("zpm3:acid_bucket",        2, 1)
                            .addNonBreakable("zpm3:toxicwater_bucket",  3, 1)
                            .addNonBreakable("zpm3:cement_material",    1, 1)
                            .addNonBreakable("zpm3:scrap_material",     5, 2, 6, ZPRandomization.power(2.0f))
                            .addNonBreakable("minecraft:iron_ingot",   3, 1, 3, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:iron_nugget",  5, 2, 8, ZPRandomization.power(1.5f))
            )
            .commonGroup("toxic_construction", 18,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:steel_black",    3, 1, 2)
                            .addNonBreakable("zpm3:steel_gray",     3, 1, 2)
                            .addNonBreakable("zpm3:steel_green",    2, 1, 2)
                            .addNonBreakable("zpm3:steel_hazard",   4, 1, 2)
                            .addNonBreakable("zpm3:steel_orange",   2, 1, 2)
                            .addNonBreakable("zpm3:armored_glass",  2, 1, 2)
                            .addNonBreakable("zpm3:armored_glasspane", 2, 4, 12)
                            .addNonBreakable("zpm3:barbared_wire",  4, 1, 2)
                            .addNonBreakable("zpm3:chain_link",     3, 4, 16)
                            .addNonBreakable("zpm3:block_lamp",     2, 1, 2)
                            .addNonBreakable("zpm3:wall_lamp",      2, 1, 2)
            )
            .commonGroup("medical", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:anti_poison_pill",       6, 1)
                            .addNonBreakable("zpm3:anti_hunger_pill",       4, 1)
                            .addNonBreakable("zpm3:radiation_protection_pill", 1, 1)
                            .addNonBreakable("zpm3:zplague_syringe",        2, 1)
                            .addNonBreakable("zpm3:morphine_syringe",       3, 1)
                            .addNonBreakable("zpm3:aid_kit",                2, 1)
                            .addBreakable("zpm3:bandage",                    7, 0.30f, 0.80f)
                            .addNonBreakable("zpm3:drugs",                   2, 1)
            )
            .commonGroup("equipment", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addBreakable("zpm3:metal_cutters", 3, 0.30f, 0.70f)
                            .addBreakable("zpm3:wrench",        4, 0.30f, 0.75f)
                            .addBreakable("zpm3:hatchet",       2, 0.30f, 0.65f)
                            .addNonBreakable("zpm3:plate",      3, 1, 3)
                            .addNonBreakable("minecraft:bucket", 4, 1)
                            .addNonBreakable("minecraft:glass_bottle", 5, 1, 4)
            )
            .commonGroup("survival", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:water",          6, 1, 3)
                            .addNonBreakable("zpm3:soda",           3, 1, 2)
                            .addNonBreakable("zpm3:chocolate",      3, 1, 4)
                            .addNonBreakable("minecraft:dried_kelp", 4, 2, 6)
                            .addNonBreakable("minecraft:torch",     4, 2, 6)
                            .addNonBreakable("minecraft:lantern",   2, 1, 2)
            )
            .bonusGroup("toxic_bonus", new ZPLootTable.RollRules(
                    0.008f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("minecraft:diamond",       4, 1, 2)
                    .addNonBreakable("minecraft:emerald",       6, 1, 3)
                    .addNonBreakable("minecraft:gold_ingot",    4, 1, 2)
                    .addNonBreakable("minecraft:netherite_scrap", 1, 1)
                    .addNonBreakable("zpm3:radiation_protection_pill", 1, 1)
                    .addNonBreakable("zpm3:drugs",              2, 1))
            .build(new ZPLootTable.RollRules(0.85f, 1, 2, ZPRandomization.power(2.25f)));

    // ====================================================

    public static final ZPLootTable loot_radiation_zone = ZPLootTable.builder("loot_radiation_zone")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension("zpm3:loot_military_tier2",
                            new ZPLootTable.RollRules(0.50f, 1, 1, ZPRandomization.uniform())),
                    new ZPLootTable.TableExtension("zpm3:loot_hospital_tier2",
                            new ZPLootTable.RollRules(0.25f, 1, 1, ZPRandomization.uniform())),
                    new ZPLootTable.TableExtension("zpm3:loot_city_tier2",
                            new ZPLootTable.RollRules(0.10f, 1, 1, ZPRandomization.uniform()))
            )
            .commonGroup("acid_protection", 20,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(8.0f)), (g) -> g
                            .addBreakable("zpm3:acid_costume_helmet",      12, 0.20f, 0.6f, ZPRandomization.power(1.5f))
                            .addBreakable("zpm3:acid_costume_chestplate",  12, 0.20f, 0.6f, ZPRandomization.power(1.5f))
                            .addBreakable("zpm3:acid_costume_leggings",    12, 0.20f, 0.6f, ZPRandomization.power(1.5f))
                            .addBreakable("zpm3:acid_costume_boots",       12, 0.20f, 0.6f, ZPRandomization.power(1.5f))
                            .addBreakable("zpm3:oxygen",                     4, 0.50f, 0.90f)
            )
            .commonGroup("hazard_materials", 20,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:steel_black",       5, 4, 12)
                            .addNonBreakable("zpm3:steel_white",       5, 4, 12)
                            .addNonBreakable("zpm3:steel_green",       5, 4, 12)
                            .addNonBreakable("zpm3:steel_orange",      5, 4, 12)
                            .addNonBreakable("zpm3:steel_gray",        5, 4, 12)
                            .addNonBreakable("zpm3:steel_hazard",      6, 4, 12)
                            .addNonBreakable("zpm3:armored_glass",     4, 4, 12)
                            .addNonBreakable("zpm3:armored_glasspane",  4, 4, 12)
                            .addNonBreakable("zpm3:cement_material",    4, 1)
                            .addNonBreakable("zpm3:plate",               4, 1, 4)
            )
            .commonGroup("industrial_equipment", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.75f)), (g) -> g
                            .addBreakable("zpm3:metal_cutters",  3, 0.50f, 0.85f)
                            .addBreakable("zpm3:wrench",         4, 0.50f, 0.90f)
                            .addNonBreakable("zpm3:barbared_wire", 4, 2, 8)
                            .addNonBreakable("minecraft:iron_bars", 3, 4, 12)
                            .addNonBreakable("minecraft:chain",     3, 4, 12)
                            .addNonBreakable("minecraft:iron_block", 2, 1, 2)
                            .addNonBreakable("minecraft:copper_block", 2, 1, 2)
            )
            .commonGroup("lighting", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:block_lamp",  3, 1, 2)
                            .addNonBreakable("zpm3:wall_lamp",   3, 1, 2)
                            .addNonBreakable("minecraft:lantern", 3, 1, 3)
                            .addNonBreakable("minecraft:torch",   4, 2, 8)
            )
            .commonGroup("hazard_survival", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:acid_bottle",              5, 1, 2, ZPRandomization.power(2.0f))
                            .addNonBreakable("zpm3:water",                     4, 1, 2)
                            .addNonBreakable("minecraft:milk_bucket",         2, 1)
                            .addNonBreakable("minecraft:glass_bottle",        4, 1, 4)
                            .addNonBreakable("zpm3:zplague_syringe",           2, 1)
                            .addBreakable("zpm3:bandage",                      4, 0.50f, 1.0f)
                            .addNonBreakable("zpm3:aid_kit",                   2, 1)
            )
            .commonGroup("advanced_materials", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:obsidian",        3, 2, 8)
                            .addNonBreakable("minecraft:crying_obsidian", 2, 1, 4)
                            .addNonBreakable("minecraft:quartz",           3, 2, 8)
                            .addNonBreakable("minecraft:redstone",         4, 4, 16)
                            .addNonBreakable("minecraft:iron_ingot",        4, 2, 6)
                            .addNonBreakable("minecraft:gold_ingot",        2, 1, 3)
                            .addNonBreakable("zpm3:scrap_material",         4, 2, 6)
            )
            .commonGroup("hazard_construction", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.75f)), (g) -> g
                            .addNonBreakable("minecraft:stone",       4, 4, 16)
                            .addNonBreakable("minecraft:iron_block",  2, 1, 2)
                            .addNonBreakable("minecraft:obsidian",    2, 1, 4)
                            .addNonBreakable("zpm3:cement_material",  5, 1, 4)
                            .addNonBreakable("zpm3:armored_glass",    4, 2, 8)
                            .addNonBreakable("zpm3:armored_glasspane", 3, 2, 8)
            )
            .bonusGroup("radiation_bonus", new ZPLootTable.RollRules(
                    0.015f, 1, 2, ZPRandomization.power(1.75f)), (g) -> g
                    .addNonBreakable("minecraft:netherite_scrap", 4, 1, 2)
                    .addNonBreakable("minecraft:diamond",        5, 1, 4)
                    .addNonBreakable("minecraft:emerald",        3, 2, 6)
                    .addNonBreakable("minecraft:gold_ingot",     3, 2, 4)
                    .addNonBreakable("zpm3:radiation_protection_pill", 1, 1)
                    .addNonBreakable("zpm3:acid_bucket", 2, 1)
            )
            .build(new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.5f)));

    // ====================================================

    public static final ZPLootTable loot_military_tier3 = ZPLootTable.builder("loot_military_tier3")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension("zpm3:loot_military_tier2",
                            new ZPLootTable.RollRules(0.40f, 1, 1, ZPRandomization.uniform()))
            )
            .commonGroup("elite_firearms", 22, (g) -> g
                            .addBreakable("zpm3:deagle",     5, 0.55f, 0.85f)
                            .addBreakable("zpm3:machinegun", 1, 0.05f, 0.15f)
                            .addBreakable("zpm3:mp5",        4, 0.45f, 0.75f)
                            .addBreakable("zpm3:m16",        4, 0.45f, 0.75f)
                            .addBreakable("zpm3:akm",        4, 0.40f, 0.70f)
                            .addBreakable("zpm3:mosin",      2, 0.25f, 0.55f)
            )
            .commonGroup("ammunition", 25,
                    new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(1.25f)), (g) -> g
                            .addNonBreakable("zpm3:_deagle",     5, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_akm",        5, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_mp5",        4, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_shotgun",   3, 16, 32, ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_machinegun", 3, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_m16",        5, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_uzi",        3, 16, 32, ZPRandomization.power(1.5f))
            )
            .commonGroup("night_vision", 8, (g) -> g
                    .addBreakable("zpm3:night_vision_goggles", 5, 0.50f, 0.85f)
            )
            .commonGroup("military_medical", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:radiation_protection_pill", 1, 1)
                            .addBreakable("zpm3:bandage", 8, 0.50f, 0.90f)
                            .addNonBreakable("zpm3:whiskey_medicine", 5, 1, 2)
                            .addNonBreakable("zpm3:vodka_medicine",   5, 1, 2)
                            .addNonBreakable("zpm3:drugs",             4, 1, 2)
                            .addBreakable("zpm3:aid_kit",               3, 0.50f, 1.0f)
            )
            .commonGroup("elite_equipment", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.5f)), (g) -> g
                            .addBreakable("minecraft:diamond_sword",   3, 0.35f, 0.70f)
                            .addBreakable("minecraft:diamond_axe",     3, 0.35f, 0.70f)
                            .addBreakable("minecraft:diamond_pickaxe", 2, 0.30f, 0.65f)
                            .addBreakable("minecraft:diamond_shovel",  2, 0.30f, 0.65f)
                            .addBreakable("minecraft:diamond_helmet",      2, 0.25f, 0.60f)
                            .addBreakable("minecraft:diamond_chestplate",  2, 0.25f, 0.60f)
                            .addBreakable("minecraft:diamond_leggings",    2, 0.25f, 0.60f)
                            .addBreakable("minecraft:diamond_boots",       2, 0.25f, 0.60f)
            )
            .commonGroup("military_blocks", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.75f)), (g) -> g
                            .addNonBreakable("zpm3:armored_glass",     4, 4, 12)
                            .addNonBreakable("zpm3:armored_glasspane", 4, 4, 12)
                            .addNonBreakable("zpm3:steel_black",        3, 1, 3)
                            .addNonBreakable("zpm3:steel_green",        3, 1, 3)
                            .addNonBreakable("zpm3:steel_gray",         3, 1, 3)
                            .addNonBreakable("zpm3:steel_hazard",       3, 1, 3)
                            .addNonBreakable("zpm3:steel_orange",       2, 1, 3)
                            .addNonBreakable("zpm3:steel_white",        2, 1, 3)
                            .addNonBreakable("zpm3:wall_lamp",           2, 1)
                            .addNonBreakable("zpm3:block_lamp",          2, 1)
                            .addNonBreakable("zpm3:sandbag",             4, 4, 12)
                            .addNonBreakable("zpm3:barbared_wire",       3, 2, 6)
                            .addNonBreakable("zpm3:chain_link",          3, 8, 24)
                            .addNonBreakable("minecraft:iron_bars",      3, 4, 12)
                            .addNonBreakable("minecraft:chain",          3, 4, 12)
            )
            .commonGroup("military_materials", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:cement_material",  4, 1, 3)
                            .addNonBreakable("zpm3:plate",             4, 1, 4)
                            .addNonBreakable("zpm3:scrap_material",   5, 2, 8, ZPRandomization.power(2.0f))
                            .addNonBreakable("minecraft:iron_ingot",  4, 2, 6, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:gold_ingot",  2, 1, 3)
                            .addNonBreakable("minecraft:diamond",     1, 1, 2)
                            .addNonBreakable("minecraft:emerald",     1, 1, 2)
            )
            .commonGroup("survival", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("minecraft:golden_carrot", 4, 2, 6)
                            .addNonBreakable("minecraft:cooked_beef",   5, 2, 8)
                            .addNonBreakable("minecraft:cooked_mutton", 4, 2, 8)
                            .addNonBreakable("zpm3:water",               5, 1, 4)
                            .addNonBreakable("zpm3:chocolate",           4, 1, 4)
            )
            .bonusGroup("military_bonus", 0.01f, (g) -> g
                    .addNonBreakable("zpm3:_deagle",  3, 24, 32)
                    .addNonBreakable("zpm3:_m16",     3, 24, 32)
                    .addNonBreakable("zpm3:_machinegun", 2, 24, 32)
                    .addNonBreakable("minecraft:diamond", 2, 1, 2)
                    .addNonBreakable("zpm3:cement_material", 3, 2, 4))
            .build(new ZPLootTable.RollRules(1.0f, 2, 3, ZPRandomization.power(2.5f)));

    // ====================================================

    public static final ZPLootTable loot_military_tier2 = ZPLootTable.builder("loot_military_tier2")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension("zpm3:loot_military_tier1",
                            new ZPLootTable.RollRules(0.50f, 1, 1, ZPRandomization.uniform()))
            )
            .commonGroup("camo_equipment", 22,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.5f)), (g) -> g
                            .addNonBreakable("zpm3:camo_snow",   5, 8, 24)
                            .addNonBreakable("zpm3:camo_sand",   5, 8, 24)
                            .addNonBreakable("zpm3:camo_forest", 6, 8, 24)
                            .addBreakable("zpm3:forest_helmet",      3, 0.45f, 0.80f)
                            .addBreakable("zpm3:forest_chestplate",  3, 0.45f, 0.80f)
                            .addBreakable("zpm3:forest_leggings",    3, 0.45f, 0.80f)
                            .addBreakable("zpm3:forest_boots",       3, 0.45f, 0.80f)
                            .addBreakable("zpm3:sand_helmet",        3, 0.45f, 0.80f)
                            .addBreakable("zpm3:sand_chestplate",    3, 0.45f, 0.80f)
                            .addBreakable("zpm3:sand_leggings",      3, 0.45f, 0.80f)
                            .addBreakable("zpm3:sand_boots",         3, 0.45f, 0.80f)
                            .addBreakable("zpm3:winter_helmet",      3, 0.40f, 0.75f)
                            .addBreakable("zpm3:winter_chestplate",  3, 0.40f, 0.75f)
                            .addBreakable("zpm3:winter_leggings",    3, 0.40f, 0.75f)
                            .addBreakable("zpm3:winter_boots",       3, 0.40f, 0.75f)
            )
            .commonGroup("firearms", 18, (g) -> g
                            .addBreakable("zpm3:akm",       8, 0.20f, 0.50f)
                            .addBreakable("zpm3:m16",       8, 0.20f, 0.50f)
                            .addBreakable("zpm3:deagle",    8, 0.20f, 0.50f)
                            .addBreakable("zpm3:shotgun",   6, 0.15f, 0.40f)
                            .addBreakable("zpm3:machinegun", 1, 0.01f, 0.05f)
            )
            .commonGroup("ammunition", 25,
                    new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_machinegun", 4, 8, 24, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_shotgun",    5, 8, 16, ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_m16",        5, 8, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_akm",        5, 8, 24, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_deagle",     4, 8, 24, ZPRandomization.power(1.5f))
            )
            .commonGroup("night_vision", 6, (g) -> g
                    .addBreakable("zpm3:night_vision_goggles", 3, 0.20f, 0.50f))
            .commonGroup("medical", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:anti_hunger_pill",   4, 1)
                            .addNonBreakable("zpm3:anti_headache_pill", 3, 1)
                            .addNonBreakable("zpm3:adrenaline_syringe", 3, 1)
                            .addNonBreakable("zpm3:morphine_syringe",   3, 1)
                            .addNonBreakable("zpm3:aid_kit",             2, 1)
                            .addNonBreakable("zpm3:drugs",               4, 1, 2)
                            .addBreakable("zpm3:bandage",                 5, 0.30f, 0.75f)
            )
            .commonGroup("military_equipment", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.75f)), (g) -> g
                            .addBreakable("zpm3:metal_cutters", 3, 0.40f, 0.75f)
                            .addBreakable("zpm3:wrench",        4, 0.40f, 0.80f)
                            .addNonBreakable("zpm3:cement_material", 3, 1)
                            .addNonBreakable("zpm3:armored_glass",    4, 4, 12)
                            .addNonBreakable("zpm3:armored_glasspane", 4, 4, 12)
                            .addNonBreakable("zpm3:wall_lamp", 2, 1)
                            .addNonBreakable("zpm3:steel_black",   2, 1, 2)
                            .addNonBreakable("zpm3:steel_green",   2, 1, 2)
                            .addNonBreakable("zpm3:steel_gray",    2, 1, 2)
                            .addNonBreakable("zpm3:steel_hazard",  2, 1, 2)
                            .addNonBreakable("zpm3:steel_orange",  2, 1, 2)
                            .addNonBreakable("zpm3:steel_white",   2, 1, 2)
            )
            .commonGroup("materials", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:leather",    4, 4, 10)
                            .addNonBreakable("minecraft:iron_ingot", 3, 1, 3, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:gold_ingot", 1, 1)
                            .addNonBreakable("minecraft:diamond",    1, 1)
                            .addNonBreakable("minecraft:emerald",    1, 1)
            )
            .commonGroup("survival", 9,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("minecraft:suspicious_stew", 2, 1)
                            .addNonBreakable("minecraft:golden_carrot",   3, 1, 2)
                            .addNonBreakable("minecraft:cooked_beef",     5, 2, 6)
                            .addNonBreakable("minecraft:cooked_mutton",   4, 2, 6)
                            .addNonBreakable("minecraft:cooked_chicken",  4, 2, 6)
                            .addNonBreakable("zpm3:water",                5, 1, 3)
                            .addNonBreakable("zpm3:chocolate",            3, 1, 3)
            )
            .bonusGroup("military_bonus", 0.006f, (g) -> g
                    .addNonBreakable("zpm3:_machinegun", 2, 16, 32)
                    .addNonBreakable("zpm3:_m16",        3, 16, 32)
                    .addNonBreakable("zpm3:_akm",        3, 12, 24)
                    .addNonBreakable("zpm3:_deagle",     3, 12, 24)
                    .addNonBreakable("minecraft:diamond", 2, 1, 2)
                    .addNonBreakable("minecraft:gold_ingot", 2, 1, 3)
                    .addBreakable("zpm3:night_vision_goggles", 2, 0.35f, 0.60f))
            .build(new ZPLootTable.RollRules(0.90f, 1, 2, ZPRandomization.power(2.25f)));

    // ====================================================

    public static final ZPLootTable loot_military_tier1 = ZPLootTable.builder("loot_military_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("camo_equipment", 28,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.5f)), (g) -> g
                            .addNonBreakable("zpm3:camo_snow",   5, 4, 16)
                            .addNonBreakable("zpm3:camo_sand",   5, 4, 16)
                            .addNonBreakable("zpm3:camo_forest", 6, 4, 16)
                            .addBreakable("zpm3:forest_helmet",      3, 0.25f, 0.60f)
                            .addBreakable("zpm3:forest_chestplate",  3, 0.25f, 0.60f)
                            .addBreakable("zpm3:forest_leggings",    3, 0.25f, 0.60f)
                            .addBreakable("zpm3:forest_boots",       3, 0.25f, 0.60f)
                            .addBreakable("zpm3:sand_helmet",        3, 0.25f, 0.60f)
                            .addBreakable("zpm3:sand_chestplate",    3, 0.25f, 0.60f)
                            .addBreakable("zpm3:sand_leggings",      3, 0.25f, 0.60f)
                            .addBreakable("zpm3:sand_boots",         3, 0.25f, 0.60f)
                            .addBreakable("zpm3:winter_helmet",      3, 0.25f, 0.60f)
                            .addBreakable("zpm3:winter_chestplate",  3, 0.25f, 0.60f)
                            .addBreakable("zpm3:winter_leggings",    3, 0.25f, 0.60f)
                            .addBreakable("zpm3:winter_boots",       3, 0.25f, 0.60f)
            )
            .commonGroup("firearms", 20, (g) -> g
                            .addBreakable("zpm3:usp",    5, 0.35f, 0.65f)
                            .addBreakable("zpm3:uzi",    4, 0.25f, 0.55f)
                            .addBreakable("zpm3:mp5",    3, 0.25f, 0.55f)
                            .addBreakable("zpm3:mosin",  3, 0.20f, 0.50f)
                            .addBreakable("zpm3:iron_club", 5, 0.35f, 0.70f)
            )
            .commonGroup("ammunition", 25,
                    new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_usp",   5, 8, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_uzi",   5, 8, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_mp5",   4, 8, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_mosin", 3, 4, 16, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:arrow", 5, 8, 28, ZPRandomization.power(1.5f))
            )
            .commonGroup("weapons", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.5f)), (g) -> g
                            .addBreakable("minecraft:iron_sword",   4, 0.25f, 0.60f)
                            .addBreakable("minecraft:iron_axe",     4, 0.25f, 0.60f)
                            .addBreakable("minecraft:iron_pickaxe", 3, 0.25f, 0.60f)
                            .addBreakable("minecraft:bow",           3, 0.20f, 0.55f)
                            .addBreakable("minecraft:crossbow",      3, 0.20f, 0.55f)
                            .addBreakable("minecraft:shield",        3, 0.30f, 0.65f)
                            .addBreakable("minecraft:diamond_sword", 1, 0.05f, 0.20f)
                            .addBreakable("minecraft:diamond_axe",   1, 0.05f, 0.20f)
            )
            .commonGroup("field_equipment", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.75f)), (g) -> g
                            .addNonBreakable("zpm3:sandbag",       5, 2, 8)
                            .addNonBreakable("zpm3:barbared_wire", 4, 1, 4)
                            .addNonBreakable("zpm3:chain_link",    4, 4, 16)
                            .addNonBreakable("minecraft:campfire", 3, 1)
                            .addNonBreakable("minecraft:torch",    4, 2, 6)
                            .addNonBreakable("minecraft:lantern",  2, 1, 2)
                            .addNonBreakable("minecraft:iron_bars", 2, 2, 8)
            )
            .commonGroup("survival", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("minecraft:cooked_beef",   5, 1, 4)
                            .addNonBreakable("minecraft:cooked_mutton",  4, 1, 4)
                            .addNonBreakable("minecraft:cooked_chicken", 4, 1, 4)
                            .addNonBreakable("minecraft:cooked_rabbit",  3, 1, 3)
                            .addNonBreakable("zpm3:water",                5, 1, 3)
                            .addNonBreakable("zpm3:chocolate",            3, 1, 3)
            )
            .bonusGroup("military_bonus", 0.008f, (g) -> g
                    .addNonBreakable("zpm3:_usp",       3, 16, 32)
                    .addNonBreakable("zpm3:_uzi",       3, 16, 32)
                    .addNonBreakable("zpm3:_mp5",       2, 16, 32)
                    .addNonBreakable("zpm3:_mosin",     2, 8, 16)
                    .addNonBreakable("zpm3:sandbag",     3, 8, 16)
                    .addNonBreakable("minecraft:iron_ingot", 2, 1, 3)
                    .addBreakable("minecraft:diamond_sword", 1, 0.15f, 0.30f))
            .build(new ZPLootTable.RollRules(0.85f, 1, 2, ZPRandomization.power(2.0f)));

    // ====================================================

    public static final ZPLootTable loot_submarine = ZPLootTable.builder("loot_submarine")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("radiation_protection", 22,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(8.0f)), (g) -> g
                            .addBreakable("zpm3:radiation_costume_helmet",      15, 0.15f, 0.6f, ZPRandomization.power(1.25f))
                            .addBreakable("zpm3:radiation_costume_chestplate",  15, 0.15f, 0.6f, ZPRandomization.power(1.25f))
                            .addBreakable("zpm3:radiation_costume_leggings",    15, 0.15f, 0.6f, ZPRandomization.power(1.25f))
                            .addBreakable("zpm3:radiation_costume_boots",       15, 0.15f, 0.6f, ZPRandomization.power(1.25f))
                            .addBreakable("zpm3:oxygen",                        5, 0.15f, 0.50f))
            .commonGroup("firearms", 18, (g) -> g
                            .addBreakable("zpm3:usp",      4, 0.65f, 0.90f)
                            .addBreakable("zpm3:colt",     4, 0.65f, 0.90f)
                            .addBreakable("zpm3:uzi",      3, 0.55f, 0.85f)
                            .addBreakable("zpm3:mp5",      2, 0.45f, 0.75f)
                            .addBreakable("zpm3:shotgun",  3, 0.50f, 0.80f)
                            .addBreakable("zpm3:mosin",    2, 0.10f, 0.40f)
                            .addBreakable("zpm3:akm",      2, 0.05f, 0.25f)
                            .addBreakable("zpm3:m16",      2, 0.05f, 0.25f))
            .commonGroup("ammunition", 22,
                    new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_usp",        4, 8, 24, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_colt",       4, 8, 24, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_uzi",        4, 8, 24, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_deagle",     2, 4, 12, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_mp5",        3, 8, 24, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_shotgun",    4, 4, 16, ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_mosin",      2, 4, 16, ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_akm",        3, 8, 24, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_m16",        3, 8, 24, ZPRandomization.power(1.5f)))
            .commonGroup("medical", 15, (g) -> g
                            .addNonBreakable("zpm3:radiation_protection_pill", 3, 1)
                            .addNonBreakable("zpm3:zplague_syringe",           2, 1)
                            .addNonBreakable("zpm3:morphine_syringe",          4, 1)
                            .addNonBreakable("zpm3:anti_headache_pill",          3, 1)
                            .addNonBreakable("zpm3:anti_poison_pill",          3, 1)
                            .addNonBreakable("zpm3:anti_hunger_pill",          3, 1)
                            .addNonBreakable("zpm3:whiskey_medicine",          3, 1)
                            .addNonBreakable("zpm3:aid_kit",                    2, 1)
                            .addBreakable("zpm3:bandage",                       6, 0.50f, 1.0f)
                            .addNonBreakable("zpm3:drugs",                      3, 1, 2))
            .commonGroup("submarine_equipment", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.75f)), (g) -> g
                            .addNonBreakable("zpm3:cement_material",  4, 1)
                            .addBreakable("zpm3:metal_cutters",        3, 0.35f, 0.70f)
                            .addBreakable("zpm3:wrench",               4, 0.35f, 0.75f)
                            .addNonBreakable("zpm3:plate",             4, 1, 3)
                            .addBreakable("zpm3:hatchet",              3, 0.35f, 0.70f)
                            .addBreakable("zpm3:sledgehammer",         2, 0.25f, 0.60f)
                            .addNonBreakable("zpm3:barbared_wire",     3, 1, 4)
                            .addNonBreakable("zpm3:chain_link",        4, 4, 16)
                            .addNonBreakable("zpm3:wall_lamp",         2, 1)
                            .addNonBreakable("zpm3:block_lamp",        2, 1)
                            .addNonBreakable("minecraft:iron_bars",    3, 2, 8)
                            .addNonBreakable("minecraft:chain",        3, 2, 8))
            .commonGroup("submarine_survival", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:minecake",             5, 2, 4)
                            .addNonBreakable("zpm3:water",                 6, 1, 3)
                            .addNonBreakable("minecraft:dried_kelp",      5, 2, 8)
                            .addNonBreakable("minecraft:kelp",            4, 2, 8)
                            .addNonBreakable("minecraft:cooked_cod",      3, 2, 6)
                            .addNonBreakable("minecraft:cooked_salmon",   3, 2, 6)
                            .addNonBreakable("minecraft:glass_bottle",    4, 1, 4)
                            .addNonBreakable("minecraft:torch",            3, 2, 6)
                            .addNonBreakable("minecraft:lantern",          2, 1, 2))
            .commonGroup("submarine_misc", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:compass",          3, 1)
                            .addNonBreakable("minecraft:clock",             2, 1)
                            .addNonBreakable("minecraft:map",               3, 1)
                            .addNonBreakable("minecraft:nautilus_shell",    3, 1, 2)
                            .addNonBreakable("minecraft:prismarine_shard",  3, 2, 8)
                            .addNonBreakable("minecraft:prismarine_crystals", 2, 1, 4)
                            .addNonBreakable("minecraft:sea_lantern",       2, 1, 2)
                            .addNonBreakable("minecraft:glass",              3, 2, 8))
            .bonusGroup("submarine_bonus", new ZPLootTable.RollRules(
                    0.01f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addBreakable("minecraft:trident",       4, 0.20f, 0.45f)
                    .addNonBreakable("zpm3:_deagle",         6, 16, 32)
                    .addNonBreakable("zpm3:_shotgun",        6, 8, 16)
                    .addNonBreakable("zpm3:_mosin",          5, 8, 16)
                    .addNonBreakable("minecraft:nautilus_shell", 2, 1, 4)
                    .addNonBreakable("zpm3:radiation_protection_pill", 1, 1))
            .build(new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.5f)));

    // ====================================================

    public static final ZPLootTable loot_ammunition_tier3 = ZPLootTable.builder("loot_ammunition_tier3")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("pistol_ammunition", 25,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_makarov",          3, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_handmade_pistol", 2, 12, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_colt",            4, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_m1911",           4, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_usp",             4, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_deagle",          2, 12, 32, ZPRandomization.power(1.5f))
            )
            .commonGroup("smg_ammunition", 18,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_uzi",       5, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_mp5",       4, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_machinegun", 2, 16, 32, ZPRandomization.power(1.5f))
            )
            .commonGroup("rifle_ammunition", 18,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_akm", 3, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_m16", 3, 16, 32, ZPRandomization.power(1.5f))
            )
            .commonGroup("special_ammunition", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_shotgun", 4, 8, 16, ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_mosin",   3, 8, 16, ZPRandomization.power(1.25f))
            )
            .commonGroup("arrows", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:arrow", 6, 16, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:tipped_arrow", 4, 8, 16, ZPRandomization.power(1.5f),
                                    nbt -> nbt.add("Potion", "minecraft:poison"))
                            .addNonBreakable("minecraft:tipped_arrow", 3, 8, 16, ZPRandomization.power(1.5f),
                                    nbt -> nbt.add("Potion", "minecraft:slowness"))
                            .addNonBreakable("minecraft:tipped_arrow", 3, 8, 16, ZPRandomization.power(1.5f),
                                    nbt -> nbt.add("Potion", "minecraft:weakness"))
                            .addNonBreakable("minecraft:tipped_arrow", 2, 8, 16, ZPRandomization.power(1.5f),
                                    nbt -> nbt.add("Potion", "minecraft:harming"))
            )
            .bonusGroup("ammunition_bonus", new ZPLootTable.RollRules(
                    0.008f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("zpm3:_m1911",    3, 24, 32)
                    .addNonBreakable("zpm3:_usp",      3, 24, 32)
                    .addNonBreakable("zpm3:_uzi",      3, 24, 32)
                    .addNonBreakable("zpm3:_mp5",      2, 24, 32)
                    .addNonBreakable("zpm3:_akm",      2, 24, 32)
                    .addNonBreakable("zpm3:_m16",      2, 24, 32)
                    .addNonBreakable("zpm3:_machinegun", 1, 24, 32)
                    .addNonBreakable("zpm3:_shotgun",  2, 12, 16)
                    .addNonBreakable("zpm3:_mosin",    1, 12, 16)
            )
            .build(new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(2.0f)));

    // ====================================================

    public static final ZPLootTable loot_ammunition_tier2 = ZPLootTable.builder("loot_ammunition_tier2")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("pistol_ammunition", 28,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_makarov",          4, 12, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_handmade_pistol", 2, 6, 16, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_colt",            5, 12, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_m1911",           5, 12, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_usp",             5, 12, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_deagle",          2, 6, 16, ZPRandomization.power(1.5f))
            )
            .commonGroup("smg_ammunition", 20,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_uzi", 5, 12, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_mp5", 4, 12, 32, ZPRandomization.power(1.5f))
            )
            .commonGroup("rifle_ammunition", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_akm",   4, 8, 24, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_m16",   3, 8, 24, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_mosin", 3, 6, 16, ZPRandomization.power(1.5f))
            )
            .commonGroup("shotgun_ammunition", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.25f)), (g) -> g
                            .addNonBreakable("zpm3:_shotgun", 5, 4, 16, ZPRandomization.power(1.25f))
            )
            .commonGroup("arrows", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:arrow", 7, 12, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:tipped_arrow", 4, 4, 12, ZPRandomization.power(1.5f),
                                    nbt -> nbt.add("Potion", "minecraft:poison"))
                            .addNonBreakable("minecraft:tipped_arrow", 3, 4, 12, ZPRandomization.power(1.5f),
                                    nbt -> nbt.add("Potion", "minecraft:slowness"))
                            .addNonBreakable("minecraft:tipped_arrow", 3, 4, 12, ZPRandomization.power(1.5f),
                                    nbt -> nbt.add("Potion", "minecraft:weakness"))
                            .addNonBreakable("minecraft:tipped_arrow", 2, 4, 8, ZPRandomization.power(1.5f),
                                    nbt -> nbt.add("Potion", "minecraft:harming"))
            )
            .commonGroup("special_ammunition", 6,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_deagle", 3, 8, 16, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_shotgun", 4, 6, 16, ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_mosin",   2, 6, 16, ZPRandomization.power(1.25f))
            )
            .bonusGroup("ammunition_bonus", new ZPLootTable.RollRules(
                    0.006f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("zpm3:_akm",     3, 16, 24)
                    .addNonBreakable("zpm3:_m16",     3, 16, 24)
                    .addNonBreakable("zpm3:_mp5",     3, 16, 32)
                    .addNonBreakable("zpm3:_shotgun", 2, 8, 16)
                    .addNonBreakable("zpm3:_mosin",   2, 8, 16)
                    .addNonBreakable("minecraft:tipped_arrow", 2, 8, 16,
                            nbt -> nbt.add("Potion", "minecraft:poison")))
            .build(new ZPLootTable.RollRules(1.0f, 1, 4, ZPRandomization.power(2.0f)));

    // ====================================================

    public static final ZPLootTable loot_ammunition_tier1 = ZPLootTable.builder("loot_ammunition_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("pistol_ammunition", 35,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_makarov",          5, 8, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_handmade_pistol", 4, 4, 16, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_colt",            5, 6, 24, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_m1911",           5, 8, 32, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_usp",             4, 6, 24, ZPRandomization.power(1.5f))
            )
            .commonGroup("smg_ammunition", 18,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_uzi", 6, 6, 24, ZPRandomization.power(1.5f))
            )
            .commonGroup("special_ammunition", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_deagle", 3, 2, 8, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_shotgun", 4, 1, 4, ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_mosin",   2, 1, 4, ZPRandomization.power(1.25f))
            )
            .commonGroup("arrows", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:arrow", 8, 8, 28, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:tipped_arrow", 3, 2, 8, ZPRandomization.power(1.5f),
                                    nbt -> nbt.add("Potion", "minecraft:poison"))
                            .addNonBreakable("minecraft:tipped_arrow", 2, 4, 16, ZPRandomization.power(1.5f),
                                    nbt -> nbt.add("Potion", "minecraft:slowness"))
                            .addNonBreakable("minecraft:tipped_arrow", 1, 8, 16, ZPRandomization.power(1.5f),
                                    nbt -> nbt.add("Potion", "minecraft:weakness"))
            )
            .bonusGroup("ammunition_bonus", new ZPLootTable.RollRules(
                    0.006f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("zpm3:_m1911", 3, 16, 32)
                    .addNonBreakable("zpm3:_usp", 3, 12, 24)
                    .addNonBreakable("zpm3:_uzi", 3, 12, 24)
                    .addNonBreakable("zpm3:_shotgun", 2, 2, 4)
                    .addNonBreakable("zpm3:_mosin", 1, 2, 4))
            .build(new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)));

    // ====================================================

    public static final ZPLootTable loot_hospital_tier2 = ZPLootTable.builder("loot_hospital_tier2")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension("zpm3:loot_pharmacy_tier2",
                            new ZPLootTable.RollRules(0.10f, 1, 1, ZPRandomization.uniform())),
                    new ZPLootTable.TableExtension("zpm3:loot_hospital_tier1",
                            new ZPLootTable.RollRules(0.50f, 1, 1, ZPRandomization.uniform()))
            )
            .commonGroup("medical", 45,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(3.25f)), (g) -> g
                            .addNonBreakable("zpm3:morphine_syringe",    6, 1)
                            .addNonBreakable("zpm3:anti_hunger_pill",    5, 1)
                            .addNonBreakable("zpm3:anti_headache_pill",    7, 1)
                            .addNonBreakable("zpm3:zplague_syringe",     3, 1)
                            .addNonBreakable("zpm3:antibiotics_syringe", 3, 1)
                            .addNonBreakable("zpm3:adrenaline_syringe",   3, 1)
                            .addBreakable("zpm3:aid_kit",                 3, 0.25f, 1.0f, ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:bandage",               16, 1, 3)
                            .addNonBreakable("zpm3:splint",                4, 1)
                            .addNonBreakable("zpm3:vitamin_pill", 1, 1)
            )
            .commonGroup("hospital_supplies", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.75f)), (g) -> g
                            .addNonBreakable("minecraft:glass_bottle",  7, 1, 4)
                            .addNonBreakable("minecraft:paper",          5, 2, 8)
                            .addNonBreakable("minecraft:string",         4, 2, 6)
                            .addNonBreakable("minecraft:glass",          4, 2, 8)
                            .addNonBreakable("minecraft:water_bucket",   2, 1)
                            .addNonBreakable("minecraft:bucket",         3, 1)
            )
            .commonGroup("hospital_utility", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:torch",        4, 2, 6)
                            .addNonBreakable("minecraft:lantern",      2, 1, 2)
                            .addNonBreakable("minecraft:bed",          2, 1)
                            .addNonBreakable("minecraft:chest",        1, 1)
                            .addNonBreakable("minecraft:barrel",       2, 1)
            )
            .commonGroup("materials", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:chisel_material", 3, 1)
                            .addNonBreakable("zpm3:scrap_material",  4, 1, 4, ZPRandomization.power(2.0f))
                            .addNonBreakable("minecraft:iron_nugget", 3, 1, 4, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:iron_ingot",  2, 1, 2, ZPRandomization.power(1.5f))
            )
            .commonGroup("hospital_food", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("minecraft:mushroom_stew",  4, 1)
                            .addNonBreakable("minecraft:beetroot_soup",  4, 1)
                            .addNonBreakable("minecraft:rabbit_stew",    2, 1)
                            .addNonBreakable("zpm3:water",                6, 1, 3)
            )
            .commonGroup("radiation_protection", 3,
                    new ZPLootTable.RollRules(1.0f, 1, 1, ZPRandomization.power(4.0f)), (g) -> g
                            .addNonBreakable("zpm3:radiation_protection_pill", 2, 1)
            )
            .bonusGroup("hospital_tier2_bonus", new ZPLootTable.RollRules(0.006f, 1, 2, ZPRandomization.power(4.0f)), (g) -> g
                    .addNonBreakable("zpm3:vitamin_pill", 2, 1)
                    .addNonBreakable("zpm3:morphine_syringe",      2, 1)
                    .addNonBreakable("zpm3:antibiotics_syringe",   2, 1)
                    .addNonBreakable("zpm3:adrenaline_syringe",    2, 1)
                    .addNonBreakable("zpm3:zplague_syringe",       1, 1)
                    .addNonBreakable("zpm3:aid_kit",               2, 1)
                    .addNonBreakable("zpm3:radiation_protection_pill", 1, 1)
            )
            .build(new ZPLootTable.RollRules(0.85f, 1, 3, ZPRandomization.power(2.5f)));

    // ====================================================

    public static final ZPLootTable loot_hospital_tier1 = ZPLootTable.builder("loot_hospital_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension("zpm3:loot_pharmacy_tier1",
                            new ZPLootTable.RollRules(0.25f, 1, 1, ZPRandomization.uniform()))
            )
            .commonGroup("medical", 40, (g) -> g
                            .addBreakable("zpm3:bandage", 16, 0.15f, 0.50f)
                            .addNonBreakable("zpm3:splint", 8, 1)
                            .addNonBreakable("zpm3:antibiotics_syringe", 2, 1)
                            .addNonBreakable("zpm3:anti_headache_pill",    7, 1)
                            .addNonBreakable("zpm3:vodka_medicine", 4, 1)
                            .addNonBreakable("zpm3:better_vision_pill", 3, 1)
                            .addNonBreakable("zpm3:morphine_syringe", 7, 1)
                            .addBreakable("zpm3:aid_kit", 1, 0.0f, 1.0f, ZPRandomization.power(1.5f))
            )
            .commonGroup("hospital_food", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("minecraft:mushroom_stew", 4, 1)
                            .addNonBreakable("minecraft:beetroot_soup", 4, 1)
                            .addNonBreakable("minecraft:rabbit_stew", 2, 1)
                            .addNonBreakable("minecraft:pufferfish", 1, 1)
            )
            .commonGroup("hospital_tools", 10, (g) -> g
                    .addBreakable("zpm3:cleaver", 4, 0.30f, 0.70f)
                    .addBreakable("minecraft:stone_axe", 4, 0.35f, 0.75f)
                    .addBreakable("minecraft:iron_axe", 2, 0.20f, 0.55f)
                    .addBreakable("minecraft:shears", 2, 0.20f, 0.55f)
            )
            .commonGroup("clothing", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addBreakable("minecraft:leather_helmet",    3, 0.20f, 0.50f)
                            .addBreakable("minecraft:leather_chestplate", 3, 0.20f, 0.50f)
                            .addBreakable("minecraft:leather_leggings",   3, 0.20f, 0.50f)
                            .addBreakable("minecraft:leather_boots",      3, 0.20f, 0.50f)
            )
            .commonGroup("materials", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:chisel_material", 3, 1)
                            .addNonBreakable("zpm3:scrap_material",  4, 1, 4, ZPRandomization.power(2.0f))
                            .addNonBreakable("minecraft:glass_bottle", 6, 1, 4)
                            .addNonBreakable("minecraft:glass",         3, 2, 6)
                            .addNonBreakable("minecraft:paper",         4, 2, 8)
            )
            .commonGroup("hospital_utility", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:water_bucket", 2, 1)
                            .addNonBreakable("minecraft:bucket",       3, 1)
                            .addNonBreakable("minecraft:torch",       4, 1, 4)
                            .addNonBreakable("minecraft:lantern",     2, 1)
                            .addNonBreakable("minecraft:bed",         2, 1)
            )
            .bonusGroup("hospital_bonus", 0.006f, (g) -> g
                    .addNonBreakable("zpm3:morphine_syringe",      2, 1)
                    .addNonBreakable("zpm3:antibiotics_syringe",   2, 1)
                    .addNonBreakable("zpm3:aid_kit",               1, 1)
                    .addNonBreakable("zpm3:better_vision_pill",    2, 1)
                    .addNonBreakable("zpm3:chisel_material",       2, 1)
            )
            .build(new ZPLootTable.RollRules(0.75f, 1, 2, ZPRandomization.power(2.5f)));

    // ====================================================

    public static final ZPLootTable loot_pharmacy_tier2 = ZPLootTable.builder("loot_pharmacy_tier2")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension("zpm3:loot_city_tier1",
                            new ZPLootTable.RollRules(0.70f, 1, 1, ZPRandomization.uniform()))
            )
            .commonGroup("medicine", 35,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:anti_headache_pill", 6, 1)
                            .addNonBreakable("zpm3:anti_hunger_pill", 5, 1)
                            .addNonBreakable("zpm3:anti_poison_pill", 5, 1)
                            .addBreakable("zpm3:bandage", 8, 0.20f, 0.70f)
                            .addNonBreakable("zpm3:splint", 6, 1)
                            .addNonBreakable("zpm3:vodka_medicine", 4, 1)
                            .addNonBreakable("zpm3:antibiotics_syringe", 3, 1)
            )
            .commonGroup("advanced_medicine", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(4.0f)), (g) -> g
                            .addNonBreakable("zpm3:better_vision_pill", 4, 1)
                            .addNonBreakable("zpm3:vitamin_pill", 2, 1)
                            .addNonBreakable("zpm3:drugs", 2, 1)
            )
            .commonGroup("pharmacy_utility", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:glass_bottle", 8, 1, 4)
                            .addNonBreakable("minecraft:paper", 6, 2, 10)
                            .addNonBreakable("minecraft:string", 5, 2, 8)
                            .addNonBreakable("minecraft:glass", 4, 2, 8)
                            .addNonBreakable("minecraft:sugar", 4, 1, 4)
                            .addNonBreakable("minecraft:leather", 3, 1, 3)
                            .addNonBreakable("minecraft:water_bucket", 2, 1)
                            .addNonBreakable("minecraft:torch", 3, 1, 4)
            )
            .commonGroup("materials", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:iron_nugget", 4, 1, 5, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:iron_ingot", 2, 1, 2, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:coal", 3, 1, 4, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:scrap_material", 4, 1, 4, ZPRandomization.power(2.0f))
            )
            .commonGroup("containers", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:bucket", 3, 1)
                            .addNonBreakable("minecraft:cauldron", 1, 1)
                            .addNonBreakable("minecraft:chest", 1, 1)
            )
            .bonusGroup("pharmacy_bonus", 0.008f, (g) -> g
                    .addNonBreakable("zpm3:antibiotics_syringe", 3, 1)
                    .addNonBreakable("zpm3:better_vision_pill", 2, 1)
                    .addNonBreakable("zpm3:vitamin_pill", 2, 1)
                    .addNonBreakable("zpm3:aid_kit", 1, 1)
                    .addNonBreakable("zpm3:bandage", 4, 1, 3)
            )
            .build(new ZPLootTable.RollRules(
                    0.75f, 1, 3, ZPRandomization.power(2.5f)));

    // ====================================================

    public static final ZPLootTable loot_pharmacy_tier1 = ZPLootTable.builder("loot_pharmacy_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension("zpm3:loot_city_tier1", new ZPLootTable.RollRules(0.30f, 1, 1, ZPRandomization.uniform()))
            )
            .commonGroup("medical_supplies", 50, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(4.0f)), (g) -> g
                    .addBreakable("zpm3:bandage", 25, 0.10f, 0.70f)
                    .addNonBreakable("zpm3:splint", 15, 1)
                    .addNonBreakable("zpm3:anti_headache_pill", 6, 1)
                    .addNonBreakable("zpm3:anti_poison_pill", 4, 1)
                    .addNonBreakable("zpm3:vodka_medicine", 1, 1)
            )
            .commonGroup("medicine_rare", 8, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.5f)), (g) -> g
                    .addNonBreakable("zpm3:anti_hunger_pill", 4, 1)
                    .addNonBreakable("zpm3:antibiotics_syringe", 1, 1)
            )
            .commonGroup("pharmacy_utility", 12, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                    .addNonBreakable("minecraft:glass_bottle", 6, 1, 3)
                    .addNonBreakable("minecraft:paper", 5, 2, 8)
                    .addNonBreakable("minecraft:string", 4, 2, 6)
                    .addNonBreakable("minecraft:glass", 3, 2, 6)
                    .addNonBreakable("minecraft:leather", 3, 1, 3)
                    .addNonBreakable("minecraft:sugar", 4, 1, 4)
                    .addNonBreakable("minecraft:spider_eye", 2, 1, 2)
            )
            .commonGroup("basic_supplies", 10, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                    .addNonBreakable("minecraft:water", 5, 1, 2)
                    .addNonBreakable("minecraft:coal", 3, 1, 3)
                    .addNonBreakable("minecraft:charcoal", 3, 1, 3)
            )
            .bonusGroup("pharmacy_bonus", 0.008f, (g) -> g
                    .addNonBreakable("zpm3:antibiotics_syringe", 1, 1)
                    .addNonBreakable("zpm3:anti_hunger_pill", 3, 1)
                    .addNonBreakable("zpm3:anti_poison_pill", 5, 1)
                    .addNonBreakable("zpm3:anti_headache_pill", 5, 1)
                    .addBreakable("zpm3:bandage", 6, 0.40f, 0.70f))
            .build(new ZPLootTable.RollRules(0.65f, 1, 2, ZPRandomization.power(3.5f)));

    // ====================================================

    public static final ZPLootTable loot_fishing_tier2 = ZPLootTable.builder("loot_fishing_tier2")
            .extendBy(new ZPLootTable.TableExtension("zpm3:loot_fishing_tier1", new ZPLootTable.RollRules(0.10f, 1, 1, ZPRandomization.uniform())),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_village_tier3"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("aqualung", 20, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(8.0f)), (g) -> g
                            .addBreakable("zpm3:aqualung_costume_helmet", 3, 0.30f, 0.70f, ZPRandomization.power(2.0f))
                            .addBreakable("zpm3:aqualung_costume_chestplate", 3, 0.30f, 0.70f, ZPRandomization.power(2.0f))
                            .addBreakable("zpm3:aqualung_costume_leggings", 3, 0.30f, 0.70f, ZPRandomization.power(2.0f))
                            .addBreakable("zpm3:aqualung_costume_boots", 3, 0.30f, 0.70f, ZPRandomization.power(2.0f))
                            .addBreakable("zpm3:oxygen", 4, 0.30f, 0.70f, ZPRandomization.power(2.0f))
            )
            .commonGroup("fishing", 38, new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(1.75f)), (g) -> g
                            .addBreakable("minecraft:fishing_rod", 8, 0.35f, 0.80f)
                            .addNonBreakable("minecraft:cod", 8, 1, 4)
                            .addNonBreakable("minecraft:salmon", 8, 1, 4)
                            .addNonBreakable("minecraft:tropical_fish", 4, 1, 4)
                            .addNonBreakable("minecraft:pufferfish", 3, 1, 2)
                            .addNonBreakable("minecraft:kelp", 7, 4, 12)
                            .addNonBreakable("minecraft:dried_kelp", 5, 2, 8)
                            .addNonBreakable("minecraft:water_bucket", 4, 1)
                            .addNonBreakable("minecraft:bucket", 5, 1)
                            .addNonBreakable("minecraft:glass", 5, 4, 12)
                            .addNonBreakable("minecraft:string", 6, 4, 12)
            )
            .commonGroup("marine_materials", 18, new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:prismarine_shard", 5, 2, 8)
                            .addNonBreakable("minecraft:prismarine_crystals", 3, 1, 5)
                            .addNonBreakable("minecraft:sea_pickle", 5, 1, 4)
                            .addNonBreakable("minecraft:dead_tube_coral", 3, 1, 4)
                            .addNonBreakable("minecraft:dead_brain_coral", 3, 1, 4)
                            .addNonBreakable("minecraft:dead_bubble_coral", 3, 1, 4)
                            .addNonBreakable("minecraft:dead_fire_coral", 3, 1, 4)
                            .addNonBreakable("minecraft:dead_horn_coral", 3, 1, 4)
                            .addNonBreakable("minecraft:nautilus_shell", 2, 1)
            )
            .commonGroup("survival", 22, new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:water", 7, 1, 4)
                            .addNonBreakable("minecraft:lantern", 4, 1, 2)
                            .addNonBreakable("zpm3:lantern2", 6, 1, 5)
                            .addNonBreakable("minecraft:torch", 6, 2, 8)
                            .addNonBreakable("zpm3:torch2", 3, 1, 3)
                            .addNonBreakable("minecraft:scaffolding", 3, 2, 8)
                            .addNonBreakable("minecraft:lead", 4, 1)
                            .addNonBreakable("minecraft:campfire", 2, 1)
            )
            .commonGroup("utility", 14, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:compass", 3, 1)
                            .addNonBreakable("minecraft:map", 3, 1)
                            .addNonBreakable("minecraft:clock", 2, 1)
                            .addNonBreakable("minecraft:oak_boat", 3, 1)
                            .addNonBreakable("minecraft:chest", 2, 1)
                            .addNonBreakable("minecraft:barrel", 2, 1)
                            .addNonBreakable("minecraft:lead", 3, 1)
            )
            .commonGroup("materials", 12, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:string", 6, 2, 10)
                            .addNonBreakable("minecraft:iron_nugget", 4, 1, 6, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:iron_ingot", 2, 1, 3,ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:copper_ingot", 3, 1, 4, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:coal", 3, 1, 5, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:scrap_material", 4, 1, 5, ZPRandomization.power(2.0f))
            )
            .bonusGroup("fishing_tier2_bonus", new ZPLootTable.RollRules(
                            0.006f, 1, 2, ZPRandomization.uniform()), (g) -> g
                            .addNonBreakable("minecraft:nautilus_shell", 3, 1, 2)
                            .addNonBreakable("minecraft:prismarine_shard", 5, 4, 12)
                            .addNonBreakable("minecraft:prismarine_crystals", 3, 2, 8)
                            .addNonBreakable("minecraft:gold_nugget", 4, 2, 6)
                            .addNonBreakable("minecraft:emerald", 1, 1)
                            .addNonBreakable("minecraft:sea_lantern", 2, 1, 2)
            )
            .build(new ZPLootTable.RollRules(0.85f, 1, 2, ZPRandomization.power(2.5f)));

    // ====================================================

    public static final ZPLootTable loot_fishing_tier1 = ZPLootTable.builder("loot_fishing_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_village_tier2")
            )
            .commonGroup("aqualung", 12, (g) -> g
                            .addBreakable("zpm3:aqualung_costume_helmet",      2, 0.05f, 0.30f)
                            .addBreakable("zpm3:aqualung_costume_chestplate",  2, 0.05f, 0.30f)
                            .addBreakable("zpm3:aqualung_costume_leggings",    2, 0.05f, 0.30f)
                            .addBreakable("zpm3:aqualung_costume_boots",       2, 0.05f, 0.30f)
                            .addBreakable("zpm3:oxygen",                       3, 0.05f, 0.30f)
            )
            .commonGroup("fishing", 35,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.75f)), (g) -> g
                            .addBreakable("minecraft:fishing_rod", 8, 0.20f, 0.70f)
                            .addNonBreakable("minecraft:cod",       8, 1, 3)
                            .addNonBreakable("minecraft:salmon",    7, 1, 3)
                            .addNonBreakable("minecraft:tropical_fish", 3, 1, 2)
                            .addNonBreakable("minecraft:pufferfish",   2, 1)
                            .addNonBreakable("minecraft:kelp",          5, 2, 8)
                            .addNonBreakable("minecraft:dried_kelp",    4, 1, 5)
                            .addNonBreakable("minecraft:water_bucket",   3, 1)
                            .addNonBreakable("minecraft:bucket",        4, 1)
            )
            .commonGroup("survival", 25,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:water",             7, 1, 3)
                            .addNonBreakable("minecraft:lantern",      5, 1, 2)
                            .addNonBreakable("zpm3:lantern2",           5, 1, 4)
                            .addNonBreakable("minecraft:torch",        6, 2, 6)
                            .addNonBreakable("zpm3:torch2",             3, 1, 2)
                            .addNonBreakable("minecraft:glass",         4, 2, 6)
                            .addNonBreakable("minecraft:scaffolding",   3, 2, 8)
                            .addNonBreakable("minecraft:lead",          3, 1)
            )
            .commonGroup("utility", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:compass",       3, 1)
                            .addNonBreakable("minecraft:map",           3, 1)
                            .addNonBreakable("minecraft:clock",         1, 1)
                            .addNonBreakable("minecraft:glass",         5, 2, 6)
                            .addNonBreakable("minecraft:oak_boat",      2, 1)
                            .addNonBreakable("minecraft:string",        5, 2, 8)
            )
            .commonGroup("materials", 10,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:string",       6, 2, 8)
                            .addNonBreakable("minecraft:iron_nugget",  4, 1, 5, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:iron_ingot",   2, 1, 2, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:coal",         3, 1, 4, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:scrap_material",    4, 1, 4, ZPRandomization.power(2.0f))
            )
            .bonusGroup("fishing_bonus", new ZPLootTable.RollRules(
                    0.008f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("minecraft:nautilus_shell",  2, 1)
                    .addNonBreakable("minecraft:prismarine_shard", 3, 1, 4)
                    .addNonBreakable("minecraft:gold_nugget",    3, 1, 5)
                    .addNonBreakable("minecraft:emerald",         1, 1)
            )
            .build(new ZPLootTable.RollRules(0.85f, 1, 2, ZPRandomization.power(3.0f)));

    // ====================================================

    public static final ZPLootTable loot_police_tier3 = ZPLootTable.builder("loot_police_tier3")
            .extendBy(
                    new ZPLootTable.TableExtension("zpm3:loot_police_tier2", new ZPLootTable.RollRules(0.50f, 1, 1, ZPRandomization.uniform())),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_city_tier3"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("firearms_pistols", 18, (g) -> g
                    .addBreakable("zpm3:makarov",  4, 0.70f, 0.95f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 8, ZPRandomization.power(2.0f)))
                    .addBreakable("zpm3:usp",      5, 0.65f, 0.90f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 10, ZPRandomization.power(2.0f)))
                    .addBreakable("zpm3:m1911",     4, 0.65f, 0.90f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 9, ZPRandomization.power(2.0f)))
                    .addBreakable("zpm3:colt",      4, 0.65f, 0.90f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 8, ZPRandomization.power(2.0f)))
                    .addBreakable("zpm3:handmade_pistol", 1, 0.50f, 0.80f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 3, ZPRandomization.power(2.0f))))
            .commonGroup("firearms_automatic", 7, (g) -> g
                    .addBreakable("zpm3:uzi", 4, 0.50f, 0.80f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 12, ZPRandomization.power(2.0f)))
                    .addBreakable("zpm3:mp5", 1, 0.05f, 0.25f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 10, ZPRandomization.power(1.5f))))
            .commonGroup("firearms_special", 8, (g) -> g
                    .addBreakable("zpm3:shotgun", 3, 0.05f, 0.20f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 6, ZPRandomization.power(1.5f)))
                    .addBreakable("zpm3:mosin", 1, 0.01f, 0.05f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 4, ZPRandomization.power(1.5f))))
            .commonGroup("ammunition", 35,
                    new ZPLootTable.RollRules(
                            1.0f, 2, 5, ZPRandomization.power(1.20f)), (g) -> g
                            .addNonBreakable("zpm3:_makarov",         5, 2, 16,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_usp",             6, 2, 20,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_m1911",           5, 2, 18,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_colt",            5, 2, 16,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_uzi",             6, 2, 24,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_shotgun",         4, 1, 6,
                                    ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_mp5",             3, 1, 8,
                                    ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_mosin",           2, 1, 4,
                                    ZPRandomization.power(1.5f)))
            .commonGroup("equipment", 10, (g) -> g
                    .addBreakable("minecraft:iron_helmet",      2, 0.50f, 0.80f)
                    .addBreakable("minecraft:iron_chestplate",  2, 0.50f, 0.80f)
                    .addBreakable("minecraft:iron_leggings",    2, 0.50f, 0.80f)
                    .addBreakable("minecraft:iron_boots",       2, 0.50f, 0.80f)
                    .addBreakable("minecraft:chainmail_helmet",     2, 0.60f, 0.85f)
                    .addBreakable("minecraft:chainmail_chestplate", 2, 0.60f, 0.85f)
                    .addBreakable("minecraft:chainmail_leggings",   2, 0.60f, 0.85f)
                    .addBreakable("minecraft:chainmail_boots",      2, 0.60f, 0.85f)
                    .addBreakable("minecraft:shield", 3, 0.50f, 0.85f))
            .commonGroup("tools", 7, (g) -> g
                    .addBreakable("minecraft:iron_axe",      3, 0.50f, 0.85f)
                    .addBreakable("minecraft:iron_pickaxe", 2, 0.45f, 0.80f)
                    .addBreakable("minecraft:iron_shovel",  2, 0.50f, 0.85f)
                    .addBreakable("minecraft:iron_sword",   2, 0.50f, 0.85f)
                    .addBreakable("zpm3:iron_club",          3, 0.50f, 0.85f))
            .commonGroup("survival", 8,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:drugs",           2, 1)
                            .addNonBreakable("zpm3:water",           5, 1, 3)
                            .addNonBreakable("zpm3:soda",            3, 1, 2)
                            .addNonBreakable("zpm3:mysterious_can",  3, 1, 2)
                            .addNonBreakable("zpm3:peaches",         3, 1, 3)
                            .addNonBreakable("zpm3:bean",             3, 1, 3)
                            .addBreakable("zpm3:bandage",             3, 0.25f, 0.70f)
                            .addNonBreakable("zpm3:splint",           2, 1))
            .commonGroup("materials", 7,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:iron_ingot",  4, 1, 4,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:iron_nugget", 5, 2, 8,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:gold_ingot",  1, 1)
                            .addNonBreakable("minecraft:gold_nugget", 3, 1, 5,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:scrap_material",   4, 1, 5,
                                    ZPRandomization.power(2.0f)))
            .commonGroup("utility", 6,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:compass",        2, 1)
                            .addNonBreakable("minecraft:clock",          2, 1)
                            .addNonBreakable("minecraft:map",            2, 1)
                            .addNonBreakable("minecraft:name_tag",       2, 1)
                            .addNonBreakable("minecraft:writable_book",  1, 1))
            .bonusGroup("police_tier3_bonus", new ZPLootTable.RollRules(
                    0.004f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("minecraft:gold_ingot",  2, 1, 2)
                    .addNonBreakable("minecraft:emerald",     2, 1, 2)
                    .addNonBreakable("minecraft:iron_ingot",  4, 1, 4)
                    .addNonBreakable("zpm3:_mp5",             3, 2, 8,
                            ZPRandomization.power(1.25f))
                    .addNonBreakable("zpm3:_shotgun",         3, 2, 6,
                            ZPRandomization.power(1.25f)))
            .build(new ZPLootTable.RollRules(
                    0.75f, 1, 3, ZPRandomization.power(2.0f)));

    // ====================================================

    public static final ZPLootTable loot_police_tier2 = ZPLootTable.builder("loot_police_tier2")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_city_tier2"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension(
                            "zpm3:loot_police_tier1",
                            new ZPLootTable.RollRules(
                                    0.50f, 1, 1, ZPRandomization.uniform()))
            )
            .commonGroup("firearms_pistols", 14, (g) -> g
                    .addBreakable("zpm3:colt", 3, 0.50f, 0.80f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 6, ZPRandomization.power(2.0f)))
                    .addBreakable("zpm3:usp", 4, 0.45f, 0.75f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 8, ZPRandomization.power(2.0f)))
                    .addBreakable("zpm3:uzi", 3, 0.25f, 0.50f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 10, ZPRandomization.power(2.0f))))
            .commonGroup("firearms_special", 5, (g) -> g
                    .addBreakable("zpm3:shotgun", 1, 0.01f, 0.05f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 4, ZPRandomization.power(1.5f)))
                    .addBreakable("zpm3:mp5", 1, 0.01f, 0.08f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 8, ZPRandomization.power(1.5f))))
            .commonGroup("ammunition", 30,
                    new ZPLootTable.RollRules(
                            1.0f, 2, 4, ZPRandomization.power(1.25f)), (g) -> g
                            .addNonBreakable("zpm3:_usp",             5, 1, 16,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_colt",            4, 1, 14,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_uzi",             5, 1, 18,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:_shotgun",         3, 1, 4,
                                    ZPRandomization.power(1.25f))
                            .addNonBreakable("zpm3:_mp5",             2, 1, 8,
                                    ZPRandomization.power(1.25f)))
            .commonGroup("equipment", 10, (g) -> g
                    .addBreakable("minecraft:iron_helmet",      2, 0.20f, 0.50f)
                    .addBreakable("minecraft:iron_chestplate",  2, 0.20f, 0.50f)
                    .addBreakable("minecraft:iron_leggings",    2, 0.20f, 0.50f)
                    .addBreakable("minecraft:iron_boots",       2, 0.20f, 0.50f)
                    .addBreakable("minecraft:chainmail_helmet",     2, 0.35f, 0.70f)
                    .addBreakable("minecraft:chainmail_chestplate", 2, 0.35f, 0.70f)
                    .addBreakable("minecraft:chainmail_leggings",   2, 0.35f, 0.70f)
                    .addBreakable("minecraft:chainmail_boots",      2, 0.35f, 0.70f)
                    .addBreakable("minecraft:shield", 3, 0.30f, 0.70f))
            .commonGroup("police_tools", 8, (g) -> g
                    .addBreakable("zpm3:iron_club",    4, 0.40f, 0.80f))
            .commonGroup("utility", 8,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:compass",   3, 1)
                            .addNonBreakable("minecraft:clock",     2, 1)
                            .addNonBreakable("minecraft:map",       2, 1)
                            .addNonBreakable("minecraft:name_tag",  2, 1)
                            .addNonBreakable("minecraft:writable_book", 2, 1)
                            .addNonBreakable("minecraft:torch",     5, 2, 6)
                            .addNonBreakable("minecraft:lantern",   2, 1, 2))
            .commonGroup("materials", 8,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:iron_ingot",  3, 1, 3,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:iron_nugget", 5, 1, 6,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:gold_ingot",  1, 1)
                            .addNonBreakable("minecraft:coal",        3, 1, 4,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:scrap_material",   4, 1, 4,
                                    ZPRandomization.power(2.0f)))
            .commonGroup("survival", 8,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:drugs",           2, 1)
                            .addNonBreakable("zpm3:water",           5, 1, 2)
                            .addNonBreakable("zpm3:soda",            3, 1, 2)
                            .addNonBreakable("zpm3:mysterious_can",  3, 1, 2)
                            .addNonBreakable("zpm3:chocolate",       3, 1, 4)
                            .addBreakable("zpm3:bandage",             3, 0.15f, 0.60f)
                            .addNonBreakable("zpm3:splint",           2, 1)
                            .addBreakable("zpm3:matches",             3, 0.05f, 0.25f))
            .bonusGroup("police_bonus", new ZPLootTable.RollRules(
                    0.006f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("minecraft:iron_ingot",  3, 1, 3)
                    .addNonBreakable("minecraft:gold_ingot",  1, 1)
                    .addNonBreakable("minecraft:emerald",    1, 1)
                    .addNonBreakable("zpm3:_usp",            2, 1, 8,
                            ZPRandomization.power(1.5f))
                    .addNonBreakable("zpm3:_uzi",            2, 1, 10,
                            ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:shield",     1, 1))
            .build(new ZPLootTable.RollRules(
                    0.85f, 1, 2, ZPRandomization.power(2.0f)));

    // ====================================================

    public static final ZPLootTable loot_police_tier1 = ZPLootTable.builder("loot_police_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_city_tier1"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("firearms", 15, (g) -> g
                    .addBreakable("zpm3:makarov", 3, 0.45f, 0.75f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 6, ZPRandomization.power(2.0f)))
                    .addBreakable("zpm3:colt", 2, 0.40f, 0.65f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 4, ZPRandomization.power(2.0f)))
                    .addBreakable("zpm3:m1911", 3, 0.40f, 0.70f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 6, ZPRandomization.power(2.0f)))
                    .addBreakable("zpm3:usp", 2, 0.25f, 0.50f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 6, ZPRandomization.power(2.0f)))
                    .addBreakable("zpm3:uzi", 1, 0.10f, 0.20f,
                            nbt -> nbt.addRandom(
                                    "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                    0, 8, ZPRandomization.power(2.0f)))
            )
            .commonGroup("ammunition", 35,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:_makarov", 6, 2, 24,
                                    ZPRandomization.power(2.0f))
                            .addNonBreakable("zpm3:_m1911", 5, 2, 24,
                                    ZPRandomization.power(2.0f))
                            .addNonBreakable("zpm3:_colt", 4, 2, 16,
                                    ZPRandomization.power(2.0f))
                            .addNonBreakable("zpm3:_usp", 5, 2, 20,
                                    ZPRandomization.power(2.0f))
                            .addNonBreakable("zpm3:_uzi", 4, 2, 24,
                                    ZPRandomization.power(2.0f))
            )
            .commonGroup("melee", 15, (g) -> g
                    .addBreakable("zpm3:iron_club", 6, 0.30f, 0.70f)
                    .addBreakable("zpm3:bat", 5, 0.40f, 0.80f)
                    .addBreakable("zpm3:pipe", 4, 0.40f, 0.75f)
            )
            .commonGroup("equipment", 12,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 2, ZPRandomization.power(1.75f)), (g) -> g
                            .addBreakable("minecraft:shield", 3, 0.30f, 0.65f)
                            .addBreakable("minecraft:iron_helmet", 2, 0.20f, 0.50f)
                            .addBreakable("minecraft:chainmail_chestplate", 1, 0.20f, 0.45f)
                            .addBreakable("minecraft:chainmail_leggings", 1, 0.20f, 0.45f)
                            .addBreakable("minecraft:leather_boots", 3, 0.50f, 0.85f)
                            .addBreakable("minecraft:stone_shovel", 2, 0.40f, 0.75f)
            )
            .commonGroup("police_survival", 15,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:water",          7, 1, 2)
                            .addNonBreakable("zpm3:soda",           5, 1, 2)
                            .addNonBreakable("zpm3:chocolate",       5, 1, 4)
                            .addNonBreakable("zpm3:plate",           3, 1, 6)
                            .addNonBreakable("zpm3:iron_nugget",     4, 1, 4)
                            .addNonBreakable("minecraft:iron_nugget", 5, 1, 5)
                            .addBreakable("zpm3:matches",            3, 0.05f, 0.20f)
            )
            .commonGroup("police_utility", 10,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 2, ZPRandomization.power(1.75f)), (g) -> g
                            .addNonBreakable("minecraft:compass",        3, 1)
                            .addNonBreakable("minecraft:clock",          2, 1)
                            .addNonBreakable("minecraft:writable_book", 2, 1)
                            .addNonBreakable("minecraft:name_tag",       1, 1)
                            .addNonBreakable("minecraft:chain",          3, 1, 4)
                            .addNonBreakable("minecraft:iron_bars",      3, 1, 4)
                            .addNonBreakable("zpm3:chain_link",           3, 2, 8)
                            .addNonBreakable("zpm3:scrap_bars",           2, 1, 4)
            )
            .commonGroup("police_materials", 8,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:iron_ingot",   3, 1, 2,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:iron_nugget",  6, 1, 6,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:leather",      3, 1, 3)
                            .addNonBreakable("zpm3:scrap_material",    4, 1, 3,
                                    ZPRandomization.power(2.0f))
            )
            .commonGroup("police_barriers", 5,
                    new ZPLootTable.RollRules(
                            1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:scrap_trapdoor", 2, 1)
                            .addNonBreakable("zpm3:scrap_door",     2, 1)
                            .addNonBreakable("zpm3:scrap_bars",      3, 1, 4)
                            .addNonBreakable("zpm3:barbared_wire",   2, 1)
                            .addNonBreakable("zpm3:chain_link",      3, 4, 16)
            )
            .bonusGroup("police_bonus", new ZPLootTable.RollRules(
                    0.006f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("zpm3:_makarov",       4, 8, 24,
                            ZPRandomization.power(2.0f))
                    .addNonBreakable("zpm3:_m1911",         3, 8, 24,
                            ZPRandomization.power(2.0f))
                    .addNonBreakable("zpm3:_usp",           3, 8, 20,
                            ZPRandomization.power(2.0f))
                    .addNonBreakable("minecraft:iron_ingot", 3, 1, 3)
                    .addNonBreakable("minecraft:chain",      3, 2, 6)
                    .addNonBreakable("zpm3:chain_link",      3, 4, 16)
            )
            .build(new ZPLootTable.RollRules(0.90f, 1, 3, ZPRandomization.power(2.0f)));

    // ====================================================

    public static final ZPLootTable loot_firefighter_tier2 = ZPLootTable.builder("loot_firefighter_tier2")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_city_tier1"),
                    new ZPLootTable.TableExtension("zpm3:loot_firefighter", new ZPLootTable.RollRules(0.85f, 1, 1, ZPRandomization.uniform())),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("professional_tools", 20, (g) -> g
                    .addBreakable("zpm3:hatchet",             6, 0.45f, 0.85f)
                    .addBreakable("zpm3:sledgehammer",        3, 0.30f, 0.70f)
                    .addBreakable("zpm3:crowbar",             5, 0.40f, 0.80f)
                    .addBreakable("zpm3:pipe",                5, 0.50f, 0.90f)
                    .addBreakable("minecraft:iron_axe",       5, 0.40f, 0.80f)
                    .addBreakable("minecraft:iron_shovel",    4, 0.40f, 0.80f)
                    .addBreakable("minecraft:iron_pickaxe",   3, 0.35f, 0.75f)
            )
            .commonGroup("firefighter_equipment", 10, (g) -> g
                    .addBreakable("zpm3:oxygen", 1, 0.05f, 0.10f, ZPRandomization.uniform())
                    .addBreakable("minecraft:iron_helmet",      1, 0.20f, 0.50f)
                    .addBreakable("minecraft:iron_chestplate",  1, 0.20f, 0.50f)
                    .addBreakable("minecraft:iron_leggings",    1, 0.20f, 0.50f)
                    .addBreakable("minecraft:iron_boots",       1, 0.20f, 0.50f)
            )
            .commonGroup("survival", 25,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("minecraft:water_bucket",  5, 1)
                            .addNonBreakable("minecraft:bucket",        5, 1)
                            .addNonBreakable("zpm3:water",              8, 1, 4)
                            .addNonBreakable("minecraft:torch",         7, 2, 8)
                            .addNonBreakable("minecraft:lantern",       4, 1, 2)
                            .addNonBreakable("minecraft:campfire",      3, 1)
                            .addNonBreakable("minecraft:ladder",        4, 2, 8)
                            .addNonBreakable("minecraft:chain",         3, 1, 4)
            )
            .commonGroup("medical", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addBreakable("zpm3:bandage",        7, 0.20f, 0.70f)
                            .addNonBreakable("zpm3:splint",       4, 1)
                            .addNonBreakable("zpm3:water",        5, 1, 2)
                            .addNonBreakable("zpm3:vodka_medicine", 1, 1)
            )
            .commonGroup("utility", 12, (g) -> g
                    .addNonBreakable("minecraft:compass",          3, 1)
                    .addNonBreakable("minecraft:clock",            3, 1)
                    .addNonBreakable("minecraft:writable_book",   2, 1)
                    .addNonBreakable("minecraft:map",              2, 1)
                    .addNonBreakable("minecraft:flint_and_steel", 2, 1)
                    .addBreakable("zpm3:matches",                  4, 0.10f, 0.30f)
            )
            .commonGroup("technical", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:iron_bars",      4, 2, 6)
                            .addNonBreakable("minecraft:chain",          4, 1, 5)
                            .addNonBreakable("minecraft:ladder",         4, 2, 8)
                            .addNonBreakable("minecraft:cauldron",       2, 1)
                            .addNonBreakable("minecraft:smoker",         2, 1)
                            .addNonBreakable("minecraft:campfire",       2, 1)
                            .addNonBreakable("minecraft:barrel",         2, 1)
            )
            .commonGroup("materials", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:iron_ingot",   4, 1, 3,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:iron_nugget",  6, 1, 6,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:copper_ingot", 5, 1, 4,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:coal",         5, 1, 5,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:charcoal",     4, 1, 5,
                                    ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:scrap_material",    4, 1, 4,
                                    ZPRandomization.power(2.0f))
            )
            .commonGroup("lighting", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("minecraft:lantern",      5, 1, 2)
                            .addNonBreakable("zpm3:torch2",            4, 1, 2)
                            .addNonBreakable("zpm3:torch3",            2, 1)
                            .addNonBreakable("zpm3:lantern3",          3, 1)
                            .addNonBreakable("zpm3:lantern4",          1, 1)
                            .addNonBreakable("zpm3:wall_lamp",         1, 1)
                            .addNonBreakable("zpm3:block_lamp",        1, 1)
            )
            .commonGroup("firefighter_blocks", 8,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:stone",          4, 2, 8)
                            .addNonBreakable("minecraft:cobblestone",   4, 2, 8)
                            .addNonBreakable("minecraft:stone_bricks",  3, 2, 6)
                            .addNonBreakable("minecraft:iron_bars",     3, 1, 5)
                            .addNonBreakable("minecraft:chain",         3, 1, 4)
            )
            .bonusGroup("firefighter_bonus", new ZPLootTable.RollRules(
                    0.008f, 1, 2, ZPRandomization.uniform()), (g) -> g
                    .addNonBreakable("minecraft:iron_ingot",      3, 1, 3)
                    .addNonBreakable("minecraft:water_bucket",   3, 1)
                    .addNonBreakable("minecraft:lantern",        3, 1, 2)
                    .addNonBreakable("zpm3:lantern3",             2, 1)
                    .addNonBreakable("zpm3:torch3",               2, 1)
                    .addNonBreakable("minecraft:cauldron",       2, 1)
                    .addNonBreakable("minecraft:iron_bars",     3, 2, 6)
            )
            .build(new ZPLootTable.RollRules(
                    0.85f, 1, 2, ZPRandomization.power(2.0f)));

    public static final ZPLootTable loot_firefighter_tier1 = ZPLootTable.builder("loot_firefighter_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_city_tier1"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("firefighter_tools", 25, (g) -> g
                    .addBreakable("zpm3:hatchet",        6, 0.25f, 0.60f)
                    .addBreakable("zpm3:sledgehammer",   3, 0.15f, 0.45f)
                    .addBreakable("zpm3:crowbar",        4, 0.20f, 0.60f)
                    .addBreakable("zpm3:pipe",           4, 0.30f, 0.70f)
                    .addBreakable("minecraft:iron_axe",  4, 0.20f, 0.55f)
                    .addBreakable("minecraft:iron_shovel", 3, 0.20f, 0.55f)
                    .addBreakable("minecraft:stone_axe",  4, 0.40f, 0.80f)
                    .addBreakable("minecraft:stone_pickaxe", 3, 0.40f, 0.80f)
                    .addBreakable("minecraft:stone_shovel",  3, 0.40f, 0.80f))
            .commonGroup("firefighter_equipment", 8, (g) -> g
                    .addBreakable("zpm3:oxygen", 1, 0.05f, 0.10f, ZPRandomization.uniform())
                    .addBreakable("minecraft:iron_helmet",      1, 0.05f, 0.15f)
                    .addBreakable("minecraft:iron_chestplate",  1, 0.05f, 0.15f)
                    .addBreakable("minecraft:iron_leggings",    1, 0.05f, 0.15f)
                    .addBreakable("minecraft:iron_boots",       1, 0.05f, 0.15f))
            .commonGroup("survival", 25, new ZPLootTable.RollRules(
                    1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                    .addNonBreakable("minecraft:water_bucket",  3, 1)
                    .addNonBreakable("minecraft:bucket",        5, 1)
                    .addNonBreakable("zpm3:water",              8, 1, 3)
                    .addNonBreakable("minecraft:torch",         8, 2, 8)
                    .addNonBreakable("minecraft:lantern",       3, 1, 2)
                    .addNonBreakable("zpm3:torch2",             2, 1)
                    .addNonBreakable("zpm3:lantern3",           2, 1)
                    .addNonBreakable("minecraft:campfire",      2, 1)
                    .addNonBreakable("minecraft:ladder",        4, 2, 8)
                    .addNonBreakable("minecraft:chain",         3, 1, 4))
            .commonGroup("medical", 10, (g) -> g
                    .addBreakable("zpm3:bandage",  7, 0.05f, 0.50f)
                    .addNonBreakable("zpm3:splint", 4, 1)
                    .addNonBreakable("zpm3:water",  5, 1, 2))
            .commonGroup("utility", 15, (g) -> g
                    .addNonBreakable("minecraft:compass",      3, 1)
                    .addNonBreakable("minecraft:clock",        2, 1)
                    .addNonBreakable("minecraft:writable_book", 2, 1)
                    .addNonBreakable("minecraft:map",          2, 1)
                    .addNonBreakable("minecraft:flint_and_steel", 2, 1)
                    .addBreakable("zpm3:matches",               4, 0.05f, 0.20f))
            .commonGroup("materials", 10, new ZPLootTable.RollRules(
                    1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                    .addNonBreakable("minecraft:iron_nugget", 5, 1, 5,
                            ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:iron_ingot",  2, 1, 2,
                            ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:coal",         4, 1, 4,
                            ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:charcoal",     4, 1, 4,
                            ZPRandomization.power(1.5f))
                    .addNonBreakable("minecraft:leather",      3, 1, 3))
            .commonGroup("firefighter_blocks", 8, new ZPLootTable.RollRules(
                    1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                    .addNonBreakable("minecraft:ladder",       4, 2, 8)
                    .addNonBreakable("minecraft:chain",        3, 1, 4)
                    .addNonBreakable("minecraft:iron_bars",   3, 1, 4)
                    .addNonBreakable("minecraft:stone",        3, 2, 8)
                    .addNonBreakable("minecraft:cobblestone",  3, 2, 8))
            .bonusGroup("firefighter_bonus", new ZPLootTable.RollRules(
                    0.006f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                    .addNonBreakable("minecraft:iron_ingot",  3, 1, 3)
                    .addNonBreakable("minecraft:water_bucket", 2, 1)
                    .addNonBreakable("minecraft:lantern",     3, 1, 2)
                    .addNonBreakable("minecraft:campfire",    2, 1)
                    .addNonBreakable("minecraft:iron_bars",   3, 2, 6))
            .build(new ZPLootTable.RollRules(
                    0.85f, 1, 3, ZPRandomization.power(2.0f)));

    // ====================================================

    public static final ZPLootTable loot_construction_site = ZPLootTable.builder("loot_construction_site")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("tools", 30, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                    .addBreakable("minecraft:wooden_axe",      6, 0.30f, 0.80f)
                    .addBreakable("minecraft:wooden_pickaxe",  5, 0.30f, 0.80f)
                    .addBreakable("minecraft:stone_pickaxe",   6, 0.25f, 0.70f)
                    .addBreakable("minecraft:stone_axe",       5, 0.25f, 0.70f)
                    .addBreakable("minecraft:iron_shovel",     3, 0.15f, 0.50f)
                    .addBreakable("minecraft:golden_shovel",   1, 0.10f, 0.30f)
                    .addBreakable("minecraft:iron_pickaxe",    2, 0.10f, 0.45f)
                    .addBreakable("minecraft:iron_axe",        2, 0.10f, 0.45f)
                    .addBreakable("zpm3:crowbar",              5, 0.20f, 0.60f)
                    .addBreakable("zpm3:pipe",                 5, 0.30f, 0.70f)
                    .addBreakable("zpm3:sledgehammer",         3, 0.15f, 0.45f)
                    .addBreakable("zpm3:broom",                5, 0.25f, 0.65f)
            )
            .commonGroup("building_materials", 40,
                    new ZPLootTable.RollRules(1.0f, 2, 5, ZPRandomization.power(1.25f)), (g) -> g
                            .addNonBreakable("minecraft:oak_planks",       6, 4, 12)
                            .addNonBreakable("minecraft:spruce_planks",    4, 4, 12)
                            .addNonBreakable("minecraft:cobblestone",      7, 4, 16)
                            .addNonBreakable("minecraft:stone",             6, 4, 16)
                            .addNonBreakable("minecraft:gravel",            7, 4, 16)
                            .addNonBreakable("minecraft:sand",              5, 4, 12)
                            .addNonBreakable("minecraft:glass",             3, 2, 8)
                            .addNonBreakable("minecraft:glass_pane",        3, 4, 12)
                            .addNonBreakable("minecraft:stone_bricks",      4, 4, 12)
                            .addNonBreakable("minecraft:bricks",            3, 4, 12)
                            .addNonBreakable("minecraft:stone_slab",        3, 4, 12)
                            .addNonBreakable("minecraft:oak_slab",          3, 4, 12)
                            .addNonBreakable("minecraft:ladder",             4, 4, 12)
                            .addNonBreakable("minecraft:scaffolding",        5, 4, 16)
                            .addNonBreakable("minecraft:iron_bars",          2, 2, 6)
                            .addNonBreakable("minecraft:chain",              2, 2, 6)
                            .addNonBreakable("zpm3:chain_link",              2, 1, 4)
            )
            .commonGroup("mod_building_materials", 25,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.35f)), (g) -> g
                            .addNonBreakable("zpm3:ancient_bricks",     3, 4, 16)
                            .addNonBreakable("zpm3:black_bricks",        3, 4, 16)
                            .addNonBreakable("zpm3:green_bricks",        3, 4, 16)
                            .addNonBreakable("zpm3:gray_bricks",         3, 4, 16)
                            .addNonBreakable("zpm3:asphalt",             3, 4, 16)
                            .addNonBreakable("zpm3:asphalt_marking",     2, 4, 12)
            )
            .commonGroup("materials", 22,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:scrap_material",       8, 2, 8, ZPRandomization.power(2.0f))
                            .addNonBreakable("zpm3:chisel_material",       4, 1)
                            .addNonBreakable("zpm3:shelves_material",      4, 1)
                            .addNonBreakable("minecraft:iron_nugget",      4, 1, 5, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:copper_ingot",     4, 1, 3, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:coal",             3, 1, 4, ZPRandomization.power(1.5f))
            )
            .commonGroup("colored_stone", 6,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.uniform()), (g) -> g
                            .addNonBreakable("zpm3:stone_white",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_black",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_blue",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_brown",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_cyan",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_gray",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_green",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_light_blue", 3, 8, 24)
                            .addNonBreakable("zpm3:stone_light_gray", 3, 8, 24)
                            .addNonBreakable("zpm3:stone_lime",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_magenta",    3, 8, 24)
                            .addNonBreakable("zpm3:stone_orange",     3, 8, 24)
                            .addNonBreakable("zpm3:stone_pink",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_purple",     3, 8, 24)
                            .addNonBreakable("zpm3:stone_red",        3, 8, 24)
                            .addNonBreakable("zpm3:stone_yellow",     3, 8, 24)
            )
            .commonGroup("survival", 16, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.5f)), (g) -> g
                    .addBreakable("zpm3:bandage",           4, 0.0f, 0.50f)
                    .addNonBreakable("zpm3:splint",          2, 1)
                    .addNonBreakable("zpm3:vodka_medicine",  1, 1)
                    .addNonBreakable("zpm3:drugs",           1, 1)
                    .addNonBreakable("zpm3:chocolate",       3, 1)
                    .addNonBreakable("zpm3:mysterious_can",  4, 1, 2)
                    .addNonBreakable("zpm3:water",           6, 1, 2)
                    .addNonBreakable("zpm3:rotten_apple",     4, 1, 4)
            )
            .commonGroup("lighting", 8, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                    .addNonBreakable("minecraft:torch",   7, 2, 6)
                    .addNonBreakable("minecraft:lantern", 3, 1, 2)
                    .addNonBreakable("zpm3:torch2",       2, 1)
            )
            .commonGroup("utility", 8, (g) -> g
                    .addNonBreakable("minecraft:bucket",            3, 1)
                    .addNonBreakable("minecraft:oak_door",          2, 1)
                    .addNonBreakable("minecraft:oak_trapdoor",      2, 1)
                    .addNonBreakable("minecraft:oak_fence",          3, 1, 4)
                    .addNonBreakable("minecraft:oak_fence_gate",     2, 1, 2)
                    .addNonBreakable("minecraft:oak_button",         3, 1, 3)
                    .addNonBreakable("minecraft:lever",              2, 1, 2)
                    .addNonBreakable("zpm3:cement_material",       1, 1)
            )
            .bonusGroup("construction_bonus", 0.01f, (g) -> g
                            .addNonBreakable("zpm3:cement_material",       3, 1)
                            .addNonBreakable("zpm3:scrap_stack_material",  2, 1, 3)
                            .addNonBreakable("minecraft:iron_ingot",        2, 1, 2)
                            .addNonBreakable("minecraft:lantern",          2, 1)
                            .addNonBreakable("minecraft:scaffolding",       3, 8, 16)
                            .addNonBreakable("minecraft:stonecutter",       1, 1)
            )
            .build(new ZPLootTable.RollRules(0.85f, 1, 3, ZPRandomization.power(2.5f)));

    // ====================================================

    public static final ZPLootTable loot_home_stash = ZPLootTable.builder("loot_home_stash")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_city_tier1"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("firearms", 22, (g) -> g
                    .addBreakable("zpm3:makarov", 5, 0.80f, 1.00f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 6,
                                    ZPRandomization.power(2.0f))))
            .commonGroup("firearms_m1911", 8, (g) -> g
                    .addBreakable("zpm3:m1911", 3, 0.75f, 1.00f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 7,
                                    ZPRandomization.power(2.0f))))
            .commonGroup("firearms_colt", 6, (g) -> g
                    .addBreakable("zpm3:colt", 3, 0.70f, 1.00f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 3,
                                    ZPRandomization.power(2.0f))))
            .commonGroup("firearms_handmade", 5, (g) -> g
                    .addBreakable("zpm3:handmade_pistol", 3, 0.70f, 1.00f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 1,
                                    ZPRandomization.power(2.0f))))
            .commonGroup("firearms_mosin", 2, (g) -> g
                    .addBreakable("zpm3:mosin", 1, 0.05f, 0.25f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 2,
                                    ZPRandomization.power(2.5f))))
            .commonGroup("ammunition", 25,
                    new ZPLootTable.RollRules(1.0f, 2, 5, ZPRandomization.power(2.25f)), (g) -> g
                            .addNonBreakable("zpm3:_makarov",         5, 1, 32, ZPRandomization.power(2.5f))
                            .addNonBreakable("zpm3:_handmade_pistol", 3, 1, 32, ZPRandomization.power(2.5f))
                            .addNonBreakable("zpm3:_colt",            3, 1, 32, ZPRandomization.power(2.5f))
                            .addNonBreakable("zpm3:_m1911",           3, 1, 32, ZPRandomization.power(2.5f))
                            .addNonBreakable("zpm3:_mosin",           1, 1, 8,  ZPRandomization.power(2.5f)))
            .commonGroup("medical", 20,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:aid_kit",          2, 1)
                            .addBreakable("zpm3:bandage",              5, 0.25f, 1.00f)
                            .addNonBreakable("zpm3:splint",            3, 1)
                            .addNonBreakable("zpm3:vodka_medicine",    2, 1)
                            .addNonBreakable("zpm3:whiskey_medicine",  2, 1)
                            .addNonBreakable("zpm3:drugs",             1, 1))
            .commonGroup("food", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)), (g) -> g
                            .addNonBreakable("zpm3:mysterious_can", 5, 1, 4)
                            .addNonBreakable("zpm3:bean",           5, 1, 4)
                            .addNonBreakable("zpm3:peaches",         4, 1, 4)
                            .addNonBreakable("zpm3:chocolate",       3, 1, 3)
                            .addNonBreakable("zpm3:jam",             3, 1, 3)
                            .addNonBreakable("zpm3:soda",            3, 1, 3)
                            .addNonBreakable("zpm3:water",            4, 1, 3))
            .commonGroup("lighting", 6, (g) -> g
                    .addNonBreakable("zpm3:block_lamp", 2, 1)
                    .addNonBreakable("zpm3:wall_lamp",  2, 1)
                    .addNonBreakable("zpm3:lantern3",    2, 1)
                    .addNonBreakable("zpm3:lantern4",    1, 1))
            .commonGroup("valuables", 8, (g) -> g
                    .addNonBreakable("minecraft:gold_nugget", 4, 1, 5)
                    .addNonBreakable("minecraft:gold_ingot",  2, 1)
                    .addNonBreakable("minecraft:emerald",     2, 1)
                    .addNonBreakable("minecraft:iron_ingot",  3, 1, 2))
            .commonGroup("utility", 8, (g) -> g
                    .addNonBreakable("minecraft:map",      2, 1)
                    .addNonBreakable("minecraft:compass",  2, 1)
                    .addNonBreakable("minecraft:clock",    2, 1)
                    .addNonBreakable("minecraft:name_tag", 1, 1)
                    .addNonBreakable("minecraft:leather",  3, 1, 4))
            .bonusGroup("home_stash_bonus",
                    new ZPLootTable.RollRules(0.015f, 1, 2, ZPRandomization.uniform()), (g) -> g
                            .addNonBreakable("minecraft:gold_ingot", 2, 1, 2)
                            .addNonBreakable("minecraft:emerald",    2, 1)
            )
            .build(new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.0f)));

    // ====================================================

    public static final ZPLootTable loot_building_store_tier2 = ZPLootTable.builder("loot_building_store_tier2")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_building_store_tier1"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("advanced_building_materials", 45,
                    new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(1.25f)), (g) -> g
                            .addNonBreakable("minecraft:polished_andesite",       5, 4, 16)
                            .addNonBreakable("minecraft:polished_diorite",        5, 4, 16)
                            .addNonBreakable("minecraft:polished_granite",       5, 4, 16)
                            .addNonBreakable("minecraft:deepslate",              5, 4, 16)
                            .addNonBreakable("minecraft:cobbled_deepslate",      5, 4, 16)
                            .addNonBreakable("minecraft:deepslate_bricks",       4, 4, 16)
                            .addNonBreakable("minecraft:deepslate_tiles",        4, 4, 16)
                            .addNonBreakable("minecraft:calcite",                 3, 4, 12)
                            .addNonBreakable("minecraft:tuff",                    4, 4, 16)
                            .addNonBreakable("minecraft:quartz_block",            2, 2, 8)
                            .addNonBreakable("minecraft:quartz_bricks",           2, 2, 8)
                            .addNonBreakable("minecraft:prismarine",              2, 2, 8)
                            .addNonBreakable("minecraft:dark_oak_planks",         4, 4, 12)
                            .addNonBreakable("minecraft:acacia_planks",            4, 4, 12)
                            .addNonBreakable("minecraft:mangrove_planks",          3, 4, 12)
                            .addNonBreakable("minecraft:spruce_log",               4, 2, 8)
                            .addNonBreakable("minecraft:dark_oak_log",             3, 2, 8)
                            .addNonBreakable("minecraft:polished_blackstone",     3, 4, 12)
                            .addNonBreakable("minecraft:polished_blackstone_bricks", 2, 4, 12)
            )
            .commonGroup("colored_stone", 25,
                    new ZPLootTable.RollRules(1.0f, 2, 5, ZPRandomization.uniform()), (g) -> g
                            .addNonBreakable("zpm3:stone_white",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_black",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_blue",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_brown",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_cyan",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_gray",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_green",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_light_blue", 3, 8, 24)
                            .addNonBreakable("zpm3:stone_light_gray", 3, 8, 24)
                            .addNonBreakable("zpm3:stone_lime",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_magenta",    3, 8, 24)
                            .addNonBreakable("zpm3:stone_orange",     3, 8, 24)
                            .addNonBreakable("zpm3:stone_pink",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_purple",     3, 8, 24)
                            .addNonBreakable("zpm3:stone_red",        3, 8, 24)
                            .addNonBreakable("zpm3:stone_yellow",     3, 8, 24)
            )
            .commonGroup("advanced_mod_building", 40,
                    new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(1.3f)), (g) -> g
                            .addNonBreakable("zpm3:ancient_bricks",       6, 6, 16)
                            .addNonBreakable("zpm3:black_bricks",          6, 6, 16)
                            .addNonBreakable("zpm3:green_bricks",          5, 6, 16)
                            .addNonBreakable("zpm3:gray_bricks",           6, 6, 16)
                            .addNonBreakable("zpm3:asphalt",               6, 6, 16)
                            .addNonBreakable("zpm3:asphalt_marking",       4, 6, 16)
                            .addNonBreakable("zpm3:scrap_block",            3, 2, 6)
                            .addNonBreakable("zpm3:scrap_slab",              3, 2, 6)
                            .addNonBreakable("zpm3:scrap_stairs",            3, 2, 6)
                            .addNonBreakable("zpm3:scrap_bars",              3, 2, 8)
            )
            .commonGroup("iron_tools", 30,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addBreakable("minecraft:iron_pickaxe",  8, 0.40f, 0.80f)
                            .addBreakable("minecraft:iron_axe",      7, 0.40f, 0.80f)
                            .addBreakable("minecraft:iron_shovel",   6, 0.40f, 0.80f)
                            .addBreakable("minecraft:iron_hoe",      4, 0.40f, 0.80f)
                            .addBreakable("minecraft:iron_sword",    3, 0.30f, 0.70f)
            )
            .commonGroup("construction_tools", 18, (g) -> g
                    .addBreakable("zpm3:wrench",        5, 0.30f, 1.0f)
                    .addBreakable("zpm3:metal_cutters", 4, 0.30f, 1.0f)
                    .addBreakable("zpm3:crowbar",       4, 0.30f, 1.0f)
                    .addBreakable("zpm3:sledgehammer",  3, 0.25f, 1.0f)
                    .addBreakable("zpm3:pipe",          4, 0.40f, 1.0f)
            )
            .commonGroup("functional_blocks", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:crafting_table",  4, 1)
                            .addNonBreakable("minecraft:furnace",         4, 1)
                            .addNonBreakable("minecraft:blast_furnace",   2, 1)
                            .addNonBreakable("minecraft:smoker",          2, 1)
                            .addNonBreakable("minecraft:stonecutter",     2, 1)
                            .addNonBreakable("minecraft:barrel",          3, 1)
                            .addNonBreakable("minecraft:chest",           2, 1)
                            .addNonBreakable("minecraft:hopper",          1, 1)
                            .addNonBreakable("minecraft:dispenser",       2, 1)
                            .addNonBreakable("minecraft:dropper",         2, 1)
            )
            .commonGroup("lighting", 18,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:torch",       8, 2, 8)
                            .addNonBreakable("minecraft:lantern",     5, 1, 3)
                            .addNonBreakable("minecraft:redstone_torch", 4, 2, 5)
                            .addNonBreakable("zpm3:torch2",           4, 1, 2)
                            .addNonBreakable("zpm3:torch3",           3, 1)
                            .addNonBreakable("zpm3:lantern3",         4, 1, 2)
                            .addNonBreakable("zpm3:lantern4",         3, 1)
                            .addNonBreakable("zpm3:wall_lamp",        2, 1)
                            .addNonBreakable("zpm3:block_lamp",       2, 1)
            )
            .commonGroup("cement", 12, (g) -> g
                            .addNonBreakable("zpm3:cement_material",  5, 1, 2)
                            .addNonBreakable("zpm3:scrap_stack_material", 4, 1, 3)
            )
            .commonGroup("technical", 12,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:redstone",             5, 1, 6, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:redstone_torch",       4, 1, 4)
                            .addNonBreakable("minecraft:repeater",             3, 1, 2)
                            .addNonBreakable("minecraft:comparator",           2, 1)
                            .addNonBreakable("minecraft:piston",               3, 1)
                            .addNonBreakable("minecraft:sticky_piston",        2, 1)
                            .addNonBreakable("minecraft:observer",              2, 1)
                            .addNonBreakable("minecraft:lever",                3, 1, 2)
                            .addNonBreakable("minecraft:stone_button",         3, 1, 3)
                            .addNonBreakable("minecraft:stone_pressure_plate", 2, 1, 2)
            )
            .commonGroup("construction_misc", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.3f)), (g) -> g
                            .addNonBreakable("minecraft:scaffolding",       5, 8, 16)
                            .addNonBreakable("minecraft:iron_bars",          4, 2, 8)
                            .addNonBreakable("minecraft:chain",              4, 2, 8)
                            .addNonBreakable("zpm3:chain_link",              3, 2, 8)
                            .addNonBreakable("minecraft:ladder",              4, 4, 12)
                            .addNonBreakable("minecraft:oak_fence",           3, 4, 12)
                            .addNonBreakable("minecraft:oak_fence_gate",      2, 2, 8)
                            .addNonBreakable("minecraft:stone_wall",           3, 4, 12)
            )
            .commonGroup("materials", 15,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("minecraft:iron_ingot",   4, 1, 3, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:copper_ingot", 5, 1, 4, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:iron_nugget", 5, 1, 6, ZPRandomization.power(1.5f))
                            .addNonBreakable("minecraft:coal",         4, 1, 5, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:scrap_material",    6, 2, 6, ZPRandomization.power(2.0f))
                            .addNonBreakable("zpm3:chisel_material",    4, 1)
                            .addNonBreakable("zpm3:shelves_material",   4, 1)
            )
            .bonusGroup("building_store_tier2_bonus", 0.012f, (g) -> g
                            .addNonBreakable("zpm3:cement_material",       3, 1, 2)
                            .addNonBreakable("zpm3:wall_lamp",              2, 1)
                            .addNonBreakable("zpm3:block_lamp",             2, 1)
                            .addNonBreakable("minecraft:stonecutter",       2, 1)
                            .addNonBreakable("minecraft:blast_furnace",     1, 1)
                            .addNonBreakable("minecraft:smoker",            1, 1)
                            .addNonBreakable("minecraft:iron_block",        1, 1)
            )
            .build(new ZPLootTable.RollRules(0.95f, 1, 3, ZPRandomization.power(2.0f)));

    // ====================================================

    public static final ZPLootTable loot_building_store_tier1 = ZPLootTable.builder("loot_building_store_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris")
            )
            .commonGroup("building_materials", 55,
                    new ZPLootTable.RollRules(1.0f, 1, 5, ZPRandomization.power(1.25f)), (g) -> g
                            .addNonBreakable("minecraft:oak_planks",          8, 4, 12)
                            .addNonBreakable("minecraft:spruce_planks",       6, 4, 12)
                            .addNonBreakable("minecraft:birch_planks",       5, 4, 12)
                            .addNonBreakable("minecraft:cobblestone",        8, 4, 16)
                            .addNonBreakable("minecraft:stone",               7, 4, 16)
                            .addNonBreakable("minecraft:stone_bricks",       6, 4, 12)
                            .addNonBreakable("minecraft:bricks",              5, 4, 12)
                            .addNonBreakable("minecraft:gravel",              6, 4, 12)
                            .addNonBreakable("minecraft:sand",                5, 4, 12)
                            .addNonBreakable("minecraft:glass",               5, 2, 8)
                            .addNonBreakable("minecraft:glass_pane",           4, 4, 12)
                            .addNonBreakable("minecraft:terracotta",          4, 4, 12)
                            .addNonBreakable("minecraft:oak_log",              5, 2, 8)
                            .addNonBreakable("minecraft:stone_slab",           4, 4, 12)
                            .addNonBreakable("minecraft:oak_slab",             4, 4, 12)
                            .addNonBreakable("minecraft:stone_stairs",         4, 4, 12)
                            .addNonBreakable("minecraft:oak_stairs",           4, 4, 12)
                            .addNonBreakable("minecraft:oak_fence",            4, 4, 12)
                            .addNonBreakable("minecraft:oak_fence_gate",       3, 2, 8)
                            .addNonBreakable("minecraft:ladder",               4, 4, 12)
                            .addNonBreakable("minecraft:iron_bars",            3, 2, 8)
                            .addNonBreakable("minecraft:chain",                3, 2, 6)
                            .addNonBreakable("minecraft:scaffolding",                3, 8, 32)
            )
            .commonGroup("mod_building_materials", 35,
                    new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(1.35f)), (g) -> g
                            .addNonBreakable("zpm3:ancient_bricks",       5, 4, 16)
                            .addNonBreakable("zpm3:black_bricks",          5, 4, 16)
                            .addNonBreakable("zpm3:green_bricks",          4, 4, 16)
                            .addNonBreakable("zpm3:gray_bricks",           5, 4, 16)
                            .addNonBreakable("zpm3:asphalt",               6, 4, 16)
                            .addNonBreakable("zpm3:asphalt_marking",       4, 4, 16)
                            .addNonBreakable("zpm3:cement_material",       3, 1)
                            .addNonBreakable("zpm3:cracked_crafting_table", 2, 1)
            )
            .commonGroup("colored_stone", 25,
                    new ZPLootTable.RollRules(1.0f, 1, 4, ZPRandomization.uniform()), (g) -> g
                            .addNonBreakable("zpm3:stone_white",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_black",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_blue",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_brown",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_cyan",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_gray",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_green",      3, 8, 24)
                            .addNonBreakable("zpm3:stone_light_blue", 3, 8, 24)
                            .addNonBreakable("zpm3:stone_light_gray", 3, 8, 24)
                            .addNonBreakable("zpm3:stone_lime",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_magenta",    3, 8, 24)
                            .addNonBreakable("zpm3:stone_orange",     3, 8, 24)
                            .addNonBreakable("zpm3:stone_pink",       3, 8, 24)
                            .addNonBreakable("zpm3:stone_purple",     3, 8, 24)
                            .addNonBreakable("zpm3:stone_red",        3, 8, 24)
                            .addNonBreakable("zpm3:stone_yellow",     3, 8, 24)
            )
            .commonGroup("tools", 25, (g) -> g
                    .addBreakable("minecraft:stone_pickaxe",  8, 0.40f, 0.90f)
                    .addBreakable("minecraft:stone_axe",      7, 0.40f, 0.90f)
                    .addBreakable("minecraft:stone_shovel",   5, 0.40f, 0.90f)
                    .addBreakable("minecraft:iron_shovel",    3, 0.20f, 0.60f)
                    .addBreakable("minecraft:iron_axe",        3, 0.20f, 0.60f)
                    .addBreakable("zpm3:wrench",              4, 0.20f, 0.50f)
                    .addBreakable("zpm3:metal_cutters",       3, 0.20f, 0.50f)
                    .addBreakable("zpm3:crowbar",             3, 0.20f, 0.60f)
                    .addBreakable("zpm3:sledgehammer",        2, 0.15f, 0.45f)
                    .addBreakable("zpm3:pipe",                4, 0.30f, 0.70f)
            )
            .commonGroup("lighting", 15, (g) -> g
                    .addNonBreakable("minecraft:torch",        8, 2, 6)
                    .addNonBreakable("minecraft:lantern",      4, 1, 2)
                    .addNonBreakable("zpm3:torch2",            3, 1)
                    .addNonBreakable("zpm3:lantern3",          2, 1)
                    .addNonBreakable("zpm3:wall_lamp",         1, 1)
                    .addNonBreakable("zpm3:block_lamp",        1, 1)
            )
            .commonGroup("materials", 18,
                    new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(1.5f)), (g) -> g
                            .addNonBreakable("zpm3:scrap_material",       8, 2, 8, ZPRandomization.power(2.0f))
                            .addNonBreakable("zpm3:scrap_stack_material",  4, 1, 3, ZPRandomization.power(1.5f))
                            .addNonBreakable("zpm3:chisel_material",       4, 1)
                            .addNonBreakable("zpm3:shelves_material",      4, 1)
            )
            .commonGroup("utility", 10, (g) -> g
                    .addNonBreakable("minecraft:oak_door",       3, 1)
                    .addNonBreakable("minecraft:oak_trapdoor",   3, 1)
                    .addNonBreakable("minecraft:oak_button",      4, 1, 3)
                    .addNonBreakable("minecraft:lever",           3, 1, 2)
                    .addNonBreakable("minecraft:stone_button",    3, 1, 3)
                    .addNonBreakable("minecraft:stone_pressure_plate", 2, 1, 2)
            )
            .commonGroup("workshop", 6, (g) -> g
                    .addNonBreakable("minecraft:crafting_table",  2, 1)
                    .addNonBreakable("minecraft:furnace",         2, 1)
                    .addNonBreakable("minecraft:barrel",          2, 1)
                    .addNonBreakable("minecraft:chest",           1, 1)
            )
            .bonusGroup("building_store_bonus", new ZPLootTable.RollRules(0.006f, 1, 2, ZPRandomization.uniform()), (g) -> g
                            .addNonBreakable("zpm3:cement_material",      2, 1)
                            .addNonBreakable("zpm3:ancient_bricks",        3, 8, 16)
                            .addNonBreakable("zpm3:black_bricks",           3, 8, 16)
                            .addNonBreakable("zpm3:gray_bricks",            3, 8, 16)
                            .addNonBreakable("zpm3:asphalt",                3, 8, 16)
                            .addNonBreakable("zpm3:scrap_stack_material",  2, 1, 3)
                            .addNonBreakable("minecraft:stonecutter",      1, 1)
            )
            .build(new ZPLootTable.RollRules(0.9f, 1, 3, ZPRandomization.power(2.0f)));

    // ====================================================

    public static final ZPLootTable loot_bar_tier1 = ZPLootTable.builder("loot_bar_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_village_tier1")
            )
            .commonGroup("food", 35, new ZPLootTable.RollRules(1.0f, 1, 3, ZPRandomization.power(2.5f)), (g) -> g
                    .addNonBreakable("minecraft:cooked_porkchop",  6, 1)
                    .addNonBreakable("minecraft:mutton",           5, 1)
                    .addNonBreakable("minecraft:porkchop",         5, 1)
                    .addNonBreakable("minecraft:beef",             6, 1)
                    .addNonBreakable("minecraft:cooked_beef",      6, 1)
                    .addNonBreakable("minecraft:cooked_mutton",    6, 1)
                    .addNonBreakable("minecraft:spider_eye",       2, 1)
                    .addNonBreakable("minecraft:suspicious_stew",  2, 1)
                    .addNonBreakable("zpm3:soda",                  8, 1, 2)
                    .addNonBreakable("zpm3:water",                 5, 1)
                    .addNonBreakable("zpm3:vodka_medicine",    3, 1, 2, ZPRandomization.power(3.0f))
                    .addNonBreakable("zpm3:whiskey_medicine",    1, 1, 2, ZPRandomization.power(3.0f))
                    .addNonBreakable("zpm3:rotten_apple",          3, 1, 2))
            .commonGroup("bar_tools", 20, (g) -> g
                    .addBreakable("zpm3:broom",       8, 0.30f, 0.70f)
                    .addBreakable("zpm3:cleaver",     5, 0.25f, 0.65f)
                    .addBreakable("zpm3:iron_club",   3, 0.20f, 0.60f)
                    .addBreakable("zpm3:bat",         6, 0.30f, 0.70f)
                    .addNonBreakable("zpm3:plate",    8, 3, 6))
            .commonGroup("medical", 20, (g) -> g
                    .addNonBreakable("zpm3:vodka_medicine",    4, 1, 2, ZPRandomization.power(3.0f))
                    .addNonBreakable("zpm3:anti_headache_pill", 3, 1)
                    .addNonBreakable("zpm3:whiskey_medicine",    3, 1, 2, ZPRandomization.power(3.0f))
                    .addNonBreakable("zpm3:drugs",              2, 1, 2, ZPRandomization.power(3.0f)))
            .commonGroup("fire", 10, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.5f)), (g) -> g
                    .addBreakable("zpm3:matches",            6, 0.05f, 0.20f)
                    .addBreakable("minecraft:flint_and_steel", 3, 0.20f, 0.50f)
                    .addNonBreakable("minecraft:charcoal",     5, 1, 2)
                    .addNonBreakable("minecraft:coal",         5, 1, 2)
                    .addNonBreakable("minecraft:campfire",     2, 1)
                    .addNonBreakable("zpm3:campfire2",          2, 1)
                    .addNonBreakable("zpm3:lantern3",           3, 1))
            .commonGroup("ammunition", 8, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(2.0f)), (g) -> g
                    .addNonBreakable("zpm3:_shotgun",          3, 1, 4,  ZPRandomization.power(2.5f))
                    .addNonBreakable("zpm3:_colt",             4, 1, 6,  ZPRandomization.power(2.5f))
                    .addNonBreakable("zpm3:_handmade_pistol",  3, 1, 3,  ZPRandomization.power(2.5f))
                    .addNonBreakable("zpm3:_uzi",              1, 1, 10, ZPRandomization.power(3.0f))
                    .addNonBreakable("zpm3:_makarov",          3, 1, 6,  ZPRandomization.power(2.5f)))
            .commonGroup("utility", 8, (g) -> g
                    .addNonBreakable("minecraft:clock", 2, 1)
                    .addNonBreakable("zpm3:plate",      6, 3, 6))
            .bonusGroup("bar_bonus", 0.01f, (g) -> g
                    .addNonBreakable("zpm3:whiskey_medicine",    2, 1, 2, ZPRandomization.power(2.0f))
                    .addNonBreakable("zpm3:vodka_medicine",     4, 1, 2, ZPRandomization.power(2.0f))
                    .addNonBreakable("zpm3:drugs",              2, 1, 2, ZPRandomization.power(2.0f))
                    .addNonBreakable("minecraft:gold_nugget",   2, 1, 3)
                    .addNonBreakable("minecraft:clock",         1, 1)
                    .addNonBreakable("zpm3:lantern3",           1, 1))
            .build(new ZPLootTable.RollRules(0.85f, 1, 3, ZPRandomization.power(2.0f)));

    // =============================================

    public static final ZPLootTable loot_restaurant_tier1 = ZPLootTable.builder("loot_restaurant_tier1")
            .extendBy(ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"))
            .commonGroup("food", 70, new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(2.5f)), (g) -> g
                    .addNonBreakable("minecraft:cooked_salmon",    5, 1, 2)
                    .addNonBreakable("zpm3:water",                 7, 1, 2)
                    .addNonBreakable("zpm3:minecake",              3, 1, 2)
                    .addNonBreakable("zpm3:jam",                   4, 1, 2)
                    .addNonBreakable("minecraft:cooked_beef",      5, 1, 2)
                    .addNonBreakable("minecraft:cooked_cod",       7, 1, 4)
                    .addNonBreakable("minecraft:cooked_porkchop",  5, 1, 2)
                    .addNonBreakable("minecraft:cooked_mutton",    5, 1, 2)
                    .addNonBreakable("minecraft:cooked_chicken",   5, 1, 2)
                    .addNonBreakable("minecraft:cooked_rabbit",    4, 1, 2)
                    .addNonBreakable("minecraft:baked_potato",     6, 1, 2)
                    .addNonBreakable("zpm3:whiskey_medicine", 1, 1)
                    .addNonBreakable("minecraft:egg",              6, 2, 6))
            .commonGroup("meals", 25, (g) -> g
                    .addNonBreakable("minecraft:honey_bottle",   3, 1)
                    .addNonBreakable("minecraft:rabbit_stew",    5, 1)
                    .addNonBreakable("minecraft:beetroot_soup",  5, 1)
                    .addNonBreakable("minecraft:cake",            3, 1))
            .commonGroup("restaurant_food", 30, new ZPLootTable.RollRules(1.0f, 2, 4, ZPRandomization.power(2.0f)), (g) -> g
                    .addNonBreakable("minecraft:bread",          7, 1, 3)
                    .addNonBreakable("minecraft:cookie",          5, 1, 2)
                    .addNonBreakable("minecraft:sugar",           5, 1, 3)
                    .addNonBreakable("minecraft:wheat",           4, 1, 3)
                    .addNonBreakable("minecraft:coal",            4, 1, 3)
                    .addNonBreakable("minecraft:charcoal",        3, 1, 3))
            .commonGroup("restaurant_tools", 12, (g) -> g
                    .addBreakable("zpm3:cleaver",          8, 0.30f, 0.70f)
                    .addBreakable("minecraft:shears",      4, 0.20f, 0.60f)
                    .addBreakable("zpm3:matches",          3, 0.05f, 0.20f))
            .commonGroup("tableware", 20, new ZPLootTable.RollRules(1.0f, 2, 5, ZPRandomization.power(1.5f)), (g) -> g
                    .addNonBreakable("zpm3:plate",            10, 2, 8)
                    .addNonBreakable("minecraft:bowl",         7, 1, 3)
                    .addNonBreakable("minecraft:glass_bottle", 6, 1, 3)
                    .addNonBreakable("minecraft:glass",         5, 1, 3))
            .commonGroup("kitchen", 8, (g) -> g
                    .addNonBreakable("minecraft:campfire",  3, 1)
                    .addNonBreakable("minecraft:barrel",    3, 1)
                    .addNonBreakable("minecraft:smoker",    2, 1)
                    .addNonBreakable("minecraft:cauldron",  1, 1))
            .commonGroup("survival", 8, (g) -> g
                    .addNonBreakable("zpm3:whiskey_medicine", 1, 1, 2, ZPRandomization.power(3.0f)))
            .bonusGroup("restaurant_bonus", 0.006f, (g) -> g
                    .addNonBreakable("minecraft:golden_carrot", 2, 1)
                    .addNonBreakable("minecraft:honey_bottle",  2, 1)
                    .addNonBreakable("minecraft:cake",          2, 1))
            .build(new ZPLootTable.RollRules(0.85f, 1, 3, ZPRandomization.power(2.0f)));

    // =============================================

    public static final ZPLootTable loot_kitchen_tier1 = ZPLootTable.builder("loot_kitchen_tier1")
            .extendBy(
                    ZPLootTable.TableExtension.defaultInst("zpm3:loot_debris"),
                    new ZPLootTable.TableExtension("zpm3:loot_city_tier1", new ZPLootTable.RollRules(0.10f, 1, 1, ZPRandomization.uniform()))
            )
            .commonGroup("food", 70, new ZPLootTable.RollRules(1.0f, 2, 6, ZPRandomization.power(2.0f)), (g) -> g
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
                    .addNonBreakable("minecraft:kelp",               6, 1, 6)
                    .addNonBreakable("minecraft:dried_kelp",         6, 1, 6)
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
                    .addNonBreakable("minecraft:egg",  4, 2, 4)
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
                    .addNonBreakable("zpm3:plate",                 8, 3, 6))
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
            .build(new ZPLootTable.RollRules(0.85f, 1, 3, ZPRandomization.power(2.25f)));

    // =============================================

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
            .commonGroup("survival", 16, new ZPLootTable.RollRules(1.0f, 1, 2, ZPRandomization.power(3.25f)), (g) -> g
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
            .build(new ZPLootTable.RollRules(0.9f, 1, 3, ZPRandomization.power(3.0f)));

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
            .commonGroup("medical", 12, (g) -> g
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
            .build(new ZPLootTable.RollRules(0.75f, 1, 2, ZPRandomization.power(2.5f)));

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
            .commonGroup("firearms_makarov",4,
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
            .commonGroup("firearms_m1911", 2,
                    (g) -> g
                            .addBreakable("zpm3:m1911", 1, 0.20f, 0.50f,
                                    nbt -> nbt.addRandom(
                                            "zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(),
                                            0, 4,
                                            ZPRandomization.power(2.5f))))
            .commonGroup("firearms_colt", 2,
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
            .commonGroup("blocks", 16, new ZPLootTable.RollRules(1.0f, 2, 6, ZPRandomization.power(1.25f)), (g) -> g
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
            .commonGroup("food", 40, (g) -> g
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
            .commonGroup("medical", 6, (g) -> g
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
            .build(new ZPLootTable.RollRules(0.7f, 1, 1, ZPRandomization.uniform()));

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
            .commonGroup("firearms_m1911", 2, (g) -> g
                    .addBreakable("zpm3:m1911", 1, 0.40f, 0.80f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 7, ZPRandomization.power(1.5f))))
            .commonGroup("firearms_makarov", 4, (g) -> g
                    .addBreakable("zpm3:makarov", 1, 0.40f, 1.00f,
                            nbt -> nbt.addRandom("zpgun_s:" + ZPTagID.GUN_AMMO_INSIDE_TAG.id(), 0, 6, ZPRandomization.power(1.5f))))
            .commonGroup("firearms_colt", 1, (g) -> g
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
            .commonGroup("medical", 8, (g) -> g
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
