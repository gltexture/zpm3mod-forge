package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegFood;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegMedicine;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegMelee;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegThrowable;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.objects.items.*;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public class ZPItems extends ZPRegistry<Item> {
    // ITEMS
    public static RegistryObject<ZPItemThrowable> acid_bottle;
    public static RegistryObject<ZPItemThrowable> plate;
    public static RegistryObject<ZPItemThrowable> rock;


    // MELEE
    public static RegistryObject<ZPItemSword> bat;
    public static RegistryObject<ZPItemSword> iron_club;
    public static RegistryObject<ZPItemSword> pipe;
    public static RegistryObject<ZPItemSword> golf_club;
    public static RegistryObject<ZPItemAxe> hatchet;
    public static RegistryObject<ZPItemPickaxe> sledgehammer;
    public static RegistryObject<ZPItemSword> crowbar;
    public static RegistryObject<ZPItemSword> cleaver;


    //FOOD
    public static RegistryObject<ZPItemFood> bean;
    public static RegistryObject<ZPItemFood> sprats;
    public static RegistryObject<ZPItemFood> jam;
    public static RegistryObject<ZPItemFood> mysterious_can;
    public static RegistryObject<ZPItemFood> peaches;
    public static RegistryObject<ZPItemFood> soda;
    public static RegistryObject<ZPItemFood> water;

    //MEDICINE
    public static RegistryObject<ZPItemMedicine> adrenaline;
    public static RegistryObject<ZPItemMedicine> morphine;
    public static RegistryObject<ZPItemMedicine> antidote;
    public static RegistryObject<ZPItemMedicine> aquaclear;
    public static RegistryObject<ZPItemMedicine> bandage;
    public static RegistryObject<ZPItemMedicine> calmexin;
    public static RegistryObject<ZPItemMedicine> carbocid;
    public static RegistryObject<ZPItemMedicine> infectonol;
    public static RegistryObject<ZPItemMedicine> vodka;

    public ZPItems() {
        super(ForgeRegistries.ITEMS, ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegThrowable.init(regSupplier);
        ZPRegMelee.init(regSupplier);
        ZPRegFood.init(regSupplier);
        ZPRegMedicine.init(regSupplier);
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