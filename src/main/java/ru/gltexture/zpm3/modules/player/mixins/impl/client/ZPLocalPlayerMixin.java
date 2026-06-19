package ru.gltexture.zpm3.modules.player.mixins.impl.client;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.modules.player.events.client.ZPPlayerLyingClientCheckEvent;

@Mixin(LocalPlayer.class)
public class ZPLocalPlayerMixin {
    @Inject(method = "isCrouching", at = @At("HEAD"), cancellable = true)
    public void isCrouching(CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer localPlayer = (LocalPlayer)(Object)this;
        if (ZPPlayerLyingClientCheckEvent.isLocalPlayerInLyingAnimationNow) {
            cir.setReturnValue(false);
        }
    }
}
