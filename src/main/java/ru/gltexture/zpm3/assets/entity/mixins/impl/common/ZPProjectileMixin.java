package ru.gltexture.zpm3.assets.entity.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.gltexture.zpm3.engine.instances.blocks.ZPTorchBlock;
import ru.gltexture.zpm3.engine.instances.blocks.ZPWallTorchBlock;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

import static net.minecraft.world.level.BlockGetter.traverseBlocks;

@Mixin(ThrowableProjectile.class)
public abstract class ZPProjectileMixin {
    @Redirect(method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/projectile/ProjectileUtil;getHitResultOnMoveVector(Lnet/minecraft/world/entity/Entity;Ljava/util/function/Predicate;)Lnet/minecraft/world/phys/HitResult;"
        )
    )
    private HitResult redirectHitResult(Entity projectile, Predicate<Entity> predicate) {
        if (projectile instanceof Snowball snowball) {
            return getHitResultOnMoveVector(snowball, predicate);
        }
        return ProjectileUtil.getHitResultOnMoveVector(projectile, predicate);
    }

    private static BlockHitResult clip(@NotNull Level level, ClipContext pContext) {
        return traverseBlocks(pContext.getFrom(), pContext.getTo(), pContext, (p_151359_, p_151360_) -> {
            BlockState blockstate = level.getBlockState(p_151360_);
            FluidState fluidstate = level.getFluidState(p_151360_);
            Vec3 vec3 = p_151359_.getFrom();
            Vec3 vec31 = p_151359_.getTo();
            VoxelShape voxelshape = p_151359_.getBlockShape(blockstate, level, p_151360_);
            if (blockstate.getBlock() instanceof TorchBlock || blockstate.getBlock() instanceof WallTorchBlock || blockstate.getBlock() instanceof ZPTorchBlock || blockstate.getBlock() instanceof ZPWallTorchBlock) {
                voxelshape = blockstate.getOcclusionShape(level, p_151360_);
            }
            BlockHitResult blockhitresult = level.clipWithInteractionOverride(vec3, vec31, p_151360_, voxelshape, blockstate);
            VoxelShape voxelshape1 = p_151359_.getFluidShape(fluidstate, level, p_151360_);
            BlockHitResult blockhitresult1 = voxelshape1.clip(vec3, vec31, p_151360_);
            double d0 = blockhitresult == null ? Double.MAX_VALUE : p_151359_.getFrom().distanceToSqr(blockhitresult.getLocation());
            double d1 = blockhitresult1 == null ? Double.MAX_VALUE : p_151359_.getFrom().distanceToSqr(blockhitresult1.getLocation());
            BlockHitResult blockHitResult = d0 <= d1 ? blockhitresult : blockhitresult1;
            return blockHitResult;
        }, (p_275153_) -> {
            Vec3 vec3 = p_275153_.getFrom().subtract(p_275153_.getTo());
            return BlockHitResult.miss(p_275153_.getTo(), Direction.getNearest(vec3.x, vec3.y, vec3.z), BlockPos.containing(p_275153_.getTo()));
        });
    }

    private static HitResult getHitResultOnMoveVector(Entity pProjectile, Predicate<Entity> pFilter) {
        Vec3 vec3 = pProjectile.getDeltaMovement();
        Level level = pProjectile.level();
        Vec3 vec31 = pProjectile.position();
        return getHitResult(vec31, pProjectile, pFilter, vec3, level);
    }

    private static HitResult getHitResultOnViewVector(Entity pProjectile, Predicate<Entity> pFilter, double pScale) {
        Vec3 vec3 = pProjectile.getViewVector(0.0F).scale(pScale);
        Level level = pProjectile.level();
        Vec3 vec31 = pProjectile.getEyePosition();
        return getHitResult(vec31, pProjectile, pFilter, vec3, level);
    }

    private static HitResult getHitResult(Vec3 pStartVec, Entity pProjectile, Predicate<Entity> pFilter, Vec3 pEndVecOffset, Level pLevel) {
        Vec3 vec3 = pStartVec.add(pEndVecOffset);
        HitResult hitresult = clip(pLevel, new ClipContext(pStartVec, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pProjectile));
        if (hitresult.getType() != HitResult.Type.MISS) {
            vec3 = hitresult.getLocation();
        }

        HitResult hitresult1 = getEntityHitResult(pLevel, pProjectile, pStartVec, vec3, pProjectile.getBoundingBox().expandTowards(pEndVecOffset).inflate((double)1.0F), pFilter);
        if (hitresult1 != null) {
            hitresult = hitresult1;
        }

        return hitresult;
    }

    @Nullable
    private static EntityHitResult getEntityHitResult(Level pLevel, Entity pProjectile, Vec3 pStartVec, Vec3 pEndVec, AABB pBoundingBox, Predicate<Entity> pFilter) {
        return getEntityHitResult(pLevel, pProjectile, pStartVec, pEndVec, pBoundingBox, pFilter, 0.3F);
    }

    @Nullable
    private static EntityHitResult getEntityHitResult(Level pLevel, Entity pProjectile, Vec3 pStartVec, Vec3 pEndVec, AABB pBoundingBox, Predicate<Entity> pFilter, float pInflationAmount) {
        double d0 = Double.MAX_VALUE;
        Entity entity = null;

        for(Entity entity1 : pLevel.getEntities(pProjectile, pBoundingBox, pFilter)) {
            AABB aabb = entity1.getBoundingBox().inflate((double)pInflationAmount);
            Optional<Vec3> optional = aabb.clip(pStartVec, pEndVec);
            if (optional.isPresent()) {
                double d1 = pStartVec.distanceToSqr((Vec3)optional.get());
                if (d1 < d0) {
                    entity = entity1;
                    d0 = d1;
                }
            }
        }

        return entity == null ? null : new EntityHitResult(entity);
    }
}
