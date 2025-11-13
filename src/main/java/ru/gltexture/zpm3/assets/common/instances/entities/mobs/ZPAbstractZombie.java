package ru.gltexture.zpm3.assets.common.instances.entities.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class ZPAbstractZombie extends Monster {
    private static final EntityDataAccessor<Integer> SKIN_ID = SynchedEntityData.defineId(ZPAbstractZombie.class, EntityDataSerializers.INT);

    protected ZPAbstractZombie(EntityType<? extends ZPAbstractZombie> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected abstract void registerGoals();

    protected abstract int getTotalZombieSkins();

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(ForgeMod.SWIM_SPEED.get(), 1.0f)
                .add(Attributes.MAX_HEALTH, 20.0f)
                .add(Attributes.FOLLOW_RANGE, 35.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.23f)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ARMOR, 2.0f)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ZPAbstractZombie.SKIN_ID, 0);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (this.getSkinID() == 0 && !this.level().isClientSide) {
            this.entityData.set(ZPAbstractZombie.SKIN_ID, ZPRandom.getRandom().nextInt(this.getTotalZombieSkins()));
        }
    }

    public int getSkinID() {
        return this.entityData.get(ZPAbstractZombie.SKIN_ID);
    }

    public void tick() {
        super.tick();
    }

    public void aiStep() {
        super.aiStep();
    }

    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (!super.hurt(pSource, pAmount)) {
            return false;
        } else {
            return this.level() instanceof ServerLevel;
        }
    }

    public boolean doHurtTarget(@NotNull Entity pEntity) {
        return super.doHurtTarget(pEntity);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return SoundEvents.ZOMBIE_HURT;
    }

    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ZOMBIE_STEP;
    }

    protected void playStepSound(@NotNull BlockPos pPos, @NotNull BlockState pBlock) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SkinId", this.getSkinID());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(ZPAbstractZombie.SKIN_ID, tag.getInt("SkinId"));
    }

    protected void populateDefaultEquipmentSlots(@NotNull RandomSource pRandom, @NotNull DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        if (pRandom.nextFloat() < (this.level().getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
            int i = pRandom.nextInt(3);
            if (i == 0) {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            } else {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
            }
        }
    }

    public boolean killedEntity(@NotNull ServerLevel pLevel, @NotNull LivingEntity pEntity) {
        return super.killedEntity(pLevel, pEntity);
    }

    protected float getStandingEyeHeight(@NotNull Pose pPose, @NotNull EntityDimensions pSize) {
        return 1.74F;
    }

    public boolean canHoldItem(@NotNull ItemStack pStack) {
        return super.canHoldItem(pStack);
    }

    public boolean wantsToPickUp(@NotNull ItemStack pStack) {
        return super.wantsToPickUp(pStack);
    }

    @Nullable
    @SuppressWarnings("all")
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        RandomSource randomsource = pLevel.getRandom();
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

        float f = pDifficulty.getSpecialMultiplier();
        this.setCanPickUpLoot(true);
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        this.populateDefaultEquipmentEnchantments(randomsource, pDifficulty);

        this.handleAttributes(f);
        return pSpawnData;
    }

    protected void handleAttributes(float pDifficulty) {
        Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * (double)0.05F, AttributeModifier.Operation.ADDITION));
        double d0 = this.random.nextDouble() * 1.5D * (double)pDifficulty;
        if (d0 > 1.0D) {
            Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).addPermanentModifier(new AttributeModifier("Random zombie-spawn bonus", d0, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    protected void dropCustomDeathLoot(@NotNull DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
    }
}