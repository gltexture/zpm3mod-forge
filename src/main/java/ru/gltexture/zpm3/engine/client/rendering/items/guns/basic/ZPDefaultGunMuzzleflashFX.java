package ru.gltexture.zpm3.engine.client.rendering.items.guns.basic;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.fbo.FBOTexture2DProgram;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.fbo.attachments.T2DAttachmentContainer;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.ZPGunLayersProcessing;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.fx.IZPGunMuzzleflashFX;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.fx.ZPGunFXGlobalData;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.instances.guns.ZPBaseGun;

import java.util.Objects;

public class ZPDefaultGunMuzzleflashFX implements IZPGunMuzzleflashFX, ZPRenderHooks.ZPItemSceneRenderingHooks {
    public static final int DEFAULT_PINGPONG_FBO_OPERATIONS = 8;
    public static String MUZZLEFLASH_TEXTURE_DEF = "textures/fx/muzzleflash1.png";
    static ResourceLocation muzzleFlash1;
    public static FBOTexture2DProgram muzzleflashFBO;
    public static FBOTexture2DProgram muzzleflashBlurFBO;
    public static FBOTexture2DProgram muzzleflashBlurFBOPingPong;

    public static final float DEFAULT_BLURRING = 3.0f;
    public static final float MFLASH_FBO_SCALE = 0.1f;

    private final float[] muzzleflashScissor;
    private float muzzleflashTime;

    protected final Matrix4f[] muzzleflashFinalTransformation;

    protected ZPDefaultGunMuzzleflashFX() {
        this.muzzleflashTime = 0.0f;
        this.muzzleflashScissor = new float[] {0.0f, 0.0f};
        this.muzzleflashFinalTransformation = new Matrix4f[] {new Matrix4f().identity(), new Matrix4f().identity()};
    }

    public static ZPDefaultGunMuzzleflashFX create() {
        return new ZPDefaultGunMuzzleflashFX();
    }

    @Override
    public void triggerRecoil(@NotNull ZPBaseGun baseGun, ZPClientCallbacks.ZPGunShotCallback.@NotNull GunFXData gunFXData) {
        this.muzzleflashTime = gunFXData.muzzleflashTime();
        this.muzzleflashScissor[this.hand(gunFXData.isRightHand())] = 0.0f;
    }

    @Override
    public void onClientTick() {

    }

    private int hand(boolean hand) {
        return hand ? 1 : 0;
    }

