package ru.gltexture.zpm3.assets.debug.imgui;

import com.mojang.blaze3d.platform.Window;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiCond;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.engine.client.rendering.ui.imgui.interfaces.DearUIInterface;
import ru.gltexture.zpm3.engine.mixins.ext.IZPPlayerMixinExt;

public class DearUITRSInterface implements DearUIInterface {
    public DearUITRSInterface() {
    }

    public static final TRS trsGun3d = new TRS("Matrix Gun 3d Person");
    public static final TRS trsMflash3d = new TRS("Matrix Mflash 3d Person");

    public static final TRS trsGun = new TRS("Matrix Gun");
    public static final TRS trsArm = new TRS("Matrix Arm");
    public static final TRS trsMFlash = new TRS("Matrix mflash");
    public static final TRS trsReloadingGun = new TRS("Matrix Gun Reloading");
    public static final TRS trsReloadingArm = new TRS("Matrix Arm Reloading");

    public static boolean muzzleflashHandling = false;
    public static boolean emmitSmoke = false;
    public static boolean emmitShells = false;

    public static int muzzleflashRenderingMode = 3;
    public static int muzzleflash1PersonFboPingPongOperations = ZPDefaultGunMuzzleflashFX.DEFAULT_PINGPONG_FBO_OPERATIONS_1P;
    public static float muzzleFlash1PersonBlurring = ZPDefaultGunMuzzleflashFX.DEFAULT_BLURRING_1P;
    public static int muzzleflash3PersonFboPingPongOperations = ZPDefaultGunMuzzleflashFX.DEFAULT_PINGPONG_FBO_OPERATIONS_3P;
    public static float muzzleFlash3PersonBlurring = ZPDefaultGunMuzzleflashFX.DEFAULT_BLURRING_3P;

    public static float scissor3P = 0.0f;
    public static float scissor1P = 0.0f;
    public static float reloadProgression = 0.0f;

    public static boolean debugDarknessValueEnable;
    public static float debugDarknessValue;

    public static class TRS {
        public final String label;
        public final Vector3f position = new Vector3f(0.0f);
        public final Vector3f rotation = new Vector3f(0.0f);
        public final Vector3f scale = new Vector3f(1.0f);

        public TRS(String label) {
            this.label = label;
        }
    }

    public void drawGui(@NotNull Window window, @NotNull MouseHandler mouseHandler) {
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Once);
        ImGui.setNextWindowSize(400, 600, ImGuiCond.Once);
        ImGui.begin("debug");

