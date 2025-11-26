package ru.gltexture.zpm3.assets.common.instances.entities.throwables;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.instances.entities.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelExt;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.entities.ZPThrowableEntity;
import ru.gltexture.zpm3.engine.sound.ZPPositionedSound;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPPlateEntity extends ZPThrowableEntity {
    public ZPPlateEntity(EntityType<ZPPlateEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ZPPlateEntity(EntityType<ZPPlateEntity> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public ZPPlateEntity(EntityType<ZPPlateEntity> pEntityType, LivingEntity pShooter, Level pLevel) {
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
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ZPUtility.sounds().play(new ZPPositionedSound(SoundEvents.GLASS_BREAK, SoundSource.MASTER, 0.8f, 1.0f, this.position().toVector3f(), 0L));
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
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), ZPConstants.DEFAULT_PLATE_DAMAGE);
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
                        ext.getGlobalBlocksDestroyMemory().addNewEntryLongMem(this.level(), pos, (0.1f + ZPRandom.getRandom().nextFloat(0.2f)) * ZPConstants.THROWABLES_BLOCK_BREAK_MULTIPLIER);
                    }
                }
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ZPItems.plate.get();
    }
}