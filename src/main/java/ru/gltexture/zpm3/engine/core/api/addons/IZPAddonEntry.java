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

package ru.gltexture.zpm3.engine.core.api.addons;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.api.addons.impl.IZPAddonImpl;
import ru.gltexture.zpm3.engine.core.config.ZPConfigConstantsClass;

public interface IZPAddonEntry {
    default void registerConfig(@NotNull String confName, @NotNull Class<? extends ZPConfigConstantsClass> clazz) {
        ZombiePlague3.processAddonConfiguration(this, confName, clazz);
    }

    @NotNull IZPAddonImpl ZP3AddonImpl();
}