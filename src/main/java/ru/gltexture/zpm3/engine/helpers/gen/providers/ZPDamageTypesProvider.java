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
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ZPDamageTypesProvider implements DataProvider {
    public static final Set<ZPDamageTypeGenData> damageTypesToGen = new HashSet<>();
    private final DataGenerator generator;
    private final String modId;

    public ZPDamageTypesProvider(DataGenerator generator, String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    public static void addDamageTypeToGen(@NotNull ZPDamageTypeGenData data) {
        ZPDamageTypesProvider.damageTypesToGen.add(data);
    }

    private static void clear() {
        ZPDamageTypesProvider.damageTypesToGen.clear();
    }

    @SuppressWarnings("all")
    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput pOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (ZPDamageTypeGenData data : ZPDamageTypesProvider.damageTypesToGen) {
            JsonObject json = new JsonObject();
            json.addProperty("name", modId + ":" + data.name());
            json.addProperty("bypassesArmor", data.bypassesArmor());
            json.addProperty("isMagic", data.isMagic());
            json.addProperty("isFire", data.isFire());
            json.addProperty("isExplosion", data.isExplosion());
            json.addProperty("exhaustion", data.exhaustion());
            json.addProperty("message_id", "damage." + modId + "." + data.name());
            json.addProperty("scaling", data.scaling());
            Path path = generator.getPackOutput().getOutputFolder().resolve("data/" + modId + "/damage_type/" + data.name() + ".json");
            futures.add(DataProvider.saveStable(pOutput, json, path));
        }

        ZPDamageTypesProvider.clear();
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public @NotNull String getName() {
        return "Damage Types";
    }

    // "never", "always", "difficulty_scaled"
    public record ZPDamageTypeGenData(String name, boolean bypassesArmor, boolean isMagic, boolean isFire, boolean isExplosion, float exhaustion, String scaling) { }
}