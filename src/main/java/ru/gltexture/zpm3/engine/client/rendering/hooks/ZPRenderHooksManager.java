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

    private Map<Supplier<Item>, ZPRenderHooks.@NonNull ZPItemRendering1PersonHook> itemRenderingHooks1PersonTemp;
    private Map<Supplier<Item>, ZPRenderHooks.@NonNull ZPItemRendering3PersonHook> itemRenderingHooks3PersonTemp;

    private final Map<Item, ZPRenderHooks.@NonNull ZPItemRendering1PersonHook> itemRendering1PersonHooks;
    private final Map<Item, ZPRenderHooks.@NonNull ZPItemRendering3PersonHook> itemRendering3PersonHooks;

    private final List<ZPRenderHooks.@NonNull ZPSceneRenderingHook> sceneRenderingHooks;

    private final List<ZPRenderHooks.@NonNull ZPItemSceneRendering1PersonHookPre> itemSceneRendering1PersonHooksPre;
    private final List<ZPRenderHooks.@NonNull ZPItemSceneRendering1PersonHookPost> itemSceneRendering1PersonHooksPost;

    private final List<ZPRenderHooks.@NonNull ZPItemSceneRendering3PersonHookPre> itemSceneRendering3PersonHooksPre;
    private final List<ZPRenderHooks.@NonNull ZPItemSceneRendering3PersonHookPost> itemSceneRendering3PersonHooksPost;

    private ZPRenderHooksManager() {
        this.sceneRenderingHooks = new ArrayList<>();

        this.itemRenderingHooks1PersonTemp = new HashMap<>();
        this.itemRendering1PersonHooks = new HashMap<>();
        this.itemRenderingHooks3PersonTemp = new HashMap<>();
        this.itemRendering3PersonHooks = new HashMap<>();

        this.itemSceneRendering1PersonHooksPre = new ArrayList<>();
        this.itemSceneRendering1PersonHooksPost = new ArrayList<>();

        this.itemSceneRendering3PersonHooksPre = new ArrayList<>();
        this.itemSceneRendering3PersonHooksPost = new ArrayList<>();
    }

    private void onPostZPInit() {
        this.itemRenderingHooks1PersonTemp.forEach((k, v) -> itemRendering1PersonHooks.put(k.get(), v));
        this.itemRenderingHooks1PersonTemp = null;

        this.itemRenderingHooks3PersonTemp.forEach((k, v) -> itemRendering3PersonHooks.put(k.get(), v));
        this.itemRenderingHooks3PersonTemp = null;
    }

    @Override
    public void setupResources(@NotNull Window window) {
        this.onPostZPInit();
    }

    @Override
    public void destroyResources(@NotNull Window window) {
        this.sceneRenderingHooks.clear();

        this.itemRenderingHooks1PersonTemp.clear();
        this.itemRendering1PersonHooks.clear();
        this.itemRenderingHooks3PersonTemp.clear();
        this.itemRendering3PersonHooks.clear();

        this.itemSceneRendering1PersonHooksPre.clear();
        this.itemSceneRendering1PersonHooksPost.clear();
        this.itemSceneRendering3PersonHooksPre.clear();
        this.itemSceneRendering3PersonHooksPost.clear();
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
    public void addItemRendering1PersonHook(@NotNull Supplier<Item> itemSupplier, @NotNull ZPRenderHooks.ZPItemRendering1PersonHook zpItemRendering1PersonHook) {
        ZombiePlague3.clientInitValidation();
        this.itemRenderingHooks1PersonTemp.put(itemSupplier, zpItemRendering1PersonHook);
    }

    @Override
    public void addItemRendering3PersonHook(@NotNull Supplier<Item> itemSupplier, @NotNull ZPRenderHooks.ZPItemRendering3PersonHook zpItemRendering3PersonHook) {
        ZombiePlague3.clientInitValidation();
        this.itemRenderingHooks3PersonTemp.put(itemSupplier, zpItemRendering3PersonHook);
    }

    @Override
    public void addItemSceneRendering1PersonHookPre(@NotNull ZPRenderHooks.ZPItemSceneRendering1PersonHookPre zpItemSceneRendering1PersonHookPre) {
        ZombiePlague3.clientInitValidation();
        this.itemSceneRendering1PersonHooksPre.add(zpItemSceneRendering1PersonHookPre);
    }

    @Override
    public void addItemSceneRendering1PersonHookPost(@NotNull ZPRenderHooks.ZPItemSceneRendering1PersonHookPost zpItemSceneRendering1PersonHookPost) {
        ZombiePlague3.clientInitValidation();
        this.itemSceneRendering1PersonHooksPost.add(zpItemSceneRendering1PersonHookPost);
    }

    @Override
    public void addItemSceneRendering1PersonHooks(@NotNull ZPRenderHooks.ZPItemSceneRendering1PersonHooks zpItemSceneRendering1PersonHooks) {
        ZombiePlague3.clientInitValidation();
        this.itemSceneRendering1PersonHooksPre.add(zpItemSceneRendering1PersonHooks);
        this.itemSceneRendering1PersonHooksPost.add(zpItemSceneRendering1PersonHooks);
    }


    @Override
    public void addItemSceneRendering3PersonHookPre(@NotNull ZPRenderHooks.ZPItemSceneRendering3PersonHookPre zpItemSceneRendering3PersonHookPre) {
        ZombiePlague3.clientInitValidation();
        this.itemSceneRendering3PersonHooksPre.add(zpItemSceneRendering3PersonHookPre);
    }

    @Override
    public void addItemSceneRendering3PersonHookPost(@NotNull ZPRenderHooks.ZPItemSceneRendering3PersonHookPost zpItemSceneRendering3PersonHookPost) {
        ZombiePlague3.clientInitValidation();
        this.itemSceneRendering3PersonHooksPost.add(zpItemSceneRendering3PersonHookPost);
    }

    @Override
    public void addItemSceneRendering3PersonHooks(@NotNull ZPRenderHooks.ZPItemSceneRendering3PersonHooks zpItemSceneRendering3PersonHooks) {
        ZombiePlague3.clientInitValidation();
        this.itemSceneRendering3PersonHooksPre.add(zpItemSceneRendering3PersonHooks);
        this.itemSceneRendering3PersonHooksPost.add(zpItemSceneRendering3PersonHooks);
    }


    public List<ZPRenderHooks.@NonNull ZPItemSceneRendering1PersonHookPre> getItemSceneRendering1PersonHooksPre() {
        return this.itemSceneRendering1PersonHooksPre;
    }

    public List<ZPRenderHooks.@NonNull ZPItemSceneRendering1PersonHookPost> getItemSceneRendering1PersonHooksPost() {
        return this.itemSceneRendering1PersonHooksPost;
    }

    public List<ZPRenderHooks.@NonNull ZPItemSceneRendering3PersonHookPre> getItemSceneRendering3PersonHooksPre() {
        return this.itemSceneRendering3PersonHooksPre;
    }

    public List<ZPRenderHooks.@NonNull ZPItemSceneRendering3PersonHookPost> getItemSceneRendering3PersonHooksPost() {
        return this.itemSceneRendering3PersonHooksPost;
    }

    public List<ZPRenderHooks.ZPSceneRenderingHook> getSceneRenderingHooks() {
        return this.sceneRenderingHooks;
    }

    public Map<Item, ZPRenderHooks.ZPItemRendering1PersonHook> getItemRendering1PersonHooks() {
        return this.itemRendering1PersonHooks;
    }

    public Map<Item, ZPRenderHooks.ZPItemRendering3PersonHook> getItemRendering3PersonHooks() {
        return this.itemRendering3PersonHooks;
    }
}