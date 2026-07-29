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

package ru.gltexture.zpm3.modules.fluids.init.helper;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlocks;
import ru.gltexture.zpm3.modules.common.init.ZPTags;
import ru.gltexture.zpm3.modules.fluids.init.ZPFluidTypes;
import ru.gltexture.zpm3.modules.fluids.init.ZPFluids;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.ZPMeleeThrowableToolsItems;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public abstract class ZPRegFluids {
    public static final ForgeFlowingFluid.Properties ACID_PROPERTIES = new ForgeFlowingFluid.Properties(() -> ZPFluidTypes.acid_fluid_type.get(), () -> ZPFluids.acid_fluid.get(), () -> ZPFluids.acid_flowing_fluid.get())
            .bucket(() -> ZPMeleeThrowableToolsItems.acid_bucket.get()).block(() -> ZPBlocks.acid_block.get());

    public static final ForgeFlowingFluid.Properties TOXIC_PROPERTIES = new ForgeFlowingFluid.Properties(() -> ZPFluidTypes.toxic_fluid_type.get(), () -> ZPFluids.toxic_fluid.get(), () -> ZPFluids.toxic_flowing_fluid.get())
            .bucket(() -> ZPMeleeThrowableToolsItems.toxicwater_bucket.get()).block(() -> ZPBlocks.toxic_block.get());

    public static void init(@NotNull ZPCommonRegistry.ZPRegSupplier<Fluid> regSupplier) {
        ZPFluids.acid_fluid = regSupplier.register("acid_fluid", () -> new ForgeFlowingFluid.Source(ZPRegFluids.ACID_PROPERTIES))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.fluids().setFluidRenderLayer(e::get, RenderType.translucent());
                    });
                    utils.fluids().addTagToFluid(e, FluidTags.WATER);
                    utils.fluids().addTagToFluid(e, ZPTags.F_ACID_PROPERTIES);
                }).end();

        ZPFluids.acid_flowing_fluid = regSupplier.register("acid_flowing_fluid", () -> new ForgeFlowingFluid.Flowing(ZPRegFluids.ACID_PROPERTIES))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.fluids().setFluidRenderLayer(e::get, RenderType.translucent());
                    });
                    utils.fluids().addTagToFluid(e, FluidTags.WATER);
                    utils.fluids().addTagToFluid(e, ZPTags.F_ACID_PROPERTIES);
                }).end();

        ZPFluids.toxic_fluid = regSupplier.register("toxic_fluid", () -> new ForgeFlowingFluid.Source(ZPRegFluids.TOXIC_PROPERTIES))
                .afterCreated((e, utils) -> {
                    utils.fluids().addTagToFluid(e, FluidTags.WATER);
                    utils.fluids().addTagToFluid(e, ZPTags.F_TOXIC_PROPERTIES);
                }).end();

        ZPFluids.toxic_flowing_fluid = regSupplier.register("toxic_flowing_fluid", () -> new ForgeFlowingFluid.Flowing(ZPRegFluids.TOXIC_PROPERTIES))
                .afterCreated((e, utils) -> {
                    utils.fluids().addTagToFluid(e, FluidTags.WATER);
                    utils.fluids().addTagToFluid(e, ZPTags.F_TOXIC_PROPERTIES);
                }).end();
    }
}