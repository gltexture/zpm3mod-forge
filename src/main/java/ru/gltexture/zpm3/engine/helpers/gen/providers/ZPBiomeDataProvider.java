package ru.gltexture.zpm3.engine.helpers.gen.providers;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.BiomeData;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Deprecated
public class ZPBiomeDataProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_CARVER, Carvers::bootstrap)
            .add(Registries.BIOME, BiomeData::bootstrap);

    public ZPBiomeDataProvider(PackOutput output, CompletableFuture<net.minecraft.core.HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of("minecraft", ZombiePlague3.MOD_ID));
    }
}