package ru.gltexture.zpm3.assets.entity.logic;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class EntityClientSideLogic implements EntityTickEventLogic {
    @Override
    public void onTickEntity(@NotNull Entity entity) {
    }
}
