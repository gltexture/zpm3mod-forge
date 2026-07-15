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
