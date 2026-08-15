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

package ru.gltexture.zpm3.engine.client.rendering.postfx;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import ru.gltexture.zpm3.engine.client.rendering.IZPClientManager;
import ru.gltexture.zpm3.engine.client.rendering.postfx.processors.*;

import java.util.Set;

public interface IZPPostFXChain extends IZPClientManager.ResourceLifecycleListener, IZPClientManager.ResourceReloadListener {
    ZPPostFXProcessor SAMPLE = new ZPSamplePostFXProcessor(1000);
    ZPPostFXProcessor INFECTION = new ZPInfectionPostFXProcessor(2000);
    ZPPostFXProcessor ADRENALINE = new ZPAdrenalinePostFXProcessor(3000);
    ZPPostFXProcessor BETTERVIS = new ZPBetterVisionPostFXProcessor(400);
    ZPPostFXProcessor NIGHTVIS = new ZPNightVisPostFXProcessor(5000);
    ZPPostFXProcessor MASK = new ZPMaskVignettePostFXProcessor(6000);
    ZPPostFXProcessor RADIATION = new ZPRadiationPostFXProcessor(7000);
    ZPPostFXProcessor ACID = new ZPAcidPostFXProcessor(8000);

    void addProcessor(@NotNull ZPPostFXProcessor processor);
    void removeProcessor(@NotNull ZPPostFXProcessor processor);

    @NotNull @Unmodifiable Set<ZPPostFXProcessor> getProcessors();
}