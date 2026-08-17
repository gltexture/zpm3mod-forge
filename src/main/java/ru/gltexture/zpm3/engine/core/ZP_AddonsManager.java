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

package ru.gltexture.zpm3.engine.core;

import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.api.addons.IZPAddonEntry;
import ru.gltexture.zpm3.engine.exceptions.ZPAPIException;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;

import java.util.*;

public class ZP_AddonsManager {
    static final ZP_AddonsManager INSTANCE = new ZP_AddonsManager();
    private final TreeSet<ZPAddonInfo> registeredAddons;

    public ZP_AddonsManager() {
        this.registeredAddons = new TreeSet<>(Comparator.comparing(e -> e.modId));
    }

    void register(@NotNull final IZPAddonEntry zpAddon) throws ZPAPIException {
        final Mod modAnnotation = zpAddon.getClass().getAnnotation(Mod.class);
        if (modAnnotation == null) {
            throw new ZPRuntimeException("Addon entry " + zpAddon.getClass().getName() + " must be annotated with @Mod");
        }
        final String modId = modAnnotation.value();
        this.registeredAddons.add(new ZPAddonInfo(zpAddon, modId));
    }

    /*
    public void processIMC(InterModProcessEvent event) {
        event.getIMCStream().filter(message -> message.method().equals(ZPAddonsUtil.REG))
                .forEach(message -> {
                    Object value = message.messageSupplier().get();
                    if (value instanceof IZPAddonEntry addon) {
                        this.register(addon, message.senderModId());
                    }
                });
    }
*/


    public String getAddonId(@NotNull IZPAddonEntry addonEntry) {
        return this.registeredAddons.stream().filter(info -> info.zpAddon() == addonEntry).map(ZPAddonInfo::modId).findFirst().orElseThrow(ZPAPIException::new);
    }

    public Set<ZPAddonInfo> getRegisteredAddons() {
        return Collections.unmodifiableSet(this.registeredAddons);
    }

    public record ZPAddonInfo(@NotNull IZPAddonEntry zpAddon, @NotNull String modId) {
        @Override
        public @NotNull String toString() {
            return this.modId;
        }
    }
}
