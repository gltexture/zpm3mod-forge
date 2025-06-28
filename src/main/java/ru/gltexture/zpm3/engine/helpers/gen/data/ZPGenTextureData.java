package ru.gltexture.zpm3.engine.helpers.gen.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.service.ZPPath;

import java.util.*;
import java.util.function.Supplier;

public class ZPGenTextureData {
    public static final String LAYER0_KEY = "layer0";
    public static final String ALL_KEY = "all";
    public static final String CROSS_KEY = "cross";
    public static final String BOTTOM_KEY = "bottom";
    public static final String SIDE_KEY = "side";
    public static final String END_KEY = "end";
    public static final String TOP_KEY = "top";

    private final Map<@Nullable String, @NotNull Supplier<ZPPath>> textures;
    private final VanillaMCModelRef vanillaModelReference;
    private ZPGenTextureData parent;

    protected ZPGenTextureData(@Nullable VanillaMCModelRef vanillaModelReference) {
        this.textures = new HashMap<>();
        this.vanillaModelReference = vanillaModelReference;
        this.parent = null;
    }

    protected ZPGenTextureData(@Nullable VanillaMCModelRef vanillaModelReference, @Nullable Map<@Nullable String, @NotNull Supplier<ZPPath>> supplierMap) {
        this.textures = supplierMap;
        this.vanillaModelReference = vanillaModelReference;
        this.parent = null;
    }

    protected ZPGenTextureData(@NotNull ZPGenTextureData zpGenTextureData) {
        this(zpGenTextureData.getVanillaModelReference(), null);
        this.parent = zpGenTextureData;
    }

    @SafeVarargs
    public static ZPGenTextureData of(@Nullable VanillaMCModelRef vanillaModelReference, Pair<@NotNull String, @NotNull Supplier<ZPPath>>... descriptors) {
        ZPGenTextureData zpGenTextureData = new ZPGenTextureData(vanillaModelReference);
        for (Pair<String, Supplier<ZPPath>> descriptor : descriptors) {
            zpGenTextureData.textures.put(descriptor.first(), descriptor.second());
        }
        return zpGenTextureData;
    }

    public static ZPGenTextureData of(@Nullable VanillaMCModelRef vanillaModelReference, @NotNull Supplier<ZPPath> path) {
        return ZPGenTextureData.of(vanillaModelReference, Pair.of(ZPGenTextureData.ALL_KEY, path));
    }

    public static ZPGenTextureData of(@Nullable VanillaMCModelRef vanillaModelReference, @NotNull String key, @NotNull Supplier<ZPPath> path) {
        return ZPGenTextureData.of(vanillaModelReference, Pair.of(key, path));
    }

    public static ZPGenTextureData copy(@NotNull ZPGenTextureData zpGenTextureData) {
        return new ZPGenTextureData(zpGenTextureData);
    }

    public static ZPGenTextureData copy(@Nullable VanillaMCModelRef vanillaModelReference, @NotNull ZPGenTextureData zpGenTextureData) {
        return new ZPGenTextureData(vanillaModelReference, zpGenTextureData.getTextures());
    }

    public Supplier<ZPPath> getTextureByKey(@NotNull String key) {
        if (!this.getTextures().containsKey(key)) {
            throw new ZPRuntimeException("TextureData object doesn't contains texture: " + key);
        }
        return this.getTextures().get(key);
    }

    public @Nullable ZPGenTextureData getParent() {
        return this.parent;
    }

    public Map<@Nullable String, @NotNull Supplier<ZPPath>> getTextures() {
        if (this.getParent() != null) {
            return this.getParent().getTextures();
        }
        return new HashMap<>(this.textures);
    }

    public @Nullable VanillaMCModelRef getVanillaModelReference() {
        return this.vanillaModelReference;
    }

    public boolean isHomogenousTextured() {
        return this.getTextures().size() == 1 && this.getTextures().containsKey(ZPGenTextureData.ALL_KEY);
    }
}
