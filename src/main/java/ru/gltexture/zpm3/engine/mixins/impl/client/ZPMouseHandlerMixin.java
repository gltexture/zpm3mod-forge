package ru.gltexture.zpm3.engine.mixins.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class ZPMouseHandlerMixin {
    @Shadow
    private boolean isMiddlePressed;

    @Shadow
    private boolean isRightPressed;

    @Shadow
    private boolean isLeftPressed;

    @Inject(method = "onPress", at = @At("HEAD"))
    private void onPress(long pWindowPointer, int pButton, int pAction, int pModifiers, CallbackInfo ci) {
        if (!(Minecraft.getInstance().screen == null && Minecraft.getInstance().getOverlay() == null)) {
            this.isMiddlePressed = false;
            this.isRightPressed = false;
            this.isLeftPressed = false;
        }
    }
}
