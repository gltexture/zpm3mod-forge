package ru.gltexture.zpm3.modules.player.mixins.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.guns.mixins.client.ZPHumanoidArmTransformations;

@OnlyIn(Dist.CLIENT)
@Mixin(MouseHandler.class)
public class ZPMouseHandlerTurnMixin {
    //TODO REMAKE
    @Inject(method = "turnPlayer", at = @At("TAIL"))
    @SuppressWarnings("removal")
    public void turn(CallbackInfo ci) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null && localPlayer.getPose() == Pose.SWIMMING) {
            if (!localPlayer.isInWater()) {
                if (Minecraft.getInstance().getCameraEntity() != null) {
                    Minecraft.getInstance().getCameraEntity().setXRot(Math.max(Minecraft.getInstance().getCameraEntity().getXRot(), ZPHumanoidArmTransformations.X_CONSTR_DEG_P));
                    if (!ZPHumanoidArmTransformations.canEntityInSwimPosLookDown(localPlayer)) {
                        Minecraft.getInstance().getCameraEntity().setXRot((float) Math.min(Minecraft.getInstance().getCameraEntity().getXRot(), Math.toDegrees(ZPHumanoidArmTransformations.X_CONSTR_RAD_M)));
                    }
                }
            }
        }
    }
}
