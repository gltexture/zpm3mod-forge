package ru.gltexture.zpm3.engine.mixins.impl.client.render;

import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;

@OnlyIn(Dist.CLIENT)
@Mixin(ScreenEffectRenderer.class)
public class ZPScreenEffectMixin {
}
