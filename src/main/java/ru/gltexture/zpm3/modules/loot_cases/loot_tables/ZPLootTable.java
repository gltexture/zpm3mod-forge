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
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.items.ZPLootItemBreakable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.items.ZPLootItemNonBreakable;
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
    @NotNull private List<TableExtension> extendBy = new ArrayList<>();
    @NotNull private ZPLootTable.LootGroupsDataSet lootGroupsDataSet;

    public ZPLootTable(@NotNull String uniqueId, @NotNull ZPLootTable.LootGroupsDataSet lootGroupsDataSet) {
        this.lootGroupsDataSet = lootGroupsDataSet;
        this.uniqueId = uniqueId;
    }

    public static Builder builder(@NotNull String uniqueId) {
        return new Builder(uniqueId);
    }

    public @NotNull List<TableExtension> getExtendBy() {
        return this.extendBy;
    }

    public @NotNull String getUniqueId() {
        return this.uniqueId;
    }

    public ZPLootTable setExtendBy(@NotNull List<TableExtension> extendBy) {
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

    public record LootGroupsDataSet(@NotNull RollRules rollRules, @Nullable List<LootCommonGroupData> lootCommonGroupDataList, @Nullable List<LootBonusGroupData> lootBonusGroupDataList) {};

    public record LootCommonGroupData(@NotNull LootGroup lootGroup, @NotNull RollRules rollRules, int spawnWeight) {};
    public record LootBonusGroupData(@NotNull LootGroup lootGroup, @NotNull RollRules rollRules) {};

    public record LootGroup(@NotNull String groupName, List<ZPLootItemNonBreakable> nonBreakable, List<ZPLootItemBreakable> breakable) {};

    public record RollRules(float chanceToStartRoll, int minRolls, int maxRolls, ZPRandomization randomization) {
        public static RollRules defaultInst(float chanceToStartRoll) {
            return new RollRules(chanceToStartRoll, 1, 1, ZPRandomization.uniform());
        }
        public static RollRules defaultInst() {
            return new RollRules(1.0f, 1, 1, ZPRandomization.uniform());
        }
    };
    public record TableExtension(@NotNull String extendByResourceLoc, @Nullable ZPLootTable.RollRules newTableRollRules) {
        public ResourceLocation getResourceLocation() {
            return ResourceLocation.tryParse(this.extendByResourceLoc);
        }

        public static TableExtension defaultInst(@NotNull String extendByResourceLoc) {
            return new TableExtension(extendByResourceLoc, null);
        }
    };

    public static class Builder {
        private final String uniqueId;

        private final List<TableExtension> extendBy = new ArrayList<>();
        private final List<LootCommonGroupData> commonGroups = new ArrayList<>();
        private final List<LootBonusGroupData> bonusGroups = new ArrayList<>();

        public Builder(@NotNull String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public Builder extendBy(@NotNull TableExtension... ids) {
            this.extendBy.addAll(Arrays.asList(ids));
            return this;
        }

        public Builder commonGroup(@NotNull String name, int weight, @NotNull Consumer<LootGroupBuilder> consumer) {
            return this.commonGroup(name, weight, RollRules.defaultInst(), consumer);
        }

        public Builder commonGroup(@NotNull String name, int weight, @NotNull RollRules spawnRules, @NotNull Consumer<LootGroupBuilder> consumer) {
            LootGroupBuilder builder = new LootGroupBuilder(name);
            consumer.accept(builder);
            this.commonGroups.add(new LootCommonGroupData(builder.build(), spawnRules, weight));
            return this;
        }

        public Builder bonusGroup(@NotNull String name, float spawnChance, @NotNull Consumer<LootGroupBuilder> consumer) {
            return this.bonusGroup(name, RollRules.defaultInst(spawnChance), consumer);
        }

        public Builder bonusGroup(@NotNull String name, @NotNull RollRules spawnRules, @NotNull Consumer<LootGroupBuilder> consumer) {
            LootGroupBuilder builder = new LootGroupBuilder(name);
            consumer.accept(builder);
            this.bonusGroups.add(new LootBonusGroupData(builder.build(), spawnRules));
            return this;
        }

        public ZPLootTable build(@NotNull RollRules spawnRules) {
            LootGroupsDataSet groups = new LootGroupsDataSet(spawnRules,
                    this.commonGroups.isEmpty() ? null : this.commonGroups,
                    this.bonusGroups.isEmpty() ? null : this.bonusGroups
            );

            ZPLootTable table = new ZPLootTable(uniqueId, groups);
            table.setExtendBy(extendBy);

            return table;
        }

        public static class LootGroupBuilder {
            private final String name;
            private final List<ZPLootItemNonBreakable> nonBreakables = new ArrayList<>();
            private final List<ZPLootItemBreakable> breakables = new ArrayList<>();

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
                this.nonBreakables.add(new ZPLootItemNonBreakable(id, spawnWeight, minQ, maxQ, randomization, nbtContainer.apply(new ZPLootNbtContainer()).getValues()));
                return this;
            }

            public LootGroupBuilder addBreakable(@NotNull String id, int spawnWeight, float minDamage, float maxDamage, @NotNull ZPRandomization randomization, @NotNull Function<IZPLootNbtContainer, IZPLootNbtContainer> nbtContainer) {
                this.breakables.add(new ZPLootItemBreakable(id, spawnWeight, minDamage, maxDamage, randomization, nbtContainer.apply(new ZPLootNbtContainer()).getValues()));
                return this;
            }

            public LootGroupBuilder addNonBreakable(@NotNull String id, int spawnWeight, int count, @NotNull Function<IZPLootNbtContainer, IZPLootNbtContainer> nbtContainer) {
                return this.addNonBreakable(id, spawnWeight, count, count, ZPRandomization.uniform(), nbtContainer);
            }

            public LootGroupBuilder addNonBreakable(@NotNull String id, int spawnWeight, int count) {
                return this.addNonBreakable(id, spawnWeight, count, count, ZPRandomization.uniform(), nbt -> nbt);
            }

            public LootGroupBuilder addNonBreakable(@NotNull String id, int spawnWeight, int minQ, int maxQ) {
                return this.addNonBreakable(id, spawnWeight, minQ, maxQ, ZPRandomization.uniform(), nbt -> nbt);
            }

            public LootGroupBuilder addBreakable(@NotNull String id, int spawnWeight, float minDamage, float maxDamage) {
                return this.addBreakable(id, spawnWeight, minDamage, maxDamage, ZPRandomization.uniform(), nbt -> nbt);
            }

            public LootGroupBuilder addNonBreakable(@NotNull String id, int spawnWeight, int minQ, int maxQ, @NotNull ZPRandomization randomization) {
                this.nonBreakables.add(new ZPLootItemNonBreakable(id, spawnWeight, minQ, maxQ, randomization, Collections.emptyMap()));
                return this;
            }

            public LootGroupBuilder addBreakable(@NotNull String id, int spawnWeight, float minDamage, float maxDamage, @NotNull ZPRandomization randomization) {
                this.breakables.add(new ZPLootItemBreakable(id, spawnWeight, minDamage, maxDamage, randomization, Collections.emptyMap()));
                return this;
            }

            public LootGroupBuilder addNonBreakable(ZPLootItemNonBreakable item) {
                this.nonBreakables.add(item);
                return this;
            }

            public LootGroupBuilder addBreakable(ZPLootItemBreakable item) {
                this.breakables.add(item);
                return this;
            }
        }
    }
}