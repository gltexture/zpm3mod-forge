package ru.gltexture.zpm3.assets.guns.processing.bullet;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.*;
import ru.gltexture.zpm3.assets.common.damage.ZPDamageSources;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

import java.lang.Math;

import static net.minecraft.world.level.BlockGetter.traverseBlocks;

public class VirtualBullet {
    private final Entity entity;
    private final Vector3f startPoint;
    private final float inaccuracy;
    private final float damage;
    private final float maxDistance;

    private @Nullable VirtualBulletHitResult virtualBulletHitResult;

    public VirtualBullet(@NotNull Entity entity, @NotNull Vector3f startPoint, float inaccuracy, float damage, float maxDistance) {
        this.entity = entity;
        this.startPoint = startPoint;
        this.inaccuracy = inaccuracy;
        this.damage = damage;
        this.virtualBulletHitResult = null;
        this.maxDistance = maxDistance;
    }

    private boolean breakFragileBlocks(@NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos) {
        return serverLevel.destroyBlock(blockPos, false);
    }

    @SuppressWarnings("all")
    private boolean isBlockFragile(@NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos) {
        float hardness = serverLevel.getBlockState(blockPos).getDestroySpeed(serverLevel, blockPos);
        if (hardness >= 0 && hardness <= 0.5f) {
            return true;
        }
        return false;
    }

    public void simulate() {
        if (!(this.entity.level() instanceof @NotNull ServerLevel level)) {
            return;
        }
        final Vector3f startPoint = new Vector3f(this.startPoint);
        final Vector3f direction = new Vector3f(0.0f, 0.0f, 0.0f);
        final float inaccuracyRad = (float) Math.toRadians(this.inaccuracy);
        final float yawOffset = ZPRandom.instance.randomFloatDuo(inaccuracyRad);
        final float pitchOffset = ZPRandom.instance.randomFloatDuo(inaccuracyRad);
        final float x = (float) (Math.cos(pitchOffset) * Math.sin(yawOffset));
        final float y = (float) Math.sin(pitchOffset);
        direction.set(x, y, 1.0f).normalize();

        float yaw = -this.entity.getYRot() * ((float) Math.PI / 180F);
        float pitch = this.entity.getXRot() * ((float) Math.PI / 180F);

        final Matrix4f space = new Matrix4f().identity();
        space.rotate(Axis.YP.rotation(yaw));
        space.rotate(Axis.XP.rotation(pitch));
        Vector4f auxPoint = new Vector4f(direction, 0.0f);
        space.transform(auxPoint);
        direction.set(auxPoint.x, auxPoint.y, auxPoint.z);

        final Vector3f potentialEndPoint = new Vector3f(startPoint).add(direction.mul(Math.abs(this.maxDistance)));

        VirtualBulletHitResult hitResult = null;
        Vector3f localStart = new Vector3f(startPoint);
        Vector3f localEnd = new Vector3f(potentialEndPoint);
        for (int i = 0; i < ZPConstants.MAX_BULLET_BLOCK_HITS; i++) {
            BlockHitResult blockHitResult = this.clip(level, new ClipContext(
                    new Vec3(localStart),
                    new Vec3(localEnd),
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    this.entity
                    ));

            if (blockHitResult.getType().equals(HitResult.Type.MISS)) {
                hitResult = new VirtualBulletHitResult(new Vector3i(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ()), blockHitResult.getLocation().toVector3f(), null, 1.0f, VirtualBulletHitType.MISS);
                break;
            } else {
                if (this.isBlockFragile(level, blockHitResult.getBlockPos()) && this.breakFragileBlocks(level, blockHitResult.getBlockPos())) {
                    localStart = new Vector3f(blockHitResult.getLocation().toVector3f());
                } else {
                    hitResult = new VirtualBulletHitResult(new Vector3i(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ()), blockHitResult.getLocation().toVector3f(), null, 1.0f, VirtualBulletHitType.BLOCK);
                }
            }
        }

        if (hitResult != null) {
            final AABB aabb = this.entity.getBoundingBox().expandTowards(new Vec3(direction.mul(this.maxDistance))).inflate(1.0D);
            EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
                    level,
                    this.entity,
                    new Vec3(startPoint),
                    new Vec3(hitResult.hitPoint()),
                    aabb,
                    (target) -> !target.isSpectator() && target.isPickable() && target != this.entity,
                    0.0f);

            if (entityHitResult != null && entityHitResult.getType() == HitResult.Type.ENTITY) {
                hitResult = new VirtualBulletHitResult(null, entityHitResult.getLocation().toVector3f(), entityHitResult.getEntity(), this.damageEntity(level, entityHitResult.getEntity(), this.entity, this.damage), VirtualBulletHitType.ENTITY);
            }
        }

