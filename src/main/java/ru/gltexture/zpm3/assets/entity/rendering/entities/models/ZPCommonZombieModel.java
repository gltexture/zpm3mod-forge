package ru.gltexture.zpm3.assets.entity.rendering.entities.models;

import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;

@OnlyIn(Dist.CLIENT)
public class ZPCommonZombieModel<T extends ZPAbstractZombie> extends AbstractZombieModel<T> {
   public ZPCommonZombieModel(ModelPart pRoot) {
      super(pRoot);
   }

   public boolean isAggressive(T pEntity) {
      return pEntity.isAggressive();
   }
}