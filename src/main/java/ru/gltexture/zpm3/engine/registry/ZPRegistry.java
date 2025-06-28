package ru.gltexture.zpm3.engine.registry;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.helpers.*;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.gen.block_exec.DefaultBlockItemModelExecutors;
import ru.gltexture.zpm3.engine.helpers.gen.data.VanillaMCModelRef;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPBlockModelProvider;
import ru.gltexture.zpm3.engine.helpers.gen.providers.ZPItemModelProvider;
import ru.gltexture.zpm3.engine.registry.collection.IZPCollectRegistryObjects;
import ru.gltexture.zpm3.engine.registry.collection.ZPRegistryObjectsCollector;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.service.ZPPath;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ZPRegistry<T> {
    private final DeferredRegister<T> deferredRegister;
    private final ZPRegistryConveyor.Target target;
    private @Nullable ZPRegistryObjectsCollector<T> zpRegistryObjectsCollector;

    public ZPRegistry(@NotNull IForgeRegistry<T> registry, @NotNull ZPRegistryConveyor.Target target) {
        this.deferredRegister = this.createDeferredRegister(registry);
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

    protected @NotNull DeferredRegister<T> createDeferredRegister(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, ZombiePlague3.MOD_ID());
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
    public void continueCollecting() throws ZPRuntimeException {
        if (!this.hasCollector()) {
            throw new ZPRuntimeException("Couldn't continue collecting, because collector wasn't attached");
        }
        this.getObjectsCollector().continueCollecting();
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

    public record ZPRegistryObject<S>(RegistryObject<S> registryObject) {
        public ZPRegistryObject<S> postConsume(@Nullable Dist side, @NotNull Consumer<@NotNull RegistryObject<S>> registryObjectConsumer) {
            final ZPRegUtils zpRegUtils = ZPRegUtils.create();

            if (side == null) {
                registryObjectConsumer.accept(this.registryObject(), zpRegUtils);
                return this;
            }
            switch (side) {
                case CLIENT -> ZPUtility.sides().onlyClient(() -> registryObjectConsumer.accept(this.registryObject(), zpRegUtils));
                case DEDICATED_SERVER -> ZPUtility.sides().onlyServer(() -> registryObjectConsumer.accept(this.registryObject(), zpRegUtils));
            }
            return this;
        }

        public interface Consumer<T> {
            void accept(@NotNull T t, @NotNull ZPRegUtils regUtils);
        }
    }

    public static final class ZPRegUtils {
        private ZPRegUtils() {
        }

        public static ZPRegUtils create() {
            return new ZPRegUtils();
        }


        public void addItemModel(@NotNull RegistryObject<? extends Item> item, @NotNull Supplier<ZPGenTextureData> itemTextureData) {
            ZPDataGenHelper.addItemDefaultModel(item, itemTextureData);
        }

        public void addBlockModel(@NotNull RegistryObject<? extends Block> block, @NotNull Supplier<ZPGenTextureData> blockTextureData) {
            ZPDataGenHelper.addBlockDefaultModel(block, blockTextureData);
        }

        public void addItemModel(@NotNull RegistryObject<? extends Item> item, @NotNull VanillaMCModelRef vanillaMCModelRef, @NotNull RegistryObject<? extends Item> textureLike) {
            this.addItemModel(item, () -> ZPGenTextureData.copy(vanillaMCModelRef, ZPItemModelProvider.getTextureData(textureLike).get()));
        }

        public void addBlockModel(@NotNull RegistryObject<? extends Block> block, @Nullable VanillaMCModelRef vanillaMCModelRef, @NotNull RegistryObject<? extends Block> textureLike) {
            this.addBlockModel(block, () -> ZPGenTextureData.copy(vanillaMCModelRef, ZPBlockModelProvider.getTextureData(textureLike).get()));
        }

        @SafeVarargs
        public final void addItemModel(@NotNull RegistryObject<? extends Item> item, @NotNull VanillaMCModelRef vanillaMCModelRef, @NotNull Pair<@NotNull String, @NotNull Supplier<ZPPath>>... descriptors) {
            this.addItemModel(item, () -> ZPGenTextureData.of(vanillaMCModelRef, descriptors));
        }

        @SafeVarargs
        public final void addBlockModel(@NotNull RegistryObject<? extends Block> block, @Nullable VanillaMCModelRef vanillaMCModelRef, Pair<@NotNull String, @NotNull Supplier<ZPPath>>... descriptors) {
            this.addBlockModel(block, () -> ZPGenTextureData.of(vanillaMCModelRef, descriptors));
        }

        public void addItemModel(@NotNull RegistryObject<? extends Item> item, @NotNull VanillaMCModelRef vanillaMCModelRef, @NotNull ZPPath textureDirectory) {
            final String textureName = Objects.requireNonNull(item.getId()).getPath();
            this.addItemModel(item, () -> ZPGenTextureData.of(vanillaMCModelRef, ZPGenTextureData.LAYER0_KEY, () -> new ZPPath(textureDirectory, textureName)));
        }

        public void addBlockModel(@NotNull RegistryObject<? extends Block> block, @Nullable VanillaMCModelRef vanillaMCModelRef, @NotNull ZPPath textureDirectory) {
            final String textureName = Objects.requireNonNull(block.getId()).getPath();
            this.addBlockModel(block, () -> ZPGenTextureData.of(vanillaMCModelRef, ZPGenTextureData.ALL_KEY, () -> new ZPPath(textureDirectory, textureName)));
        }

        public void addItemModel(@NotNull RegistryObject<? extends Item> item, @NotNull VanillaMCModelRef vanillaMCModelRef, @NotNull String key, @NotNull ZPPath textureDirectory) {
            final String textureName = Objects.requireNonNull(item.getId()).getPath();
            this.addItemModel(item, () -> ZPGenTextureData.of(vanillaMCModelRef, key, () -> new ZPPath(textureDirectory, textureName)));
        }

        public void addBlockModel(@NotNull RegistryObject<? extends Block> block, @Nullable VanillaMCModelRef vanillaMCModelRef, @NotNull String key, @NotNull ZPPath textureDirectory) {
            final String textureName = Objects.requireNonNull(block.getId()).getPath();
            this.addBlockModel(block, () -> ZPGenTextureData.of(vanillaMCModelRef, key, () -> new ZPPath(textureDirectory, textureName)));
        }

        public <T extends Block> void addModelExecutor(@NotNull Class<T> clazz, @NotNull ZPBlockModelProvider.BlockModelExecutor blockModelExecutor) {
            ZPDataGenHelper.addBlockModelExecutor(clazz, blockModelExecutor);
        }

        public void addBlockModelExecutor(@NotNull RegistryObject<? extends Block> block, @NotNull ZPBlockModelProvider.BlockModelExecutor blockModelExecutor) {
            ZPDataGenHelper.addBlockModelExecutor(block, blockModelExecutor);
        }

        public void addBlockModelExecutor(@NotNull RegistryObject<? extends Block> block, @NotNull ZPBlockModelProvider.BlockModelExecutor.EBlock<?> blockModelExecutor) {
            ZPDataGenHelper.addBlockModelExecutor(block, () -> new ZPBlockModelProvider.BlockModelExecutor.Pair(blockModelExecutor, DefaultBlockItemModelExecutors.getDefaultItemAsBlock()));
        }

        public void setBlockRenderType(@NotNull RegistryObject<? extends Block> block, @NotNull String renderType) {
            ZPDataGenHelper.setBlockRenderType(block, renderType);
        }

        public void addItemInTab(@NotNull RegistryObject<? extends Item> item, @NotNull RegistryObject<CreativeModeTab> creativeModeTab) {
            ZPItemTabAddHelper.addItemInTab(item, creativeModeTab);
        }

        public void addBlockLootTable(@NotNull RegistryObject<? extends Block> blockSupplier, @NotNull Supplier<LootPool.Builder> lootPool) {
            ZPDataGenHelper.addBlockLootTable(blockSupplier, lootPool);
        }

        public void addTagToBlock(@NotNull RegistryObject<? extends Block> registryObject, @NotNull TagKey<Block> tagKey) {
            ZPDataGenHelper.addTagToBlock(registryObject, tagKey);
        }

        public <T extends ParticleOptions> void matchParticleRendering(@NotNull RegistryObject<? extends ParticleType<T>> type, @NotNull Function<SpriteSet, @NotNull ParticleProvider<T>> particleProvider) {
            ZPParticleRenderHelper.matchParticleRendering(type, particleProvider);
        }

        public <T extends ParticleOptions> void addParticlesTexturesData(@NotNull RegistryObject<? extends ParticleType<T>> typeRegistryObject, @NotNull String texturesLink, int arraySize) {
            ZPDataGenHelper.addParticlesTexturesData(typeRegistryObject, texturesLink, arraySize);
        }

        public void addDispenserData(@NotNull RegistryObject<? extends Item> registryObject, @NotNull ZPDispenserHelper.ProjectileData projectileData) {
            ZPDispenserHelper.addDispenserData(registryObject, projectileData);
        }

        public <T extends Entity> void matchEntityRendering(@NotNull RegistryObject<EntityType<T>> registryObject, @NotNull EntityRendererProvider<T> entityRenderer) {
            ZPEntityRenderMatchHelper.matchEntityRendering(registryObject, entityRenderer);
        }

        public void addSelfDropLootTable(@NotNull RegistryObject<? extends Block> e) {
            this.addBlockLootTable(e, () -> new LootPool.Builder().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(e.get())));
        }
    }
}
