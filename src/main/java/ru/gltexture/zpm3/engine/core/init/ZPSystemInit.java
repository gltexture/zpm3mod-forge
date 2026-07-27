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

package ru.gltexture.zpm3.engine.core.init;

import com.mojang.blaze3d.platform.Window;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public abstract class ZPSystemInit {
    @OnlyIn(Dist.CLIENT)
    public static void client() {
        ZPClientInitManager.INSTANCE.setupRunner((w) -> ZPRenderHelper.INSTANCE.init());
        ZPSystemInit.callbackRuns();
        ZPClientInitManager.INSTANCE.setupRunner((w) -> {
            try {
                ZPClientCallbacksManager.INSTANCE.getOnSetupResourcesCallbacks().forEach(e -> e.setupResources(w));
            } catch (ConcurrentModificationException e) {
                throw new ZPRuntimeException("Tried to create setup callback, during setup processing");
            }
        });

        ZPClientInitManager.INSTANCE.destroyRunner((w) -> {
            try {
                ZPClientCallbacksManager.INSTANCE.getOnDestroyResourcesCallbacks().forEach(e -> e.destroyResources(w));
            } catch (ConcurrentModificationException e) {
                throw new ZPRuntimeException("Tried to create destroy callback, during destroy processing");
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    private static void callbackRuns() {
        ZPClientInitManager.INSTANCE.setupRunner(ZPClientCallbacksManager.INSTANCE::setupResources);
        ZPClientInitManager.INSTANCE.destroyRunner(ZPClientCallbacksManager.INSTANCE::destroyResources);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientRunSetup(@NotNull Window window) {
        ZPClientInitManager.INSTANCE.runSetup(window);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientRunDestroy(@NotNull Window window) {
        ZPClientInitManager.INSTANCE.runDestroy(window);
    }
}