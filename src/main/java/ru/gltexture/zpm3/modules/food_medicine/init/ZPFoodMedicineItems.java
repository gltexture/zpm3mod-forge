/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.food_medicine.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.instances.items.*;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.modules.food_medicine.init.helper.ZPRegFood;
import ru.gltexture.zpm3.modules.food_medicine.init.helper.ZPRegMedicine;

public class ZPFoodMedicineItems extends ZPCommonRegistry<Item> implements IZPCollectRegistryObjects {
    //FOOD
    public static RegistryObject<ZPItemFood> bean;
    public static RegistryObject<ZPItemFood> sprats;
    public static RegistryObject<ZPItemFood> jam;
    public static RegistryObject<ZPItemFood> mysterious_can;
    public static RegistryObject<ZPItemFood> peaches;
    public static RegistryObject<ZPItemFood> soda;
    public static RegistryObject<ZPItemFood> water;
    public static RegistryObject<ZPItemFood> fried_egg;
    public static RegistryObject<ZPItemFood> rotten_apple;
    public static RegistryObject<ZPItemFood> chocolate;
    public static RegistryObject<ZPItemFood> minecake;

    //MEDICINE
    public static RegistryObject<ZPItemMedicine> adrenaline_syringe;
    public static RegistryObject<ZPItemMedicine> morphine_syringe;
    public static RegistryObject<ZPItemMedicine> antibiotics_syringe;
    public static RegistryObject<ZPItemMedicine> anti_headache_pill;
    public static RegistryObject<ZPItemMedicine> anti_hunger_pill;
    public static RegistryObject<ZPItemMedicine> anti_poison_pill;
    public static RegistryObject<ZPItemMedicine> anti_zplague_syringe;
    public static RegistryObject<ZPItemMedicine> zplague_syringe;
    public static RegistryObject<ZPItemMedicine> splint;
    public static RegistryObject<ZPItemMedicine> bandage;
    public static RegistryObject<ZPItemMedicine> drugs;
    public static RegistryObject<ZPItemMedicine> vodka_medicine;
    public static RegistryObject<ZPItemMedicine> whiskey_medicine;
    public static RegistryObject<ZPItemMedicine> radiation_protection_pill;
    public static RegistryObject<ZPItemMedicine> vitamin_pill;
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