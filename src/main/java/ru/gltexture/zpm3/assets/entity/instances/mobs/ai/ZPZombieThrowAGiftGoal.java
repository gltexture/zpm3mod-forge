package ru.gltexture.zpm3.assets.entity.instances.mobs.ai;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPEntityAttributes;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.entities.ZPThrowableEntity;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.EnumSet;
import java.util.function.Supplier;

public class ZPZombieThrowAGiftGoal extends Goal {
    protected final ZPAbstractZombie mob;
    private LivingEntity target;
    private int ticksBeforeThrowSomething;
    private final Pair<Supplier<ZPThrowableEntity>, Integer>[] weightedPairsArray;
    private float zombiesAroundMul;
    private float throwAGiftChance;

    @SafeVarargs
    public ZPZombieThrowAGiftGoal(ZPAbstractZombie pMob, @NotNull Pair<Supplier<ZPThrowableEntity>, Integer>... weightedPairsArray) {
        this.mob = pMob;
        this.weightedPairsArray = weightedPairsArray;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    protected float getThrowChance(@NotNull ZPAbstractZombie abstractZombie) {
        return (float) Mth.clamp(abstractZombie.getAttributes().getBaseValue(ZPEntityAttributes.zm_throw_a_gift_chance.get()), 0.0f, 1.0f);
    }

    public boolean canUse() {
        this.target = this.mob.getTarget();
        if (this.mob.level().getDifficulty() == Difficulty.EASY || this.target == null || !this.target.isAlive()) {
            return false;
        }
        float dy = (float) (this.target.position().y - this.mob.position().y);
        if (dy <= 4.0f) {
            return false;
        }
        if (this.ticksBeforeThrowSomething-- <= 0) {
            final int zombiesAround = this.computeZombiesAround(this.mob.level());
            this.zombiesAroundMul = (float) (Math.pow(zombiesAround, Math.E) * 0.01f);
            this.zombiesAroundMul = Mth.clamp(this.zombiesAroundMul, 1.0f, 100.0f);
            this.throwAGiftChance = this.zombiesAroundMul * this.getThrowChance(this.mob);
            float defaultCooldown = 400;
            defaultCooldown -= 250 * (1.0f - this.throwAGiftChance);
            defaultCooldown *= ZPConstants.ZOMBIE_THROW_A_GIFT_TRY_COOLDOWN_MULTIPLIER;
            this.ticksBeforeThrowSomething = (int) (defaultCooldown + ZPRandom.getRandom().nextInt(101));
            return ZPRandom.getRandom().nextFloat() <= this.throwAGiftChance;
        }
        return false;
    }

    private int computeZombiesAround(Level level) {
        return level.getEntitiesOfClass(ZPAbstractZombie.class, this.mob.getBoundingBox().inflate(3.0f, 0.0f, 3.0f), (e) -> !e.equals(this.mob)).size();
    }

    public void start() {
        super.start();
        ZPThrowableEntity throwable = this.pickRandomWeighted();
        if (throwable == null) {
            return;
        }
        this.mob.level().playSound(null, this.mob.getX(), this.mob.getY(), this.mob.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.HOSTILE, 1.25f, 0.6f + this.mob.getRandom().nextFloat() * 0.2f);
        final Vec3 dir = this.target.position().subtract(mob.position()).normalize();
        final float pitch = (float) Math.toDegrees(-Math.asin(dir.y));
        final float yaw = (float) Math.toDegrees(Math.atan2(dir.z, dir.x)) - 90f;
        throwable.setPos(mob.position().add(0.0f, mob.getEyeHeight(), 0.0f));
        throwable.shootFromRotation(this.mob, pitch, yaw, 0.0f, 0.5f + ZPRandom.instance.randomFloat(0.2f) + ZPRandom.instance.randomFloat(0.75f + this.throwAGiftChance * 0.5f), 6.0f + ZPRandom.instance.randomFloat(16.0f - (this.throwAGiftChance * 8.0f)));
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