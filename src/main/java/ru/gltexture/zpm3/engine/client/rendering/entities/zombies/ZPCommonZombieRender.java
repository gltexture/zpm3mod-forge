package ru.gltexture.zpm3.engine.client.rendering.entities.zombies;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.ZPCommonZombie;
import ru.gltexture.zpm3.engine.client.rendering.entities.models.ZPCommonZombieModel;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.util.ArrayList;
import java.util.List;

public class ZPCommonZombieRender extends ZPAbstractZombieRenderer<ZPCommonZombie, ZPCommonZombieModel<ZPCommonZombie>> {
    private static final List<ResourceLocation> ZOMBIE_LOCATION = new ArrayList<>();

    static {
        for (int i = 0; i < ZPConstants.TOTAL_COMMON_ZOMBIE_TEXTURES; i++) {
            ZPCommonZombieRender.ZOMBIE_LOCATION.add(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), String.format("textures/entity/zombie_common/citizen%d.png", i)));
        }
    }

    public ZPCommonZombieRender(EntityRendererProvider.Context p_174456_) {
        this(p_174456_, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public ZPCommonZombieRender(EntityRendererProvider.Context pContext, ModelLayerLocation pZombieLayer, ModelLayerLocation pInnerArmor, ModelLayerLocation pOuterArmor) {
        super(pContext, new ZPCommonZombieModel<>(pContext.bakeLayer(pZombieLayer)), new ZPCommonZombieModel<>(pContext.bakeLayer(pInnerArmor)), new ZPCommonZombieModel<>(pContext.bakeLayer(pOuterArmor)));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ZPCommonZombie pEntity) {
        return ZPCommonZombieRender.ZOMBIE_LOCATION.get(pEntity.getSkinID());
    }
}
