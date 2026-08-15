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

package ru.gltexture.zpm3.engine.core.api.events.client;

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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.client.rendering.lightmap.ZPLightMapModifier;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventDef;

@OnlyIn(Dist.CLIENT)
public abstract class ZPEventBus_ClientRendering {
    public static final class PreCalcMinecraftLightMapEvent implements ZPEventDef.IEvent {
        private final ZPLightMapModifier zpLightMapModifier;

        public PreCalcMinecraftLightMapEvent(@NotNull ZPLightMapModifier zpLightMapModifier) {
            this.zpLightMapModifier = zpLightMapModifier;
        }

        public ZPLightMapModifier getZpLightMapModifier() {
            return this.zpLightMapModifier;
        }
    }

    public static final class PostCalcMinecraftLightMapEvent implements ZPEventDef.IEvent {
        private final ZPLightMapModifier zpLightMapModifier;
        private final Vector3f currentRGB;
        private final float currentGAMMA;

        public PostCalcMinecraftLightMapEvent(@NotNull ZPLightMapModifier zpLightMapModifier, @NotNull Vector3f currentRGB, float currentGAMMA) {
            this.zpLightMapModifier = zpLightMapModifier;
            this.currentRGB = currentRGB;
            this.currentGAMMA = currentGAMMA;
        }

        public Vector3f getCurrentRGB() {
            return this.currentRGB;
        }

        public float getCurrentGAMMA() {
            return this.currentGAMMA;
        }

        public ZPLightMapModifier getLightMapModifier() {
            return this.zpLightMapModifier;
        }
    }

    public static final class SceneRenderEvent implements ZPEventDef.IEvent {
        private final ZPEventDef.Run renderStage;
        private final float partialTicks;
        private final float deltaTime;
        private final long nanoTime;
        private final boolean renderLevel;

        public SceneRenderEvent(@NotNull ZPEventDef.Run renderStage, float partialTicks, float deltaTime, long nanoTime, boolean renderLevel) {
            this.renderStage = renderStage;
            this.partialTicks = partialTicks;
            this.deltaTime = deltaTime;
            this.nanoTime = nanoTime;
            this.renderLevel = renderLevel;
        }

        public @NotNull ZPEventDef.Run getRenderStage() {
            return this.renderStage;
        }

        public float getPartialTicks() {
            return this.partialTicks;
        }

        public float getDeltaTime() {
            return this.deltaTime;
        }

        public long getNanoTime() {
            return this.nanoTime;
        }

        public boolean isRenderLevel() {
            return this.renderLevel;
        }
    }

    public static final class ItemRenderFirstPersonEvent extends ZPEventDef.Cancellable implements ZPEventDef.IEvent {
        private final AbstractClientPlayer player;
        private final float deltaTicks;
        private final float partialTicks;
        private final float pitch;
        private final InteractionHand hand;
        private final float swingProgress;
        private final ItemStack stack;
        private final float equippedProgress;
        private final PoseStack poseStack;
        private final MultiBufferSource buffer;
        private final int combinedLight;

        public ItemRenderFirstPersonEvent(@NotNull AbstractClientPlayer player, float deltaTicks, float partialTicks, float pitch, @NotNull InteractionHand hand, float swingProgress, @NotNull ItemStack stack, float equippedProgress, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int combinedLight) {
            this.player = player;
            this.deltaTicks = deltaTicks;
            this.partialTicks = partialTicks;
            this.pitch = pitch;
            this.hand = hand;
            this.swingProgress = swingProgress;
            this.stack = stack;
            this.equippedProgress = equippedProgress;
            this.poseStack = poseStack;
            this.buffer = buffer;
            this.combinedLight = combinedLight;
        }

        public @NotNull AbstractClientPlayer getPlayer() {
            return this.player;
        }

        public float getDeltaTicks() {
            return this.deltaTicks;
        }

        public float getPartialTicks() {
            return this.partialTicks;
        }

        public float getPitch() {
            return this.pitch;
        }

        public @NotNull InteractionHand getHand() {
            return this.hand;
        }

        public float getSwingProgress() {
            return this.swingProgress;
        }

        public @NotNull ItemStack getStack() {
            return this.stack;
        }

        public float getEquippedProgress() {
            return this.equippedProgress;
        }

        public @NotNull PoseStack getPoseStack() {
            return this.poseStack;
        }

        public @NotNull MultiBufferSource getBuffer() {
            return this.buffer;
        }

        public int getCombinedLight() {
            return this.combinedLight;
        }
    }

    public static final class ItemRenderThirdPersonEvent extends ZPEventDef.Cancellable implements ZPEventDef.IEvent {
        private final ItemInHandRenderer itemInHandRenderer;
        private final float deltaTicks;
        private final EntityModel<?> entityModel;
        private final LivingEntity livingEntity;
        private final ItemStack itemStack;
        private final ItemDisplayContext displayContext;
        private final HumanoidArm arm;
        private final PoseStack poseStack;
        private final MultiBufferSource buffer;
        private final int packedLight;

