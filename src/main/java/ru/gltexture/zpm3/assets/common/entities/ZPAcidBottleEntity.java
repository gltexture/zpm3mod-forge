package ru.gltexture.zpm3.assets.common.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.engine.objects.entities.ZPThrowableEntity;

public class ZPAcidBottleEntity extends ZPThrowableEntity {
    public ZPAcidBottleEntity(EntityType<ZPAcidBottleEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ZPAcidBottleEntity(EntityType<ZPAcidBottleEntity> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public ZPAcidBottleEntity(EntityType<ZPAcidBottleEntity> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ZPItems.acid_bottle.get();
    }
}
