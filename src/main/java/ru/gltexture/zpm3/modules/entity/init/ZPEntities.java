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

package ru.gltexture.zpm3.modules.entity.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPZombieConfig;
import ru.gltexture.zpm3.engine.helpers.ZPBiomeModifyingHelper;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.modules.entity.instances.throwables.*;
import ru.gltexture.zpm3.modules.entity.events.common.ZPEntityMobAttributes;

import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPDogZombie;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPMinerZombie;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPCommonZombie;
import ru.gltexture.zpm3.modules.entity.rendering.entities.misc.ZPThrowableEntityRender;
import ru.gltexture.zpm3.modules.entity.rendering.entities.zombies.ZPCommonZombieRender;
import ru.gltexture.zpm3.modules.entity.rendering.entities.zombies.ZPDogZombieRenderer;
import ru.gltexture.zpm3.modules.entity.rendering.entities.zombies.ZPMinerZombieRender;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.List;
import java.util.Objects;

public class ZPEntities extends ZPCommonRegistry<EntityType<?>> {
    public static RegistryObject<EntityType<ZPAcidBottleEntity>> acid_bottle_entity;
    public static RegistryObject<EntityType<ZPPlateEntity>> plate_entity;
    public static RegistryObject<EntityType<ZPRockEntity>> rock_entity;
    public static RegistryObject<EntityType<ZPBrickEntity>> brock_entity;
    public static RegistryObject<EntityType<ZPRottenFleshEntity>> rotten_flesh_entity;

    public static RegistryObject<EntityType<ZPCommonZombie>> zp_common_zombie_entity;
    public static RegistryObject<EntityType<ZPMinerZombie>> zp_miner_zombie_entity;
    public static RegistryObject<EntityType<ZPDogZombie>> zp_dog_zombie_entity;

