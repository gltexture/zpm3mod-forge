package ru.gltexture.zpm3.engine.mixins.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.engine.instances.guns.ZPBaseGun;

@Mixin(MultiPlayerGameMode.class)
@OnlyIn(Dist.CLIENT)
public class ZPMultiplayerGameModeMixin {
    @Inject(method = "continueDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void continueDestroyBlock(BlockPos pPosBlock, Direction pDirectionFacing, CallbackInfoReturnable<Boolean> ci) {
        if (Minecraft.getInstance().player != null) {
            ItemStack itemStack = Minecraft.getInstance().player.getMainHandItem();
            if (itemStack.getItem() instanceof ZPBaseGun) {
                ci.setReturnValue(false);
            }
        }
    }
}
