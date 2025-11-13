package ru.gltexture.zpm3.engine.helpers.gen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.service.ZPPath;

import java.util.Objects;
import java.util.*;
import java.util.function.Supplier;

public class ZPItemModelProvider extends ItemModelProvider {
    private static final Map<RegistryObject<? extends Item>, Supplier<ZPGenTextureData>> itemsWithDefaultModel = new LinkedHashMap<>();

    public ZPItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ZombiePlague3.MOD_ID, existingFileHelper);
    }

    public static void addNewObject(RegistryObject<? extends Item> item, Supplier<ZPGenTextureData> data) {
        ZPItemModelProvider.itemsWithDefaultModel.put(item, data);
    }

    public static void clearMap() {
        ZPItemModelProvider.itemsWithDefaultModel.clear();
    }

    @Override
    protected void registerModels() {
        ZPItemModelProvider.itemsWithDefaultModel.forEach(this::simpleItem);
        ZPItemModelProvider.clearMap();
    }

    private void simpleItem(RegistryObject<? extends Item> item, Supplier<ZPGenTextureData> itemTextureData) {
        String name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get())).getPath();
        final ZPGenTextureData genTextureData = itemTextureData.get();

        if (genTextureData.getVanillaModelReference() == null) {
            throw new ZPRuntimeException("Item's model should be extended by vanilla model");
        }

        String modelRef = genTextureData.getVanillaModelReference().reference();
        Supplier<ZPPath> supplier = genTextureData.getTextureByKey(ZPGenTextureData.LAYER0_KEY);
        if (supplier != null) {
            ZPPath texture = supplier.get();
            if (texture != null) {
                String texturePath = texture.getFullPath();
                this.withExistingParent(name, this.mcLoc(modelRef)).texture(ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.locate(this, texturePath));
                return;
            }
        }
        this.withExistingParent(name, this.mcLoc(modelRef));
    }

    public static Supplier<ZPGenTextureData> getTextureData(@NotNull RegistryObject<? extends Item> registryObject) {
        return ZPItemModelProvider.itemsWithDefaultModel.get(registryObject);
    }
}