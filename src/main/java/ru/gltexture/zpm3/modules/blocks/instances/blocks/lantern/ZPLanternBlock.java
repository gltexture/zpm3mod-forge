package ru.gltexture.zpm3.modules.blocks.instances.blocks.lantern;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.blocks.init.ZPBlockEntities;
import ru.gltexture.zpm3.modules.blocks.instances.block_entities.ZPFadingBlockEntity;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.IFadingBlock;
import ru.gltexture.zpm3.modules.blocks.instances.blocks.torch.ZPFadingTorchBlock;

import java.util.function.Supplier;

public class ZPLanternBlock extends LanternBlock {
    public ZPLanternBlock(Properties pProperties) {
        super(pProperties);
    }
}
