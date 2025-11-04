package ru.gltexture.zpm3.assets.guns.rendering.fx;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public abstract class ZPGunFXGlobalData {
    public static @NotNull ClientGunRenderGlobalData leftGunData = new ClientGunRenderGlobalData();
    public static @NotNull ClientGunRenderGlobalData rightGunData = new ClientGunRenderGlobalData();

    private ZPGunFXGlobalData() {
    }

    public static @NotNull ClientGunRenderGlobalData getGunData(boolean isRightHanded) {
        return isRightHanded ? ZPGunFXGlobalData.getRightGunData() : ZPGunFXGlobalData.getLeftGunData();
    }

    public static @NotNull ClientGunRenderGlobalData getLeftGunData() {
        return ZPGunFXGlobalData.leftGunData;
    }

    public static @NotNull ClientGunRenderGlobalData getRightGunData() {
        return ZPGunFXGlobalData.rightGunData;
    }

    public static class ClientGunRenderGlobalData {
        private @NotNull Matrix4f currentGunItemMatrix;
        private @NotNull Matrix4f currentArmMatrix;
        private @NotNull Matrix4f gunReloadingTransformationTarget;
        private @NotNull Matrix4f armReloadingTransformationTarget;
        private @Nullable Matrix4f mflash1spTransformationTarget;
        private @NotNull Matrix4f mflash3dpTransformationTarget;

        public ClientGunRenderGlobalData() {
            this.currentGunItemMatrix = new Matrix4f().identity();
            this.currentArmMatrix = new Matrix4f().identity();
            this.gunReloadingTransformationTarget = new Matrix4f().identity();
            this.armReloadingTransformationTarget = new Matrix4f().identity();
            this.mflash1spTransformationTarget = new Matrix4f().identity();
            this.mflash3dpTransformationTarget = new Matrix4f().identity();
        }

        public @NotNull Matrix4f getCurrentGunItemMatrix() {
            return new Matrix4f(this.currentGunItemMatrix);
        }

        public ClientGunRenderGlobalData setCurrentGunItemMatrix(@NotNull Matrix4f currentGunItemMatrix) {
            this.currentGunItemMatrix = currentGunItemMatrix;
            return this;
        }

        public @NotNull Matrix4f getCurrentArmMatrix() {
            return new Matrix4f(this.currentArmMatrix);
        }

        public ClientGunRenderGlobalData setCurrentArmMatrix(@NotNull Matrix4f currentArmMatrix) {
            this.currentArmMatrix = currentArmMatrix;
            return this;
        }

        public @NotNull Matrix4f getGunReloadingTransformationTarget() {
            return new Matrix4f(this.gunReloadingTransformationTarget);
        }

        public ClientGunRenderGlobalData setGunReloadingTransformationTarget(@NotNull Matrix4f gunReloadingTransformationTarget) {
            this.gunReloadingTransformationTarget = gunReloadingTransformationTarget;
            return this;
        }

        public @NotNull Matrix4f getArmReloadingTransformationTarget() {
            return new Matrix4f(this.armReloadingTransformationTarget);
        }

        public ClientGunRenderGlobalData setArmReloadingTransformationTarget(@NotNull Matrix4f armReloadingTransformationTarget) {
            this.armReloadingTransformationTarget = armReloadingTransformationTarget;
            return this;
        }

        public @Nullable Matrix4f getMflash1spTransformationTarget() {
            return new Matrix4f(this.mflash1spTransformationTarget);
        }

        public void setMflash1spTransformationTarget(@Nullable Matrix4f mflash1spTransformationTarget) {
            this.mflash1spTransformationTarget = mflash1spTransformationTarget;
        }

        public @NotNull Matrix4f getMflash3dpTransformationTarget() {
            return this.mflash3dpTransformationTarget;
        }

        public void setMflash3dpTransformationTarget(@NotNull Matrix4f mflash3dpTransformationTarget) {
            this.mflash3dpTransformationTarget = mflash3dpTransformationTarget;
        }
    }
}