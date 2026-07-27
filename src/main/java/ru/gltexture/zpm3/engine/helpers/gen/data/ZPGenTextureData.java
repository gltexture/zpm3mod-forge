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

package ru.gltexture.zpm3.engine.helpers.gen.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.service.ZPPath;

import java.util.*;
import java.util.function.Supplier;

public class ZPGenTextureData {
    public static final String LAYER0_KEY = "layer0";
    public static final String LAYER1_KEY = "layer1";
    public static final String LAYER2_KEY = "layer2";
    public static final String ALL_KEY = "all";
    public static final String BARS_KEY = "bars";
    public static final String CROSS_KEY = "cross";
    public static final String BOTTOM_KEY = "bottom";
    public static final String SIDE_KEY = "side";
    public static final String END_KEY = "end";
    public static final String TOP_KEY = "top";

    private final Map<@Nullable String, @NotNull Supplier<ZPPath>> textures;
    private final MinecraftModelParentReference vanillaModelReference;
    private ZPGenTextureData parent;

    protected ZPGenTextureData(@Nullable MinecraftModelParentReference vanillaModelReference) {
        this.textures = new HashMap<>();
        this.vanillaModelReference = vanillaModelReference;
        this.parent = null;
    }

    protected ZPGenTextureData(@Nullable MinecraftModelParentReference vanillaModelReference, @Nullable Map<@Nullable String, @NotNull Supplier<ZPPath>> supplierMap) {
        this.textures = supplierMap;
        this.vanillaModelReference = vanillaModelReference;
        this.parent = null;
    }

    protected ZPGenTextureData(@NotNull ZPGenTextureData zpGenTextureData) {
        this(zpGenTextureData.getVanillaModelReference(), null);
        this.parent = zpGenTextureData;
    }

    @SafeVarargs
    public static ZPGenTextureData of(@Nullable MinecraftModelParentReference vanillaModelReference, Pair<@NotNull String, @NotNull Supplier<ZPPath>>... descriptors) {
        ZPGenTextureData zpGenTextureData = new ZPGenTextureData(vanillaModelReference);
        for (Pair<String, Supplier<ZPPath>> descriptor : descriptors) {
            zpGenTextureData.textures.put(descriptor.first(), descriptor.second());
        }
        return zpGenTextureData;
    }

    public static ZPGenTextureData of(@Nullable MinecraftModelParentReference vanillaModelReference, @NotNull Supplier<ZPPath> path) {
        return ZPGenTextureData.of(vanillaModelReference, Pair.of(ZPGenTextureData.ALL_KEY, path));
    }

    public static ZPGenTextureData of(@Nullable MinecraftModelParentReference vanillaModelReference, @NotNull String key, @NotNull Supplier<ZPPath> path) {
        return ZPGenTextureData.of(vanillaModelReference, Pair.of(key, path));
    }

    public static ZPGenTextureData copy(@NotNull ZPGenTextureData zpGenTextureData) {
        return new ZPGenTextureData(zpGenTextureData);
    }

    public static ZPGenTextureData copy(@Nullable MinecraftModelParentReference vanillaModelReference, @NotNull ZPGenTextureData zpGenTextureData) {
        return new ZPGenTextureData(vanillaModelReference, zpGenTextureData.getTextures());
    }

    public @Nullable Supplier<ZPPath> getTextureByKey(@NotNull String key) {
        if (!this.getTextures().containsKey(key)) {
            return null;
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

    public @Nullable MinecraftModelParentReference getVanillaModelReference() {
        return this.vanillaModelReference;
    }

    public boolean isHomogenousTextured() {
        return this.getTextures().size() == 1 && this.getTextures().containsKey(ZPGenTextureData.ALL_KEY);
    }
}
