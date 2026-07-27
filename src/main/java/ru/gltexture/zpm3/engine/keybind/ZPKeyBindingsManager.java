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

package ru.gltexture.zpm3.engine.keybind;

import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ZPKeyBindingsManager {
    private final List<KeyMapping> keyMappingList;

    public ZPKeyBindingsManager() {
        this.keyMappingList = new ArrayList<>();
    }

    public abstract void init();

    public List<KeyMapping> getKeyMappingList() {
        return this.keyMappingList;
    }

    protected KeyMapping addKeyBinding(@NotNull KeyMapping keyMapping) {
        this.getKeyMappingList().add(keyMapping);
        return keyMapping;
    }
}
