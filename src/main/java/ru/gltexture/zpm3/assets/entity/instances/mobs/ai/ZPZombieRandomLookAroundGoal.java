package ru.gltexture.zpm3.assets.entity.instances.mobs.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class ZPZombieRandomLookAroundGoal extends Goal {
    private final Mob mob;
    private double relX;
    private double relZ;
    private int lookTime;

    public ZPZombieRandomLookAroundGoal(Mob pMob) {
        this.mob = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return this.mob.getTarget() == null && this.mob.getRandom().nextFloat() < 0.02F;
    }

    public boolean canContinueToUse() {
        return this.lookTime >= 0 && this.mob.getTarget() == null;
    }

    public void start() {
        double d0 = (Math.PI * 2D) * this.mob.getRandom().nextDouble();
        this.relX = Math.cos(d0);
        this.relZ = Math.sin(d0);
        this.lookTime = 20 + this.mob.getRandom().nextInt(20);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        --this.lookTime;
        this.mob.getLookControl().setLookAt(this.mob.getX() + this.relX, this.mob.getEyeY(), this.mob.getZ() + this.relZ);
    }
}