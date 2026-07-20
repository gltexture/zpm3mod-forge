package ru.gltexture.zpm3.modules.melee_throwables_tools.instances.items.admin;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.instances.items.ZPItem;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.IFadingBlockEntity;

public class ZPAdminWrenchFadingBlocks extends ZPItem {
    public ZPAdminWrenchFadingBlocks(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        Player player = context.getPlayer();
        if (player == null || !player.hasPermissions(2)) {
            return InteractionResult.FAIL;
        }
        BlockPos pos = context.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof IFadingBlockEntity fading) {
            boolean active = fading.zpm3forge$isActive();
            fading.setActive(!active);
            blockEntity.setChanged();
            level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), Block.UPDATE_ALL);
            player.displayClientMessage(Component.literal("Fading: " + (!active ? "Enabled" : "Disabled")), true);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}