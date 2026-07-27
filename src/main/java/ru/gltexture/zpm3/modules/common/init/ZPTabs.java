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

package ru.gltexture.zpm3.modules.common.init;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.instances.ZPBlockItemsRegistry;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.modules.armor.init.ZPArmorItems;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlocks;
import ru.gltexture.zpm3.modules.blocks.init.ZPTorchBlocks;
import ru.gltexture.zpm3.modules.food_medicine.init.ZPFoodMedicineItems;
import ru.gltexture.zpm3.modules.guns.init.ZPGunItems;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.ZPMeleeThrowableToolsItems;

public class ZPTabs extends ZPRegistry<CreativeModeTab> {
    public static RegistryObject<CreativeModeTab> zp_guns_tab;
    public static RegistryObject<CreativeModeTab> zp_items_tab;
   // public static RegistryObject<CreativeModeTab> zp_misc_tab;
    public static RegistryObject<CreativeModeTab> zp_melee_tab;
    public static RegistryObject<CreativeModeTab> zp_food_tab;
    public static RegistryObject<CreativeModeTab> zp_medicine_tab;
    public static RegistryObject<CreativeModeTab> zp_blocks_tab;
    //public static RegistryObject<CreativeModeTab> zp_functional_blocks_tab;
    //public static RegistryObject<CreativeModeTab> zp_spawns_tab;
    //public static @Nullable RegistryObject<CreativeModeTab> zp_lootcases_tab;
    public static RegistryObject<CreativeModeTab> zp_armor_tab;

    public ZPTabs() {
        super(ZPRegistryConveyor.Target.CREATIVE_MODE_TAB);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<CreativeModeTab> regSupplier) {
        ZPTabs.zp_guns_tab = regSupplier.register("zp_guns_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.guns")).icon(() -> new ItemStack(ZPGunItems.makarov.get())).build()).end();
        ZPTabs.zp_items_tab = regSupplier.register("zp_items_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.items")).icon(() -> new ItemStack(ZPMeleeThrowableToolsItems.acid_bottle.get())).build()).end();
        ZPTabs.zp_blocks_tab = regSupplier.register("zp_blocks_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.blocks")).icon(() -> new ItemStack(ZPBlockItemsRegistry.getBlockItem(ZPBlocks.block_lamp).get())).build()).end();
        //ZPTabs.zp_functional_blocks_tab = regSupplier.register("zp_functional_blocks_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.functional_blocks")).icon(() -> new ItemStack(ZPBlockItemsRegistry.getBlockItem(ZPTorchBlocks.torch4).get())).build()).end();
        ZPTabs.zp_melee_tab = regSupplier.register("zp_melee_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.melee")).icon(() -> new ItemStack(ZPMeleeThrowableToolsItems.crowbar.get())).build()).end();
        ZPTabs.zp_food_tab = regSupplier.register("zp_food_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.food")).icon(() -> new ItemStack(ZPFoodMedicineItems.soda.get())).build()).end();
        ZPTabs.zp_medicine_tab = regSupplier.register("zp_medicine_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.medicine")).icon(() -> new ItemStack(ZPFoodMedicineItems.aid_kit.get())).build()).end();
        //ZPTabs.zp_spawns_tab = regSupplier.register("zp_spawns_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.spawns")).icon(() -> new ItemStack(ZPSpawnItems.common_zm_spawn.get())).build()).end();
       // ZPTabs.zp_misc_tab = regSupplier.register("zp_misc_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.misc")).icon(() -> new ItemStack(ZPMiscItems.cement_material.get())).build()).end();
        //ZPTabs.zp_lootcases_tab = regSupplier.register("zp_lootcases_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.lootcases")).icon(() -> new ItemStack(Blocks.CHEST)).build()).end();
        ZPTabs.zp_armor_tab = regSupplier.register("zp_armor_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.armor")).icon(() -> new ItemStack(ZPArmorItems.acid_costume_helmet.get())).build()).end();
    }

    @Override
    protected void postRegister(String name, RegistryObject<CreativeModeTab> object) {
        super.postRegister(name, object);
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