    public ZPEntities() {
        super(ZPRegistryConveyor.Target.ENTITY_TYPE);
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerZombieRenderer(RegistryObject<EntityType<ZPCommonZombie>> e, ZPRegUtils utils) {
        utils.entities().matchEntityRendering(e, c -> new ZPCommonZombieRender(c, "textures/entity/zombie_common/citizen%d.png", ZPZombieConfig.TOTAL_COMMON_ZOMBIE_TEXTURES.getVar()));
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerMinerZombieRenderer(RegistryObject<EntityType<ZPMinerZombie>> e, ZPRegUtils utils) {
        utils.entities().matchEntityRendering(e, c -> new ZPMinerZombieRender(c, "textures/entity/zombie_miner/miner%d.png", ZPZombieConfig.TOTAL_MINER_ZOMBIE_TEXTURES.getVar()));
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerDogZombieRenderer(RegistryObject<EntityType<ZPDogZombie>> e, ZPRegUtils utils) {
        utils.entities().matchEntityRendering(e, ZPDogZombieRenderer::new);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<EntityType<? extends Entity>> regSupplier) {
        ZPEntities.acid_bottle_entity = regSupplier.register("acid_bottle_entity", () -> EntityType.Builder.<ZPAcidBottleEntity>of(ZPAcidBottleEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "acid_bottle_entity").toString()))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.entities().matchEntityRendering(e, ZPThrowableEntityRender::new);
                    });
                }).end();

        ZPEntities.plate_entity = regSupplier.register("plate_entity", () -> EntityType.Builder.<ZPPlateEntity>of(ZPPlateEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "plate_entity").toString())).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.entities().matchEntityRendering(e, ZPThrowableEntityRender::new);
            });
        }).end();

        ZPEntities.rock_entity = regSupplier.register("rock_entity", () -> EntityType.Builder.<ZPRockEntity>of(ZPRockEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "rock_entity").toString())).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.entities().matchEntityRendering(e, ZPThrowableEntityRender::new);
            });
        }).end();

        ZPEntities.brock_entity = regSupplier.register("brock_entity", () -> EntityType.Builder.<ZPBrickEntity>of(ZPBrickEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "brock_entity").toString())).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.entities().matchEntityRendering(e, ZPThrowableEntityRender::new);
            });
        }).end();

        ZPEntities.rotten_flesh_entity = regSupplier.register("rotten_flesh_entity", () -> EntityType.Builder.<ZPRottenFleshEntity>of(ZPRottenFleshEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "rotten_flesh_entity").toString())).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.entities().matchEntityRendering(e, ZPThrowableEntityRender::new);
            });
        }).end();

        ZPEntities.zp_common_zombie_entity = regSupplier.register("zp_common_zombie_entity", () -> EntityType.Builder.<ZPCommonZombie>of(ZPCommonZombie::new, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(8)
                        .build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "zp_common_zombie_entity").toString()))
                .afterCreated((e, utils) -> {
                    utils.loot().addEntityLootTable(e, () -> new LootPool.Builder()
                            .setRolls(UniformGenerator.between(0, 2))
                            .add(LootItem.lootTableItem(Items.ROTTEN_FLESH))
                    );
                    ZPUtility.sides().onlyClient(() -> ZPEntities.registerZombieRenderer(e, utils));
                    ZPEntityMobAttributes.addNewAttributeCreationUnsafe(e, ZPCommonZombie::createAttributes);
                }).end();

        ZPEntities.zp_miner_zombie_entity = regSupplier.register("zp_miner_zombie_entity", () -> EntityType.Builder.<ZPMinerZombie>of(ZPMinerZombie::new, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(8)
                        .build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "zp_miner_zombie_entity").toString()))
                .afterCreated((e, utils) -> {
                    utils.loot().addEntityLootTable(e, () -> new LootPool.Builder()
                            .setRolls(UniformGenerator.between(0, 2))
                            .add(LootItem.lootTableItem(Items.ROTTEN_FLESH))
                    );
                    ZPUtility.sides().onlyClient(() -> ZPEntities.registerMinerZombieRenderer(e, utils));
                    ZPEntityMobAttributes.addNewAttributeCreationUnsafe(e, ZPCommonZombie::createAttributes);
                }).end();

        ZPEntities.zp_dog_zombie_entity = regSupplier.register("zp_dog_zombie_entity", () -> EntityType.Builder.<ZPDogZombie>of(ZPDogZombie::new, MobCategory.MONSTER)
                        .sized(0.6f, 0.85f)
                        .clientTrackingRange(8)
                        .build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "zp_dog_zombie_entity").toString()))
                .afterCreated((e, utils) -> {
                    utils.loot().addEntityLootTable(e, () -> new LootPool.Builder()
                            .setRolls(UniformGenerator.between(0, 2))
                            .add(LootItem.lootTableItem(Items.ROTTEN_FLESH))
                    );
                    ZPUtility.sides().onlyClient(() -> ZPEntities.registerDogZombieRenderer(e, utils));
                    ZPEntityMobAttributes.addNewAttributeCreationUnsafe(e, ZPDogZombie::createAttributes);
                }).end();
    }

    @Override
    protected void postRegister(String name, RegistryObject<EntityType<?>> object) {
        super.postRegister(name, object);
        {
            List<String> allBiomes = List.of(
                    "minecraft:the_void",
                    "minecraft:plains",
                    "minecraft:sunflower_plains",
                    "minecraft:snowy_plains",
                    "minecraft:ice_spikes",
                    "minecraft:desert",
                    "minecraft:swamp",
                    "minecraft:mangrove_swamp",
                    "minecraft:forest",
                    "minecraft:flower_forest",
                    "minecraft:birch_forest",
                    "minecraft:dark_forest",
                    "minecraft:old_growth_birch_forest",
                    "minecraft:old_growth_pine_taiga",
                    "minecraft:old_growth_spruce_taiga",
                    "minecraft:taiga",
                    "minecraft:snowy_taiga",
                    "minecraft:savanna",
                    "minecraft:savanna_plateau",
                    "minecraft:windswept_hills",
                    "minecraft:windswept_gravelly_hills",
                    "minecraft:windswept_forest",
                    "minecraft:windswept_savanna",
                    "minecraft:jungle",
                    "minecraft:sparse_jungle",
                    "minecraft:bamboo_jungle",
                    "minecraft:badlands",
                    "minecraft:eroded_badlands",
                    "minecraft:wooded_badlands",
                    "minecraft:meadow",
                    "minecraft:cherry_grove",
                    "minecraft:grove",
                    "minecraft:snowy_slopes",
                    "minecraft:frozen_peaks",
                    "minecraft:jagged_peaks",
                    "minecraft:stony_peaks",
                    "minecraft:river",
                    "minecraft:frozen_river",
                    "minecraft:beach",
                    "minecraft:snowy_beach",
                    "minecraft:stony_shore",
                    "minecraft:warm_ocean",
                    "minecraft:lukewarm_ocean",
                    "minecraft:deep_lukewarm_ocean",
                    "minecraft:ocean",
                    "minecraft:deep_ocean",
                    "minecraft:cold_ocean",
                    "minecraft:deep_cold_ocean",
                    "minecraft:frozen_ocean",
                    "minecraft:deep_frozen_ocean",
                    "minecraft:mushroom_fields",
                    "minecraft:dripstone_caves",
                    "minecraft:lush_caves",
                    "minecraft:deep_dark",
                    "minecraft:nether_wastes",
                    "minecraft:warped_forest",
                    "minecraft:crimson_forest",
                    "minecraft:soul_sand_valley",
                    "minecraft:basalt_deltas",
                    "minecraft:the_end",
                    "minecraft:end_highlands",
                    "minecraft:end_midlands",
                    "minecraft:small_end_islands",
                    "minecraft:end_barrens"
            );
            List<String> overworldHostiles = List.of(
                    "minecraft:zombie",
                    "minecraft:zombie_villager",
                    "minecraft:husk",
                    "minecraft:drowned",
                    "minecraft:skeleton",
                    "minecraft:stray",
                    "minecraft:spider",
                    "minecraft:cave_spider",
                    "minecraft:witch",
                    "minecraft:slime",
                    "minecraft:enderman",
                    "minecraft:pillager",
                    "minecraft:ravager",
                    "minecraft:creeper",
                    "minecraft:phantom",
                    "minecraft:vindicator",
                    "minecraft:evoker",
                    "minecraft:illusioner",
                    "minecraft:warden",
                    "minecraft:allay"
            );

            ZPDataGenHelper.addNewBiomeSpawnAddModifier(new ZPBiomeModifyingHelper.ModifyEntryAddSpawns("add_zp3_common_zombie", allBiomes,
                    new ZPBiomeModifyingHelper.SpawnerEntry(() -> Objects.requireNonNull(ZPEntities.zp_common_zombie_entity.getKey()).location().toString(), 100, 1, 4)
            ));
            ZPDataGenHelper.addNewBiomeSpawnAddModifier(new ZPBiomeModifyingHelper.ModifyEntryAddSpawns("add_zp3_dog_zombie", allBiomes,
                    new ZPBiomeModifyingHelper.SpawnerEntry(() -> Objects.requireNonNull(ZPEntities.zp_dog_zombie_entity.getKey()).location().toString(), 6, 2, 4)
            ));
            ZPDataGenHelper.addNewBiomeSpawnAddModifier(new ZPBiomeModifyingHelper.ModifyEntryAddSpawns("add_zp3_miner_zombie", allBiomes,
                    new ZPBiomeModifyingHelper.SpawnerEntry(() -> Objects.requireNonNull(ZPEntities.zp_miner_zombie_entity.getKey()).location().toString(), 65, 1, 2)
            ));
            ZPDataGenHelper.addNewBiomeSpawnRemoveModifier(new ZPBiomeModifyingHelper.ModifyEntryRemoveSpawns("remove_from_spawn", allBiomes, overworldHostiles));
        }
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}