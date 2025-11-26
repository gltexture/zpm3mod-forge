package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public abstract class ZPBlockEntityRenderMatchHelper {
    private static final Set<BlockEntityRenderPair<? extends BlockEntity>> blockEntityRendererPairs = new HashSet<>();

    public static <T extends BlockEntity> void matchBlockEntityRendering(@NotNull RegistryObject<BlockEntityType<T>> registryObject, @NotNull BlockEntityRendererProvider<T> entityRenderer) {
        ZPBlockEntityRenderMatchHelper.blockEntityRendererPairs.add(new BlockEntityRenderPair<>(registryObject, entityRenderer));
    }

    public static Set<BlockEntityRenderPair<? extends BlockEntity>> getBlockEntityRendererPairs() {
        return ZPBlockEntityRenderMatchHelper.blockEntityRendererPairs;
    }

    public static void clear() {
        ZPBlockEntityRenderMatchHelper.blockEntityRendererPairs.clear();
    }

    public record BlockEntityRenderPair<T extends BlockEntity> (@NotNull RegistryObject<BlockEntityType<T>> registryObject, @NotNull BlockEntityRendererProvider<T> provider) {}
}
