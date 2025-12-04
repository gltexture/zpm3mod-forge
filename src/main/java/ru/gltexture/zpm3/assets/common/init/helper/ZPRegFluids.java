package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPBlocks;
import ru.gltexture.zpm3.assets.common.init.ZPFluidTypes;
import ru.gltexture.zpm3.assets.common.init.ZPFluids;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public abstract class ZPRegFluids {
    public static final ForgeFlowingFluid.Properties ACID_PROPERTIES = new ForgeFlowingFluid.Properties(() -> ZPFluidTypes.acid_fluid_type.get(), () -> ZPFluids.acid_fluid.get(), () -> ZPFluids.acid_flowing_fluid.get())
            .bucket(() -> ZPItems.acid_bucket.get()).block(() -> ZPBlocks.acid_block.get());

    public static final ForgeFlowingFluid.Properties TOXIC_PROPERTIES = new ForgeFlowingFluid.Properties(() -> ZPFluidTypes.toxic_fluid_type.get(), () -> ZPFluids.toxic_fluid.get(), () -> ZPFluids.toxic_flowing_fluid.get())
            .bucket(() -> ZPItems.toxicwater_bucket.get()).block(() -> ZPBlocks.toxic_block.get());

    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Fluid> regSupplier) {
        ZPFluids.acid_fluid = regSupplier.register("acid_fluid", () -> new ForgeFlowingFluid.Source(ZPRegFluids.ACID_PROPERTIES))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.fluids().setFluidRenderLayer(e::get, RenderType.translucent());
                    });
                    utils.fluids().addTagToFluid(e, FluidTags.WATER);
                }).end();
        ZPFluids.acid_flowing_fluid = regSupplier.register("acid_flowing_fluid", () -> new ForgeFlowingFluid.Flowing(ZPRegFluids.ACID_PROPERTIES))
                .afterCreated((e, utils) -> {
                    ZPUtility.sides().onlyClient(() -> {
                        utils.fluids().setFluidRenderLayer(e::get, RenderType.translucent());
                    });
                    utils.fluids().addTagToFluid(e, FluidTags.WATER);
                }).end();

        ZPFluids.toxic_fluid = regSupplier.register("toxic_fluid", () -> new ForgeFlowingFluid.Source(ZPRegFluids.TOXIC_PROPERTIES))
                .afterCreated((e, utils) -> {
                    utils.fluids().addTagToFluid(e, FluidTags.WATER);
                }).end();
        ZPFluids.toxic_flowing_fluid = regSupplier.register("toxic_flowing_fluid", () -> new ForgeFlowingFluid.Flowing(ZPRegFluids.TOXIC_PROPERTIES))
                .afterCreated((e, utils) -> {
                    utils.fluids().addTagToFluid(e, FluidTags.WATER);
                }).end();
    }
}