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
    public static final ZPItemModelProvider.ItemTextureData DEFAULT_FOOD = new ZPItemModelProvider.ItemTextureData("item/bread", "food");
    public static final ZPItemModelProvider.ItemTextureData DEFAULT_ITEM = new ZPItemModelProvider.ItemTextureData("item/diamond", "items");
    public static final ZPItemModelProvider.ItemTextureData DEFAULT_MELEE = new ZPItemModelProvider.ItemTextureData("item/diamond_sword", "melee");
    public static final ZPItemModelProvider.ItemTextureData DEFAULT_AXE = new ZPItemModelProvider.ItemTextureData("item/diamond_axe", "tools");
    public static final ZPItemModelProvider.ItemTextureData DEFAULT_PICKAXE = new ZPItemModelProvider.ItemTextureData("item/diamond_pickaxe", "tools");
    public static final ZPItemModelProvider.ItemTextureData DEFAULT_SHOVEL = new ZPItemModelProvider.ItemTextureData("item/diamond_shovel", "tools");
    public static final ZPItemModelProvider.ItemTextureData DEFAULT_HOE = new ZPItemModelProvider.ItemTextureData("item/diamond_hoe", "tools");

    public static void addNewItemWithDefaultModel(@NotNull Supplier<Item> item, ZPItemModelProvider.ItemTextureData itemTextureData) {
        ZPItemModelProvider.addNewObject(new Pair<>(item, itemTextureData));
    }

    public static void addNewBlockWithDefaultModel(@NotNull Supplier<Block> block, String reference) {
        ZPBlockModelProvider.addNewObject(new Pair<>(block, reference));
    }
}
