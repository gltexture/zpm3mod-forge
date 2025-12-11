package ru.gltexture.zpm3.assets.player.events.common;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.player.ZPPlayerAsset;
import ru.gltexture.zpm3.assets.player.misc.ZPDefaultItemsHandReach;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

import java.util.Objects;
import java.util.UUID;

public class ZPPlayerTickEvent implements ZPEventClass {
    public ZPPlayerTickEvent() {
    }

    private static final UUID REACH_BONUS_UUID = UUID.fromString("e3d6a3c0-4b73-4e72-9ad1-1c1c0a9c1111");

    @SubscribeEvent
    public static void exec(TickEvent.@NotNull PlayerTickEvent event) {
        if (event.player.level().isClientSide()) {
            return;
        }

        Player player = event.player;
        AttributeInstance attr = player.getAttribute(ForgeMod.ENTITY_REACH.get());
        if (attr == null) {
            return;
        }

        ItemStack stack = player.getMainHandItem();
        float bonus = ZPDefaultItemsHandReach.get(stack.getItem());

        AttributeModifier old = attr.getModifier(ZPPlayerTickEvent.REACH_BONUS_UUID);
        if (old != null) {
            attr.removeModifier(old);
        }

        if (bonus == 0.0f) {
            return;
        }

        AttributeModifier mod = new AttributeModifier(ZPPlayerTickEvent.REACH_BONUS_UUID, "zp_reach_bonus", bonus, AttributeModifier.Operation.ADDITION);
        attr.addPermanentModifier(mod);
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
