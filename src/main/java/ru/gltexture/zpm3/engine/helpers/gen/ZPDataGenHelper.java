package ru.gltexture.zpm3.engine.helpers.gen;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.helpers.ZPBlockTagsHelper;
import ru.gltexture.zpm3.engine.helpers.ZPLootTableHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.VanillaMCModelRef;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPBlockModelProvider;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPItemModelProvider;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPParticleTextureProvider;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPSoundListProvider;
import ru.gltexture.zpm3.engine.service.ZPPath;

import java.util.function.Supplier;

public abstract class ZPDataGenHelper {
    public static final String DEFAULT_RENDER_TYPE = "solid";
    public static final String CUTOUT_RENDER_TYPE = "cutout";
    public static final String TRANSLUCENT_RENDER_TYPE = "translucent";

    public static final VanillaMCModelRef DEFAULT_BLOCK_CUBE = new VanillaMCModelRef("block/cobblestone");
    public static final VanillaMCModelRef DEFAULT_BLOCK_STAIRS = new VanillaMCModelRef("block/cobblestone_stairs");
    public static final VanillaMCModelRef DEFAULT_BLOCK_SLAB = new VanillaMCModelRef("block/cobblestone_slab");
    public static final VanillaMCModelRef DEFAULT_BLOCK_CROSS = new VanillaMCModelRef("block/cross");

    public static final VanillaMCModelRef DEFAULT_TORCH = new VanillaMCModelRef("block/template_torch");
    public static final VanillaMCModelRef DEFAULT_TORCH_WALL = new VanillaMCModelRef("block/template_torch_wall");

    public static final VanillaMCModelRef DEFAULT_BLOCK_PILLAR = new VanillaMCModelRef("block/cube_column");

    public static final VanillaMCModelRef DEFAULT_FOOD = new VanillaMCModelRef("item/bread");
    public static final VanillaMCModelRef DEFAULT_ITEM = new VanillaMCModelRef("item/diamond");
    public static final VanillaMCModelRef DEFAULT_MELEE = new VanillaMCModelRef("item/diamond_sword");
    public static final VanillaMCModelRef DEFAULT_AXE = new VanillaMCModelRef("item/diamond_axe");
    public static final VanillaMCModelRef DEFAULT_PICKAXE = new VanillaMCModelRef("item/diamond_pickaxe");
    public static final VanillaMCModelRef DEFAULT_SHOVEL = new VanillaMCModelRef("item/diamond_shovel");
    public static final VanillaMCModelRef DEFAULT_HOE = new VanillaMCModelRef("item/diamond_hoe");

    public static final ZPPath COMMON_BLOCKS_DIRECTORY = new ZPPath("common");
    public static final ZPPath TORCH_BLOCKS_DIRECTORY = new ZPPath("torch");
    public static final ZPPath PILLAR_BLOCKS_DIRECTORY = new ZPPath("pillar");
    public static final ZPPath MINECRAFT_VANILLA_ROOT = new ZPPath("VANILLA_MC$");

    public static final ZPPath FOOD_ITEMS_DIRECTORY = new ZPPath("food");
    public static final ZPPath ITEMS_ITEMS_DIRECTORY = new ZPPath("items");
    public static final ZPPath MELEE_ITEMS_DIRECTORY = new ZPPath("melee");
    public static final ZPPath MEDICINE_ITEMS_DIRECTORY = new ZPPath("medicine");

    public static void addItemDefaultModel(@NotNull RegistryObject<? extends Item> item, @NotNull Supplier<ZPGenTextureData> itemTextureData) {
        ZPItemModelProvider.addNewObject(item, itemTextureData);
    }

    public static void addBlockDefaultModel(@NotNull RegistryObject<? extends Block> block, @NotNull Supplier<ZPGenTextureData> blockTextureData) {
        ZPBlockModelProvider.addNewObject(block, blockTextureData);
    }

    public static void addBlockLootTable(@NotNull RegistryObject<? extends Block> blockSupplier, @NotNull Supplier<LootPool.Builder> lootPool) {
        ZPLootTableHelper.addBlockLootTable(blockSupplier, lootPool);
    }

    public static void addTagToBlock(@NotNull RegistryObject<? extends Block> registryObject, @NotNull TagKey<Block> tagKey) {
        ZPBlockTagsHelper.addTagToBlock(registryObject, tagKey);
    }

    public static <T extends Block> void addBlockModelExecutor(@NotNull Class<T> clazz, @NotNull ZPBlockModelProvider.BlockModelExecutor blockModelExecutor) {
        ZPBlockModelProvider.addBlockModelExecutor(clazz, blockModelExecutor);
    }

    public static void setBlockModelExecutor(@NotNull RegistryObject<? extends Block> block, @NotNull ZPBlockModelProvider.BlockModelExecutor blockModelExecutor) {
        ZPBlockModelProvider.setBlockModelExecutor(block, blockModelExecutor);
    }

    public static void setBlockRenderType(@NotNull RegistryObject<? extends Block> block, @NotNull String renderType) {
        ZPBlockModelProvider.setBlockRenderType(block, renderType);
    }

    public static void addParticlesTexturesData(@NotNull RegistryObject<? extends ParticleType<?>> typeRegistryObject, @NotNull String texturesLink, int arraySize) {
        ZPParticleTextureProvider.addParticlesTexturesData(typeRegistryObject, texturesLink, arraySize);
    }

    public static void addNewSoundEvent(@NotNull ZPSoundListProvider.ZPSoundEvent soundEvent) {
        ZPSoundListProvider.addNewSoundEvent(soundEvent);
    }

    public static @NotNull ResourceLocation locate(@NotNull BlockStateProvider blockStateProvider, @NotNull String ref) {
        if (ZPDataGenHelper.isVanillaRoot(ref)) {
            return blockStateProvider.mcLoc("block" + ref.substring(ZPDataGenHelper.MINECRAFT_VANILLA_ROOT.getFullPath().length()));
        }
        return blockStateProvider.modLoc("block" + ref);
    }

    public static @NotNull ResourceLocation locate(@NotNull ItemModelProvider itemModelProvider, @NotNull String ref) {
        if (ZPDataGenHelper.isVanillaRoot(ref)) {
            return itemModelProvider.mcLoc("item" + ref.substring(ZPDataGenHelper.MINECRAFT_VANILLA_ROOT.getFullPath().length()));
        }
        return itemModelProvider.modLoc("item" + ref);
    }

    public static boolean isVanillaRoot(String ref) {
        return ref.startsWith(ZPDataGenHelper.MINECRAFT_VANILLA_ROOT.getFullPath());
    }
}