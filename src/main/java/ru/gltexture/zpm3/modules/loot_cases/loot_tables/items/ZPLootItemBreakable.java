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

package ru.gltexture.zpm3.modules.loot_cases.loot_tables.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.ZPLootNbtValue;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.random.ZPRandomization;

import java.util.*;

@SuppressWarnings("all")
public record ZPLootItemBreakable(@NotNull String locationKey, int spawnWeight, float minDamage, float maxDamage, @NotNull ZPRandomization damageRandomization, @NotNull Map<String, ZPLootNbtValue>  nbtContainer) implements IZPLootItem {
    @Override
    public @Nullable ItemStack buildItemStack() {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(locationKey));
        if (item == null) {
            ZPLogger.error("Couldn't get item: " + locationKey);
            return null;
        }
        ItemStack stack = new ItemStack(item);
        if (stack.isDamageableItem()) {
            int max = stack.getMaxDamage();
            float damageFrac = 1.0f - this.damageRandomization.random(this.minDamage, this.maxDamage);
            int damageValue = (int) (max * damageFrac);
            if (damageFrac <= 0.01f) {
                damageValue = 0;
            }
            stack.setDamageValue(Mth.clamp(damageValue, 0, max));
        }
        for (Map.Entry<String, ZPLootNbtValue> entry : nbtContainer.entrySet()) {
            CompoundTag nbt = stack.getOrCreateTag();
            final String[] subNBTs = entry.getKey().split(":");
            for (int i = 0; i < subNBTs.length - 1; i++) {
                nbt = nbt.getCompound(subNBTs[i]);
            }
            entry.getValue().writeValue(nbt, subNBTs[subNBTs.length - 1]);
        }
        return stack;
    }

    @Override
    public int spawnWeight() {
        return this.spawnWeight;
    }
}
