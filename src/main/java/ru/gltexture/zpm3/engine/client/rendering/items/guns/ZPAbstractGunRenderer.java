package ru.gltexture.zpm3.engine.client.rendering.items.guns;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;

public abstract class ZPAbstractGunRenderer implements ZPRenderHooks.ZPItemRenderingHook{
    protected ZPAbstractGunRenderer() {
    }

    protected void renderItem(ItemRenderer itemRenderer, LivingEntity pEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pSeed) {
        if (!pItemStack.isEmpty()) {
            itemRenderer.renderStatic(pEntity, pItemStack, pDisplayContext, pLeftHand, pPoseStack, pBuffer, pEntity.level(), pSeed, OverlayTexture.NO_OVERLAY, pEntity.getId() + pDisplayContext.ordinal());
        }
    }
}