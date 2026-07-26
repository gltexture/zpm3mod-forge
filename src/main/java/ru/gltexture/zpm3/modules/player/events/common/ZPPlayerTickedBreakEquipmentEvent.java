package ru.gltexture.zpm3.modules.player.events.common;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZPPlayerTickedBreakEquipmentEvent implements ZPEventClass {
    private static final Map<ArmorItem, ArmorBreakPredicate> armorBreakConditions = new HashMap<>();
    private static final Map<Item, ItemBreakPredicate> itemBreakConditions = new HashMap<>();

    private static @Nullable Map<Supplier<ArmorItem>, ArmorBreakPredicate> TEMP_armorBreakConditions = new HashMap<>();
    private static @Nullable Map<Supplier<Item>, ItemBreakPredicate> TEMP_itemBreakConditions = new HashMap<>();

    public static void registerArmorBreakPerTickCondition(@NotNull Supplier<ArmorItem> armorItem, @NotNull ArmorBreakPredicate armorBreakPredicate) {
        Objects.requireNonNull(ZPPlayerTickedBreakEquipmentEvent.TEMP_armorBreakConditions).put(armorItem, armorBreakPredicate);
    }

    public static void registerItemBreakPerTickCondition(@NotNull Supplier<Item> item, @NotNull ItemBreakPredicate itemBreakPredicate) {
        Objects.requireNonNull(ZPPlayerTickedBreakEquipmentEvent.TEMP_itemBreakConditions).put(item, itemBreakPredicate);
    }

    private static void LAZY_INIT() {
        if (ZPPlayerTickedBreakEquipmentEvent.TEMP_armorBreakConditions != null) {
            ZPPlayerTickedBreakEquipmentEvent.TEMP_armorBreakConditions.forEach((supplier, predicate) -> {
                ZPPlayerTickedBreakEquipmentEvent.armorBreakConditions.put(supplier.get(), predicate);
            });
            ZPPlayerTickedBreakEquipmentEvent.TEMP_armorBreakConditions.clear();
            ZPPlayerTickedBreakEquipmentEvent.TEMP_armorBreakConditions = null;
        }

        if (ZPPlayerTickedBreakEquipmentEvent.TEMP_itemBreakConditions != null) {
            ZPPlayerTickedBreakEquipmentEvent.TEMP_itemBreakConditions.forEach((supplier, predicate) -> {
                ZPPlayerTickedBreakEquipmentEvent.itemBreakConditions.put(supplier.get(), predicate);
            });
            ZPPlayerTickedBreakEquipmentEvent.TEMP_itemBreakConditions.clear();
            ZPPlayerTickedBreakEquipmentEvent.TEMP_itemBreakConditions = null;
        }
    }
    public ZPPlayerTickedBreakEquipmentEvent() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (ZPPlayerTickedBreakEquipmentEvent.TEMP_itemBreakConditions != null ||  ZPPlayerTickedBreakEquipmentEvent.TEMP_armorBreakConditions != null) {
            ZPPlayerTickedBreakEquipmentEvent.LAZY_INIT();
        }
        Player player = event.player;
        int tick = player.tickCount;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) {
                continue;
            }
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armorItem)) {
                continue;
            }
            final ArmorBreakPredicate predicate = ZPPlayerTickedBreakEquipmentEvent.armorBreakConditions.get(armorItem);
            if (predicate == null) {
                continue;
            }
            if (predicate.canBreak(player, armorItem, slot, tick)) {
                stack.hurtAndBreak(1, player, e -> {
                    e.broadcastBreakEvent(slot);
                });
            }
        }

        for (ItemStack stack : Stream.concat(player.getInventory().items.stream(), player.getInventory().offhand.stream()).toArray(ItemStack[]::new)) {
            if (stack.isEmpty()) {
                continue;
            }
            ItemBreakPredicate predicate = ZPPlayerTickedBreakEquipmentEvent.itemBreakConditions.get(stack.getItem());
            if (predicate == null) {
                continue;
            }
            if (predicate.canBreak(player, stack, stack.getItem(), tick)) {
                stack.hurtAndBreak(1, player, e -> {});
                if (stack.isEmpty()) {
                    player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F + player.getRandom().nextFloat() * 0.4F);
                }
            }
        }
    }

    @FunctionalInterface
    public interface ArmorBreakPredicate {
        boolean canBreak(Entity entity, ArmorItem armorItem, EquipmentSlot slot, int tick);
    }

    @FunctionalInterface
    public interface ItemBreakPredicate {
        boolean canBreak(Entity entity, ItemStack stack, Item item, int tick);
    }
}