package ru.gltexture.zpm3.modules.player.events.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.modules.common.init.ZPSounds;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityUtil;

@OnlyIn(Dist.CLIENT)
public class ZPPlayerClientTickGeigerSoundEvent implements ZPEventClass {
    public static @Nullable ItemEntity entityToPickUp = null;

    public ZPPlayerClientTickGeigerSoundEvent() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        final int radMult = ZPEntityUtil.getLivingEntityRadiationIncMultiplier(event.player);
        if (radMult > 0) {
            if (event.player.tickCount % ((20 + ZPRandom.getRandom().nextInt(8)) / radMult) == 0) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ZPSounds.geiger_fx.get(), 0.8f + ZPRandom.instance.randomFloat(0.5f), 0.5f));
            }
        }
    }
}
