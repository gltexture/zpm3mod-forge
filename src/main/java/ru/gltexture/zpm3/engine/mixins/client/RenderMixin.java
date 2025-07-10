package ru.gltexture.zpm3.engine.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;

@Mixin(GameRenderer.class)
public class RenderMixin {
    @Unique
    private static double lastFrameTime = 0.0f;
    @Unique
    private static double deltaTime = -1.0f;

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderTail2(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        double currentTime = GLFW.glfwGetTime();
        RenderMixin.deltaTime = currentTime - lastFrameTime;
        RenderMixin.lastFrameTime = currentTime;

        ZPRenderHelper.INSTANCE.getRenderFunctionList().forEach((e) -> e.onRender(ZPRenderHelper.RenderStage.PRE, (float) RenderMixin.deltaTime, pNanoTime, pRenderLevel));
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderTail1(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        ZPRenderHelper.INSTANCE.getRenderFunctionList().forEach((e) -> e.onRender(ZPRenderHelper.RenderStage.POST, (float) RenderMixin.deltaTime, pNanoTime, pRenderLevel));
    }
}
