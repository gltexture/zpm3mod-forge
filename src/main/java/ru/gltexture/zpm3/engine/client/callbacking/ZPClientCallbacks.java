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

package ru.gltexture.zpm3.engine.client.callbacking;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;

@OnlyIn(Dist.CLIENT)
public abstract class ZPClientCallbacks {
    @FunctionalInterface
    public interface ZPCharCallback {
        void onCharAction(long descriptor, int c);
    }

    @FunctionalInterface
    public interface ZPReloadGameResourcesCallback {
        void onReloadResources(@NotNull Window window);
    }

    @FunctionalInterface
    public interface ZPWindowResizeCallback {
        void onWindowResizeAction(long descriptor, int width, int height);
    }

    @FunctionalInterface
    public interface ZPMouseClickCallback {
        void onMouseClickAction(long descriptor, int key);
    }

    @FunctionalInterface
    public interface ZPMouseHoldCallback {
        void onMouseHoldAction(long descriptor, int key);
    }

    @FunctionalInterface
    public interface ZPMouseReleaseCallback {
        void onMouseReleaseAction(long descriptor, int key);
    }

    @FunctionalInterface
    public interface ZPMouseScrollCallback {
        void onMouseScrollAction(long descriptor, int x, int y);
    }

    @FunctionalInterface
    public interface ZPKeyboardClickCallback {
        void onKeyboardClickAction(long descriptor, int key, int scanCode, int mods);
    }

    @FunctionalInterface
    public interface ZPKeyboardHoldCallback {
        void onKeyBoardHoldAction(long descriptor, int key, int scanCode, int mods);
    }

    @FunctionalInterface
    public interface ZPKeyboardReleaseCallback {
        void onKeyboardReleaseAction(long descriptor, int key, int scanCode, int mods);
    }

    @FunctionalInterface
    public interface ZPSetupResourcesCallback {
        void setupResources(@NotNull Window window);
    }

    @FunctionalInterface
    public interface ZPDestroyResourcesCallback {
        void destroyResources(@NotNull Window window);
    }

    @FunctionalInterface
    public interface ZPClientTickCallback {
        void onTick(@NotNull TickEvent.Phase phase);
    }

    @FunctionalInterface
    public interface ZPGunShotCallback {
        void onShot(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, @NotNull GunFXData gunFXData);
        record GunFXData(boolean isRightHand, float recoilStrength, float muzzleflashTime) {}
    }

    @FunctionalInterface
    public interface ZPGunReloadStartCallback {
        void onReloadStart(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, @NotNull GunFXData gunFXData);
        record GunFXData(boolean isRightHand) {}
    }

    //@FunctionalInterface
    //public interface ZPGunReloadEndCallback {
    //    void onReloadEnd(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, @NotNull GunFXData gunFXData);
    //    record GunFXData(boolean isRightHand) {}
    //}

    public interface ZPClientResourceDependentObject extends ZPSetupResourcesCallback, ZPDestroyResourcesCallback, ZPWindowResizeCallback {
    }
}
