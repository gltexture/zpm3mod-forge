package ru.gltexture.zpm3.engine.mixins.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemInHandRenderer.class)
public class ZPItemMixin {
    @Shadow @Final private ItemRenderer itemRenderer;
    @Unique
    private static double lastFrameTime = 0.0f;
    @Unique
    private static double deltaTime = -1.0f;

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void renderArmWithItem(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        Item itemToRender = pStack.getItem();
        ZPRenderHooks.ZPItemRenderingHook itemRenderingHook = ZPRenderHooksManager.INSTANCE.getItemRenderingHooks().get(itemToRender);

        if (itemRenderingHook != null) {
            itemRenderingHook.onRender(pPlayer, (float) ZPItemMixin.deltaTime, pPartialTicks, pPitch, pHand, pSwingProgress, pStack, pEquippedProgress, pPoseStack, pBuffer, pCombinedLight);
            ci.cancel();
        }
    }

    @Inject(method = "renderHandsWithItems", at = @At("HEAD"))
    private void renderHandsWithItems1(float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight, CallbackInfo ci) {
        double currentTime = GLFW.glfwGetTime();
        ZPItemMixin.deltaTime = currentTime - lastFrameTime;
        ZPItemMixin.lastFrameTime = currentTime;
        ZPRenderHooksManager.INSTANCE.getItemSceneRenderingHooksPre().forEach(e -> e.onPreRender((float) ZPItemMixin.deltaTime, pPartialTicks, pPoseStack, pBuffer, pPlayerEntity, pCombinedLight));
    }

    @Inject(method = "renderHandsWithItems", at = @At("TAIL"))
    private void renderHandsWithItems2(float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight, CallbackInfo ci) {
        ZPRenderHooksManager.INSTANCE.getItemSceneRenderingHooksPost().forEach(e -> e.onPostRender((float) ZPItemMixin.deltaTime, pPartialTicks, pPoseStack, pBuffer, pPlayerEntity, pCombinedLight));
    }
}