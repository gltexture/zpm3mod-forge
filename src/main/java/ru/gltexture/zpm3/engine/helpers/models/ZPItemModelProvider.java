package ru.gltexture.zpm3.engine.helpers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ZPItemModelProvider extends ItemModelProvider {
    private static final Set<Supplier<Item>> itemsWithDefaultModel = new HashSet<>();

    public ZPItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ZombiePlague3.MOD_ID(), existingFileHelper);
    }

    public static void addNewItem(Supplier<Item> item) {
        ZPItemModelProvider.itemsWithDefaultModel.add(item);
    }

    public static void clearSet() {
        ZPItemModelProvider.itemsWithDefaultModel.clear();
    }

    @Override
    protected void registerModels() {
        ZPItemModelProvider.itemsWithDefaultModel.forEach(e -> this.simpleItem(e.get()));
        ZPItemModelProvider.clearSet();
    }

    private void simpleItem(Item item) {
        String name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
        this.withExistingParent(name, mcLoc("item/generated")).texture("layer0", modLoc("item/" + name));
    }
}
