package ru.gltexture.zpm3.assets.common.population;

import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPEntities;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.core.population.ZPPopulationController;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

public abstract class SetupPopulation {
    public static void setup(@NotNull ZPPopulationController controller) {
        controller.getVanillaBiomePopulationManager().setCancelVanilla_monsters_Method(true);
        controller.getVanillaBiomePopulationManager().addMonster_Consumer(((pBuilder, pZombieWeight, pZombieVillageWeight,pSkeletonWeight,pIsUnderwater) -> {
            pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ZPEntities.zp_common_zombie_entity.get(), 100, 1, 4));
            pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ZPEntities.zp_miner_zombie_entity.get(), 80, 1, 2));
            pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ZPEntities.zp_dog_zombie_entity.get(), 20, 2, 5));
            return null;
        }));

        controller.addREPLACE_Rule(() -> ZPEntities.zp_common_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZPAbstractZombie::checkZombieSpawnRules);
        controller.addREPLACE_Rule(() -> ZPEntities.zp_miner_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, level, spawnType, pos, random) -> (level.getBlockState(pos).is(Blocks.CAVE_AIR) || ZPRandom.getRandom().nextFloat() <= 0.01f) && ZPAbstractZombie.checkZombieSpawnRules(entityType, level, spawnType, pos, random));
        controller.addREPLACE_Rule(() -> ZPEntities.zp_dog_zombie_entity.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, level, spawnType, pos, random) -> (!level.getBlockState(pos).is(Blocks.CAVE_AIR)) && ZPAbstractZombie.checkZombieSpawnRules(entityType, level, spawnType, pos, random));
    }
}