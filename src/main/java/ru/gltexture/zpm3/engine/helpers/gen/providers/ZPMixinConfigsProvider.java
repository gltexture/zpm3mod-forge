package ru.gltexture.zpm3.engine.helpers.gen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.mixins.ZPMixinPlugin;
import ru.gltexture.zpm3.engine.service.Pair;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ZPMixinConfigsProvider implements DataProvider {
    public static final Set<Pair<ZombiePlague3.IMixinEntry.MixinConfig, ZombiePlague3.IMixinEntry.MixinClass[]>> mixinClasses = new HashSet<>();
    private final DataGenerator generator;
    private final String modId;

    public ZPMixinConfigsProvider(DataGenerator generator, String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    public static void addNewMixinData(@NotNull ZombiePlague3.IMixinEntry.MixinConfig mixinConfig, @NotNull ZombiePlague3.IMixinEntry.MixinClass[] mixinClasses) {
        ZPMixinConfigsProvider.mixinClasses.add(new Pair<>(mixinConfig, mixinClasses));
    }

    private static void clear() {
        ZPMixinConfigsProvider.mixinClasses.clear();
    }

    @SuppressWarnings("all")
    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput pOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (Pair<ZombiePlague3.IMixinEntry.MixinConfig, ZombiePlague3.IMixinEntry.MixinClass[]> pair : mixinClasses) {
            ZombiePlague3.IMixinEntry.MixinConfig config = pair.first();
            ZombiePlague3.IMixinEntry.MixinClass[] mixins = pair.second();

            JsonObject json = new JsonObject();
            json.addProperty("required", true);
            json.addProperty("minVersion", "0.8");
            json.addProperty("compatibilityLevel", "JAVA_17");
            json.addProperty("refmap", modId + ".refmap.json");
            json.addProperty("package", config.packagePath());

            JsonObject injectors = new JsonObject();
            injectors.addProperty("defaultRequire", 1);
            json.add("injectors", injectors);

            JsonArray mixinsArr = new JsonArray();
            JsonArray clientArr = new JsonArray();
            JsonArray serverArr = new JsonArray();

            for (ZombiePlague3.IMixinEntry.MixinClass mc : mixins) {
                ZPSide side = mc.side();
                String name = mc.name();
                if (side == ZPSide.BOTH) {
                    mixinsArr.add(name);
                } else if (side == ZPSide.CLIENT) {
                    clientArr.add(name);
                } else if (side == ZPSide.SERVER) {
                    serverArr.add(name);
                }
            }

            json.add("mixins", mixinsArr);
            json.add("client", clientArr);
            json.add("both", serverArr);

            Path outPath = generator.getPackOutput().getOutputFolder().resolve(ZPMixinPlugin.pathToMixinsCfg + config.name() + ".json");
            futures.add(DataProvider.saveStable(pOutput, json, outPath));
        }

        ZPMixinConfigsProvider.clear();
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public @NotNull String getName() {
        return "MixinConfigs";
    }
}