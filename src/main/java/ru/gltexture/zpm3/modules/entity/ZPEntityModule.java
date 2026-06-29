package ru.gltexture.zpm3.modules.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.entity.init.ZPEntities;
import ru.gltexture.zpm3.modules.entity.events.common.*;
import ru.gltexture.zpm3.modules.entity.init.ZPEntityAttributes;
import ru.gltexture.zpm3.modules.entity.init.ZPSpawnItems;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.modules.entity.rendering.entities.misc.ZPRenderEntityItem;
import ru.gltexture.zpm3.modules.entity.population.ZPSetupPopulation;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.helpers.ZPBiomeModifyingHelper;
import ru.gltexture.zpm3.engine.helpers.ZPEntityRenderMatchHelper;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.population.ZPPopulationController;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.List;
import java.util.Objects;

public class ZPEntityModule extends ZPModule {
    public ZPEntityModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPEntityModule() {
    }

    @Override
    public void fml_commonSetupEvent() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void fml_clientSetupEvent() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {

    }

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("entities", "ru.gltexture.zpm3.modules.entity.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("entity.ZPEntityItemLifespanMixin", ZPSide.COMMON)
    //    );
    //}

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        moduleEntry.addRegistryClass(ZPEntityAttributes.class);
        moduleEntry.addRegistryClass(ZPEntities.class);
        moduleEntry.addRegistryClass(ZPSpawnItems.class);

        moduleEntry.setPopulationSetup(new ZPEntityModule.ZPCommonPopulationSetup());
        moduleEntry.addEventClass(ZPEntitySpawnEvent.class);
        moduleEntry.addEventClass(ZPEntityTickEvent.class);
        moduleEntry.addEventClass(ZPEntityLivingEvents.class);
        moduleEntry.addEventClass(ZPEntityMobAttributes.class);
        moduleEntry.addEventClass(ZPWorldTickEvent.class);
    }

    @Override
    public void preInitialize() {
        ZPUtility.sides().onlyClient(() -> {
            ZPEntityRenderMatchHelper.matchEntityRendering(() -> EntityType.ITEM, ZPRenderEntityItem::new);
        });
    }

    @Override
    public void postInitialize() {

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
