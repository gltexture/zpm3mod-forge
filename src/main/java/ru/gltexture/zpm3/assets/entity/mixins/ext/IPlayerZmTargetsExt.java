package ru.gltexture.zpm3.assets.entity.mixins.ext;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;

import java.util.Iterator;
import java.util.TreeSet;

public interface IPlayerZmTargetsExt {
    TreeSet<ZPAbstractZombie> angryZombies();

    default boolean test(@NotNull ZPAbstractZombie zombie) {
        final int currSize = this.angryZombies().size();
        boolean flag = currSize < ZPConstants.MAX_ZOMBIES_CAN_BE_TARGETED_ON_PLAYER;
        if (flag) {
            this.angryZombies().add(zombie);
            return true;
        }
        final float distAbs = zombie.distanceTo((Entity) this) - (this.angryZombies().last().distanceTo((Entity) this));
        if (distAbs < -1.0f) {
            ZPAbstractZombie abstractZombie = this.angryZombies().pollLast();
            if (abstractZombie != null && abstractZombie.getTarget() == (Entity) this) {
                abstractZombie.setTarget(null);
            }
            flag = true;
        }
        if (flag) {
            this.angryZombies().add(zombie);
        }
        return flag;
    }

    default void filter() {
        Iterator<ZPAbstractZombie> it = this.angryZombies().iterator();
        while (it.hasNext()) {
            ZPAbstractZombie abstractZombie = it.next();
            final boolean isValid = abstractZombie != null && abstractZombie.isAlive();
            if (!isValid || abstractZombie.getTarget() != (Entity) this) {
                it.remove();
            }
        }
    }
}
