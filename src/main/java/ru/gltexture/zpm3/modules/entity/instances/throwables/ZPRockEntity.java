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
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPCombatConfig;
import ru.gltexture.zpm3.modules.commands.zones.ZPZoneChecks;

import ru.gltexture.zpm3.modules.melee_throwables_tools.init.ZPMeleeThrowableToolsItems;
import ru.gltexture.zpm3.modules.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.fake.ZPFakePlayer;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelExt;
import ru.gltexture.zpm3.engine.client.rendering.util.ZPCommonClientUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.instances.entities.ZPThrowableEntity;
import ru.gltexture.zpm3.engine.world.ZPGlobalBlocksDestroyMemory;

public class ZPRockEntity extends ZPThrowableEntity {
    public ZPRockEntity(EntityType<ZPRockEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ZPRockEntity(EntityType<ZPRockEntity> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public ZPRockEntity(EntityType<ZPRockEntity> pEntityType, LivingEntity pShooter, Level pLevel) {
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
            this.level().playLocalSound(this.getOnPos(), SoundEvents.METAL_BREAK, SoundSource.MASTER, 0.8f, 1.0f, false);
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
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), ZPCombatConfig.ROCK_DAMAGE.getVar());
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
                        if (ZPFakePlayer.canBreakBlock((ServerLevel) this.level(), pos) && !ZPZoneChecks.INSTANCE.isNoThrowableBlockDamage((ServerLevel) this.level(), pos)) {
                            ext.zpm3forge$getGlobalBlocksDestroyMemory().addNewEntryLongMem(this, this.level(), pos, (5.0f + ZPRandom.getRandom().nextFloat(5.0f)) * ZPCombatConfig.THROWABLES_BLOCK_BREAK_MULTIPLIER.getVar());
                            ZPGlobalBlocksDestroyMemory.spawnBlockCrackParticles((ServerLevel) this.level(), pos);
                        }
                    }
                }
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ZPMeleeThrowableToolsItems.rock.get();
    }
}