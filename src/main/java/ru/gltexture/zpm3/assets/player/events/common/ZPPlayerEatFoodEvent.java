package ru.gltexture.zpm3.assets.player.events.common;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.player.misc.ZPDefaultItemsHandReach;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

import java.util.UUID;

public class ZPPlayerEatFoodEvent implements ZPEventClass {
    public ZPPlayerEatFoodEvent() {
    }

    private static final UUID REACH_BONUS_UUID = UUID.fromString("e3d6a3c0-4b73-4e72-9ad1-1c1c0a9c1111");

    @SubscribeEvent
    public static void exec(LivingEntityUseItemEvent.Finish event) {
        ItemStack stack = event.getItem();
        LivingEntity entity = event.getEntity();

        if (!entity.level().isClientSide()) {
            if (stack.getItem().equals(Items.ROTTEN_FLESH)) {
                ZPAbstractZombie.applyRandomEffect(entity);
            }
            if (stack.is(Items.BEEF) || stack.is(Items.PORKCHOP) || stack.is(Items.CHICKEN) || stack.is(Items.MUTTON) || stack.is(Items.RABBIT) || stack.is(Items.COD) || stack.is(Items.SALMON) || stack.is(Items.TROPICAL_FISH)) {
                if (ZPRandom.getRandom().nextFloat() <= ZPConstants.RANDOM_FRY_FOOD_POSIONING) {
                    entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 2400));
                }
            }
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
