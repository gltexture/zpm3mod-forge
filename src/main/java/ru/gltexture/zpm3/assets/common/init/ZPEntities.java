package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.events.common.ZPMobAttributes;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPDogZombie;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPMinerZombie;
import ru.gltexture.zpm3.assets.common.instances.entities.throwables.*;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPCommonZombie;
import ru.gltexture.zpm3.assets.common.rendering.entities.zombies.ZPCommonZombieRender;
import ru.gltexture.zpm3.assets.common.rendering.entities.zombies.ZPDogZombieRenderer;
import ru.gltexture.zpm3.assets.common.rendering.entities.zombies.ZPMinerZombieRender;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.helpers.ZPEntityRenderMatchHelper;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.assets.common.rendering.entities.misc.ZPThrowableEntityRender;

public class ZPEntities extends ZPRegistry<EntityType<?>> {
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

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<EntityType<? extends Entity>> regSupplier) {
        ZPEntities.acid_bottle_entity = regSupplier.register("acid_bottle_entity", () -> EntityType.Builder.<ZPAcidBottleEntity>of(ZPAcidBottleEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "acid_bottle_entity").toString())).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.entities().matchEntityRendering(e, ZPThrowableEntityRender::new);
        }).registryObject();

        ZPEntities.plate_entity = regSupplier.register("plate_entity", () -> EntityType.Builder.<ZPPlateEntity>of(ZPPlateEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "plate_entity").toString())).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.entities().matchEntityRendering(e, ZPThrowableEntityRender::new);
        }).registryObject();

        ZPEntities.rock_entity = regSupplier.register("rock_entity", () -> EntityType.Builder.<ZPRockEntity>of(ZPRockEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "rock_entity").toString())).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.entities().matchEntityRendering(e, ZPThrowableEntityRender::new);
        }).registryObject();

        ZPEntities.brock_entity = regSupplier.register("brock_entity", () -> EntityType.Builder.<ZPBrickEntity>of(ZPBrickEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "brock_entity").toString())).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.entities().matchEntityRendering(e, ZPThrowableEntityRender::new);
        }).registryObject();

        ZPEntities.rotten_flesh_entity = regSupplier.register("rotten_flesh_entity", () -> EntityType.Builder.<ZPRottenFleshEntity>of(ZPRottenFleshEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "rotten_flesh_entity").toString())).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.entities().matchEntityRendering(e, ZPThrowableEntityRender::new);
        }).registryObject();

        ZPEntities.zp_common_zombie_entity = regSupplier.register("zp_common_zombie_entity", () -> EntityType.Builder.<ZPCommonZombie>of(ZPCommonZombie::new, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(8)
                        .build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "zp_common_zombie_entity").toString()))
                .postConsume(Dist.CLIENT, (e, utils) -> {
                    utils.entities().matchEntityRendering(e, (c) -> new ZPCommonZombieRender(c, "textures/entity/zombie_common/citizen%d.png", ZPConstants.TOTAL_COMMON_ZOMBIE_TEXTURES));
                    ZPMobAttributes.addNewAttributeCreationUnsafe(e, ZPCommonZombie::createAttributes);
                }).registryObject();

        ZPEntities.zp_miner_zombie_entity = regSupplier.register("zp_miner_zombie_entity", () -> EntityType.Builder.<ZPMinerZombie>of(ZPMinerZombie::new, MobCategory.MONSTER)
                        .sized(0.6f, 1.95f)
                        .clientTrackingRange(8)
                        .build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "zp_miner_zombie_entity").toString()))
                .postConsume(Dist.CLIENT, (e, utils) -> {
                    utils.entities().matchEntityRendering(e, (c) -> new ZPMinerZombieRender(c, "textures/entity/zombie_miner/miner%d.png", ZPConstants.TOTAL_MINER_ZOMBIE_TEXTURES));
                    ZPMobAttributes.addNewAttributeCreationUnsafe(e, ZPMinerZombie::createAttributes);
                }).registryObject();

        ZPEntities.zp_dog_zombie_entity = regSupplier.register("zp_dog_zombie_entity", () -> EntityType.Builder.<ZPDogZombie>of(ZPDogZombie::new, MobCategory.MONSTER)
                        .sized(0.6f, 0.85f)
                        .clientTrackingRange(8)
                        .build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "zp_dog_zombie_entity").toString()))
                .postConsume(Dist.CLIENT, (e, utils) -> {
                    utils.entities().matchEntityRendering(e, ZPDogZombieRenderer::new);
                    ZPMobAttributes.addNewAttributeCreationUnsafe(e, ZPDogZombie::createAttributes);
                }).registryObject();
    }

    @Override
    protected void postRegister(String name, RegistryObject<EntityType<?>> object) {
        super.postRegister(name, object);
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