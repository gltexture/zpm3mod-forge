package ru.gltexture.zpm3.engine.mixins.ext;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.server.level.BlockDestructionProgress;

import java.util.SortedSet;

public interface IZPLevelRendererExt {
    Int2ObjectMap<BlockDestructionProgress> zpm3forge$destroyingBlocks();
    Long2ObjectMap<SortedSet<BlockDestructionProgress>> zpm3forge$destructionProgress();
}
