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
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPShaderLoader;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPZombieConfig;
import ru.gltexture.zpm3.modules.debug.imgui.ZPImGuiDebugInterface;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;

import java.util.Objects;

public class ZPInfectionPostFXProcessor extends ZPPostFXProcessor{
    public ZPInfectionPostFXProcessor(int chainOrder) {
        super(chainOrder);
    }

    public static float getPlagueProgressPercent() {
        if (Minecraft.getInstance().player == null) {
            return 0f;
        }
        MobEffectInstance effect = Minecraft.getInstance().player.getEffect(ZPMobEffects.zombie_plague.get());
        if (effect == null) {
            return 0f;
        }
        int duration = effect.getDuration();
        int maxDuration = ZPZombieConfig.ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS.getVar();
        return 1.0f - Mth.clamp((float) duration / (float) maxDuration, 0f, 1f);
    }
    @Override
    public void renderTextureInFBO(float deltaTime, float partialTicks, int screenTexture_GL_ID) {
        ShaderInstance shader = this.getPostFXShader().getShaderInstance();
        Objects.requireNonNull(shader).apply();
        Window window = Minecraft.getInstance().getWindow();
        GL46.glViewport(0, 0, window.getWidth(), window.getHeight());
        {
            GL46.glActiveTexture(GL46.GL_TEXTURE0);
            GL46.glBindTexture(GL46.GL_TEXTURE_2D, screenTexture_GL_ID);
            shader.safeGetUniform("texture_map").set(0);
            shader.safeGetUniform("value").set((ZPImGuiDebugInterface.FORCE_ENABLE_INFECTION_POST_FX_SHADER ? (ZPImGuiDebugInterface.PARAM_INF_POSTFX[0] / 100.0f) : ZPInfectionPostFXProcessor.getPlagueProgressPercent()));
            ZombiePlague3.getClientManager().renderScreenMesh();
        }
        Objects.requireNonNull(shader).clear();
    }

    @Override
    protected ZPShaderLoader.ZPShaderInstance getPostFXShader() {
        return ZPDefaultShaders.post_fx_infection;
    }

    @Override
    public boolean bypass() {
        if (Minecraft.getInstance().player != null) {
            if (ZPInfectionPostFXProcessor.getPlagueProgressPercent() > 0) {
                return false;
            }
        }
        return !ZPImGuiDebugInterface.FORCE_ENABLE_INFECTION_POST_FX_SHADER;
    }
}
