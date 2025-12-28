package ru.gltexture.zpm3.assets.entity.instances.mobs.zombies;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.commands.zones.ZPZoneChecks;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPDamageTypes;
import ru.gltexture.zpm3.assets.common.init.ZPEntityAttributes;
import ru.gltexture.zpm3.assets.common.instances.blocks.ZPAntiZombie;
import ru.gltexture.zpm3.assets.entity.mixins.ext.IPlayerZmTargetsExt;
import ru.gltexture.zpm3.assets.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.items.ZPItemFood;

import java.util.Arrays;
import java.util.function.Function;

public abstract class ZPAbstractZombie extends Monster {
    public static final EntityDataAccessor<Integer> DEBUG_TARGET_ID = SynchedEntityData.defineId(ZPAbstractZombie.class, EntityDataSerializers.INT); //DEBUG ONLY
    private static final EntityDataAccessor<Integer> SKIN_ID = SynchedEntityData.defineId(ZPAbstractZombie.class, EntityDataSerializers.INT);
    public int attackTicks;
    private int stopDespawning;
    private int eatingTime;
    private @Nullable ItemStack stolenStack;
    public boolean wantsToNullTarget;

    protected ZPAbstractZombie(EntityType<? extends ZPAbstractZombie> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.wantsToNullTarget = false;
    }

    @Override
    protected abstract void registerGoals();

    protected abstract int getTotalZombieSkins();

    @SuppressWarnings("all")
    public static boolean checkZombieSpawnRules(@NotNull EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, @NotNull MobSpawnType pSpawnType, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        return true;
    }

    @Override
    public double getPerceivedTargetDistanceSquareForMeleeAttack(@NotNull LivingEntity pEntity) {
        return super.getPerceivedTargetDistanceSquareForMeleeAttack(pEntity) * this.getAttributes().getBaseValue(ZPEntityAttributes.zm_attack_range_multiplier.get());
    }

