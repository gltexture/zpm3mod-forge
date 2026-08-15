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

package ru.gltexture.zpm3.modules.guns.rendering.fx;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.rendering.IZPClientManager;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.ZPClientCallbacks;

//@Deprecated(forRemoval = true)
public interface IZPGunMuzzleflashFX extends IZPGunFX, ZPClientCallbacks.ZPGunShotCallback, IZPClientManager.ResourceLifecycleListener {
    void render1Person(@NotNull MultiBufferSource buffer, float partialTicks, float deltaTicks);
    void render3Person(@NotNull LivingEntity livingEntity, @NotNull MultiBufferSource buffer, float deltaTicks, boolean isRightHanded);
}