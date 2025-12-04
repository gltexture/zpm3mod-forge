package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.mixins.ext.IZPPlayerMixinExt;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Objects;

public class ZPNetCheckPacket implements ZPNetwork.ZPPacket {
    private final int entityId;

    public ZPNetCheckPacket(int entityId) {
        this.entityId = entityId;
    }

    public ZPNetCheckPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readVarInt();
    }

    public static Encoder<ZPNetCheckPacket> encoder() {
        return (packet, buf) -> {
            buf.writeVarInt(packet.entityId);
        };
    }

    public static Decoder<ZPNetCheckPacket> decoder() {
        return ZPNetCheckPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
        try {
            Entity entity = Objects.requireNonNull(serverLevel).getEntity(this.entityId);
            if (entity instanceof IZPPlayerMixinExt ext) {
                ext.getResultFromClient();
            }
        } catch (Exception ignored) {
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ClientLevel clientLevel = Objects.requireNonNull(Minecraft.getInstance().level);
        ((IZPPlayerMixinExt) localPlayer).getResultFromServer();
    }
}