package ru.gltexture.zpm3.engine.client.rendering.ui.imgui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.shaders.Uniform;
import imgui.*;
import imgui.flag.ImGuiKey;
import imgui.type.ImInt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.engine.client.callbacking.ZPCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.textures.TextureSimple2DProgram;
import ru.gltexture.zpm3.engine.client.rendering.gl.programs.textures.properties.TextureProperties;
import ru.gltexture.zpm3.engine.client.rendering.resources.IZPResourceInit;
import ru.gltexture.zpm3.engine.client.rendering.ui.imgui.interfaces.DearUIInterface;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ZPDearUIRenderer implements IZPResourceInit {
    private ZPDearUIInterfacesManager zpDearUIInterfacesManager;
    private final Supplier<ShaderInstance> shaderManager;
    private DearUIMesh dearImGuiMesh;
    private TextureSimple2DProgram textureSample;
    private int sampler;

    public ZPDearUIRenderer(@NotNull Supplier<ShaderInstance> imguiShader) {
        this.shaderManager = imguiShader;
        this.sampler = 0;
    }

    //-javaagent:"C:\Users\forge\.gradle\caches\modules-2\files-2.1\org.spongepowered\mixin\0.8.5\9d1c0c3a304ae6697ecd477218fa61b850bf57fc\mixin-0.8.5.jar"
    public void setupResources(@NotNull Window window) {
        this.sampler = GL46.glGenSamplers();
        GL46.glSamplerParameteri(this.sampler, GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_LINEAR);
        GL46.glSamplerParameteri(this.sampler, GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_LINEAR);
        GL46.glSamplerParameteri(this.sampler, GL46.GL_TEXTURE_WRAP_S, GL46.GL_CLAMP_TO_EDGE);
        GL46.glSamplerParameteri(this.sampler, GL46.GL_TEXTURE_WRAP_T, GL46.GL_CLAMP_TO_EDGE);

        ImGui.createContext();
        ImGuiIO imGuiIO = ImGui.getIO();
        imGuiIO.setIniFilename(null);
        imGuiIO.setDisplaySize(window.getWidth(), window.getHeight());

        ImFontAtlas fontAtlas = imGuiIO.getFonts();

        ImInt width = new ImInt();
        ImInt height = new ImInt();

        ByteBuffer buffer = fontAtlas.getTexDataAsRGBA32(width, height);
        this.textureSample = new TextureSimple2DProgram();
        this.textureSample.createTexture(new Vector2i(width.get(), height.get()), new TextureProperties(GL46.GL_RGBA, GL46.GL_RGBA, GL46.GL_NEAREST, GL46.GL_NEAREST, GL46.GL_NONE, GL46.GL_LESS, GL46.GL_CLAMP_TO_EDGE, GL46.GL_CLAMP_TO_EDGE, null), buffer);

        this.dearImGuiMesh = new DearUIMesh();
        this.createUICallbacks(window);
        this.zpDearUIInterfacesManager = new ZPDearUIInterfacesManager(this);
    }

    public void destroyResources(@NotNull Window window) {
        GL46.glDeleteSamplers(this.sampler);
        this.getImguiMesh().clear();
        if (this.textureSample != null) {
            this.textureSample.clear();
        }
    }

    private void createUICallbacks(Window window) {
        ImGuiIO io = ImGui.getIO();

        ZPCallbacksManager.INSTANCE.addWindowResizeCallback((l, w, h) -> {
            io.setDisplaySize(w, h);
        });

        io.setKeyMap(ImGuiKey.C, GLFW.GLFW_KEY_C);
        io.setKeyMap(ImGuiKey.X, GLFW.GLFW_KEY_X);
        io.setKeyMap(ImGuiKey.A, GLFW.GLFW_KEY_A);
        io.setKeyMap(ImGuiKey.V, GLFW.GLFW_KEY_V);
        io.setKeyMap(ImGuiKey.Z, GLFW.GLFW_KEY_Z);
        io.setKeyMap(ImGuiKey.Y, GLFW.GLFW_KEY_Y);

        io.setKeyMap(ImGuiKey.Tab, GLFW.GLFW_KEY_TAB);
        io.setKeyMap(ImGuiKey.LeftArrow, GLFW.GLFW_KEY_LEFT);
        io.setKeyMap(ImGuiKey.RightArrow, GLFW.GLFW_KEY_RIGHT);
        io.setKeyMap(ImGuiKey.UpArrow, GLFW.GLFW_KEY_UP);
        io.setKeyMap(ImGuiKey.DownArrow, GLFW.GLFW_KEY_DOWN);
        io.setKeyMap(ImGuiKey.PageUp, GLFW.GLFW_KEY_PAGE_UP);
        io.setKeyMap(ImGuiKey.PageDown, GLFW.GLFW_KEY_PAGE_DOWN);
        io.setKeyMap(ImGuiKey.Home, GLFW.GLFW_KEY_HOME);
        io.setKeyMap(ImGuiKey.End, GLFW.GLFW_KEY_END);
        io.setKeyMap(ImGuiKey.Insert, GLFW.GLFW_KEY_INSERT);
        io.setKeyMap(ImGuiKey.Delete, GLFW.GLFW_KEY_DELETE);
        io.setKeyMap(ImGuiKey.Backspace, GLFW.GLFW_KEY_BACKSPACE);
        io.setKeyMap(ImGuiKey.Space, GLFW.GLFW_KEY_SPACE);
        io.setKeyMap(ImGuiKey.Enter, GLFW.GLFW_KEY_ENTER);
        io.setKeyMap(ImGuiKey.Escape, GLFW.GLFW_KEY_ESCAPE);
        io.setKeyMap(ImGuiKey.KeyPadEnter, GLFW.GLFW_KEY_KP_ENTER);

        ZPCallbacksManager.INSTANCE.addKeyboardHoldCallback((descriptor, key, scanCode, mods) -> {
            if (!io.getWantCaptureKeyboard()) {
                return;
            }
            io.setKeysDown(key, true);
            io.setKeyCtrl(io.getKeysDown(GLFW.GLFW_KEY_LEFT_CONTROL));
            io.setKeyShift(io.getKeysDown(GLFW.GLFW_KEY_LEFT_SHIFT));
            io.setKeyAlt(io.getKeysDown(GLFW.GLFW_KEY_LEFT_ALT));
            io.setKeySuper(io.getKeysDown(GLFW.GLFW_KEY_LEFT_SUPER));
        });

        ZPCallbacksManager.INSTANCE.addKeyboardReleaseCallback((descriptor, key, scanCode, mods) -> {
            if (!io.getWantCaptureKeyboard()) {
                return;
            }
            io.setKeysDown(key, false);
            io.setKeyCtrl(io.getKeysDown(GLFW.GLFW_KEY_LEFT_CONTROL));
            io.setKeyShift(io.getKeysDown(GLFW.GLFW_KEY_LEFT_SHIFT));
            io.setKeyAlt(io.getKeysDown(GLFW.GLFW_KEY_LEFT_ALT));
            io.setKeySuper(io.getKeysDown(GLFW.GLFW_KEY_LEFT_SUPER));
        });

        ZPCallbacksManager.INSTANCE.addCharCallback((descriptor, c) -> {
            if (!io.getWantCaptureKeyboard()) {
                return;
            }
            io.addInputCharacter(c);
        });
    }

    public void onRender(@NotNull Window window, @NotNull DearUIInterface dearUIInterface, float frameTicking) {
        final ShaderInstance shader = this.getShaderManager().get();
        if (shader == null || Minecraft.getInstance().options.hideGui) {
            return;
        }

        final Minecraft mc = Minecraft.getInstance();
        final MouseHandler mouse = mc.mouseHandler;

        ImGui.newFrame();
        dearUIInterface.drawGui(window, mouse);
        ImGui.endFrame();
        ImGui.render();

        ImDrawData drawData = ImGui.getDrawData();

        ImGuiIO io = ImGui.getIO();
        float delta = frameTicking;
        if (delta == 0.0f) {
            delta = 1.0f;
        }
        io.setDeltaTime(delta);

        ImVec2 dSize = new ImVec2();
        io.getDisplaySize(dSize);

        shader.apply();
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

        GL46.glBindVertexArray(this.getImguiMesh().getVaoId());
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, this.getImguiMesh().getVerticesVbo());
        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, this.getImguiMesh().getIndicesVbo());

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
                final int indices = idxBufferOffset * ImDrawData.SIZEOF_IM_DRAW_IDX;

                int textureId = drawData.getCmdListCmdBufferTextureId(i, j);
                GL46.glActiveTexture(GL46.GL_TEXTURE0);

                if (textureId > 0) {
                    GL46.glBindSampler(0, this.sampler);
                    GL46.glBindTexture(GL46.GL_TEXTURE_2D, textureId);
                } else {
                    this.getTextureSample().bindTexture();
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

        GL46.glEnable(GL46.GL_DEPTH_TEST);
        GL46.glEnable(GL46.GL_CULL_FACE);
        GL46.glEnable(GL46.GL_BLEND);

        io.setMousePos((float) mouse.xpos(), (float) mouse.ypos());
        long win = window.getWindow();
        io.setMouseDown(0, GLFW.glfwGetMouseButton(win, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS);
        io.setMouseDown(1, GLFW.glfwGetMouseButton(win, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS);

        ZPCallbacksManager.INSTANCE.addMouseScrollCallback((descriptor, x, y) -> {
            io.setMouseWheel((float) y);
        });
    }

    public ZPDearUIInterfacesManager getInterfacesManager() {
        return this.zpDearUIInterfacesManager;
    }

    public TextureSimple2DProgram getTextureSample() {
        return this.textureSample;
    }

    public Supplier<ShaderInstance> getShaderManager() {
        return this.shaderManager;
    }

    public DearUIMesh getImguiMesh() {
        return this.dearImGuiMesh;
    }
}
