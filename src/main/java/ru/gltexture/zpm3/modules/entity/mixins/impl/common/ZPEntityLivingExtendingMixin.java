package ru.gltexture.zpm3.modules.entity.mixins.impl.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPLivingEntityExt;

@Mixin(LivingEntity.class)
public abstract class ZPEntityLivingExtendingMixin implements IZPLivingEntityExt {
    @Unique private static final EntityDataAccessor<Integer> ZP_RADIATION = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
    @Unique private static final EntityDataAccessor<Integer> INTOXICATION_LEVEL = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
    

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void zp$defineSynchedData(CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;
        self.getEntityData().define(ZP_RADIATION, 0);
        self.getEntityData().define(INTOXICATION_LEVEL, 0);
    }

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
        return ((LivingEntity) (Object) this).getEntityData().get(INTOXICATION_LEVEL);
    }

    @Override
    public void zpm3forge$setIntoxicationLevel(int intoxicationLevel) {
        ((LivingEntity) (Object) this).getEntityData().set(INTOXICATION_LEVEL, intoxicationLevel);
    }

    @Override
    public int zpm3forge$getRadiationLevel() {
        return ((LivingEntity) (Object) this).getEntityData().get(ZP_RADIATION);
    }

    @Override
    public void zpm3forge$setRadiationLevel(int value) {
        ((LivingEntity) (Object) this).getEntityData().set(ZP_RADIATION, value);
    }
}
