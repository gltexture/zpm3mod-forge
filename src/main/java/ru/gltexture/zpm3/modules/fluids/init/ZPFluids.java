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

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.modules.fluids.init.helper.ZPRegFluids;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;

public class ZPFluids extends ZPCommonRegistry<Fluid> implements IZPCollectRegistryObjects {
    public static RegistryObject<ForgeFlowingFluid.Source> acid_fluid;
    public static RegistryObject<ForgeFlowingFluid.Flowing> acid_flowing_fluid;

    public static RegistryObject<ForgeFlowingFluid.Source> toxic_fluid;
    public static RegistryObject<ForgeFlowingFluid.Flowing> toxic_flowing_fluid;

    public ZPFluids() {
        super(ZPRegistryConveyor.Target.FLUID);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Fluid> regSupplier) {
      //  this.startCollectingInto("fluids");
        ZPRegFluids.init(regSupplier);
       // this.stopCollecting();
    }

    @Override
    public void preProcessing() {
        super.preProcessing();
    }

    @Override
    protected void postRegister(String name, RegistryObject<Fluid> object) {
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