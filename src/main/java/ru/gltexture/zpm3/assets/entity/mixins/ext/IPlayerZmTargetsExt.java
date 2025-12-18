package ru.gltexture.zpm3.assets.entity.mixins.ext;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;

import java.util.Iterator;
import java.util.*;

public interface IPlayerZmTargetsExt {
    Set<ZPAbstractZombie> angryZombiesRegistrySet();
    List<ZPAbstractZombie> angryZombies();

    default float dist(ZPAbstractZombie z) {
        return z.distanceTo((Entity) this);
    }

    default void sortByDistance() {
        this.angryZombies().sort(Comparator.comparingDouble(this::dist));
    }

    default boolean addInRegistry(@NotNull ZPAbstractZombie zombie) {
        if (!this.angryZombiesRegistrySet().add(zombie)) {
            return true;
        }
        List<ZPAbstractZombie> list = this.angryZombies();
        int max = ZPConstants.MAX_ZOMBIES_CAN_BE_TARGETED_ON_PLAYER;
        if (list.size() < max) {
            list.add(zombie);
            this.sortByDistance();
            return true;
        }
        ZPAbstractZombie farthest = list.get(list.size() - 1);
        float distDiff = this.dist(zombie) - this.dist(farthest);
        float minDist = list.size() == 1 ? -1.0f : Math.min(this.dist(list.get(0)) * ZPConstants.CLOSEST_ZOMBIE_SWAP_TARGET_PERCENTAGE, 8.0f);
        if (distDiff < -minDist) {
            list.remove(list.size() - 1);
            this.angryZombiesRegistrySet().remove(farthest);
            if (farthest.getTarget() == (Entity) this) {
                farthest.setTarget(null);
            }
            list.add(zombie);
            this.sortByDistance();
            return true;
        }
        this.angryZombiesRegistrySet().remove(zombie);
        return false;
    }

    default boolean test(@NotNull ZPAbstractZombie zombie) {
        if (this.angryZombiesRegistrySet().contains(zombie)) {
            return true;
        }
        List<ZPAbstractZombie> list = this.angryZombies();
        int max = ZPConstants.MAX_ZOMBIES_CAN_BE_TARGETED_ON_PLAYER;
        if (list.size() < max) {
            return true;
        }
        if (list.isEmpty()) {
            return true;
        }
        ZPAbstractZombie farthest = list.get(list.size() - 1);
        float distDiff = this.dist(zombie) - this.dist(farthest);
        float minDist = list.size() == 1 ? -1.0f : Math.min(this.dist(list.get(0)) * 0.75f, 8.0f);
        return distDiff < -minDist;
    }

    default void filter() {
        //System.out.println(this.angryZombiesRegistrySet().size() + " " + this.angryZombies().size());

        Iterator<ZPAbstractZombie> it = this.angryZombies().iterator();
        Entity self = (Entity) this;
        while (it.hasNext()) {
            ZPAbstractZombie z = it.next();
            boolean valid = z != null && z.isAlive() && z.getTarget() == self;
            if (!valid) {
                it.remove();
                this.angryZombiesRegistrySet().remove(z);
            }
        }
    }
}
