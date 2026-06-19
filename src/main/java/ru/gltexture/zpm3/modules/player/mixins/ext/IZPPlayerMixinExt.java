package ru.gltexture.zpm3.modules.player.mixins.ext;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.net_pack.data.ZPNetSyncDataPack;

public interface IZPPlayerMixinExt {
    ZPNetSyncDataPack zpm3forge$zpNetDataPack_fromClient();

    void zpm3forge$getResponseNetCheckFromServer();
    void zpm3forge$getResponseNetCheckFromClient();
    int zpm3forge$getPing();

    boolean zpm3forge$isLying();
    void zpm3forge$setLying(boolean value);

    static boolean checkIfPlayerCanLieOnGround(@NotNull Player player) {
       // if (!player.onGround()) {
       //     return false;
       // }
        if (player.getDeltaMovement().y > 0.01f) {
            return false;
        }
        if (player.isSwimming()) {
            return false;
        }
        if (player.isSleeping()) {
            return false;
        }
        if (player.isFallFlying()) {
            return false;
        }
        if (player.isPassenger()) {
            return false;
        }
        return player.getPose() == Pose.STANDING || player.getPose() == Pose.CROUCHING || player.getPose() == Pose.SWIMMING;
    }
}
