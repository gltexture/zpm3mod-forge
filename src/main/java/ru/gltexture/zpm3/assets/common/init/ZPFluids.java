package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegFluids;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;

public class ZPFluids extends ZPRegistry<Fluid> implements IZPCollectRegistryObjects {
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