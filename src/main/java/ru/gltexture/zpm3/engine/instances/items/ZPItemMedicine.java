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

import com.mojang.datafixers.util.Pair;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPCombatConfig;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPLivingEntityExt;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.registry.ZPRegistryCollections;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ZPItemMedicine extends ZPItem {
    private final ZPItemMedicine.ZPMedicineProperties zpMedicineProperties;

    public ZPItemMedicine(@NotNull Properties pProperties, @NotNull ZPItemMedicine.ZPMedicineProperties zpMedicineProperties) {
        super(pProperties.food(zpMedicineProperties.getFoodProperties()));
        this.zpMedicineProperties = zpMedicineProperties;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!this.isEdible()) {
            return pStack;
        }

        return this.isEdible() ? this.consume(pLevel, pStack, pLivingEntity, pLivingEntity) : pStack;
    }

    protected void cooldownForMedicine(@NotNull Player player) {
        try {
            for (RegistryObject<Item> registryObject : ZPRegistryCollections.getCollectionById(ZPMiscItems.class, "medicine")) {
                player.getCooldowns().addCooldown(registryObject.get(), ZPCombatConfig.MEDICINE_USE_COOLDOWN.getVar());
            }
        } catch (ZPRuntimeException e) {
            ZPLogger.warn(e.getMessage());
        }
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (this.getZpMedicineProperties().isCanBeAffectedOnOther()) {
            this.consume(target.level(), stack, target, attacker);
            return false;
        }
        return true;
    }

    public ItemStack consume(Level pLevel, ItemStack pFood, LivingEntity entityToAffect, LivingEntity entityWhoUsed) {
        if (pFood.isEdible()) {
            if (this.getZpMedicineProperties().getSoundToPlayOnConsume() != null) {
                pLevel.playSound(null, entityToAffect.getX(), entityToAffect.getY(), entityToAffect.getZ(), this.getZpMedicineProperties().getSoundToPlayOnConsume().get(), SoundSource.NEUTRAL, 1.0F, 1.2F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.2F);
            }
            this.addEatEffect(pFood, pLevel, entityToAffect);
            if (this.getZpMedicineProperties().getIntoxication() > 0) {
                if (entityToAffect instanceof IZPLivingEntityExt ext) {
                    ext.zpm3forge$addIntoxicationLevel(this.getZpMedicineProperties().getIntoxication());
                }
            }
            if (this.getZpMedicineProperties().getConsumer() != null) {
                this.getZpMedicineProperties().getConsumer().accept(entityToAffect);
            }
            if (!(entityToAffect instanceof Player) || !((Player) entityToAffect).getAbilities().instabuild) {
                if (pFood.isDamageableItem()) {
                    pFood.hurtAndBreak(1, entityWhoUsed, e -> {
                        e.broadcastBreakEvent(entityWhoUsed.getUsedItemHand());
                    });
                } else {
                    pFood.shrink(1);
                }
            }
            if (entityWhoUsed instanceof Player player) {
                if (!pLevel.isClientSide) {
                    this.cooldownForMedicine(player);
                }
            }
            entityToAffect.gameEvent(GameEvent.EAT);
        }
        return pFood;
    }

    private void addEatEffect(ItemStack pFood, Level pLevel, LivingEntity pLivingEntity) {
        Item item = pFood.getItem();
        if (pFood.getFoodProperties(pLivingEntity) != null && item.isEdible()) {
            if (pLivingEntity instanceof Player player) {
                if (Objects.requireNonNull(pFood.getFoodProperties(pLivingEntity)).getNutrition() > 0) {
                    player.eat(pLevel, pFood);
                }
            }
            for(Pair<MobEffectInstance, Float> pair : Objects.requireNonNull(pFood.getFoodProperties(pLivingEntity)).getEffects()) {
                if (!pLevel.isClientSide && pair.getFirst() != null && pLevel.random.nextFloat() < pair.getSecond()) {
                    MobEffectInstance mobEffectInstance = new MobEffectInstance(pair.getFirst());
                    if (mobEffectInstance.getDuration() < 0) {
                        if (mobEffectInstance.getAmplifier() < 0) {
                            MobEffectInstance current = pLivingEntity.getEffect(mobEffectInstance.getEffect());
                            if (current != null) {
                                pLivingEntity.removeEffect(mobEffectInstance.getEffect());
                                int newAmplifier = current.getAmplifier() - 1;
                                if (newAmplifier >= 0) {
                                    pLivingEntity.addEffect(new MobEffectInstance(current.getEffect(), current.getDuration(), newAmplifier, current.isAmbient(), current.isVisible(), current.showIcon()));
                                }
                            }
                        } else {
                            pLivingEntity.removeEffect(mobEffectInstance.getEffect());
                        }
                    } else {
                        pLivingEntity.addEffect(mobEffectInstance);
                    }
                }
            }
        }
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        switch (this.getZpMedicineProperties().getMedicineAnim()) {
            case EAT -> {
                return UseAnim.EAT;
            }
            case DRINK -> {
                return UseAnim.DRINK;
            }
            case BLOCK -> {
                return UseAnim.BLOCK;
            }
        }
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        if (pStack.getItem().isEdible()) {
            boolean fastFood = Objects.requireNonNull(pStack.getFoodProperties(null)).isFastFood();
            int time = this.getZpMedicineProperties().getConsumeTime();
            if (fastFood) {
                time /= 2;
            }
            return time;
        } else {
            return 0;
        }
    }

    public ZPItemMedicine.ZPMedicineProperties getZpMedicineProperties() {
        return this.zpMedicineProperties;
    }

    public enum MedicineAnim {
        EAT,
        DRINK,
        BLOCK
    }

    public static class ZPMedicineProperties {
        private final FoodProperties foodProperties;
        private MedicineAnim medicineAnim;
        private int eatTime;
        private Supplier<SoundEvent> soundToPlayOnConsume;
        private boolean canBeAffectedOnOther;
        private @Nullable Consumer<LivingEntity> consumer;
        private int intoxication;

        public ZPMedicineProperties(@NotNull FoodProperties foodProperties) {
            this.setDefaults();
            this.foodProperties = foodProperties;
        }

        protected void setDefaults() {
            this.medicineAnim = MedicineAnim.EAT;
            this.eatTime = 32;
            this.intoxication = 0;
            this.soundToPlayOnConsume = null;
            this.canBeAffectedOnOther = false;
            this.consumer = null;
        }

        public FoodProperties getFoodProperties() {
            return this.foodProperties;
        }

        public int getEatTime() {
            return this.eatTime;
        }

        public int getIntoxication() {
            return this.intoxication;
        }

        public ZPMedicineProperties setIntoxication(int intoxication) {
            this.intoxication = intoxication;
            return this;
        }

        public boolean isCanBeAffectedOnOther() {
            return this.canBeAffectedOnOther;
        }

        public ZPMedicineProperties setCanBeAffectedOnOther(boolean canBeAffectedOnOther) {
            this.canBeAffectedOnOther = canBeAffectedOnOther;
            return this;
        }

        public Supplier<SoundEvent> getSoundToPlayOnConsume() {
            return this.soundToPlayOnConsume;
        }

        public ZPMedicineProperties setSoundToPlayOnConsume(@Nullable Supplier<SoundEvent> soundToPlayOnConsume) {
            this.soundToPlayOnConsume = soundToPlayOnConsume;
            return this;
        }

        public MedicineAnim getMedicineAnim() {
            return this.medicineAnim;
        }

        public ZPMedicineProperties setMedicineAnim(@NotNull MedicineAnim medicineAnim) {
            this.medicineAnim = medicineAnim;
            return this;
        }

        public ZPItemMedicine.ZPMedicineProperties setEatTime(int eatTime) {
            this.eatTime = eatTime;
            return this;
        }

        public @Nullable Consumer<LivingEntity> getConsumer() {
            return this.consumer;
        }

        public ZPMedicineProperties setConsumer(@Nullable Consumer<LivingEntity> consumer) {
            this.consumer = consumer;
            return this;
        }

        public int getConsumeTime() {
            return this.eatTime;
        }
    }
}
