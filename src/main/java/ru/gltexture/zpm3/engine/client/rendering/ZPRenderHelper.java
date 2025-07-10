package ru.gltexture.zpm3.engine.client.rendering;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.client.rendering.ui.imgui.ZPDearUIRenderer;
import ru.gltexture.zpm3.engine.client.rendering.ui.imgui.interfaces.DearUIMenuInterface;

import java.util.ArrayList;
import java.util.List;

public class ZPRenderHelper implements IZPRenderHelper {
    public static ZPRenderHelper INSTANCE = new ZPRenderHelper();
    private ZPDearUIRenderer dearUIRenderer;
    private final List<ZPRenderFunction> zpRenderFunctionList;

    private ZPRenderHelper() {
        this.zpRenderFunctionList = new ArrayList<>();
    }

    private void defaultSetup() {
        this.getDearUIRenderer().getInterfacesManager().addActiveInterface(new DearUIMenuInterface());
        this.addNewRenderFunction(((renderStage, partialTicks, pNanoTime, pRenderLevel) -> {
            if (renderStage == RenderStage.POST) {
                if (this.getDearUIRenderer() != null && !Minecraft.getInstance().isPaused()) {
                    this.getDearUIRenderer().getInterfacesManager().renderAll(Minecraft.getInstance().getWindow(), partialTicks);
                }
            }
        }));
    }

    private void defaultDestroy() {
        this.getDearUIRenderer().getInterfacesManager().clear();
    }

    @Override
    public void setupResources(@NotNull final Window window) {
        this.dearUIRenderer = new ZPDearUIRenderer(ZPDefaultShaders.imgui::getShaderInstance);
        this.getDearUIRenderer().setupResources(window);
        this.defaultSetup();
    }

    @Override
    public void destroyResources(@NotNull final Window window) {
        this.getDearUIRenderer().destroyResources(window);
        this.defaultDestroy();
    }

    public void addNewRenderFunction(@NotNull ZPRenderFunction zpRenderFunction) {
        this.getRenderFunctionList().add(zpRenderFunction);
    }

    public List<ZPRenderFunction> getRenderFunctionList() {
        return this.zpRenderFunctionList;
    }

    public ZPDearUIRenderer getDearUIRenderer() {
        return this.dearUIRenderer;
    }

    @FunctionalInterface
    public interface ZPRenderFunction {
        void onRender(@NotNull RenderStage renderStage, float partialTicks, long pNanoTime, boolean pRenderLevel);
    }

    public enum RenderStage {
        PRE,
        POST
    }
}