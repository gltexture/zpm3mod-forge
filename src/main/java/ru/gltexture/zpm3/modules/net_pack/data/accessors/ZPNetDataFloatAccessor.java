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

package ru.gltexture.zpm3.modules.net_pack.data.accessors;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.net_pack.data.codecs.ZPNetDataCodec;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetDataVar;

import java.util.function.Supplier;

public final class ZPNetDataFloatAccessor extends ZPNetDataAccessor<Float> {
    public ZPNetDataFloatAccessor(@NotNull ResourceLocation resourceLocation) {
        super(resourceLocation, ZPNetDataCodec.FLOAT);
    }

    @Override
    public @NotNull Supplier<ZPNetDataVar<Float>> createDefault() {
        return () -> ZPNetDataAccessor.DEFAULT_FLOAT;
    }
}