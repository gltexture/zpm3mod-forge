package ru.gltexture.zpm3.modules.net_pack.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

public class ZPValidateModePacket implements ZPNetwork.ZPPacket {
    private final boolean devMode;
    private final boolean wipMode;

    public ZPValidateModePacket(boolean devMode, boolean wipMode) {
        this.devMode = devMode;
        this.wipMode = wipMode;
    }

    public ZPValidateModePacket(FriendlyByteBuf buf) {
        this.devMode = buf.readBoolean();
        this.wipMode = buf.readBoolean();
    }

    public static Encoder<ZPValidateModePacket> encoder() {
        return (packet, buf) -> {
            buf.writeBoolean(packet.devMode);
            buf.writeBoolean(packet.wipMode);
        };
    }

    public static Decoder<ZPValidateModePacket> decoder() {
        return ZPValidateModePacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
        if (this.devMode != ZombiePlague3.DEV_MODE() || this.wipMode != ZombiePlague3.WIP_MODE()) {
            if (sender instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.disconnect(Component.literal("Client core-configuration mismatch. DEV_MODE and/or WIP_MODE differ from server."));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ZombiePlague3.net().sendToServer(new ZPValidateModePacket(ZombiePlague3.DEV_MODE(), ZombiePlague3.WIP_MODE()));
    }
}