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

package ru.gltexture.zpm3.engine.core.api.addons.impl;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.api.addons.context.IAddonClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.addons.context.IAddonInitContext;
import ru.gltexture.zpm3.engine.core.api.addons.context.IAddonPostInitContext;
import ru.gltexture.zpm3.engine.core.api.addons.context.IAddonPreInitContext;

public interface IZPAddonImpl {
    @OnlyIn(Dist.CLIENT)
    void clientSetup(@NotNull IAddonClientSetupContext context);

    @OnlyIn(Dist.CLIENT)
    void clientShutDown();

    void preInitialize(@NotNull IAddonPreInitContext context);
    void initialize(@NotNull IAddonInitContext context);
    void postInitialize(@NotNull IAddonPostInitContext context);
}
