package ru.gltexture.zpm3.assets.common.damage;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPDamageTypes;

public class ZPDamageSources {
    public static DamageSource bullet(@NotNull ServerLevel level, @NotNull Entity shooter) {
        return new DamageSource(ZPDamageTypes.getDamageType(level, ZPDamageTypes.zp_bullet), shooter, shooter);
    }

    public static DamageSource bleed(@NotNull ServerLevel level) {
        return new DamageSource(ZPDamageTypes.getDamageType(level, ZPDamageTypes.zp_bleeding));
    }
}
