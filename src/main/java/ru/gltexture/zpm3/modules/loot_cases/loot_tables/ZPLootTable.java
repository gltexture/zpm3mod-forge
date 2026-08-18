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

package ru.gltexture.zpm3.modules.loot_cases.loot_tables;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.items.LootItemBreakable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.items.LootItemNonBreakable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.container.IZPLootNbtContainer;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.container.ZPLootNbtContainer;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.random.ZPRandomization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ZPLootTable {
    @NotNull private final String uniqueId;
    @NotNull private List<ResourceLocation> extendBy = new ArrayList<>();
    @NotNull private ZPLootTable.LootGroupsDataSet lootGroupsDataSet;

    public ZPLootTable(@NotNull String uniqueId, @NotNull ZPLootTable.LootGroupsDataSet lootGroupsDataSet) {
        this.lootGroupsDataSet = lootGroupsDataSet;
        this.uniqueId = uniqueId;
    }

    public static Builder builder(@NotNull String uniqueId) {
        return new Builder(uniqueId);
    }

    public @NotNull List<ResourceLocation> getExtendBy() {
        return this.extendBy;
    }

    public @NotNull String getUniqueId() {
        return this.uniqueId;
    }

    public ZPLootTable setExtendBy(@NotNull List<ResourceLocation> extendBy) {
        this.extendBy = extendBy;
        return this;
    }

    public @NotNull ZPLootTable.LootGroupsDataSet getLootGroupsSpawnDataSet() {
        return this.lootGroupsDataSet;
    }

    public ZPLootTable setLootGroupsSpawnDataSet(@NotNull ZPLootTable.LootGroupsDataSet lootGroupsDataSet) {
        this.lootGroupsDataSet = lootGroupsDataSet;
        return this;
    }

    public record LootCaseData(@NotNull String name, @NotNull String textureId, boolean isUnbreakable, int respawnTime) {};

    public record LootGroupsDataSet(int minRolls, int maxRolls, float chanceToStartRolling, float nextRollChanceMultiplier, @Nullable List<LootCommonGroupData> lootCommonGroupDataList, @Nullable List<LootBonusGroupData> lootBonusGroupDataList) {};

    public record LootCommonGroupData(@NotNull LootGroup lootGroup, int maxSpawnTimes, float nextSpawnChanceMultiplier, int spawnWeight) {};
    public record LootBonusGroupData(@NotNull LootGroup lootGroup, int maxSpawnTimes, float nextSpawnChanceMultiplier, float spawnChance) {};

    public record LootGroup(@NotNull String groupName, List<LootItemNonBreakable> nonBreakable, List<LootItemBreakable> breakable) {};

    public static class Builder {
        private final String uniqueId;

        private final List<ResourceLocation> extendBy = new ArrayList<>();

        private final List<LootCommonGroupData> commonGroups = new ArrayList<>();
        private final List<LootBonusGroupData> bonusGroups = new ArrayList<>();

        public Builder(@NotNull String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public Builder extendBy(@NotNull ResourceLocation... ids) {
            this.extendBy.addAll(Arrays.asList(ids));
            return this;
        }

        public Builder commonGroup(@NotNull String name, int weight, @NotNull Consumer<LootGroupBuilder> consumer) {
            LootGroupBuilder builder = new LootGroupBuilder(name);
            consumer.accept(builder);
            commonGroups.add(new LootCommonGroupData(builder.build(), 1, 1.0f, weight));
            return this;
        }

        public Builder bonusGroup(@NotNull String name, float chance, @NotNull Consumer<LootGroupBuilder> consumer) {
            LootGroupBuilder builder = new LootGroupBuilder(name);
            consumer.accept(builder);
            bonusGroups.add(new LootBonusGroupData(builder.build(), 1, 1.0f, chance));
            return this;
        }

        public Builder commonGroup(@NotNull String name, int maxSpawnTimes, float nextSpawnChanceMultiplier, int weight, @NotNull Consumer<LootGroupBuilder> consumer) {
            LootGroupBuilder builder = new LootGroupBuilder(name);
            consumer.accept(builder);
            commonGroups.add(new LootCommonGroupData(builder.build(), maxSpawnTimes, nextSpawnChanceMultiplier, weight));
            return this;
        }

        public Builder bonusGroup(@NotNull String name, int maxSpawnTimes, float nextSpawnChanceMultiplier, float chance, @NotNull Consumer<LootGroupBuilder> consumer) {
            LootGroupBuilder builder = new LootGroupBuilder(name);
            consumer.accept(builder);
            bonusGroups.add(new LootBonusGroupData(builder.build(), maxSpawnTimes, nextSpawnChanceMultiplier, chance));
            return this;
        }

        public ZPLootTable build(int maxRolls, float chanceToStartRolling, float nextRollChanceMultiplier) {
            return this.build(1, maxRolls, chanceToStartRolling, nextRollChanceMultiplier);
        }

        public ZPLootTable build(int minRolls, int maxRolls, float chanceToStartRolling, float nextRollChanceMultiplier) {
            LootGroupsDataSet groups = new LootGroupsDataSet(minRolls, maxRolls, chanceToStartRolling, nextRollChanceMultiplier,
                    commonGroups.isEmpty() ? null : commonGroups,
                    bonusGroups.isEmpty() ? null : bonusGroups
            );

            ZPLootTable table = new ZPLootTable(uniqueId, groups);
            table.setExtendBy(extendBy);

            return table;
        }

        public static class LootGroupBuilder {
            private final String name;
            private final List<LootItemNonBreakable> nonBreakables = new ArrayList<>();
            private final List<LootItemBreakable> breakables = new ArrayList<>();

            public LootGroupBuilder(String name) {
                this.name = name;
            }

            public LootGroup build() {
                return new LootGroup(this.name, this.nonBreakables, this.breakables);
            }

            public LootGroupBuilder addNonBreakable(@NotNull String id, int spawnWeight, int minQ, int maxQ, @NotNull Function<IZPLootNbtContainer, IZPLootNbtContainer> nbtContainer) {
                return this.addNonBreakable(id, spawnWeight, minQ, maxQ, ZPRandomization.uniform(), nbtContainer);
            }

            public LootGroupBuilder addBreakable(@NotNull String id, int spawnWeight, float minDamage, float maxDamage, @NotNull Function<IZPLootNbtContainer, IZPLootNbtContainer> nbtContainer) {
                return this.addBreakable(id, spawnWeight, minDamage, maxDamage, ZPRandomization.uniform(), nbtContainer);
            }

            public LootGroupBuilder addNonBreakable(@NotNull String id, int spawnWeight, int minQ, int maxQ, @NotNull ZPRandomization randomization, @NotNull Function<IZPLootNbtContainer, IZPLootNbtContainer> nbtContainer) {
                this.nonBreakables.add(new LootItemNonBreakable(id, spawnWeight, minQ, maxQ, randomization, nbtContainer.apply(new ZPLootNbtContainer()).getValues()));
                return this;
            }

            public LootGroupBuilder addBreakable(@NotNull String id, int spawnWeight, float minDamage, float maxDamage, @NotNull ZPRandomization randomization, @NotNull Function<IZPLootNbtContainer, IZPLootNbtContainer> nbtContainer) {
                this.breakables.add(new LootItemBreakable(id, spawnWeight, minDamage, maxDamage, randomization, nbtContainer.apply(new ZPLootNbtContainer()).getValues()));
                return this;
            }

            public LootGroupBuilder addNonBreakable(@NotNull String id, int spawnWeight, int minQ, int maxQ) {
                return this.addNonBreakable(id, spawnWeight, minQ, maxQ, ZPRandomization.uniform(), nbt -> nbt);
            }

            public LootGroupBuilder addBreakable(@NotNull String id, int spawnWeight, float minDamage, float maxDamage) {
                return this.addBreakable(id, spawnWeight, minDamage, maxDamage, ZPRandomization.uniform(), nbt -> nbt);
            }

            public LootGroupBuilder addNonBreakable(@NotNull String id, int spawnWeight, int minQ, int maxQ, @NotNull ZPRandomization randomization) {
                this.nonBreakables.add(new LootItemNonBreakable(id, spawnWeight, minQ, maxQ, randomization, Collections.emptyMap()));
                return this;
            }

            public LootGroupBuilder addBreakable(@NotNull String id, int spawnWeight, float minDamage, float maxDamage, @NotNull ZPRandomization randomization) {
                this.breakables.add(new LootItemBreakable(id, spawnWeight, minDamage, maxDamage, randomization, Collections.emptyMap()));
                return this;
            }

            public LootGroupBuilder addNonBreakable(LootItemNonBreakable item) {
                this.nonBreakables.add(item);
                return this;
            }

            public LootGroupBuilder addBreakable(LootItemBreakable item) {
                this.breakables.add(item);
                return this;
            }
        }
    }
}