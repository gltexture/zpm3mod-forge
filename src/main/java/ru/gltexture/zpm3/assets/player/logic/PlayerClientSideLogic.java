package ru.gltexture.zpm3.assets.player.logic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.entity.logic.EntityTickEventLogic;

@OnlyIn(Dist.CLIENT)
public class PlayerClientSideLogic implements PlayerTickEventLogic {
    @Override
    public void onTickPlayer(TickEvent.Phase phase, @NotNull Player player) {

    }
}
