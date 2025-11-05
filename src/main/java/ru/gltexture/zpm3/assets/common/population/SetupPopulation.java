package ru.gltexture.zpm3.assets.common.population;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.population.ZPPopulationController;

public abstract class SetupPopulation {
    public static void setup(@NotNull ZPPopulationController controller) {
        controller.getVanillaBiomePopulationManager().setCancelVanilla_monsters_Method(true);
        controller.getVanillaBiomePopulationManager().addMonster_Consumer(((pBuilder, pZombieWeight, pZombieVillageWeight,pSkeletonWeight,pIsUnderwater) -> {
            pBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 120, 16, 16));
            return null;
        }));
    }
}