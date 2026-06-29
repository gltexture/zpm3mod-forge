package ru.gltexture.zpm3.engine.client.rendering;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;

import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.crosshair.ZPClientCrosshairRecoilManager;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.base.ITexture2DProgram;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.meshes.ZPScreenMesh;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.client.rendering.postfx.ZPPostFXChain;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.client.rendering.ui.imgui.ZPDearUIRenderer;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPClientConfig;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.events.client.ZPClientForge;
import ru.gltexture.zpm3.engine.instances.items.ZPItemMedicine;
import ru.gltexture.zpm3.engine.mixins.impl.client.render.ZPGameRendererFovAccessor;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.modules.common.utils.ZPCommonClientUtils;

import java.util.List;
import java.util.function.Consumer;

public final class ZPRenderHelper implements ZPClientCallbacks.ZPClientResourceDependentObject {
    private final ZPPostFXChain postFXChain;
    public static ZPRenderHelper INSTANCE = new ZPRenderHelper();
    private @Nullable ZPDearUIRenderer dearUIRenderer;
    private ZPScreenMesh screenMesh;

    private ZPRenderHelper() {
        {
            this.postFXChain = new ZPPostFXChain();
        }

        try {
            Class.forName("imgui.ImGui", false, ZPRenderHelper.class.getClassLoader());
            Class.forName("imgui.gl3.ImGuiImplGl3", false, ZPRenderHelper.class.getClassLoader());
            this.dearUIRenderer = new ZPDearUIRenderer(ZPDefaultShaders.imgui::getShaderInstance);
        } catch (ClassNotFoundException e) {
        }
    }

    public static float DELTA_TIME() {
        return ZPClientForge.RENDER_DELTA_TIME;
    }

    public void init() {
        ZPClientCallbacksManager.INSTANCE.addResourceDependentObjectCallback(this.getPostFXChain());
        ZPClientCallbacksManager.INSTANCE.addReloadGameResourcesCallback(this.getPostFXChain().reloadGameResourcesCallback());
        ZPClientCallbacksManager.INSTANCE.addResourceDependentObjectCallback(this);
        if (ZPRenderHelper.INSTANCE.getDearUIRenderer() != null) {
            ZPClientCallbacksManager.INSTANCE.addResourceDependentObjectCallback(ZPRenderHelper.INSTANCE.getDearUIRenderer());
        }
        ZPClientCallbacksManager.INSTANCE.addResourceDependentObjectCallback(ZPRenderHooksManager.INSTANCE);

       //ZPRenderHooksManager.INSTANCE.addItemSceneRendering1PersonHookPost((deltaTicks, pPartialTicks, pPoseStack, pBuffer, pPlayerEntity, pCombinedLight) -> {
       //    this.postFXChain.render();
       //});

        ZPRenderHooksManager.INSTANCE.addSceneRenderingHook(((renderStage, partialTicks, deltaTime, pNanoTime, pRenderLevel) -> {
            if (renderStage == RenderStage.POST) {
                if (ZombiePlague3.isDevEnvironment()) {
                    if (!Minecraft.getInstance().isPaused() && this.getDearUIRenderer() != null) {
                        this.getDearUIRenderer().getInterfacesManager().renderAll(Minecraft.getInstance().getWindow(), deltaTime);
                    }
                }
            }
        }));

        ZPRenderHooksManager.INSTANCE.addSceneRenderingHook((renderStage, partialTicks, deltaTime, pNanoTime, pRenderLevel) -> {
            if (renderStage == RenderStage.PRE) {
                ZPClientCrosshairRecoilManager.onRenderTick(deltaTime, Minecraft.getInstance());
            }
        });

        ZPClientCallbacksManager.INSTANCE.addClientTickCallback(e -> {
            if (e == TickEvent.Phase.START) {
                ZPClientCrosshairRecoilManager.onClientTick(Minecraft.getInstance());
            }
        });
    }

    public void registerEvents() {
        //MinecraftForge.EVENT_BUS.register(this.getPostFXChain());
    }

    public @Nullable ZPDearUIRenderer getDearUIRenderer() {
        return this.dearUIRenderer;
    }

    @Override
    public void destroyResources(@NotNull Window window) {
        this.screenMesh.clear();
    }

    @Override
    public void setupResources(@NotNull Window window) {
        this.screenMesh = new ZPScreenMesh();
        Minecraft.getInstance().getMainRenderTarget().enableStencil();
    }

    @Override
    public void onWindowResizeAction(long descriptor, int width, int height) {
    }

    public void renderZpScreenMesh() {
        GL46.glBindVertexArray(this.getScreenMesh().getVao());
        GL46.glEnableVertexAttribArray(0);
        GL46.glEnableVertexAttribArray(1);
        GL46.glDrawElements(GL46.GL_TRIANGLES, 6, GL46.GL_UNSIGNED_INT, 0);
        GL46.glDisableVertexAttribArray(0);
        GL46.glDisableVertexAttribArray(1);
    }

    public ZPPostFXChain getPostFXChain() {
        return this.postFXChain;
    }

    public ZPScreenMesh getScreenMesh() {
        return this.screenMesh;
    }

    public enum RenderStage {
        PRE,
        POST
    }


    // =================================================================================================================


