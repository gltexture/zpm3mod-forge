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

package ru.gltexture.zpm3.modules.entity.events.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.engine.nbt.entity.ZPEntityNBT;

public class ZPEntitySpawnEvent implements ZPForgeEventHandlerClass {
    @SubscribeEvent
    public static void exec(@NotNull EntityJoinLevelEvent event) {
        ZPEntitySpawnEvent.registerNBT(event.getEntity());
        if (event.getEntity() instanceof Phantom) {
            event.setCanceled(true);
        }
        if (event.getEntity() instanceof WanderingTrader) {
            event.setCanceled(true);
        }
        if (event.getEntity() instanceof SkeletonHorse horse) {
            if (horse.isTrap()) {
                event.setCanceled(true);
            }
        }
    }

    public static void registerNBT(Entity entity) {
        CompoundTag persistentData = entity.getPersistentData();
        if (!persistentData.contains(ZPEntityNBT.PERSISTED_NBT_TAG)) {
            CompoundTag persisted = new CompoundTag();
            persistentData.put(ZPEntityNBT.PERSISTED_NBT_TAG, persisted);
        }
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
