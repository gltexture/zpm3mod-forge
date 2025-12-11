package ru.gltexture.zpm3.engine.registry;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.player.misc.ZPDefaultItemsHandReach;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooks;
import ru.gltexture.zpm3.engine.client.rendering.hooks.ZPRenderHooksManager;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.*;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockItemModelExecutors;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockModelExecutors;
import ru.gltexture.zpm3.engine.helpers.gen.data.VanillaMinecraftModelParentReference;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPBlockModelProvider;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPItemModelProvider;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPSoundListProvider;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.engine.registry.collection.ZPRegistryObjectsCollector;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ZPRegistry<T> {
    private final DeferredRegister<T> deferredRegister;
    private final ZPRegistryConveyor.Target target;
    private @Nullable ZPRegistryObjectsCollector<T> zpRegistryObjectsCollector;

    @SuppressWarnings("all")
    public ZPRegistry(@NotNull ZPRegistryConveyor.Target target) {
        this.deferredRegister = this.createDeferredRegister((ResourceKey<? extends Registry<T>>) target.getRegistryKey());
        this.target = target;
    }
    public ZPRegistry(@NotNull ResourceKey<? extends Registry<T>> registry, @NotNull ZPRegistryConveyor.Target target) {
        this.deferredRegister = this.createDeferredRegister(registry);
        this.target = target;
    }

    protected abstract void runRegister(@NotNull ZPRegistry.ZPRegSupplier<T> regSupplier);

    public final void runRegister() {
        this.checkIfCollector();
        this.runRegister(this.getSupplier());
    }

    @SuppressWarnings("unchecked")
    private void checkIfCollector() {
        if (this instanceof IZPCollectRegistryObjects) {
            this.zpRegistryObjectsCollector = new ZPRegistryObjectsCollector<>();
            ZPRegistryCollections.addNewEntry((Class<? extends ZPRegistry<?>>) this.getClass(), Objects.requireNonNull(this.getObjectsCollector()));
        }
    }

    protected @NotNull DeferredRegister<T> createDeferredRegister(ResourceKey<? extends Registry<T>> registry) {
        return DeferredRegister.create(registry, ZombiePlague3.MOD_ID());
    }

    @SuppressWarnings("all")
    public void stopCollecting() throws ZPRuntimeException {
        if (!this.hasCollector()) {
            throw new ZPRuntimeException("Couldn't stop collecting, because collector wasn't attached");
        }
        this.getObjectsCollector().stopCollecting();
    }

    @SuppressWarnings("all")
    public void pushInstanceCollecting(@NotNull String id) throws ZPRuntimeException {
        if (!this.hasCollector()) {
            throw new ZPRuntimeException("Couldn't continue collecting, because collector wasn't attached");
        }
        this.getObjectsCollector().startCollectingInto(id);
    }

    @SuppressWarnings("all")
    public boolean hasCollector() {
        return this.zpRegistryObjectsCollector != null;
    }

    public @Nullable ZPRegistryObjectsCollector<T> getObjectsCollector() {
        return this.zpRegistryObjectsCollector;
    }

    public abstract int priority();
    public abstract @NotNull String getID();

    public boolean registerLater() {
        return false;
    }

    @SuppressWarnings("all")
    protected ResourceKey<T> createResourceKey(@NotNull String id, @Nullable Consumer<String> after) {
        ResourceKey<T> resourceKey = ResourceKey.create((ResourceKey<? extends Registry<T>>) this.target.getRegistryKey(), ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, id));
        if (after != null) {
            after.accept(id);
        }
        return resourceKey;
    }

    public ZPRegistryConveyor.Target getTarget() {
        return this.target;
    }

    public DeferredRegister<T> getDeferredRegister() {
        return this.deferredRegister;
    }

    protected void preRegister(String name) {
    }

    protected void postRegister(String name, RegistryObject<T> object) {
    }

    public void preProcessing() {
    }

    public void postProcessing() {
    }

    @SuppressWarnings("unchecked")
    private <I extends T> ZPRegSupplier<I> getSupplier() {
        return new ZPRegSupplier<>() {
            @Override
            public <E extends I> ZPRegistryObject<E> register(@NotNull String name, @NotNull Supplier<E> supplier) {
                ZPRegistry.this.preRegister(name);
                RegistryObject<E> object = ZPRegistry.this.getDeferredRegister().register(name, supplier);
                RegistryObject<T> objectT = (RegistryObject<T>) object;
                ZPRegistry.this.postRegister(name, objectT);
                if (ZPRegistry.this.hasCollector()) {
                    Objects.requireNonNull(ZPRegistry.this.getObjectsCollector()).add(objectT);
                }
                return new ZPRegistryObject<>(object);
            }
        };
    }

    @FunctionalInterface
    public interface ZPRegSupplier<R> {
        <E extends R> ZPRegistryObject<E> register(@NotNull String name, @NotNull Supplier<E> supplier);
    }

    @Override
    public String toString() {
        return this.getID();
    }

    public static void execLaterConsumers() {
        for (Map.Entry<Dist, List<Consumer<Void>>> entrySet : ZPRegistry.ZPRegUtils.execLater.entrySet()) {
            switch (entrySet.getKey()) {
                case CLIENT -> ZPUtility.sides().onlyClient(() -> entrySet.getValue().forEach(e -> e.accept(null)));
                case DEDICATED_SERVER -> ZPUtility.sides().onlyDedicatedServer(() -> entrySet.getValue().forEach(e -> e.accept(null)));
            }
        }
        ZPRegistry.ZPRegUtils.execLater.clear();
    }

    public record ZPRegistryObject<S>(RegistryObject<S> end) {
        public ZPRegistryObject<S> afterCreated(@NotNull Consumer<@NotNull RegistryObject<S>> registryObjectConsumer) {
            final ZPRegUtils zpRegUtils = new ZPRegUtils();
            registryObjectConsumer.accept(this.end(), zpRegUtils);
            return this;
        }

        public interface Consumer<T> {
            void accept(@NotNull T t, @NotNull ZPRegUtils regUtils);
        }
    }

    public static final class ZPRegUtils {
        static Map<Dist, List<Consumer<Void>>> execLater = new HashMap<>();

        private ZPRegUtils() {
            this.items = new Items();
            this.blocks = new Blocks();
            this.particles = new Particles();
            this.entities = new Entities();
            this.blockEntities = new BlockEntities();
            this.loot = new Loot();
            this.sounds = new Sounds();
            this.fluids = new Fluids();
        }

        public void execLater(@NotNull Dist dist, @NotNull Consumer<Void> consumer) {
            if (!ZPRegUtils.execLater.containsKey(dist)) {
               ZPRegUtils.execLater.put(dist, new ArrayList<>());
            }
            ZPRegUtils.execLater.get(dist).add(consumer);
        }

        private final Items items;
        private final Blocks blocks;
        private final Fluids fluids;
        private final Particles particles;
        private final Entities entities;
        private final BlockEntities blockEntities;
        private final Loot loot;
        private final Sounds sounds;

        public Fluids fluids() {
            return this.fluids;
        }

        public Items items() {
            return this.items;
        }

        public Blocks blocks() {
            return this.blocks;
        }

        public Particles particles() {
            return this.particles;
        }

        public Entities entities() {
            return this.entities;
        }

        public BlockEntities blockEntities() {
            return this.blockEntities;
        }

        public Loot loot() {
            return this.loot;
        }

        public Sounds sounds() {
            return this.sounds;
        }

        public static final class Fluids {
            private Fluids() {
            }

            @OnlyIn(Dist.CLIENT)
            public void setFluidRenderLayer(@NotNull Supplier<Fluid> liquid, @NotNull RenderType renderType) {
                ZPBlocksRenderLayerHelper.addLiquidRenderLayerData(new ZPBlocksRenderLayerHelper.LiquidPair(liquid, renderType));
            }

            public void addTagToFluid(@NotNull RegistryObject<? extends Fluid> registryObject, @NotNull TagKey<Fluid> tagKey) {
                ZPDataGenHelper.addTagToFluid(registryObject, tagKey);
            }
        }

        public static final class Items {
            private Items() {
            }

            @OnlyIn(Dist.CLIENT)
            public void setItemRenderer(@NotNull RegistryObject<? extends Item> item, @NotNull ZPRenderHooks.ZPItemRendering1PersonHook itemRenderingProcessor1, @NotNull ZPRenderHooks.ZPItemRendering3PersonHook itemRenderingProcessor3) {
                ZPRenderHooksManager.INSTANCE.addItemRendering1PersonHook(item::get, itemRenderingProcessor1);
                ZPRenderHooksManager.INSTANCE.addItemRendering3PersonHook(item::get, itemRenderingProcessor3);
            }

            @OnlyIn(Dist.CLIENT)
            public void setItemRenderer1Person(@NotNull RegistryObject<? extends Item> item, @NotNull ZPRenderHooks.ZPItemRendering1PersonHook itemRenderingProcessor) {
                ZPRenderHooksManager.INSTANCE.addItemRendering1PersonHook(item::get, itemRenderingProcessor);
            }

            @OnlyIn(Dist.CLIENT)
            public void setItemRenderer3Person(@NotNull RegistryObject<? extends Item> item, @NotNull ZPRenderHooks.ZPItemRendering3PersonHook itemRenderingProcessor) {
                ZPRenderHooksManager.INSTANCE.addItemRendering3PersonHook(item::get, itemRenderingProcessor);
            }

            @OnlyIn(Dist.CLIENT)
            public void addItemModel(@NotNull RegistryObject<? extends Item> item, @NotNull Supplier<ZPGenTextureData> itemTextureData) {
                ZPDataGenHelper.addItemDefaultModel(item, itemTextureData);
            }

            @OnlyIn(Dist.CLIENT)
            public void addItemModel(@NotNull RegistryObject<? extends Item> item, @NotNull VanillaMinecraftModelParentReference vanillaMinecraftModelRef, @NotNull String mainTextureKey, @NotNull ZPPath textureDirectory) {
                final String textureName = Objects.requireNonNull(item.getId()).getPath();
                this.addItemModel(item, () -> ZPGenTextureData.of(vanillaMinecraftModelRef, mainTextureKey, () -> new ZPPath(textureDirectory, textureName)));
            }

            @OnlyIn(Dist.CLIENT)
            @SafeVarargs
            public final void addItemModel(@NotNull RegistryObject<? extends Item> item, @NotNull VanillaMinecraftModelParentReference vanillaMinecraftModelRef, @NotNull Pair<@NotNull String, @NotNull Supplier<ZPPath>>... descriptors) {
                this.addItemModel(item, () -> ZPGenTextureData.of(vanillaMinecraftModelRef, descriptors));
            }

            @OnlyIn(Dist.CLIENT)
            public void addItemModel(@NotNull RegistryObject<? extends Item> item, @NotNull VanillaMinecraftModelParentReference vanillaMinecraftModelRef, @NotNull RegistryObject<? extends Item> textureLike) {
                this.addItemModel(item, () -> ZPGenTextureData.copy(vanillaMinecraftModelRef, ZPItemModelProvider.getTextureData(textureLike).get()));
            }

            @OnlyIn(Dist.CLIENT)
            public void addItemInTab(@NotNull RegistryObject<? extends Item> item, @NotNull RegistryObject<CreativeModeTab> creativeModeTab) {
                ZPItemTabAddHelper.addItemInTab(item, creativeModeTab);
            }

            public void addDispenserData(@NotNull RegistryObject<? extends Item> registryObject, @NotNull ZPDispenseProjectileHelper.ProjectileData projectileData) {
                ZPDispenseProjectileHelper.addDispenserData(registryObject, projectileData);
            }

            public void addTagToItem(@NotNull RegistryObject<? extends Item> registryObject, @NotNull TagKey<Item> tagKey) {
                ZPDataGenHelper.addTagToItem(registryObject, tagKey);
            }

            public void setItemDistanceBonus(ResourceLocation path, float v) {
                ZPDefaultItemsHandReach.SET(path, v);
            }
        }

        public static final class Blocks {
            private Blocks() {
            }

            @OnlyIn(Dist.CLIENT)
            @Deprecated
            public void setBlockRenderLayer(@NotNull Supplier<Block> block, @NotNull RenderType renderType) {
                ZPBlocksRenderLayerHelper.addBlockRenderLayerData(new ZPBlocksRenderLayerHelper.BlockPair(block, renderType));
            }

            @OnlyIn(Dist.CLIENT)
            public void addBlockModelWitchGenTextureData(@NotNull RegistryObject<? extends Block> block, @NotNull Supplier<ZPGenTextureData> blockTextureData) {
                ZPDataGenHelper.addBlockDefaultModel(block, blockTextureData);
            }

            @OnlyIn(Dist.CLIENT)
            public void addBlockModelSimpleOneTexture(@NotNull RegistryObject<? extends Block> block, @Nullable VanillaMinecraftModelParentReference vanillaMinecraftModelRef, @NotNull String mainTextureKey, @NotNull ZPPath textureDirectory) {
                final String textureName = Objects.requireNonNull(block.getId()).getPath();
                this.addBlockModelWitchGenTextureData(block, () -> ZPGenTextureData.of(vanillaMinecraftModelRef, mainTextureKey, () -> new ZPPath(textureDirectory, textureName)));
            }

            @OnlyIn(Dist.CLIENT)
            @SafeVarargs
            public final void addBlockModelKey_ValueArray(@NotNull RegistryObject<? extends Block> block, @Nullable VanillaMinecraftModelParentReference vanillaMinecraftModelRef, Pair<@NotNull String, @NotNull Supplier<ZPPath>>... descriptors) {
                this.addBlockModelWitchGenTextureData(block, () -> ZPGenTextureData.of(vanillaMinecraftModelRef, descriptors));
            }

            @OnlyIn(Dist.CLIENT)
            public void addBlockModelWithCopiedTexture(@NotNull RegistryObject<? extends Block> block, @Nullable VanillaMinecraftModelParentReference vanillaMinecraftModelRef, @NotNull RegistryObject<? extends Block> textureLike) {
                this.addBlockModelWitchGenTextureData(block, () -> ZPGenTextureData.copy(vanillaMinecraftModelRef, ZPBlockModelProvider.getTextureData(textureLike).get()));
            }

            @OnlyIn(Dist.CLIENT)
            public <T extends Block> void addModelExecutor(@NotNull Class<T> clazz, @NotNull ZPBlockModelProvider.BlockModelExecutor blockModelExecutor) {
                ZPDataGenHelper.addBlockModelExecutor(clazz, blockModelExecutor);
            }

            @OnlyIn(Dist.CLIENT)
            public void setBlockModelExecutor(@NotNull RegistryObject<? extends Block> block, @NotNull ZPBlockModelProvider.BlockModelExecutor blockModelExecutor) {
                ZPDataGenHelper.setBlockModelExecutor(block, blockModelExecutor);
            }

            @OnlyIn(Dist.CLIENT)
            public void setBlockModelExecutor(@NotNull RegistryObject<? extends Block> block, @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<?> blockModelExecutor) {
                ZPDataGenHelper.setBlockModelExecutor(block, () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(blockModelExecutor, DefaultBlockItemModelExecutors.getDefaultItemAsBlock()));
            }

            @OnlyIn(Dist.CLIENT)
            public void setBlockItemModelExecutor(@NotNull RegistryObject<? extends Block> block, @Nullable ZPBlockModelProvider.BlockModelExecutor.EItem<?> itemModelExecutor) {
                ZPDataGenHelper.setBlockModelExecutor(block, () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(DefaultBlockModelExecutors.getDefault(), itemModelExecutor));
            }

            @OnlyIn(Dist.CLIENT)
            public void setBlockRenderType(@NotNull RegistryObject<? extends Block> block, @NotNull String renderType) {
                ZPDataGenHelper.setBlockRenderType(block, renderType);
            }

            public void addTagToBlock(@NotNull RegistryObject<? extends Block> registryObject, @NotNull TagKey<Block> tagKey) {
                ZPDataGenHelper.addTagToBlock(registryObject, tagKey);
            }
        }

        public static final class Particles {
            private Particles() {
            }

            @OnlyIn(Dist.CLIENT)
            public <T extends ParticleOptions> void matchParticleRenderingSet(@NotNull RegistryObject<? extends ParticleType<T>> type, @NotNull Function<SpriteSet, @NotNull ParticleProvider<T>> particleProvider) {
                ZPParticleRenderHelper.matchParticleRenderingSet(type, particleProvider);
            }

            @OnlyIn(Dist.CLIENT)
            public <T extends ParticleOptions> void addParticlesTexturesData(@NotNull RegistryObject<? extends ParticleType<T>> typeRegistryObject, @NotNull String texturesLink, int arraySize) {
                ZPDataGenHelper.addParticlesTexturesData(typeRegistryObject, texturesLink, arraySize);
            }
        }

        public static final class Entities {
            private Entities() {
            }

            @OnlyIn(Dist.CLIENT)
            public <T extends Entity> void matchEntityRendering(@NotNull RegistryObject<EntityType<T>> registryObject, @NotNull EntityRendererProvider<T> entityRenderer) {
                ZPEntityRenderMatchHelper.matchEntityRendering(registryObject, entityRenderer);
            }
        }

        public static final class BlockEntities {
            private BlockEntities() {
            }

            @OnlyIn(Dist.CLIENT)
            public <T extends BlockEntity> void matchBlockEntityRendering(@NotNull RegistryObject<BlockEntityType<T>> registryObject, @NotNull BlockEntityRendererProvider<T> entityRenderer) {
                ZPBlockEntityRenderMatchHelper.matchBlockEntityRendering(registryObject, entityRenderer);
            }
        }

        public static final class Loot {
            private Loot() {
            }

            public void addBlockLootTable(@NotNull RegistryObject<? extends Block> blockSupplier, @NotNull Supplier<LootPool.Builder> lootPool) {
                ZPDataGenHelper.addBlockLootTable(blockSupplier, lootPool);
            }

            public void addSelfDropLootTable(@NotNull RegistryObject<? extends Block> e) {
                this.addBlockLootTable(e, () -> new LootPool.Builder().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(e.get())));
            }
        }

        public static final class Sounds {
            private Sounds() {
            }

            public void addNewSound(@NotNull ZPSoundListProvider.ZPSoundEvent soundEvent) {
                ZPDataGenHelper.addNewSoundEvent(soundEvent);
            }
        }
    }
}
