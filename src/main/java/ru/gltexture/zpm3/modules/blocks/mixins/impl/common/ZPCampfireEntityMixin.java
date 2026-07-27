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

package ru.gltexture.zpm3.modules.blocks.mixins.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlocks;
import ru.gltexture.zpm3.modules.blocks.init.ZPCampfireBlocks;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.IFadingBlockEntity;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPCampfireBlockEntity;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.modules.blocks.mixins.ext.ICampfireExt;

import java.util.Objects;

@Mixin(CampfireBlockEntity.class)
public abstract class ZPCampfireEntityMixin implements ICampfireExt {
    @Shadow @Final private NonNullList<ItemStack> items;
    @Shadow protected abstract void markUpdated();
    @Shadow @Final private int[] cookingTime;
    @Shadow @Final private int[] cookingProgress;
    @Unique private long zpm3forge$timeLock;
    @Unique private int zpm3forge$fadeCooldown;
    @Unique private boolean zpm3forge$active;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(BlockPos pPos, BlockState pBlockState, CallbackInfo ci) {
        this.zpm3forge$active = true;
    }

    @Inject(method = "placeFood", at = @At("HEAD"), cancellable = true)
    public void placeFood(Entity pEntity, ItemStack pStack, int pCookTime, CallbackInfoReturnable<Boolean> cir) {
        for(int i = 0; i < this.items.size(); ++i) {
            BlockEntity blockEntity = (BlockEntity) (Object) this;
            ItemStack itemstack = this.items.get(i);
            if (itemstack.isEmpty()) {
                this.cookingTime[i] = pCookTime * 2;
                this.cookingProgress[i] = 0;
                this.items.set(i, pStack.split(1));
                Objects.requireNonNull(blockEntity.getLevel()).gameEvent(GameEvent.BLOCK_CHANGE, blockEntity.getBlockPos(), GameEvent.Context.of(pEntity, blockEntity.getBlockState()));
                this.markUpdated();
                cir.setReturnValue(true);
            }
        }
        cir.setReturnValue(false);
    }

    @Inject(method = "cooldownTick", at = @At("TAIL"))
    private static void cooldownTick(Level pLevel, BlockPos pPos, BlockState pState, CampfireBlockEntity pBlockEntity, CallbackInfo ci) {
        if (!pLevel.isClientSide()) {
            if (pBlockEntity instanceof ICampfireExt fadingBlock) {
                fadingBlock.zpm3forge$setTimeLock(-1);
            }
        }
    }

    @Inject(method = "cookTick", at = @At("TAIL"))
    private static void cookTick(Level pLevel, BlockPos pPos, BlockState pState, CampfireBlockEntity pBlockEntity, CallbackInfo ci) {
        ZPCampfireEntityMixin.zpm3forge$fadeTick(pLevel, pPos, pState, pBlockEntity);
    }

    @Unique
    private static void zpm3forge$fadeTick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull CampfireBlockEntity blockEntity) {
        if (blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide()) {
            if (blockEntity instanceof ICampfireExt fadingBlock) {
                //if (state.getBlock() instanceof ICampfireExt fadingBlock) {
                    if (!fadingBlock.zpm3forge$isActive() || !ZPWorldConfig.FADING_CAMPFIRES.getVar()) {
                        return;
                    }
                    if (fadingBlock.zpm3forge$getTimeLock() <= 0) {
                        fadingBlock.zpm3forge$setTimeLock(level.getGameTime() + ZPWorldConfig.CAMPFIRE_FADING_TIME.getVar());
                    }
                    final boolean isFinalStage = !state.getBlock().equals(Blocks.CAMPFIRE);
                    Block turnInto = !isFinalStage ? ZPCampfireBlocks.campfire2.get() : ZPBlocks.ash_layer.get();
                    if (ZPUtility.blocks().isRainingOnBlock(level, pos) && ZPRandom.getRandom().nextFloat() <= 0.1f) {
                        CampfireBlock.dowse(null, level, pos, state);
                        return;
                    }
                    fadingBlock.zpm3forge$incCooldown(1);
                    if (fadingBlock.zpm3forge$fadeCooldown() >= ZPWorldConfig.CAMPFIRE_FADING_TIME.getVar() || (level.getGameTime() >= fadingBlock.zpm3forge$getTimeLock())) {
                        if (!isFinalStage) {
                            BlockState newState = turnInto.defaultBlockState();
                            newState = ZPUtility.blocks().copyProperties(state, newState);
                            level.setBlock(pos, newState, Block.UPDATE_ALL);
                        } else {
                            Containers.dropContents(level, pos, blockEntity.getItems());
                            level.setBlock(pos, turnInto.defaultBlockState(), Block.UPDATE_ALL);
                            if ((Object) level.getBlockEntity(pos) instanceof ZPCampfireEntityMixin fadingBlockEntity) {
                                fadingBlockEntity.setActive(true);
                                fadingBlockEntity.zpm3forge$setTimeLock(fadingBlock.zpm3forge$getTimeLock() + ZPWorldConfig.CAMPFIRE_FADING_TIME.getVar());
                            }
                        }
                        level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                //}
            }
        }
    }

    @Inject(method = "load", at = @At("TAIL"))
    public void load(CompoundTag pTag, CallbackInfo ci) {
        if (pTag.contains("active")) {
            this.zpm3forge$active = pTag.getBoolean("active");
        }
        if (pTag.contains("cooldown")) {
            this.zpm3forge$fadeCooldown = pTag.getInt("cooldown");
        }
        if (pTag.contains("timeLock")) {
            this.zpm3forge$timeLock = pTag.getInt("timeLock");
        }
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    protected void saveAdditional(CompoundTag pTag, CallbackInfo ci) {
        if (!Objects.requireNonNull(((BlockEntity) (Object) this).getLevel()).isClientSide()) {
            pTag.putBoolean("active", this.zpm3forge$active);
            pTag.putInt("cooldown", this.zpm3forge$fadeCooldown);
            pTag.putLong("timeLock", this.zpm3forge$timeLock);
        }
    }

    @Override
    public long zpm3forge$getTimeLock() {
        return this.zpm3forge$timeLock;
    }

    @Override
    public void zpm3forge$setTimeLock(long timeLock) {
        this.zpm3forge$timeLock = timeLock;
    }

    @Override
    public int zpm3forge$fadeCooldown() {
        return this.zpm3forge$fadeCooldown;
    }

    @Override
    public void zpm3forge$incCooldown(int inc) {
        this.zpm3forge$fadeCooldown += inc;
    }

    @Override
    public void zpm3forge$setCooldown(int cooldown) {
        this.zpm3forge$fadeCooldown = cooldown;
    }

    @Override
    public boolean zpm3forge$isActive() {
        return this.zpm3forge$active;
    }

    @Override
    public IFadingBlockEntity setActive(boolean active) {
        this.zpm3forge$active = active;
        return this;
    }
}
