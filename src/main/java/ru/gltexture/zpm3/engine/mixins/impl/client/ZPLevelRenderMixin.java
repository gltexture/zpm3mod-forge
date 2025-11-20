package ru.gltexture.zpm3.engine.mixins.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelRendererExt;

import java.util.SortedSet;

@Mixin(LevelRenderer.class)
@OnlyIn(Dist.CLIENT)
public class ZPLevelRenderMixin implements IZPLevelRendererExt {
    @Shadow @Final private Int2ObjectMap<BlockDestructionProgress> destroyingBlocks = new Int2ObjectOpenHashMap<>();
    @Shadow @Final private Long2ObjectMap<SortedSet<BlockDestructionProgress>> destructionProgress = new Long2ObjectOpenHashMap<>();

    @Inject(method = "renderHitOutline", at = @At("HEAD"), cancellable = true)
    private void renderHitOutline(PoseStack pPoseStack, VertexConsumer pConsumer, Entity pEntity, double pCamX, double pCamY, double pCamZ, BlockPos pPos, BlockState pState, CallbackInfo ci) {
        if (Minecraft.getInstance().player != null) {
            ItemStack itemStack = Minecraft.getInstance().player.getMainHandItem();
            if (itemStack.getItem() instanceof ZPBaseGun) {
                ci.cancel();
            }
        }
    }

    @Override
    public Int2ObjectMap<BlockDestructionProgress> destroyingBlocks() {
        return this.destroyingBlocks;
    }

    @Override
    public Long2ObjectMap<SortedSet<BlockDestructionProgress>> destructionProgress() {
        return this.destructionProgress;
    }
}
