package ru.gltexture.zpm3.engine.client.rendering.postfx;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.fbo.FBOTexture2DProgram;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.fbo.attachments.T2DAttachmentContainer;
import ru.gltexture.zpm3.engine.client.rendering.postfx.processors.ZPNightVisPostFXProcessor;
import ru.gltexture.zpm3.engine.client.rendering.postfx.processors.ZPPostFXProcessor;
import ru.gltexture.zpm3.engine.client.rendering.postfx.processors.ZPSamplePostFXProcessor;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.Comparator;
import java.util.TreeSet;

@OnlyIn(Dist.CLIENT)
public class ZPPostFXChain implements ZPClientCallbacks.ZPClientResourceDependentObject {
    public static ZPPostFXProcessor SAMPLE = new ZPSamplePostFXProcessor(100);
    public static ZPPostFXProcessor NIGHTVIS = new ZPNightVisPostFXProcessor(200);

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
        this.processors.add(ZPPostFXChain.NIGHTVIS);
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
    public void render() {
        if (ZPPostFXChain.screenFBO != null) {
            ZPPostFXChain.TIMER += ZPRenderHelper.DELTA_TIME();
            this.getProcessors().forEach(e -> {
                if (!e.bypass()) {
                   // GL46.glDisable(GL46.GL_DEPTH_TEST);
                    {
                        ZPPostFXChain.screenFBO.bindFBO();
                        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
                        e.renderTextureInFBO(Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
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

    public void addProcessor(ZPPostFXProcessor processor) {
        this.getProcessors().add(processor);
    }

    public void removeProcessor(ZPPostFXProcessor processor) {
        this.getProcessors().remove(processor);
    }

    public TreeSet<ZPPostFXProcessor> getProcessors() {
        return this.processors;
    }

    public ZPClientCallbacks.ZPReloadGameResourcesCallback reloadGameResourcesCallback() {
        return (window) -> {
            this.destroyFBOs();
            this.createFBOs(window.getWidth(), window.getHeight());
        };
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
    public void destroyResources(@NotNull Window window) {
        this.destroyFBOs();
    }

    @Override
    public void setupResources(@NotNull Window window) {
        this.createFBOs(window.getWidth(), window.getHeight());
    }

    @Override
    public void onWindowResizeAction(long descriptor, int width, int height) {
        this.destroyFBOs();
        this.createFBOs(width, height);
    }

   // @SubscribeEvent
   // public void exec(@NotNull RenderGuiEvent.Pre renderLevelStageEvent) {
   //     this.render();
   // }
}
