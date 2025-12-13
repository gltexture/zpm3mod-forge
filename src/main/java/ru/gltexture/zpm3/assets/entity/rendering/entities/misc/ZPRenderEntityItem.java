package ru.gltexture.zpm3.assets.entity.rendering.entities.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import ru.gltexture.zpm3.assets.player.client.ZPClientGlobalSettings;
import ru.gltexture.zpm3.assets.player.events.client.ZPRenderWorldEventWithPickUpCheck;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.player.keybind.ZPPickUpKeyBindings;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.item.ZPGunPistol;

public class ZPRenderEntityItem extends ItemEntityRenderer {
    private final ItemRenderer itemRenderer;
    private final RandomSource random = RandomSource.create();
    private final RandomSource random2 = RandomSource.create();

    public ZPRenderEntityItem(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.itemRenderer = pContext.getItemRenderer();
        this.shadowStrength = 0.0f;
    }

    @Override
    public boolean shouldRender(@NotNull ItemEntity pLivingEntity, @NotNull Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return super.shouldRender(pLivingEntity, pCamera, pCamX, pCamY, pCamZ);
    }

    protected void renderPickUpTip(ItemEntity pEntity, PoseStack pPoseStack, MultiBufferSource pBuffer, float itScale) {
        KeyMapping km = ZPPickUpKeyBindings.pickItem;
        int keyCode = km.getKey().getValue();
        String letter = GLFW.glfwGetKeyName(keyCode, 0);
        if (letter == null) {
            letter = "NULL";
        }
        String text = "[" + letter.toUpperCase() + "] | " + pEntity.getDisplayName().getString();
        float f = pEntity.getNameTagOffsetY();
        pPoseStack.pushPose();
        pPoseStack.translate(0.0F, f, 0.0F);
        pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        final float f12 = 0.0125f;
        pPoseStack.scale(-f12, -f12, f12);
        Matrix4f matrix4f = pPoseStack.last().pose();
        Font font = this.getFont();
        float f2 = (float) (-font.width(text) / 2);
        font.drawInBatch(text, f2, 10.0f, ZPRenderWorldEventWithPickUpCheck.canBePickedUp(pEntity) ? 0x00ff00 : 0xc8c8c8, true, matrix4f, pBuffer, Font.DisplayMode.NORMAL, 0x00aaaaaa, 255);
        pPoseStack.popPose();
    }

    @Override
    public void render(@NotNull ItemEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        float scale = 0.0f;
        if (ZPConstants.FANCY_ITEM_ENTITIES) {
            pPoseStack.pushPose();
            ItemStack itemstack = pEntity.getItem();
            int itemId = Item.getId(itemstack.getItem()) * pEntity.getId();
            this.random2.setSeed(itemId);
            int i = itemstack.isEmpty() ? 187 : (this.random2.nextInt(Short.MAX_VALUE) + itemstack.getDamageValue() + pEntity.getId());
            this.random.setSeed(i);
            BakedModel bakedmodel = this.itemRenderer.getModel(itemstack, pEntity.level(), null, pEntity.getId());
            int j = this.getRenderAmount(itemstack);
            float scaling = this.getScaling(itemstack);
            scale = scaling;
            pPoseStack.translate(0.0f, 1.0e-8f * this.random2.nextInt(Short.MAX_VALUE), 0.0f);

            if (!pEntity.onGround()) {
                pPoseStack.mulPose(Axis.YP.rotation((float) (Math.PI * (pEntity.tickCount + pPartialTicks) * 0.01f)));
                pPoseStack.mulPose(Axis.ZP.rotation((float) (Math.PI * (pEntity.tickCount + pPartialTicks) * -0.025f)));
            } else {
                BlockPos entityPos = pEntity.blockPosition();
                BlockState stateBelow = pEntity.level().getBlockState(entityPos);
                float yOffset = 0f;
                if (stateBelow.is(Blocks.SNOW) || stateBelow.is(Blocks.SNOW_BLOCK)) {
                    yOffset = 0.125f;
                }
                pPoseStack.translate(0.0f, yOffset, 0.0f);
            }
            if (!bakedmodel.isGui3d()) {
                pPoseStack.translate(0.0F, 0.05f, 0.0F);
                pPoseStack.mulPose(Axis.XP.rotation((float) (-Math.PI / 2.0f)));
                pPoseStack.mulPose(Axis.ZP.rotation((float) (Math.PI * this.random.nextFloat() * 2.0f)));
            } else {
                pPoseStack.translate(0.0F, -0.1f, 0.0F);
                scaling += 1.0f;
            }
            pPoseStack.scale(scaling, scaling, scaling);
            for (int k = 0; k < j; ++k) {
                pPoseStack.pushPose();
                if (k > 0) {
                    float f12 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F * 0.5F;
                    float f14 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F * 0.5F;
                    pPoseStack.translate(this.shouldSpreadItems() ? f12 : 0, this.shouldSpreadItems() ? f14 : 0, 0.0D);
                }
                this.itemRenderer.render(itemstack, ItemDisplayContext.GROUND, false, pPoseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, bakedmodel);
                pPoseStack.popPose();
                pPoseStack.translate(0.0, 0.0, 0.01F);
            }
            pPoseStack.popPose();
        } else {
            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        }
        if (ZPConstants.PICK_UP_ON_F && ZPClientGlobalSettings.SERVER_PICK_UP_ON_F && ZPRenderWorldEventWithPickUpCheck.entityToPickUp != null && ZPRenderWorldEventWithPickUpCheck.entityToPickUp.equals(pEntity)) {
            this.renderPickUpTip(pEntity, pPoseStack, pBuffer, scale);
        }
    }

    private float getScaling(ItemStack stack) {
        if (stack.getItem() instanceof ZPBaseGun baseGun) {
            return baseGun.getGunProperties().getHeldType().equals(ZPBaseGun.GunProperties.HeldType.RIFLE) ? 2.4f : 1.8f;
        }
        if (stack.getItem() instanceof ZPGunPistol || stack.getItem() instanceof SwordItem || stack.getItem() instanceof PickaxeItem || stack.getItem() instanceof AxeItem || stack.getItem() instanceof ArmorItem || stack.getItem() instanceof BowItem || stack.getItem() instanceof ShovelItem) {
            return 1.45f;
        }
        if (stack.getItem() instanceof BlockItem) {
            return 0.25f;
        }
        return 1.15f;
    }
}
