package ru.gltexture.zpm3.modules.blocks.instances.blocks.campfire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlockEntities;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPCampfireBlockEntity;
import ru.gltexture.zpm3.modules.blocks.mixins.ext.ICampfireExt;

public class ZPCampfireBlock extends CampfireBlock implements EntityBlock {
    public ZPCampfireBlock(boolean pSpawnParticles, int pFireDamage, Properties pProperties) {
        super(pSpawnParticles, pFireDamage, pProperties);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new ZPCampfireBlockEntity(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return pState.getValue(LIT) ? createTickerHelper(pBlockEntityType, ZPBlockEntities.campfire_block_entity.get(), ZPCampfireBlockEntity::particleTick) : null;
        } else {
            return pState.getValue(LIT) ? createTickerHelper(pBlockEntityType, ZPBlockEntities.campfire_block_entity.get(), ZPCampfireBlockEntity::cookTick) : createTickerHelper(pBlockEntityType, ZPBlockEntities.campfire_block_entity.get(), ZPCampfireBlockEntity::cooldownTick);
        }
    }

    @SuppressWarnings("all")
    @Override
    public void onPlace(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    public static void activationCheck(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable LivingEntity pPlacer, @NotNull ItemStack pStack) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if (entity instanceof ICampfireExt iCampfireExt && pPlacer instanceof Player player) {
            if (ZPWorldConfig.SKIP_FADING_BLOCKS_PLACED_IN_CREATIVE.getVar() && player.isCreative()) {
                iCampfireExt.setActive(false);
            }
        }
    }
}
