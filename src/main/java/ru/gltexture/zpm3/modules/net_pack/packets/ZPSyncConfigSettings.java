package ru.gltexture.zpm3.modules.net_pack.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.exceptions.ZPRuntimeException;
import ru.gltexture.zpm3.engine.mixins.ext.IZPPlayerMixinExt;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.net_pack.data.ZPNetSyncDataPack;

public class ZPSyncConfigSettings implements ZPNetwork.ZPPacket {
    private final ZPNetSyncDataPack zpNetSyncDataPack;

    public ZPSyncConfigSettings(ZPNetSyncDataPack zpNetSyncDataPack) {
        this.zpNetSyncDataPack = zpNetSyncDataPack;
    }

    public ZPSyncConfigSettings(FriendlyByteBuf buf) {
        this.zpNetSyncDataPack = new ZPNetSyncDataPack(buf.readMap(
                FriendlyByteBuf::readUtf,
                b -> {
                    byte type = b.readByte();
                    return switch (type) {
                        case 0 -> b.readInt();
                        case 1 -> b.readDouble();
                        case 2 -> b.readBoolean();
                        case 3 -> b.readFloat();
                        default -> throw new ZPRuntimeException("Unknown type: " + type);
                    };
                }
        ));
    }

    public static Encoder<ZPSyncConfigSettings> encoder() {
        return (packet, buf) -> {
            buf.writeMap(
                    packet.zpNetSyncDataPack.dataPack(),
                    FriendlyByteBuf::writeUtf,
                    (b, value) -> {
                        if (value instanceof Integer i) {
                            b.writeByte(0);
                            b.writeInt(i);
                        } else if (value instanceof Double d) {
                            b.writeByte(1);
                            b.writeDouble(d);
                        } else if (value instanceof Boolean bool) {
                            b.writeByte(2);
                            b.writeBoolean(bool);
                        } else if (value instanceof Float f) {
                            b.writeByte(3);
                            b.writeFloat(f);
                        }
                    }
            );
        };
    }

    public static Decoder<ZPSyncConfigSettings> decoder() {
        return ZPSyncConfigSettings::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
        if (sender instanceof IZPPlayerMixinExt ext) {
            ext.zpm3forge$zpNetDataPack_fromClient().replace(this.zpNetSyncDataPack);
            ZPLogger.info(sender.getDisplayName() + " : ZPSyncConfigSettings onServer : " + this.zpNetSyncDataPack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ZombiePlague3.getClient_netSyncDataPack().replace(this.zpNetSyncDataPack);
        ZPLogger.info("ZPSyncConfigSettings onClient : " + this.zpNetSyncDataPack);
    }
}