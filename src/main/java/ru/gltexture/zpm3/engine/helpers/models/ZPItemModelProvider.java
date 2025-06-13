package ru.gltexture.zpm3.engine.helpers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.utils.Pair;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ZPItemModelProvider extends ItemModelProvider {
    private static final Set<Pair<Supplier<Item>, String>> itemsWithDefaultModel = new HashSet<>();

    public ZPItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ZombiePlague3.MOD_ID(), existingFileHelper);
    }

    public static void addNewObject(Pair<Supplier<Item>, String> item) {
        ZPItemModelProvider.itemsWithDefaultModel.add(item);
    }

    public static void clearSet() {
        ZPItemModelProvider.itemsWithDefaultModel.clear();
    }

    @Override
    protected void registerModels() {
        ZPItemModelProvider.itemsWithDefaultModel.forEach(e -> this.simpleItem(e.getFirst().get(), e.getSecond()));
        ZPItemModelProvider.clearSet();
    }

    private void simpleItem(Item item, String reference) {
        String name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
        this.withExistingParent(name, this.mcLoc(reference)).texture("layer0", this.modLoc("item/" + name));
    }
}
