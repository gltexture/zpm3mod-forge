package ru.gltexture.zpm3.assets.common.instances.items;

import net.minecraft.world.food.FoodProperties;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.objects.items.ZPItem;

public class ZPItemFood extends ZPItem {
    public ZPItemFood(@NotNull Properties pProperties, @NotNull FoodProperties foodProperties) {
        super(pProperties.food(foodProperties));
    }
}