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

package ru.gltexture.zpm3.engine.client.rendering.imgui.manager;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import ru.gltexture.zpm3.engine.client.rendering.imgui.interfaces.IZPImGuiInterface;

import java.util.Set;

@OnlyIn(Dist.CLIENT)
public interface IZPImGuiInterfacesManager {
    void addRenderableInterface(@NotNull IZPImGuiInterface renderableInterface);
    void removeRenderableInterface(@NotNull IZPImGuiInterface renderableInterface);
    @NotNull @Unmodifiable Set<IZPImGuiInterface> getRenderableInterfaces();
}