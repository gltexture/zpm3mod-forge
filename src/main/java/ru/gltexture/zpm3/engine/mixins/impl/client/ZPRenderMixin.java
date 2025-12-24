package ru.gltexture.zpm3.engine.mixins.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;

@Mixin(GameRenderer.class)
@OnlyIn(Dist.CLIENT)
public class ZPRenderMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderTail2(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        ZPRenderHooksManager.INSTANCE.getSceneRenderingHooks().forEach((e) -> e.onRender(ZPRenderHelper.RenderStage.PRE, pPartialTicks, ZPRenderHelper.DELTA_TIME(), pNanoTime, pRenderLevel));
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderTail1(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        ZPRenderHooksManager.INSTANCE.getSceneRenderingHooks().forEach((e) -> e.onRender(ZPRenderHelper.RenderStage.POST, pPartialTicks, ZPRenderHelper.DELTA_TIME(), pNanoTime, pRenderLevel));
    }
}
