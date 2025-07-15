package ru.gltexture.zpm3.assets.player.events.server;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.events.ZPEvent;
import ru.gltexture.zpm3.engine.instances.blocks.IHotLiquid;

public class ZPPlayerFillBucketEvent implements ZPEvent<FillBucketEvent> {
    @Override
    public void exec(@NotNull FillBucketEvent event) {
        if (event.getEmptyBucket().getItem().equals(Items.BUCKET)) {
            if (event.getLevel().isClientSide()) {
                event.getEntity().swing(event.getEntity().getUsedItemHand());
                event.setCanceled(true);
                return;
            }
            if (event.getTarget() == null) {
                return;
            }
            BlockPos pos = BlockPos.containing(event.getTarget().getLocation());
            Level level = event.getLevel();
            Player player = event.getEntity();

            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof LiquidBlock liquidBlock) {
                if (!liquidBlock.getFluidState(state).isSource()) {
                    return;
                }
                if (state.getBlock() instanceof IHotLiquid hotLiquid) {
                    if (ZPRandom.getRandom().nextFloat() > hotLiquid.bucketFillingChance()) {
                        player.getCooldowns().addCooldown(Items.BUCKET, 10);
                        player.getMainHandItem().shrink(1);
                        player.level().playSound(null, player.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5F, 0.885F);
                        player.level().playSound(null, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                        player.swing(InteractionHand.MAIN_HAND);
                        this.spawnItemParticles(player);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    private void spawnItemParticles(@NotNull Entity entity) {
        for (int i = 0; i < 16; ++i) {
            Vec3 vec3 = new Vec3(((double) ZPRandom.getRandom().nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3 = vec3.xRot(-entity.getXRot() * ((float) Math.PI / 180F));
            vec3 = vec3.yRot(-entity.getYRot() * ((float) Math.PI / 180F));
            double d0 = (double) (-ZPRandom.getRandom().nextFloat()) * 0.6D - 0.3D;
            Vec3 vec31 = new Vec3(((double) ZPRandom.getRandom().nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
            vec31 = vec31.xRot(-entity.getXRot() * ((float) Math.PI / 180F));
            vec31 = vec31.yRot(-entity.getYRot() * ((float) Math.PI / 180F));
            vec31 = vec31.add(entity.getX(), entity.getEyeY(), entity.getZ());
            ((ServerLevel) entity.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.BUCKET)), vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D, vec3.z, 0.0D);
            ((ServerLevel) entity.level()).sendParticles(ParticleTypes.SMOKE, vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D, vec3.z, 0.0D);
        }
    }

    @Override
    public @NotNull Class<FillBucketEvent> getEventType() {
        return FillBucketEvent.class;
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.SERVER;
    }

    @Override
    public Mod.EventBusSubscriber.@NotNull Bus getBus() {
        return Mod.EventBusSubscriber.Bus.MOD;
    }
}
