package ru.gltexture.zpm3.engine.objects.items;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZPLogger;

import java.util.Objects;
import java.util.function.Supplier;

public class ZPItemMedicine extends ZPItem {
    private final ZPItemMedicine.ZPMedicineProperties zpMedicineProperties;

    public ZPItemMedicine(@NotNull Properties pProperties, @NotNull FoodProperties foodProperties, @NotNull ZPItemMedicine.ZPMedicineProperties zpMedicineProperties) {
        super(pProperties.food(foodProperties));
        this.zpMedicineProperties = zpMedicineProperties;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!this.isEdible()) {
            return pStack;
        }

        return this.isEdible() ? this.consume(pLevel, pStack, pLivingEntity, pLivingEntity) : pStack;
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
                pLevel.playSound(null, entityToAffect.getX(), entityToAffect.getY(), entityToAffect.getZ(), this.getZpMedicineProperties().getSoundToPlayOnConsume().get(), SoundSource.NEUTRAL, 1.0F, 1.0F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.4F);
            }
            this.addEatEffect(pFood, pLevel, entityToAffect);
            if (!(entityToAffect instanceof Player) || !((Player) entityToAffect).getAbilities().instabuild) {
                if (pFood.isDamageableItem()) {
                    pFood.hurtAndBreak(1, entityWhoUsed, e -> {
                        e.broadcastBreakEvent(entityWhoUsed.getUsedItemHand());
                    });
                } else {
                    pFood.shrink(1);
                }
            }
            entityToAffect.gameEvent(GameEvent.EAT);
        }
        return pFood;
    }

    private void addEatEffect(ItemStack pFood, Level pLevel, LivingEntity pLivingEntity) {
        Item item = pFood.getItem();
        if (item.isEdible()) {
            for(Pair<MobEffectInstance, Float> pair : Objects.requireNonNull(pFood.getFoodProperties(pLivingEntity)).getEffects()) {
                if (!pLevel.isClientSide && pair.getFirst() != null && pLevel.random.nextFloat() < pair.getSecond()) {
                    MobEffectInstance mobEffectInstance = new MobEffectInstance(pair.getFirst());
                    if (mobEffectInstance.getDuration() < 0) {
                        pLivingEntity.removeEffect(mobEffectInstance.getEffect());
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
        private MedicineAnim medicineAnim;
        private int eatTime;
        private Supplier<SoundEvent> soundToPlayOnConsume;
        private boolean canBeAffectedOnOther;

        public ZPMedicineProperties() {
            this.setDefaults();
        }

        protected void setDefaults() {
            this.medicineAnim = MedicineAnim.EAT;
            this.eatTime = 48;
            this.soundToPlayOnConsume = null;
            this.canBeAffectedOnOther = false;
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

        public int getConsumeTime() {
            return this.eatTime;
        }
    }
}
