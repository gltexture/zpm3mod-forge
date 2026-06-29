package ru.gltexture.zpm3.modules.player.events.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.modules.melee_throwables_tools.misc.ZPDefaultItemsHandReach;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

public class ZPPlayerItemToolTipsEvent implements ZPEventClass {
    public static @Nullable ItemEntity entityToPickUp = null;

    public ZPPlayerItemToolTipsEvent() {
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
    public static void toolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        float bonus = ZPDefaultItemsHandReach.get(stack.getItem());
        if (bonus != 0.0f) {
            event.getToolTip().add(Component.translatable("tooltip.zpm3.weapon.hand_bonus", bonus).withStyle(bonus > 0.0f ? ChatFormatting.BLUE : ChatFormatting.RED));
        }
    }
}
