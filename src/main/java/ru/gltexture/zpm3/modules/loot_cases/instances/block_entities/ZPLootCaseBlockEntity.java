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

package ru.gltexture.zpm3.modules.loot_cases.instances.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZP_EventsManager;
import ru.gltexture.zpm3.engine.core.api.events.ZPEventDef;
import ru.gltexture.zpm3.engine.core.api.events.common.ZPEventBus_Blocks;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.modules.loot_cases.init.ZPBlockLootCaseEntities;
import ru.gltexture.zpm3.modules.loot_cases.instances.blocks.ZPDefaultBlockLootCase;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.items.IZPLootItem;
import ru.gltexture.zpm3.modules.loot_cases.registry.ZPLootTablesCollection;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

public class ZPLootCaseBlockEntity extends ChestBlockEntity {
    private long timeLock;

    public ZPLootCaseBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ZPBlockLootCaseEntities.loot_case_block_entity.get(), pPos, pBlockState);
    }

    public static void tick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ZPLootCaseBlockEntity blockEntity) {
        ChestBlockEntity.lidAnimateTick(level, pos, state, blockEntity);
    }

    public void setTime(@NotNull Level level, long exact, long salt) {
        this.timeLock = level.getGameTime() + (long) (exact + ZPRandom.instance.randomFloatDuo(salt));
    }

    @Override
    public void startOpen(@NotNull Player pPlayer) {
        super.startOpen(pPlayer);
        if (this.getLevel() != null && this.isServer()) {
            final BlockEntity block = this.getLevel().getBlockEntity(this.getBlockPos());
            final BlockState blockState = this.getLevel().getBlockState(this.getBlockPos());
            if (blockState.getBlock() instanceof ZPDefaultBlockLootCase defaultBlockLootCase) {
                boolean flag = false;
                if (this.timeLock <= 0L) {
                    flag = true;
                } else if (this.getLevel().getGameTime() >= this.timeLock) {
                    flag = true;
                }
                if (flag) {
                    final ZPLootTable rootLootTable = ZPLootTablesCollection.INSTANCE.getLootTableById(defaultBlockLootCase.getConnectedLootTable());
                    final ZPEventDef.Cancellable cancellable = new ZPEventBus_Blocks.LootCaseRespawnEvent(this.getLevel(), this.getBlockPos(), pPlayer, this, rootLootTable);
                    ZP_EventsManager.pushEvent((ZPEventDef.IEvent) cancellable);
                    if (!cancellable.isCancelled()) {
                        this.clearContent();
                        rootLootTable.getExtendBy().forEach(e -> {
                            ZPLootTable table = ZPLootTablesCollection.INSTANCE.getLootTableById(e.getResourceLocation());
                            if (table != null) {
                                this.spawnLoot(table, e.newTableRollRules());
                            }
                        });
                        this.spawnLoot(rootLootTable, null);
                    }
                    this.setTime(this.getLevel(), defaultBlockLootCase.getLootRespawnTime(), defaultBlockLootCase.getLootRespawnTime() / 10);
                }
            }
        }
    }

    protected void spawnLoot(@NotNull ZPLootTable lootTable, @Nullable ZPLootTable.RollRules overrideRollRules) {
        final ZPLootTable.LootGroupsDataSet dataSet = lootTable.getLootGroupsSpawnDataSet();
        final ZPLootTable.RollRules rollRules = overrideRollRules != null ? overrideRollRules : lootTable.getLootGroupsSpawnDataSet().rollRules();

        List<Integer> freeSlots = new ArrayList<>();
        for (int i = 0; i < this.getContainerSize(); i++) {
            if (this.getItem(i).isEmpty()) {
                freeSlots.add(i);
            }
        }

        if (freeSlots.isEmpty()) {
            return;
        }

        float rollChance = rollRules.chanceToStartRoll();
        if (ZPRandom.getRandom().nextFloat() > rollChance) {
            return;
        }

        final int maxRolls = rollRules.randomization().random(rollRules.minRolls(), rollRules.maxRolls());
        L0:
        for (int roll = 0; roll < maxRolls; roll++) {
            if (dataSet.lootCommonGroupDataList() != null && !dataSet.lootCommonGroupDataList().isEmpty()) {
                final Pair<Integer, ZPLootTable.LootCommonGroupData> commonGroup = this.pickWeightedCommonGroup(dataSet.lootCommonGroupDataList());
                if (commonGroup != null) {
                    if (ZPRandom.getRandom().nextFloat() <= commonGroup.second().rollRules().chanceToStartRoll()) {
                        for (int o = 0; o < commonGroup.first(); o++) {
                            this.fillFromGroup(commonGroup.second().lootGroup(), freeSlots);
                            if (freeSlots.isEmpty()) {
                                break L0;
                            }
                        }
                    }
                }
            }
            if (dataSet.lootBonusGroupDataList() != null) {
                for (ZPLootTable.LootBonusGroupData bonus : dataSet.lootBonusGroupDataList()) {
                    final ZPLootTable.RollRules rollRulesBonus = bonus.rollRules();
                    if (ZPRandom.getRandom().nextFloat() <= rollRulesBonus.chanceToStartRoll()) {
                        for (int o = 0; o < rollRulesBonus.randomization().random(rollRulesBonus.minRolls(), rollRulesBonus.maxRolls()); o++) {
                            this.fillFromGroup(bonus.lootGroup(), freeSlots);
                            if (freeSlots.isEmpty()) {
                                break L0;
                            }
                        }
                    }
                }
            }
            if (freeSlots.isEmpty()) {
                break;
            }
        }
    }

    private void fillFromGroup(ZPLootTable.LootGroup group, List<Integer> freeSlots) {
        if (freeSlots.isEmpty()) {
            return;
        }
        final List<IZPLootItem> pool = new ArrayList<>();
        if (group.nonBreakable() != null) {
            pool.addAll(group.nonBreakable());
        }
        if (group.breakable() != null) {
            pool.addAll(group.breakable());
        }
        if (pool.isEmpty()) {
            return;
        }
        IZPLootItem selected = this.pickWeightedItem(pool);
        if (selected == null) {
            return;
        }
        ItemStack stack = selected.buildItemStack();
        if (stack == null || stack.isEmpty()) {
            return;
        }
        this.putItemRandom(stack, freeSlots);
    }

    private Pair<Integer, ZPLootTable.LootCommonGroupData> pickWeightedCommonGroup(List<ZPLootTable.LootCommonGroupData> list) {
        final ZPLootTable.LootCommonGroupData lootCommonGroupData = this.pickWeighted(list, ZPLootTable.LootCommonGroupData::spawnWeight);
        if (lootCommonGroupData == null) {
            return null;
        }
        return Pair.of(lootCommonGroupData.rollRules().randomization().random(lootCommonGroupData.rollRules().minRolls(), lootCommonGroupData.rollRules().maxRolls()), lootCommonGroupData);
    }

    private IZPLootItem pickWeightedItem(List<IZPLootItem> list) {
        return this.pickWeighted(list, IZPLootItem::spawnWeight);
    }

    private <T> T pickWeighted(List<T> list, ToIntFunction<T> weightGetter) {
        int totalWeight = 0;
        for (T value : list) {
            int weight = weightGetter.applyAsInt(value);
            if (weight > 0) {
                totalWeight += weight;
            }
        }
        if (totalWeight <= 0) {
            return null;
        }
        int random = ZPRandom.getRandom().nextInt(totalWeight);
        for (T value : list) {
            int weight = weightGetter.applyAsInt(value);
            if (weight <= 0) {
                continue;
            }
            random -= weight;
            if (random < 0) {
                return value;
            }
        }
        return null;
    }

    private void putItemRandom(ItemStack stack, List<Integer> freeSlots) {
        if (freeSlots.isEmpty()) {
            return;
        }
        int idx = freeSlots.remove(ZPRandom.getRandom().nextInt(freeSlots.size()));
        this.setItem(idx, stack);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains(ZPFadingBlockEntity.NBT_TIMELOCK)) {
            this.timeLock = pTag.getLong(ZPFadingBlockEntity.NBT_TIMELOCK);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (this.isServer()) {
            pTag.putLong(ZPFadingBlockEntity.NBT_TIMELOCK, this.timeLock);
        }
    }

    public boolean isServer() {
        return this.getLevel() != null && !this.getLevel().isClientSide();
    }
}