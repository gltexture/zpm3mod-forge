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

package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.core.Position;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class ZPDispenseProjectileHelper {
    private static final Map<RegistryObject<? extends Item>, ProjectileData> dispenserMap = new HashMap<>();

    public static void addDispenserData(@NotNull RegistryObject<? extends Item> registryObject, @NotNull ProjectileData projectileData) {
        ZPDispenseProjectileHelper.dispenserMap.put(registryObject, projectileData);
    }

    public static Map<RegistryObject<? extends Item>, ProjectileData> getDispenserMap() {
        return ZPDispenseProjectileHelper.dispenserMap;
    }

    public static void clear() {
        ZPDispenseProjectileHelper.dispenserMap.clear();
    }

    public record ProjectileData(@NotNull DispenserProjectileFactory projectileFactory, float inaccuracy, float power) {}

    @FunctionalInterface
    public interface DispenserProjectileFactory {
        @NotNull Projectile getProjectile(@NotNull Level pLevel, @NotNull Position pPosition, @NotNull ItemStack pStack);
    }
}