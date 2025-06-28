package ru.gltexture.zpm3.engine.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.util.Set;
import java.util.function.Supplier;

public class ZPNetwork {
    private static int packetId = 0;

    private static final String PROTO_VERSION = ZombiePlague3.MOD_VERSION();
    public static final String MAIN_CHANNEL = "main";

    private final SimpleChannel mainChannel;

    public ZPNetwork() {
        this.mainChannel = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID(), ZPNetwork.MAIN_CHANNEL), () -> ZPNetwork.PROTO_VERSION, ZPNetwork.PROTO_VERSION::equals, ZPNetwork.PROTO_VERSION::equals);
    }

    @SuppressWarnings("all")
    protected <T extends ZPPacket> void registerPacket(PacketData<T> packetData) {
        this.getMainChannel().<T>registerMessage(ZPNetwork.packetId++, (Class<T>) packetData.packetClass(), packetData.encoder()::encode, packetData.decoder()::decode, (msg, ctxSupplier) -> msg.handle(ctxSupplier));
    }

    public void register(@NotNull Set<PacketData<? extends ZPPacket>> packetsData) {
        for (PacketData<? extends ZPPacket> packetClass : packetsData) {
            this.registerPacket(packetClass);
        }
    }

    public SimpleChannel getMainChannel() {
        return this.mainChannel;
    }

    public record PacketData <T extends ZPPacket> (Class<T> packetClass, ZPPacket.Encoder<T> encoder, ZPPacket.Decoder<T> decoder) { }

    public interface ZPPacket {
        default void handle(Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (context.getDirection().getReceptionSide().isServer()) {
                    this.onServer();
                } else {
                    this.onClient();
                }
            });
            context.setPacketHandled(true);
        }

        void onServer();
        void onClient();

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
