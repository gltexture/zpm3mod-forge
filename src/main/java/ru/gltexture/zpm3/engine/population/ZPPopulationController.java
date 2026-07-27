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

package ru.gltexture.zpm3.engine.population;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import ru.gltexture.zpm3.engine.service.Pair;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public final class ZPPopulationController {
    private final ZPVanillaBiomePopulationManager vanillaBiomePopulationManager;
    private final List<PopulationData<?>> AND_SpawnRulesMap;
    private final List<PopulationData<?>> OR_SpawnRulesMap;
    private final List<PopulationData<?>> REPLACE_SpawnRulesMap;

    public ZPPopulationController() {
        this.AND_SpawnRulesMap = new ArrayList<>();
        this.OR_SpawnRulesMap = new ArrayList<>();
        this.REPLACE_SpawnRulesMap = new ArrayList<>();
        this.vanillaBiomePopulationManager = new ZPVanillaBiomePopulationManager();
    }

    public <T extends Entity> void addAND_Rule(Supplier<EntityType<T>> entityType, @Nullable SpawnPlacements.Type placementType, @Nullable Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        this.AND_SpawnRulesMap.add(new PopulationData<>(entityType, placementType, heightmap, spawnPredicate, SpawnPlacementRegisterEvent.Operation.AND));
    }

    public <T extends Entity> void addOR_Rule(Supplier<EntityType<T>> entityType, @Nullable SpawnPlacements.Type placementType, @Nullable Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        this.OR_SpawnRulesMap.add(new PopulationData<>(entityType, placementType, heightmap, spawnPredicate, SpawnPlacementRegisterEvent.Operation.OR));
    }

    public <T extends Entity> void addREPLACE_Rule(Supplier<EntityType<T>> entityType, @Nullable SpawnPlacements.Type placementType, @Nullable Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        this.REPLACE_SpawnRulesMap.add(new PopulationData<>(entityType, placementType, heightmap, spawnPredicate, SpawnPlacementRegisterEvent.Operation.REPLACE));
    }

    @SuppressWarnings("all")
    private <T extends Entity> Pair<T, T> getNormalizedPair(EntityType<?> entityType, SpawnPlacements.SpawnPredicate<?> spawnPredicate) {
        return Pair.of((T) (Object) entityType, (T) (Object) spawnPredicate);
    }

    @SuppressWarnings("all")
    private <T extends Entity> List<PopulationData<T>> listOfAll() {
        List<PopulationData<T>> list = new ArrayList<>();
        list.addAll(this.getAND_SpawnRulesMap());
        list.addAll(this.getOR_SpawnRulesMap());
        list.addAll(this.getREPLACE_SpawnRulesMap());
        return list;
    }

    @SuppressWarnings("all")
    public <T extends Entity> List<PopulationData<T>> getAND_SpawnRulesMap() {
        return (List<PopulationData<T>>) (Object) this.AND_SpawnRulesMap;
    }

    @SuppressWarnings("all")
    public <T extends Entity> List<PopulationData<T>> getOR_SpawnRulesMap() {
        return (List<PopulationData<T>>) (Object) this.OR_SpawnRulesMap;
    }

    @SuppressWarnings("all")
    public <T extends Entity> List<PopulationData<T>> getREPLACE_SpawnRulesMap() {
        return (List<PopulationData<T>>) (Object) this.REPLACE_SpawnRulesMap;
    }

    public void eventPopulation(SpawnPlacementRegisterEvent event) {
        this.listOfAll().forEach(e -> {
            event.register(e.entityType().get(), e.placementType(), e.heightmap(), e.spawnPredicate(), e.operation());
        });
        this.AND_SpawnRulesMap.clear();
        this.OR_SpawnRulesMap.clear();
        this.REPLACE_SpawnRulesMap.clear();
    }

    @Deprecated
    public ZPVanillaBiomePopulationManager getVanillaBiomePopulationManager() {
        return this.vanillaBiomePopulationManager;
    }

    public record PopulationData<E extends Entity>(Supplier<EntityType<E>> entityType, @Nullable SpawnPlacements.Type placementType, @Nullable Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<E> spawnPredicate, SpawnPlacementRegisterEvent.Operation operation) {
    }
}
