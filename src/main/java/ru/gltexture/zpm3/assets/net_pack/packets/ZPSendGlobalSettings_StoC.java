package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.player.client.ZPClientGlobalSettings;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

public class ZPSendGlobalSettings_StoC implements ZPNetwork.ZPPacket {
    private final int dayTimeCycleTicksFreeze;
    private final int nightTimeCycleTicksFreeze;
    private final boolean isDarknessEnabled;
    private final boolean pickUpOnF;
    private final float darknessFactor;

    public static @NotNull ZPSendGlobalSettings_StoC create() {
        return new ZPSendGlobalSettings_StoC(ZPConstants.WORLD_DAY_TIME_SLOWDOWN_CYCLE_TICKING, ZPConstants.WORLD_NIGHT_TIME_SLOWDOWN_CYCLE_TICKING, ZPConstants.ENABLE_HARDCORE_DARKNESS_SERVER_SIDE, ZPConstants.PICK_UP_ON_F, ZPConstants.DARKNESS_GAMMA_STATIC_FACTOR_SERVER_SIDE);
    }

    public ZPSendGlobalSettings_StoC(int dayTimeCycleTicksFreeze, int nightTimeCycleTicksFreeze, boolean isDarknessEnabled, boolean pickUpOnF, float darknessFactor) {
        this.dayTimeCycleTicksFreeze = dayTimeCycleTicksFreeze;
        this.nightTimeCycleTicksFreeze = nightTimeCycleTicksFreeze;
        this.isDarknessEnabled = isDarknessEnabled;
        this.pickUpOnF = pickUpOnF;
        this.darknessFactor = darknessFactor;
    }

    public ZPSendGlobalSettings_StoC(FriendlyByteBuf buf) {
        this.dayTimeCycleTicksFreeze = buf.readInt();
        this.nightTimeCycleTicksFreeze = buf.readInt();
        this.isDarknessEnabled = buf.readBoolean();
        this.pickUpOnF = buf.readBoolean();
        this.darknessFactor = buf.readFloat();
    }

    public static Encoder<ZPSendGlobalSettings_StoC> encoder() {
        return (packet, buf) -> {
            buf.writeInt(packet.dayTimeCycleTicksFreeze);
            buf.writeInt(packet.nightTimeCycleTicksFreeze);
            buf.writeBoolean(packet.isDarknessEnabled);
            buf.writeBoolean(packet.pickUpOnF);
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
        ZPClientGlobalSettings.DAY_TIME_CYCLE_TICKS_FREEZE = this.dayTimeCycleTicksFreeze;
        ZPClientGlobalSettings.NIGHT_TIME_CYCLE_TICKS_FREEZE = this.nightTimeCycleTicksFreeze;
        ZPClientGlobalSettings.DARKNESS_ENABLED = this.isDarknessEnabled;
        ZPClientGlobalSettings.DARKNESS_FACTOR = this.darknessFactor;
        ZPClientGlobalSettings.SERVER_PICK_UP_ON_F = this.pickUpOnF;
    }
}