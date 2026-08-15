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

package ru.gltexture.zpm3.engine.client.init;

import com.mojang.blaze3d.platform.Window;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;

import java.util.ConcurrentModificationException;

public abstract class ZPSystemInit {
    @OnlyIn(Dist.CLIENT)
    public static void client() {
        {
            ZPClientInitManager.INSTANCE.setupRunner(ZombiePlague3.getClientManager().getCallbacksManager()::setup);
            ZPClientInitManager.INSTANCE.setupRunner((w) -> {
                try {
                    ZombiePlague3.getClientManager().getCallbacksManager().getOnSetupResourcesCallbacks().forEach(e -> e.onSetupResources(w));
                } catch (ConcurrentModificationException e) {
                    throw new ZPRuntimeException("Tried to create setup callback, during setup processing");
                }
            });
        }

        {
            ZPClientInitManager.INSTANCE.destroyRunner(ZombiePlague3.getClientManager().getCallbacksManager()::destroy);
            ZPClientInitManager.INSTANCE.destroyRunner((w) -> {
                try {
                    ZombiePlague3.getClientManager().getCallbacksManager().getOnDestroyResourcesCallbacks().forEach(e -> e.onDestroyResources(w));
                } catch (ConcurrentModificationException e) {
                    throw new ZPRuntimeException("Tried to create destroy callback, during destroy processing");
                }
            });
        }
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