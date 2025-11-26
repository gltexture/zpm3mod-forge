package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.player.client.ZPClientGlobalSettings;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPSendGlobalSettingsToClients implements ZPNetwork.ZPPacket {
    private final boolean isDarknessEnabled;
    private final float darknessFactor;

    public ZPSendGlobalSettingsToClients(boolean isDarknessEnabled, float darknessFactor) {
        this.isDarknessEnabled = isDarknessEnabled;
        this.darknessFactor = darknessFactor;
    }

    public ZPSendGlobalSettingsToClients(FriendlyByteBuf buf) {
        this.isDarknessEnabled = buf.readBoolean();
        this.darknessFactor = buf.readFloat();
    }

    public static Encoder<ZPSendGlobalSettingsToClients> encoder() {
        return (packet, buf) -> {
            buf.writeBoolean(packet.isDarknessEnabled);
            buf.writeFloat(packet.darknessFactor);
        };
    }

    public static Decoder<ZPSendGlobalSettingsToClients> decoder() {
        return ZPSendGlobalSettingsToClients::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
    }

    @Override
    public void onClient(@NotNull Player localPlayer, @NotNull ClientLevel clientLevel) {
        ZPClientGlobalSettings.DARKNESS_ENABLED = this.isDarknessEnabled;
        ZPClientGlobalSettings.DARKNESS_FACTOR = this.darknessFactor;
    }
}