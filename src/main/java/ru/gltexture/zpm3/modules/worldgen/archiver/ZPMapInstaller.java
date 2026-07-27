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

import net.minecraftforge.fml.loading.FMLPaths;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.modules.ui.screen.maps.meta.ZPMapMetaData;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class ZPMapInstaller {
    public static Path getSavesFolder() {
        return FMLPaths.GAMEDIR.get().resolve("saves");
    }

    public static void installMap(ZPMapMetaData data) {
        String folderName = data.mapName();
        Path saveFolder = ZPMapInstaller.getSavesFolder().resolve(folderName);
        try {
            while (Files.exists(saveFolder)) {
                saveFolder = saveFolder.resolveSibling(saveFolder.getFileName() + "_");
            }
            Files.createDirectories(saveFolder);
            ZPMapInstaller.unzip(data.mapDataResourcesManager().getArchiveStream().get(), saveFolder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to install map: " + folderName, e);
        }
    }

    private static void unzip(InputStream input, Path target) throws IOException {
        try (ZipInputStream zip = new ZipInputStream(input)) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                Path path = target.resolve(entry.getName());
                if (!path.normalize().startsWith(target.normalize())) {
                    throw new ZPIOException("Invalid zip entry: " + entry.getName());
                }
                if (entry.isDirectory()) {
                    Files.createDirectories(path);
                } else {
                    Files.createDirectories(path.getParent());
                    Files.copy(zip, path, StandardCopyOption.REPLACE_EXISTING);
                }
                zip.closeEntry();
            }
        }
    }
}