package ru.gltexture.zpm3.assets.loot_cases.loot_tables;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ZPLootTable {
    @NotNull private final String uniqueId;
    @Nullable private final LootCaseData lootCaseData;
    @NotNull private List<String> extendBy = new ArrayList<>();
    @NotNull private ZPLootTable.LootGroupsDataSet lootGroupsDataSet;

    public ZPLootTable(@NotNull String uniqueId, @Nullable LootCaseData lootCaseData, @NotNull ZPLootTable.LootGroupsDataSet lootGroupsDataSet) {
        this.lootCaseData = lootCaseData;
        this.lootGroupsDataSet = lootGroupsDataSet;
        this.uniqueId = uniqueId;
    }

    public static Builder builder(@NotNull String uniqueId) {
        return new Builder(uniqueId);
    }

    public @NotNull List<String> getExtendBy() {
        return this.extendBy;
    }

    public @NotNull String getUniqueId() {
        return this.uniqueId;
    }

    public ZPLootTable setExtendBy(@NotNull List<String> extendBy) {
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

    public @Nullable LootCaseData getLootCaseData() {
        return this.lootCaseData;
    }

    public record LootCaseData(@NotNull String name, @NotNull String textureId, boolean isUnbreakable, int respawnTime) {};

    public record LootGroupsDataSet(int minRolls, int maxRolls, float chanceToStartRolling, float nextRollChanceMultiplier, @Nullable List<LootCommonGroupData> lootCommonGroupDataList, @Nullable List<LootBonusGroupData> lootBonusGroupDataList) {};

    public record LootCommonGroupData(@NotNull LootGroup lootGroup, int maxSpawnTimes, float nextSpawnChanceMultiplier, int spawnWeight) {};
    public record LootBonusGroupData(@NotNull LootGroup lootGroup, int maxSpawnTimes, float nextSpawnChanceMultiplier, float spawnChance) {};

    public record LootGroup(@NotNull String groupName, List<LootItemNonBreakable> nonBreakable, List<LootItemBreakable> breakable) {};

    @SuppressWarnings("all")
    public record LootItemNonBreakable(@NotNull String locationKey, int spawnWeight, int minQuantity, int maxQuantity, float quantityRandomizeGrade) implements ILootItem {
        @Override
        public @Nullable ItemStack buildItemStack() {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(locationKey));
            if (item == null) {
                ZPLogger.error("Couldn't get item: " + locationKey);
                return null;
            }
            ItemStack stack = new ItemStack(item);
            if (!stack.isDamageableItem()) {
                float r = ZPRandom.getRandom().nextFloat();
                float k = (float) Math.pow(r, quantityRandomizeGrade);
                int minQ = Mth.clamp(minQuantity, 0, 64);
                int maxQ = Mth.clamp(maxQuantity, 0, 64);
                int quantity = (int) (minQ + (maxQ - minQ) * k);
                stack.setCount(quantity);
            }
            return stack;
        }

        @Override
        public int getWeight() {
            return this.spawnWeight;
        }
    }

    @SuppressWarnings("all")
    public record LootItemBreakable(@NotNull String locationKey, int spawnWeight, float minDamage, float maxDamage, float damageRandomizeGrade) implements ILootItem {
        @Override
        public @Nullable ItemStack buildItemStack() {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(locationKey));
            if (item == null) {
                ZPLogger.error("Couldn't get item: " + locationKey);
                return null;
            }
            ItemStack stack = new ItemStack(item);
            if (stack.isDamageableItem()) {
                int damageValue = 0;
                int max = stack.getMaxDamage();
                if (ZPRandom.getRandom().nextFloat() > 0.005f) {
                    float r = ZPRandom.getRandom().nextFloat();
                    float k = (float) Math.pow(r, damageRandomizeGrade);
                    float damageFrac = minDamage + (maxDamage - minDamage) * k;
                    damageValue = (int) (damageFrac * max);
                }
                stack.setDamageValue(Mth.clamp(damageValue, 0, max - 1));
            }
            return stack;
        }

        @Override
        public int getWeight() {
            return this.spawnWeight;
        }
    }

    public interface ILootItem {
        @Nullable ItemStack buildItemStack();
        int getWeight();
    }

    public static class Builder {
        private final String uniqueId;

        private LootCaseData lootCaseData = null;
        private final List<String> extendBy = new ArrayList<>();

        private final List<LootCommonGroupData> commonGroups = new ArrayList<>();
        private final List<LootBonusGroupData> bonusGroups = new ArrayList<>();

        public Builder(@NotNull String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public Builder lootCase(@NotNull String textureId, boolean isUnbreakable, int respawnTime) {
            this.lootCaseData = new LootCaseData(this.uniqueId, textureId, isUnbreakable, respawnTime);
            return this;
        }

        public Builder lootCase(@NotNull String name, @NotNull String textureId, boolean isUnbreakable, int respawnTime) {
            this.lootCaseData = new LootCaseData(name, textureId, isUnbreakable, respawnTime);
            return this;
        }

        public Builder extendBy(@NotNull String... ids) {
            extendBy.addAll(Arrays.asList(ids));
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

            ZPLootTable table = new ZPLootTable(uniqueId, lootCaseData, groups);
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
                return new LootGroup(name, nonBreakables, breakables);
            }

            public LootGroupBuilder addNonBreakable(@NotNull String id, int spawnWeight, int minQ, int maxQ, float grade) {
                nonBreakables.add(new LootItemNonBreakable(id, spawnWeight, minQ, maxQ, grade));
                return this;
            }

            public LootGroupBuilder addBreakable(@NotNull String id, int spawnWeight, float minDamage, float maxDamage, float grade) {
                breakables.add(new LootItemBreakable(id, spawnWeight, minDamage, maxDamage, grade));
                return this;
            }

            public LootGroupBuilder addNonBreakable(LootItemNonBreakable item) {
                nonBreakables.add(item);
                return this;
            }

            public LootGroupBuilder addBreakable(LootItemBreakable item) {
                breakables.add(item);
                return this;
            }
        }
    }
}
