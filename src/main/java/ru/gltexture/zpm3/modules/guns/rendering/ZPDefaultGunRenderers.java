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
import ru.gltexture.zpm3.engine.client.rendering.IZPClientManager;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.rendering.callbacks.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.modules.guns.rendering.fx.*;


@Deprecated(forRemoval = true)
public abstract class  ZPDefaultGunRenderers {
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

        ((ZPClientCallbacksManager) ZombiePlague3.getClientManager().getCallbacksManager()).addGunShotCallback(ZPDefaultGunRenderers.defaultPistolRenderer);
        ((ZPClientCallbacksManager) ZombiePlague3.getClientManager().getCallbacksManager()).addGunShotCallback(ZPDefaultGunRenderers.defaultShutterRifleRenderer);
        ((ZPClientCallbacksManager) ZombiePlague3.getClientManager().getCallbacksManager()).addGunShotCallback(ZPDefaultGunRenderers.defaultRifleRenderer);

        ((ZPClientCallbacksManager) ZombiePlague3.getClientManager().getCallbacksManager()).addGunReloadStartCallback(ZPDefaultGunRenderers.defaultPistolRenderer);
        ((ZPClientCallbacksManager) ZombiePlague3.getClientManager().getCallbacksManager()).addGunReloadStartCallback(ZPDefaultGunRenderers.defaultShutterRifleRenderer);
        ZombiePlague3.getClientManager().getCallbacksManager().addGunReloadStartCallback(ZPDefaultGunRenderers.defaultRifleRenderer);
    }

    private static void initFxCallbacks(IZPGunFX gunFX) {
        if (gunFX instanceof ZPClientCallbacks.ZPGunReloadStartCallback callback) {
            ((ZPClientCallbacksManager) ZombiePlague3.getClientManager().getCallbacksManager()).addGunReloadStartCallback(callback);
        }
        if (gunFX instanceof ZPClientCallbacks.ZPGunShotCallback callback) {
            ((ZPClientCallbacksManager) ZombiePlague3.getClientManager().getCallbacksManager()).addGunShotCallback(callback);
        }
        if (gunFX instanceof IZPClientManager.ResourceLifecycleListener callback) {
            ZombiePlague3.getClientManager().registerResourceLifecycleListener(callback);
        }
        if (gunFX instanceof ZPClientCallbacks.ZPClientTickCallback callback) {
            ((ZPClientCallbacksManager) ZombiePlague3.getClientManager().getCallbacksManager()).addClientTickCallback(callback);
        }
    }
}