package ru.gltexture.zpm3.modules.player.mixins.impl.client;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.player.events.client.ZPPlayerLyingClientCheckEvent;

@Mixin(Player.class)
public class ZPCPlayerMixin {
    @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
    protected void updatePlayerPose(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (ZPPlayerLyingClientCheckEvent.isLocalPlayerInLyingAnimationNow) {
            player.setPose(Pose.SWIMMING);
            ci.cancel();
        }
    }
}
