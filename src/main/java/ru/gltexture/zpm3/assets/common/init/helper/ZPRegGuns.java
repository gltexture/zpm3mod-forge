package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.instances.guns.ZPBaseGun;
import ru.gltexture.zpm3.engine.instances.guns.ZPGunPistol;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public abstract class ZPRegGuns {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPItems.makarov = regSupplier.register("makarov",
                () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties().setClientRecoil(3.0f).setFireSound(() -> ZPSounds.makarov_fire.get()))
        ).postConsume(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer);
        }).registryObject();
    }
}