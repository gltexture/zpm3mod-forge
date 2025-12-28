package ru.gltexture.zpm3.assets.guns.rendering.basic;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.assets.guns.events.ZPGunPostRender;
import ru.gltexture.zpm3.assets.guns.mixins.ext.IZPPlayerClientDataExt;
import ru.gltexture.zpm3.assets.guns.processing.input.ZPClientGunClientTickProcessing;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.fbo.FBOTexture2DProgram;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.fbo.attachments.T2DAttachmentContainer;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunMuzzleflashFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.ZPGunFXGlobalData;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.client.utils.ClientRenderFunctions;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.util.Objects;

public class ZPDefaultGunMuzzleflashFX implements IZPGunMuzzleflashFX, ZPRenderHooks.ZPItemSceneRendering1PersonHooks, ZPRenderHooks.ZPItemSceneRendering3PersonHooks {
    public static final int DEFAULT_PINGPONG_FBO_OPERATIONS_1P = 6;
    public static final float DEFAULT_BLURRING_1P = 3.0f;
    public static final int DEFAULT_PINGPONG_FBO_OPERATIONS_3P = 2;
    public static final float DEFAULT_BLURRING_3P = 2.0f;
    public static final float MUZZLEFLASH_3PERSON_TIME = 0.35f;

    public static String MUZZLEFLASH1_TEXTURE_DEF = "textures/fx/muzzleflash1.png";
    public static String MUZZLEFLASH3_TEXTURE_DEF = "textures/fx/muzzleflash2.png";

    //TEMP
    static {
        MUZZLEFLASH1_TEXTURE_DEF = MUZZLEFLASH3_TEXTURE_DEF;
    }

    public static ResourceLocation muzzleFlash1;
    public static ResourceLocation muzzleFlash3;
    public static FBOTexture2DProgram muzzleflashFBO;
    public static FBOTexture2DProgram muzzleflashBlurFBO;
    public static FBOTexture2DProgram muzzleflashBlurFBOPingPong;

    public static final float MFLASH_FBO_SCALE = 0.1f;

    private final float[] initialRotation;
    private final float[] muzzleflashScissor1Person;
    private float muzzleflashTime1Person;

    protected final Matrix4f[] muzzleflashFinal1PersonTransformation;

    protected ZPDefaultGunMuzzleflashFX() {
        this.muzzleflashTime1Person = 0.0f;
        this.muzzleflashScissor1Person = new float[]{1.0f, 1.0f};
        this.initialRotation = new float[]{0.0f, 0.0f};
        this.muzzleflashFinal1PersonTransformation = new Matrix4f[]{new Matrix4f().identity(), new Matrix4f().identity()};
    }

    public static ZPDefaultGunMuzzleflashFX create() {
        return new ZPDefaultGunMuzzleflashFX();
    }

    @Override
    public void onShot(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, @NotNull ZPClientCallbacks.ZPGunShotCallback.GunFXData gunFXData) {
        if (gunFXData.muzzleflashTime() < 0.0f) {
            return;
        }
        final int id = this.hand(gunFXData.isRightHand());
        this.initialRotation[id] = ZPRandom.instance.randomFloat((float) Math.PI);
        final boolean isTheSame = player.equals(Minecraft.getInstance().player);
        if (isTheSame) {
            this.muzzleflashTime1Person = gunFXData.muzzleflashTime();
            this.muzzleflashScissor1Person[id] = this.muzzleflashScissor1Person[id] > 0.01f ? 0.35f : 0.0f;
        }
        if (Minecraft.getInstance().getCameraEntity() != null) {
            if (isTheSame || Minecraft.getInstance().getEntityRenderDispatcher().shouldRender(player, Minecraft.getInstance().levelRenderer.getFrustum(), Minecraft.getInstance().getCameraEntity().getX(), Minecraft.getInstance().getCameraEntity().getY(), Minecraft.getInstance().getCameraEntity().getZ())) {
                if (player instanceof IZPPlayerClientDataExt playerClientDataExt) {
                    if (!isTheSame || !Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                        playerClientDataExt.getPlayerMuzzleflashScissor3Person()[id] = 0.0f;
                    }
                }
            }
        }
    }

    private int hand(boolean hand) {
        return hand ? 1 : 0;
    }

