package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.player.client.ZPClientGlobalSettings;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

public class ZPSendGlobalSettings_StoC implements ZPNetwork.ZPPacket {
    private final boolean isDarknessEnabled;
    private final float darknessFactor;

    public ZPSendGlobalSettings_StoC(boolean isDarknessEnabled, float darknessFactor) {
        this.isDarknessEnabled = isDarknessEnabled;
        this.darknessFactor = darknessFactor;
    }

    public ZPSendGlobalSettings_StoC(FriendlyByteBuf buf) {
        this.isDarknessEnabled = buf.readBoolean();
        this.darknessFactor = buf.readFloat();
    }

    public static Encoder<ZPSendGlobalSettings_StoC> encoder() {
        return (packet, buf) -> {
            buf.writeBoolean(packet.isDarknessEnabled);
            buf.writeFloat(packet.darknessFactor);
        };
    }

    public static Decoder<ZPSendGlobalSettings_StoC> decoder() {
        return ZPSendGlobalSettings_StoC::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ZPClientGlobalSettings.DARKNESS_ENABLED = this.isDarknessEnabled;
        ZPClientGlobalSettings.DARKNESS_FACTOR = this.darknessFactor;
    }
}