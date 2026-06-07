package ru.gltexture.zpm3.engine.mixins.impl.client.render;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface ZPGameRendererFovAccessor {
    @Invoker("getFov")
    double invokeGetFov(Camera camera, float partialTicks, boolean useFovSetting);
}