package ru.gltexture.zpm3.engine.client.rendering.hooks;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.world.item.Item;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class ZPRenderHooksManager implements IZPRenderHooksManager {
    public static final ZPRenderHooksManager INSTANCE = new ZPRenderHooksManager();

    private Map<Supplier<Item>, ZPRenderHooks.@NonNull ZPItemRenderingHook> itemRenderingHooksTemp;

    private final Map<Item, ZPRenderHooks.@NonNull ZPItemRenderingHook> itemRenderingHooks;
    private final List<ZPRenderHooks.@NonNull ZPSceneRenderingHook> sceneRenderingHooks;

    private final List<ZPRenderHooks.@NonNull ZPItemSceneRenderingHookPre> itemSceneRenderingHooksPre;
    private final List<ZPRenderHooks.@NonNull ZPItemSceneRenderingHookPost> itemSceneRenderingHooksPost;

    private ZPRenderHooksManager() {
        this.sceneRenderingHooks = new ArrayList<>();
        this.itemRenderingHooksTemp = new HashMap<>();
        this.itemRenderingHooks = new HashMap<>();
        this.itemSceneRenderingHooksPre = new ArrayList<>();
        this.itemSceneRenderingHooksPost = new ArrayList<>();
    }

    private void onPostZPInit() {
        this.itemRenderingHooksTemp.forEach((k, v) -> itemRenderingHooks.put(k.get(), v));
        this.itemRenderingHooksTemp = null;
    }

    @Override
    public void setupResources(@NotNull Window window) {
        this.onPostZPInit();
    }

    @Override
    public void destroyResources(@NotNull Window window) {
        this.sceneRenderingHooks.clear();
        this.itemRenderingHooksTemp.clear();
        this.itemRenderingHooks.clear();
        this.itemSceneRenderingHooksPre.clear();
        this.itemSceneRenderingHooksPost.clear();
    }

    @Override
    public void onWindowResizeAction(long descriptor, int width, int height) {

    }

    @Override
    public void addSceneRenderingHook(@NotNull ZPRenderHooks.ZPSceneRenderingHook zpSceneRenderingHook) {
        ZombiePlague3.clientInitValidation();
        this.getSceneRenderingHooks().add(zpSceneRenderingHook);
    }

    @Override
    public void addItemRenderingHook(@NotNull Supplier<Item> itemSupplier, @NotNull ZPRenderHooks.ZPItemRenderingHook zpItemRenderingHook) {
        ZombiePlague3.clientInitValidation();
        this.itemRenderingHooksTemp.put(itemSupplier, zpItemRenderingHook);
    }

    @Override
    public void addItemSceneRenderingHookPre(@NotNull ZPRenderHooks.ZPItemSceneRenderingHookPre zpItemSceneRenderingHookPre) {
        ZombiePlague3.clientInitValidation();
        this.itemSceneRenderingHooksPre.add(zpItemSceneRenderingHookPre);
    }

    @Override
    public void addItemSceneRenderingHookPost(@NotNull ZPRenderHooks.ZPItemSceneRenderingHookPost zpItemSceneRenderingHookPost) {
        ZombiePlague3.clientInitValidation();
        this.itemSceneRenderingHooksPost.add(zpItemSceneRenderingHookPost);
    }

    @Override
    public void addItemSceneRenderingHooks(@NotNull ZPRenderHooks.ZPItemSceneRenderingHooks zpItemSceneRenderingHooks) {
        ZombiePlague3.clientInitValidation();
        this.itemSceneRenderingHooksPre.add(zpItemSceneRenderingHooks);
        this.itemSceneRenderingHooksPost.add(zpItemSceneRenderingHooks);
    }

    public List<ZPRenderHooks.@NonNull ZPItemSceneRenderingHookPre> getItemSceneRenderingHooksPre() {
        return this.itemSceneRenderingHooksPre;
    }

    public List<ZPRenderHooks.@NonNull ZPItemSceneRenderingHookPost> getItemSceneRenderingHooksPost() {
        return this.itemSceneRenderingHooksPost;
    }

    public List<ZPRenderHooks.ZPSceneRenderingHook> getSceneRenderingHooks() {
        return this.sceneRenderingHooks;
    }

    public Map<Item, ZPRenderHooks.ZPItemRenderingHook> getItemRenderingHooks() {
        return this.itemRenderingHooks;
    }
}