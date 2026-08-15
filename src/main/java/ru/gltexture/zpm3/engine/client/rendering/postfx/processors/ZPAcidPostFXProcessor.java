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

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.rendering.postfx.ZPPostFXChain;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPShaderLoader;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.modules.debug.imgui.ZPImGuiDebugInterface;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityStat;

import java.util.Objects;

public class ZPAcidPostFXProcessor extends ZPPostFXProcessor {
    private float value;

    public ZPAcidPostFXProcessor(int chainOrder) {
        super(chainOrder);
    }

    @Override
    public void renderTextureInFBO(float deltaTime, float partialTicks, int screenTexture_GL_ID) {
        ShaderInstance shader = this.getPostFXShader().getShaderInstance();
        Objects.requireNonNull(shader).apply();
        Window window = Minecraft.getInstance().getWindow();
        float acidConst = Minecraft.getInstance().player == null ? 20.0f : ZPEntityStat.ACID.get(Objects.requireNonNull(Minecraft.getInstance().player));
        acidConst = Math.min(acidConst, 20.0f);
        final float a = 8.0f;
        if (this.value < acidConst) {
            this.value = Math.min(this.value + (a * deltaTime), 20.0f);
        } else if (this.value > acidConst) {
            this.value = Math.max(this.value - (a * deltaTime), 0.0f);
        }
        GL46.glViewport(0, 0, window.getWidth(), window.getHeight());
        {
            GL46.glActiveTexture(GL46.GL_TEXTURE0);
            GL46.glBindTexture(GL46.GL_TEXTURE_2D, screenTexture_GL_ID);
            shader.safeGetUniform("texture_map").set(0);
            shader.safeGetUniform("timer").set(ZPPostFXChain.TIMER);
            shader.safeGetUniform("value").set((ZPImGuiDebugInterface.FORCE_ENABLE_ACID_POST_FX_SHADER ? 20.0f : this.value) / 20.0f);
            ZombiePlague3.getClientManager().renderScreenMesh();
        }
        Objects.requireNonNull(shader).clear();
    }

    @Override
    public void clientPreTick() {
        if (this.bypass()) {
            this.value = 0;
        }
    }

    @Override
    public void clientPostTick() {
    }

    @Override
    protected ZPShaderLoader.ZPShaderInstance getPostFXShader() {
        return ZPDefaultShaders.post_fx_acid;
    }

    @Override
    public boolean bypass() {
        if (Minecraft.getInstance().player != null) {
            if (this.value > 0 || ZPEntityStat.ACID.get(Objects.requireNonNull(Minecraft.getInstance().player)) > 0) {
                return false;
            }
        }
        return !ZPImGuiDebugInterface.FORCE_ENABLE_ACID_POST_FX_SHADER;
    }
}
