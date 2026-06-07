package ru.gltexture.zpm3.modules.player.events.common;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.events.common.ZPCommonForge;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;

public class ZPPlayerGunCancelInterEvent implements ZPEventClass {
    @SubscribeEvent
    public static void onBreakBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (ZPPlayerGunCancelInterEvent.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (ZPPlayerGunCancelInterEvent.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (ZPPlayerGunCancelInterEvent.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (ZPPlayerGunCancelInterEvent.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    private static boolean shouldCancelInteraction(@NotNull Player player) {
        ItemStack stack = player.getMainHandItem();
        return stack.getItem() instanceof ZPBaseGun;
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
