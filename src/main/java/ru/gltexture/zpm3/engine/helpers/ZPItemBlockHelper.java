package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

import java.util.function.Consumer;

public abstract class ZPItemBlockHelper {
    public static ZPRegistry.ZPRegistryObject<BlockItem> createBlockItem(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier, @NotNull RegistryObject<? extends Block> block) {
        if (block.getId() == null) {
            throw new ZPRuntimeException("Block has NULL ResourceId");
        }
        return regSupplier.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static ZPRegistry.ZPRegistryObject<BlockItem> createWallBlockItem(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier, @NotNull RegistryObject<? extends Block> block, @NotNull RegistryObject<? extends Block> wallBlock) {
        if (block.getId() == null) {
            throw new ZPRuntimeException("Block has NULL ResourceId");
        }
        return regSupplier.register(block.getId().getPath(), () -> new StandingAndWallBlockItem(block.get(), wallBlock.get(), new Item.Properties(), Direction.DOWN));
    }

    public static ZPRegistry.ZPRegistryObject<BlockItem> createBlockItemWithClientCustomInit(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier, @NotNull RegistryObject<? extends Block> block, @NotNull Consumer<Consumer<IClientItemExtensions>> customConsumer) {
        if (block.getId() == null) {
            throw new ZPRuntimeException("Block has NULL ResourceId");
        }
        return regSupplier.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()) {
            @Override
            public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
                customConsumer.accept(consumer);
            }
        });
    }
}
