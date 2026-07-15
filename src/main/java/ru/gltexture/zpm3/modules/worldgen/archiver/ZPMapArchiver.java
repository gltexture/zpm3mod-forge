package ru.gltexture.zpm3.modules.worldgen.archiver;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@OnlyIn(Dist.CLIENT)
public final class ZPMapArchiver {
    private ZPMapArchiver() {}

    public static void archive(MinecraftServer server, Path destinationZip, Consumer<ArchiveBuilder> config) throws IOException {
        server.saveEverything(false, true, true);
        Path worldRoot = server.getWorldPath(LevelResource.ROOT);
        ArchiveBuilder builder = new ArchiveBuilder(worldRoot);
        config.accept(builder);
        Files.createDirectories(destinationZip.getParent());
        try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(destinationZip))) {
            for (Path entry : builder.entries) {
                if (!Files.exists(entry)) {
                    continue;
                }
                if (Files.isDirectory(entry)) {
                    try (Stream<Path> stream = Files.walk(entry)) {
                        stream.forEach(file -> {
                            if (Files.isDirectory(file)) {
                                return;
                            }
                            try {
                                ZPMapArchiver.addFile(worldRoot, file, zip);
                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        });
                    }
                } else {
                    ZPMapArchiver.addFile(worldRoot, entry, zip);
                }
            }
        }
    }

    private static void addFile(Path root, Path file, ZipOutputStream zip) throws IOException {
        String name = root.relativize(file).toString().replace('\\', '/');
        zip.putNextEntry(new ZipEntry(name));
        Files.copy(file, zip);
        zip.closeEntry();
    }

    public static final class ArchiveBuilder {
        private final Path root;
        private final List<Path> entries = new ArrayList<>();

        private ArchiveBuilder(Path root) {
            this.root = root;
        }

        public ArchiveBuilder file(String relative) {
            entries.add(root.resolve(relative));
            return this;
        }

        public ArchiveBuilder folder(String relative) {
            entries.add(root.resolve(relative));
            return this;
        }
    }
}