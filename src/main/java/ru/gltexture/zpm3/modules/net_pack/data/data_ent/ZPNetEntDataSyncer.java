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

package ru.gltexture.zpm3.modules.net_pack.data.data_ent;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.modules.net_pack.data.ZPAccessorsNetEntSyncUtil;
import ru.gltexture.zpm3.modules.net_pack.data.accessors.ZPNetDataAccessor;
import ru.gltexture.zpm3.modules.net_pack.packets.S2C.ZPSyncEntityDataAllVarsPacket;
import ru.gltexture.zpm3.modules.net_pack.packets.S2C.ZPSyncEntityDataVarPacket;
import ru.gltexture.zpm3.modules.net_pack.packets.S2C.ZPSyncGroupOfEntitiesDataVarsPacket;

import java.util.*;

public class ZPNetEntDataSyncer implements IZPNetEntDataSyncer {
    private final WeakHashMap<Entity, ZPNetEntityData> thisTick_dirtyValues;
    private final WeakHashMap<Entity, ZPNetEntityData> container_VarsPerEntity;
    private final Map<ServerPlayer, List<Entity>> serverPlayer_dirtyTrackedEntities;
    private final Int2ObjectMap<ZPNetDataAccessor<?>> dataAccessors_idMap;
    private final Map<Class<? extends Entity>, List<ZPNetDataAccessor<?>>> dataAccessors_Registry;

    public ZPNetEntDataSyncer() {
        this.thisTick_dirtyValues = new WeakHashMap<>();
        this.container_VarsPerEntity = new WeakHashMap<>();
        this.serverPlayer_dirtyTrackedEntities = new HashMap<>();
        this.dataAccessors_Registry = new HashMap<>();
        this.dataAccessors_idMap = new Int2ObjectOpenHashMap<>();
    }

    public <R> void ENCODE(@NotNull ZPNetDataVar<R> var, int accessorId, @NotNull FriendlyByteBuf buffer) {
        ZPAccessorsNetEntSyncUtil.ENCODE(var, accessorId, buffer, this::getAccessorUnsafe);
    }

    public <R> ZPNetDataVar<R> DECODE(int accessorId, @NotNull FriendlyByteBuf buffer) {
        return ZPAccessorsNetEntSyncUtil.DECODE(accessorId, buffer, this::getAccessorUnsafe);
    }

    public void ENCODE_ALL(@NotNull ZPNetEntityData entityData, @NotNull FriendlyByteBuf buffer) {
        ZPAccessorsNetEntSyncUtil.ENCODE_ALL(entityData, buffer, this::getAccessorUnsafe);
    }

    public @NotNull ZPNetEntityData DECODE_ALL(@NotNull FriendlyByteBuf buffer) {
        return ZPAccessorsNetEntSyncUtil.DECODE_ALL(buffer, this::getAccessorUnsafe);
    }

    public void ENCODE_ALL_ENTITIES(@NotNull Int2ObjectMap<ZPNetEntityData> entities, @NotNull FriendlyByteBuf buffer) {
        ZPAccessorsNetEntSyncUtil.ENCODE_ALL_ENTITIES(entities, buffer, this::getAccessorUnsafe);
    }

    public Int2ObjectMap<ZPNetEntityData> DECODE_ALL_ENTITIES(@NotNull FriendlyByteBuf buffer) {
        return ZPAccessorsNetEntSyncUtil.DECODE_ALL_ENTITIES(buffer, this::getAccessorUnsafe);
    }

    public void initializeOnEntity(@NotNull Entity entity) {
        if (this.container_VarsPerEntity.containsKey(entity)) {
            ZPLogger.warn(this.getClass().getSimpleName() + " : Entity " + entity.getId() + " was initialized twice.");
            return;
        }
        ZPNetEntityData data = new ZPNetEntityData(new Int2ObjectOpenHashMap<>());
        this.container_VarsPerEntity.put(entity, data);
        this.dataAccessors_Registry.forEach((aClass, accessors) -> {
            if (aClass.isAssignableFrom(entity.getClass())) {
                for (ZPNetDataAccessor<?> accessor : accessors) {
                    data.vars().put(accessor.getGlobalId(), accessor.createDefault().get());
                }
            }
        });
    }

    public void clearEntity(@NotNull Entity entity) {
        this.container_VarsPerEntity.remove(entity);
    }

    @SuppressWarnings("all")
    public <T> ZPNetDataAccessor<T> getAccessorUnsafe(int id) {
        ZPNetDataAccessor<?> accessor = this.dataAccessors_idMap.get(id);
        if (accessor == null) {
            ZPLogger.error(this.getClass().getSimpleName() + " : " + "Unknown accessor id: " + id);
            return null;
        }
        return (ZPNetDataAccessor<T>) accessor;
    }

