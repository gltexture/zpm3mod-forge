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

package ru.gltexture.zpm3.modules.entity.mixins.impl.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPLivingEntityExt;
import ru.gltexture.zpm3.modules.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.modules.mob_effects.utils.ZPEffectUtils;
import ru.gltexture.zpm3.modules.net_pack.ZPNetPackModule;
import ru.gltexture.zpm3.modules.net_pack.data.vars.ZPNetDataInt;

@Mixin(LivingEntity.class)
public abstract class ZPEntityLivingExtendingMixin implements IZPLivingEntityExt {
   //@Unique
   //private static final EntityDataAccessor<Integer> ZP_RADIATION = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
   //@Unique private static final EntityDataAccessor<Integer> INTOXICATION_LEVEL = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
    

 //  @Inject(method = "defineSynchedData", at = @At("TAIL"))
 //  private void zp$defineSynchedData(CallbackInfo ci) {
 //      LivingEntity self = (LivingEntity)(Object)this;
 //      self.getEntityData().define(ZP_RADIATION, 0);
 //      self.getEntityData().define(INTOXICATION_LEVEL, 0);
 //  }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void zp$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;

        tag.putInt("zp_radiation_level", this.zpm3forge$getRadiationLevel());
        tag.putInt("zp_toxic_level", this.zpm3forge$getIntoxicationLevel());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void zp$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;

        if (tag.contains("zp_radiation_level")) {
            this.zpm3forge$setRadiationLevel(tag.getInt("zp_radiation_level"));
        }

        if (tag.contains("zp_toxic_level")) {
            this.zpm3forge$setIntoxicationLevel(tag.getInt("zp_toxic_level"));
        }
    }

    @Override
    public int zpm3forge$getIntoxicationLevel() {
        final Entity entity = (Entity) (Object) this;
        return ZombiePlague3.net(!entity.level().isClientSide()).getNetEntDataSyncer().getVarOfDefault(((Entity) (Object) this), ZPNetPackModule.INTOXICATION).getValue();
    }

    @Override
    public void zpm3forge$setIntoxicationLevelForce(int intoxicationLevel) {
        final Entity entity = (Entity) (Object) this;
        ZombiePlague3.net(!entity.level().isClientSide()).getNetEntDataSyncer().setVar(((Entity) (Object) this), ZPNetPackModule.INTOXICATION, new ZPNetDataInt(Math.min(intoxicationLevel, 1024)));
    }

    @Override
    public void zpm3forge$setIntoxicationLevel(int intoxicationLevel) {
        if (ZPEffectUtils.isImmune((LivingEntity) (Object) this)) {
            return;
        }
        final Entity entity = (Entity) (Object) this;
        ZombiePlague3.net(!entity.level().isClientSide()).getNetEntDataSyncer().setVar(((LivingEntity) (Object) this), ZPNetPackModule.INTOXICATION, new ZPNetDataInt(Math.min(intoxicationLevel, 1024)));
    }

    @Override
    public int zpm3forge$getRadiationLevel() {
        final Entity entity = (Entity) (Object) this;
        return ZombiePlague3.net(!entity.level().isClientSide()).getNetEntDataSyncer().getVarOfDefault(((Entity) (Object) this), ZPNetPackModule.RADIATION).getValue();
    }

    @Override
    public void zpm3forge$setRadiationLevel(int value) {
        final Entity entity = (Entity) (Object) this;
        ZombiePlague3.net(!entity.level().isClientSide()).getNetEntDataSyncer().setVar(((Entity) (Object) this), ZPNetPackModule.RADIATION, new ZPNetDataInt(Math.min(value, 512)));
    }
}
