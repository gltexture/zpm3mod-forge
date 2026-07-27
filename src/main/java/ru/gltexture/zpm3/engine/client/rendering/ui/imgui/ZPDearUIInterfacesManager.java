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

package ru.gltexture.zpm3.engine.client.rendering.ui.imgui;

import com.mojang.blaze3d.platform.Window;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.rendering.ui.imgui.interfaces.DearUIInterface;

import java.util.HashSet;
import java.util.Set;

public class ZPDearUIInterfacesManager {
    private final Set<DearUIInterface> dearUIInterfaceSet;
    private final ZPDearUIRenderer dearUIRenderer;

    public ZPDearUIInterfacesManager(@NotNull ZPDearUIRenderer zpDearUIRenderer) {
        this.dearUIInterfaceSet = new HashSet<>();
        this.dearUIRenderer = zpDearUIRenderer;
    }

    public void renderAll(@NotNull Window window, float renderTicking) {
        this.getDearUIRenderer().onRender(window, this.getDearUIInterfaceSet(), renderTicking);
    }

    public void removeInterface(@NotNull DearUIInterface dearUIInterface) {
        this.getDearUIInterfaceSet().remove(dearUIInterface);
    }

    public void addInterface(@NotNull DearUIInterface dearUIInterface) {
        this.getDearUIInterfaceSet().add(dearUIInterface);
    }

    public ZPDearUIRenderer getDearUIRenderer() {
        return this.dearUIRenderer;
    }

    public void clear() {
        this.getDearUIInterfaceSet().clear();
    }

    public Set<DearUIInterface> getDearUIInterfaceSet() {
        return this.dearUIInterfaceSet;
    }
}
