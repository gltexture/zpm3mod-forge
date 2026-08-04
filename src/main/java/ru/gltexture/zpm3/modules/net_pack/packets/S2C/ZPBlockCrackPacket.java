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

package ru.gltexture.zpm3.modules.net_pack.packets.S2C;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

import java.util.Objects;

public class ZPBlockCrackPacket implements ZPNetwork.ZPPacket {
    private final int num;
    private final int blockX;
    private final int blockY;
    private final int blockZ;

    public ZPBlockCrackPacket(int num, int blockX, int blockY, int blockZ) {
        this.num = num;
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
    }

    public ZPBlockCrackPacket(FriendlyByteBuf buf) {
        this.num = buf.readVarInt();
        this.blockX = buf.readVarInt();
        this.blockY = buf.readVarInt();
        this.blockZ = buf.readVarInt();
    }

    public static Encoder<ZPBlockCrackPacket> encoder() {
        return (packet, buf) -> {
            buf.writeVarInt(packet.num);
            buf.writeVarInt(packet.blockX);
            buf.writeVarInt(packet.blockY);
            buf.writeVarInt(packet.blockZ);
        };
    }

    public static Decoder<ZPBlockCrackPacket> decoder() {
        return ZPBlockCrackPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ClientLevel clientLevel = Objects.requireNonNull(Minecraft.getInstance().level);
        final int nNum = Math.min(num, 5);
        for (Direction dir : Direction.values()) {
            for (int i = 0; i < 1 + (nNum * 2) + ZPRandom.getRandom().nextInt(5); i++) {
                double x = this.blockX + (dir == Direction.WEST ? -0.2 : dir == Direction.EAST ? 1.1 : ZPRandom.getRandom().nextDouble());
                double y = this.blockY + (dir == Direction.DOWN ? -0.1 : dir == Direction.UP ? 1.1 : ZPRandom.getRandom().nextDouble());
                double z = this.blockZ + (dir == Direction.NORTH ? -0.1 : dir == Direction.SOUTH ? 1.1 : ZPRandom.getRandom().nextDouble());

                BlockPos checkPos = new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
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
    }
}