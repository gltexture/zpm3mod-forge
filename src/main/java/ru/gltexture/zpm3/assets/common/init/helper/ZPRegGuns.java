package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.assets.guns.rendering.ZPDefaultGunRenderers;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunParticlesFX;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.item.ZPGunPistol;
import ru.gltexture.zpm3.engine.instances.items.ZPItem;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public abstract class ZPRegGuns {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        {
            ZPItems._makarov = regSupplier.register("_makarov",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).registryObject();

            ZPItems.makarov = regSupplier.register("makarov",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._makarov.get())
                            .setDamage(4)
                            .setDurability(180)
                            .setInaccuracy(2.0f)
                            .setMaxAmmo(8)
                            .setReloadTime(40)
                            .setShootCooldown(4)
                            .setClientRecoil(3.0f)
                            .setReloadSound(() -> ZPSounds.makarov_reload.get())
                            .setFireSound(() -> ZPSounds.makarov_fire.get())
                    )
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).registryObject();
        }

        {
            ZPItems._handmade_pistol = regSupplier.register("_handmade_pistol",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).registryObject();

            ZPItems.handmade_pistol = regSupplier.register("handmade_pistol",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._handmade_pistol.get())
                            .setCustomShotParticlesEmitter(IZPGunParticlesFX.DEFAULT_PARTICLES_EMITTER_SUPER_SMOKY_NO_SHELL)
                            .setDamage(6)
                            .setDurability(60)
                            .setInaccuracy(3.0f)
                            .setMaxAmmo(1)
                            .setReloadTime(50)
                            .setShootCooldown(5)
                            .setClientRecoil(8.0f)
                            .setReloadSound(() -> ZPSounds.handmade_pistol_reload.get())
                            .setFireSound(() -> ZPSounds.handmade_pistol_fire.get())
                    )
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).registryObject();
        }

        {
            ZPItems._colt = regSupplier.register("_colt",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).registryObject();

            ZPItems.colt = regSupplier.register("colt",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._colt.get())
                            .setCustomShotParticlesEmitter(IZPGunParticlesFX.DEFAULT_PARTICLES_EMITTER_NO_SHELL)
                            .setDamage(6)
                            .setDurability(260)
                            .setInaccuracy(1.0f)
                            .setMaxAmmo(6)
                            .setReloadTime(70)
                            .setShootCooldown(5)
                            .setClientRecoil(5.0f)
                            .setReloadSound(() -> ZPSounds.colt_reload.get())
                            .setFireSound(() -> ZPSounds.colt_fire.get())
                    )
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).registryObject();
        }

        {
            ZPItems._m1911 = regSupplier.register("_m1911",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).registryObject();

            ZPItems.m1911 = regSupplier.register("m1911",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._m1911.get())
                            .setDamage(5)
                            .setDurability(220)
                            .setInaccuracy(2.25f)
                            .setMaxAmmo(7)
                            .setReloadTime(40)
                            .setShootCooldown(4)
                            .setClientRecoil(3.8f)
                            .setReloadSound(() -> ZPSounds.m1911_reload.get())
                            .setFireSound(() -> ZPSounds.m1911_fire.get())
                    )
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).registryObject();
        }

        {
            ZPItems._usp = regSupplier.register("_usp",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).registryObject();

            ZPItems.usp = regSupplier.register("usp",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._usp.get())
                            .setDamage(4)
                            .setDurability(310)
                            .setInaccuracy(2.85f)
                            .setMaxAmmo(12)
                            .setReloadTime(60)
                            .setShootCooldown(3)
                            .setClientRecoil(3.2f)
                            .setReloadSound(() -> ZPSounds.usp_reload.get())
                            .setFireSound(() -> ZPSounds.usp_fire.get())
                    )
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).registryObject();
        }

        {
            ZPItems._uzi = regSupplier.register("_uzi",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).registryObject();

            ZPItems.uzi = regSupplier.register("uzi",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._uzi.get())
                            .setDamage(3)
                            .setAuto(true)
                            .setDurability(400)
                            .setInaccuracy(3.25f)
                            .setMaxAmmo(30)
                            .setReloadTime(60)
                            .setShootCooldown(2)
                            .setClientRecoil(1.5f)
                            .setReloadSound(() -> ZPSounds.uzi_reload.get())
                            .setFireSound(() -> ZPSounds.uzi_fire.get())
                    )
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).registryObject();
        }

        {
            ZPItems._deagle = regSupplier.register("_deagle",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).registryObject();

            ZPItems.deagle = regSupplier.register("deagle",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._deagle.get())
                            .setDamage(7)
                            .setAuto(true)
                            .setDurability(300)
                            .setInaccuracy(2.25f)
                            .setMaxAmmo(7)
                            .setReloadTime(80)
                            .setShootCooldown(5)
                            .setClientRecoil(6.0f)
                            .setReloadSound(() -> ZPSounds.deagle_reload.get())
                            .setFireSound(() -> ZPSounds.deagle_fire.get())
                    )
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).registryObject();

            ZPItems.golden_deagle = regSupplier.register("golden_deagle",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._deagle.get())
                            .setDamage(7)
                            .setAuto(true)
                            .setDurability(500)
                            .setInaccuracy(2.25f)
                            .setMaxAmmo(9)
                            .setReloadTime(80)
                            .setShootCooldown(5)
                            .setClientRecoil(6.0f)
                            .setReloadSound(() -> ZPSounds.deagle_reload.get())
                            .setFireSound(() -> ZPSounds.deagle_fire.get())
                    )
            ).postConsume(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).registryObject();
        }
    }
}