        this.setVirtualBulletHitResult(hitResult);
    }

    private float damageEntity(@NotNull Level level, @NotNull Entity entityToDamage, @NotNull Entity attacker, float amount) {
        if (entityToDamage.hurt(ZPDamageSources.bullet(attacker), amount)) {
            entityToDamage.invulnerableTime = 0;
            return amount;
        }
        return -1.0f;
    }

    private BlockHitResult clip(@NotNull Level level, ClipContext pContext) {
        return traverseBlocks(pContext.getFrom(), pContext.getTo(), pContext, (p_151359_, p_151360_) -> {
            BlockState blockstate = level.getBlockState(p_151360_);
            FluidState fluidstate = level.getFluidState(p_151360_);
            Vec3 vec3 = p_151359_.getFrom();
            Vec3 vec31 = p_151359_.getTo();
            VoxelShape voxelshape = p_151359_.getBlockShape(blockstate, level, p_151360_);
            BlockHitResult blockhitresult = level.clipWithInteractionOverride(vec3, vec31, p_151360_, voxelshape, blockstate);
            VoxelShape voxelshape1 = p_151359_.getFluidShape(fluidstate, level, p_151360_);
            BlockHitResult blockhitresult1 = voxelshape1.clip(vec3, vec31, p_151360_);
            double d0 = blockhitresult == null ? Double.MAX_VALUE : p_151359_.getFrom().distanceToSqr(blockhitresult.getLocation());
            double d1 = blockhitresult1 == null ? Double.MAX_VALUE : p_151359_.getFrom().distanceToSqr(blockhitresult1.getLocation());

            BlockHitResult blockHitResult = d0 <= d1 ? blockhitresult : blockhitresult1;

            if (blockHitResult != null && level.getBlockState(blockHitResult.getBlockPos()).getBlock() == Blocks.IRON_BARS) {
                if (ZPRandom.getRandom().nextBoolean()) {
                    return null;
                }
            }

            return blockHitResult;
        }, (p_275153_) -> {
            Vec3 vec3 = p_275153_.getFrom().subtract(p_275153_.getTo());
            return BlockHitResult.miss(p_275153_.getTo(), Direction.getNearest(vec3.x, vec3.y, vec3.z), BlockPos.containing(p_275153_.getTo()));
        });
    }

    public @Nullable VirtualBulletHitResult getVirtualBulletHitResult() {
        return this.virtualBulletHitResult;
    }

    public VirtualBullet setVirtualBulletHitResult(@Nullable VirtualBulletHitResult virtualBulletHitResult) {
        this.virtualBulletHitResult = virtualBulletHitResult;
        return this;
    }

    public record VirtualBulletHitResult(@Nullable Vector3i blockPos, @NotNull Vector3f hitPoint, @Nullable Entity damagedEntity, float entityDamagedAmount, @NotNull VirtualBulletHitType bulletHitType) { ; }

    public enum VirtualBulletHitType {
        MISS(0x01),
        BLOCK(0x02),
        ENTITY(0x03);

        private final int flag;

        VirtualBulletHitType(int flag) {
            this.flag = flag;
        }

        public int getFlag() {
            return this.flag;
        }
    }
}
