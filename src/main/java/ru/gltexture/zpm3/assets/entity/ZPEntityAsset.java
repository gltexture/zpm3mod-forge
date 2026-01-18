package ru.gltexture.zpm3.assets.entity;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
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
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.helpers.ZPBiomeModifyingHelper;
import ru.gltexture.zpm3.engine.helpers.ZPEntityRenderMatchHelper;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.population.ZPPopulationController;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.List;
import java.util.Objects;

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
    //            new ZombiePlague3.IMixinEntry.MixinClass("entity.ZPEntityItemMixin", ZPSide.COMMON)
    //    );
    //}

    @Override
    public void initializeAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
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
                    new ZPBiomeModifyingHelper.SpawnerEntry(Objects.requireNonNull(ZPEntities.zp_common_zombie_entity.getKey()).location().toString(), 100, 1, 4)
            ));
            ZPDataGenHelper.addNewBiomeSpawnAddModifier(new ZPBiomeModifyingHelper.ModifyEntryAddSpawns("add_zp3_dog_zombie", allBiomes,
                    new ZPBiomeModifyingHelper.SpawnerEntry(Objects.requireNonNull(ZPEntities.zp_dog_zombie_entity.getKey()).location().toString(), 6, 2, 4)
            ));
            ZPDataGenHelper.addNewBiomeSpawnAddModifier(new ZPBiomeModifyingHelper.ModifyEntryAddSpawns("add_zp3_miner_zombie", allBiomes,
                    new ZPBiomeModifyingHelper.SpawnerEntry(Objects.requireNonNull(ZPEntities.zp_miner_zombie_entity.getKey()).location().toString(), 65, 1, 2)
            ));
            ZPDataGenHelper.addNewBiomeSpawnRemoveModifier(new ZPBiomeModifyingHelper.ModifyEntryRemoveSpawns("remove_from_spawn", allBiomes, overworldHostiles));
        }

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
                controller.addREPLACE_Rule(() -> ZPEntities.zp_common_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZPAbstractZombie::checkZombieSpawnRules);
                controller.addREPLACE_Rule(() -> ZPEntities.zp_miner_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, level, spawnType, pos, random) -> (level.getBlockState(pos).is(Blocks.CAVE_AIR) || ZPRandom.getRandom().nextFloat() <= 0.03f) && ZPAbstractZombie.checkZombieSpawnRules(entityType, level, spawnType, pos, random));
                controller.addREPLACE_Rule(() -> ZPEntities.zp_dog_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, level, spawnType, pos, random) -> (!level.getBlockState(pos).is(Blocks.CAVE_AIR)) && ZPAbstractZombie.checkZombieSpawnRules(entityType, level, spawnType, pos, random));
            }
        }
    }
}
