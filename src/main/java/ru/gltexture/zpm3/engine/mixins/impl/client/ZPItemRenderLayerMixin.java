package ru.gltexture.zpm3.engine.mixins.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
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

@Mixin(ItemInHandLayer.class)
@OnlyIn(Dist.CLIENT)
public abstract class ZPItemRenderLayerMixin<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> extends RenderLayer<T, M> {
    @Shadow @Final private ItemInHandRenderer itemInHandRenderer;
    @Unique
    private static double lastFrameTime = 0.0f;
    @Unique
    private static double deltaTime = -1.0f;

    public ZPItemRenderLayerMixin(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    @Inject(method = "render*", at = @At("HEAD"))
    public void render1(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        double currentTime = GLFW.glfwGetTime();
        ZPItemRenderLayerMixin.deltaTime = currentTime - lastFrameTime;
        ZPItemRenderLayerMixin.lastFrameTime = currentTime;
        ZPRenderHooksManager.INSTANCE.getItemSceneRendering3PersonHooksPre().forEach(e -> e.onPreRender3Person((float) ZPItemRenderLayerMixin.deltaTime, pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks, pAgeInTicks, pNetHeadYaw, pHeadPitch));
    }

    @Inject(method = "render*", at = @At("TAIL"))
    public void render2(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        ZPRenderHooksManager.INSTANCE.getItemSceneRendering3PersonHooksPost().forEach(e -> e.onPostRender3Person((float) ZPItemRenderLayerMixin.deltaTime, pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks, pAgeInTicks, pNetHeadYaw, pHeadPitch));
    }

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    public void renderArmWithItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {

        if (!pItemStack.isEmpty()) {
            Item itemToRender = pItemStack.getItem();
            ZPRenderHooks.ZPItemRendering3PersonHook itemRenderingHook = ZPRenderHooksManager.INSTANCE.getItemRendering3PersonHooks().get(itemToRender);
            if (itemRenderingHook != null) {
                itemRenderingHook.onRenderItem3Person(this.itemInHandRenderer, (float) ZPItemRenderLayerMixin.deltaTime, this.getParentModel(), pLivingEntity, pItemStack, pDisplayContext, pArm, pPoseStack, pBuffer, pPackedLight);
                ci.cancel();
            }
        }
    }
}
