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
            blockStateProvider.itemModels().withExistingParent(name, blockStateProvider.modLoc(reference.reference()));
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EItem<? extends Block> getDefaultItemAsVanillaParent(@NotNull MinecraftModelParentReference reference) {
        return (blockStateProvider, block, name, textureData) -> {
            blockStateProvider.itemModels().withExistingParent(name, blockStateProvider.mcLoc(reference.reference()));
        };
    }

    public static @NotNull ZPBlockModelProvider.BlockModelExecutor.EItem<? extends Block> getDefaultItemAsBlock() {
        return (blockStateProvider, block, name, textureData) -> {
            blockStateProvider.itemModels().withExistingParent(name, blockStateProvider.modLoc("block/" + name));
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
            blockStateProvider.itemModels().withExistingParent(name, "item/generated").texture(ZPGenTextureData.LAYER0_KEY, path);
        };
    }
}
