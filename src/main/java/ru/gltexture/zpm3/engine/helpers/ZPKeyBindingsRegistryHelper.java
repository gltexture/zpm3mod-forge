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

package ru.gltexture.zpm3.engine.helpers;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.keybind.ZPKeyBindingsManager;

import java.util.HashSet;
import java.util.Set;

public abstract class ZPKeyBindingsRegistryHelper {
    private static Set<ZPKeyBindingsManager> keyBindings = new HashSet<>();

    public static void clear() {
        ZPKeyBindingsRegistryHelper.keyBindings = null;
    }

    public static void addNewKeybinding(@NotNull ZPKeyBindingsManager zpBaseKeyBindings) {
        ZPKeyBindingsRegistryHelper.keyBindings.add(zpBaseKeyBindings);
    }

    public static Set<ZPKeyBindingsManager> getAllKeyBindings() {
        return ZPKeyBindingsRegistryHelper.keyBindings;
    }
}
