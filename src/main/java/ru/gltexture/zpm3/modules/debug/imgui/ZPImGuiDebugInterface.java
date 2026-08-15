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

package ru.gltexture.zpm3.modules.debug.imgui;

import com.mojang.blaze3d.platform.Window;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiCond;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.modules.guns.rendering.fx.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.engine.client.rendering.imgui.interfaces.IZPImGuiInterface;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.IZPNetEntDataSyncer;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;
import ru.gltexture.zpm3.modules.net_pack.data.data_static.ZPNetStaticDataPack;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;

import java.util.*;

public class ZPImGuiDebugInterface implements IZPImGuiInterface {
    public ZPImGuiDebugInterface() {
    }

    public static boolean FORCE_ENABLE_SAMPLE_POST_FX_SHADER = false;
    public static boolean FORCE_ENABLE_NIGHTVIS_POST_FX_SHADER = false;
    public static boolean FORCE_ENABLE_RADIATION_POST_FX_SHADER = false;
    public static boolean FORCE_ENABLE_ACID_POST_FX_SHADER = false;
    public static boolean FORCE_ENABLE_ADRENALINE_POST_FX_SHADER = false;
    public static boolean FORCE_ENABLE_BETTERVIS_POST_FX_SHADER = false;
    public static boolean FORCE_ENABLE_INFECTION_POST_FX_SHADER = false;
    public static boolean FORCE_ENABLE_MASK_POST_FX_SHADER = false;
    public static float[] PARAM_RAD_POSTFX = new float[] { 0.0f };
    public static float[] PARAM_INF_POSTFX = new float[] { 0.0f };

    public static float[] PARAM1 = new float[] { 0.0f };
    public static float[] PARAM2 = new float[] { 0.0f };
    public static float[] PARAM3 = new float[] { 0.0f };
    public static float[] PARAM4 = new float[] { 0.0f };
    public static float[] PARAM5 = new float[] { 0.0f };

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
   // public static float muzzleFlash1PersonBlurring = ZPDefaultGunMuzzleflashFX.DEFAULT_BLURRING_1P;
    //public static int muzzleflash3PersonFboPingPongOperations = ZPDefaultGunMuzzleflashFX.DEFAULT_PINGPONG_FBO_OPERATIONS_3P;
    //public static float muzzleFlash3PersonBlurring = ZPDefaultGunMuzzleflashFX.DEFAULT_BLURRING_3P;

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

