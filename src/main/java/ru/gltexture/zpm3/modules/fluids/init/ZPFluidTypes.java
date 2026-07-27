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

package ru.gltexture.zpm3.modules.fluids.init;

import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.fluids.data.AcidFluidType;
import ru.gltexture.zpm3.modules.fluids.data.ToxicFluidType;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;

public class ZPFluidTypes extends ZPRegistry<FluidType> implements IZPCollectRegistryObjects {
    public static RegistryObject<AcidFluidType> acid_fluid_type;
    public static RegistryObject<ToxicFluidType> toxic_fluid_type;

    public ZPFluidTypes() {
        super(ZPRegistryConveyor.Target.FLUID_TYPE);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<FluidType> regSupplier) {
        ZPFluidTypes.acid_fluid_type = regSupplier.register("acid_fluid_type", AcidFluidType::new).end();
        ZPFluidTypes.toxic_fluid_type = regSupplier.register("toxic_fluid_type", ToxicFluidType::new).end();
    }

    @Override
    public void preProcessing() {
        super.preProcessing();
    }

    @Override
    protected void postRegister(String name, RegistryObject<FluidType> object) {
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}