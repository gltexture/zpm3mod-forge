package ru.gltexture.zpm3.modules.blocks.instances.blocks.fading;

import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface IFadingBlock {
    @Nullable Supplier<Block> zpm3forge$getTurnInto();
}
