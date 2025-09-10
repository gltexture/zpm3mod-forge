package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.helper.*;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.assets.guns.item.ZPGunPistol;
import ru.gltexture.zpm3.engine.instances.items.*;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;

public class ZPItems extends ZPRegistry<Item> implements IZPCollectRegistryObjects {
    // ITEMS
    public static RegistryObject<ZPItemThrowable> acid_bottle;
    public static RegistryObject<ZPItemThrowable> plate;
    public static RegistryObject<ZPItemThrowable> rock;
    public static RegistryObject<ZPItemBucket> acid_bucket;
    public static RegistryObject<ZPItemBucket> toxicwater_bucket;

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

    //GUNS
    public static RegistryObject<ZPGunPistol> handmade_pistol;
    public static RegistryObject<ZPItem> _handmade_pistol;

    public static RegistryObject<ZPGunPistol> makarov;
    public static RegistryObject<ZPItem> _makarov;

    public static RegistryObject<ZPGunPistol> m1911;
    public static RegistryObject<ZPItem> _m1911;

    public static RegistryObject<ZPGunPistol> usp;
    public static RegistryObject<ZPItem> _usp;

    public static RegistryObject<ZPGunPistol> colt;
    public static RegistryObject<ZPItem> _colt;

    public static RegistryObject<ZPGunPistol> deagle;
    public static RegistryObject<ZPGunPistol> golden_deagle;
    public static RegistryObject<ZPItem> _deagle;

    public static RegistryObject<ZPGunPistol> uzi;
    public static RegistryObject<ZPItem> _uzi;

    public ZPItems() {
        super(ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegThrowable.init(regSupplier);
        ZPRegItems.init(regSupplier);
        ZPRegMelee.init(regSupplier);
        ZPRegFood.init(regSupplier);
        ZPRegMedicine.init(this, regSupplier);
        ZPRegGuns.init(regSupplier);
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