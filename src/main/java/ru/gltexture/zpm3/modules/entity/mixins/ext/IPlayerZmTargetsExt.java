/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.entity.mixins.ext;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPZombieConfig;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;

import java.util.Iterator;
import java.util.*;

public interface IPlayerZmTargetsExt {
    Set<ZPAbstractZombie> zpm3forge$angryZombiesRegistrySet();
    List<ZPAbstractZombie> zpm3forge$angryZombies();

    default float dist(ZPAbstractZombie z) {
        return z.distanceTo((Entity) this);
    }

    default void sortByDistance() {
        this.zpm3forge$angryZombies().sort(Comparator.comparingDouble(this::dist));
    }

    default boolean addInRegistry(@NotNull ZPAbstractZombie zombie) {
        if (!this.zpm3forge$angryZombiesRegistrySet().add(zombie)) {
            return true;
        }
        List<ZPAbstractZombie> list = this.zpm3forge$angryZombies();
        int max = ZPZombieConfig.MAX_ZOMBIES_TARGETED_ON_PLAYER.getVar();
        if (list.size() < max) {
            list.add(zombie);
            this.sortByDistance();
            return true;
        }
        ZPAbstractZombie farthest = list.get(list.size() - 1);
        float distDiff = this.dist(zombie) - this.dist(farthest);
        float minDist = list.size() == 1 ? -1.0f : Math.min(this.dist(list.get(0)) * ZPZombieConfig.CLOSEST_ZOMBIE_SWAP_TARGET_PERCENTAGE.getVar(), 8.0f);
        if (distDiff < -minDist) {
            list.remove(list.size() - 1);
            this.zpm3forge$angryZombiesRegistrySet().remove(farthest);
            if (farthest.getTarget() == (Entity) this) {
                farthest.setTarget(null);
            }
            list.add(zombie);
            this.sortByDistance();
            return true;
        }
        this.zpm3forge$angryZombiesRegistrySet().remove(zombie);
        return false;
    }

    default boolean test(@NotNull ZPAbstractZombie zombie) {
        if (this.zpm3forge$angryZombiesRegistrySet().contains(zombie)) {
            return true;
        }
        List<ZPAbstractZombie> list = this.zpm3forge$angryZombies();
        int max = ZPZombieConfig.MAX_ZOMBIES_TARGETED_ON_PLAYER.getVar();
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

        Iterator<ZPAbstractZombie> it = this.zpm3forge$angryZombies().iterator();
        Entity self = (Entity) this;
        while (it.hasNext()) {
            ZPAbstractZombie z = it.next();
            boolean valid = z != null && z.isAlive() && z.getTarget() == self;
            if (!valid) {
                it.remove();
                this.zpm3forge$angryZombiesRegistrySet().remove(z);
            }
        }
    }
}
