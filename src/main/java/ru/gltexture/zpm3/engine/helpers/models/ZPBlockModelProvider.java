package ru.gltexture.zpm3.engine.helpers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.utils.Pair;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ZPBlockModelProvider extends BlockStateProvider {
    private static final Set<Pair<Supplier<Block>, String>> blocksWithDefaultModel = new HashSet<>();

    public ZPBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ZombiePlague3.MOD_ID(), existingFileHelper);
    }

    public static void addNewObject(Pair<Supplier<Block>, String> block) {
        ZPBlockModelProvider.blocksWithDefaultModel.add(block);
    }

    public static void clearSet() {
        ZPBlockModelProvider.blocksWithDefaultModel.clear();
    }

    @Override
    protected void registerStatesAndModels() {
        ZPBlockModelProvider.blocksWithDefaultModel.forEach(dataSupplier -> {
            Block block = dataSupplier.getFirst().get();
            String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
            ResourceLocation texture = modLoc("block/" + name);
            this.models().withExistingParent(name, "minecraft:" + dataSupplier.getSecond()).texture("all", texture);
            this.models().getBuilder(name + "_item").parent(this.models().getExistingFile(modLoc("block/" + name)));
            this.itemModels().withExistingParent(name, modLoc("block/" + name));
            this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(this.models().getExistingFile(modLoc("block/" + name))).build());
        });

        ZPBlockModelProvider.clearSet();
    }
}
