package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;

public abstract class ZPItemBlockHelper {
    public static RegistryObject<Item> createBlockItem(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier, @NotNull RegistryObject<Block> block) {
        if (block.getId() == null) {
            throw new ZPRuntimeException("Block has NULL ResourceId");
        }
        return regSupplier.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
