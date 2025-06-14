package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.entities.ZPAcidBottleEntity;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.helpers.ZPEntityRenderMatching;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;
import ru.gltexture.zpm3.engine.utils.ZPUtils;

public class ZPEntities extends ZPRegistry<EntityType<?>> {
    public static RegistryObject<EntityType<ZPAcidBottleEntity>> acid_bottle_entity;

    public ZPEntities() {
        super(ForgeRegistries.ENTITY_TYPES, ZPRegistryConveyor.Target.ENTITY);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<EntityType<? extends Entity>> regSupplier) {
        ZPEntities.acid_bottle_entity = regSupplier.register("acid_bottle_entity", () -> EntityType.Builder.<ZPAcidBottleEntity>of(ZPAcidBottleEntity::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build(new ResourceLocation(ZombiePlague3.MOD_ID(), "acid_bottle_entity").toString())
        );

        ZPUtils.onlyClient(() -> {
            ZPEntityRenderMatching.match(ZPEntities.acid_bottle_entity, ThrownItemRenderer::new);
        });
    }

    @Override
    protected void postRegister(String name, RegistryObject<EntityType<?>> object) {
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