package ru.gltexture.zpm3.engine.helpers.gen;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.helpers.ZPBlockTagsHelper;
import ru.gltexture.zpm3.engine.helpers.ZPFluidTagsHelper;
import ru.gltexture.zpm3.engine.helpers.ZPLootTableHelper;
import ru.gltexture.zpm3.engine.helpers.gen.data.VanillaMinecraftModelParentReference;
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

    public static final VanillaMinecraftModelParentReference DEFAULT_CHEST_BLOCK = new VanillaMinecraftModelParentReference("block/chest");
    public static final VanillaMinecraftModelParentReference DEFAULT_CHEST_ITEM = new VanillaMinecraftModelParentReference("item/chest");
    public static final VanillaMinecraftModelParentReference DEFAULT_WATER = new VanillaMinecraftModelParentReference("block/water");
    public static final VanillaMinecraftModelParentReference DEFAULT_BLOCK_CUBE = new VanillaMinecraftModelParentReference("block/cobblestone");
    public static final VanillaMinecraftModelParentReference DEFAULT_BLOCK_STAIRS = new VanillaMinecraftModelParentReference("block/cobblestone_stairs");
    public static final VanillaMinecraftModelParentReference DEFAULT_BLOCK_SLAB = new VanillaMinecraftModelParentReference("block/cobblestone_slab");
    public static final VanillaMinecraftModelParentReference DEFAULT_BLOCK_CROSS = new VanillaMinecraftModelParentReference("block/cross");
    public static final @Nullable VanillaMinecraftModelParentReference NO_REFERENCE = null;

    public static final VanillaMinecraftModelParentReference DEFAULT_TORCH = new VanillaMinecraftModelParentReference("block/template_torch");
    public static final VanillaMinecraftModelParentReference DEFAULT_TORCH_WALL = new VanillaMinecraftModelParentReference("block/template_torch_wall");

    public static final VanillaMinecraftModelParentReference DEFAULT_BLOCK_PILLAR = new VanillaMinecraftModelParentReference("block/cube_column");

    public static final VanillaMinecraftModelParentReference DEFAULT_SPAWN_EGG = new VanillaMinecraftModelParentReference("item/template_spawn_egg");

    public static final VanillaMinecraftModelParentReference DEFAULT_FOOD = new VanillaMinecraftModelParentReference("item/bread");
    public static final VanillaMinecraftModelParentReference DEFAULT_ITEM = new VanillaMinecraftModelParentReference("item/diamond");
    public static final VanillaMinecraftModelParentReference DEFAULT_MELEE = new VanillaMinecraftModelParentReference("item/diamond_sword");
    public static final VanillaMinecraftModelParentReference DEFAULT_AXE = new VanillaMinecraftModelParentReference("item/diamond_axe");
    public static final VanillaMinecraftModelParentReference DEFAULT_PICKAXE = new VanillaMinecraftModelParentReference("item/diamond_pickaxe");
    public static final VanillaMinecraftModelParentReference DEFAULT_SHOVEL = new VanillaMinecraftModelParentReference("item/diamond_shovel");
    public static final VanillaMinecraftModelParentReference DEFAULT_HOE = new VanillaMinecraftModelParentReference("item/diamond_hoe");

    public static final ZPPath COMMON_BLOCKS_DIRECTORY = new ZPPath("common");
    public static final ZPPath TORCH_BLOCKS_DIRECTORY = new ZPPath("torch");
    public static final ZPPath PILLAR_BLOCKS_DIRECTORY = new ZPPath("pillar");
    public static final ZPPath MINECRAFT_VANILLA_ROOT = new ZPPath("VANILLA_MC$");

    public static final ZPPath GUN_ITEMS_DIRECTORY = new ZPPath("guns");
    public static final ZPPath FOOD_ITEMS_DIRECTORY = new ZPPath("food");
    public static final ZPPath ITEMS_ITEMS_DIRECTORY = new ZPPath("items");
    public static final ZPPath MISC_ITEMS_DIRECTORY = new ZPPath("misc");
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

    public static void addTagToFluid(@NotNull RegistryObject<? extends Fluid> registryObject, @NotNull TagKey<Fluid> tagKey) {
        ZPFluidTagsHelper.addTagToFluid(registryObject, tagKey);
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