package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.mixins.ext.IZPPlayerMixinExt;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Objects;

public class ZPBlockCrack implements ZPNetwork.ZPPacket {
    private final int blockX;
    private final int blockY;
    private final int blockZ;

    public ZPBlockCrack(int blockX, int blockY, int blockZ) {
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
    }

    public ZPBlockCrack(FriendlyByteBuf buf) {
        this.blockX = buf.readVarInt();
        this.blockY = buf.readVarInt();
        this.blockZ = buf.readVarInt();
    }

    public static Encoder<ZPBlockCrack> encoder() {
        return (packet, buf) -> {
            buf.writeVarInt(packet.blockX);
            buf.writeVarInt(packet.blockY);
            buf.writeVarInt(packet.blockZ);
        };
    }

    public static Decoder<ZPBlockCrack> decoder() {
        return ZPBlockCrack::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClient(@NotNull Player localPlayer, @NotNull ClientLevel clientLevel) {
        ZPUtility.client().ifClientLevelValid(() -> {
            for (Direction dir : Direction.values()) {
                for (int i = 0; i < 5 + ZPRandom.getRandom().nextInt(5); i++) {
                    double x = this.blockX + (dir == Direction.WEST ? -0.1 : dir == Direction.EAST ? 1.1 : ZPRandom.getRandom().nextDouble());
                    double y = this.blockY + (dir == Direction.DOWN ? -0.1 : dir == Direction.UP ? 1.1 : ZPRandom.getRandom().nextDouble());
                    double z = this.blockZ + (dir == Direction.NORTH ? -0.1 : dir == Direction.SOUTH ? 1.1 : ZPRandom.getRandom().nextDouble());

                    BlockPos checkPos = new BlockPos((int) x, (int) y, (int) z);
                    BlockState checkState = localPlayer.level().getBlockState(checkPos);
                    if (checkState.isSolid()) {
                        continue;
                    }

                    double vx = (ZPRandom.getRandom().nextDouble() - 0.5) * 0.05;
                    double vy = (ZPRandom.getRandom().nextDouble() - 0.5) * 0.05;
                    double vz = (ZPRandom.getRandom().nextDouble() - 0.5) * 0.05;

                    clientLevel.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, localPlayer.level().getBlockState(new BlockPos(this.blockX, this.blockY, this.blockZ))), x, y, z, vx, vy, vz);
                }
            }
        });
    }
}