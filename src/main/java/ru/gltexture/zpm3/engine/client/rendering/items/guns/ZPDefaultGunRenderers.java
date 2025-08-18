package ru.gltexture.zpm3.engine.client.rendering.items.guns;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.basic.ZPDefaultGunMuzzleflashFX;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.basic.ZPDefaultGunRecoilXF;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.basic.ZPDefaultGunParticlesFX;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.fx.IZPGunMuzzleflashFX;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.fx.IZPGunRecoilFX;
import ru.gltexture.zpm3.engine.client.rendering.items.guns.fx.IZPGunParticlesFX;

public abstract class ZPDefaultGunRenderers {
    public static final @NotNull IZPGunRecoilFX defaultRecoilFXUniversal = ZPDefaultGunRecoilXF.create();
    public static final @NotNull IZPGunMuzzleflashFX defaultMuzzleflashFXUniversal = ZPDefaultGunMuzzleflashFX.create();
    public static final @NotNull IZPGunParticlesFX defaultSmokeFX = ZPDefaultGunParticlesFX.create();

    private ZPDefaultGunRenderers() {
    }

    public static final @NotNull ZPDefaultPistolRenderer defaultPistolRenderer = ZPDefaultPistolRenderer.create();

    public static void init() {
        ZPClientCallbacksManager.INSTANCE.addResourceDependentObjectCallback(ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal);
        ZPClientCallbacksManager.INSTANCE.addGunShotCallback(ZPDefaultGunRenderers.gunShotCallback());
        ZPClientCallbacksManager.INSTANCE.addClientTickCallback(ZPDefaultGunRenderers.defaultRecoilFXUniversal);
    }

    private static ZPClientCallbacks.ZPGunShotCallback gunShotCallback() {
        return ((player, baseGun, itemStack, gunFXData) ->  {
            ZPDefaultGunRenderers.defaultMuzzleflashFXUniversal.triggerRecoil(baseGun, gunFXData);
            ZPDefaultGunRenderers.defaultRecoilFXUniversal.triggerRecoil(baseGun, gunFXData);
            ZPDefaultGunRenderers.defaultSmokeFX.triggerSmoke(player, baseGun, gunFXData);
        });
    }
}