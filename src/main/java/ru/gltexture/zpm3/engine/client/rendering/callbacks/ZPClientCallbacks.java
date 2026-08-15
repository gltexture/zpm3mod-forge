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

package ru.gltexture.zpm3.engine.client.rendering.callbacks;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.modules.guns.processing.logic.ZPDefaultGunLogicFunctions;

@OnlyIn(Dist.CLIENT)
public abstract class ZPClientCallbacks {
    @FunctionalInterface
    public interface ZPKeyboardCallback {
        void onKeyboardAction(long descriptor, int key, int scanCode, int action, int mods);
    }

    @FunctionalInterface
    public interface ZPMouseButtonCallback {
        void onMouseButtonAction(long descriptor, int button, int action, int mods);
    }

    @FunctionalInterface
    public interface ZPCharCallback {
        void onCharAction(long descriptor, int codepoint);
    }

    @FunctionalInterface
    public interface ZPWindowResizeCallback {
        void onWindowResized(long descriptor, int width, int height);
    }

    @FunctionalInterface
    public interface ZPMouseScrollCallback {
        void onMouseScrollAction(long descriptor, int x, int y);
    }

    @FunctionalInterface
    public interface ZPSetupResourcesCallback {
        void onSetupResources(@NotNull Window window);
    }

    @FunctionalInterface
    public interface ZPDestroyResourcesCallback {
        void onDestroyResources(@NotNull Window window);
    }

    @FunctionalInterface
    public interface ZPReloadResourcesCallback {
        void onReloadResources(@NotNull Window window);
    }

    //@Deprecated(forRemoval = true)
    @FunctionalInterface
    public interface ZPClientTickCallback {
        void onTick(@NotNull TickEvent.Phase phase);
    }

    //@Deprecated(forRemoval = true)
    @FunctionalInterface
    public interface ZPGunShotCallback {
        void onShot(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, @NotNull ZPDefaultGunLogicFunctions.GunFXData_Shot gunFXData);
    }

    //@Deprecated(forRemoval = true)
    @FunctionalInterface
    public interface ZPGunReloadStartCallback {
        void onReloadStart(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, @NotNull ZPDefaultGunLogicFunctions.GunFXData_Reload gunFXData);
    }
}