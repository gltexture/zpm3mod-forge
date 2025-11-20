package ru.gltexture.zpm3.assets.common.instances.entities.mobs.ai;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPEntities;
import ru.gltexture.zpm3.assets.common.init.ZPEntityAttributes;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.common.instances.entities.throwables.ZPPlateEntity;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.entities.ZPThrowableEntity;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.EnumSet;
import java.util.function.Supplier;

public class ZPZombieThrowAGiftGoal extends Goal {
    protected final ZPAbstractZombie mob;

    private int ticksBeforeThrowSomething;
    private final Pair<Supplier<ZPThrowableEntity>, Integer>[] weightedPairsArray;
    private float zombiesAroundMul;

    @SafeVarargs
    public ZPZombieThrowAGiftGoal(ZPAbstractZombie pMob, @NotNull Pair<Supplier<ZPThrowableEntity>, Integer>... weightedPairsArray) {
        this.mob = pMob;
        this.weightedPairsArray = weightedPairsArray;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    protected float getThrowChance(@NotNull ZPAbstractZombie abstractZombie) {
        return (float) abstractZombie.getAttributes().getBaseValue(ZPEntityAttributes.zm_throw_a_gift_chance.get());
    }

    public boolean canUse() {
        if (this.mob.level().getDifficulty() == Difficulty.EASY || this.mob.getTarget() == null || !this.mob.getTarget().isAlive()) {
            return false;
        }
        float dy = (float) (this.mob.getTarget().position().y - this.mob.position().y);
        if (dy <= 4.0f) {
            return false;
        }
        if (this.ticksBeforeThrowSomething-- <= 0) {
            int zombiesAround = this.computeZombiesAround(this.mob.level());
            this.zombiesAroundMul = 1.0f + (Math.min(zombiesAround, 10) * 0.05f);
            if (ZPRandom.getRandom().nextFloat() <= this.getThrowChance(this.mob) * this.zombiesAroundMul) {
                this.ticksBeforeThrowSomething = (int) ((ZPConstants.ZOMBIE_THROW_A_GIFT_TRY_DEFAULT_COOLDOWN - this.zombiesAroundMul * 80) + ZPRandom.getRandom().nextInt(201));
                return true;
            }
        }
        return false;
    }

    private int computeZombiesAround(Level level) {
        return level.getEntitiesOfClass(ZPAbstractZombie.class, this.mob.getBoundingBox().inflate(2.0f, 0.0f, 2.0f), (e) -> !e.equals(this.mob)).size();
    }

    public void start() {
        super.start();
        LivingEntity target = mob.getTarget();
        if (target == null) {
            return;
        }

        ZPThrowableEntity throwable = this.pickRandomWeighted();
        if (throwable == null) {
            return;
        }
        this.mob.level().playSound(null, this.mob.getX(), this.mob.getY(), this.mob.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.HOSTILE, 1.25f, 0.6f + this.mob.getRandom().nextFloat() * 0.2f);
        final Vec3 dir = mob.getTarget().position().subtract(mob.position()).normalize();
        final float pitch = (float) Math.toDegrees(-Math.asin(dir.y));
        final float yaw = (float) Math.toDegrees(Math.atan2(dir.z, dir.x)) - 90f;
        throwable.setPos(mob.position().add(0.0f, mob.getEyeHeight(), 0.0f));
        throwable.shootFromRotation(this.mob, pitch, yaw, 0.0f, 0.5f + ZPRandom.getRandom().nextFloat(0.2f) + ZPRandom.getRandom().nextFloat(0.75f * this.zombiesAroundMul), 6.0f + ZPRandom.getRandom().nextFloat(14.0f * this.zombiesAroundMul));
        throwable.setOwner(this.mob);
        this.mob.level().addFreshEntity(throwable);
        mob.swing(InteractionHand.MAIN_HAND, true);
    }

    private ZPThrowableEntity pickRandomWeighted() {
        int totalWeight = 0;
        for (Pair<Supplier<ZPThrowableEntity>, Integer> p : this.weightedPairsArray) {
            totalWeight += p.second();
        }

        int roll = ZPRandom.getRandom().nextInt(totalWeight);
        int cumulative = 0;

        for (Pair<Supplier<ZPThrowableEntity>, Integer> p : this.weightedPairsArray) {
            cumulative += p.second();
            if (roll < cumulative) {
                return p.first().get();
            }
        }

        return null;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void stop() {
        super.stop();
    }
}