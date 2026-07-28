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

package ru.gltexture.zpm3.modules.worldgen;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.worldgen.archiver.ZPMapArchivedRegistry;

public class ZPWorldGenModule extends ZPModule {
    public ZPWorldGenModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPWorldGenModule() {
    }

    @Override
    public void fml_commonSetupEvent() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void fml_clientSetupEvent() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {

    }

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        ZPUtility.sides().onlyClient(() -> {
           // moduleEntry.addMinecraftEventClass(ZPRenderWorldEventWithPickUpCheck.class);
        });

      //  moduleEntry.addMinecraftEventClass(ZPPlayerGunCancelInterEvent.class);
    }

    @Override
    public void preInitialize() {
        ZPUtility.sides().onlyClient(() -> {
           // ZPMapArchivedRegistry.registerZpArchivedMap(ZombiePlague3.MOD_ID(), new ZPPath(ZPMapArchivedRegistry.MAPS_DIR, "zombie_city").getFullPath());
        });
      //  ZPUtility.sides().onlyClient(() -> {
      //      ZombiePlague3.registerKeyBindings(new ZPPickUpKeyBindings());
      //  });
    }

    @Override
    public void postInitialize() {
    }
}