    @Override
    public void render3Person(@NotNull LivingEntity livingEntity, @NotNull MultiBufferSource buffer, float deltaTicks, boolean isRightHanded) {
        if (livingEntity instanceof IZPPlayerClientDataExt player) {
            player.getPlayerMuzzleflashScissor3Person()[0] += deltaTicks / ZPDefaultGunMuzzleflashFX.MUZZLEFLASH_3PERSON_TIME;
            player.getPlayerMuzzleflashScissor3Person()[1] += deltaTicks / ZPDefaultGunMuzzleflashFX.MUZZLEFLASH_3PERSON_TIME;

            if (DearUITRSInterface.muzzleflashHandling) {
                player.getPlayerMuzzleflashScissor3Person()[0] = player.getPlayerMuzzleflashScissor3Person()[1] = DearUITRSInterface.scissor3P;
            }
            if (player.getPlayerMuzzleflashScissor3Person()[this.hand(isRightHanded)] <= 0.0f || player.getPlayerMuzzleflashScissor3Person()[this.hand(isRightHanded)] > 1.0f) {
                return;
            }
            if (ZPDefaultGunMuzzleflashFX.useFancyRendering3person()) {
                int ref = isRightHanded ? 0x02 : 0x01;
                GL46.glStencilFunc(GL46.GL_NOTEQUAL, ref, 0xFF);
                GL46.glStencilOp(GL46.GL_KEEP, GL46.GL_KEEP, GL46.GL_KEEP);
            }
            this.renderMuzzleflash3Person(player, buffer, deltaTicks, ZPGunFXGlobalData.getGunData(isRightHanded).getMflash3dpTransformationTarget(), isRightHanded);
        }
    }

    private void renderMuzzleflash3Person(@NotNull IZPPlayerClientDataExt player, @NotNull MultiBufferSource buffer, float deltaTicks, @NotNull Matrix4f matrix, boolean isRightHanded) {
        //final Matrix4f viewMatrix = new Matrix4f().identity().rotate(camRot).translate(camPos.negate());
        {
            matrix.translate(DearUITRSInterface.trsMflash3d.position);
            matrix.setRotationXYZ(0.0f, (float) Math.PI, 0.0f);
        }

        float mul = (1.0f);
        final float size = 0.2F;
        float meshSize = size * mul;
        float uvMin = 0.0f - (mul - 1.0f) * 0.5f;
        float uvMax = 1.0f + (mul - 1.0f) * 0.5f;

        {
            ShaderInstance shader = ZPDefaultShaders.muzzleflash3dp.getShaderInstance();

            RenderSystem.setShader(() -> shader);
            GL46.glActiveTexture(GL46.GL_TEXTURE0);
            RenderSystem.setShaderTexture(0, ZPDefaultGunMuzzleflashFX.muzzleFlash3);

            Objects.requireNonNull(Objects.requireNonNull(shader).getUniform("scissor")).set(player.getPlayerMuzzleflashScissor3Person()[this.hand(isRightHanded)]);

            BufferBuilder builder = Tesselator.getInstance().getBuilder();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

            builder.vertex(matrix, -meshSize, -meshSize, 0.0F).uv(uvMin, uvMax).color(255, 255, 255, 255).endVertex();
            builder.vertex(matrix, -meshSize, meshSize, 0.0F).uv(uvMin, uvMin).color(255, 255, 255, 255).endVertex();
            builder.vertex(matrix, meshSize, meshSize, 0.0F).uv(uvMax, uvMin).color(255, 255, 255, 255).endVertex();
            builder.vertex(matrix, meshSize, -meshSize, 0.0F).uv(uvMax, uvMax).color(255, 255, 255, 255).endVertex();

            BufferUploader.drawWithShader(builder.end());
        }
    }

