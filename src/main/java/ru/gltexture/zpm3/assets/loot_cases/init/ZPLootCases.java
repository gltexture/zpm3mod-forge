package ru.gltexture.zpm3.assets.loot_cases.init;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.loot_cases.instances.blocks.ZPDefaultBlockLootCase;
import ru.gltexture.zpm3.assets.loot_cases.loot_tables.ZPLootTable;
import ru.gltexture.zpm3.assets.loot_cases.registry.ZPLootTablesCollection;
import ru.gltexture.zpm3.assets.loot_cases.registry.ZPLootTablesReader;
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
        this.pushInstanceCollecting("lootCases");
        for (ZPLootTable lootTable : ZPLootTablesCollection.INSTANCE.getAllLootTables().stream().filter(e -> e.getLootCaseData() != null).toList()) {
            final String lootCaseName = Objects.requireNonNull(lootTable.getLootCaseData()).name().toLowerCase();
            final boolean isUnbreakable = lootTable.getLootCaseData().isUnbreakable();
            final int lootRespawnTime = lootTable.getLootCaseData().respawnTime();
            RegistryObject<ZPDefaultBlockLootCase> syntheticLootCase = regSupplier.register(lootCaseName, () -> new ZPDefaultBlockLootCase(BlockBehaviour.Properties.of().strength(isUnbreakable ? -1.0f : 5.0f, isUnbreakable ? Float.MAX_VALUE : 5.0f).sound(SoundType.WOOD), lootTable.getLootCaseData().textureId(), lootTable, lootRespawnTime)
            ).afterCreated((e, utils) -> {
                ZPUtility.sides().onlyClient(() -> {
                    utils.blocks().addBlockModelKey_ValueArray(e, ZPDataGenHelper.DEFAULT_CHEST_BLOCK, Pair.of("particle", () -> new ZPPath(ZPDataGenHelper.MINECRAFT_VANILLA_ROOT, "oak_planks")));
                    utils.blocks().setBlockItemModelExecutor(e, DefaultBlockItemModelExecutors.getDefaultItemAsVanillaParent(ZPDataGenHelper.DEFAULT_CHEST_ITEM));
                });
            }).end();
            ZPLootCases.generatedLootCases.put(lootCaseName, syntheticLootCase);
        }
        this.stopCollecting();
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