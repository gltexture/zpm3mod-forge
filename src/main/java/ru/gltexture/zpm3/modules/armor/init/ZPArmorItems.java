package ru.gltexture.zpm3.modules.armor.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.instances.armor.ZPArmorItem;
import ru.gltexture.zpm3.engine.instances.items.ZPItem;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.modules.armor.init.helper.ZPRegArmorItemsHelper;

public class ZPArmorItems extends ZPRegistry<Item> implements IZPCollectRegistryObjects {
    public static RegistryObject<ZPArmorItem> forest_helmet;
    public static RegistryObject<ZPArmorItem> forest_chestplate;
    public static RegistryObject<ZPArmorItem> forest_leggings;
    public static RegistryObject<ZPArmorItem> forest_boots;

    public static RegistryObject<ZPArmorItem> winter_helmet;
    public static RegistryObject<ZPArmorItem> winter_chestplate;
    public static RegistryObject<ZPArmorItem> winter_leggings;
    public static RegistryObject<ZPArmorItem> winter_boots;

    public static RegistryObject<ZPArmorItem> sand_helmet;
    public static RegistryObject<ZPArmorItem> sand_chestplate;
    public static RegistryObject<ZPArmorItem> sand_leggings;
    public static RegistryObject<ZPArmorItem> sand_boots;

    public static RegistryObject<ZPArmorItem> acid_costume_helmet;
    public static RegistryObject<ZPArmorItem> acid_costume_chestplate;
    public static RegistryObject<ZPArmorItem> acid_costume_leggings;
    public static RegistryObject<ZPArmorItem> acid_costume_boots;

    public static RegistryObject<ZPArmorItem> radiation_costume_helmet;
    public static RegistryObject<ZPArmorItem> radiation_costume_chestplate;
    public static RegistryObject<ZPArmorItem> radiation_costume_leggings;
    public static RegistryObject<ZPArmorItem> radiation_costume_boots;

    public static RegistryObject<ZPArmorItem> night_vision_goggles;

    public ZPArmorItems() {
        super(ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegArmorItemsHelper.init(regSupplier);
    }

    @Override
    protected void postRegister(String name, RegistryObject<Item> object) {
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}
