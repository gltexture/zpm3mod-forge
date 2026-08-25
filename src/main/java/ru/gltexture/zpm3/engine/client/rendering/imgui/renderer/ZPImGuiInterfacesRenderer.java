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

package ru.gltexture.zpm3.engine.client.rendering.imgui.renderer;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import imgui.*;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiMouseButton;
import imgui.type.ImInt;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.rendering.IZPClientManager;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.IZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.gl.textures.TextureSimple2DProgram;
import ru.gltexture.zpm3.engine.client.rendering.gl.textures.properties.TextureProperties;
import ru.gltexture.zpm3.engine.client.rendering.imgui.interfaces.IZPImGuiInterface;
import ru.gltexture.zpm3.engine.client.rendering.imgui.manager.IZPImGuiInterfacesManager;
import ru.gltexture.zpm3.engine.client.rendering.imgui.manager.ZPImGuiInterfacesManager;
import ru.gltexture.zpm3.engine.client.rendering.imgui.mesh.ZPImGuiMesh;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ZPImGuiInterfacesRenderer implements IZPClientManager.ResourceLifecycleListener {
    private ImGuiIO io;
    private final IZPImGuiInterfacesManager imGuiInterfacesManager;
    private ZPImGuiMesh imGuiMesh;
    private TextureSimple2DProgram textureSample;
    private int sampler;

    public ZPImGuiInterfacesRenderer(@NotNull IZPClientCallbacksManager clientCallbacksManager,  @NotNull IZPImGuiInterfacesManager imGuiInterfacesManager) {
        this.imGuiInterfacesManager = imGuiInterfacesManager;
        this.sampler = 0;
        this.createUICallbacks(clientCallbacksManager);
    }

    @Override
    public void onDestroyResources(@NotNull Window window) {
        GL46.glDeleteSamplers(this.sampler);
        this.imGuiMesh.clear();
        if (this.textureSample != null) {
            this.textureSample.clear();
        }
        ((ZPImGuiInterfacesManager) this.imGuiInterfacesManager).clear();
    }

    @Override
    public void onSetupResources(@NotNull Window window) {
        this.sampler = GL46.glGenSamplers();
        GL46.glSamplerParameteri(this.sampler, GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_LINEAR);
        GL46.glSamplerParameteri(this.sampler, GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_LINEAR);
        GL46.glSamplerParameteri(this.sampler, GL46.GL_TEXTURE_WRAP_S, GL46.GL_CLAMP_TO_EDGE);
        GL46.glSamplerParameteri(this.sampler, GL46.GL_TEXTURE_WRAP_T, GL46.GL_CLAMP_TO_EDGE);

        ImGui.createContext();
        this.io = ImGui.getIO();
        this.io.setIniFilename(null);
        this.io.setDisplaySize(window.getWidth(), window.getHeight());

        ImFontAtlas fontAtlas = this.io.getFonts();

        ImInt width = new ImInt();
        ImInt height = new ImInt();

        ByteBuffer buffer = fontAtlas.getTexDataAsRGBA32(width, height);
        this.textureSample = new TextureSimple2DProgram();
        this.textureSample.createTexture(new Vector2i(width.get(), height.get()), new TextureProperties(GL46.GL_RGBA, GL46.GL_RGBA, GL46.GL_NEAREST, GL46.GL_NEAREST, GL46.GL_NONE, GL46.GL_LESS, GL46.GL_CLAMP_TO_EDGE, GL46.GL_CLAMP_TO_EDGE, null), buffer);
        fontAtlas.setTexID(this.textureSample.getTextureId());

        this.imGuiMesh = new ZPImGuiMesh();
    }

    @Override
    public void onWindowResized(long descriptor, int width, int height) {
        this.io.setDisplaySize(width, height);
    }

    private boolean wantsMouseInput() {
        return ImGui.getIO().getWantCaptureMouse();
    }

    private boolean wantsKeyboardInput() {
        return ImGui.getIO().getWantCaptureKeyboard();
    }

    private void createUICallbacks(@NotNull IZPClientCallbacksManager clientCallbacksManager) {
        clientCallbacksManager.addMouseScrollCallback((descriptor, x, y) -> {
            final boolean flag = GLFW.glfwGetInputMode(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_CURSOR) != GLFW.GLFW_CURSOR_DISABLED;
            if (flag) {
                this.io.addMouseWheelEvent(x, y);
            }
        });

        clientCallbacksManager.addMouseButtonCallback((descriptor, button, action, mods) -> {
            this.io.addMouseButtonEvent(button, action != GLFW.GLFW_RELEASE);
        });

        clientCallbacksManager.addKeyboardCallback((descriptor, key, scanCode, action, mods) -> {
            final int imguiKey = ZPImGuiInterfacesRenderer.mapGlfwKey(key);
            if (imguiKey != ImGuiKey.None) {
                io.addKeyEvent(imguiKey, action != GLFW.GLFW_RELEASE);
            }
            this.io.addKeyEvent(ImGuiKey.ImGuiMod_Ctrl, (mods & GLFW.GLFW_MOD_CONTROL) != 0);
            this.io.addKeyEvent(ImGuiKey.ImGuiMod_Shift, (mods & GLFW.GLFW_MOD_SHIFT) != 0);
            this.io.addKeyEvent(ImGuiKey.ImGuiMod_Alt, (mods & GLFW.GLFW_MOD_ALT) != 0);
            this.io.addKeyEvent(ImGuiKey.ImGuiMod_Super, (mods & GLFW.GLFW_MOD_SUPER) != 0);
        });

        clientCallbacksManager.addCharCallback((descriptor, c) -> {
            this.io.addInputCharacter(c);
        });
    }

    public static int mapGlfwMouse(int key) {
        return switch (key) {
            case GLFW.GLFW_MOUSE_BUTTON_LEFT -> ImGuiMouseButton.Left;
            case GLFW.GLFW_MOUSE_BUTTON_RIGHT -> ImGuiMouseButton.Right;
            case GLFW.GLFW_MOUSE_BUTTON_MIDDLE -> ImGuiMouseButton.Middle;
            default -> ImGuiKey.None;
        };
    }

    public static int mapGlfwKey(int key) {
        return switch (key) {
            case GLFW.GLFW_KEY_C -> ImGuiKey.C;
            case GLFW.GLFW_KEY_X -> ImGuiKey.X;
            case GLFW.GLFW_KEY_A -> ImGuiKey.A;
            case GLFW.GLFW_KEY_V -> ImGuiKey.V;
            case GLFW.GLFW_KEY_Z -> ImGuiKey.Z;
            case GLFW.GLFW_KEY_Y -> ImGuiKey.Y;
            case GLFW.GLFW_KEY_TAB -> ImGuiKey.Tab;
            case GLFW.GLFW_KEY_LEFT -> ImGuiKey.LeftArrow;
            case GLFW.GLFW_KEY_RIGHT -> ImGuiKey.RightArrow;
            case GLFW.GLFW_KEY_UP -> ImGuiKey.UpArrow;
            case GLFW.GLFW_KEY_DOWN -> ImGuiKey.DownArrow;
            case GLFW.GLFW_KEY_PAGE_UP -> ImGuiKey.PageUp;
            case GLFW.GLFW_KEY_PAGE_DOWN -> ImGuiKey.PageDown;
            case GLFW.GLFW_KEY_HOME -> ImGuiKey.Home;
            case GLFW.GLFW_KEY_END -> ImGuiKey.End;
            case GLFW.GLFW_KEY_INSERT -> ImGuiKey.Insert;
            case GLFW.GLFW_KEY_DELETE -> ImGuiKey.Delete;
            case GLFW.GLFW_KEY_BACKSPACE -> ImGuiKey.Backspace;
            case GLFW.GLFW_KEY_SPACE -> ImGuiKey.Space;
            case GLFW.GLFW_KEY_ENTER -> ImGuiKey.Enter;
            case GLFW.GLFW_KEY_ESCAPE -> ImGuiKey.Escape;
            case GLFW.GLFW_KEY_KP_ENTER -> ImGuiKey.KeypadEnter;
            default -> ImGuiKey.None;
        };
    }

    public void onRender(@NotNull Window window, float frameTicking) {
        final @NotNull Set<IZPImGuiInterface> dearUIInterfaceSet = this.getImGuiInterfacesManager().getRenderableInterfaces();
        final ShaderInstance shader = this.getShaderManager().get();
        if (shader == null || Minecraft.getInstance().options.hideGui) {
            return;
        }

        final Minecraft mc = Minecraft.getInstance();
        final MouseHandler mouse = mc.mouseHandler;
        final KeyboardHandler keyboardHandler = mc.keyboardHandler;

        {
            final boolean flag = GLFW.glfwGetInputMode(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_CURSOR) != GLFW.GLFW_CURSOR_DISABLED;
            this.io.setWantCaptureKeyboard(flag);
            this.io.setWantCaptureMouse(flag);
            this.io.setWantTextInput(flag);
            this.io.setWantCaptureMouse(flag);
            this.io.setWantSetMousePos(flag);
            if (flag) {
            this.io.setMousePos((float) Minecraft.getInstance().mouseHandler.xpos(), (float) Minecraft.getInstance().mouseHandler.ypos());
            }
        }

        float delta = frameTicking;
        if (delta <= 0.0f) {
            delta = 1.0f / 60.0f;
        }
        this.io.setDeltaTime(delta);
        ImGui.newFrame();
        try {
            dearUIInterfaceSet.forEach(e -> e.drawGui(window, new IZPImGuiInterface.Input(mouse, keyboardHandler)));
        } catch (Exception e) {
            ImGui.pushID(e.toString());
            ImGui.setNextWindowSize(800, 1200);
            ImGui.begin("ERROR: " + e.toString().hashCode());
            ImGui.textWrapped(e.toString());
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            ImGui.beginChild("stacktrace", 0, 300, true);
            ImGui.textUnformatted(sw.toString());
            ImGui.endChild();
            ImGui.end();
            ImGui.popID();
        }
        ImGui.render();

        final ImDrawData drawData = ImGui.getDrawData();
        this.openGlPass(drawData, shader);
    }

    private void openGlPass(@NotNull ImDrawData drawData, @NotNull ShaderInstance shader) {
        final int previousVao = GL46.glGetInteger(GL46.GL_VERTEX_ARRAY_BINDING);
        final int previousArrayBuffer = GL46.glGetInteger(GL46.GL_ARRAY_BUFFER_BINDING);
        final int previousElementBuffer = GL46.glGetInteger(GL46.GL_ELEMENT_ARRAY_BUFFER_BINDING);
        final int previousActiveTexture = GL46.glGetInteger(GL46.GL_ACTIVE_TEXTURE);

        final boolean blendEnabled = GL46.glIsEnabled(GL46.GL_BLEND);
        final boolean depthEnabled = GL46.glIsEnabled(GL46.GL_DEPTH_TEST);
        final boolean cullEnabled = GL46.glIsEnabled(GL46.GL_CULL_FACE);
        final boolean scissorEnabled = GL46.glIsEnabled(GL46.GL_SCISSOR_TEST);

        final int blendSrcRgb = GL46.glGetInteger(GL46.GL_BLEND_SRC_RGB);
        final int blendDstRgb = GL46.glGetInteger(GL46.GL_BLEND_DST_RGB);
        final int blendSrcAlpha = GL46.glGetInteger(GL46.GL_BLEND_SRC_ALPHA);
        final int blendDstAlpha = GL46.glGetInteger(GL46.GL_BLEND_DST_ALPHA);

        final int blendEquationRgb = GL46.glGetInteger(GL46.GL_BLEND_EQUATION_RGB);
        final int blendEquationAlpha = GL46.glGetInteger(GL46.GL_BLEND_EQUATION_ALPHA);

        final int[] previousScissorBox = new int[4];
        GL46.glGetIntegerv(GL46.GL_SCISSOR_BOX, previousScissorBox);

        final int previousTexture = GL46.glGetInteger(GL46.GL_TEXTURE_BINDING_2D);
        final int previousSampler = GL46.glGetInteger(GL46.GL_SAMPLER_BINDING);

        try {
            final ShaderInstance oldShader = RenderSystem.getShader();
            shader.apply();
            ImVec2 dSize = new ImVec2();
            this.io.getDisplaySize(dSize);
            Uniform scaleUniform = shader.getUniform("scale");
            if (scaleUniform != null) {
                scaleUniform.set(2.0f / dSize.x, -2.0f / dSize.y);
            }
            Uniform textureUniform = shader.getUniform("texture_map");
            if (textureUniform != null) {
                textureUniform.set(0);
            }
            GL46.glEnable(GL46.GL_BLEND);
            GL46.glBlendEquation(GL46.GL_FUNC_ADD);
            GL46.glBlendFuncSeparate(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA, GL46.GL_ONE, GL46.GL_ONE_MINUS_SRC_ALPHA);
            GL46.glDisable(GL46.GL_DEPTH_TEST);
            GL46.glDisable(GL46.GL_CULL_FACE);
            GL46.glBindVertexArray(this.imGuiMesh.getVaoId());
            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, this.imGuiMesh.getVerticesVbo());
            GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, this.imGuiMesh.getIndicesVbo());
            int numLists = drawData.getCmdListsCount();
            ImVec2 dPos = new ImVec2();
            ImVec2 fbScale = new ImVec2();
            drawData.getDisplayPos(dPos);
            drawData.getFramebufferScale(fbScale);
            final float clipOffX = dPos.x;
            final float clipOffY = dPos.y;
            final float clipScaleX = fbScale.x;
            final float clipScaleY = fbScale.y;

            for (int i = 0; i < numLists; i++) {
                GL46.glBufferData(GL46.GL_ARRAY_BUFFER, drawData.getCmdListVtxBufferData(i), GL46.GL_STREAM_DRAW);
                GL46.glBufferData(GL46.GL_ELEMENT_ARRAY_BUFFER, drawData.getCmdListIdxBufferData(i), GL46.GL_STREAM_DRAW);
                for (int j = 0; j < drawData.getCmdListCmdBufferSize(i); j++) {
                    final int elemCount = drawData.getCmdListCmdBufferElemCount(i, j);
                    final int idxBufferOffset = drawData.getCmdListCmdBufferIdxOffset(i, j);
                    final int indices = idxBufferOffset * ImDrawData.sizeOfImDrawIdx();
                    int textureId = (int) drawData.getCmdListCmdBufferTextureId(i, j);
                    GL46.glActiveTexture(GL46.GL_TEXTURE0);
                    if (textureId > 0) {
                        GL46.glBindSampler(0, this.sampler);
                        GL46.glBindTexture(GL46.GL_TEXTURE_2D, textureId);
                    } else {
                        this.textureSample.bindTexture();
                    }
                    ImVec4 clipRect = drawData.getCmdListCmdBufferClipRect(i, j);
                    final float clipMinX = (clipRect.x - clipOffX) * clipScaleX;
                    final float clipMinY = (clipRect.y - clipOffY) * clipScaleY;
                    final float clipMaxX = (clipRect.z - clipOffX) * clipScaleX;
                    final float clipMaxY = (clipRect.w - clipOffY) * clipScaleY;
                    final int fbHeight = (int) (dSize.y * fbScale.y);
                    if (clipMaxX <= clipMinX || clipMaxY <= clipMinY) {
                        continue;
                    }
                    GL46.glEnable(GL46.GL_SCISSOR_TEST);
                    GL46.glScissor((int) clipMinX, (int) (fbHeight - clipMaxY), (int) (clipMaxX - clipMinX), (int) (clipMaxY - clipMinY));
                    GL46.glDrawElements(GL46.GL_TRIANGLES, elemCount, GL46.GL_UNSIGNED_SHORT, indices);
                    GL46.glDisable(GL46.GL_SCISSOR_TEST);
                }
            }
            Objects.requireNonNull(oldShader).apply();
        } finally {
            GL46.glBindVertexArray(previousVao);
            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, previousArrayBuffer);
            GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, previousElementBuffer);

            GL46.glActiveTexture(previousActiveTexture);
            GL46.glBindTexture(GL46.GL_TEXTURE_2D, previousTexture);
            GL46.glBindSampler(0, previousSampler);

            if (blendEnabled) {
                GL46.glEnable(GL46.GL_BLEND);
            } else {
                GL46.glDisable(GL46.GL_BLEND);
            }

            GL46.glBlendFuncSeparate(blendSrcRgb, blendDstRgb, blendSrcAlpha, blendDstAlpha);
            GL46.glBlendEquationSeparate(blendEquationRgb, blendEquationAlpha);

            if (depthEnabled) {
                GL46.glEnable(GL46.GL_DEPTH_TEST);
            } else {
                GL46.glDisable(GL46.GL_DEPTH_TEST);
            }

            if (cullEnabled) {
                GL46.glEnable(GL46.GL_CULL_FACE);
            } else {
                GL46.glDisable(GL46.GL_CULL_FACE);
            }

            if (scissorEnabled) {
                GL46.glEnable(GL46.GL_SCISSOR_TEST);
            } else {
                GL46.glDisable(GL46.GL_SCISSOR_TEST);
            }

            GL46.glScissor(previousScissorBox[0], previousScissorBox[1], previousScissorBox[2], previousScissorBox[3]);
        }
    }

    public Supplier<ShaderInstance> getShaderManager() {
        return ZPDefaultShaders.imgui::getShaderInstance;
    }

    public IZPImGuiInterfacesManager getImGuiInterfacesManager() {
        return this.imGuiInterfacesManager;
    }
}
