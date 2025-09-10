package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

public class ZPGunActionPacket implements ZPNetwork.ZPPacket {
    public final static int SHOT = 0x01;
    public final static int RELOAD = 0x02;
    public final static int UNLOAD = 0x03;

    private final int entityId;
    private final int action;
    private final boolean isRightHand;

    public ZPGunActionPacket(int entityId, int action, boolean isRightHand) {
        this.entityId = entityId;
        this.action = action;
        this.isRightHand = isRightHand;
    }

    public ZPGunActionPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.action = buf.readVarInt();
        this.isRightHand = buf.readBoolean();
    }

    public static Encoder<ZPGunActionPacket> encoder() {
        return (packet, buf) -> {
            buf.writeVarInt(packet.entityId);
            buf.writeVarInt(packet.action);
            buf.writeBoolean(packet.isRightHand);
        };
    }

    public static Decoder<ZPGunActionPacket> decoder() {
        return ZPGunActionPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
        Entity entityGot = serverLevel.getEntity(this.entityId);
        if (entityGot != null) {
            if (entityGot.equals(sender)) {
                ItemStack stack = sender.getItemInHand(this.isRightHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
                if (stack.getItem() instanceof ZPBaseGun baseGun) {
                    switch (this.action) {
                        case ZPGunActionPacket.SHOT -> {
                            if (baseGun.getCurrentAmmo(sender, stack) <= 0) {
                                serverLevel.playSound(sender, new BlockPos(sender.blockPosition()), baseGun.emptyAmmoSound(), SoundSource.MASTER, 1.0f, 1.0f);
                            } else {
                                if (baseGun.getServerGunLogic().tryToShoot(serverLevel, sender, baseGun, stack, this.isRightHand)) {
                                    this.sendPacketFromServer(sender, serverLevel);
                                }
                            }
                        }
                        case ZPGunActionPacket.RELOAD -> {
                            if (baseGun.getServerGunLogic().tryToReload(serverLevel, sender, baseGun, stack, false, this.isRightHand)) {
                                this.sendPacketFromServer(sender, serverLevel);
                            }
                        }
                        case ZPGunActionPacket.UNLOAD -> {
                            if (baseGun.getServerGunLogic().tryToReload(serverLevel, sender, baseGun, stack, true, this.isRightHand)) {
                                this.sendPacketFromServer(sender, serverLevel);
                            }
                        }
                    }
                }
            }
        }
    }

    private void sendPacketFromServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
        ZombiePlague3.net().sendToDimensionRadius(new ZPGunActionPacket(sender.getId(), this.action, this.isRightHand), serverLevel.dimension(), sender.position(), ZPConstants.DEFAULT_GUN_ACTION_PACKET_RANGE);
    }

    @Override
    public void onClient(@NotNull Player localPlayer, @NotNull ClientLevel clientLevel) {
        Entity entity = clientLevel.getEntity(this.entityId);
        if ((entity instanceof Player player) && !entity.equals(localPlayer)) {
            ItemStack stack = player.getItemInHand(this.isRightHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
            if (stack.getItem() instanceof ZPBaseGun baseGun) {
                switch (this.action) {
                    case ZPGunActionPacket.SHOT -> {
                        baseGun.getClientGunLogic().tryToShoot(clientLevel, player, baseGun, stack, this.isRightHand);
                    }
                    case ZPGunActionPacket.RELOAD -> {
                        baseGun.getClientGunLogic().tryToReload(clientLevel, player, baseGun, stack, false, this.isRightHand);
                    }
                    case ZPGunActionPacket.UNLOAD -> {
                        baseGun.getClientGunLogic().tryToReload(clientLevel, player, baseGun, stack, true, this.isRightHand);
                    }
                }
            }
        }
    }
}