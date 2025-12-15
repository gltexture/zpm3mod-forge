package ru.gltexture.zpm3.assets.entity.instances.mobs.ai.attack;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.entity.instances.mobs.ai.paths.ZPPathFindingGoal;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;

public class ZPZombieAttackGoalRewrite extends ZPPathFindingGoal {
    private final ZPAbstractZombie zombie;
    private int raiseArmTicks;

    public ZPZombieAttackGoalRewrite(ZPAbstractZombie pZombie, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pZombie, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.zombie = pZombie;
    }

    public void start() {
        super.start();
        this.raiseArmTicks = 0;
    }

    public void stop() {
        super.stop();
        this.zombie.setAggressive(false);
    }

    public void tick() {
        super.tick();
        ++this.raiseArmTicks;
        this.zombie.setAggressive(this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2);
    }

    protected void checkAndPerformAttack(@NotNull LivingEntity pEnemy, double pDistToEnemySqr) {
        if (this.mob.hasLineOfSight(pEnemy)) {
            super.checkAndPerformAttack(pEnemy, pDistToEnemySqr);
        }
    }
}