package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.player.client.ZPClientGlobalSettings;
import ru.gltexture.zpm3.engine.mixins.ext.IZPPlayerMixinExt;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

public class ZPSendGlobalSettings_CtoS implements ZPNetwork.ZPPacket {
    private final boolean enabledPickUpOnF;

    public ZPSendGlobalSettings_CtoS(boolean enabledPickUpOnF) {
        this.enabledPickUpOnF = enabledPickUpOnF;
    }

    public ZPSendGlobalSettings_CtoS(FriendlyByteBuf buf) {
        this.enabledPickUpOnF = buf.readBoolean();
    }

    public static Encoder<ZPSendGlobalSettings_CtoS> encoder() {
        return (packet, buf) -> {
            buf.writeBoolean(packet.enabledPickUpOnF);
        };
    }

    public static Decoder<ZPSendGlobalSettings_CtoS> decoder() {
        return ZPSendGlobalSettings_CtoS::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
        if (sender instanceof IZPPlayerMixinExt ext) {
            ext.setEnabledPickUpOnF(this.enabledPickUpOnF);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
    }
}