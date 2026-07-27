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

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.function.Supplier;

public abstract class ZPMapDataResourcesManager {
    protected @Nullable ResourceLocation resourceLocationImage;
    protected @Nullable DynamicTexture dynamicTexture;

    protected final String archiveRelPath;
    protected final String imageRelPath;

    public ZPMapDataResourcesManager(@NotNull final String archiveRelPath, @Nullable final String imageRelPath) {
        this.resourceLocationImage = null;
        this.dynamicTexture = null;

        this.archiveRelPath = archiveRelPath;
        this.imageRelPath = imageRelPath;
    }

    public int imageHeight() {
        return 0;
    }

    public int imageWidth() {
        return 0;
    }

    public abstract void lazyCreate();
    public abstract void lazyClear();

    public abstract Supplier<InputStream> getArchiveStream();
    public abstract @Nullable ResourceLocation getImageResourceLocation();
}
