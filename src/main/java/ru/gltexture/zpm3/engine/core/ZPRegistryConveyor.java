package ru.gltexture.zpm3.engine.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class ZPRegistryConveyor {
    private final List<ZPRegistry<?>> laterRun;

    public ZPRegistryConveyor() {
        this.laterRun = new ArrayList<>();
    }

    void launch(Set<Class<? extends ZPRegistry<?>>> registryClasses) {
        List<ZPRegistry<?>> registries = new ArrayList<>();
        try {
            for (Class<? extends ZPRegistry<?>> zpRegistryProcessorClass : registryClasses) {
                ZPRegistry<?> zpRegistry = zpRegistryProcessorClass.getConstructor().newInstance();
                if (zpRegistry.registerLater()) {
                    this.laterRun.add(zpRegistry);
                } else {
                    registries.add(zpRegistry);
                }
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new ZPRuntimeException(e);
        }
        registries.sort(Comparator.comparing((ZPRegistry<?> p) -> p.getTarget().getOrder()).thenComparing(ZPRegistry::priority));

        this.runSet(registries);
    }

    void launchLaterList() {
        this.runSet(this.laterRun);
        this.laterRun.clear();
    }

    private void runSet(List<ZPRegistry<?>> registries) {
        for (ZPRegistry<?> zpRegistry : registries) {
            ZPLogger.info("Initializing ZP registry: " + zpRegistry);
            ZombiePlague3.registerDeferred(zpRegistry.getDeferredRegister());
            zpRegistry.preProcessing();
            zpRegistry.runRegister();
            zpRegistry.postProcessing();
        }
    }

    public enum Target {
        // VANILLA BASIC
        BLOCK(0, Registries.BLOCK),
        FLUID(1, Registries.FLUID),
        ITEM(2, Registries.ITEM),
        ENTITY_TYPE(3, Registries.ENTITY_TYPE),
        BLOCK_ENTITY_TYPE(4, Registries.BLOCK_ENTITY_TYPE),

        // FX
        SOUND_EVENT(10, Registries.SOUND_EVENT),
        PARTICLE_TYPE(11, Registries.PARTICLE_TYPE),
        ENCHANTMENT(12, Registries.ENCHANTMENT),
        MOB_EFFECT(13, Registries.MOB_EFFECT),
        POTION(14, Registries.POTION),
        ATTRIBUTE(15, Registries.ATTRIBUTE),
        PAINTING_VARIANT(16, Registries.PAINTING_VARIANT),
        BANNER_PATTERN(17, Registries.BANNER_PATTERN),
        TRIM_PATTERN(18, Registries.TRIM_PATTERN),
        TRIM_MATERIAL(19, Registries.TRIM_MATERIAL),
        INSTRUMENT(20, Registries.INSTRUMENT),

        // AI
        VILLAGER_PROFESSION(30, Registries.VILLAGER_PROFESSION),
        VILLAGER_TYPE(31, Registries.VILLAGER_TYPE),
        POI_TYPE(32, Registries.POINT_OF_INTEREST_TYPE),
        MEMORY_MODULE_TYPE(33, Registries.MEMORY_MODULE_TYPE),
        SENSOR_TYPE(34, Registries.SENSOR_TYPE),
        ACTIVITY(35, Registries.ACTIVITY),
        SCHEDULE(36, Registries.SCHEDULE),
        CAT_VARIANT(37, Registries.CAT_VARIANT),
        FROG_VARIANT(38, Registries.FROG_VARIANT),
        WOLF_VARIANT(39, Registries.WORLD_PRESET),

        // VANILLA_GEN
        BIOME(50, Registries.BIOME),
        DIMENSION_TYPE(51, Registries.DIMENSION_TYPE),
        DIMENSION(52, Registries.DIMENSION),
        LEVEL_STEM(53, Registries.LEVEL_STEM),
        WORLD_PRESET(54, Registries.WORLD_PRESET),
        NOISE(55, Registries.NOISE),
        NOISE_SETTINGS(56, Registries.NOISE_SETTINGS),
        FEATURE(57, Registries.FEATURE),
        CONFIGURED_FEATURE(58, Registries.CONFIGURED_FEATURE),
        PLACED_FEATURE(59, Registries.PLACED_FEATURE),
        STRUCTURE(60, Registries.STRUCTURE),
        STRUCTURE_SET(61, Registries.STRUCTURE_SET),
        STRUCTURE_TYPE(62, Registries.STRUCTURE_TYPE),
        STRUCTURE_PLACEMENT(63, Registries.STRUCTURE_PLACEMENT),
        STRUCTURE_PIECE(64, Registries.STRUCTURE_PIECE),
        STRUCTURE_POOL_ELEMENT(65, Registries.STRUCTURE_POOL_ELEMENT),
        STRUCTURE_PROCESSOR(66, Registries.STRUCTURE_PROCESSOR),
        PROCESSOR_LIST(67, Registries.PROCESSOR_LIST),
        TEMPLATE_POOL(68, Registries.TEMPLATE_POOL),
        WORLD_CARVER(69, Registries.CARVER),
        CONFIGURED_CARVER(70, Registries.CONFIGURED_CARVER),
        TREE_DECORATOR_TYPE(71, Registries.TREE_DECORATOR_TYPE),
        FOLIAGE_PLACER_TYPE(72, Registries.FOLIAGE_PLACER_TYPE),
        ROOT_PLACER_TYPE(73, Registries.ROOT_PLACER_TYPE),
        TRUNK_PLACER_TYPE(74, Registries.TRUNK_PLACER_TYPE),
        BLOCK_STATE_PROVIDER_TYPE(75, Registries.BLOCK_STATE_PROVIDER_TYPE),
        BIOME_SOURCE(76, Registries.BIOME_SOURCE),
        DENSITY_FUNCTION_TYPE(77, Registries.DENSITY_FUNCTION_TYPE),
        PLACEMENT_MODIFIER_TYPE(78, Registries.PLACEMENT_MODIFIER_TYPE),
        MATERIAL_CONDITION(79, Registries.MATERIAL_CONDITION),
        MATERIAL_RULE(80, Registries.MATERIAL_RULE),
        MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST(81, Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST),

        // VANILLA_SYS
        MENU(91, Registries.MENU),
        GAME_EVENT(92, Registries.GAME_EVENT),
        CHAT_TYPE(93, Registries.CHAT_TYPE),
        COMMAND_ARGUMENT_TYPE(94, Registries.COMMAND_ARGUMENT_TYPE),
        CUSTOM_STAT(95, Registries.CUSTOM_STAT),
        STAT_TYPE(96, Registries.STAT_TYPE),
        DAMAGE_TYPE(97, Registries.DAMAGE_TYPE),
        LOOT_CONDITION_TYPE(98, Registries.LOOT_CONDITION_TYPE),
        LOOT_FUNCTION_TYPE(99, Registries.LOOT_FUNCTION_TYPE),
        LOOT_NBT_PROVIDER_TYPE(100, Registries.LOOT_NBT_PROVIDER_TYPE),
        LOOT_NUMBER_PROVIDER_TYPE(101, Registries.LOOT_NUMBER_PROVIDER_TYPE),
        LOOT_POOL_ENTRY_TYPE(102, Registries.LOOT_POOL_ENTRY_TYPE),
        LOOT_SCORE_PROVIDER_TYPE(103, Registries.LOOT_SCORE_PROVIDER_TYPE),
        RULE_TEST(104, Registries.RULE_TEST),
        POS_RULE_TEST(105, Registries.POS_RULE_TEST),
        POSITION_SOURCE_TYPE(106, Registries.POSITION_SOURCE_TYPE),
        RULE_BLOCK_ENTITY_MODIFIER(107, Registries.RULE_BLOCK_ENTITY_MODIFIER),
        FEATURE_SIZE_TYPE(108, Registries.FEATURE_SIZE_TYPE),
        HEIGHT_PROVIDER_TYPE(109, Registries.HEIGHT_PROVIDER_TYPE),
        INT_PROVIDER_TYPE(110, Registries.INT_PROVIDER_TYPE),
        FLOAT_PROVIDER_TYPE(111, Registries.FLOAT_PROVIDER_TYPE),
        DENSITY_FUNCTION(112, Registries.DENSITY_FUNCTION),
        FLAT_LEVEL_GENERATOR_PRESET(113, Registries.FLAT_LEVEL_GENERATOR_PRESET),

        RECIPE_TYPE(120, Registries.RECIPE_TYPE),
        RECIPE_SERIALIZER(121, Registries.RECIPE_SERIALIZER),

        // FORGE
        FLUID_TYPE(200, ForgeRegistries.Keys.FLUID_TYPES),
        ENTITY_DATA_SERIALIZER(201, ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS),
        GLOBAL_LOOT_MODIFIER_SERIALIZER(202, ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS),
        BIOME_MODIFIER_SERIALIZER(203, ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS),
        STRUCTURE_MODIFIER_SERIALIZER(204, ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS),
        BIOME_MODIFIER(205, ForgeRegistries.Keys.BIOME_MODIFIERS),
        STRUCTURE_MODIFIER(206, ForgeRegistries.Keys.STRUCTURE_MODIFIERS),
        HOLDER_SET_TYPE(207, ForgeRegistries.Keys.HOLDER_SET_TYPES),
        DISPLAY_CONTEXT(208, ForgeRegistries.Keys.DISPLAY_CONTEXTS),

        CREATIVE_MODE_TAB(-1, Registries.CREATIVE_MODE_TAB);

        private final int order;
        private final ResourceKey<? extends Registry<?>> registryKey;

        Target(int order, ResourceKey<? extends Registry<?>> registryKey) {
            this.order = order;
            this.registryKey = registryKey;
        }

        public int getOrder() {
            return order;
        }

        public ResourceKey<? extends Registry<?>> getRegistryKey() {
            return registryKey;
        }

        public static @Nullable Target byRegistry(ResourceKey<? extends Registry<?>> key) {
            for (Target t : values()) {
                if (t.registryKey.equals(key)) {
                    return t;
                }
            }
            return null;
        }
    }

}
