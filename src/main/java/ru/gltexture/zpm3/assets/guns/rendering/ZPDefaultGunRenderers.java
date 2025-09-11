package ru.gltexture.zpm3.assets.guns.rendering;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunRecoilFX;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunParticlesFX;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunReloadingFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunMuzzleflashFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunRecoilFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunParticlesFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunReloadingFX;

public abstract class ZPDefaultGunRenderers {
    public static final @NotNull IZPGunRecoilFX defaultRecoilFXUniversal = ZPDefaultGunRecoilFX.create();
    public static final @NotNull IZPGunMuzzleflashFX defaultMuzzleflashFXUniversal = ZPDefaultGunMuzzleflashFX.create();
    public static final @NotNull IZPGunReloadingFX defaultReloadingFXUniversal = ZPDefaultGunReloadingFX.create();
    public static final @NotNull IZPGunParticlesFX defaultParticlesFX = ZPDefaultGunParticlesFX.create();

    private ZPDefaultGunRenderers() {
    }

    public static final @NotNull ZPDefaultPistolRenderer defaultPistolRenderer = ZPDefaultPistolRenderer.create();

    public static void init() {
        ZPClientCallbacksManager.INSTANCE.addResourceDependentObjectCallback(ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        ZPClientCallbacksManager.INSTANCE.addGunShotCallback(ZPDefaultGunRenderers.gunShotCallback());
        ZPClientCallbacksManager.INSTANCE.addGunReloadStartCallback(ZPDefaultGunRenderers.gunReloadStartCallback());
        ZPClientCallbacksManager.INSTANCE.addClientTickCallback(ZPDefaultGunRenderers.defaultRecoilFXUniversal);
        ZPClientCallbacksManager.INSTANCE.addClientTickCallback(ZPDefaultGunRenderers.defaultReloadingFXUniversal);
        ZPClientCallbacksManager.INSTANCE.addClientTickCallback(ZPDefaultGunRenderers.defaultParticlesFX);
    }

    private static ZPClientCallbacks.ZPGunShotCallback gunShotCallback() {
        return ((player, baseGun, itemStack, gunFXData) ->  {
            ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal.triggerMuzzleflash(player, baseGun, itemStack, gunFXData);
            ZPDefaultGunRenderers.defaultRecoilFXUniversal.triggerRecoil(player, baseGun, itemStack, gunFXData);
            ZPDefaultGunRenderers.defaultParticlesFX.triggerParticles(player, baseGun, itemStack, gunFXData);
        });
    }

    private static ZPClientCallbacks.ZPGunReloadStartCallback gunReloadStartCallback() {
        return (ZPDefaultGunRenderers.defaultReloadingFXUniversal::triggerReloadingStart);
    }
}