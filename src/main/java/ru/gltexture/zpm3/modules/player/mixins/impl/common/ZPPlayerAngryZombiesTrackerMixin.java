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

package ru.gltexture.zpm3.modules.player.mixins.impl.common;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IPlayerZmTargetsExt;

import java.util.*;

@Mixin(ServerPlayer.class)
public abstract class ZPPlayerAngryZombiesTrackerMixin implements IPlayerZmTargetsExt {
    @Unique List<ZPAbstractZombie> zpm3forge$angryZombies = new ArrayList<>();
    @Unique Set<ZPAbstractZombie> zpm3forge$angryZombiesRegistry = new HashSet<>();

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        this.filter();
    }

    @Override
    public List<ZPAbstractZombie> zpm3forge$angryZombies() {
        return this.zpm3forge$angryZombies;
    }

    @Override
    public Set<ZPAbstractZombie> zpm3forge$angryZombiesRegistrySet() {
        return this.zpm3forge$angryZombiesRegistry;
    }
}