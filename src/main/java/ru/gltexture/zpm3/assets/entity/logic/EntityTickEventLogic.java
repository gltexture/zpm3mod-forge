package ru.gltexture.zpm3.assets.entity.logic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;

public interface EntityTickEventLogic {
    void onTickEntity(@NotNull Entity entity);
}
