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

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPCombatConfig;
import ru.gltexture.zpm3.modules.entity.init.ZPEntities;
import ru.gltexture.zpm3.modules.entity.instances.throwables.ZPAcidBottleEntity;
import ru.gltexture.zpm3.engine.instances.entities.ZPThrowableEntity;

public class ZPItemThrowable extends ZPItem {
    private final ThrowableProjectileFabric throwableProjectileFabric;

    public ZPItemThrowable(@NotNull ThrowableProjectileFabric throwableProjectileFabric, @NotNull Properties pProperties) {
        super(pProperties);
        this.throwableProjectileFabric = throwableProjectileFabric;
    }

    public ThrowableProjectileFabric getThrowableProjectileFabric() {
        return this.throwableProjectileFabric;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            pPlayer.getCooldowns().addCooldown(this, ZPCombatConfig.ITEMS_THROW_COOLDOWN.getVar());
            ZPAcidBottleEntity acidBottle = new ZPAcidBottleEntity(ZPEntities.acid_bottle_entity.get(), pPlayer, pLevel);
            acidBottle.setItem(itemstack);
            acidBottle.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.0F, 12.0F);
            pLevel.addFreshEntity(this.getThrowableProjectileFabric().createThrowableEntity(ZPCombatConfig.ITEMS_THROW_INACCURACY.getVar(), ZPCombatConfig.ITEMS_THROW_VELOCITY.getVar(), itemstack, pLevel, pPlayer, pHand));
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @FunctionalInterface
    public interface ThrowableProjectileFabric {
        ZPThrowableEntity createThrowableEntity(float inaccuracy, float velocity, @NotNull ItemStack itemstack, @NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand);
    }
}
