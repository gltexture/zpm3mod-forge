package ru.gltexture.zpm3.assets.net_pack.packets;

import com.mojang.math.Axis;
import net.minecraft.client.Camera;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.gltexture.zpm3.assets.fx.init.ZPParticles;
import ru.gltexture.zpm3.assets.fx.particles.options.ColoredSmokeOptions;
import ru.gltexture.zpm3.assets.guns.rendering.tracer.ZPBulletTracerManager;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Objects;

public class ZPBulletTracePacket implements ZPNetwork.ZPPacket {
    private final float hitX;
    private final float hitY;
    private final float hitZ;
    private final boolean rightHand;

    public ZPBulletTracePacket(float hitX, float hitY, float hitZ, boolean rightHand) {
        this.hitX = hitX;
        this.hitY = hitY;
        this.hitZ = hitZ;
        this.rightHand = rightHand;
    }

    public ZPBulletTracePacket(FriendlyByteBuf buf) {
        this.hitX = buf.readFloat();
        this.hitY = buf.readFloat();
        this.hitZ = buf.readFloat();
        this.rightHand = buf.readBoolean();
    }

    public static Encoder<ZPBulletTracePacket> encoder() {
        return (packet, buf) -> {
            buf.writeFloat(packet.hitX);
            buf.writeFloat(packet.hitY);
            buf.writeFloat(packet.hitZ);
            buf.writeBoolean(packet.rightHand);
        };
    }

    public static Decoder<ZPBulletTracePacket> decoder() {
        return ZPBulletTracePacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {

    }

    @Override
    public void onClient(@NotNull Player localPlayer, @NotNull ClientLevel clientLevel) {
        ZPUtility.client().ifClientLevelValid(() -> {
            Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
            Vec3 camPos = camera.getPosition();
            Vector3f pos = camPos.toVector3f();

            Vector4f pointInSpace = new Vector4f(localPlayer.position().toVector3f().add(0.0f, localPlayer.getEyeHeight() * 0.75f, 0.0f), 1.0f);

            if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                float yaw = -localPlayer.getYRot() * ((float) Math.PI / 180F);
                float pitch = localPlayer.getXRot() * ((float) Math.PI / 180F);
                final Matrix4f space = new Matrix4f().identity();
                space.translate(pos);
                space.rotate(Axis.YP.rotation(yaw));
                space.rotate(Axis.XP.rotation(pitch));
                float interval = 1.0f;
                pointInSpace = new Vector4f(this.rightHand ? -interval : interval, -1.25f, 8.0f, 1.0f);
                space.transform(pointInSpace);
            }

            ZPBulletTracerManager.INSTANCE.addNew(new Vector3f(pointInSpace.x, pointInSpace.y, pointInSpace.z), new Vector3f(this.hitX, this.hitY, this.hitZ));
        });
    }
}
