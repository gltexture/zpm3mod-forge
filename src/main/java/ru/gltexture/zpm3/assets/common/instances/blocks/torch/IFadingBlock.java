package ru.gltexture.zpm3.assets.common.instances.blocks.torch;

import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface IFadingBlock {
    @Nullable Supplier<Block> getTurnInto();
}
