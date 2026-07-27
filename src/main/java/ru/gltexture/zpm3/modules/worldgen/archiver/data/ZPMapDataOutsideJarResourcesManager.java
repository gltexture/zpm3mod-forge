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

package ru.gltexture.zpm3.modules.worldgen.archiver.data;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.function.Supplier;

public class ZPMapDataOutsideJarResourcesManager extends ZPMapDataResourcesManager {
    private final String pathToFolder;

    private int imageW;
    private int imageH;

    public ZPMapDataOutsideJarResourcesManager(String pathToFolder, @NotNull String archiveRelPath, @Nullable String imageRelPath) {
        super(archiveRelPath, imageRelPath);
        this.pathToFolder = pathToFolder;
    }

    @Override
    public void lazyCreate() {
        if (this.imageRelPath == null) {
            return;
        }
        final Path previewFile = Path.of(this.pathToFolder).resolve(this.imageRelPath);
        if (Files.exists(previewFile)) {
            try (InputStream stream = Files.newInputStream(previewFile)) {
                try (final NativeImage image = NativeImage.read(stream)) {
                    final DynamicTexture texture = new DynamicTexture(image);
                    this.imageW = image.getWidth();
                    this.imageH = image.getHeight();
                    this.resourceLocationImage = Minecraft.getInstance().getTextureManager().register("zpm_map_preview/" + UUID.randomUUID(), texture);
                    this.dynamicTexture = texture;
                }
            } catch (IOException e) {
                throw new ZPIOException(e);
            }
        }
    }

    @Override
    public void lazyClear() {
        if (this.resourceLocationImage != null) {
            Minecraft.getInstance().getTextureManager().release(this.resourceLocationImage);
            this.resourceLocationImage = null;
        }
        if (this.dynamicTexture != null) {
            this.dynamicTexture.close();
            this.dynamicTexture = null;
        }
    }

    @Override
    public int imageHeight() {
        return this.imageH;
    }

    @Override
    public int imageWidth() {
        return this.imageW;
    }

    @Override
    public Supplier<InputStream> getArchiveStream() {
        Path archivePath = Path.of(this.pathToFolder).resolve(this.archiveRelPath);
        return () -> {
            try {
                return Files.newInputStream(archivePath);
            } catch (IOException e) {
                throw new ZPIOException(e);
            }
        };
    }

    @Override
    public @Nullable ResourceLocation getImageResourceLocation() {
        return this.resourceLocationImage;
    }
}
