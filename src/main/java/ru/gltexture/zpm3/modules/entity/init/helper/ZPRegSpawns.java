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

package ru.gltexture.zpm3.modules.entity.init.helper;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.modules.entity.init.ZPEntities;
import ru.gltexture.zpm3.modules.common.init.ZPTabs;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.entity.init.ZPSpawnItems;

public abstract class ZPRegSpawns {
    public static void init(@NotNull ZPCommonRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPSpawnItems.common_zm_spawn = regSupplier.register("common_zm_spawn", () -> new ForgeSpawnEggItem((() -> ZPEntities.zp_common_zombie_entity.get()), 0x3E3B36, 0x799C65, new Item.Properties())
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_SPAWN_EGG);
            });
        }).end();

        ZPSpawnItems.miner_zm_spawn = regSupplier.register("miner_zm_spawn", () -> new ForgeSpawnEggItem((() -> ZPEntities.zp_miner_zombie_entity.get()), 0xA63B36, 0x7C9F65, new Item.Properties())
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_SPAWN_EGG);
            });
        }).end();

        ZPSpawnItems.dog_zm_spawn = regSupplier.register("dog_zm_spawn", () -> new ForgeSpawnEggItem((() -> ZPEntities.zp_dog_zombie_entity.get()), 0xC8C8C8, 0xDD0205, new Item.Properties())
        ).afterCreated((e, utils) -> {
            ZPUtility.sides().onlyClient(() -> {
                utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_SPAWN_EGG);
            });
        }).end();
    }
}
