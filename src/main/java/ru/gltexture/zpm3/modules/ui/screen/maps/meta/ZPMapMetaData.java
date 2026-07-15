package ru.gltexture.zpm3.modules.ui.screen.maps.meta;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.worldgen.archiver.data.ZPMapDataResourcesManager;

public record ZPMapMetaData(@NotNull ZPMapDataResourcesManager mapDataResourcesManager, @NotNull String mapName, @NotNull String version, @NotNull String[] authors, @NotNull String description, int recommendedPlayers, @NotNull String modVersion) {
}