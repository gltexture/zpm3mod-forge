/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.player.events.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.modules.guns.init.helper.ZPRegGuns;
import ru.gltexture.zpm3.modules.melee_throwables_tools.misc.ZPDefaultItemsHandReach;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;

import java.util.List;

public class ZPPlayerItemToolTipsEvent implements ZPForgeEventHandlerClass {
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
        final List<Component> tooltip = event.getToolTip();
        if (ZPRegGuns.gunTrophies.contains(ForgeRegistries.ITEMS.getKey(stack.getItem()))) {
            Component component = Component.translatable("tooltip.zpm3.weapon.trophy").withStyle(ChatFormatting.LIGHT_PURPLE);
            tooltip.add(1, component);
        }
        if (bonus != 0.0F) {
            final String s = (bonus > 0.0F ? "+" : "") + bonus;
            final Component component = Component.translatable("tooltip.zpm3.weapon.hand_bonus", (s)).withStyle(bonus > 0.0F ? ChatFormatting.BLUE : ChatFormatting.RED);
            tooltip.add(3, component);
        }
    }
}
