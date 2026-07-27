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

package ru.gltexture.zpm3.engine.client.rendering.lightmap;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayDeque;
import java.util.Deque;

@OnlyIn(Dist.CLIENT)
public class ZPLightMapModifier {
    public static ZPLightMapModifier INSTANCE = new ZPLightMapModifier();
    private final Deque<LightMapModRequest> lightMapModRequests;

    private ZPLightMapModifier() {
        this.lightMapModRequests = new ArrayDeque<>();
    }

    public void add(LightMapModRequest lightMapModRequest) {
        this.lightMapModRequests.add(lightMapModRequest);
    }

    public LightMapModRequest pop() {
        return this.lightMapModRequests.pollLast();
    }

    public Deque<LightMapModRequest> getLightMapModRequests() {
        return this.lightMapModRequests;
    }

    public record LightMapModRequest(@Nullable Vector3f rgb_ADD, @Nullable Vector3f rgb_MUL, float gamma_MUL, float gamme_ADD) {
        public LightMapModRequest(float gamma_MUL, float gamme_ADD) {
            this(null, null, gamma_MUL, gamme_ADD);
        }

        public LightMapModRequest(@Nullable Vector3f rgb_ADD, @Nullable Vector3f rgb_MUL) {
            this(rgb_ADD, rgb_MUL, 1.0f, 0.0f);
        }
    }
}