    public void syncDirtyValues() {
        {
            {
                final Iterator<Map.Entry<Entity, ZPNetEntityData>> iterator = this.thisTick_dirtyValues.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Entity, ZPNetEntityData> entry = iterator.next();
                    final Entity entity = entry.getKey();
                    this.broadcastDirtyFromServer(entity, entry.getValue());
                    iterator.remove();
                }
            }

            {
                Iterator<Map.Entry<ServerPlayer, List<Entity>>> iterator = this.serverPlayer_dirtyTrackedEntities.entrySet().iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<ServerPlayer, List<Entity>> entry = iterator.next();
                    final ServerPlayer player = entry.getKey();
                    final List<Entity> entities = entry.getValue();
                    final Int2ObjectMap<ZPNetEntityData> data = new Int2ObjectOpenHashMap<>(entities.size());
                    for (Entity entity : entities) {
                        ZPNetEntityData entityData = this.getEntityDataVars(entity);
                        if (entityData != null) {
                            data.put(entity.getId(), entityData);
                        }
                    }
                    if (!data.isEmpty()) {
                        ZombiePlague3.netServer().sendToPlayer(new ZPSyncGroupOfEntitiesDataVarsPacket(data), player);
                    }
                    iterator.remove();
                }
            }
        }
    }

    public void broadcastDirtyEntitiesFromServer(@NotNull Entity entity, @NotNull ZPNetEntityData dirtyData) {
        {
            if (dirtyData.vars().isEmpty()) {
                return;
            }
            ZPSyncEntityDataAllVarsPacket packet = new ZPSyncEntityDataAllVarsPacket(entity.getId(), dirtyData);
            if (entity instanceof ServerPlayer player) {
                ZombiePlague3.netServer().sendToAllTrackingAndSelf(player, packet);
            } else {
                ZombiePlague3.netServer().sendToAllTracking(entity, packet);
            }
        }
    }

    public void broadcastFromServer(@NotNull Entity entity, @NotNull ZPNetDataAccessor<?> accessor, @NotNull ZPNetDataVar<?> value) {
        {
            final ZPSyncEntityDataVarPacket packet = new ZPSyncEntityDataVarPacket(entity.getId(), accessor.getGlobalId(), value);
            if (entity instanceof ServerPlayer player) {
                ZombiePlague3.netServer().sendToAllTrackingAndSelf(player, packet);
            } else {
                ZombiePlague3.netServer().sendToAllTracking(entity, packet);
            }
        }
    }

    public void broadcastAllFromServer(@NotNull Entity entity) {
        {
            ZPNetEntityData entityData = this.container_VarsPerEntity.get(entity);
            if (entityData == null) {
                return;
            }
            if (entityData.vars().isEmpty()) {
                return;
            }
            ZPSyncEntityDataAllVarsPacket packet = new ZPSyncEntityDataAllVarsPacket(entity.getId(), entityData);
            if (entity instanceof ServerPlayer player) {
                ZombiePlague3.netServer().sendToAllTrackingAndSelf(player, packet);
            } else {
                ZombiePlague3.netServer().sendToAllTracking(entity, packet);
            }
        }
    }

    public void broadcastDirtyFromServer(@NotNull Entity entity, @NotNull ZPNetEntityData dirtyData) {
        {
            if (dirtyData.vars().isEmpty()) {
                return;
            }
            ZPSyncEntityDataAllVarsPacket packet = new ZPSyncEntityDataAllVarsPacket(entity.getId(), dirtyData);
            if (entity instanceof ServerPlayer player) {
                ZombiePlague3.netServer().sendToAllTrackingAndSelf(player, packet);
            } else {
                ZombiePlague3.netServer().sendToAllTracking(entity, packet);
            }
        }
    }

    public void syncAllToPlayer(@NotNull ServerPlayer player, @NotNull Entity target) {
        {
            ZPNetEntityData data = this.container_VarsPerEntity.get(target);
            if (data == null) {
                return;
            }
            if (data.vars().isEmpty()) {
                return;
            }
            ZombiePlague3.netServer().sendToPlayer(new ZPSyncEntityDataAllVarsPacket(target.getId(), data), player);
        }
    }

    public void syncPlayerHimSelf(@NotNull ServerPlayer player) {
        {
            ZPNetEntityData data = this.container_VarsPerEntity.get(player);
            if (data == null) {
                return;
            }
            if (data.vars().isEmpty()) {
                return;
            }
            ZombiePlague3.netServer().sendToPlayer(new ZPSyncEntityDataAllVarsPacket(player.getId(), data), player);
        }
    }

    @SuppressWarnings("deprecation")
    @OnlyIn(Dist.CLIENT)
    public void setDataOnEntity(@NotNull Entity entity, @NotNull ZPNetEntityData netEntityData) {
        final ZPNetEntityData currentData = this.container_VarsPerEntity.get(entity);
        if (currentData == null) {
            this.container_VarsPerEntity.put(entity, new ZPNetEntityData(new Int2ObjectOpenHashMap<>()));
        }
        netEntityData.vars().forEach((accessorId, value) -> {
            this.container_VarsPerEntity.get(entity).vars().put(accessorId, value);
        });
    }

    @OnlyIn(Dist.CLIENT)
    public void setDataOnEntitiesGroup(@NotNull Int2ObjectMap<ZPNetEntityData> entitiesData, @NotNull Player player) {
        for (Int2ObjectMap.Entry<ZPNetEntityData> entry : entitiesData.int2ObjectEntrySet()) {
            Entity entity = player.level().getEntity(entry.getIntKey());
            if (entity == null) {
                continue;
            }
            this.setDataOnEntity(entity, entry.getValue());
        }
    }

    public void markEntityDirty(@NotNull Entity entity, @NotNull ServerPlayer player) {
        this.serverPlayer_dirtyTrackedEntities.computeIfAbsent(player, p -> new ArrayList<>()).add(entity);
    }

    @SuppressWarnings("all")
    public <E> void setVar(@NotNull Entity entity, @NotNull ZPNetDataAccessor<E> dataAccessor, @NotNull ZPNetDataVar<E> value) {
        if (!this.container_VarsPerEntity.containsKey(entity)) {
            return;
        }
        final ZPNetEntityData entityData = this.container_VarsPerEntity.get(entity);
        if (entityData == null) {
            return;
        }
        try {
            ZPNetDataVar<E> netDataVar = (ZPNetDataVar<E>) entityData.vars().get(dataAccessor.getGlobalId());
            if (netDataVar == null) {
                //ZPLogger.warn(this.getClass().getSimpleName() + " : " + "Entity " + entity + " doesn't have accessor " + dataAccessor.getGlobalId());
                return;
            }
            final boolean flag = !Objects.equals(netDataVar.getValue(), value.getValue());
            if (flag) {
                final ZPNetDataVar<E> copied = (ZPNetDataVar<E>) value.copy();
                entityData.vars().replace(dataAccessor.getGlobalId(), copied);
                {
                    final ZPNetEntityData dirtyData = this.thisTick_dirtyValues.computeIfAbsent(entity, id -> new ZPNetEntityData(new Int2ObjectOpenHashMap<>()));
                    dirtyData.vars().put(dataAccessor.getGlobalId(), copied);
                }
            }
        } catch (final ClassCastException e) {
            throw new ZPRuntimeException(e);
        }
    }

    @SuppressWarnings("all")
    public <E> Optional<ZPNetDataVar<E>> getVar(@NotNull Entity entity, @NotNull ZPNetDataAccessor<E> dataAccessor) {
        final ZPNetEntityData entityData = this.container_VarsPerEntity.get(entity);
        if (entityData == null) {
            return Optional.empty();
        }
        if (!entityData.vars().containsKey(dataAccessor.getGlobalId())) {
            //ZPLogger.warn(this.getClass().getSimpleName() + " : " + "Entity " + entity + " doesn't have accessor " + dataAccessor.getGlobalId());
            return Optional.empty();
        }
        try {
            return Optional.ofNullable((ZPNetDataVar<E>) entityData.vars().get(dataAccessor.getGlobalId()));
        } catch (final ClassCastException e) {
            throw new ZPRuntimeException(e);
        }
    }

    public void defineAccessorOnEntity(@NotNull Class<? extends Entity> clazz, @NotNull ZPNetDataAccessor<?> dataAccessor) {
        if (!this.dataAccessors_Registry.containsKey(clazz)) {
            this.dataAccessors_Registry.put(clazz, new ArrayList<>());
        }
        this.dataAccessors_Registry.get(clazz).add(dataAccessor);
        if (this.dataAccessors_idMap.containsKey(dataAccessor.getGlobalId())) {
            throw new ZPRuntimeException("Accessor was registered twice.");
        }
        this.dataAccessors_idMap.put(dataAccessor.getGlobalId(), dataAccessor);
    }

    public int structSize() {
        return this.container_VarsPerEntity.size();
    }

    public void clearAll() {
        this.container_VarsPerEntity.clear();
        this.serverPlayer_dirtyTrackedEntities.clear();
        this.thisTick_dirtyValues.clear();
    }

    public ZPNetEntityData getEntityDataVars(@NotNull Entity entity) {
        return this.container_VarsPerEntity.get(entity);
    }

    public String buildAccessorsHash() {
        return ZPNetDataAccessor.buildAccessorsHash(this.dataAccessors_idMap.values());
    }
}