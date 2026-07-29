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

package ru.gltexture.zpm3.engine.helpers.gen.block_exec;

import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.MinecraftModelParentReference;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPBlockModelProvider;

public abstract class DefaultBlockItemModelExecutors {
    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EItem<? extends Block> getDefaultItemAsModParent(@NotNull MinecraftModelParentReference reference) {
        return (blockStateProvider, block, name, textureData) -> {
            blockStateProvider.itemModels().withExistingParent(name, blockStateProvider.modLoc(reference.mainBlockReference()));
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EItem<? extends Block> getDefaultItemAsVanillaParent(@NotNull MinecraftModelParentReference reference) {
        return (blockStateProvider, block, name, textureData) -> {
            blockStateProvider.itemModels().withExistingParent(name, blockStateProvider.mcLoc(reference.mainBlockReference()));
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EItem<? extends Block> getDefaultItemAsModBlock(String link) {
        return (blockStateProvider, block, name, textureData) -> {
            blockStateProvider.itemModels().withExistingParent(name, blockStateProvider.modLoc("block/" + link));
        };
    }


    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EItem<? extends Block> getDefaultItemAsBlock() {
        return (blockStateProvider, block, name, textureData) -> {
            blockStateProvider.itemModels().withExistingParent(name, blockStateProvider.modLoc("block/" + name));
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EItem<? extends Block> getDefaultItemAsBlock(String postfix) {
        return (blockStateProvider, block, name, textureData) -> {
            blockStateProvider.itemModels().withExistingParent(name, blockStateProvider.modLoc("block/" + name + postfix));
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EItem<? extends Block> getDefaultItemAsItem() {
        return (blockStateProvider, block, name, textureData) -> {
            String texturePath = textureData.getTextures().values().stream().findFirst().orElseThrow(() -> new ZPRuntimeException("Couldn't create texture for item")).get().getFullPath();
            blockStateProvider.itemModels().withExistingParent(name, "item/generated").texture(ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.locate(blockStateProvider, texturePath));
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EItem<? extends Block> getDefaultItemAs2DTexture(@NotNull String path) {
        return (blockStateProvider, block, name, textureData) -> {
            blockStateProvider.itemModels().withExistingParent(name, "item/generated").texture(ZPGenTextureData.LAYER0_KEY,  blockStateProvider.modLoc(path));
        };
    }
}
