package ru.gltexture.zpm3.assets.common.rendering.entities.zombies.base;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.common.rendering.entities.models.ZPCommonZombieModel;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.util.ArrayList;
import java.util.List;

public abstract class ZPDefaultZombieRender <R extends ZPAbstractZombie> extends ZPAbstractZombieRenderer<R, ZPCommonZombieModel<R>> {
    private List<ResourceLocation> ZOMBIE_LOCATION;

    public ZPDefaultZombieRender(EntityRendererProvider.Context p_174456_, @NotNull String path, int maxTextures) {
        this(p_174456_, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
        this.ZOMBIE_LOCATION = new ArrayList<>();
        for (int i = 0; i < maxTextures; i++) {
            this.ZOMBIE_LOCATION.add(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), String.format(path, i)));
        }
    }

    public ZPDefaultZombieRender(EntityRendererProvider.Context pContext, ModelLayerLocation pZombieLayer, ModelLayerLocation pInnerArmor, ModelLayerLocation pOuterArmor) {
        super(pContext, new ZPCommonZombieModel<>(pContext.bakeLayer(pZombieLayer)), new ZPCommonZombieModel<>(pContext.bakeLayer(pInnerArmor)), new ZPCommonZombieModel<>(pContext.bakeLayer(pOuterArmor)));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull R pEntity) {
        return this.ZOMBIE_LOCATION.get(pEntity.getSkinID());
    }
}
