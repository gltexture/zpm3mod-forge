package ru.gltexture.zpm3.assets.common.instances.entities;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.assets.entity.nbt.ZPTagsList;
import ru.gltexture.zpm3.assets.net_pack.packets.AcidSpreadPacket;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.nbt.ZPEntityNBT;
import ru.gltexture.zpm3.engine.objects.entities.ZPThrowableEntity;
import ru.gltexture.zpm3.engine.sound.ZPPositionedSound;
import ru.gltexture.zpm3.engine.utils.ZPUtility;

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
    @OnlyIn(Dist.CLIENT)
    protected void initClient() {
    }

    @Override
    @OnlyIn(Dist.DEDICATED_SERVER)
    protected void initServer() {

    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            ZPUtility.client().ifClientLevelValid(() -> {
                ZPCommonClientUtils.emmitAcidParticle(1.5f + ZPRandom.getRandom().nextFloat(0.3f), this.position().toVector3f().add(0.0f, this.getBbHeight() + 0.4f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f));
                if (this.tickCount % 3 == 0) {
                    ZPUtility.sounds().play(new ZPPositionedSound(SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 0.375f, 1.15f, this.position().toVector3f(), 0L));
                }
            });
        }
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ZPUtility.sounds().play(new ZPPositionedSound(SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 0.8f, 0.75f, this.position().toVector3f(), 0L));
            ZPUtility.sounds().play(new ZPPositionedSound(SoundEvents.GLASS_BREAK, SoundSource.MASTER, 0.8f, 1.0f, this.position().toVector3f(), 0L));

            for (int i = 0; i < 40; i++) {
                final Vector3f randomVector = ZPRandom.instance.randomVector3f(0.05f);
                final Vector3f position = this.position().toVector3f();
                position.add(ZPRandom.instance.randomVector3f(0.3f, new Vector3f(0.6f, 0.0f, 0.6f)));
                ZPCommonClientUtils.emmitAcidParticle(2.2f + ZPRandom.getRandom().nextFloat(0.3f), position, new Vector3f(randomVector.x, (randomVector.y * 0.1f) + 0.05f, randomVector.z));
            }

            ZPCommonClientUtils.emmitItemBreakParticle(this.getItem(), this.position().toVector3f(), this.getDeltaMovement().toVector3f());
        }
    }

    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if (!entity.level().isClientSide()) {
            new ZPEntityNBT(entity).incrementInt(ZPTagsList.ACID_AFFECT_COOLDOWN, ZPConstants.DEFAULT_ACID_BOTTLE_AFFECT_TIME);
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), ZPConstants.DEFAULT_ACID_BOTTLE_DAMAGE);
            ZombiePlague3.net().sendToRadius(new AcidSpreadPacket(entity.getId(), ZPConstants.DEFAULT_ACID_BOTTLE_AFFECT_TIME), this.level(), this.position(), ZPConstants.DEFAULT_ACID_BOTTLE_PACKET_RANGE);
        }
    }

    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ZPItems.acid_bottle.get();
    }
}