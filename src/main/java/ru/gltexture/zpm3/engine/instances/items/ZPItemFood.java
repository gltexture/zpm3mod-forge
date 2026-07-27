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

package ru.gltexture.zpm3.engine.instances.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ZPItemFood extends ZPItem {
    private final ZPFoodProperties zpFoodProperties;

    public ZPItemFood(@NotNull Properties pProperties, @NotNull FoodProperties foodProperties, @NotNull ZPFoodProperties ZPFoodProperties) {
        super(pProperties.food(foodProperties));
        this.zpFoodProperties = ZPFoodProperties;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return this.getZpFoodProperties().isDrinkable() ? UseAnim.DRINK : UseAnim.EAT;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        if (pStack.getItem().isEdible()) {
            boolean fastFood = Objects.requireNonNull(pStack.getFoodProperties(null)).isFastFood();
            int time = this.getZpFoodProperties().getEatTime();
            if (fastFood) {
                time /= 2;
            }
            return time;
        } else {
            return 0;
        }
    }

    public ZPFoodProperties getZpFoodProperties() {
        return this.zpFoodProperties;
    }

    public static class ZPFoodProperties {
        private boolean isDrinkable;
        private int eatTime;

        public ZPFoodProperties() {
            this.setDefaults();
        }

        protected void setDefaults() {
            this.isDrinkable = false;
            this.eatTime = 32;
        }

        public ZPFoodProperties setDrinkable(boolean drinkable) {
            isDrinkable = drinkable;
            return this;
        }

        public boolean isDrinkable() {
            return this.isDrinkable;
        }

        public ZPFoodProperties setEatTime(int eatTime) {
            this.eatTime = eatTime;
            return this;
        }

        public int getEatTime() {
            return this.eatTime;
        }
    }
}