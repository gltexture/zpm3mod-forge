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

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public abstract class ZPEntityRenderMatchHelper {
    private static final Set<EntityRenderPair<? extends Entity>> entityRendererPairs = new HashSet<>();

    public static <T extends Entity> void matchEntityRendering(@NotNull Supplier<EntityType<T>> registryObject, @NotNull EntityRendererProvider<T> entityRenderer) {
        ZPEntityRenderMatchHelper.entityRendererPairs.add(new EntityRenderPair<>(registryObject, entityRenderer));
    }

    public static Set<EntityRenderPair<? extends Entity>> getEntityRendererPairs() {
        return ZPEntityRenderMatchHelper.entityRendererPairs;
    }

    public static void clear() {
        ZPEntityRenderMatchHelper.entityRendererPairs.clear();
    }

    public record EntityRenderPair<T extends Entity> (@NotNull Supplier<EntityType<T>> registryObject, @NotNull EntityRendererProvider<T> provider) {}
}
