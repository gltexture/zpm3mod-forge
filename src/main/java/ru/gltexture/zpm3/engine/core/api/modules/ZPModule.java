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

package ru.gltexture.zpm3.engine.core.api.modules;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleClientSetupContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModuleInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePostInitContext;
import ru.gltexture.zpm3.engine.core.api.modules.context.IModulePreInitContext;

public abstract class ZPModule {
    private final ZPModuleData zpModuleData;

    public ZPModule(@NotNull final ZPModuleData zpModuleData) {
        this.zpModuleData = zpModuleData;
    }


    protected ZPModule() {
        this.zpModuleData = null;
    }


    public abstract void commonSetup();

    public abstract void commonShutdown();

    @OnlyIn(Dist.CLIENT)
    public abstract void clientSetup(@NotNull IModuleClientSetupContext context);

    @OnlyIn(Dist.CLIENT)
    public abstract void clientShutDown();

    //@Deprecated
    //public void setupMixins(@NotNull ZombiePlague3.IMixinEntry mixinEntry) { }

    public abstract void preInitialize(@NotNull IModulePreInitContext context);
    public abstract void initialize(@NotNull IModuleInitContext context);
    public abstract void postInitialize(@NotNull IModulePostInitContext context);

    public final ZPModuleData getModuleData() {
        return this.zpModuleData;
    }

    @Override
    public final String toString() {
        return "Module: " + this.getModuleData();
    }
}
