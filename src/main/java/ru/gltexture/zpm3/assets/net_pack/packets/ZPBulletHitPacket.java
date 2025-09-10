package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.fx.init.ZPParticles;
import ru.gltexture.zpm3.assets.fx.particles.options.ColoredSmokeOptions;
import ru.gltexture.zpm3.assets.guns.rendering.tracer.ZPBulletTracerManager;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Objects;

public class ZPBulletHitPacket implements ZPNetwork.ZPPacket {
    public final static int MISS = 0x01;
    public final static int BLOCK = 0x02;
    public final static int ENTITY = 0x03;
    private final int blockX;
    private final int blockY;
    private final int blockZ;

    private final float vectorFromX;
    private final float vectorFromY;
    private final float vectorFromZ;

    private final float hitX;
    private final float hitY;
    private final float hitZ;

    private final int hitType;

    public ZPBulletHitPacket(int blockX, int blockY, int blockZ, float vectorFromX, float vectorFromY, float vectorFromZ, float hitX, float hitY, float hitZ, int hitType) {
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.vectorFromX = vectorFromX;
        this.vectorFromY = vectorFromY;
        this.vectorFromZ = vectorFromZ;
        this.hitX = hitX;
        this.hitY = hitY;
        this.hitZ = hitZ;
        this.hitType = hitType;
    }

    public ZPBulletHitPacket(FriendlyByteBuf buf) {
        this.blockX = buf.readInt();
        this.blockY = buf.readInt();
        this.blockZ = buf.readInt();
        this.vectorFromX = buf.readFloat();
        this.vectorFromY = buf.readFloat();
        this.vectorFromZ = buf.readFloat();
        this.hitX = buf.readFloat();
        this.hitY = buf.readFloat();
        this.hitZ = buf.readFloat();
        this.hitType = buf.readVarInt();
    }

    public static Encoder<ZPBulletHitPacket> encoder() {
        return (packet, buf) -> {
            buf.writeInt(packet.blockX);
            buf.writeInt(packet.blockY);
            buf.writeInt(packet.blockZ);
            buf.writeFloat(packet.vectorFromX);
            buf.writeFloat(packet.vectorFromY);
            buf.writeFloat(packet.vectorFromZ);
            buf.writeFloat(packet.hitX);
            buf.writeFloat(packet.hitY);
            buf.writeFloat(packet.hitZ);
            buf.writeVarInt(packet.hitType);
        };
    }

    public static Decoder<ZPBulletHitPacket> decoder() {
        return ZPBulletHitPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {

    }

    @Override
    public void onClient(@NotNull Player localPlayer, @NotNull ClientLevel clientLevel) {
        ZPUtility.client().ifClientLevelValid(() -> {
            if (this.hitType == ZPBulletHitPacket.BLOCK) {
                float pitch = 1.5f;
                BlockPos hitPos = new BlockPos(this.blockX, this.blockY, this.blockZ);
                BlockState state = clientLevel.getBlockState(hitPos);
                clientLevel.playSound(null, hitPos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0f, pitch);

                for (int i = 0; i < 6; i++) {
                    final Vector3f color = new Vector3f(0.3f).add(ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f)));
                    final int lifetime = 10 + ZPRandom.getRandom().nextInt(6);
                    final Vector3f motion = new Vector3f(this.vectorFromX, this.vectorFromY, this.vectorFromZ);
                    final Vector3f posToSpawn = new Vector3f(this.hitX, this.hitY, this.hitZ);
                    posToSpawn.add(new Vector3f(motion).mul(0.25f));
                    motion.mul(0.025f).add(ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f)));
                    Objects.requireNonNull(Minecraft.getInstance().level).addParticle(new ColoredSmokeOptions(ZPParticles.colored_cloud.get(), color, 0.65f, lifetime), true,
                            posToSpawn.x, posToSpawn.y, posToSpawn.z,
                            motion.x, motion.y, motion.z);
                }

                for (int i = 0; i < 12; i++) {
                    final Vector3f blockCenter = new Vector3f(this.hitX + 0.5f, this.hitY + 0.5f, this.hitZ + 0.5f);
                    final Vector3f posToSpawn = new Vector3f(this.hitX, this.hitY, this.hitZ);
                    final Vector3f motion = new Vector3f(posToSpawn).sub(blockCenter).normalize();

                    motion.mul(0.1f).add(ZPRandom.instance.randomVector3f(0.02f, new Vector3f(0.05f)));

                    Objects.requireNonNull(Minecraft.getInstance().level).addParticle(
                            new BlockParticleOption(ParticleTypes.BLOCK, Minecraft.getInstance().level.getBlockState(new BlockPos(this.blockX, this.blockY, this.blockZ))),
                            posToSpawn.x, posToSpawn.y, posToSpawn.z,
                            motion.x, motion.y, motion.z
                    );
                }
            }
        });
    }
}
