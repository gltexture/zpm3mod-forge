package ru.gltexture.zpm3.assets.net_pack.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.mixins.ext.IZPPlayerMixinExt;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

public class ZPPlayerWantToPickUpItem implements ZPNetwork.ZPPacket {
    private final int itemId;

    public ZPPlayerWantToPickUpItem(int itemId) {
        this.itemId = itemId;
    }

    public ZPPlayerWantToPickUpItem(FriendlyByteBuf buf) {
        this.itemId = buf.readInt();
    }

    public static Encoder<ZPPlayerWantToPickUpItem> encoder() {
        return (packet, buf) -> {
            buf.writeInt(packet.itemId);
        };
    }

    public static Decoder<ZPPlayerWantToPickUpItem> decoder() {
        return ZPPlayerWantToPickUpItem::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
        if (sender instanceof IZPPlayerMixinExt ext) {
            Entity entity = level.getEntity(this.itemId);
            if (ext.enabledPickUpOnF() && entity instanceof ItemEntity entity1) {
                if (entity1.isAlive() && !entity1.hasPickUpDelay() && sender.distanceTo(entity1) <= 2.25f) {
                    this.pickUpItem(sender, entity1);
                }
            }
        }
    }

    protected void pickUpItem(@NotNull Player player, ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        if (!stack.isEmpty()) {
            boolean added = player.getInventory().add(stack.copy());
            if (added) {
                player.level().playSound(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((ZPRandom.getRandom().nextFloat() - ZPRandom.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.onItemPickup(itemEntity);
                itemEntity.discard();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
    }
}