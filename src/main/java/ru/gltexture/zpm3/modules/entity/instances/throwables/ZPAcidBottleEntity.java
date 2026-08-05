/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.entity.instances.throwables;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPCombatConfig;
import ru.gltexture.zpm3.engine.zones.ZPZoneChecks;

import ru.gltexture.zpm3.modules.entity.util.ZPEntityStat;
import ru.gltexture.zpm3.modules.melee_throwables_tools.init.ZPMeleeThrowableToolsItems;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.modules.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.fake.ZPFakePlayer;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPEntityExt;
import ru.gltexture.zpm3.engine.instances.entities.ZPThrowableEntity;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelExt;
import ru.gltexture.zpm3.engine.world.ZPGlobalBlocksDestroyMemory;

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
    protected void initServer() {
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            final Vector3f randomVector = ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f, 0.0f, 0.1f)).add(0.0f, 0.05f, 0.0f);
            ZPCommonClientUtils.emmitAcidParticle(1.2f + ZPRandom.getRandom().nextFloat(0.3f), this.position().toVector3f().add(0.0f, this.getBbHeight() + 0.4f, 0.0f), randomVector);
            if (this.tickCount % 3 == 0) {
                this.level().playLocalSound(this.getOnPos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 0.375f, 1.15f, false);
            }
        }
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            this.level().playLocalSound(this.getOnPos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 0.8f, 0.75f, false);
            this.level().playLocalSound(this.getOnPos(), SoundEvents.GLASS_BREAK, SoundSource.MASTER, 0.8f, 1.0f, false);

            for (int i = 0; i < 40; i++) {
                final Vector3f randomVector = ZPRandom.instance.randomVector3f(0.1f, new Vector3f(0.2f, 0.075f, 0.2f));
                final Vector3f position = this.position().toVector3f();
                position.add(ZPRandom.instance.randomVector3f(0.3f, new Vector3f(0.6f, 0.0f, 0.6f)));
                ZPCommonClientUtils.emmitAcidParticle(2.2f + ZPRandom.getRandom().nextFloat(0.3f), position, randomVector);
            }

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

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if (!entity.level().isClientSide()) {
            ZPEntityStat.ACID.add(entity, ZPCombatConfig.ACID_BOTTLE_DIRECT_HIT_AFFECT_TIME.getVar());
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), ZPCombatConfig.ACID_BOTTLE_DAMAGE.getVar());
            this.affectSplash(entity);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (!this.level().isClientSide) {
            this.affectSplash(null);
        }
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            if (pResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) pResult;
                BlockPos pos = blockHit.getBlockPos();
                if (!this.level().isEmptyBlock(pos)) {
                    if (this.level() instanceof IZPLevelExt ext) {
                        if (ZPFakePlayer.canBreakBlock((ServerLevel) this.level(), pos) && !ZPZoneChecks.INSTANCE.isNoThrowableBlockDamage((ServerLevel) this.level(), pos)) {
                            ext.zpm3forge$getGlobalBlocksDestroyMemory().addNewEntryLongMem(this.level(), pos, (0.25f + ZPRandom.getRandom().nextFloat(0.15f)) * ZPCombatConfig.THROWABLES_BLOCK_BREAK_MULTIPLIER.getVar());
                            ZPGlobalBlocksDestroyMemory.spawnBlockCrackParticles((ServerLevel) this.level(), pos);
                        }
                    }
                }
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    protected void affectSplash(@Nullable Entity hitEntity) {
        if (!this.level().isClientSide()) {
            if (ZPCombatConfig.ACID_BOTTLE_SPLASH_HIT_MAX_AFFECT_TIME.getVar() > 0) {
                final double radius = ZPCombatConfig.ACID_BOTTLE_SPLASH_RADIUS.getVar();
                final int maxTime = ZPCombatConfig.ACID_BOTTLE_SPLASH_HIT_MAX_AFFECT_TIME.getVar();
                final int minTime = 20;
                final float dY = hitEntity == null ? 0.0f : (float) (hitEntity.getBoundingBox().maxY - hitEntity.getBoundingBox().minY);
                Vec3 center = hitEntity != null ? new Vec3(hitEntity.position().toVector3f().add(0.0f, dY, 0.0f)) : this.position();
                Level level = this.level();
                AABB area = new AABB(center, center).inflate(radius);
                for (Entity target : level.getEntitiesOfClass(Entity.class, area, e -> (e != hitEntity && (e instanceof LivingEntity || e instanceof ItemEntity)))) {
                    double dist = target.distanceTo(hitEntity != null ? hitEntity : this);
                    if (dist > radius) {
                        continue;
                    }
                    Vec3 from = center;
                    Vec3 to = target.getBoundingBox().getCenter();
                    BlockHitResult hit = level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        continue;
                    }
                    final double k = 1.0 - Math.pow((dist / radius), Math.E);
                    int affectTime = (int) Math.max(minTime, Math.round(k * maxTime));
                    if (target instanceof ItemEntity) {
                        affectTime *= 4;
                    }
                    ZPEntityStat.ACID.add(target, affectTime);
                }
            }
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ZPMeleeThrowableToolsItems.acid_bottle.get();
    }
}