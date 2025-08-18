package ru.gltexture.zpm3.engine.client.rendering.hooks;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;

public abstract class ZPRenderHooks {
    @FunctionalInterface
    public interface ZPSceneRenderingHook {
        void onRender(@NotNull ZPRenderHelper.RenderStage renderStage, float partialTicks, float deltaTime, long pNanoTime, boolean pRenderLevel);
    }

    @FunctionalInterface
    public interface ZPItemRenderingHook {
        void onRender(AbstractClientPlayer pPlayer, float deltaTicks, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight);
    }

    @FunctionalInterface
    public interface ZPItemSceneRenderingHookPre {
        void onPreRender(float deltaTicks, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight);
    }

    @FunctionalInterface
    public interface ZPItemSceneRenderingHookPost {
        void onPostRender(float deltaTicks, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight);
    }

    public interface ZPItemSceneRenderingHooks extends ZPItemSceneRenderingHookPre, ZPItemSceneRenderingHookPost {
    }
}