        public ItemRenderThirdPersonEvent(@NotNull ItemInHandRenderer itemInHandRenderer, float deltaTicks, @NotNull EntityModel<?> entityModel, @NotNull LivingEntity livingEntity, @NotNull ItemStack itemStack, @NotNull ItemDisplayContext displayContext, @NotNull HumanoidArm arm, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
            this.itemInHandRenderer = itemInHandRenderer;
            this.deltaTicks = deltaTicks;
            this.entityModel = entityModel;
            this.livingEntity = livingEntity;
            this.itemStack = itemStack;
            this.displayContext = displayContext;
            this.arm = arm;
            this.poseStack = poseStack;
            this.buffer = buffer;
            this.packedLight = packedLight;
        }

        public @NotNull ItemInHandRenderer getItemInHandRenderer() {
            return this.itemInHandRenderer;
        }

        public float getDeltaTicks() {
            return this.deltaTicks;
        }

        public @NotNull EntityModel<?> getEntityModel() {
            return this.entityModel;
        }

        public @NotNull LivingEntity getLivingEntity() {
            return this.livingEntity;
        }

        public @NotNull ItemStack getItemStack() {
            return this.itemStack;
        }

        public @NotNull ItemDisplayContext getDisplayContext() {
            return this.displayContext;
        }

        public @NotNull HumanoidArm getArm() {
            return this.arm;
        }

        public @NotNull PoseStack getPoseStack() {
            return this.poseStack;
        }

        public @NotNull MultiBufferSource getBuffer() {
            return this.buffer;
        }

        public int getPackedLight() {
            return this.packedLight;
        }
    }

    public static final class ItemSceneRenderFirstPersonEvent extends ZPEventDef.Cancellable implements ZPEventDef.IEvent {
        private final ZPEventDef.Run run;
        private final float deltaTicks;
        private final float partialTicks;
        private final PoseStack poseStack;
        private final MultiBufferSource.BufferSource buffer;
        private final LocalPlayer player;
        private final int combinedLight;

        public ItemSceneRenderFirstPersonEvent(@NotNull ZPEventDef.Run run, float deltaTicks, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource.BufferSource buffer, @NotNull LocalPlayer player, int combinedLight) {
            this.run = run;
            this.deltaTicks = deltaTicks;
            this.partialTicks = partialTicks;
            this.poseStack = poseStack;
            this.buffer = buffer;
            this.player = player;
            this.combinedLight = combinedLight;
        }

        public @NotNull ZPEventDef.Run getRun() {
            return this.run;
        }

        public boolean isPre() {
            return this.run == ZPEventDef.Run.PRE;
        }

        public boolean isPost() {
            return this.run == ZPEventDef.Run.POST;
        }

        public float getDeltaTicks() {
            return this.deltaTicks;
        }

        public float getPartialTicks() {
            return this.partialTicks;
        }

        public @NotNull PoseStack getPoseStack() {
            return this.poseStack;
        }

        public @NotNull MultiBufferSource.BufferSource getBuffer() {
            return this.buffer;
        }

        public @NotNull LocalPlayer getPlayer() {
            return this.player;
        }

        public int getCombinedLight() {
            return this.combinedLight;
        }
    }

    public static final class ItemSceneRenderThirdPersonEvent extends ZPEventDef.Cancellable implements ZPEventDef.IEvent {
        private final ZPEventDef.Run run;
        private final float deltaTicks;
        private final PoseStack poseStack;
        private final MultiBufferSource buffer;
        private final int packedLight;
        private final LivingEntity livingEntity;
        private final float limbSwing;
        private final float limbSwingAmount;
        private final float partialTicks;
        private final float ageInTicks;
        private final float netHeadYaw;
        private final float headPitch;

        public ItemSceneRenderThirdPersonEvent(@NotNull ZPEventDef.Run run, float deltaTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, @NotNull LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            this.run = run;
            this.deltaTicks = deltaTicks;
            this.poseStack = poseStack;
            this.buffer = buffer;
            this.packedLight = packedLight;
            this.livingEntity = livingEntity;
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.partialTicks = partialTicks;
            this.ageInTicks = ageInTicks;
            this.netHeadYaw = netHeadYaw;
            this.headPitch = headPitch;
        }

        public @NotNull ZPEventDef.Run getRun() {
            return this.run;
        }

        public boolean isPre() {
            return this.run == ZPEventDef.Run.PRE;
        }

        public boolean isPost() {
            return this.run == ZPEventDef.Run.POST;
        }

        public float getDeltaTicks() {
            return this.deltaTicks;
        }

        public @NotNull PoseStack getPoseStack() {
            return this.poseStack;
        }

        public @NotNull MultiBufferSource getBuffer() {
            return this.buffer;
        }

        public int getPackedLight() {
            return this.packedLight;
        }

        public @NotNull LivingEntity getLivingEntity() {
            return this.livingEntity;
        }

        public float getLimbSwing() {
            return this.limbSwing;
        }

        public float getLimbSwingAmount() {
            return this.limbSwingAmount;
        }

        public float getPartialTicks() {
            return this.partialTicks;
        }

        public float getAgeInTicks() {
            return this.ageInTicks;
        }

        public float getNetHeadYaw() {
            return this.netHeadYaw;
        }

        public float getHeadPitch() {
            return this.headPitch;
        }
    }
}
/*
EventLauncher.pushEvent(new ZPEventBus_ClientRendering.RenderOGLSceneEvent(this, frameTicking, ZPEventBus_ClientRendering.Run.POST, toRenderObjects, toRenderLiquids), TODO);

    public static final class Class123 implements IEvent {

    }
 */