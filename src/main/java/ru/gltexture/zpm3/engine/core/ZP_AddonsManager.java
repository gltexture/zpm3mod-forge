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

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.api.addons.ZPAddon;

import java.util.ArrayList;
import java.util.List;

public class ZP_AddonsManager {
    static final ZP_AddonsManager INSTANCE = new ZP_AddonsManager();
    private final List<ZPAddon> registeredAddons;

    public ZP_AddonsManager() {
        this.registeredAddons = new ArrayList<>();
    }

    public void register(@NotNull final ZPAddon zpAddon) {
        this.registeredAddons.add(zpAddon);
    }

    public List<ZPAddon> getRegisteredAddons() {
        return List.copyOf(this.registeredAddons);
    }
}
