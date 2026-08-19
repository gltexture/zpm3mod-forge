/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.engine.client.rendering.hooks;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Deprecated(forRemoval = true)
public abstract class ZPRenderHooks {
    public enum RenderStage {
        PRE,
        POST
    }

    @FunctionalInterface
    public interface ZPSceneRenderingHook {
        void onRender(@NotNull ZPRenderHooks.RenderStage renderStage, float partialTicks, float deltaTime, long pNanoTime, boolean pRenderLevel);
    }

    @FunctionalInterface
    public interface ZPItemRendering1PersonHook {
        void onRenderItem1Person(AbstractClientPlayer pPlayer, float deltaTicks, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight);
    }

    @FunctionalInterface
    public interface ZPItemRendering3PersonHook {
        void onRenderItem3Person(@NotNull ItemInHandRenderer itemInHandRenderer, float deltaTicks, @NotNull EntityModel<?> entityModel, LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight);
    }

    @FunctionalInterface
    public interface ZPItemSceneRendering1PersonHookPre {
        void onPreRender1Person(float deltaTicks, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight);
    }

    @FunctionalInterface
    public interface ZPItemSceneRendering1PersonHookPost {
        void onPostRender1Person(float deltaTicks, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight);
    }

    @FunctionalInterface
    public interface ZPItemSceneRendering3PersonHookPre {
        void onPreRender3Person(float deltaTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, LivingEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch);
    }

    @FunctionalInterface
    public interface ZPItemSceneRendering3PersonHookPost {
        void onPostRender3Person(float deltaTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, LivingEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch);
    }
    
    public interface ZPItemSceneRendering1PersonHooks extends ZPItemSceneRendering1PersonHookPre, ZPItemSceneRendering1PersonHookPost {
    }

    public interface ZPItemSceneRendering3PersonHooks extends ZPItemSceneRendering3PersonHookPre, ZPItemSceneRendering3PersonHookPost {
    }
}