        @Nullable Player player = Minecraft.getInstance().player;
        if (player instanceof IZPPlayerMixinExt ext) {
            ImGui.text("Ping: " + ext.getPing());
        }
        ImGui.separator();
        if (ImGui.collapsingHeader("Gun-Rendering")) {
            ImGui.treePush();

            if (ImGui.checkbox("manual mflash", DearUITRSInterface.muzzleflashHandling)) {
                DearUITRSInterface.muzzleflashHandling = !DearUITRSInterface.muzzleflashHandling;
            }
            if (ImGui.checkbox("emmit smoke", DearUITRSInterface.emmitSmoke)) {
                DearUITRSInterface.emmitSmoke = !DearUITRSInterface.emmitSmoke;
            }
            if (ImGui.checkbox("emmit shells", DearUITRSInterface.emmitShells)) {
                DearUITRSInterface.emmitShells = !DearUITRSInterface.emmitShells;
            }
            int[] mode = new int[] {DearUITRSInterface.muzzleflashRenderingMode};
            ImGui.sliderInt("Quality", mode, 0, 3);
            DearUITRSInterface.muzzleflashRenderingMode = mode[0];

            if (ImGui.treeNode("Adjust Gun Render(1P)")) {
                if (ImGui.collapsingHeader("Matrices")) {
                    ImGui.treePush();
                    ImGui.pushStyleColor(ImGuiCol.Text, 0xffff00ff);
                    this.drawTRS(trsGun);
                    this.drawTRS(trsArm);
                    this.drawTRS(trsMFlash);
                    this.drawTRS(trsReloadingGun);
                    this.drawTRS(trsReloadingArm);
                    ImGui.popStyleColor();
                    ImGui.treePop();
                }
                float[] scissor = new float[] {DearUITRSInterface.scissor1P};
                ImGui.sliderFloat("scissor", scissor, 0.0f, 1.0f);
                DearUITRSInterface.scissor1P = scissor[0];

                float[] reload = new float[] {DearUITRSInterface.reloadProgression};
                ImGui.sliderFloat("reload", reload, 0.0f, 1.0f);
                DearUITRSInterface.reloadProgression = reload[0];

                float[] blur = new float[] {DearUITRSInterface.muzzleFlash1PersonBlurring};
                ImGui.sliderFloat("blurring", blur, 1.0f, 12.0f);
                DearUITRSInterface.muzzleFlash1PersonBlurring = blur[0];

                int[] operations = new int[] {DearUITRSInterface.muzzleflash1PersonFboPingPongOperations};
                ImGui.sliderInt("FBO-Operations", operations, 0, 128);
                DearUITRSInterface.muzzleflash1PersonFboPingPongOperations = operations[0];
                ImGui.treePop();
            }
            if (ImGui.treeNode("Adjust Gun Render(3P)")) {
                if (ImGui.collapsingHeader("Matrices")) {
                    ImGui.treePush();
                    ImGui.pushStyleColor(ImGuiCol.Text, 0xffffaaaa);
                    this.drawTRS(trsGun3d);
                    this.drawTRS(trsMflash3d);
                    ImGui.popStyleColor();
                    ImGui.treePop();
                }

                float[] scissor = new float[] {DearUITRSInterface.scissor3P};
                ImGui.sliderFloat("scissor", scissor, 0.0f, 1.0f);
                DearUITRSInterface.scissor3P = scissor[0];

                float[] blur = new float[] {DearUITRSInterface.muzzleFlash3PersonBlurring};
                ImGui.sliderFloat("blurring", blur, 1.0f, 12.0f);
                DearUITRSInterface.muzzleFlash3PersonBlurring = blur[0];

                int[] operations = new int[] {DearUITRSInterface.muzzleflash3PersonFboPingPongOperations};
                ImGui.sliderInt("FBO-Operations", operations, 0, 128);
                DearUITRSInterface.muzzleflash3PersonFboPingPongOperations = operations[0];
                ImGui.treePop();
            }

            if (ImGui.treeNode("FBO buffers")) {
                GL46.glScissor(0, 0, 1, 1);
                ImGui.text("1 Person");
                ImGui.image(ZPDefaultGunMuzzleflashFX.muzzleflashFBO.getTextureByIndex(0).getTextureId(), 300, 200, 0.0f, 1.0f, 1.0f, 0.0f);
                ImGui.image(ZPDefaultGunMuzzleflashFX.muzzleflashFBO.getTextureByIndex(1).getTextureId(), 300, 200, 0.0f, 1.0f, 1.0f, 0.0f);
                ImGui.image(ZPDefaultGunMuzzleflashFX.muzzleflashFBO.getTextureByIndex(2).getTextureId(), 300, 200, 0.0f, 1.0f, 1.0f, 0.0f);
                ImGui.separator();
                ImGui.text("Bloom");
                ImGui.image(ZPDefaultGunMuzzleflashFX.muzzleflashBlurFBO.getTextureByIndex(0).getTextureId(), 300, 200, 0.0f, 1.0f, 1.0f, 0.0f);
                ImGui.treePop();
            }
            ImGui.treePop();
        }
        if (ImGui.collapsingHeader("Debug Darkness")) {
            if (ImGui.checkbox("Enable", DearUITRSInterface.debugDarknessValueEnable)) {
                DearUITRSInterface.debugDarknessValueEnable = !DearUITRSInterface.debugDarknessValueEnable;
            }
            float[] v = new float[] {DearUITRSInterface.debugDarknessValue};
            ImGui.sliderFloat("Value", v, -10.0f, 1.0f);
            DearUITRSInterface.debugDarknessValue = v[0];
        }
        ImGui.end();
    }

    private void drawTRS(TRS trs) {
        if (ImGui.treeNode(trs.label)) {
            float[] trX = new float[] {trs.position.x};
            float[] trY = new float[] {trs.position.y};
            float[] trZ = new float[] {trs.position.z};

            float[] rotX = new float[] {trs.rotation.x};
            float[] rotY = new float[] {trs.rotation.y};
            float[] rotZ = new float[] {trs.rotation.z};

            float[] scaleX = new float[] {trs.scale.x};
            float[] scaleY = new float[] {trs.scale.y};
            float[] scaleZ = new float[] {trs.scale.z};

            ImGui.dragFloat("Translation X##" + trs.label, trX, 0.001f, -100f, 100f, "%.4f", 1);
            ImGui.dragFloat("Translation Y##" + trs.label, trY, 0.001f, -100f, 100f, "%.4f", 1);
            ImGui.dragFloat("Translation Z##" + trs.label, trZ, 0.001f, -100f, 100f, "%.4f", 1);

            ImGui.separator();

            ImGui.dragFloat("Rotation X##" + trs.label, rotX, 0.001f, -360f, 360f, "%.4f", 1);
            ImGui.dragFloat("Rotation Y##" + trs.label, rotY, 0.001f, -360f, 360f, "%.4f", 1);
            ImGui.dragFloat("Rotation Z##" + trs.label, rotZ, 0.001f, -360f, 360f, "%.4f", 1);

            ImGui.separator();

            ImGui.dragFloat("Scale X##" + trs.label, scaleX, 0.001f, 0.01f, 100f, "%.4f", 1);
            ImGui.dragFloat("Scale Y##" + trs.label, scaleY, 0.001f, 0.01f, 100f, "%.4f", 1);
            ImGui.dragFloat("Scale Z##" + trs.label, scaleZ, 0.001f, 0.01f, 100f, "%.4f", 1);

            trs.position.set(trX[0], trY[0], trZ[0]);
            trs.rotation.set(rotX[0], rotY[0], rotZ[0]);
            trs.scale.set(scaleX[0], scaleY[0], scaleZ[0]);

            ImGui.treePop();
        }
    }
}
