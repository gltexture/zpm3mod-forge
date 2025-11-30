package ru.gltexture.zpm3.assets.common.init;

import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.fluids.AcidFluidType;
import ru.gltexture.zpm3.assets.common.fluids.ToxicFluidType;
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