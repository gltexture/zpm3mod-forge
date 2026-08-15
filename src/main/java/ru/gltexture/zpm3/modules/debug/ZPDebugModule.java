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

package ru.gltexture.zpm3.modules.debug;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.debug.events.ZPFreeCameraEvents;
import ru.gltexture.zpm3.modules.debug.events.ZPRenderStuffEvent;
import ru.gltexture.zpm3.modules.debug.imgui.ZPImGuiDebugInterface;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Objects;

public class ZPDebugModule extends ZPModule {
    public ZPDebugModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPDebugModule() {
    }

    @Override
    public void fml_commonSetupEvent() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void fml_clientSetupEvent() {
        if (ZombiePlague3.getClientManager().isImGuiValid()) {
            Objects.requireNonNull(ZombiePlague3.getClientManager().getImGuiInterfacesManager()).addRenderableInterface(new ZPImGuiDebugInterface());
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {

    }

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("debug", "ru.gltexture.zpm3.modules.debug.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPCameraMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPInputMixin", ZPSide.CLIENT));
    //}

    public static void addNewLineToDraw(@NotNull ZPRenderStuffEvent.LineRequest lineRequest) {
        ZPRenderStuffEvent.addNewLineToDraw(lineRequest);
    }

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        ZPUtility.sides().onlyClient(() -> {
            moduleEntry.registerForgeEventHandlerClass(ZPFreeCameraEvents.class);
            moduleEntry.registerForgeEventHandlerClass(ZPRenderStuffEvent.class);
        });
    }

    @Override
    public void preInitialize() {

    }

    @Override
    public void postInitialize() {

    }
}
