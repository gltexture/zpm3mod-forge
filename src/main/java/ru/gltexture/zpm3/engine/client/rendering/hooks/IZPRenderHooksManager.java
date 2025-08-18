package ru.gltexture.zpm3.engine.client.rendering.hooks;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;

import java.util.function.Supplier;

public interface IZPRenderHooksManager extends ZPClientCallbacks.ZPClientResourceDependentObject {
    void addSceneRenderingHook(@NotNull ZPRenderHooks.ZPSceneRenderingHook zpSceneRenderingHook);
    void addItemRenderingHook(@NotNull Supplier<Item> itemSupplier, @NotNull ZPRenderHooks.ZPItemRenderingHook zpItemRenderingHook);
    void addItemSceneRenderingHookPre(@NotNull ZPRenderHooks.ZPItemSceneRenderingHookPre zpItemSceneRenderingHookPre);
    void addItemSceneRenderingHookPost(@NotNull ZPRenderHooks.ZPItemSceneRenderingHookPost zpItemSceneRenderingHookPost);
    void addItemSceneRenderingHooks(@NotNull ZPRenderHooks.ZPItemSceneRenderingHooks zpItemSceneRenderingHooks);
}