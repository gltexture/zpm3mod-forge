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

package ru.gltexture.zpm3.engine.client.rendering.postfx;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.rendering.gl.fbo.FBOTexture2DProgram;
import ru.gltexture.zpm3.engine.client.rendering.gl.fbo.attachments.T2DAttachmentContainer;
import ru.gltexture.zpm3.engine.client.rendering.postfx.processors.*;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@OnlyIn(Dist.CLIENT)
public class ZPPostFXChain implements IZPPostFXChain {
    public static ZPPostFXProcessor SAMPLE = new ZPSamplePostFXProcessor(1000);
    public static ZPPostFXProcessor INFECTION = new ZPInfectionPostFXProcessor(2000);
    public static ZPPostFXProcessor ADRENALINE = new ZPAdrenalinePostFXProcessor(3000);
    public static ZPPostFXProcessor BETTERVIS = new ZPBetterVisionPostFXProcessor(4000);
    public static ZPPostFXProcessor NIGHTVIS = new ZPNightVisPostFXProcessor(5000);
    public static ZPPostFXProcessor MASK = new ZPMaskVignettePostFXProcessor(6000);
    public static ZPPostFXProcessor RADIATION = new ZPRadiationPostFXProcessor(7000);
    public static ZPPostFXProcessor ACID = new ZPAcidPostFXProcessor(8000);

    public static @Nullable FBOTexture2DProgram screenFBO;
    private final TreeSet<ZPPostFXProcessor> processors;

    public static float TIMER = 0.0f;

    public ZPPostFXChain() {
        this.processors = new TreeSet<>(Comparator.comparingInt(ZPPostFXProcessor::getChainOrder));
        {
            this.defaultFX();
        }
    }

    private void defaultFX() {
        this.processors.add(ZPPostFXChain.SAMPLE);
        this.processors.add(ZPPostFXChain.RADIATION);
        this.processors.add(ZPPostFXChain.NIGHTVIS);
        this.processors.add(ZPPostFXChain.MASK);
        this.processors.add(ZPPostFXChain.INFECTION);
        this.processors.add(ZPPostFXChain.BETTERVIS);
        this.processors.add(ZPPostFXChain.ADRENALINE);
        this.processors.add(ZPPostFXChain.ACID);
    }

    public void setupOverlayRenderState(boolean blend, boolean depthTest) {
        if (blend) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        } else {
            RenderSystem.disableBlend();
        }

        if (depthTest) {
            RenderSystem.enableDepthTest();
        } else {
            RenderSystem.disableDepthTest();
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
    }

    @SuppressWarnings("unchecked")
    public void render(float deltaTime, float partialTicks) {
        if (ZPPostFXChain.screenFBO != null && Minecraft.getInstance().getMainRenderTarget().width > 0 && Minecraft.getInstance().getMainRenderTarget().height > 0) {
            if (GLFW.glfwGetWindowAttrib(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_ICONIFIED) == GLFW.GLFW_FALSE) {
                ZPPostFXChain.TIMER += deltaTime;
                this.getProcessors().forEach(e -> {
                    if (!e.bypass()) {
                        // GL46.glDisable(GL46.GL_DEPTH_TEST);
                        {
                            ZPPostFXChain.screenFBO.bindFBO();
                            GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
                            e.renderTextureInFBO(deltaTime, partialTicks, Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
                            ZPPostFXChain.screenFBO.unBindFBO();

                            ZPPostFXChain.screenFBO.copyFBOtoFBOColor(Minecraft.getInstance().getMainRenderTarget().frameBufferId, new Pair[]{Pair.of(GL46.GL_COLOR_ATTACHMENT0, GL46.GL_COLOR_ATTACHMENT0)}
                                    , new Vector2i(Minecraft.getInstance().getMainRenderTarget().width, Minecraft.getInstance().getMainRenderTarget().height)
                                    , new Vector2i(Minecraft.getInstance().getMainRenderTarget().width, Minecraft.getInstance().getMainRenderTarget().height));
                        }
                        // GL46.glEnable(GL46.GL_DEPTH_TEST);
                    }
                });
            }
        }
    }

    public void clientPostTick() {
        this.getProcessors().forEach(ZPPostFXProcessor::clientPostTick);
    }

    public void clientPreTick() {
        this.getProcessors().forEach(ZPPostFXProcessor::clientPreTick);
    }

    public void addProcessor(@NotNull ZPPostFXProcessor processor) {
        this.getProcessors().add(processor);
    }

    public void removeProcessor(@NotNull ZPPostFXProcessor processor) {
        this.getProcessors().remove(processor);
    }

    public @NotNull Set<ZPPostFXProcessor> getProcessors() {
        return Collections.unmodifiableSet(this.processors);
    }

    private void destroyFBOs() {
        if (ZPPostFXChain.screenFBO != null) {
            ZPPostFXChain.screenFBO.clearFBO();
            ZPPostFXChain.screenFBO = null;
        }
    }

    private void createFBOs(int width, int height) {
        T2DAttachmentContainer c = new T2DAttachmentContainer() {{
            add(GL46.GL_COLOR_ATTACHMENT0, GL46.GL_RGBA, GL46.GL_RGBA);
        }};

        ZPPostFXChain.screenFBO = new FBOTexture2DProgram(true);
        ZPPostFXChain.screenFBO.createFrameBuffer2DTexture(new Vector2i(width, height), c, false, GL46.GL_NEAREST, GL46.GL_NONE, GL46.GL_LESS, GL46.GL_CLAMP_TO_EDGE, null);
    }

    @Override
    public void onDestroyResources(@NotNull Window window) {
        this.destroyFBOs();
    }

    @Override
    public void onSetupResources(@NotNull Window window) {
        this.createFBOs(window.getWidth(), window.getHeight());
    }

    @Override
    public void onWindowResized(long descriptor, int width, int height) {
        this.destroyFBOs();
        this.createFBOs(width, height);
    }

    @Override
    public void onReloadResources(@NotNull Window window) {
        this.destroyFBOs();
        this.createFBOs(window.getWidth(), window.getHeight());
    }

    // @ZombiePlagueEvent
   // public void exec(@NotNull RenderGuiEvent.Pre renderLevelStageEvent) {
   //     this.render();
   // }
}