    public void drawGui(@NotNull Window window, @NotNull Input input) {
       // if (!ZombiePlague3.isDevEnvironment()) {
       //     return;
       // }

        ImGui.setNextWindowPos(0, 0, ImGuiCond.Once);
        ImGui.setNextWindowSize(400, 600, ImGuiCond.Once);
        ImGui.begin("debug");

        @Nullable Player player = Minecraft.getInstance().player;
        if (player instanceof IZPPlayerMixinExt ext) {
            ImGui.text("Ping: " + ext.zpm3forge$getPing());
        }
        ImGui.separator();

        if (ImGui.collapsingHeader("PostFX")) {
            if (ImGui.checkbox("Sample", ZPImGuiDebugInterface.FORCE_ENABLE_SAMPLE_POST_FX_SHADER)) {
                ZPImGuiDebugInterface.FORCE_ENABLE_SAMPLE_POST_FX_SHADER = !ZPImGuiDebugInterface.FORCE_ENABLE_SAMPLE_POST_FX_SHADER;
            }
            if (ImGui.checkbox("NightVis", ZPImGuiDebugInterface.FORCE_ENABLE_NIGHTVIS_POST_FX_SHADER)) {
                ZPImGuiDebugInterface.FORCE_ENABLE_NIGHTVIS_POST_FX_SHADER = !ZPImGuiDebugInterface.FORCE_ENABLE_NIGHTVIS_POST_FX_SHADER;
            }
            if (ImGui.checkbox("Radiation", ZPImGuiDebugInterface.FORCE_ENABLE_RADIATION_POST_FX_SHADER)) {
                ZPImGuiDebugInterface.FORCE_ENABLE_RADIATION_POST_FX_SHADER = !ZPImGuiDebugInterface.FORCE_ENABLE_RADIATION_POST_FX_SHADER;
            }
            if (ImGui.checkbox("Mask", ZPImGuiDebugInterface.FORCE_ENABLE_MASK_POST_FX_SHADER)) {
                ZPImGuiDebugInterface.FORCE_ENABLE_MASK_POST_FX_SHADER = !ZPImGuiDebugInterface.FORCE_ENABLE_MASK_POST_FX_SHADER;
            }
            if (ImGui.checkbox("Infection", ZPImGuiDebugInterface.FORCE_ENABLE_INFECTION_POST_FX_SHADER)) {
                ZPImGuiDebugInterface.FORCE_ENABLE_INFECTION_POST_FX_SHADER = !ZPImGuiDebugInterface.FORCE_ENABLE_INFECTION_POST_FX_SHADER;
            }
            if (ImGui.checkbox("Adrenaline", ZPImGuiDebugInterface.FORCE_ENABLE_ADRENALINE_POST_FX_SHADER)) {
                ZPImGuiDebugInterface.FORCE_ENABLE_ADRENALINE_POST_FX_SHADER = !ZPImGuiDebugInterface.FORCE_ENABLE_ADRENALINE_POST_FX_SHADER;
            }
            if (ImGui.checkbox("Acid", ZPImGuiDebugInterface.FORCE_ENABLE_ACID_POST_FX_SHADER)) {
                ZPImGuiDebugInterface.FORCE_ENABLE_ACID_POST_FX_SHADER = !ZPImGuiDebugInterface.FORCE_ENABLE_ACID_POST_FX_SHADER;
            }
            if (ImGui.checkbox("BetterVision", ZPImGuiDebugInterface.FORCE_ENABLE_BETTERVIS_POST_FX_SHADER)) {
                ZPImGuiDebugInterface.FORCE_ENABLE_BETTERVIS_POST_FX_SHADER = !ZPImGuiDebugInterface.FORCE_ENABLE_BETTERVIS_POST_FX_SHADER;
            }
            ImGui.dragFloat("RadParam", ZPImGuiDebugInterface.PARAM_RAD_POSTFX, 0.01f, 0.0f, 100.0f);
            ImGui.dragFloat("InfParam", ZPImGuiDebugInterface.PARAM_INF_POSTFX, 0.01f, 0.0f, 100.0f);
            //if (ZPPostFXChain.screenFBO != null) {
           //    ImGui.image(ZPPostFXChain.screenFBO.getTextureByIndex(0).getTextureId(), 400, 200, 0.0f, 1.0f, 1.0f, 0.0f);
           //}
        }
        if (ImGui.collapsingHeader("Gun-Rendering")) {
            ImGui.treePush("##GunRendering");

            if (ImGui.checkbox("manual mflash", ZPImGuiDebugInterface.muzzleflashHandling)) {
                ZPImGuiDebugInterface.muzzleflashHandling = !ZPImGuiDebugInterface.muzzleflashHandling;
            }
            if (ImGui.checkbox("emmit smoke", ZPImGuiDebugInterface.emmitSmoke)) {
                ZPImGuiDebugInterface.emmitSmoke = !ZPImGuiDebugInterface.emmitSmoke;
            }
            if (ImGui.checkbox("emmit shells", ZPImGuiDebugInterface.emmitShells)) {
                ZPImGuiDebugInterface.emmitShells = !ZPImGuiDebugInterface.emmitShells;
            }
            int[] mode = new int[] {ZPImGuiDebugInterface.muzzleflashRenderingMode};
            ImGui.sliderInt("Quality", mode, 0, 3);
            ZPImGuiDebugInterface.muzzleflashRenderingMode = mode[0];

            if (ImGui.treeNode("Adjust Gun Render(1P)")) {
                if (ImGui.collapsingHeader("Matrices")) {
                    ImGui.treePush("##Matrices");
                    ImGui.pushStyleColor(ImGuiCol.Text, 0xffff00ff);
                    this.drawTRS(trsGun);
                    this.drawTRS(trsArm);
                    this.drawTRS(trsMFlash);
                    this.drawTRS(trsReloadingGun);
                    this.drawTRS(trsReloadingArm);
                    ImGui.popStyleColor();
                    ImGui.treePop();
                }
                float[] scissor = new float[] {ZPImGuiDebugInterface.scissor1P};
                ImGui.sliderFloat("scissor", scissor, 0.0f, 1.0f);
                ZPImGuiDebugInterface.scissor1P = scissor[0];

                float[] reload = new float[] {ZPImGuiDebugInterface.reloadProgression};
                ImGui.sliderFloat("reload", reload, 0.0f, 1.0f);
                ZPImGuiDebugInterface.reloadProgression = reload[0];

            //    float[] blur = new float[] {ZPImGuiDebugInterface.muzzleFlash1PersonBlurring};
            //    ImGui.sliderFloat("blurring", blur, 1.0f, 12.0f);
            //    ZPImGuiDebugInterface.muzzleFlash1PersonBlurring = blur[0];

                int[] operations = new int[] {ZPImGuiDebugInterface.muzzleflash1PersonFboPingPongOperations};
                ImGui.sliderInt("FBO-Operations", operations, 0, 128);
                ZPImGuiDebugInterface.muzzleflash1PersonFboPingPongOperations = operations[0];
                ImGui.treePop();
            }
            if (ImGui.treeNode("Adjust Gun Render(3P)")) {
                if (ImGui.collapsingHeader("Matrices")) {
                    ImGui.treePush("##Matrices2");
                    ImGui.pushStyleColor(ImGuiCol.Text, 0xffffaaaa);
                    this.drawTRS(trsGun3d);
                    this.drawTRS(trsMflash3d);
                    ImGui.popStyleColor();
                    ImGui.treePop();
                }

                float[] scissor = new float[] {ZPImGuiDebugInterface.scissor3P};
                ImGui.sliderFloat("scissor", scissor, 0.0f, 1.0f);
                ZPImGuiDebugInterface.scissor3P = scissor[0];

              //  float[] blur = new float[] {ZPImGuiDebugInterface.muzzleFlash3PersonBlurring};
              //  ImGui.sliderFloat("blurring", blur, 1.0f, 12.0f);
              //  ZPImGuiDebugInterface.muzzleFlash3PersonBlurring = blur[0];

               // int[] operations = new int[] {ZPImGuiDebugInterface.muzzleflash3PersonFboPingPongOperations};
               // ImGui.sliderInt("FBO-Operations", operations, 0, 128);
               // ZPImGuiDebugInterface.muzzleflash3PersonFboPingPongOperations = operations[0];
                ImGui.treePop();
            }

            if (ImGui.treeNode("FBO buffers")) {
                if (ZPDefaultGunMuzzleflashFX.muzzleflashFBO != null) {
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
            }
            ImGui.treePop();
        }
        if (ImGui.collapsingHeader("Debug Darkness")) {
            if (ImGui.checkbox("Enable", ZPImGuiDebugInterface.debugDarknessValueEnable)) {
                ZPImGuiDebugInterface.debugDarknessValueEnable = !ZPImGuiDebugInterface.debugDarknessValueEnable;
            }
            float[] v = new float[] {ZPImGuiDebugInterface.debugDarknessValue};
            ImGui.sliderFloat("Value", v, -10.0f, 1.0f);
            ZPImGuiDebugInterface.debugDarknessValue = v[0];
        }
        if (ImGui.collapsingHeader("Debug Client Data")) {
            if (player != null) {
                final IZPNetEntDataSyncer.ZPNetEntityData vars = ZombiePlague3.netClient().getNetEntDataSyncer().getEntityDataVars(player);
                if (vars != null) {
                    ImGui.text("ZPNetSynced (struct) Size: " + ZombiePlague3.netClient().getNetEntDataSyncer().structSize());
                    new ArrayList<>(vars.vars().int2ObjectEntrySet()).forEach((k) -> {
                        ImGui.bullet();
                        ImGui.textWrapped(k.getIntKey() + " : " + ZombiePlague3.netClient().getNetEntDataSyncer().getAccessorUnsafe(k.getIntKey()).getResourceId() + " - " + k.getValue().getValue());
                    });
                }
            }
            ImGui.separator();

            final ZPNetStaticDataPack pack = ZombiePlague3.netClient().getNetStaticDataSyncer().getPackServerData();
            final Map<ZPNetDataAccessor<?>, ZPNetDataVar<?>> data = pack.getVars();
            if (data.isEmpty()) {
                ImGui.text("Empty");
            } else {
                data.forEach((key, value) -> {
                    ImGui.bullet();
                    ImGui.text(key.getGlobalId() + " : " + key.getResourceId() + " = " + value.getValue());
                });
            }
            ImGui.separator();
            if (player != null && player.getEntityData().getNonDefaultValues() != null) {
                player.getEntityData().getNonDefaultValues().forEach(e -> {
                    ImGui.bullet();
                    ImGui.textWrapped(e.id() + " / " + e + " " + e.value());
                });
            }
        }
        if (ImGui.collapsingHeader("Debug Params")) {
            ImGui.dragFloat("PARAM1", ZPImGuiDebugInterface.PARAM1);
            ImGui.dragFloat("PARAM2", ZPImGuiDebugInterface.PARAM2);
            ImGui.dragFloat("PARAM3", ZPImGuiDebugInterface.PARAM3);
            ImGui.dragFloat("PARAM4", ZPImGuiDebugInterface.PARAM4);
            ImGui.dragFloat("PARAM5", ZPImGuiDebugInterface.PARAM5);
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
