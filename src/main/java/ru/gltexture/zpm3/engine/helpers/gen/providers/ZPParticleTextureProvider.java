package ru.gltexture.zpm3.engine.helpers.gen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.service.Pair;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.*;

public class ZPParticleTextureProvider implements DataProvider {
    public static final Map<RegistryObject<? extends ParticleType<?>>, Pair<String, Integer>> particlePairs = new HashMap<>();
    private final DataGenerator generator;
    private final String modId;

    public ZPParticleTextureProvider(DataGenerator generator, String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    public static void addParticlesTexturesData(@NotNull RegistryObject<? extends ParticleType<?>> typeRegistryObject, @NotNull String texturesLink, int arraySize) {
        ZPParticleTextureProvider.particlePairs.put(typeRegistryObject, new Pair<>(texturesLink, arraySize));
    }

    private static void clear() {
        ZPParticleTextureProvider.particlePairs.clear();
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput pOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        ZPParticleTextureProvider.particlePairs.forEach((particleId, pair) -> {
            JsonObject json = new JsonObject();
            JsonArray array = new JsonArray();

            String baseName = pair.first();
            int maxIndex = pair.second();

            for (int i = maxIndex - 1; i >= 0; i--) {
                array.add(baseName + i);
            }

            json.add("textures", array);

            String name = Objects.requireNonNull(particleId.getId()).getPath();
            Path path = generator.getPackOutput().getOutputFolder().resolve("assets/" + this.modId + "/particles/" + name + ".json");

            futures.add(DataProvider.saveStable(pOutput, json, path));
        });

        ZPParticleTextureProvider.clear();
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public @NotNull String getName() {
        return "Particle Textures";
    }
}