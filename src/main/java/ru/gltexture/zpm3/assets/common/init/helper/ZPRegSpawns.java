package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPEntities;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public abstract class ZPRegSpawns {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPItems.common_zm_spawn = regSupplier.register("common_zm_spawn", () -> new ForgeSpawnEggItem((() -> ZPEntities.zp_common_zombie_entity.get()), 0x3E3B36, 0x799C65, new Item.Properties())
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_spawns_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_SPAWN_EGG);
        }).registryObject();

        ZPItems.miner_zm_spawn = regSupplier.register("miner_zm_spawn", () -> new ForgeSpawnEggItem((() -> ZPEntities.zp_miner_zombie_entity.get()), 0xA63B36, 0x7C9F65, new Item.Properties())
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_spawns_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_SPAWN_EGG);
        }).registryObject();

        ZPItems.dog_zm_spawn = regSupplier.register("dog_zm_spawn", () -> new ForgeSpawnEggItem((() -> ZPEntities.zp_dog_zombie_entity.get()), 0xC8C8C8, 0xDD0205, new Item.Properties())
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_spawns_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_SPAWN_EGG);
        }).registryObject();
    }
}
