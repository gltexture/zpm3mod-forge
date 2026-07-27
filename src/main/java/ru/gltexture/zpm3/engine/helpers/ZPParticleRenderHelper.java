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

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public abstract class ZPParticleRenderHelper {
    private static final Set<ParticleRenderPairSet<? extends ParticleOptions>> particleRendererPairSets = new HashSet<>();

    public static <T extends ParticleOptions> void matchParticleRenderingSet(@NotNull RegistryObject<? extends ParticleType<T>> type, @NotNull Function<SpriteSet, @NotNull ParticleProvider<T>> particleProvider) {
        ZPParticleRenderHelper.particleRendererPairSets.add(new ParticleRenderPairSet<>(type, particleProvider));
    }

    public static Set<ParticleRenderPairSet<? extends ParticleOptions>> getParticleRendererPairSets() {
        return ZPParticleRenderHelper.particleRendererPairSets;
    }

    public static void clear() {
        ZPParticleRenderHelper.particleRendererPairSets.clear();
    }

    public record ParticleRenderPairSet<T extends ParticleOptions> (@NotNull RegistryObject<? extends ParticleType<T>> type, @NotNull Function<SpriteSet, @NotNull ParticleProvider<T>> particleProvider) {}
}
