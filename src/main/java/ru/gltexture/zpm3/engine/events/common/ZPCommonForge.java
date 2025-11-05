package ru.gltexture.zpm3.engine.events.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

@Mod.EventBusSubscriber(modid = ZombiePlague3.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ZPCommonForge {
    @SubscribeEvent
    public static void onBreakBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (ZPCommonForge.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (ZPCommonForge.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (ZPCommonForge.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (ZPCommonForge.shouldCancelInteraction(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    private static boolean shouldCancelInteraction(@NotNull Player player) {
        ItemStack stack = player.getMainHandItem();
        return stack.getItem() instanceof ZPBaseGun;
    }
}
