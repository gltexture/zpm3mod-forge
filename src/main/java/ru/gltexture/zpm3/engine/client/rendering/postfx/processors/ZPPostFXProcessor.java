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

package ru.gltexture.zpm3.engine.client.rendering.postfx.processors;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPShaderLoader;

@OnlyIn(Dist.CLIENT)
public abstract class ZPPostFXProcessor {
    private final int chainOrder;

    public ZPPostFXProcessor(int chainOrder) {
        this.chainOrder = chainOrder;
    }

    public abstract void renderTextureInFBO(int screenTexture_GL_ID);

    protected abstract ZPShaderLoader.ZPShaderInstance getPostFXShader();

    public abstract boolean bypass();

    public int getChainOrder() {
        return this.chainOrder;
    }
}
