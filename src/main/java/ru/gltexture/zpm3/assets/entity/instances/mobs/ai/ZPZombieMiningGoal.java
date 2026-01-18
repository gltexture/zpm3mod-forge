package ru.gltexture.zpm3.assets.entity.instances.mobs.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.commands.zones.ZPZoneChecks;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPEntityAttributes;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.engine.fake.ZPFakePlayer;
import ru.gltexture.zpm3.engine.instances.blocks.ZPTorchBlock;
import ru.gltexture.zpm3.engine.mixins.ext.IZPLevelExt;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPBlockCrack;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.*;
import java.util.function.Predicate;

public class ZPZombieMiningGoal extends Goal {
    public static final Map<BlockPos, Pair<Integer, ResourceKey<Level>>> affectedBlocks = new HashMap<>();

    protected final ZPAbstractZombie mob;
    @Nullable
    protected LivingEntity targetMob;

    private float miningTicks;
    private int updateMineDirTicks;
    private int maxUpdateMineDirTicks;
    private ZMMineDir mineDir;

    private final Predicate<Pair<BlockPos, ZPAbstractZombie>>[] mineConditions;
    private int ticksBeforeCanMine;

    private final Predicate<BlockState> predicateToFilterBlocks;

    private static List<String> blockBlackListToBreak;

    @SafeVarargs
    public ZPZombieMiningGoal(ZPAbstractZombie pMob, @Nullable Predicate<BlockState> predicateToFilterBlocks, Predicate<Pair<BlockPos, ZPAbstractZombie>>... mineConditions) {
        this.mob = pMob;
        this.mineDir = null;
        this.mineConditions = mineConditions;
        this.predicateToFilterBlocks = predicateToFilterBlocks;
        this.ticksBeforeCanMine = ZPRandom.getRandom().nextInt(1200);
        this.maxUpdateMineDirTicks = 10;
    }

    @SuppressWarnings("all")
    public static Predicate<BlockState> DEFAULT_BLOCKS_FILTER() {
        if (ZPZombieMiningGoal.blockBlackListToBreak == null) {
            ZPZombieMiningGoal.blockBlackListToBreak = Arrays.stream(ZPConstants.ZOMBIE_BLOCK_MINING_BLACKLIST.split(";")).toList();
        }
        return state -> {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
            if (ZPZombieMiningGoal.blockBlackListToBreak.stream().anyMatch(e -> e.equals(id.toString()))) {
                return false;
            }
            return true;
        };
    }

    public static Predicate<Pair<BlockPos, ZPAbstractZombie>> DEFAULT_MINING_CONDITION(final float maxBlockStrength) {
        return (p) -> {
            if (p.first().getY() > ZPConstants.ZOMBIE_MAX_MINING_HEIGHT) {
                return false;
            }
            if (p.first().getY() < ZPConstants.ZOMBIE_MIN_MINING_HEIGHT) {
                return false;
            }
            float f = maxBlockStrength;
            BlockState blockState = p.second().level().getBlockState(p.first());
            ItemStack held = p.second().getMainHandItem();
            float hardness = blockState.getDestroySpeed(p.second().level(), p.first());
            float speed = 1.0f;
            if (!held.isEmpty()) {
                float efficiency = held.getDestroySpeed(blockState);
                if (efficiency > 1.0f && held.isCorrectToolForDrops(blockState)) {
                    speed = efficiency * 5.0f;
                }
            }
            if (p.second().level().getDifficulty() == Difficulty.HARD) {
                f *= 10.0f;
            }
            return hardness >= 0 && hardness <= f * speed;
        };
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        return this.mob.getTarget() != null && this.mob.getTarget().isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.getTarget() != null && this.mob.getTarget().isAlive() && this.mob.attackTicks <= 0 && this.mob.isAlive() && this.mob.hurtTime <= 0;
    }

    protected float getMiningSpeed(@NotNull ZPAbstractZombie abstractZombie) {
        return (float) abstractZombie.getAttributes().getBaseValue(ZPEntityAttributes.zm_mining_speed.get());
    }

    protected float blockHardnessMiningTime(@NotNull BlockState blockState, @NotNull BlockPos pos, @NotNull ZPAbstractZombie zombie) {
        float blockHardness = blockState.getDestroySpeed(zombie.level(), pos) * 2.0f;
        if (blockHardness < 0.0f) {
            return Float.MAX_VALUE;
        }
        return blockHardness;
    }

