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

package ru.gltexture.zpm3.modules.melee_throwables_tools.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.instances.items.*;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.helper.ZPRegToolItems;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.helper.ZPRegMelee;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.helper.ZPRegThrowable;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.ZPCrowbarSword;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.ZPMatches;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.ZPMetalCuttersTool;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.ZPWrenchTool;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.admin.ZPAdminWrenchFadingBlocks;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.melee.ZPBroomSword;

public class ZPMeleeThrowableToolsItems extends ZPCommonRegistry<Item> implements IZPCollectRegistryObjects {
    // ITEMS
    public static RegistryObject<ZPItemThrowable> acid_bottle;
    public static RegistryObject<ZPItemThrowable> plate;
    public static RegistryObject<ZPItemThrowable> rock;
    public static RegistryObject<ZPItemBucket> acid_bucket;
    public static RegistryObject<ZPItemBucket> toxicwater_bucket;

    // TOOLS
    public static RegistryObject<ZPMetalCuttersTool> metal_cutters;
    public static RegistryObject<ZPWrenchTool> wrench;
    public static RegistryObject<ZPMatches> matches;
    public static RegistryObject<ZPAdminWrenchFadingBlocks> admin_wrench_torches;

    // MELEE
    public static RegistryObject<ZPItemSword> bat;
    public static RegistryObject<ZPItemSword> iron_club;
    public static RegistryObject<ZPItemSword> pipe;
    public static RegistryObject<ZPItemSword> golf_club;
    public static RegistryObject<ZPItemAxe> hatchet;
    public static RegistryObject<ZPItemPickaxe> sledgehammer;
    public static RegistryObject<ZPCrowbarSword> crowbar;
    public static RegistryObject<ZPItemSword> cleaver;
    public static RegistryObject<ZPBroomSword> broom;

    public ZPMeleeThrowableToolsItems() {
        super(ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPRegThrowable.init(regSupplier);
        ZPRegToolItems.init(regSupplier);
        ZPRegMelee.init(regSupplier);
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