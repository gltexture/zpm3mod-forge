package ru.gltexture.zpm3.engine.mixins.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelRendererExt;

@Mixin(MultiPlayerGameMode.class)
@OnlyIn(Dist.CLIENT)
public class ZPMultiplayerGameModeMixin {
    @Shadow private float destroyProgress;

    @Inject(method = "continueDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void continueDestroyBlock(BlockPos pPosBlock, Direction pDirectionFacing, CallbackInfoReturnable<Boolean> ci) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null && Minecraft.getInstance().player != null) {
            if (Minecraft.getInstance().levelRenderer instanceof IZPLevelRendererExt levelRendererExt) {
                int fakeIdFromServerGlobalMem = -new Vector3i(pPosBlock.getX(), pPosBlock.getY(), pPosBlock.getZ()).hashCode();
                BlockDestructionProgress progress = levelRendererExt.destroyingBlocks().get(fakeIdFromServerGlobalMem);
                float visualProgress = progress != null ? progress.getProgress() : 0f;
                if (this.destroyProgress <= 0.0f) {
                    this.destroyProgress = Math.max((visualProgress / 10.0f) - 0.1f, 0.0f);
                }
                ItemStack itemStack = Minecraft.getInstance().player.getMainHandItem();
                if (itemStack.getItem() instanceof ZPBaseGun) {
                    ci.setReturnValue(false);
                }
            }
        }
    }
}
