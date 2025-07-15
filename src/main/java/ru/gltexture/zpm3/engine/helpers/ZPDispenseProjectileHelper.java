package ru.gltexture.zpm3.engine.helpers;

import net.minecraft.core.Position;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class ZPDispenseProjectileHelper {
    private static final Map<RegistryObject<? extends Item>, ProjectileData> dispenserMap = new HashMap<>();

    public static void addDispenserData(@NotNull RegistryObject<? extends Item> registryObject, @NotNull ProjectileData projectileData) {
        ZPDispenseProjectileHelper.dispenserMap.put(registryObject, projectileData);
    }

    public static Map<RegistryObject<? extends Item>, ProjectileData> getDispenserMap() {
        return ZPDispenseProjectileHelper.dispenserMap;
    }

    public static void clear() {
        ZPDispenseProjectileHelper.dispenserMap.clear();
    }

    public record ProjectileData(@NotNull DispenserProjectileFactory projectileFactory, float inaccuracy, float power) {}

    @FunctionalInterface
    public interface DispenserProjectileFactory {
        @NotNull Projectile getProjectile(@NotNull Level pLevel, @NotNull Position pPosition, @NotNull ItemStack pStack);
    }
}