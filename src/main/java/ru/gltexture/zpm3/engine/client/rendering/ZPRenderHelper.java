package ru.gltexture.zpm3.engine.client.rendering;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.items.ZPItemRenderingProcessor;
import ru.gltexture.zpm3.engine.client.rendering.shaders.ZPDefaultShaders;
import ru.gltexture.zpm3.engine.client.rendering.ui.imgui.ZPDearUIRenderer;
import ru.gltexture.zpm3.engine.client.rendering.items.ZPItemRenderingMatcher;

import java.util.ArrayList;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ZPRenderHelper implements IZPRenderHelper {
    public static ZPRenderHelper INSTANCE = new ZPRenderHelper();
    private ZPDearUIRenderer dearUIRenderer;
    private final List<ZPRenderFunction> zpRenderFunctionList;
    private final ZPItemRenderingMatcher zpItemRenderingMatcher;
    private final List<Consumer<Void>> onClientTickCallbacks;

    private Map<Supplier<Item>, ZPItemRenderingProcessor> tempItemRenderingProcessors;

    private ZPRenderHelper() {
        this.zpRenderFunctionList = new ArrayList<>();
        this.zpItemRenderingMatcher = new ZPItemRenderingMatcher();
        this.onClientTickCallbacks = new ArrayList<>();

        this.tempItemRenderingProcessors = new HashMap<>();
    }

    private void defaultSetup() {
        //this.getDearUIRenderer().getInterfacesManager().addActiveInterface(new DearUIDemoInterface());
        this.addNewRenderFunction(((renderStage, partialTicks, pNanoTime, pRenderLevel) -> {
            if (renderStage == RenderStage.POST) {
                if (this.getDearUIRenderer() != null && !Minecraft.getInstance().isPaused()) {
                    this.getDearUIRenderer().getInterfacesManager().renderAll(Minecraft.getInstance().getWindow(), partialTicks);
                }
            }
        }));

        ZPCallbacksManager.INSTANCE.addWindowResizeCallback(((descriptor, width, height) -> {
            this.getItemRenderingMatcher().consumeForAllRenderProcessors(e -> e.gunRenderer().onWindowResizeAction(descriptor, width, height));
        }));

        this.addOnClientTickCallback(e -> this.getItemRenderingMatcher().consumeForAllRenderProcessors(f -> f.gunRenderer().onClientTicking()));
    }

    private void defaultDestroy() {
        this.getDearUIRenderer().getInterfacesManager().clear();
    }

    @Override
    public void setupResources(@NotNull final Window window) {
        this.tempItemRenderingProcessors.forEach((k, v) -> this.setItemRenderingProcessor(k.get(), v));
        this.tempItemRenderingProcessors = null;

        this.getItemRenderingMatcher().consumeForAllRenderProcessors(e -> e.gunRenderer().setupResources());
        this.dearUIRenderer = new ZPDearUIRenderer(ZPDefaultShaders.imgui::getShaderInstance);
        this.getDearUIRenderer().setupResources(window);
        this.defaultSetup();
    }

    @Override
    public void destroyResources(@NotNull final Window window) {
        this.getItemRenderingMatcher().consumeForAllRenderProcessors(e -> e.gunRenderer().destroyResources());
        this.getDearUIRenderer().destroyResources(window);
        this.defaultDestroy();
    }

    public void setItemRenderingProcessor(@NotNull Supplier<Item> item, @NotNull ZPItemRenderingProcessor renderingProcessor) {
        this.tempItemRenderingProcessors.put(item, renderingProcessor);
    }

    public void setItemRenderingProcessor(@NotNull Item item, @NotNull ZPItemRenderingProcessor renderingProcessor) {
        this.getItemRenderingMatcher().match(item, renderingProcessor);
    }

    public void addNewRenderFunction(@NotNull ZPRenderFunction zpRenderFunction) {
        this.getRenderFunctionList().add(zpRenderFunction);
    }

    public final void onClientTick() {
        this.onClientTickCallbacks.forEach(e -> e.accept(null));
    }

    public void addOnClientTickCallback(@NotNull Consumer<Void> callback) {
        this.onClientTickCallbacks.add(callback);
    }

    public ZPItemRenderingMatcher getItemRenderingMatcher() {
        return this.zpItemRenderingMatcher;
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