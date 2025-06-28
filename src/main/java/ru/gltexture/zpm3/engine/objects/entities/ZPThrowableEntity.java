package ru.gltexture.zpm3.engine.objects.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public abstract class ZPThrowableEntity extends ThrowableItemProjectile {
    public ZPThrowableEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.initFromConstructor();
    }

    public ZPThrowableEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
        this.initFromConstructor();
    }

    public ZPThrowableEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
        this.initFromConstructor();
    }

    private void initFromConstructor() {
        ZPUtility.sides().onlyClient(this::initClient);
        ZPUtility.sides().onlyServer(this::initServer);
    }

    protected void initClient() { }

    protected void initServer() { }
}
