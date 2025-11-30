package ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPEntities;
import ru.gltexture.zpm3.assets.common.init.ZPEntityAttributes;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.ai.*;
import ru.gltexture.zpm3.assets.common.instances.entities.throwables.*;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.List;
import java.util.Objects;

public class ZPDogZombie extends ZPAbstractZombie {
    private float baseMovementSpeed;

    public ZPDogZombie(Level pLevel) {
        this(ZPEntities.zp_dog_zombie_entity.get(), pLevel);
    }

    public ZPDogZombie(EntityType<ZPDogZombie> zpRockEntityEntityType, Level level) {
        super(zpRockEntityEntityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new ZPZombieAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, (new ZPZombieEatingGoal(this)));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new ZPZombieRandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new ZPZombieHurtByMobGoal(this)));
        this.targetSelector.addGoal(2, new ZPZombieNearestAttackableTargetPlayerGoal(this, 1.0f, true, 10, (e) -> true));
        this.targetSelector.addGoal(3, new ZPZombieNearestAttackableTargetLivingGoal(this, List.of(AbstractVillager.class), 0.5f, true, 20, (e) -> true));
        this.targetSelector.addGoal(4, new ZPZombieNearestAttackableTargetLivingGoal(this, List.of(Cow.class, IronGolem.class, Horse.class, Sheep.class, Pig.class), 0.4f, false, 60, (e) -> true));
        this.targetSelector.addGoal(5, new ZPZombieAngryGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(ForgeMod.SWIM_SPEED.get(), 1.75f)
                .add(Attributes.MAX_HEALTH, 20.0f)
                .add(Attributes.FOLLOW_RANGE, ZPConstants.ZOMBIE_FOLLOW_RANGE)
                .add(Attributes.MOVEMENT_SPEED, 0.34f)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ARMOR, 2.0f)
                .add(ZPEntityAttributes.zm_attack_range_multiplier.get(), 0.9f)
                .add(ZPEntityAttributes.zm_mining_speed.get(), 0.0f)
                .add(ZPEntityAttributes.zm_random_effect_chance.get(), 0.025f)
                .add(ZPEntityAttributes.zm_throw_a_gift_chance.get(), 0.0f);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.lastHurtByPlayerTime > 60) {
            Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(this.baseMovementSpeed * 0.75f);
        } else {
            Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(this.baseMovementSpeed);
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void spawnEatingParticles(ItemStack stack) {
        Vec3 f = this.getViewVector(1.0f);
        this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), this.getX() + f.x * 0.6f, this.getEyeY() + f.y * 0.6f - 0.3f, this.getZ() + f.z * 0.6f, (this.random.nextDouble() - 0.5D) * 0.1D, this.random.nextDouble() * 0.1D, (this.random.nextDouble() - 0.5D) * 0.1D);
    }

    protected float getStandingEyeHeight(@NotNull Pose pPose, @NotNull EntityDimensions pSize) {
        return pSize.height * 0.8F;
    }

    @Override
    protected boolean canReplaceCurrentItem(@NotNull ItemStack pCandidate, @NotNull ItemStack pExisting) {
        return !this.isFood(pExisting) && this.isFood(pCandidate);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.WOLF_GROWL;
    }

    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return SoundEvents.WOLF_HURT;
    }

    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    @Override
    protected int getTotalZombieSkins() {
        return ZPConstants.TOTAL_DOG_ZOMBIE_TEXTURES;
    }

    @Override
    protected @NotNull LootTable getZmLootTable() {
        return new LootTable.Builder().withPool(new LootPool.Builder().setRolls(UniformGenerator.between(0, 2)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH))).build();
    }

    @Override
    protected void randomizeAttributes() {
        this.addRandomAttributeValue(Attributes.MAX_HEALTH, ZPAbstractZombie.getRandomSalt(0.0f, 21.0f));
        this.addRandomAttributeValue(Attributes.FOLLOW_RANGE, ZPAbstractZombie.getRandomSalt(-2.0f, 2.0f));
        this.addRandomAttributeValue(Attributes.MOVEMENT_SPEED, ZPAbstractZombie.getRandomSalt(-0.005f, 0.01f));
        this.addRandomAttributeValue(Attributes.ATTACK_DAMAGE, ZPAbstractZombie.getRandomSalt(0.0f, 0.5f));
        this.addRandomAttributeValue(ZPEntityAttributes.zm_attack_range_multiplier.get(), ZPAbstractZombie.getRandomSalt(-0.05f, 0.05f));
        this.addRandomAttributeValue(ZPEntityAttributes.zm_random_effect_chance.get(), ZPAbstractZombie.getRandomSalt(-0.005f, 0.005f));

        this.baseMovementSpeed = (float) Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).getBaseValue();
    }

    @Override
    public int getExperienceReward() {
        return super.getExperienceReward();
    }
}