package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.assets.guns.item.ZPGunClassicRifle;
import ru.gltexture.zpm3.assets.guns.item.ZPGunShotgun;
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
            ZPItems.admin_pistol = regSupplier.register("admin_pistol",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(null, ZPBaseGun.GunProperties.HeldType.PISTOL)
                            .setDamage(999)
                            .setDurability(9999)
                            .setInaccuracy(0.0f)
                            .setMaxAmmo(9999)
                            .setReloadTime(40)
                            .setShootCooldown(1)
                            .setClientRecoil(0.0f)
                            .setAuto(true)
                            .setReloadSound(() -> ZPSounds.uzi_reload.get())
                            .setFireSound(() -> ZPSounds.usp_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).end();

            ZPItems._makarov = regSupplier.register("_makarov",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).end();

            ZPItems.makarov = regSupplier.register("makarov",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._makarov.get(), ZPBaseGun.GunProperties.HeldType.PISTOL)
                            .setDamage(4)
                            .setDurability(280)
                            .setInaccuracy(1.0f)
                            .setMaxAmmo(8)
                            .setReloadTime(40)
                            .setShootCooldown(4)
                            .setClientRecoil(3.0f)
                            .setReloadSound(() -> ZPSounds.makarov_reload.get())
                            .setFireSound(() -> ZPSounds.makarov_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).end();
        }

        {
            ZPItems._handmade_pistol = regSupplier.register("_handmade_pistol",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).end();

            ZPItems.handmade_pistol = regSupplier.register("handmade_pistol",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._handmade_pistol.get(), ZPBaseGun.GunProperties.HeldType.PISTOL)
                            .setCustomShotParticlesEmitter(IZPGunParticlesFX.DEFAULT_PARTICLES_EMITTER_SUPER_SMOKY_NO_SHELL)
                            .setDamage(8)
                            .setDurability(90)
                            .setInaccuracy(2.0f)
                            .setMaxAmmo(1)
                            .setReloadTime(50)
                            .setShootCooldown(5)
                            .setClientRecoil(8.0f)
                            .setReloadSound(() -> ZPSounds.handmade_pistol_reload.get())
                            .setFireSound(() -> ZPSounds.handmade_pistol_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).end();
        }

        {
            ZPItems._colt = regSupplier.register("_colt",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).end();

            ZPItems.colt = regSupplier.register("colt",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._colt.get(), ZPBaseGun.GunProperties.HeldType.PISTOL)
                            .setCustomShotParticlesEmitter(IZPGunParticlesFX.DEFAULT_PARTICLES_EMITTER_NO_SHELL)
                            .setDamage(7)
                            .setDurability(360)
                            .setInaccuracy(0.5f)
                            .setMaxAmmo(6)
                            .setReloadTime(70)
                            .setShootCooldown(5)
                            .setClientRecoil(5.0f)
                            .setReloadSound(() -> ZPSounds.colt_reload.get())
                            .setFireSound(() -> ZPSounds.colt_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).end();
        }

        {
            ZPItems._m1911 = regSupplier.register("_m1911",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).end();

            ZPItems.m1911 = regSupplier.register("m1911",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._m1911.get(), ZPBaseGun.GunProperties.HeldType.PISTOL)
                            .setDamage(5)
                            .setDurability(320)
                            .setInaccuracy(1.15f)
                            .setMaxAmmo(7)
                            .setReloadTime(40)
                            .setShootCooldown(4)
                            .setClientRecoil(3.8f)
                            .setReloadSound(() -> ZPSounds.m1911_reload.get())
                            .setFireSound(() -> ZPSounds.m1911_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).end();
        }

        {
            ZPItems._usp = regSupplier.register("_usp",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).end();

            ZPItems.usp = regSupplier.register("usp",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._usp.get(), ZPBaseGun.GunProperties.HeldType.PISTOL)
                            .setDamage(4)
                            .setDurability(410)
                            .setInaccuracy(0.85f)
                            .setMaxAmmo(12)
                            .setReloadTime(60)
                            .setShootCooldown(3)
                            .setClientRecoil(3.2f)
                            .setReloadSound(() -> ZPSounds.usp_reload.get())
                            .setFireSound(() -> ZPSounds.usp_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).end();
        }

        {
            ZPItems._uzi = regSupplier.register("_uzi",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).end();

            ZPItems.uzi = regSupplier.register("uzi",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._uzi.get(), ZPBaseGun.GunProperties.HeldType.PISTOL)
                            .setDamage(3)
                            .setAuto(true)
                            .setDurability(600)
                            .setInaccuracy(1.25f)
                            .setMaxAmmo(30)
                            .setReloadTime(60)
                            .setShootCooldown(2)
                            .setClientRecoil(1.5f)
                            .setReloadSound(() -> ZPSounds.uzi_reload.get())
                            .setFireSound(() -> ZPSounds.uzi_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).end();
        }

        {
            ZPItems._deagle = regSupplier.register("_deagle",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).end();

            ZPItems.deagle = regSupplier.register("deagle",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._deagle.get(), ZPBaseGun.GunProperties.HeldType.PISTOL)
                            .setDamage(7)
                            .setDurability(400)
                            .setInaccuracy(0.25f)
                            .setMaxAmmo(7)
                            .setReloadTime(80)
                            .setShootCooldown(5)
                            .setClientRecoil(6.0f)
                            .setReloadSound(() -> ZPSounds.deagle_reload.get())
                            .setFireSound(() -> ZPSounds.deagle_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).end();

            ZPItems.golden_deagle = regSupplier.register("golden_deagle",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._deagle.get(), ZPBaseGun.GunProperties.HeldType.PISTOL)
                            .setDamage(7)
                            .setDurability(800)
                            .setInaccuracy(0.25f)
                            .setMaxAmmo(9)
                            .setReloadTime(80)
                            .setShootCooldown(5)
                            .setClientRecoil(6.0f)
                            .setReloadSound(() -> ZPSounds.deagle_reload.get())
                            .setFireSound(() -> ZPSounds.deagle_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultPistolRenderer, ZPDefaultGunRenderers.defaultPistolRenderer);
            }).end();
        }

        {
            ZPItems._shotgun = regSupplier.register("_shotgun",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).end();

            ZPItems.shotgun = regSupplier.register("shotgun",
                    () -> new ZPGunShotgun(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._shotgun.get(), ZPBaseGun.GunProperties.HeldType.RIFLE)
                            .setDamage(3)
                            .setDurability(180)
                            .setInaccuracy(8.0f)
                            .setMaxAmmo(6)
                            .setReloadTime(15)
                            .setShootCooldown(20)
                            .setClientRecoil(12.5f)
                            .setReloadSound(() -> ZPSounds.shotgun_reload.get())
                            .setFireSound(() -> ZPSounds.shotgun_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultShutterRifleRenderer, ZPDefaultGunRenderers.defaultShutterRifleRenderer);
            }).end();
        }

        {
            ZPItems._mosin = regSupplier.register("_mosin",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).end();

            ZPItems.mosin = regSupplier.register("mosin",
                    () -> new ZPGunClassicRifle(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._mosin.get(), ZPBaseGun.GunProperties.HeldType.RIFLE)
                            .setDamage(10)
                            .setDurability(220)
                            .setInaccuracy(0.05f)
                            .setMaxAmmo(5)
                            .setReloadTime(18)
                            .setShootCooldown(22)
                            .setClientRecoil(9.0f)
                            .setReloadSound(() -> ZPSounds.rifle_shutter.get())
                            .setFireSound(() -> ZPSounds.mosin_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultShutterRifleRenderer, ZPDefaultGunRenderers.defaultShutterRifleRenderer);
            }).end();
        }

        {
            ZPItems._akm = regSupplier.register("_akm",
                    () -> new ZPItem(new Item.Properties().stacksTo(32))
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
            }).end();

            ZPItems.akm = regSupplier.register("akm",
                    () -> new ZPGunPistol(new Item.Properties(), new ZPBaseGun.GunProperties(ZPItems._akm.get(), ZPBaseGun.GunProperties.HeldType.RIFLE)
                            .setDamage(3)
                            .setAuto(true)
                            .setDurability(680)
                            .setInaccuracy(0.8f)
                            .setMaxAmmo(30)
                            .setReloadTime(76)
                            .setShootCooldown(2)
                            .setClientRecoil(1.5f)
                            .setReloadSound(() -> ZPSounds.akm_reload.get())
                            .setFireSound(() -> ZPSounds.akm_fire.get())
                    )
            ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
                utils.items().addItemInTab(e, ZPTabs.zp_guns_tab);
                utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.GUN_ITEMS_DIRECTORY);
                utils.items().setItemRenderer(e, ZPDefaultGunRenderers.defaultRifleRenderer, ZPDefaultGunRenderers.defaultRifleRenderer);
            }).end();
        }
    }
}