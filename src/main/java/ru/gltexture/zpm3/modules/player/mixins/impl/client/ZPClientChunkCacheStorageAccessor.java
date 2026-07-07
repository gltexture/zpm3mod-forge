package ru.gltexture.zpm3.modules.player.mixins.impl.client;

import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.atomic.AtomicReferenceArray;

@Mixin(targets = "net.minecraft.client.multiplayer.ClientChunkCache$Storage")
public interface ZPClientChunkCacheStorageAccessor {
    @Accessor("chunks")
    AtomicReferenceArray<LevelChunk> zpm3forge$getChunks();
}