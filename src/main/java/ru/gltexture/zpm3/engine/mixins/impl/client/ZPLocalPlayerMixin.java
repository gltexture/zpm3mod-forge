package ru.gltexture.zpm3.engine.mixins.impl.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LocalPlayer.class)
@OnlyIn(Dist.CLIENT)
public class ZPLocalPlayerMixin {
}
