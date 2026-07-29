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

package ru.gltexture.zpm3.modules.armor.events.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.gltexture.zpm3.engine.client.rendering.lightmap.ZPLightMapModifier;
import ru.gltexture.zpm3.engine.core.api.events.ZP3EventHandlerClass;
import ru.gltexture.zpm3.engine.core.api.events.ZombiePlagueEvent;
import ru.gltexture.zpm3.engine.core.api.events.ZPModEventBus;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;

@OnlyIn(Dist.CLIENT)
public class ZPAdjustNightVisionGogglesLightMap implements ZP3EventHandlerClass {
    public static final float NV_GAMM = 2.5f;

    @ZombiePlagueEvent
    public static void lightmapUpdate(ZPModEventBus.PostCalcMinecraftLightMapEvent event) {
        if (Minecraft.getInstance().player != null) {
            if (ZPArmorUtil.isEntityHasNightVisionGoggles(Minecraft.getInstance().player) && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                event.getZpLightMapModifier().add(new ZPLightMapModifier.LightMapModRequest(1.0f, event.getCurrentGAMMA() < ZPAdjustNightVisionGogglesLightMap.NV_GAMM ? ZPAdjustNightVisionGogglesLightMap.NV_GAMM - event.getCurrentGAMMA() : 0.0f));
            }
        }
    }
}
