package ru.gltexture.zpm3.assets.guns.rendering.fx;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public abstract class ZPGunFXGlobalData {
    public static @NotNull ClientGunRenderGlobalData leftGunData = new ClientGunRenderGlobalData();
    public static @NotNull ClientGunRenderGlobalData rightGunData = new ClientGunRenderGlobalData();

    private ZPGunFXGlobalData() {
    }

    public static @NotNull ClientGunRenderGlobalData getGunData(boolean right) {
        return right ? ZPGunFXGlobalData.getRightGunData() : ZPGunFXGlobalData.getLeftGunData();
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
        private @NotNull Matrix4f mflashTransformationTarget;

        public ClientGunRenderGlobalData() {
            this.currentGunItemMatrix = new Matrix4f().identity();
            this.currentArmMatrix = new Matrix4f().identity();
            this.gunReloadingTransformationTarget = new Matrix4f().identity();
            this.armReloadingTransformationTarget = new Matrix4f().identity();
            this.mflashTransformationTarget = new Matrix4f().identity();
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

        public @NotNull Matrix4f getMflashTransformationTarget() {
            return new Matrix4f(this.mflashTransformationTarget);
        }

        public ClientGunRenderGlobalData setMflashTransformationTarget(@NotNull Matrix4f mflashTransformationTarget) {
            this.mflashTransformationTarget = mflashTransformationTarget;
            return this;
        }
    }
}