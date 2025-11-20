package ru.gltexture.zpm3.assets.common.rendering.entities.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPDogZombie;
import ru.gltexture.zpm3.assets.common.rendering.entities.models.ZPCommonDogZombieModel;

@OnlyIn(Dist.CLIENT)
public class ZPDogZombieItemLayer extends RenderLayer<ZPDogZombie, ZPCommonDogZombieModel<ZPDogZombie>> {
   private final ItemInHandRenderer itemInHandRenderer;

   public ZPDogZombieItemLayer(RenderLayerParent<ZPDogZombie, ZPCommonDogZombieModel<ZPDogZombie>> pRenderer, ItemInHandRenderer pItemInHandRenderer) {
      super(pRenderer);
      this.itemInHandRenderer = pItemInHandRenderer;
   }

   public void render(PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, ZPDogZombie pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      pPoseStack.pushPose();

      pPoseStack.translate((this.getParentModel()).head.x / 16.0F, (this.getParentModel()).head.y / 16.0F, (this.getParentModel()).head.z / 16.0F);
      float f1 = 0.0f;
      pPoseStack.mulPose(Axis.ZP.rotation(f1));
      pPoseStack.mulPose(Axis.YP.rotationDegrees(pNetHeadYaw));
      pPoseStack.mulPose(Axis.XP.rotationDegrees(pHeadPitch));
      pPoseStack.translate(0.06F, 0.125F, -0.4F);
      pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

      ItemStack itemstack = pLivingEntity.getItemBySlot(EquipmentSlot.MAINHAND);
      this.itemInHandRenderer.renderItem(pLivingEntity, itemstack, ItemDisplayContext.GROUND, false, pPoseStack, pBuffer, pPackedLight);
      pPoseStack.popPose();
   }
}