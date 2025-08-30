package ru.gltexture.zpm3.assets.debug.mixins.impl.client;

import net.minecraft.client.Camera;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.assets.debug.events.ZPFreeCameraEvents;

@OnlyIn(Dist.CLIENT)
@Mixin(LocalPlayer.class)
public class ZPInputMixin {
    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void move(MoverType pType, Vec3 pPos, CallbackInfo ci) {
        if (ZPFreeCameraEvents.enabled) {
            ci.cancel();
        }
    }
}
