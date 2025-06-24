package ru.gltexture.zpm3.engine.helpers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.utils.Pair;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ZPItemModelProvider extends ItemModelProvider {
    private static final Set<Pair<Supplier<Item>, ItemTextureData>> itemsWithDefaultModel = new HashSet<>();

    public ZPItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ZombiePlague3.MOD_ID(), existingFileHelper);
    }

    public static void addNewObject(Pair<Supplier<Item>, ItemTextureData> item) {
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

    private void simpleItem(Item item, ItemTextureData itemTextureData) {
        String name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
        String directory = itemTextureData.path() == null ? "" : itemTextureData.path();
        this.withExistingParent(name, this.mcLoc(itemTextureData.mcReference())).texture("layer0", this.modLoc("item/" + directory + "/" + name));
    }

    public record ItemTextureData(@NotNull String mcReference, @Nullable String path) { }
}