package ru.gltexture.zpm3.engine.mixins.inst;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;

@OnlyIn(Dist.CLIENT)
public class HumanoidArmorLayerOnArm<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
   private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
   private final A outerModel;
   private final TextureAtlas armorTrimAtlas;

   public HumanoidArmorLayerOnArm(RenderLayerParent<T, M> pRenderer, A pOuterModel, ModelManager pModelManager) {
      super(pRenderer);
      this.outerModel = pOuterModel;
      this.armorTrimAtlas = pModelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
   }

   public void render(@NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, @NotNull T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
   }

   //PUBLIC
   public void renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, boolean rightArm, T entity, int packedLight) {
      final EquipmentSlot slot = EquipmentSlot.CHEST;
      ItemStack itemstack = entity.getItemBySlot(slot);
      Item item = itemstack.getItem();

      if (!(item instanceof ArmorItem armorItem) || armorItem.getEquipmentSlot() != slot) {
         return;
      }

      A armorModel = this.outerModel;
      armorModel.head.visible = false;
      armorModel.hat.visible = false;
      armorModel.body.visible = false;
      armorModel.leftArm.visible = !rightArm;
      armorModel.rightArm.visible = rightArm;
      armorModel.leftLeg.visible = false;
      armorModel.rightLeg.visible = false;

      net.minecraft.client.model.Model model = getArmorModelHook(entity, itemstack, slot, armorModel);
      boolean useInnerModel = this.usesInnerModel(slot);
      ResourceLocation texture = this.getArmorResource(entity, itemstack, slot, null);

      Matrix4f translate = new Matrix4f().identity();

      float x = 0.515f;
      float y = -1.665f;

      translate
              .translate(new Vector3f(rightArm ? x : -x, y, 0.0f))
              .rotateX((float) Math.toRadians(0.0f))
              .rotateY((float) Math.toRadians(0.0f))
              .rotateZ((float) Math.toRadians(0.0f))
              .scale(1.55f, 2.0f, 1.55f);

      poseStack.pushPose();
      if (rightArm) {
         poseStack.mulPoseMatrix(translate);
         this.getParentModel().rightArm.translateAndRotate(poseStack);
      } else {
         poseStack.mulPoseMatrix(translate);
         this.getParentModel().leftArm.translateAndRotate(poseStack);
      }

      if (armorItem instanceof net.minecraft.world.item.DyeableLeatherItem dyeable) {
         int color = dyeable.getColor(itemstack);
         float r = (color >> 16 & 255) / 255.0F;
         float g = (color >> 8 & 255) / 255.0F;
         float b = (color & 255) / 255.0F;

         this.renderModel(poseStack, buffer, packedLight, armorItem, model, useInnerModel, r, g, b, texture);
         this.renderModel(poseStack, buffer, packedLight, armorItem, model, useInnerModel, 1.0F, 1.0F, 1.0F, this.getArmorResource(entity, itemstack, slot, "overlay"));
      } else {
         this.renderModel(poseStack, buffer, packedLight, armorItem, model, useInnerModel, 1.0F, 1.0F, 1.0F, texture);
      }

      ArmorTrim.getTrim(entity.level().registryAccess(), itemstack).ifPresent(trim -> {
         this.renderTrim(armorItem.getMaterial(), poseStack, buffer, packedLight, trim, model, useInnerModel);
      });

      if (itemstack.hasFoil()) {
         this.renderGlint(poseStack, buffer, packedLight, model);
      }

      poseStack.popPose();
   }

   private void renderModel(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, ArmorItem pArmorItem, net.minecraft.client.model.Model pModel, boolean pWithGlint, float pRed, float pGreen, float pBlue, ResourceLocation armorResource) {
      VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.armorCutoutNoCull(armorResource));
      pModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, pRed, pGreen, pBlue, 1.0F);
   }

   private void renderTrim(ArmorMaterial pArmorMaterial, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, ArmorTrim pTrim, net.minecraft.client.model.Model pModel, boolean pInnerTexture) {
      TextureAtlasSprite textureatlassprite = this.armorTrimAtlas.getSprite(pInnerTexture ? pTrim.innerTexture(pArmorMaterial) : pTrim.outerTexture(pArmorMaterial));
      VertexConsumer vertexconsumer = textureatlassprite.wrap(pBuffer.getBuffer(Sheets.armorTrimsSheet()));
      pModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderGlint(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, net.minecraft.client.model.Model pModel) {
      pModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.armorEntityGlint()), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   private boolean usesInnerModel(EquipmentSlot pSlot) {
      return pSlot == EquipmentSlot.LEGS;
   }

   @SuppressWarnings("all")
   private ResourceLocation getArmorLocation(ArmorItem pArmorItem, boolean pLayer2, @Nullable String pSuffix) {
      String s = "textures/models/armor/" + pArmorItem.getMaterial().getName() + "_layer_" + (pLayer2 ? 2 : 1) + (pSuffix == null ? "" : "_" + pSuffix) + ".png";
      return ARMOR_LOCATION_CACHE.computeIfAbsent(s, ResourceLocation::new);
   }

   @SuppressWarnings("all")
   protected net.minecraft.client.model.Model getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model) {
      return net.minecraftforge.client.ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
   }

   @SuppressWarnings("all")
   public ResourceLocation getArmorResource(net.minecraft.world.entity.Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
      ArmorItem item = (ArmorItem) stack.getItem();
      String texture = item.getMaterial().getName();
      String domain = "minecraft";
      int idx = texture.indexOf(':');
      if (idx != -1) {
         domain = texture.substring(0, idx);
         texture = texture.substring(idx + 1);
      }
      String s1 = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (usesInnerModel(slot) ? 2 : 1), type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));

      s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
      ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);

      if (resourcelocation == null) {
         resourcelocation = new ResourceLocation(s1);
         ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
      }

      return resourcelocation;
   }
}