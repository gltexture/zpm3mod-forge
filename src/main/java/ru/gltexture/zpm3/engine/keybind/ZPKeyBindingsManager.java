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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class ZPKeyBindingsManager {
    private final List<KeyMapping> keyMappingList;

    public ZPKeyBindingsManager() {
        this.keyMappingList = new ArrayList<>();
    }

    public abstract void init();

    public @Unmodifiable @NotNull List<KeyMapping> getKeyMappingList() {
        return Collections.unmodifiableList(this.keyMappingList);
    }

    protected final KeyMapping addKeyBinding(@NotNull KeyMapping keyMapping) {
        this.keyMappingList.add(keyMapping);
        return keyMapping;
    }
}
