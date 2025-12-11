package ru.gltexture.zpm3.assets.entity.rendering.entities.zombies.base;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.entity.rendering.entities.models.ZPCommonZombieModel;

@OnlyIn(Dist.CLIENT)
public abstract class ZPAbstractZombieRenderer<T extends ZPAbstractZombie, M extends ZPCommonZombieModel<T>> extends HumanoidMobRenderer<T, M> {
   protected ZPAbstractZombieRenderer(EntityRendererProvider.Context pContext, M pModel, M pInnerModel, M pOuterModel) {
      super(pContext, pModel, 0.5F);
      this.addLayer(new HumanoidArmorLayer<>(this, pInnerModel, pOuterModel, pContext.getModelManager()));
   }
}