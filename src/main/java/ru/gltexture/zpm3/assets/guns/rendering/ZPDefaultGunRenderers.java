package ru.gltexture.zpm3.assets.guns.rendering;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunRecoilXF;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunParticlesFX;
import ru.gltexture.zpm3.assets.guns.rendering.basic.ZPDefaultGunReloadingXF;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunMuzzleflashFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunRecoilFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunParticlesFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunReloadingFX;

public abstract class ZPDefaultGunRenderers {
    public static final @NotNull IZPGunRecoilFX defaultRecoilFXUniversal = ZPDefaultGunRecoilXF.create();
    public static final @NotNull IZPGunMuzzleflashFX defaultMuzzleflashFXUniversal = ZPDefaultGunMuzzleflashFX.create();
    public static final @NotNull IZPGunReloadingFX defaultReloadingFXUniversal = ZPDefaultGunReloadingXF.create();

    public static final @NotNull IZPGunParticlesFX defaultSmokeFX = ZPDefaultGunParticlesFX.create();

    private ZPDefaultGunRenderers() {
    }

    public static final @NotNull ZPDefaultPistolRenderer defaultPistolRenderer = ZPDefaultPistolRenderer.create();

    public static void init() {
        ZPClientCallbacksManager.INSTANCE.addResourceDependentObjectCallback(ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        ZPClientCallbacksManager.INSTANCE.addGunShotCallback(ZPDefaultGunRenderers.gunShotCallback());
        ZPClientCallbacksManager.INSTANCE.addGunReloadStartCallback(ZPDefaultGunRenderers.gunReloadStartCallback());
        ZPClientCallbacksManager.INSTANCE.addClientTickCallback(ZPDefaultGunRenderers.defaultRecoilFXUniversal);
        ZPClientCallbacksManager.INSTANCE.addClientTickCallback(ZPDefaultGunRenderers.defaultReloadingFXUniversal);
    }

    private static ZPClientCallbacks.ZPGunShotCallback gunShotCallback() {
        return ((player, baseGun, itemStack, gunFXData) ->  {
            ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal.triggerRecoil(baseGun, gunFXData);
            ZPDefaultGunRenderers.defaultRecoilFXUniversal.triggerRecoil(baseGun, gunFXData);
            ZPDefaultGunRenderers.defaultSmokeFX.triggerSmoke(player, baseGun, gunFXData);
        });
    }

    private static ZPClientCallbacks.ZPGunReloadStartCallback gunReloadStartCallback() {
        return ((player, baseGun, itemStack, gunFXData) ->  {
            ZPDefaultGunRenderers.defaultReloadingFXUniversal.triggerReloadingStart(baseGun, gunFXData);
        });
    }
}