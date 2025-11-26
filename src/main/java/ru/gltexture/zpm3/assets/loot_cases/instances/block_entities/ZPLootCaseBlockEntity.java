package ru.gltexture.zpm3.assets.loot_cases.instances.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.assets.loot_cases.init.ZPBlockLootCaseEntities;
import ru.gltexture.zpm3.assets.loot_cases.instances.blocks.ZPDefaultBlockLootCase;
import ru.gltexture.zpm3.assets.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.assets.loot_cases.registry.ZPLootTablesCollection;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.util.ArrayList;
import java.util.List;

public class ZPLootCaseBlockEntity extends ChestBlockEntity {
    public static final String NBT_TIMELOCK = "timeLock";
    public static final String NBT_ACTIVE = "active";
    public static final String NBT_FADING_TIME = "fadingTime";
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
            BlockEntity block = this.getLevel().getBlockEntity(this.getBlockPos());
            BlockState blockState = this.getLevel().getBlockState(this.getBlockPos());
            if (blockState.getBlock() instanceof ZPDefaultBlockLootCase defaultBlockLootCase) {
                boolean flag = false;
                if (this.timeLock <= 0L) {
                    this.setTime(this.getLevel(), defaultBlockLootCase.getLootRespawnTime(), defaultBlockLootCase.getLootRespawnTime() / 10);
                    flag = true;
                } else if (this.getLevel().getGameTime() >= this.timeLock) {
                    flag = true;
                }
                if (flag) {
                    this.clearContent();
                    ZPLootTable rootLootTable = defaultBlockLootCase.getConnectedLootTable();
                    rootLootTable.getExtendBy().forEach(e -> {
                        ZPLootTable table = ZPLootTablesCollection.INSTANCE.get(e);
                        if (table != null) {
                            this.spawnLoot(table);
                        }
                    });
                    this.spawnLoot(rootLootTable);
                }
            }
        }
    }

    protected void spawnLoot(@NotNull ZPLootTable lootTable) {
        ZPLootTable.LootGroupsDataSet dataSet = lootTable.getLootGroupsSpawnDataSet();
        int maxRolls = dataSet.maxRolls();
        int minRolls = dataSet.minRolls();
        float nextRollMultiplier = dataSet.nextRollChanceMultiplier();

        List<Integer> freeSlots = new ArrayList<>();
        for (int i = 0; i < this.getContainerSize(); i++) {
            if (this.getItem(i).isEmpty()) {
                freeSlots.add(i);
            }
        }

        if (freeSlots.isEmpty()) {
            return;
        }

        float rollChance = 1.0f;

        for (int roll = 0; roll < minRolls + (maxRolls - minRolls); roll++) {
            if (ZPRandom.getRandom().nextFloat() > rollChance) {
                break;
            }

            if (dataSet.lootCommonGroupDataList() != null && !dataSet.lootCommonGroupDataList().isEmpty()) {
                ZPLootTable.LootGroup group = this.pickWeightedCommonGroup(dataSet.lootCommonGroupDataList());
                if (group != null) {
                    this.fillFromGroup(group, freeSlots);
                    if (freeSlots.isEmpty()) {
                        break;
                    }
                }
            }

            if (dataSet.lootBonusGroupDataList() != null) {
                for (ZPLootTable.LootBonusGroupData bonus : dataSet.lootBonusGroupDataList()) {
                    if (ZPRandom.getRandom().nextFloat() <= bonus.spawnChance()) {
                        this.fillFromGroup(bonus.lootGroup(), freeSlots);
                        if (freeSlots.isEmpty()) {
                            break;
                        }
                    }
                }
            }

            if (roll >= minRolls) {
                rollChance *= nextRollMultiplier;
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

        List<ZPLootTable.ILootItem> pool = new ArrayList<>();

        if (group.nonBreakable() != null) {
            pool.addAll(group.nonBreakable());
        }

        if (group.breakable() != null) {
            pool.addAll(group.breakable());
        }

        if (pool.isEmpty()) {
            return;
        }

        ZPLootTable.ILootItem selected = this.pickWeightedItem(pool);
        if (selected == null) {
            return;
        }

        ItemStack stack = selected.buildItemStack();
        if (stack == null || stack.isEmpty()) {
            return;
        }

        this.putItemRandom(stack, freeSlots);
    }

    private ZPLootTable.LootGroup pickWeightedCommonGroup(List<ZPLootTable.LootCommonGroupData> list) {
        int totalWeight = 0;
        for (ZPLootTable.LootCommonGroupData g : list) {
            totalWeight += g.spawnWeight();
        }
        if (totalWeight <= 0) {
            return null;
        }
        int r = ZPRandom.getRandom().nextInt(totalWeight);
        for (ZPLootTable.LootCommonGroupData g : list) {
            r -= g.spawnWeight();
            if (r < 0) {
                return g.lootGroup();
            }
        }
        return null;
    }

    private ZPLootTable.ILootItem pickWeightedItem(List<ZPLootTable.ILootItem> list) {
        int totalWeight = 0;
        for (ZPLootTable.ILootItem item : list) {
            totalWeight += item.getWeight();
        }

        if (totalWeight <= 0) {
            return null;
        }

        int r = ZPRandom.getRandom().nextInt(totalWeight);
        int current = 0;

        for (ZPLootTable.ILootItem item : list) {
            current += item.getWeight();
            if (r < current) {
                return item;
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