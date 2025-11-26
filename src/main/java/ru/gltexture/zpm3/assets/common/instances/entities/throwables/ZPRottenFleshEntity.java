package ru.gltexture.zpm3.assets.common.instances.entities.throwables;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelExt;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.entities.ZPThrowableEntity;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.engine.sound.ZPPositionedSound;

public class ZPRottenFleshEntity extends ZPThrowableEntity {
    public ZPRottenFleshEntity(EntityType<ZPRottenFleshEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ZPRottenFleshEntity(EntityType<ZPRottenFleshEntity> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public ZPRottenFleshEntity(EntityType<ZPRottenFleshEntity> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void initClient() {
    }

    @Override
    protected void initServer() {

    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            ZPUtility.client().ifClientLevelValid(() -> {
                final Vector3f randomVector = ZPRandom.instance.randomVector3f(0.01f, new Vector3f(0.05f, 0.0f, 0.05f)).add(0.0f, 0.05f, 0.0f);
                ZPCommonClientUtils.emmitToxicParticle(0.6f + ZPRandom.getRandom().nextFloat(0.3f), this.position().toVector3f().add(0.0f, this.getBbHeight() + 0.4f, 0.0f), randomVector);
            });
        }
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ZPUtility.sounds().play(new ZPPositionedSound(SoundEvents.SLIME_BLOCK_BREAK, SoundSource.MASTER, 1.0f, 0.6f, this.position().toVector3f(), 0L));
            ZPCommonClientUtils.emmitItemBreakParticle(this.getItem(), this.position().toVector3f(), this.getDeltaMovement().toVector3f());
        }
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity pTarget) {
        if (!super.canHitEntity(pTarget)) {
            return false;
        }
        return !(this.getOwner() instanceof ZPAbstractZombie) || !(pTarget instanceof ZPAbstractZombie);
    }

    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if (!entity.level().isClientSide()) {
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), ZPConstants.DEFAULT_ROTTEN_FLESH_DAMAGE);
            if (entity instanceof LivingEntity livingEntity) {
                ZPAbstractZombie.applyRandomEffect(livingEntity);
            }
        }
    }

    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            if (pResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) pResult;
                BlockPos pos = blockHit.getBlockPos();
                if (!this.level().isEmptyBlock(pos)) {
                    if (this.level() instanceof IZPLevelExt ext) {
                        ext.getGlobalBlocksDestroyMemory().addNewEntryLongMem(this.level(), pos, (0.5f + ZPRandom.getRandom().nextFloat(1.5f)) * ZPConstants.THROWABLES_BLOCK_BREAK_MULTIPLIER);
                    }
                }
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return Items.ROTTEN_FLESH;
    }
}