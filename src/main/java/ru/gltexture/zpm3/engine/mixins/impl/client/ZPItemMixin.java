package ru.gltexture.zpm3.engine.mixins.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.client.rendering.items.ZPItemRenderingProcessor;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemInHandRenderer.class)
public class ZPItemMixin {
    @Shadow @Final private ItemRenderer itemRenderer;

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void renderArmWithItem(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        Item itemToRender = pStack.getItem();
        ZPItemRenderingProcessor itemRenderingProcessor = ZPRenderHelper.INSTANCE.getItemRenderingMatcher().getRenderingProcessor(itemToRender);

        if (itemRenderingProcessor != null) {
            itemRenderingProcessor.gunRenderer().onPreRender(pPartialTicks);
            itemRenderingProcessor.gunRenderer().onRender(pPartialTicks);
            itemRenderingProcessor.gunRenderer().onPostRender(pPartialTicks);

            ci.cancel();
        }
    }
}