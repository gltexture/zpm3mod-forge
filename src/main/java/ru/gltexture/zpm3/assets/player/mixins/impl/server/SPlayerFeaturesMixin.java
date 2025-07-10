package ru.gltexture.zpm3.assets.player.mixins.impl.server;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class SPlayerFeaturesMixin {
    @Inject(method = "canEat", at = @At("HEAD"), cancellable = true)
    private void canEat(boolean pCanAlwaysEat, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}