package ru.gltexture.zpm3.assets.guns.processing.bullet;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.*;
import ru.gltexture.zpm3.assets.commands.zones.ZPZoneChecks;
import ru.gltexture.zpm3.assets.common.damage.ZPDamageSources;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPCommonZombie;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPMinerZombie;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.fake.ZPFakePlayer;
import ru.gltexture.zpm3.engine.mixins.ext.IZPEntityExt;
import ru.gltexture.zpm3.engine.world.ZPGlobalBlocksDestroyMemory;

import java.lang.Math;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static net.minecraft.world.level.BlockGetter.traverseBlocks;

public class VirtualBullet {
    private final Entity entity;
    private final Vector3f startPoint;
    private final float inaccuracy;
    private final float damage;
    private final float maxDistance;

    private @Nullable VirtualBulletHitResult virtualBulletHitResult;
    private static List<String> blockBlackListToBreak;

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
        if (!ZPConstants.CAN_BULLET_BREAK_BLOCK) {
            return false;
        }
        if (!ZPFakePlayer.canBreakBlock((ServerLevel) serverLevel, blockPos)) {
            return false;
        }
        if (ZPZoneChecks.INSTANCE.isNoBulletBlockDmg((ServerLevel) serverLevel, blockPos)) {
            return false;
        }
        BlockState blockState = serverLevel.getBlockState(blockPos);
        {
            if (VirtualBullet.blockBlackListToBreak == null) {
                VirtualBullet.blockBlackListToBreak = Arrays.stream(ZPConstants.BULLET_BLOCK_BREAKING_BLACKLIST.split(";")).toList();
            }
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(blockState.getBlock());
            if (VirtualBullet.blockBlackListToBreak.stream().anyMatch(e -> e.equals(id.toString()))) {
                return false;
            }
        }
        if (blockState.getBlock().soundType.equals(SoundType.GLASS)) {
            float hardness = blockState.getDestroySpeed(serverLevel, blockPos);
            if (hardness >= 0 && hardness <= ZPConstants.MAX_BULLET_HIT_BLOCK_HARDNESS) {
                return true;
            }
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
                hitResult = new VirtualBulletHitResult(false, new Vector3i(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ()), blockHitResult.getLocation().toVector3f(), null, 1.0f, VirtualBulletHitType.MISS);
                break;
            } else {
                if (this.isBlockFragile(level, blockHitResult.getBlockPos()) && this.breakFragileBlocks(level, blockHitResult.getBlockPos())) {
                    hitResult = new VirtualBulletHitResult(false, new Vector3i(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ()), blockHitResult.getLocation().toVector3f(), null, 1.0f, VirtualBulletHitType.MISS);
                    localStart = new Vector3f(blockHitResult.getLocation().toVector3f());
                } else {
                    hitResult = new VirtualBulletHitResult(false, new Vector3i(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ()), blockHitResult.getLocation().toVector3f(), null, 1.0f, VirtualBulletHitType.BLOCK);
                }
            }
        }

        if (hitResult != null) {
            final AABB aabb = this.entity.getBoundingBox().expandTowards(new Vec3(direction.mul(this.maxDistance))).inflate(1.0D);
            EntityHitResult entityHitResult = null;
            if (this.entity instanceof ServerPlayer serverPlayer) {
                entityHitResult = VirtualBullet.getEntityHitResult(
                        serverPlayer,
                        new Vec3(startPoint),
                        new Vec3(hitResult.hitPoint()),
                        aabb,
                        (target) -> !target.isSpectator() && target.isPickable() && target != this.entity,
                        this.maxDistance * this.maxDistance, 0.0f);
            } else {
                entityHitResult = ProjectileUtil.getEntityHitResult(
                        level,
                        this.entity,
                        new Vec3(startPoint),
                        new Vec3(hitResult.hitPoint()),
                        aabb,
                        (target) -> !target.isSpectator() && target.isPickable() && target != this.entity,
                        0.0f);
            }

            if (entityHitResult != null && entityHitResult.getType() == HitResult.Type.ENTITY) {
                final boolean isHeadshot = VirtualBullet.isHeadshot(entityHitResult.getEntity(), entityHitResult.getLocation());
                hitResult = new VirtualBulletHitResult(isHeadshot, null, entityHitResult.getLocation().toVector3f(), entityHitResult.getEntity(), this.damageEntity(isHeadshot, new Vec3(hitResult.hitPoint), level, entityHitResult.getEntity(), this.entity, this.damage), VirtualBulletHitType.ENTITY);
            }
        }

        this.setVirtualBulletHitResult(hitResult);
    }

    public static @Nullable EntityHitResult getEntityHitResult(@NotNull ServerPlayer serverPlayer, Vec3 pStartVec, Vec3 pEndVec, AABB pBoundingBox, Predicate<Entity> pFilter, double pDistance, float pInflationAmount) {
        Level level = serverPlayer.level();
        double d0 = pDistance;
        Entity entity = null;
        Vec3 vec3 = null;

        for (Entity entity1 : level.getEntities(serverPlayer, pBoundingBox, pFilter)) {
            if (!entity1.isAlive()) {
                continue;
            }
            AABB aabb = ((IZPEntityExt) entity1).getAABBWithLagCompensation(entity1, serverPlayer).inflate(pInflationAmount);
            Optional<Vec3> optional = aabb.clip(pStartVec, pEndVec);
            if (aabb.contains(pStartVec)) {
                if (d0 >= 0.0D) {
                    entity = entity1;
                    vec3 = optional.orElse(pStartVec);
                    d0 = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vec3 vec31 = optional.get();
                double d1 = pStartVec.distanceToSqr(vec31);
                if (d1 < d0 || d0 == 0.0D) {
                    if (entity1.getRootVehicle() == serverPlayer.getRootVehicle() && !entity1.canRiderInteract()) {
                        if (d0 == 0.0D) {
                            entity = entity1;
                            vec3 = vec31;
                        }
                    } else {
                        entity = entity1;
                        vec3 = vec31;
                        d0 = d1;
                    }
                }
            }
        }

        return entity == null ? null : new EntityHitResult(entity, vec3);
    }

    public static boolean isHeadshot(Entity entity, Vec3 hitPoint) {
        if (ZPConstants.BULLET_HEADSHOT_BONUS_DAMAGE <= 0) {
            return false;
        }
        if (!(entity instanceof Player) && !(entity instanceof Villager) && !(entity instanceof ZPCommonZombie) && !(entity instanceof ZPMinerZombie)) {
            return false;
        }
        AABB box = entity.getBoundingBox();
        double baseY = box.minY;
        double height = box.getYsize();
        double headThreshold = baseY + height * 0.75;
        return hitPoint.y >= headThreshold;
    }

    private float damageEntity(boolean isHeadshot, @NotNull Vec3 hitPoint, @NotNull Level level, @NotNull Entity entityToDamage, @NotNull Entity attacker, float amount) {
        float damage = amount * this.getBulletReductionMultiplier(entityToDamage);
        if (isHeadshot) {
            damage += ZPConstants.BULLET_HEADSHOT_BONUS_DAMAGE;
        }
        if (!(entityToDamage instanceof ZPAbstractZombie)) {
            if (entityToDamage instanceof Animal) {
                damage *= 1.5f;
            } else if (entityToDamage instanceof Mob) {
                damage *= 3.0f;
            }
        }
        if (entityToDamage.hurt(ZPDamageSources.bullet((ServerLevel) level, attacker), damage)) {
            entityToDamage.invulnerableTime = 0;
            return amount;
        }
        return -1.0f;
    }

    private float getBulletReductionMultiplier(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            int reduction = 0;
            for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
                ItemStack stack = livingEntity.getItemBySlot(slot);
                if (!stack.isEmpty() && stack.getItem() instanceof ArmorItem armor) {
                    int materialReduction = armor.getDefense();
                    reduction += materialReduction;
                }
            }
            return Math.max(0.0f, 1.0f - ((reduction / 20.0f) * ZPConstants.ARMOR_BULLET_DAMAGE_REDUCTION_MULTIPLIER));
        }
        return 1.0f;
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

            if (blockHitResult != null) {
                if (level.getBlockState(blockHitResult.getBlockPos()).getBlock() instanceof LeavesBlock) {
                    return null;
                }

                if (level.getBlockState(blockHitResult.getBlockPos()).getBlock() == Blocks.IRON_BARS) {
                    if (ZPRandom.getRandom().nextBoolean()) {
                        return null;
                    }
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

    public record VirtualBulletHitResult(boolean wasHeadshot, @Nullable Vector3i blockPos, @NotNull Vector3f hitPoint, @Nullable Entity damagedEntity, float entityDamagedAmount, @NotNull VirtualBulletHitType bulletHitType) { ; }

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
