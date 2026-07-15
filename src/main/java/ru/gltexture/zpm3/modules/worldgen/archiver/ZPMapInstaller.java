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