package ru.gltexture.zpm3.engine.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ZPNetwork {
    private final SimpleChannel mainChannel;

    public ZPNetwork(String channel, String version) {
        String protoFullVer = version;
        {
            if (ZombiePlague3.DEV_MODE()) {
                protoFullVer += "DEV ";
            }
            if (ZombiePlague3.WIP_MODE()) {
                protoFullVer += "WIP ";
            }
        }
        final String finalProtoFullVer = protoFullVer;
        this.mainChannel = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), channel), () -> finalProtoFullVer, finalProtoFullVer::equals, finalProtoFullVer::equals);
    }

    @SuppressWarnings("all")
    protected <T extends ZPPacket> void registerPacket(PacketData<T> packetData) {
        this.getMainChannel().<T>registerMessage(packetData.id, (Class<T>) packetData.packetClass(), packetData.encoder()::encode, packetData.decoder()::decode, (msg, ctxSupplier) -> {
            ctxSupplier.get().enqueueWork(() -> msg.handle(() -> ctxSupplier.get()));
            ctxSupplier.get().setPacketHandled(true);
        });
    }

    public void register(@NotNull List<PacketData<? extends ZPPacket>> packetsData) {
        for (PacketData<? extends ZPPacket> packetClass : packetsData) {
            this.registerPacket(packetClass);
        }
    }

    public SimpleChannel getMainChannel() {
        return this.mainChannel;
    }

    public record PacketData <T extends ZPPacket> (int id, Class<T> packetClass, ZPPacket.Encoder<T> encoder, ZPPacket.Decoder<T> decoder) { }

    public interface ZPPacket {
        default void handle(Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            if (context.getDirection() == NetworkDirection.PLAY_TO_SERVER && context.getSender() != null) {
                context.enqueueWork(() -> this.onServer(context.getSender(), (ServerLevel) context.getSender().level()));
            } else if (context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                ZPUtility.sides().onlyClient(() -> {
                    context.enqueueWork(() -> this.onClient(Objects.requireNonNull(Minecraft.getInstance().player)));
                });
            }
        }

        void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel);
        void onClient(@NotNull Player localPlayer);

        @FunctionalInterface
        interface Encoder<T extends ZPPacket> {
            void encode(T packet, FriendlyByteBuf buf);
        }

        @FunctionalInterface
        interface Decoder<T extends ZPPacket> {
            T decode(FriendlyByteBuf buf);
        }
    }
}
