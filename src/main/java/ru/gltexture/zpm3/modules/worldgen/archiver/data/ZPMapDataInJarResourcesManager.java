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
import net.minecraft.server.packs.resources.Resource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.exceptions.ZPIOException;
import ru.gltexture.zpm3.engine.service.ZPPath;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.function.Supplier;

public class ZPMapDataInJarResourcesManager extends ZPMapDataResourcesManager {
    private final String modNameSpace;
    private final String pathToFolder;

    private int imageW;
    private int imageH;

    public ZPMapDataInJarResourcesManager(@NotNull String modNameSpace, String pathToFolder, @NotNull String archiveRelPath, @NotNull String imageRelPath) {
        super(archiveRelPath, imageRelPath);
        this.modNameSpace = modNameSpace;
        this.pathToFolder = pathToFolder;
    }

    @Override
    public void lazyCreate() {
        if (this.imageRelPath == null) {
            return;
        }
        Resource resource = Minecraft.getInstance().getResourceManager().getResource(ResourceLocation.fromNamespaceAndPath(this.modNameSpace, new ZPPath(this.pathToFolder, this.imageRelPath).getFullPath())).orElse(null);
        if (resource != null) {
            try (InputStream stream = resource.open()) {
                try (final NativeImage image = NativeImage.read(stream)) {
                    this.dynamicTexture = new DynamicTexture(image);
                    this.imageW = image.getWidth();
                    this.imageH = image.getHeight();
                    this.resourceLocationImage = Minecraft.getInstance().getTextureManager().register("zpm_map_preview/" + UUID.randomUUID(), this.dynamicTexture);
                }
            } catch (IOException e) {
                throw new ZPIOException(e);
            }
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
    public Supplier<InputStream> getArchiveStream() {
        ResourceLocation archiveLocation = ResourceLocation.fromNamespaceAndPath(this.modNameSpace, new ZPPath(this.pathToFolder, this.archiveRelPath).getFullPath());
        return () ->
        {
            try {
                return Minecraft.getInstance().getResourceManager().getResource(archiveLocation).orElseThrow(() -> new IOException("Archive not found: " + archiveLocation)).open();
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
