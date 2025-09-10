package ru.gltexture.zpm3.assets.common.damage;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import ru.gltexture.zpm3.assets.common.init.ZPDamageTypes;

public class ZPDamageSources {
    public static DamageSource bullet(Entity shooter) {
        return new DamageSource(ZPDamageTypes.getDamageType((ServerLevel) shooter.level(), ZPDamageTypes.zp_bullet), shooter, shooter);
    }
}