    @Override
    public void render1Person(@NotNull MultiBufferSource buffer, float partialTicks, float deltaTicks) {
        this.muzzleflashScissor1Person[0] += deltaTicks / this.muzzleflashTime1Person;
        this.muzzleflashScissor1Person[1] += deltaTicks / this.muzzleflashTime1Person;
        if (DearUITRSInterface.muzzleflashHandling) {
            this.muzzleflashScissor1Person[0] = this.muzzleflashScissor1Person[1] = DearUITRSInterface.scissor1P;
        }
        if (ZPDefaultGunMuzzleflashFX.useFancyRendering1person()) {
            GL46.glBlendFuncSeparate(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA, GL46.GL_ONE, GL46.GL_ONE_MINUS_SRC_ALPHA);
            ZPDefaultGunMuzzleflashFX.muzzleflashFBO.bindFBO();
            GL46.glDrawBuffers(new int[]{GL46.GL_COLOR_ATTACHMENT1, GL46.GL_COLOR_ATTACHMENT2});
            GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);
        }
        boolean blendEnabled = GL46.glIsEnabled(GL46.GL_BLEND);
        boolean depthEnabled = GL46.glIsEnabled(GL46.GL_DEPTH_TEST);
        GL46.glEnable(GL46.GL_BLEND);
        GL46.glEnable(GL46.GL_DEPTH_TEST);
        this.renderMuzzleFlash1Person(this.muzzleflashScissor1Person[0], false);
        this.renderMuzzleFlash1Person(this.muzzleflashScissor1Person[1], true);
        if (!blendEnabled) {
            GL46.glDisable(GL46.GL_BLEND);
        }
        if (!depthEnabled) {
            GL46.glDisable(GL46.GL_DEPTH_TEST);
        }
    }

    private void renderMuzzleFlash1Person(float muzzleflashScissor, boolean isRightHanded) {
        if (muzzleflashScissor <= 0.0f || muzzleflashScissor > 1.0f) {
            return;
        }

        final Matrix4f gunTransformation = ZPGunFXGlobalData.getGunData(isRightHanded).getCurrentGunItemMatrix();
        final Matrix4f transformation = ZPGunFXGlobalData.getGunData(isRightHanded).getMflash1spTransformationTarget();

        if (transformation == null) {
            return;
        }

        Vector3f pos = new Vector3f();
        Vector3f scale = new Vector3f();
        gunTransformation.mul(transformation);
        gunTransformation.getTranslation(pos);
        gunTransformation.getScale(scale);
        gunTransformation.identity().translate(pos).scale(scale);
        gunTransformation.rotateY((float) Math.PI)
                .rotateZ(this.initialRotation[this.hand(isRightHanded)]);

        this.muzzleflashFinal1PersonTransformation[this.hand(isRightHanded)] = gunTransformation;

        final float size = 0.2F;
        {
            ShaderInstance shader = ZPDefaultShaders.muzzleflash.getShaderInstance();

            RenderSystem.setShader(() -> shader);
            GL46.glActiveTexture(GL46.GL_TEXTURE0);
            RenderSystem.setShaderTexture(0, ZPDefaultGunMuzzleflashFX.muzzleFlash3);

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

    public Matrix4f[] getMuzzleflashFinal1PersonTransformation() {
        return this.muzzleflashFinal1PersonTransformation;
    }

    public float[] getMuzzleflashScissor1Person() {
        return this.muzzleflashScissor1Person;
    }

    public static int minecraftGraphicSetting() {
        GraphicsStatus graphics = Minecraft.getInstance().options.graphicsMode().get();
        switch (graphics) {
            case FABULOUS -> {
                return 3;
            }
            case FANCY -> {
                return 2;
            }
            default -> {
                return 1;
            }
        }
    }

    public static int minQuality() {
        return Math.min(ZPDefaultGunMuzzleflashFX.quality(), ZPDefaultGunMuzzleflashFX.minecraftGraphicSetting());
    }

    public static boolean renderMuzzleflash1Person() {
        return ZPDefaultGunMuzzleflashFX.minQuality() >= 2 && ZPConstants.RENDER_MUZZLE_FLASHES;
    }

    public static boolean renderMuzzleflash3Person() {
        return ZPDefaultGunMuzzleflashFX.minQuality() >= 2 && ZPConstants.RENDER_MUZZLE_FLASHES;
    }

    public static boolean useFancyRendering1person() {
        return ZPDefaultGunMuzzleflashFX.minQuality() == 3 && ZPDefaultGunMuzzleflashFX.muzzleflashFBO != null && !ZPDefaultGunMuzzleflashFX.muzzleflashFBO.getTexturePrograms().isEmpty();
    }

    public static boolean useFancyRendering3person() {
        return ZPDefaultGunMuzzleflashFX.minQuality() == 3;
    }

    public static int quality() {
        return DearUITRSInterface.muzzleflashRenderingMode;
    }

    public ZPClientCallbacks.ZPReloadGameResourcesCallback reloadGameResourcesCallback() {
        return (window) -> {
            this.destroyFBOs();
            this.createFBOs(window.getWidth(), window.getHeight());
        };
    }

    private void destroyFBOs() {
        if (ZPDefaultGunMuzzleflashFX.muzzleflashFBO != null) {
            ZPDefaultGunMuzzleflashFX.muzzleflashFBO.clearFBO();
            ZPDefaultGunMuzzleflashFX.muzzleflashFBO = null;
        }
        if (ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBO != null) {
            ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBO.clearFBO();
            ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBO = null;
        }
    }

    private void createFBOs(int width, int height) {
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
        this.destroyFBOs();
    }

    @Override
    public void setupResources(@NotNull Window window) {
        ZPDefaultGunMuzzleflashFX.muzzleFlash1 = ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), ZPDefaultGunMuzzleflashFX.MUZZLEFLASH1_TEXTURE_DEF);
        ZPDefaultGunMuzzleflashFX.muzzleFlash3 = ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), ZPDefaultGunMuzzleflashFX.MUZZLEFLASH3_TEXTURE_DEF);
        this.createFBOs(window.getWidth(), window.getHeight());
    }

    @Override
    public void onWindowResizeAction(long descriptor, int width, int height) {
        this.destroyFBOs();
        this.createFBOs(width, height);
    }

    @Override
    public void onPreRender1Person(float deltaTicks, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight) {
        if (ZPDefaultGunMuzzleflashFX.useFancyRendering1person()) {
            GL46.glBlendFuncSeparate(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA, GL46.GL_ONE, GL46.GL_ONE_MINUS_SRC_ALPHA);
            ZPDefaultGunMuzzleflashFX.muzzleflashFBO.bindFBO();
            GL46.glClear(GL46.GL_DEPTH_BUFFER_BIT | GL46.GL_STENCIL_BUFFER_BIT);
            GL46.glDrawBuffers(new int[]{GL46.GL_COLOR_ATTACHMENT0});
            GL46.glClearBufferfv(GL46.GL_COLOR, 0, new float[]{0.0f, 0.0f, 0.0f, 0.0f});
        }
    }

    @Override
    public void onPostRender1Person(float deltaTicks, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight) {
        if (ZPDefaultGunMuzzleflashFX.renderMuzzleflash1Person()) {
            this.render1Person(pBuffer, pPartialTicks, deltaTicks);
        }

        if (ZPDefaultGunMuzzleflashFX.useFancyRendering1person()) {
            ZPDefaultGunMuzzleflashFX.muzzleflashFBO.unBindFBO();
        }
    }

    @Override
    public void onPostRender3Person(float deltaTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, LivingEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (ZPDefaultGunMuzzleflashFX.renderMuzzleflash3Person()) {
            boolean stencilEnabled = GL46.glIsEnabled(GL46.GL_STENCIL_TEST);
            boolean depthEnabled = GL46.glIsEnabled(GL46.GL_DEPTH_TEST);
            boolean blendEnabled = GL46.glIsEnabled(GL46.GL_BLEND);
            GL46.glEnable(GL46.GL_STENCIL_TEST);
            GL46.glEnable(GL46.GL_DEPTH_TEST);
            GL46.glEnable(GL46.GL_BLEND);
            GL46.glBlendEquation(GL46.GL_FUNC_ADD);
            //GL46.glBlendFuncSeparate(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA, GL46.GL_ONE, GL46.GL_ONE_MINUS_SRC_ALPHA);
            this.render3Person(pLivingEntity, pBuffer, deltaTicks, false);
            this.render3Person(pLivingEntity, pBuffer, deltaTicks, true);
            //GL46.glBlendFuncSeparate(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA, GL46.GL_ONE, GL46.GL_ONE_MINUS_SRC_ALPHA);
            if (!stencilEnabled) {
                GL46.glDisable(GL46.GL_STENCIL_TEST);
            }
            if (!depthEnabled) {
                GL46.glDisable(GL46.GL_DEPTH_TEST);
            }
            if (!blendEnabled) {
                GL46.glDisable(GL46.GL_BLEND);
            }
        }
    }

    @Override
    public void onPreRender3Person(float deltaTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, LivingEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (ZPDefaultGunMuzzleflashFX.renderMuzzleflash3Person()) {
            GL46.glClear(GL46.GL_STENCIL_BUFFER_BIT);
        }
    }
}
