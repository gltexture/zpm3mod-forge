package ru.gltexture.zpm3.engine.mixins.impl.both;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.assets.common.init.ZPBlocks;
import ru.gltexture.zpm3.engine.mixins.ext.ZPEntityExtTicking;
import ru.gltexture.zpm3.engine.mixins.ext.IZPEntityExt;
import ru.gltexture.zpm3.engine.service.ZPUtility;

@Mixin(Entity.class)
public abstract class EntityMixin implements IZPEntityExt {
    @Shadow public abstract void fillCrashReportCategory(CrashReportCategory pCategory);
    @Shadow public abstract Level level();
    @Shadow public abstract SynchedEntityData getEntityData();

    @Unique private static final EntityDataAccessor<Integer> ACID_LEVEL = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT);
    @Unique private static final EntityDataAccessor<Integer> INTOXICATION_LEVEL = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT);

    @Unique private boolean touchesAcidBlock;
    @Unique private boolean touchesToxicBlock;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructed(EntityType<?> type, Level world, CallbackInfo ci) {
        this.defineZPSyncData();
    }

    @Override
    public void defineZPSyncData() {
        Entity self = (Entity) (Object) this;
        self.getEntityData().define(ACID_LEVEL, 0);
        self.getEntityData().define(INTOXICATION_LEVEL, 0);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickPre(CallbackInfo ci) {
        if (this.level().isClientSide()) {
            ZPEntityExtTicking.clientEntityTickPre((Entity) (Object) this, this);
        } else {
            if (ZPUtility.entity().isCollidingWithBlock((Entity) (Object) this, ZPBlocks.acid_block.get())) {
                this.touchesAcidBlock = true;
            }
            if (ZPUtility.entity().isCollidingWithBlock((Entity) (Object) this, ZPBlocks.toxic_block.get())) {
                this.touchesToxicBlock = true;
            }
            ZPEntityExtTicking.serverEntityTickPre((Entity) (Object) this, this);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickPost(CallbackInfo ci) {
        if (this.level().isClientSide()) {
            ZPEntityExtTicking.clientEntityTickPost((Entity)(Object)this, this);
        } else {
            ZPEntityExtTicking.serverEntityTickPost((Entity) (Object) this, this);
            this.touchesAcidBlock = false;
            this.touchesToxicBlock = false;
        }
    }

    @Inject(method = "saveWithoutId", at = @At("HEAD"))
    private void saveWithoutId(CompoundTag pCompound, CallbackInfoReturnable<CompoundTag> ci) {
        try {
            pCompound.putInt("acidLevel", this.getAcidLevel());
            pCompound.putInt("intoxicationLevel", this.getIntoxicationLevel());
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
            this.setAcidLevel(pCompound.getInt("acidLevel"));
            this.setIntoxicationLevel(pCompound.getInt("intoxicationLevel"));
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.forThrowable(throwable, "Loading entity NBT");
            CrashReportCategory crashreportcategory = crashreport.addCategory("MEntity being loaded");
            this.fillCrashReportCategory(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    @Override
    public boolean touchesAcidBlock() {
        return this.touchesAcidBlock;
    }

    @Override
    public boolean touchesToxicBlock() {
        return this.touchesToxicBlock;
    }

    @Override
    public int getAcidLevel() {
        return this.getEntityData().get(ACID_LEVEL);
    }

    @Override
    public void setAcidLevel(int level) {
        this.getEntityData().set(ACID_LEVEL, level);
    }

    @Override
    public int getIntoxicationLevel() {
        return this.getEntityData().get(INTOXICATION_LEVEL);
    }

    @Override
    public void setIntoxicationLevel(int level) {
        this.getEntityData().set(INTOXICATION_LEVEL, level);
    }
}