    protected float getMiningSpeedWithBonus(@NotNull BlockState blockState, @NotNull BlockPos pos, @NotNull ZPAbstractZombie zombie) {
        ItemStack held = zombie.getMainHandItem();
        float bonus = 1.0f;

        if (!held.isEmpty()) {
            float efficiency = held.getDestroySpeed(blockState);
            if (efficiency > 1.0f && held.isCorrectToolForDrops(blockState)) {
                bonus += (efficiency - 1.0f) * 0.15f;
            }
        }

        return this.getMiningSpeed(zombie) * bonus;
    }

    private boolean ignoreCollision(BlockState blockState) {
        return blockState.getBlock() instanceof TorchBlock || blockState.getBlock() instanceof ZPTorchBlock || blockState.getBlock() instanceof CropBlock;
    }

    @Override
    public void tick() {
        if (this.mob.level().getDifficulty() == Difficulty.EASY || this.mob.getTarget() == null || this.ticksBeforeCanMine-- > 0) {
            return;
        }
        if (this.updateMineDirTicks-- <= 0) {
            this.mineDir = ZMMineDir.build(this.mob, ZPConstants.ZOMBIE_HANDS_LENGTH_FOR_MINING);
            this.resetMaxTimeForResetMineUpdateDirTicks();
            this.resetMineDirTicks();
        }
        if (this.mineDir == null) {
            return;
        }
        final Vec3 start = new Vec3(this.mineDir.center());
        @Nullable BlockPos blockToMine = null;
        for (Vector3f vec : this.mineDir.ends()) {
            BlockHitResult blockHitResult = this.rayCastBlock(this.mob.level(), start, new Vec3(vec));
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                for (Predicate<Pair<BlockPos, ZPAbstractZombie>> predicate : this.mineConditions) {
                    if (!predicate.test(Pair.of(blockHitResult.getBlockPos(), this.mob))) {
                        continue;
                    }
                    blockToMine = blockHitResult.getBlockPos();
                }
            }
        }

        if (blockToMine == null) {
            Level level = this.mob.level();
            BlockPos feet = this.mob.blockPosition();
            BlockPos body = feet.above();
            BlockPos[] candidates = new BlockPos[] {
                    feet,
                    body
            };
            for (BlockPos pos : candidates) {
                final BlockState state = level.getBlockState(pos);
                if (state.isAir()) {
                    continue;
                }
                if ((state.getBlock() instanceof FenceBlock || state.getBlock() instanceof FenceGateBlock) || this.ignoreCollision(state)) {
                    boolean flag = true;
                    for (Predicate<Pair<BlockPos, ZPAbstractZombie>> predicate : this.mineConditions) {
                        if (!predicate.test(Pair.of(pos, this.mob))) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        blockToMine = pos;
                        break;
                    }
                }
            }
        }

