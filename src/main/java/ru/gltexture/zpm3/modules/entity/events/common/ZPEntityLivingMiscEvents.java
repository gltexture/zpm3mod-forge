package ru.gltexture.zpm3.modules.entity.events.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

public class ZPEntityLivingMiscEvents implements ZPEventClass {
    @SubscribeEvent
    public static void exec(@NotNull LivingKnockBackEvent event) {
        event.setStrength(0.25f);
        if (event.getEntity().getLastDamageSource() != null && "damage.zpm3.zp_bullet".equals(event.getEntity().getLastDamageSource().getMsgId())) {
            event.setStrength(0.1f);
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (event.getEntity() instanceof ZPAbstractZombie) {
            float damageMultiplier = event.getDamageMultiplier();
            event.setDamageMultiplier(damageMultiplier * 0.5f);
        }
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (ZPArmorUtil.isFullAqualungBreathingRightNow(entity)) {
            event.getEntity().setAirSupply(entity.getMaxAirSupply());
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            return;
        }
        if (!ZPArmorUtil.isFullAqualungBreathingRightNow(mc.player)) {
            return;
        }
        if (mc.player.tickCount % 20 == 0) {
            ZPEntityLivingMiscEvents.spawnWaterBubbles();
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void spawnWaterBubbles() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) {
            return;
        }
        for (int i = 0; i < 8; i++) {
            double x = player.getX() + (ZPRandom.getRandom().nextFloat() - 0.5D) * 0.6D;
            double y = player.getEyeY() - 0.2D + ZPRandom.getRandom().nextFloat() * 0.3D;
            double z = player.getZ() + (ZPRandom.getRandom().nextFloat() - 0.5D) * 0.6D;
            mc.level.addParticle(ParticleTypes.BUBBLE, x, y, z, 0.0D, 0.03D, 0.0D);
        }
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
