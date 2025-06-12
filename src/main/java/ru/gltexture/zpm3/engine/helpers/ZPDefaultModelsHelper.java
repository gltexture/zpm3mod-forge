package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.world.item.Item;
import ru.gltexture.zpm3.engine.helpers.models.ZPItemModelProvider;

import java.util.function.Supplier;

public abstract class ZPDefaultModelsHelper {
    public static void addNewItemWithDefaultModel(Supplier<Item> item) {
        ZPItemModelProvider.addNewItem(item);
    }
}
