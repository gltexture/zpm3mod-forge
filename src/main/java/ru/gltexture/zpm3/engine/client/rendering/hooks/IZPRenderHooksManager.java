package ru.gltexture.zpm3.engine.client.rendering.hooks;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;

import java.util.function.Supplier;

public interface IZPRenderHooksManager extends ZPClientCallbacks.ZPClientResourceDependentObject {
    void addSceneRenderingHook(@NotNull ZPRenderHooks.ZPSceneRenderingHook zpSceneRenderingHook);

    void addItemRendering1PersonHook(@NotNull Supplier<Item> itemSupplier, @NotNull ZPRenderHooks.ZPItemRendering1PersonHook zpItemRendering1PersonHook);
    void addItemRendering3PersonHook(@NotNull Supplier<Item> itemSupplier, @NotNull ZPRenderHooks.ZPItemRendering3PersonHook zpItemRendering3PersonHook);

    void addItemSceneRendering1PersonHookPre(@NotNull ZPRenderHooks.ZPItemSceneRendering1PersonHookPre zpItemSceneRendering1PersonHookPre);
    void addItemSceneRendering1PersonHookPost(@NotNull ZPRenderHooks.ZPItemSceneRendering1PersonHookPost zpItemSceneRendering1PersonHookPost);
    void addItemSceneRendering1PersonHooks(@NotNull ZPRenderHooks.ZPItemSceneRendering1PersonHooks zpItemSceneRendering1PersonHooks);

    void addItemSceneRendering3PersonHookPre(@NotNull ZPRenderHooks.ZPItemSceneRendering3PersonHookPre zpItemSceneRendering3PersonHookPre);
    void addItemSceneRendering3PersonHookPost(@NotNull ZPRenderHooks.ZPItemSceneRendering3PersonHookPost zpItemSceneRendering3PersonHookPost);
    void addItemSceneRendering3PersonHooks(@NotNull ZPRenderHooks.ZPItemSceneRendering3PersonHooks zpItemSceneRendering3PersonHooks);
}