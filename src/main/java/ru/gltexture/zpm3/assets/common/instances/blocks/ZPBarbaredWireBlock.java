package ru.gltexture.zpm3.assets.common.instances.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.commands.zones.ZPZoneChecks;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPBlockEntities;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPBarbaredWireBlockEntity;
import ru.gltexture.zpm3.assets.common.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.assets.common.instances.blocks.torch.ZPFadingTorchBlock;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.assets.entity.instances.mobs.zombies.ZPAbstractZombie;
import ru.gltexture.zpm3.assets.mob_effects.init.ZPMobEffects;
import ru.gltexture.zpm3.assets.mob_effects.utils.ZPEffectUtils;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;

public class ZPBarbaredWireBlock extends Block implements EntityBlock {
    public ZPBarbaredWireBlock(Properties pProperties) {
        super(pProperties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            entity.makeStuckInBlock(state, new Vec3(0.375D, 0.375D, 0.375D));
        }
        if (level instanceof ServerLevel serverLevel && !ZPZoneChecks.INSTANCE.isBarbaredWiresDisabled(serverLevel, pos)) {
            if (entity instanceof LivingEntity livingEntity) {
                entity.hurt(entity.damageSources().generic(), entity instanceof Player ? 1 : 2);
                if (entity.tickCount % 20 == 0 && ZPRandom.getRandom().nextFloat() <= 0.1f) {
                    if (!ZPConstants.BLEEDING_ONLY_FOR_PLAYERS || entity instanceof Player) {
                        if (!ZPEffectUtils.isBleeding(livingEntity)) {
                            livingEntity.addEffect(new MobEffectInstance(ZPMobEffects.bleeding.get(), 300));
                        }
                    }
                }
                if (entity.tickCount % 20 == 0) {
                    if (level.getBlockEntity(pos) instanceof ZPBarbaredWireBlockEntity blockEntity) {
                        blockEntity.setDamage(blockEntity.getDamage() + 1);
                        if (blockEntity.getDamage() >= ZPConstants.MAX_BARBARED_WIRE_STRENGTH) {
                            level.playSound(null, pos, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.2F);
                            level.destroyBlock(pos, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ZPBarbaredWireBlockEntity(pPos, pState);
    }
}
