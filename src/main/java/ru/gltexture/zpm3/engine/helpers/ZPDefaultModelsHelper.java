package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.helpers.models.ZPBlockModelProvider;
import ru.gltexture.zpm3.engine.helpers.models.ZPItemModelProvider;
import ru.gltexture.zpm3.engine.utils.Pair;

import java.util.function.Supplier;

public abstract class ZPDefaultModelsHelper {
    public static final String DEFAULT_BLOCK_CUBE = "block/cobblestone";
    public static final String DEFAULT_ITEM = "item/stick";

    public static void addNewItemWithDefaultModel(@NotNull Supplier<Item> item, String reference) {
        ZPItemModelProvider.addNewObject(new Pair<>(item, reference));
    }

    public static void addNewBlockWithDefaultModel(@NotNull Supplier<Block> block, String reference) {
        ZPBlockModelProvider.addNewObject(new Pair<>(block, reference));
    }
}
