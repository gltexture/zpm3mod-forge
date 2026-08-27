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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockModelExecutors;
import ru.gltexture.zpm3.engine.registry.ZPCommonRegistry;
import ru.gltexture.zpm3.modules.loot_cases.events.provider.ZPSyntheticLootCasesDataGenRegistry;
import ru.gltexture.zpm3.modules.loot_cases.instances.blocks.ZPDefaultBlockLootCase;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.synthetic.ZPSyntheticLootCaseDescription;
import ru.gltexture.zpm3.modules.loot_cases.registry.ZPLootCasesReader;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockItemModelExecutors;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ZPLootCases extends ZPCommonRegistry<ZPDefaultBlockLootCase> implements IZPCollectRegistryObjects {
    public ZPLootCases() {
        super(ZPRegistryConveyor.Target.BLOCK);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<ZPDefaultBlockLootCase> regSupplier) {
        this.initInstanceCollecting("lootCases");
        for (ZPSyntheticLootCaseDescription lootCase : ZPSyntheticLootCasesDataGenRegistry.getDataToGenRuntime_LootCases()) {
            final String lootCaseName = lootCase.blockId().toLowerCase();
            final float hardness = lootCase.hardness();
            final int lootRespawnTime = lootCase.lootRespawnTime();
            final RegistryObject<ZPDefaultBlockLootCase> syntheticLootCase = regSupplier.register(lootCaseName, () -> new ZPDefaultBlockLootCase(BlockBehaviour.Properties.of().strength(hardness, hardness).sound(SoundType.WOOD), lootCase.textureId(), Objects.requireNonNull(ResourceLocation.tryParse(lootCase.lootId())), lootRespawnTime)
            ).afterCreated((e, utils) -> {
                ZPUtility.sides().onlyClient(() -> {
                    utils.blocks().addBlockModelKey_ValueArray(e, ZPDataGenHelper.DEFAULT_CHEST_BLOCK, Pair.of("particle", () -> new ZPPath(ZPDataGenHelper.MINECRAFT_VANILLA_ROOT, "oak_planks")));
                    utils.blocks().setBlockItemModelExecutor(e, DefaultBlockModelExecutors.getDefault(), DefaultBlockItemModelExecutors.getDefaultItemAsVanillaParent(ZPDataGenHelper.DEFAULT_CHEST_ITEM));
                });
                ZPLogger.info("Registering synthetic loot case: " + e.getId() + " : LootTable=" + lootCase.lootId());
            }).end();
        }
        ZPSyntheticLootCasesDataGenRegistry.clearRuntime();
        this.stopInstanceCollecting();
    }

    @Override
    public void preProcessing() {
        ZPLootCasesReader.readFiles();
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