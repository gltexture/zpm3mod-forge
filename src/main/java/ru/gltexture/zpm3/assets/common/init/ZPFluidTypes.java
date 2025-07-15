package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.common.fluids.AcidFluidType;
import ru.gltexture.zpm3.assets.common.fluids.ToxicFluidType;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;

import java.util.function.Consumer;

public class ZPFluidTypes extends ZPRegistry<FluidType> implements IZPCollectRegistryObjects {
    public static RegistryObject<AcidFluidType> acid_fluid_type;
    public static RegistryObject<ToxicFluidType> toxic_fluid_type;

    public ZPFluidTypes() {
        super(ForgeRegistries.Keys.FLUID_TYPES, ZPRegistryConveyor.Target.FLUID_TYPE);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<FluidType> regSupplier) {
        ZPFluidTypes.acid_fluid_type = regSupplier.register("acid_fluid_type", AcidFluidType::new).registryObject();
        ZPFluidTypes.toxic_fluid_type = regSupplier.register("toxic_fluid_type", ToxicFluidType::new).registryObject();
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