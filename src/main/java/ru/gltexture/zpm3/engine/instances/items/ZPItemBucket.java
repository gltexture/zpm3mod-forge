package ru.gltexture.zpm3.engine.instances.items;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ZPItemBucket extends BucketItem {
    public ZPItemBucket(@NotNull Supplier<? extends Fluid> supplier, @NotNull Properties builder) {
        super(supplier, builder);
    }
}
