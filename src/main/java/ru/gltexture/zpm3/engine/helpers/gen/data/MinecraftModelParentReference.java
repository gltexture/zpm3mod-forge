package ru.gltexture.zpm3.engine.helpers.gen.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;

public record MinecraftModelParentReference(@NotNull String mainBlockReference, @Nullable Map<String, String> subReferences) {
    public MinecraftModelParentReference(@NotNull String mainBlockReference) {
        this(mainBlockReference, null);
    }
}
