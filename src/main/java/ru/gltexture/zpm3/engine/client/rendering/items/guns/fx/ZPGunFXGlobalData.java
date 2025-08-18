package ru.gltexture.zpm3.engine.client.rendering.items.guns.fx;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public abstract class ZPGunFXGlobalData {
    public static @NotNull ClientGunRenderGlobalData left = new ClientGunRenderGlobalData(new Matrix4f().identity(), new Matrix4f().identity(), new Matrix4f().identity());
    public static @NotNull ClientGunRenderGlobalData right = new ClientGunRenderGlobalData(new Matrix4f().identity(), new Matrix4f().identity(), new Matrix4f().identity());

    private ZPGunFXGlobalData() {
    }

    public static @NotNull ClientGunRenderGlobalData getLeft() {
        return ZPGunFXGlobalData.left;
    }

    public static void setLeft(@NotNull ClientGunRenderGlobalData left) {
        ZPGunFXGlobalData.left = left;
    }

    public static @NotNull ClientGunRenderGlobalData getRight() {
        return ZPGunFXGlobalData.right;
    }

    public static void setRight(@NotNull ClientGunRenderGlobalData right) {
        ZPGunFXGlobalData.right = right;
    }

    public record ClientGunRenderGlobalData(@NotNull Matrix4f getCurrentMuzzleflashOffset, @NotNull Matrix4f getCurrentGunItemMatrix, @NotNull Matrix4f getCurrentArmMatrix) {
    }
}