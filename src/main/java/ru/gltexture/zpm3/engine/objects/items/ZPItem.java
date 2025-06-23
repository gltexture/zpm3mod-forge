package ru.gltexture.zpm3.engine.objects.items;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public abstract class ZPItem extends Item {
    public ZPItem(@NotNull Properties pProperties) {
        super(pProperties);
    }
}