    @Override
    public void render(@NotNull MultiBufferSource buffer, float partialTicks, float deltaTicks) {
        if (!this.useRendering()) {
            return;
        }
        this.muzzleflashScissor[0] += deltaTicks / this.muzzleflashTime;
        this.muzzleflashScissor[1] += deltaTicks / this.muzzleflashTime;
        if (DearUITRSInterface.muzzleflashHandling) {
            this.muzzleflashScissor[0] = this.muzzleflashScissor[1] = DearUITRSInterface.scissor;
        }
        if (this.useFbo()) {
            ZPDefaultGunMuzzleflashFX.muzzleflashFBO.bindFBO();
            GL46.glDrawBuffers(new int[]{GL46.GL_COLOR_ATTACHMENT1, GL46.GL_COLOR_ATTACHMENT2});
            GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);
        }
        GL46.glEnable(GL46.GL_BLEND);
        GL46.glEnable(GL46.GL_DEPTH_TEST);
        this.renderMuzzleFlash(this.muzzleflashScissor[0], false);
        this.renderMuzzleFlash(this.muzzleflashScissor[1], true);
        GL46.glDisable(GL46.GL_BLEND);
        GL46.glDisable(GL46.GL_DEPTH_TEST);
    }

    private void renderMuzzleFlash(float muzzleflashScissor, boolean isRightHanded) {
        if (muzzleflashScissor <= 0.0f || muzzleflashScissor > 1.0f) {
            return;
        }

        final Matrix4f gunTransformation = new Matrix4f(isRightHanded ? ZPGunFXGlobalData.getRight().getCurrentGunItemMatrix() : ZPGunFXGlobalData.getLeft().getCurrentGunItemMatrix());
        final Matrix4f transformation = new Matrix4f(isRightHanded ? ZPGunFXGlobalData.getRight().getCurrentMuzzleflashOffset() : ZPGunFXGlobalData.getLeft().getCurrentMuzzleflashOffset());

        Vector3f pos = new Vector3f();
        Vector3f scale = new Vector3f();
        gunTransformation.mul(transformation);
        gunTransformation.getTranslation(pos);
        gunTransformation.getScale(scale);
        gunTransformation.identity().translate(pos).scale(scale);
        gunTransformation.rotateY((float) Math.PI);
//.rotateZ((float) (muzzleflashScissor * Math.PI * 3.0f))

        this.muzzleflashFinalTransformation[this.hand(isRightHanded)] = gunTransformation;

        final float size = 0.3F;
        {
            ShaderInstance shader = ZPDefaultShaders.muzzleflash.getShaderInstance();

            RenderSystem.setShader(() -> shader);
            GL46.glActiveTexture(GL46.GL_TEXTURE0);
            RenderSystem.setShaderTexture(0, ZPDefaultGunMuzzleflashFX.muzzleFlash1);

            Objects.requireNonNull(Objects.requireNonNull(shader).getUniform("scissor")).set(muzzleflashScissor);

            BufferBuilder builder = Tesselator.getInstance().getBuilder();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

            builder.vertex(gunTransformation, -size, -size, 0.0F).uv(0.0F, 1.0F).color(255, 255, 255, 255).endVertex();
            builder.vertex(gunTransformation, -size, size, 0.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
            builder.vertex(gunTransformation, size, size, 0.0F).uv(1.0F, 0.0F).color(255, 255, 255, 255).endVertex();
            builder.vertex(gunTransformation, size, -size, 0.0F).uv(1.0F, 1.0F).color(255, 255, 255, 255).endVertex();

            final int oldDepthFunc = GL46.glGetInteger(GL46.GL_DEPTH_FUNC);
            GL46.glDepthFunc(GL46.GL_LEQUAL);
            BufferUploader.drawWithShader(builder.end());
            GL46.glDepthFunc(oldDepthFunc);
        }
    }

    public Matrix4f[] getMuzzleflashFinalTransformation() {
        return this.muzzleflashFinalTransformation;
    }

    public float[] getMuzzleflashScissor() {
        return this.muzzleflashScissor;
    }

    public boolean useRendering() {
        return this.quality() >= 2;
    }

    public boolean useFbo() {
        return this.quality() == 3 && ZPDefaultGunMuzzleflashFX.muzzleflashFBO != null;
    }

    public int quality() {
        return DearUITRSInterface.muzzleflashRenderingMode;
    }

    private static void destroyFBOs() {
        if (ZPDefaultGunMuzzleflashFX.muzzleflashFBO != null) {
            ZPDefaultGunMuzzleflashFX.muzzleflashFBO.clearFBO();
        }
        if (ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBO != null) {
            ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBO.clearFBO();
        }
    }

    private static void createFBOs(int width, int height) {
        T2DAttachmentContainer clr = new T2DAttachmentContainer() {{
            add(GL46.GL_COLOR_ATTACHMENT0, GL46.GL_RGBA, GL46.GL_RGBA);
            add(GL46.GL_COLOR_ATTACHMENT1, GL46.GL_RGBA, GL46.GL_RGBA);
            add(GL46.GL_COLOR_ATTACHMENT2, GL46.GL_RGBA, GL46.GL_RGBA);
        }};
        T2DAttachmentContainer clr2 = new T2DAttachmentContainer() {{
            add(GL46.GL_COLOR_ATTACHMENT0, GL46.GL_RGBA, GL46.GL_RGBA);
        }};

        ZPDefaultGunMuzzleflashFX.muzzleflashFBO = new FBOTexture2DProgram(true);
        ZPDefaultGunMuzzleflashFX.muzzleflashFBO.createFrameBuffer2DTexture(new Vector2i(width, height), clr, true, GL46.GL_NEAREST, GL46.GL_NONE, GL46.GL_LESS, GL46.GL_CLAMP_TO_EDGE, null);

        ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBO = new FBOTexture2DProgram(true);
        ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBO.createFrameBuffer2DTexture(new Vector2i((int) (width * ZPDefaultGunMuzzleflashFX.MFLASH_FBO_SCALE), (int) (height * ZPDefaultGunMuzzleflashFX.MFLASH_FBO_SCALE)), clr2, false, GL46.GL_LINEAR, GL46.GL_NONE, GL46.GL_LESS, GL46.GL_CLAMP_TO_EDGE, null);

        ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBOPingPong = new FBOTexture2DProgram(true);
        ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBOPingPong.createFrameBuffer2DTexture(new Vector2i((int) (width * ZPDefaultGunMuzzleflashFX.MFLASH_FBO_SCALE), (int) (height * ZPDefaultGunMuzzleflashFX.MFLASH_FBO_SCALE)), clr2, false, GL46.GL_LINEAR, GL46.GL_NONE, GL46.GL_LESS, GL46.GL_CLAMP_TO_EDGE, null);
    }

    @Override
    public void destroyResources(@NotNull Window window) {
        ZPDefaultGunMuzzleflashFX.destroyFBOs();
    }

    @Override
    public void setupResources(@NotNull Window window) {
        ZPDefaultGunMuzzleflashFX.muzzleFlash1 = ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), ZPDefaultGunMuzzleflashFX.MUZZLEFLASH_TEXTURE_DEF);
        ZPDefaultGunMuzzleflashFX.createFBOs(window.getWidth(), window.getHeight());
    }

    @Override
    public void onWindowResizeAction(long descriptor, int width, int height) {
        ZPDefaultGunMuzzleflashFX.destroyFBOs();
        ZPDefaultGunMuzzleflashFX.createFBOs(width, height);
    }

    @Override
    public void onPreRender(float deltaTicks, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight) {
        if (this.useFbo()) {
            ZPDefaultGunMuzzleflashFX.muzzleflashFBO.bindFBO();
            GL46.glClear(GL46.GL_DEPTH_BUFFER_BIT | GL46.GL_STENCIL_BUFFER_BIT);
            GL46.glDrawBuffers(new int[]{GL46.GL_COLOR_ATTACHMENT0});
            GL46.glClearBufferfv(GL46.GL_COLOR, 0, new float[]{0.0f, 0.0f, 0.0f, 0.0f});
        }
    }

    @Override
    public void onPostRender(float deltaTicks, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight) {
        this.render(pBuffer, pPartialTicks, deltaTicks);
        if (this.useFbo()) {
            ZPDefaultGunMuzzleflashFX.muzzleflashFBO.unBindFBO();
            ZPGunLayersProcessing.postRender(this);
        }
    }
}
