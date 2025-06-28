package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import ru.gltexture.zpm3.assets.entity.nbt.ZPTagsList;
import ru.gltexture.zpm3.engine.nbt.ZPEntityNBT;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Objects;

public class AcidSpreadPacket implements ZPNetwork.ZPPacket {
    private final int entityId;
    private final int acidLevel;

    public AcidSpreadPacket(int entityId, int acidLevel) {
        this.entityId = entityId;
        this.acidLevel = acidLevel;
    }

    public AcidSpreadPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.acidLevel = buf.readVarInt();
    }

    public static Encoder<AcidSpreadPacket> encoder() {
        return (packet, buf) -> {
            buf.writeVarInt(packet.entityId);
            buf.writeVarInt(packet.acidLevel);
        };
    }

    public static Decoder<AcidSpreadPacket> decoder() {
        return AcidSpreadPacket::new;
    }

    @Override
    public void onServer() {

    }

    @Override
    public void onClient() {
        ZPUtility.client().ifClientLevelValid(() -> {
            Entity entity = Objects.requireNonNull(Minecraft.getInstance().level).getEntity(this.entityId);
            if (entity != null) {
                new ZPEntityNBT(entity).incrementInt(ZPTagsList.ACID_AFFECT_COOLDOWN, this.acidLevel);
            } else {
                ZPLogger.warn("Received entity-id: " + this.entityId + ", but entity is NULL");
            }
        });
    }
}
