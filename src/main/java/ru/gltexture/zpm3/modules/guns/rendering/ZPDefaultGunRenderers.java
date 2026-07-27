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

package ru.gltexture.zpm3.modules.guns.rendering;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.guns.rendering.fx.*;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;

@Deprecated(forRemoval = true)
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