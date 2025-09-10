package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPAcidBottleEntity;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPPlateEntity;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPRockEntity;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.helpers.ZPEntityRenderMatchHelper;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.client.rendering.entities.ZPThrowableEntityRender;

public class ZPEntities extends ZPRegistry<EntityType<?>> {
    public static RegistryObject<EntityType<ZPAcidBottleEntity>> acid_bottle_entity;
    public static RegistryObject<EntityType<ZPPlateEntity>> plate_entity;
    public static RegistryObject<EntityType<ZPRockEntity>> rock_entity;

    public ZPEntities() {
        super(ZPRegistryConveyor.Target.ENTITY_TYPE);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<EntityType<? extends Entity>> regSupplier) {
        ZPEntities.acid_bottle_entity = regSupplier.register("acid_bottle_entity", () -> EntityType.Builder.<ZPAcidBottleEntity>of(ZPAcidBottleEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "acid_bottle_entity").toString())).postConsume(Dist.CLIENT, (e, utils) -> {
            ZPEntityRenderMatchHelper.matchEntityRendering(e, ZPThrowableEntityRender::new);
        }).registryObject();

        ZPEntities.plate_entity = regSupplier.register("plate_entity", () -> EntityType.Builder.<ZPPlateEntity>of(ZPPlateEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "plate_entity").toString())).postConsume(Dist.CLIENT, (e, utils) -> {
            ZPEntityRenderMatchHelper.matchEntityRendering(e, ZPThrowableEntityRender::new);
        }).registryObject();

        ZPEntities.rock_entity = regSupplier.register("rock_entity", () -> EntityType.Builder.<ZPRockEntity>of(ZPRockEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), "rock_entity").toString())).postConsume(Dist.CLIENT, (e, utils) -> {
            ZPEntityRenderMatchHelper.matchEntityRendering(e, ZPThrowableEntityRender::new);
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