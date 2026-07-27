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

package ru.gltexture.zpm3.engine.client.rendering.shaders;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.HashSet;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public abstract class ZPShaderLoader {
    static final Set<Pair<ZPShaderInstance, ShaderData>> shaderDataSet = new HashSet<>();

    public static void createNewShader(@NotNull ShaderData shaderData, @NotNull ZPShaderInstance shaderInstance) {
        ZPShaderLoader.shaderDataSet.add(new Pair<>(shaderInstance, shaderData));
    }

    static void setShaderInstance(@NotNull ZPShaderInstance zpShaderInstance, @NotNull ShaderInstance shaderInstance) {
        zpShaderInstance.setShaderInstance(shaderInstance);
    }

    public record ShaderData(@NotNull ResourceLocation resourceLocation, @NotNull VertexFormat vertexFormat) { ; }

    public static class ZPShaderInstance {
        private ShaderInstance shaderInstance;

        public ZPShaderInstance() {
            this.shaderInstance = null;
        }

        public @Nullable ShaderInstance getShaderInstance() {
            return this.shaderInstance;
        }

        private void setShaderInstance(ShaderInstance shaderInstance) {
            this.shaderInstance = shaderInstance;
        }
    }
}
