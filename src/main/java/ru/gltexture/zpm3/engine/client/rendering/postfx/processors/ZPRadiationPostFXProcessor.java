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
import ru.gltexture.zpm3.modules.entity.util.ZPLivingStat;

import java.util.Objects;

public class ZPRadiationPostFXProcessor extends ZPPostFXProcessor {
    private float value;

    public ZPRadiationPostFXProcessor(int chainOrder) {
        super(chainOrder);
    }

    @Override
    public void renderTextureInFBO(float deltaTime, float partialTicks, int screenTexture_GL_ID) {
        ShaderInstance shader = this.getPostFXShader().getShaderInstance();
        Objects.requireNonNull(shader).apply();
        Window window = Minecraft.getInstance().getWindow();
        float radConst = Minecraft.getInstance().player == null ? 100.0f : ZPLivingStat.RADIATION.get(Objects.requireNonNull(Minecraft.getInstance().player));
        radConst = Math.min(radConst, 100.0f);
        final float a = 2.5f;
        if (this.value < radConst) {
            this.value = Math.min(this.value + (a * deltaTime), 100.0f);
        } else if (this.value > radConst) {
            this.value = Math.max(this.value - (a * deltaTime), 0.0f);
        }
        GL46.glViewport(0, 0, window.getWidth(), window.getHeight());
        {
            GL46.glActiveTexture(GL46.GL_TEXTURE0);
            GL46.glBindTexture(GL46.GL_TEXTURE_2D, screenTexture_GL_ID);
            shader.safeGetUniform("texture_map").set(0);
            shader.safeGetUniform("timer").set(ZPPostFXChain.TIMER);
            shader.safeGetUniform("value").set((ZPImGuiDebugInterface.FORCE_ENABLE_RADIATION_POST_FX_SHADER ? ZPImGuiDebugInterface.PARAM_RAD_POSTFX[0] : this.value) / 100.0f);
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
        return ZPDefaultShaders.post_fx_radiation;
    }

    @Override
    public boolean bypass() {
        if (Minecraft.getInstance().player != null) {
            if (this.value > 0 || ZPLivingStat.RADIATION.get(Objects.requireNonNull(Minecraft.getInstance().player)) > 0) {
                return false;
            }
        }
        return !ZPImGuiDebugInterface.FORCE_ENABLE_RADIATION_POST_FX_SHADER;
    }
}
