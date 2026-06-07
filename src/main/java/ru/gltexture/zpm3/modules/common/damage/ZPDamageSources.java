package ru.gltexture.zpm3.modules.common.damage;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.common.init.ZPDamageTypes;

public class ZPDamageSources {
    public static DamageSource bullet(@NotNull ServerLevel level, @NotNull Entity shooter) {
        return new DamageSource(ZPDamageTypes.getDamageType(level, ZPDamageTypes.zp_bullet), shooter, shooter);
    }

    public static DamageSource bleed(@NotNull ServerLevel level) {
        return new DamageSource(ZPDamageTypes.getDamageType(level, ZPDamageTypes.zp_bleeding));
    }
}
