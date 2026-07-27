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

package ru.gltexture.zpm3.modules.worldgen.archiver;

import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.modules.ui.screen.maps.meta.ZPMapMetaData;
import ru.gltexture.zpm3.modules.worldgen.archiver.data.ZPMapDataInJarResourcesManager;
import ru.gltexture.zpm3.modules.worldgen.archiver.data.ZPMapDataOutsideJarResourcesManager;
import ru.gltexture.zpm3.modules.worldgen.archiver.data.ZPMapDataResourcesManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public final class ZPMapArchivedRegistry {
    public static final String MAPS_DIR = "zp_maps";
    private static final List<ZPMapMetaData> registeredArchivedMaps = new ArrayList<>();
    private static final List<Pair<String, String>> toRegisterInJarsList = new ArrayList<>();

    public static Stream<ZPMapMetaData> streamMaps() {
        return ZPMapArchivedRegistry.registeredArchivedMaps.stream();
    }

    public static Path getMapsFolder() {
        return FMLPaths.GAMEDIR.get().resolve(MAPS_DIR);
    }

    public static void createMapsFolder() {
        Path folder = ZPMapArchivedRegistry.getMapsFolder();
        try {
            Files.createDirectories(folder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create maps folder: " + folder, e);
        }
    }

    private static void readMinecraftFolder() throws IOException {
        final Path mapsDir = FMLPaths.GAMEDIR.get().resolve(ZPMapArchivedRegistry.MAPS_DIR);
        if (!Files.exists(mapsDir)) {
            Files.createDirectories(mapsDir);
            return;
        }
        try (Stream<Path> stream = Files.list(mapsDir)) {
            stream.filter(Files::isDirectory).forEach(ZPMapArchivedRegistry::readFolderMap);
        }
    }

    public static void registerAll() throws IOException {
        if (!Files.exists(ZPMapArchivedRegistry.getMapsFolder())) {
            ZPMapArchivedRegistry.createMapsFolder();
        }
        ZPMapArchivedRegistry.readMinecraftFolder();
        ZPMapArchivedRegistry.toRegisterInJarsList.forEach(e -> {
            ZPMapArchivedRegistry.readFolderMapInJar(e.first(), e.second());
        });
        ZPMapArchivedRegistry.toRegisterInJarsList.clear();
    }

    public static void registerZpArchivedMap(@NotNull String modId, @NotNull String folder) {
        ZPMapArchivedRegistry.toRegisterInJarsList.add(new Pair<>(modId, folder));
    }

    private static void readFolderMapInJar(@NotNull String modId, @NotNull String folder) {
        final ZPPath path = new ZPPath(folder, "map.json");
        final ResourceLocation jsonResource = ResourceLocation.fromNamespaceAndPath(modId, path.getFullPath());
        Minecraft.getInstance().getResourceManager().getResource(jsonResource).ifPresentOrElse(resource -> {
            try (InputStream stream = resource.open();
                 Reader reader = new InputStreamReader(stream)) {
                ZPMapMetaData data = ZPMapArchivedRegistry.readJson(reader, modId, new ZPPath(jsonResource.getPath()).getDirectory().getFullPath());
                if (data != null) {
                    ZPMapArchivedRegistry.registeredArchivedMaps.add(data);
                }
            } catch (IOException e) {
                throw new ZPIOException(e);
            }
        }, () -> {
            throw new ZPIOException("Couldn't find: " + path);
        });
    }

    private static void readFoldersInside(@NotNull Path folder) {
        if (!Files.exists(folder)) {
            return;
        }
        Stream.of(Objects.requireNonNull(folder.toFile().listFiles())).forEach(e -> {
            ZPMapArchivedRegistry.readFolderMap(e.toPath());
        });
    }

    private static void readFolderMap(@NotNull Path folder) {
        Path jsonPath = folder.resolve("map.json");
        if (!Files.exists(jsonPath)) {
            return;
        }
        try (Reader reader = Files.newBufferedReader(jsonPath)) {
            ZPMapMetaData data = ZPMapArchivedRegistry.readJson(reader, null, folder.toAbsolutePath().toString());
            if (data != null) {
                ZPMapArchivedRegistry.registeredArchivedMaps.add(data);
            }
        } catch (IOException e) {
            throw new ZPIOException(e);
        }
    }

    private static @Nullable ZPMapMetaData readJson(@NotNull Reader reader, @Nullable String insideJarModID, @NotNull String resourcePath) {
        try {
            final JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (!json.has("archive")) {
                return null;
            }
            final String archive = json.get("archive").getAsString();
            if (archive.isEmpty()) {
                return null;
            }
            final String preview = getJsonString(json, "preview", "");
            final String name = getJsonString(json, "name", archive);
            final String version = getJsonString(json, "version", "Unknown");
            final String description = getJsonString(json, "description", "");
            final int recommendedPlayers = json.has("recommendedPlayers") ? json.get("recommendedPlayers").getAsInt() : -1;
            final String modVersion = getJsonString(json, "modVersion", "Unknown");
            final String[] authors = getAuthors(json);
            ZPMapDataResourcesManager manager;
            if (insideJarModID != null) {
                manager = new ZPMapDataInJarResourcesManager(insideJarModID, resourcePath, archive, preview);
            } else {
                manager = new ZPMapDataOutsideJarResourcesManager(resourcePath, archive, preview);
            }
            return new ZPMapMetaData(manager, name, version, authors, description, recommendedPlayers, modVersion);
        } catch (Exception e) {
            throw new ZPIOException(e);
        }
    }

    private static String getJsonString(JsonObject json, String key, String def) {
        return json.has(key) ? json.get(key).getAsString() : def;
    }


    private static String[] getAuthors(JsonObject json) {
        if (!json.has("authors") || !json.get("authors").isJsonArray()) {
            return new String[]{"Unknown"};
        }
        final JsonArray array = json.getAsJsonArray("authors");
        final String[] result = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            result[i] = array.get(i).getAsString();
        }
        return result;
    }
}