    public static double fovItemOffset(Camera camera, float partialTicks, PoseStack poseStack) {
        if (ZPClientConfig.FIRST_PERSON_RENDER_SCALE_TYPE.getVar() == 1) {
            return 0.0f;
        }
        final double def = 70.0f;
        final double maxAbs = 110.0f - def;
        double fov = ((ZPGameRendererFovAccessor) Minecraft.getInstance().gameRenderer).invokeGetFov(camera, partialTicks, true);
        double absFov = (fov - def) / maxAbs;
        return Math.pow(Math.abs(absFov), 1.25f) * Math.signum(absFov);
    }

    @SuppressWarnings("all")
    public static boolean isPlayerModelSlim() {
        PlayerInfo info = Minecraft.getInstance().getConnection().getPlayerInfo(Minecraft.getInstance().player.getUUID());
        if (info != null) {
            boolean slim = info.getModelName().equals("slim");
        }
        return false;
    }

    public static void blockAnimation(AbstractClientPlayer pPlayer, float pPartialTicks, InteractionHand pHand, ItemStack pStack, PoseStack pPoseStack) {
        if (pStack.getItem() instanceof ZPItemMedicine) {
            if (pPlayer.isUsingItem() && pPlayer.getUseItemRemainingTicks() > 0 && pPlayer.getUsedItemHand() == pHand) {
                if (pStack.getUseAnimation() == UseAnim.BLOCK) {
                    applyMedicineTransform(pPlayer, pPoseStack, pPartialTicks, (pHand.equals(InteractionHand.OFF_HAND) ? HumanoidArm.LEFT : HumanoidArm.RIGHT), pStack);
                }
            }
        }
    }

    private static void applyMedicineTransform(AbstractClientPlayer pPlayer, PoseStack pPoseStack, float pPartialTicks, HumanoidArm pHand, ItemStack pStack) {
        float f = (float) pPlayer.getUseItemRemainingTicks() - pPartialTicks + 1.0F;
        float f1 = f / (float) pStack.getUseDuration();

        float f3 = 1.0F - (float) Math.pow(f1, 4.0D);

        if (pHand == HumanoidArm.RIGHT) {
            pPoseStack.translate(f3 * 0.32f, f3 * 0.28f, f3 * -0.15f);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(f3 * 98.0f));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(f3 * -67.0f));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(f3 * -10.0f));
        } else { //TODO
            pPoseStack.translate(-f3 * 0.32f, f3 * 0.28f, f3 * -0.15f);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(f3 * -98.0f));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(f3 * -67.0f));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(f3 * 10.0f));
        }
    }

    public static void addAcidParticles(Entity entity) {
        addAcidParticles(20, entity);
    }

    public static void addAcidParticles(int acidLevel, Entity entity) {
        final float scaling = Mth.clamp(acidLevel / 20.0f, 0.175f, 1.0f);
        int maxParticles = 1 + (int) Math.floor(entity.getBbWidth() * entity.getBbHeight());
        maxParticles = Math.min(maxParticles, 6);

        for (int i = 0; i < maxParticles; ++i) {
            final Vector3f randomVector = ZPRandom.instance.randomVector3f(0.15f, new Vector3f(0.3f, 0.1f, 0.3f)).mul(scaling);
            final Vector3f position = entity.position().toVector3f().add(0.0f, ZPRandom.instance.randomFloat(entity.getBbHeight()), 0.0f);
            position.add(ZPRandom.instance.randomVector3f(0.3f, new Vector3f(0.6f, 0.3f, 0.6f)));
            ZPCommonClientUtils.emmitAcidParticle((2.0f * scaling) + 0.2f + ZPRandom.getRandom().nextFloat(0.4f), position, new Vector3f(randomVector.x, (randomVector.y * 0.1f) + 0.05f, randomVector.z));
        }
    }

    @Deprecated(forRemoval = true)
    public static void renderTextureIDScreenOverlayFromFBO(@NotNull ShaderInstance shaderToRender, @NotNull Consumer<ShaderInstance> doUniforms, @NotNull List<Pair<String, ITexture2DProgram>> texturesWithUniforms) {
        shaderToRender.apply();

        //  GL46.glDisable(GL46.GL_CULL_FACE);
        GL46.glEnable(GL46.GL_BLEND);
        GL46.glDisable(GL46.GL_DEPTH_TEST);

        int texUnit = 0;
        for (Pair<String, ITexture2DProgram> pair : texturesWithUniforms) {
            String uniformName = pair.first();
            ITexture2DProgram textureProgram = pair.second();

            Uniform uniform = shaderToRender.getUniform(uniformName);
            if (uniform != null) {
                uniform.set(texUnit);
            }

            GL46.glActiveTexture(GL46.GL_TEXTURE0 + texUnit);
            textureProgram.bindSampler(texUnit);
            textureProgram.bindTexture();

            texUnit++;
        }

        doUniforms.accept(shaderToRender);

        INSTANCE.renderZpScreenMesh();

        texUnit = 0;
        for (Pair<String, ITexture2DProgram> pair : texturesWithUniforms) {
            pair.second().unBindSampler(texUnit);
            texUnit++;
        }

        GL46.glEnable(GL46.GL_DEPTH_TEST);
        GL46.glActiveTexture(GL46.GL_TEXTURE0);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
    }

    public static Vector2i getWindowSize() {
        final Minecraft mc = Minecraft.getInstance();
        final int w = mc.getWindow().getWidth();
        final int h = mc.getWindow().getHeight();
        return new Vector2i(w, h);
    }
}