package ru.gltexture.zpm3.assets.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPEntities;
import ru.gltexture.zpm3.assets.entity.events.common.*;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.entity.rendering.entities.misc.ZPRenderEntityItem;
import ru.gltexture.zpm3.assets.entity.population.ZPSetupPopulation;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.helpers.ZPEntityRenderMatchHelper;
import ru.gltexture.zpm3.engine.population.ZPPopulationController;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPEntityAsset extends ZPAsset {
    public ZPEntityAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPEntityAsset() {
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

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("entities", "ru.gltexture.zpm3.assets.entity.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("entity.ZPForgeItemMixin", ZPSide.COMMON)
    //    );
    //}

    @Override
    public void initializeAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.setPopulationSetup(new ZPEntityAsset.ZPCommonPopulationSetup());
        assetEntry.addEventClass(ZPEntitySpawnEvent.class);
        assetEntry.addEventClass(ZPEntityTickEvent.class);
        assetEntry.addEventClass(ZPEntityLivingEvents.class);
        assetEntry.addEventClass(ZPEntityMobAttributes.class);
        assetEntry.addEventClass(ZPWorldTickEvent.class);
    }

    @Override
    public void preCommonInitializeAsset() {
        ZPUtility.sides().onlyClient(() -> {
            ZPEntityRenderMatchHelper.matchEntityRendering(() -> EntityType.ITEM, ZPRenderEntityItem::new);
        });
    }

    @Override
    public void postCommonInitializeAsset() {

    }

    private static class ZPCommonPopulationSetup extends ZPSetupPopulation {
        public static void caveSpawns(MobSpawnSettings.Builder pBuilder) {
            pBuilder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
            pBuilder.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6));
        }

        @Override
        public void setup(@NotNull ZPPopulationController controller) {
            {
                controller.getVanillaBiomePopulationManager().setCancelVanilla_monsters_Method(true);
                controller.getVanillaBiomePopulationManager().addMonster_Consumer(((pBuilder, pZombieWeight, pZombieVillageWeight,pSkeletonWeight,pIsUnderwater) -> {
                    pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ZPEntities.zp_common_zombie_entity.get(), 100, 1, 4));
                    pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ZPEntities.zp_miner_zombie_entity.get(), 80, 1, 2));
                    pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ZPEntities.zp_dog_zombie_entity.get(), 20, 2, 5));
                    return null;
                }));

                controller.getVanillaBiomePopulationManager().setCancelVanilla_oceanSpawns_Method(true);
                controller.getVanillaBiomePopulationManager().addOceanSpawn_Consumer((pBuilder, pSquidWeight, pSquidMaxCount, pCodWeight) -> {
                    pBuilder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, pSquidWeight, 1, pSquidMaxCount));
                    pBuilder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, pCodWeight, 3, 6));
                    return null;
                });

                controller.getVanillaBiomePopulationManager().setCancelVanilla_warmOceanSpawns_Method(true);
                controller.getVanillaBiomePopulationManager().addWarmOceanSpawn_Consumer((pBuilder, pSquidWeight, pSquidMinCount) -> {
                    pBuilder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, pSquidWeight, pSquidMinCount, 4));
                    pBuilder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 8, 8));
                    pBuilder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 2, 1, 2));
                    return null;
                });

                controller.getVanillaBiomePopulationManager().setCancelVanilla_snowySpawns_Method(true);
                controller.getVanillaBiomePopulationManager().addSnowySpawn_Consumer((pBuilder) -> {
                    pBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 2, 3));
                    pBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2));
                    caveSpawns(pBuilder);
                });

                controller.getVanillaBiomePopulationManager().setCancelVanilla_desertSpawns_Method(true);
                controller.getVanillaBiomePopulationManager().addDesertSpawn_Consumer((pBuilder) -> {
                    pBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
                    caveSpawns(pBuilder);
                });

                controller.getVanillaBiomePopulationManager().setCancelVanilla_dripstoneCavesSpawns_Method(true);
                controller.getVanillaBiomePopulationManager().addDripstoneCaveSpawn_Consumer((pBuilder) -> {
                    caveSpawns(pBuilder);
                });

                controller.addREPLACE_Rule(() -> ZPEntities.zp_common_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZPAbstractZombie::checkZombieSpawnRules);
                controller.addREPLACE_Rule(() -> ZPEntities.zp_miner_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, level, spawnType, pos, random) -> (level.getBlockState(pos).is(Blocks.CAVE_AIR) || ZPRandom.getRandom().nextFloat() <= 0.01f) && ZPAbstractZombie.checkZombieSpawnRules(entityType, level, spawnType, pos, random));
                controller.addREPLACE_Rule(() -> ZPEntities.zp_dog_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, level, spawnType, pos, random) -> (!level.getBlockState(pos).is(Blocks.CAVE_AIR)) && ZPAbstractZombie.checkZombieSpawnRules(entityType, level, spawnType, pos, random));
            }
        }
    }
}
