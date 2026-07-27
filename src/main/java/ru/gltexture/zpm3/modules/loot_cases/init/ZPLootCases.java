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

package ru.gltexture.zpm3.modules.loot_cases.init;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockModelExecutors;
import ru.gltexture.zpm3.modules.loot_cases.instances.blocks.ZPDefaultBlockLootCase;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.modules.loot_cases.registry.ZPLootTablesCollection;
import ru.gltexture.zpm3.modules.loot_cases.registry.ZPLootTablesReader;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockItemModelExecutors;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.*;

public class ZPLootCases extends ZPRegistry<ZPDefaultBlockLootCase> implements IZPCollectRegistryObjects {
    public static Map<String, RegistryObject<ZPDefaultBlockLootCase>> generatedLootCases = new HashMap<>();

    public ZPLootCases() {
        super(ZPRegistryConveyor.Target.BLOCK);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<ZPDefaultBlockLootCase> regSupplier) {
        this.initInstanceCollecting("lootCases");
        for (ZPLootTable lootTable : ZPLootTablesCollection.INSTANCE.getAllLootTables().stream().filter(e -> e.getLootCaseData() != null).toList()) {
            final String lootCaseName = Objects.requireNonNull(lootTable.getLootCaseData()).name().toLowerCase();
            final boolean isUnbreakable = lootTable.getLootCaseData().isUnbreakable();
            final int lootRespawnTime = lootTable.getLootCaseData().respawnTime();
            RegistryObject<ZPDefaultBlockLootCase> syntheticLootCase = regSupplier.register(lootCaseName, () -> new ZPDefaultBlockLootCase(BlockBehaviour.Properties.of().strength(isUnbreakable ? -1.0f : 5.0f, isUnbreakable ? Float.MAX_VALUE : 5.0f).sound(SoundType.WOOD), lootTable.getLootCaseData().textureId(), lootTable, lootRespawnTime)
            ).afterCreated((e, utils) -> {
                ZPUtility.sides().onlyClient(() -> {
                    utils.blocks().addBlockModelKey_ValueArray(e, ZPDataGenHelper.DEFAULT_CHEST_BLOCK, Pair.of("particle", () -> new ZPPath(ZPDataGenHelper.MINECRAFT_VANILLA_ROOT, "oak_planks")));
                    utils.blocks().setBlockItemModelExecutor(e, DefaultBlockModelExecutors.getDefault(), DefaultBlockItemModelExecutors.getDefaultItemAsVanillaParent(ZPDataGenHelper.DEFAULT_CHEST_ITEM));
                });
            }).end();
            ZPLootCases.generatedLootCases.put(lootCaseName, syntheticLootCase);
        }
        this.stopInstanceCollecting();
    }

    @Override
    public void preProcessing() {
        ZPLootTablesReader.READ_FILES();
    }

    @Override
    protected void postRegister(String name, RegistryObject<ZPDefaultBlockLootCase> object) {
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}