        if (blockToMine != null) {
            if (!ZPFakePlayer.canBreakBlock((ServerLevel) this.mob.level(), blockToMine)) {
                return;
            }
            if (ZPZoneChecks.INSTANCE.isNoZombieMining((ServerLevel) this.mob.level(), blockToMine)) {
                return;
            }

            final Level level = this.mob.level();
            final BlockState state = level.getBlockState(blockToMine);

            if (this.predicateToFilterBlocks != null && !this.predicateToFilterBlocks.test(state)) {
                return;
            }

            if (!this.ignoreCollision(state) && state.getCollisionShape(level, blockToMine).isEmpty()) {
                return;
            }

            if (this.mob.tickCount % 5 == 0) {
                this.mob.swing(InteractionHand.MAIN_HAND);
            }

            ZPZombieMiningGoal.affectedBlocks.merge(blockToMine.immutable(), Pair.of(1, level.dimension()),
                    (oldVal, newVal) -> Pair.of(oldVal.first() + 1, oldVal.second())
            );

            if (ZPConstants.USE_ZOMBIE_MINING_SHARED_GLOBAL_MEM) {
                if (this.mob.level() instanceof IZPLevelExt ext) {
                    ext.getGlobalBlocksDestroyMemory().addNewEntryShortMem(this.mob.level(), blockToMine, this.getMiningSpeedWithBonus(state, blockToMine, this.mob));
                }
            } else {
                this.miningTicks += this.getMiningSpeedWithBonus(state, blockToMine, this.mob);
                if (this.miningTicks >= this.blockHardnessMiningTime(state, blockToMine, this.mob)) {
                    this.mob.level().destroyBlock(blockToMine, false);
                    this.ticksBeforeCanMine = 20;
                    this.miningTicks = 0;
                }
            }
        }
    }

    protected void resetMineDirTicks() {
        this.updateMineDirTicks = this.maxUpdateMineDirTicks;
    }

    public BlockHitResult rayCastBlock(Level world, Vec3 start, Vec3 end) {
        ClipContext context = new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, null);
        return world.clip(context);
    }

    @Override
    public void start() {
        this.updateMineDirTicks = 0;
        this.resetMaxTimeForResetMineUpdateDirTicks();
    }

    protected void resetMaxTimeForResetMineUpdateDirTicks() {
        int minersNear = this.mob.level().getEntitiesOfClass(ZPAbstractZombie.class, this.mob.getBoundingBox().inflate(3.0f)).size();
        this.maxUpdateMineDirTicks = 10 + Math.min(minersNear + ZPRandom.getRandom().nextInt(2), 10);
    }

    @Override
    public void stop() {
        this.mineDir = null;
        this.ticksBeforeCanMine = 40 + ZPRandom.getRandom().nextInt(41);
        this.miningTicks = 0;
        this.targetMob = null;
    }

    private record ZMMineDir(Vector3f center, Collection<Vector3f> ends) {
        public static @Nullable ZMMineDir build(@NotNull ZPAbstractZombie abstractZombie, float dirLength) {
            Entity target = abstractZombie.getTarget();
            if (target != null) {
                final Vector3f targetPos = target.position().toVector3f();
                final float yHalf = (float) ((abstractZombie.getBoundingBox().maxY - abstractZombie.getBoundingBox().minY) / 2.0f);
                final float yQuat = yHalf * 0.5f;
                final Vector3f mobCenter = abstractZombie.position().toVector3f().add(0.0f, yHalf, 0.0f);
                final float dy = (float) (targetPos.y - abstractZombie.position().y);
                final Vector3f rayCenter = mobCenter.add(0.0f, Mth.clamp(dy * 0.3f, -yQuat, yQuat), 0.0f);
                final Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);

                Vector3f lookAt = (new Vector3f(targetPos).sub(rayCenter)).normalize();
                Vector3f right = new Vector3f(lookAt).cross(up).normalize();

                final Vector3f angle1 = new Vector3f(lookAt);
                final Vector3f angle2 = new Vector3f(lookAt).add(new Vector3f(right).mul(0.5f));
                final Vector3f angle3 = new Vector3f(lookAt).add(new Vector3f(right).mul(-0.5f));
                final Vector3f angle4 = new Vector3f(lookAt).add(new Vector3f(up).mul(0.5f));
                final Vector3f angle5 = new Vector3f(lookAt).add(new Vector3f(up).mul(-0.5f));
                final Vector3f angle6 = new Vector3f(lookAt).mul(1.0f, 0.0f, 1.0f);
                Vector3f vector6N = angle6.normalize();

                Set<Vector3f> vector3fs = new TreeSet<>(Comparator.comparingDouble(e -> e.distance(targetPos)));
                vector3fs.add(new Vector3f(rayCenter).add(angle1.normalize().mul(dirLength)));
                vector3fs.add(new Vector3f(rayCenter).add(angle2.normalize().mul(dirLength)));
                vector3fs.add(new Vector3f(rayCenter).add(angle3.normalize().mul(dirLength)));
                vector3fs.add(new Vector3f(rayCenter).add(angle4.normalize().mul(dirLength)));

                if ((abstractZombie.getNavigation().getPath() != null && abstractZombie.getNavigation().getPath().isDone()) || dy < 0.0f) {
                    if (abstractZombie.onGround() && !abstractZombie.isSwimming() && !abstractZombie.onClimbable()) {
                        vector3fs.add(new Vector3f(rayCenter).add(angle5.normalize().mul(dirLength)));
                    }
                }
                if (Math.abs(vector6N.dot(lookAt)) < 0.7f) {
                    vector3fs.add(new Vector3f(rayCenter).add(vector6N.mul(dirLength)));
                }
                return new ZMMineDir(rayCenter, vector3fs);
            }
            return null;
        }
    }
}