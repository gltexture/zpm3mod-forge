package ru.gltexture.zpm3.modules.entity.mixins.impl.common;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ru.gltexture.zpm3.engine.client.rendering.ZPRenderHelper;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPEntityConfig;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;
import ru.gltexture.zpm3.modules.entity.mixins.ext.IZPEntityExt;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityUtil;

import java.util.ArrayDeque;
import java.util.Deque;

@Mixin(Entity.class)
public abstract class ZPEntityExtendingMixin implements IZPEntityExt {
    @Unique private static final EntityDataAccessor<Integer> ACID_LEVEL = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT);

    @Shadow public abstract void fillCrashReportCategory(CrashReportCategory pCategory);
    @Shadow public abstract Level level();
    @Shadow public abstract SynchedEntityData getEntityData();

    @Shadow public abstract AABB getBoundingBox();

    @Unique private Deque<Snapshot> zpm3forge$aabbDeque = new ArrayDeque<>(20);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructed(EntityType<?> type, Level world, CallbackInfo ci) {
        this.zpm3forge$defineZPSyncData();
    }

    @Override
    public void zpm3forge$defineZPSyncData() {
        Entity self = (Entity) (Object) this;
        self.getEntityData().define(ACID_LEVEL, 0);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickPre(CallbackInfo ci) {
        final Entity entity = (Entity) (Object) this;
        if (this.level().isClientSide()) {
            if (this.zpm3forge$getAcidLevel() > 0) {
                ZPRenderHelper.addAcidParticles(this.zpm3forge$getAcidLevel(), entity);
                if (entity.tickCount % 3 == 0) {
                    entity.level().playLocalSound(entity.getOnPos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 0.375f, 1.0f + ZPRandom.getRandom().nextFloat() * 0.2f, false);
                }
            }
        } else {
            AABB aabb = this.getBoundingBox();
            this.zpm3forge$aabbDeque.addFirst(new Snapshot(System.currentTimeMillis(), aabb));
            if (this.zpm3forge$aabbDeque.size() > ZPEntityConfig.ENTITY_MAX_AABB_MEMORY_ANTILAG.getVar()) {
                this.zpm3forge$aabbDeque.removeLast();
            }

            if (!(entity instanceof LivingEntity)) {
                if (this.zpm3forge$getAcidLevel() > 120) {
                    entity.discard();
                }
            }
            final int acidTickRate = ZPEntityUtil.getEntityAcidAffectionTickRate(entity);
            if (acidTickRate > 0) {
                if (entity.tickCount % acidTickRate == 0) {
                    this.addAcidLevel(1);
                }
            } else if (entity.tickCount % 2 == 0 && this.zpm3forge$getAcidLevel() > 0) {
                this.addAcidLevel(-1);
            }
            if (this.zpm3forge$getAcidLevel() > 120) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 1, false, true));
                }
            }
            if (this.zpm3forge$getAcidLevel() > 0) {
                //if (entity.tickCount % (2 / ZPEntityUtil.getEntityAcidIncMultiplier(entity)) == 0) {
                    ZPEntityUtil.damageEntityAndPossiblyEquipment(entity);
                //}
            }
        }
    }

    @Inject(method = "saveWithoutId", at = @At("HEAD"))
    private void saveWithoutId(CompoundTag pCompound, CallbackInfoReturnable<CompoundTag> ci) {
        try {
            pCompound.putInt("zp_acidLevel", this.zpm3forge$getAcidLevel());
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.forThrowable(throwable, "Saving entity NBT");
            CrashReportCategory crashreportcategory = crashreport.addCategory("MEntity being saved");
            this.fillCrashReportCategory(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    public void load(CompoundTag pCompound, CallbackInfo ci) {
        try {
            this.zpm3forge$setAcidLevel(pCompound.getInt("zp_acidLevel"));
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.forThrowable(throwable, "Loading entity NBT");
            CrashReportCategory crashreportcategory = crashreport.addCategory("MEntity being loaded");
            this.fillCrashReportCategory(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    @Override
    public Deque<Snapshot> zpm3forge$getAabbDeque() {
        return this.zpm3forge$aabbDeque;
    }

    @Override
    public int zpm3forge$getAcidLevel() {
        return this.getEntityData().get(ACID_LEVEL);
    }

    @Override
    public void zpm3forge$setAcidLevel(int level) {
        this.getEntityData().set(ACID_LEVEL, level);
    }
}
