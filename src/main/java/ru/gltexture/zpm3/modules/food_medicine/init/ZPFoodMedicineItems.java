package ru.gltexture.zpm3.modules.food_medicine.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.instances.items.*;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.modules.food_medicine.init.helper.ZPRegFood;
import ru.gltexture.zpm3.modules.food_medicine.init.helper.ZPRegMedicine;

public class ZPFoodMedicineItems extends ZPRegistry<Item> implements IZPCollectRegistryObjects {
    //FOOD
    public static RegistryObject<ZPItemFood> bean;
    public static RegistryObject<ZPItemFood> sprats;
    public static RegistryObject<ZPItemFood> jam;
    public static RegistryObject<ZPItemFood> mysterious_can;
    public static RegistryObject<ZPItemFood> peaches;
    public static RegistryObject<ZPItemFood> soda;
    public static RegistryObject<ZPItemFood> water;
    public static RegistryObject<ZPItemFood> fried_egg;

    //MEDICINE
    public static RegistryObject<ZPItemMedicine> adrenaline_syringe;
    public static RegistryObject<ZPItemMedicine> morphine_syringe;
    public static RegistryObject<ZPItemMedicine> antibiotics_syringe;
    public static RegistryObject<ZPItemMedicine> anti_headache_pill;
    public static RegistryObject<ZPItemMedicine> anti_hunger_pill;
    public static RegistryObject<ZPItemMedicine> anti_poison_pill;
    public static RegistryObject<ZPItemMedicine> anti_zplague_syringe;
    public static RegistryObject<ZPItemMedicine> zplague_syringe;
    public static RegistryObject<ZPItemMedicine> tire;
    public static RegistryObject<ZPItemMedicine> bandage;
    public static RegistryObject<ZPItemMedicine> military_bandage;
    public static RegistryObject<ZPItemMedicine> meth_pill;
    public static RegistryObject<ZPItemMedicine> healing_pill;
    public static RegistryObject<ZPItemMedicine> better_vision_pill;
    public static RegistryObject<ZPItemMedicine> aid_kit;

    public ZPFoodMedicineItems() {
        super(ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegFood.init(regSupplier);
        ZPRegMedicine.init(this, regSupplier);
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