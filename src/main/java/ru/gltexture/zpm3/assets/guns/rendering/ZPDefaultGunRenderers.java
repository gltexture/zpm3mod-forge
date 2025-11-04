package ru.gltexture.zpm3.assets.guns.rendering;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.guns.rendering.basic.*;
import ru.gltexture.zpm3.assets.guns.rendering.fx.*;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;

public abstract class ZPDefaultGunRenderers {
    public static final @NotNull IZPGunRecoilFX defaultRecoilFXUniversal = ZPDefaultGunRecoilFX.create();
    public static final @NotNull IZPGunMuzzleflashFX defaultMuzzleflashFXUniversal = ZPDefaultGunMuzzleflashFX.create();
    public static final @NotNull IZPGunReloadingFX defaultReloadingFXUniversal = ZPDefaultGunReloadingFX.create();
    public static final @NotNull IZPGunParticlesFX defaultParticlesFXUniversal = ZPDefaultGunParticlesFX.create();
    public static final @NotNull IZPGunGunShutterFX defaultShotgunShutterFXUniversal = ZPDefaultGunShutterFX.create();

    private ZPDefaultGunRenderers() {
    }

    public static final @NotNull ZPDefaultPistolRenderer defaultPistolRenderer = ZPDefaultPistolRenderer.create();
    public static final @NotNull ZPDefaultRifleWithShutterRenderer defaultShutterRifleRenderer = ZPDefaultRifleWithShutterRenderer.create();
    public static final @NotNull ZPDefaultRifleRenderer defaultRifleRenderer = ZPDefaultRifleRenderer.create();

    public static void init() {
        ZPDefaultGunRenderers.initFxCallbacks(ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        ZPDefaultGunRenderers.initFxCallbacks(ZPDefaultGunRenderers.defaultRecoilFXUniversal);
        ZPDefaultGunRenderers.initFxCallbacks(ZPDefaultGunRenderers.defaultReloadingFXUniversal);
        ZPDefaultGunRenderers.initFxCallbacks(ZPDefaultGunRenderers.defaultParticlesFXUniversal);
        ZPDefaultGunRenderers.initFxCallbacks(ZPDefaultGunRenderers.defaultShotgunShutterFXUniversal);

        ZPClientCallbacksManager.INSTANCE.addGunShotCallback(ZPDefaultGunRenderers.defaultPistolRenderer);
        ZPClientCallbacksManager.INSTANCE.addGunShotCallback(ZPDefaultGunRenderers.defaultShutterRifleRenderer);
        ZPClientCallbacksManager.INSTANCE.addGunShotCallback(ZPDefaultGunRenderers.defaultRifleRenderer);

        ZPClientCallbacksManager.INSTANCE.addGunReloadStartCallback(ZPDefaultGunRenderers.defaultPistolRenderer);
        ZPClientCallbacksManager.INSTANCE.addGunReloadStartCallback(ZPDefaultGunRenderers.defaultShutterRifleRenderer);
        ZPClientCallbacksManager.INSTANCE.addGunReloadStartCallback(ZPDefaultGunRenderers.defaultRifleRenderer);
    }

    private static void initFxCallbacks(IZPGunFX gunFX) {
        if (gunFX instanceof ZPClientCallbacks.ZPGunReloadStartCallback callback) {
            ZPClientCallbacksManager.INSTANCE.addGunReloadStartCallback(callback);
        }
        if (gunFX instanceof ZPClientCallbacks.ZPGunShotCallback callback) {
            ZPClientCallbacksManager.INSTANCE.addGunShotCallback(callback);
        }
        if (gunFX instanceof ZPClientCallbacks.ZPClientResourceDependentObject callback) {
            ZPClientCallbacksManager.INSTANCE.addResourceDependentObjectCallback(callback);
        }
        if (gunFX instanceof ZPClientCallbacks.ZPClientTickCallback callback) {
            ZPClientCallbacksManager.INSTANCE.addClientTickCallback(callback);
        }
    }
}