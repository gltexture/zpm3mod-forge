package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Objects;

public class ZPAcidSpreadPacket implements ZPNetwork.ZPPacket {
    private final int entityId;
    private final int acidLevel;

    public ZPAcidSpreadPacket(int entityId, int acidLevel) {
        this.entityId = entityId;
        this.acidLevel = acidLevel;
    }

    public ZPAcidSpreadPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.acidLevel = buf.readVarInt();
    }

    public static Encoder<ZPAcidSpreadPacket> encoder() {
        return (packet, buf) -> {
            buf.writeVarInt(packet.entityId);
            buf.writeVarInt(packet.acidLevel);
        };
    }

    public static Decoder<ZPAcidSpreadPacket> decoder() {
        return ZPAcidSpreadPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {

    }

    @Override
    public void onClient(@NotNull Player localPlayer, @NotNull ClientLevel clientLevel) {
        ZPUtility.client().ifClientLevelValid(() -> {
            Entity entity = Objects.requireNonNull(Minecraft.getInstance().level).getEntity(this.entityId);
            if (entity != null) {
               // new ZPItemStackNBT(entity).incrementInt(ZPEntityTagsList.ACID_AFFECT_COOLDOWN, this.acidLevel);
            } else {
                ZPLogger.warn("Received entity-id: " + this.entityId + ", but entity is NULL");
            }
        });
    }
}
