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

package ru.gltexture.zpm3.modules.common.init;

import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public class ZPBiomeModifying extends ZPRegistry<BiomeModifier> {
    public ZPBiomeModifying() {
        super(ZPRegistryConveyor.Target.BIOME_MODIFIER);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<BiomeModifier> regSupplier) {
    }

    @Override
    protected void postRegister(String name, RegistryObject<BiomeModifier> object) {
        super.postRegister(name, object);
    }

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}