package ru.gltexture.zpm3.assets.player.logic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;

public interface PlayerTickEventLogic {
    void onTickPlayer(TickEvent.Phase phase, @NotNull Player player);
}
