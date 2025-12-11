package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.fx.init.ZPParticles;
import ru.gltexture.zpm3.assets.fx.particles.options.ColoredDefaultParticleOptions;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

import java.util.Objects;

public class ZPBulletBloodFXPacket implements ZPNetwork.ZPPacket {
    private final float hitX;
    private final float hitY;
    private final float hitZ;
    private final float motionX;
    private final float motionY;
    private final float motionZ;
    private final boolean isHeadshot;

    public ZPBulletBloodFXPacket(float hitX, float hitY, float hitZ, float motionX, float motionY, float motionZ, boolean isHeadshot) {
        this.hitX = hitX;
        this.hitY = hitY;
        this.hitZ = hitZ;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.isHeadshot = isHeadshot;
    }

    public ZPBulletBloodFXPacket(FriendlyByteBuf buf) {
        this.hitX = buf.readFloat();
        this.hitY = buf.readFloat();
        this.hitZ = buf.readFloat();
        this.motionX = buf.readFloat();
        this.motionY = buf.readFloat();
        this.motionZ = buf.readFloat();
        this.isHeadshot = buf.readBoolean();
    }

    public static Encoder<ZPBulletBloodFXPacket> encoder() {
        return (packet, buf) -> {
            buf.writeFloat(packet.hitX);
            buf.writeFloat(packet.hitY);
            buf.writeFloat(packet.hitZ);
            buf.writeFloat(packet.motionX);
            buf.writeFloat(packet.motionY);
            buf.writeFloat(packet.motionZ);
            buf.writeBoolean(packet.isHeadshot);
        };
    }

    public static Decoder<ZPBulletBloodFXPacket> decoder() {
        return ZPBulletBloodFXPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ClientLevel clientLevel = Objects.requireNonNull(Minecraft.getInstance().level);
        for (int i = 0; i < 6; i++) {
            Vector3f motion = ZPRandom.instance.randomVector3f(0.4f, new Vector3f(0.8f)).add(this.motionX, this.motionY, this.motionZ).negate().normalize(0.1f);
            Objects.requireNonNull(clientLevel).addParticle(new ColoredDefaultParticleOptions(ZPParticles.blood_fx.get(),
                            new Vector3f(0.5f + ZPRandom.getRandom().nextFloat(0.2f), 0.0f, 0.0f), 0.5f, 8, 0.9f), false,
                    this.hitX, this.hitY, this.hitZ, motion.x(), motion.y(), motion.z());
        }
        if (this.isHeadshot) {
            clientLevel.playLocalSound(this.hitX, this.hitY, this.hitZ, ZPSounds.headshot.get(), SoundSource.MASTER, 1.0f, 1.4f, false);

            for (int i = 0; i < 6; i++) {
                Vector3f motion = ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f));
                Objects.requireNonNull(clientLevel).addParticle(new ColoredDefaultParticleOptions(ZPParticles.blood_fx.get(),
                                new Vector3f(0.5f + ZPRandom.getRandom().nextFloat(0.2f), 0.0f, 0.0f), 1.2f, 10, 0.9f), false,
                        this.hitX, this.hitY, this.hitZ, motion.x(), motion.y() + 0.15f, motion.z());
            }
        }
    }
}