    public static float getRandomSalt(float min, float max) {
        return min + ZPRandom.getRandom().nextFloat() * (max - min);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ZPAbstractZombie.SKIN_ID, 0);
        this.entityData.define(ZPAbstractZombie.DEBUG_TARGET_ID, -1);
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
        if (this.level() instanceof ServerLevel serverLevel) {
            if (ZPZoneChecks.INSTANCE.isZombieErasing(serverLevel, this)) {
                ZPAntiZombie.eraseMob(this);
                return;
            }
        }
        super.tick();
        if (!this.level().isClientSide) {
            this.entityData.set(ZPAbstractZombie.DEBUG_TARGET_ID, this.getTarget() == null ? -1 : this.getTarget().getId());
            this.attackTicks -= 1;

            if (this.getTarget() != null) {
                if (this.stopDespawning < ZPConstants.ZOMBIE_MAX_ANGRY_PERSISTENCE_TICKS) {
                    this.stopDespawning += 1;
                }
            } else {
                if (ZPConstants.ZOMBIE_STOP_DESPAWNING_IF_HAS_IMPORTANT_LOOT && this.hasImportantLoot()) {
                    this.stopDespawning = ZPConstants.ZOMBIE_MAX_ANGRY_PERSISTENCE_TICKS;
                } else {
                    if (this.stopDespawning > 0) {
                        this.stopDespawning -= 1;
                    }
                }
            }
            if (this.isEating()) {
                if (this.eatingTime++ >= ZPConstants.ZOMBIE_EATING_TIME) {
                    ItemStack stack = this.getMainHandItem();
                    if (!stack.isEmpty()) {
                        this.heal(stack.getItem().equals(Items.ROTTEN_FLESH) ? 4.0f : 2.0f);
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            this.setItemInHand(InteractionHand.MAIN_HAND, this.stolenStack == null ? ItemStack.EMPTY : this.stolenStack);
                            this.stolenStack = null;
                        }
                    }
                    this.eatingTime = 0;
                }
            }
        } else {
            if (this.isEating()) {
                this.spawnEatingParticlesAndSounds();
            }
        }
    }

    @Override
    public void setTarget(@Nullable LivingEntity pTarget) {
        if (pTarget instanceof IPlayerZmTargetsExt zmTargetsExt) {
            if (!zmTargetsExt.addInRegistry(this)) {
                return;
            }
        }
        //if (pTarget == null) {System.out.println("A");}
        super.setTarget(pTarget);
    }

    @Override
    public ItemStack equipItemIfPossible(ItemStack pStack) {
        EquipmentSlot equipmentslot = getEquipmentSlotForItem(pStack);
        ItemStack itemstack = this.getItemBySlot(equipmentslot);
        boolean flag = this.canReplaceCurrentItem(pStack, itemstack);
        if (equipmentslot.isArmor() && !flag) {
            equipmentslot = EquipmentSlot.MAINHAND;
            itemstack = this.getItemBySlot(equipmentslot);
            flag = itemstack.isEmpty();
        }

        if (flag && this.canHoldItem(pStack)) {
            double d0 = this.getEquipmentDropChance(equipmentslot);
            if (!itemstack.isEmpty() && (double)Math.max(this.random.nextFloat() - 0.1F, 0.0F) < d0) {
                this.spawnAtLocation(itemstack);
            } else {
                this.stolenStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            }

            if (equipmentslot.isArmor() && pStack.getCount() > 1) {
                ItemStack itemstack1 = pStack.copyWithCount(1);
                this.setItemSlotAndDropWhenKilled(equipmentslot, itemstack1);
                return itemstack1;
            } else {
                this.setItemSlotAndDropWhenKilled(equipmentslot, pStack);
                return pStack;
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void spawnEatingParticlesAndSounds() {
        ItemStack stack = this.getMainHandItem();
        SoundEvent sound = SoundEvents.GENERIC_EAT;
        if (stack.getUseAnimation() == UseAnim.DRINK) {
            sound = SoundEvents.GENERIC_DRINK;
        }
        if (stack.isEmpty()) {
            return;
        }

        for (int i = 0; i < 2; i++) {
            this.spawnEatingParticles(stack);
        }

        if (this.tickCount % 5 == 0) {
            this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), sound, SoundSource.NEUTRAL, 0.8F, 0.9F + this.random.nextFloat() * 0.2F, false);
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void spawnEatingParticles(ItemStack stack) {
        Vec3 f = this.getViewVector(1.0f);
        this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), this.getX() + f.x * 0.5f, this.getEyeY() + f.y * 0.5f - 0.25f, this.getZ() + f.z * 0.5f, (this.random.nextDouble() - 0.5D) * 0.1D, this.random.nextDouble() * 0.1D, (this.random.nextDouble() - 0.5D) * 0.1D);
    }

    public void aiStep() {
        super.aiStep();
    }

    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (this.level() instanceof ServerLevel serverLevel && pSource.type().equals(ZPDamageTypes.getDamageType(serverLevel, ZPDamageTypes.zp_bullet).get())) {
            pAmount *= ZPConstants.ZOMBIE_BULLET_DAMAGE_MULTIPLIER;
        }
        if (!super.hurt(pSource, pAmount)) {
            return false;
        } else {
            return this.level() instanceof ServerLevel;
        }
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity pEntity) {
        AttributeInstance attribute = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attribute == null) {
            return false;
        }
        final float baseDamage = (float) attribute.getBaseValue();
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        final float delta = Math.abs(baseDamage - f);
        f = baseDamage + delta * 0.375f;
        if (pEntity instanceof LivingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) pEntity).getMobType());
            f1 += (float) EnchantmentHelper.getKnockbackBonus(this);
        }
        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            pEntity.setSecondsOnFire(i * 4);
        }
        boolean flag = pEntity.hurt(this.damageSources().mobAttack(this), f);
        if (flag) {
            if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                ((LivingEntity) pEntity).knockback(f1 * 0.5F, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0F, 0.6));
            }
            if (pEntity instanceof Player player) {
                this.maybeDisableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }
            this.doEnchantDamageEffects(this, pEntity);
            this.setLastHurtMob(pEntity);
        }
        if (flag) {
            if (ZPRandom.getRandom().nextFloat() <= this.getAttributes().getBaseValue(ZPEntityAttributes.zm_random_effect_chance.get())) {
                if (pEntity instanceof LivingEntity livingEntity) {
                    ZPAbstractZombie.applyRandomEffect(livingEntity);
                }
            }
            this.attackTicks = 30;
        }
        return flag;
    }

    private void maybeDisableShield(Player pPlayer, ItemStack pMobItemStack, ItemStack pPlayerItemStack) {
        if (!pMobItemStack.isEmpty() && !pPlayerItemStack.isEmpty() && pMobItemStack.getItem() instanceof AxeItem && pPlayerItemStack.is(Items.SHIELD)) {
            float f = 0.25F + (float)EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
            if (this.random.nextFloat() < f) {
                pPlayer.getCooldowns().addCooldown(Items.SHIELD, 100);
                this.level().broadcastEntityEvent(pPlayer, (byte)30);
            }
        }
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

    public boolean killedEntity(@NotNull ServerLevel pLevel, @NotNull LivingEntity pEntity) {
        if (pEntity instanceof Player || pEntity instanceof Villager) {
            ZPCommonZombie zombie = new ZPCommonZombie(pLevel);
            zombie.moveTo(pEntity.getX(), pEntity.getY(), pEntity.getZ(), pEntity.getYRot(), pEntity.getXRot());
            pLevel.addFreshEntity(zombie);
        }
        return super.killedEntity(pLevel, pEntity);
    }

    protected float getStandingEyeHeight(@NotNull Pose pPose, @NotNull EntityDimensions pSize) {
        return 1.74F;
    }

    @Override
    protected boolean canReplaceCurrentItem(@NotNull ItemStack pCandidate, @NotNull ItemStack pExisting) {
        boolean flag = false;
        if (pExisting.isEmpty()) {
            flag = true;
        } else {
            if (!this.isFood(pExisting)) {
                if (this.isFood(pCandidate)) {
                    return true;
                }
            } else {
                if (!(pCandidate.getItem() instanceof ArmorItem)) {
                    return false;
                }
            }
        }
        if (super.canReplaceCurrentItem(pCandidate, pExisting)) {
            flag = true;
        }
        if (flag) {
            this.stopDespawning = 12000;
        }
        return flag;
    }

    public boolean isFood(ItemStack stack) {
        return stack.getItem() instanceof ZPItemFood || stack.getItem().isEdible();
    }

    public boolean isEating() {
        ItemStack itemStack = this.getItemInHand(InteractionHand.MAIN_HAND);
        return this.isFood(itemStack);
    }

    @Override
    protected @NotNull Vec3i getPickupReach() {
        return new Vec3i(2, 1, 2);
    }

    @Override
    public boolean canHoldItem(@NotNull ItemStack pStack) {
        return ZPConstants.ZOMBIE_CAN_PICK_UP_LOOT;
    }

    @Override
    public boolean wantsToPickUp(@NotNull ItemStack pStack) {
        return ZPConstants.ZOMBIE_CAN_PICK_UP_LOOT;
    }

    @Override
    public boolean canPickUpLoot() {
        return ZPConstants.ZOMBIE_CAN_PICK_UP_LOOT;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return this.getTarget() != null || this.stopDespawning > 0;
    }

    private boolean canBeDropped(EquipmentSlot equipmentSlot) {
        return this.getRandom().nextFloat() < this.getEquipmentDropChance(equipmentSlot);
    }

    protected abstract @NotNull LootTable getZmLootTable();

    protected void dropFromLootTable(@NotNull DamageSource pDamageSource, boolean pHitByPlayer) {
        LootTable loottable = this.getZmLootTable();
        LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel)this.level())).withParameter(LootContextParams.THIS_ENTITY, this).withParameter(LootContextParams.ORIGIN, this.position()).withParameter(LootContextParams.DAMAGE_SOURCE, pDamageSource).withOptionalParameter(LootContextParams.KILLER_ENTITY, pDamageSource.getEntity()).withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, pDamageSource.getDirectEntity());
        if (pHitByPlayer && this.lastHurtByPlayer != null) {
            lootparams$builder = lootparams$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer).withLuck(this.lastHurtByPlayer.getLuck());
        }

        LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
        loottable.getRandomItems(lootparams, this.getLootTableSeed(), this::spawnAtLocation);
    }

    public boolean hasImportantLoot() {
        ItemStack main = this.getMainHandItem();
        ItemStack off = this.getOffhandItem();

        if (main.getEquipmentSlot() != null && !main.isEmpty() && this.canBeDropped(this.getMainHandItem().getEquipmentSlot()) || (off.getEquipmentSlot() != null && !off.isEmpty() && this.canBeDropped(this.getOffhandItem().getEquipmentSlot()))) {
            return true;
        }

        for (ItemStack armor : this.getArmorSlots()) {
            if (armor != null && armor.getEquipmentSlot() != null && !armor.isEmpty() && this.canBeDropped(armor.getEquipmentSlot())) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    @SuppressWarnings("all")
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

        float f = pDifficulty.getSpecialMultiplier();
        this.randomEquipment(pDifficulty);

        double multiplier = 1.0;
        switch (pDifficulty.getDifficulty()) {
            case EASY -> multiplier = 0.65;
            case NORMAL -> multiplier = 1.0;
            case HARD -> multiplier = 1.2;
        }

        Arrays.fill(this.armorDropChances, ZPRandom.getRandom().nextFloat((float) (multiplier * 0.0025f)));
        Arrays.fill(this.handDropChances, ZPRandom.getRandom().nextFloat((float) (multiplier * 0.0025f)));

        this.setHealth((float) this.scaleAttributeByDifficulty(Attributes.MAX_HEALTH, multiplier));
        this.scaleAttributeByDifficulty(Attributes.FOLLOW_RANGE, multiplier);
        this.scaleAttributeByDifficulty(ZPEntityAttributes.zm_mining_speed.get(), multiplier);
        this.scaleAttributeByDifficulty(ZPEntityAttributes.zm_attack_range_multiplier.get(), multiplier);
        this.scaleAttributeByDifficulty(Attributes.ARMOR, multiplier);
        this.scaleAttributeByDifficulty(Attributes.ATTACK_DAMAGE, multiplier);
        this.scaleAttributeByDifficulty(ZPEntityAttributes.zm_random_effect_chance.get(), multiplier);

        this.randomizeAttributes();

        return pSpawnData;
    }

    protected void defaultRandomEquipment(@NotNull DifficultyInstance pDifficulty, @org.jetbrains.annotations.Nullable Function<EquipmentSlot, ItemStack> getArmorForSlot, float armorChance, @org.jetbrains.annotations.Nullable Function<EquipmentSlot, ItemStack> getItem, float itemChance) {
        EquipmentSlot[] armorSlots = {
                EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
        };

        if (getArmorForSlot != null && ZPRandom.getRandom().nextFloat() < armorChance) {
            for (EquipmentSlot slot : armorSlots) {
                ItemStack armor = getArmorForSlot.apply(slot);
                if (armor != null) {
                    armor.setDamageValue(ZPRandom.getRandom().nextInt(armor.getMaxDamage() / 2));
                    this.setItemSlot(slot, armor);
                }
            }
        }

        if (getItem != null && ZPRandom.getRandom().nextFloat() < itemChance) {
            ItemStack toolOrWeapon = getItem.apply(EquipmentSlot.MAINHAND);
            toolOrWeapon.setDamageValue(ZPRandom.getRandom().nextInt(toolOrWeapon.getMaxDamage() / 2));
            this.setItemSlot(EquipmentSlot.MAINHAND, toolOrWeapon);
        }
    }

    protected void randomEquipment(DifficultyInstance pDifficulty) {
    }

    public static void applyRandomEffect(LivingEntity entity) {
        if (entity == null || entity.level().isClientSide) {
            return;
        }

        if (entity instanceof Player && ZPRandom.getRandom().nextFloat() <= 0.03f * ZPConstants.ZOMBIE_PLAGUE_EFFECT_CHANCE_MULTIPLIER) {
            entity.addEffect(new MobEffectInstance(ZPMobEffects.zombie_plague.get(), ZPConstants.ZOMBIE_PLAGUE_VIRUS_EFFECT_TIME_TICKS, 0, false, false));
        }

        float roll = ZPRandom.getRandom().nextFloat();
        MobEffectInstance effect;

        if (roll < 0.30) {
            effect = new MobEffectInstance(MobEffects.HUNGER, 12000, 0);
        } else if (roll < 0.50) {
            effect = new MobEffectInstance(MobEffects.CONFUSION, 12000, 0);
        } else if (roll < 0.75) {
            effect = new MobEffectInstance(MobEffects.POISON, 400, 0);
        } else {
            effect = new MobEffectInstance(MobEffects.WEAKNESS, 400, 0);
        }

        entity.addEffect(effect);
    }
    protected void randomizeAttributes() {
    }

    protected double scaleAttributeByDifficulty(Attribute attribute, double multiplier) {
        AttributeInstance attributeInstance = this.getAttribute(attribute);
        if (attributeInstance != null) {
            double base = attributeInstance.getBaseValue();
            double newValue = base * multiplier;
            attributeInstance.setBaseValue(newValue);
            return newValue;
        }
        return -1.0f;
    }

    protected void addRandomAttributeValue(Attribute attribute, double value) {
        AttributeInstance attributeInstance = this.getAttribute(attribute);
        if (attributeInstance != null) {
            double base = attributeInstance.getBaseValue();
            double newValue = base + value;
            attributeInstance.setBaseValue(newValue);
        }
    }

    protected void dropCustomDeathLoot(@NotNull DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
    }
}