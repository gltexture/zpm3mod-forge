package ru.gltexture.zpm3.engine.client.rendering.items;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.exceptions.ZPNullException;

import java.util.*;
import java.util.function.Consumer;

public final class ZPItemRenderingMatcher {
    private final Map<Item, ZPItemRenderingProcessor> gunRenderingMatching;

    public ZPItemRenderingMatcher() {
        this.gunRenderingMatching = new HashMap<>();
    }

    public void match(@NotNull Item item, @NotNull ZPItemRenderingProcessor zpItemRenderingProcessor) {
        this.gunRenderingMatching.put(item, zpItemRenderingProcessor);
    }

    public void consumeForAllRenderProcessors(@NotNull Consumer<ZPItemRenderingProcessor> zpItemRenderingProcessor) {
        this.gunRenderingMatching.values().forEach(zpItemRenderingProcessor);
    }

    public @Nullable ZPItemRenderingProcessor getRenderingProcessor(@NotNull Item item) {
        return this.gunRenderingMatching.getOrDefault(item, null);
    }
}