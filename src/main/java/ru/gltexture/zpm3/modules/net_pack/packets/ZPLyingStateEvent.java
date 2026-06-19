package ru.gltexture.zpm3.modules.net_pack.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;

public class ZPLyingStateEvent implements ZPNetwork.ZPPacket {
    private final boolean forceLying;

    public ZPLyingStateEvent(boolean forceLying) {
        this.forceLying = forceLying;
    }

    public ZPLyingStateEvent(FriendlyByteBuf buf) {
        this.forceLying = buf.readBoolean();
    }

    public static Encoder<ZPLyingStateEvent> encoder() {
        return (packet, buf) -> {
            buf.writeBoolean(packet.forceLying);
        };
    }

    public static Decoder<ZPLyingStateEvent> decoder() {
        return ZPLyingStateEvent::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
        if (sender instanceof ServerPlayer serverPlayer) {
            if (sender instanceof IZPPlayerMixinExt izpPlayerMixinExt) {
                izpPlayerMixinExt.zpm3forge$setLying(this.forceLying);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
    }
}