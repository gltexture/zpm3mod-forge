/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.net_pack.packets.C2S;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.modules.net_pack.ZPNetPackModule;
import ru.gltexture.zpm3.modules.net_pack.data.vars.ZPNetDataBoolean;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

public class ZPPlayerWantToPickUpItemPacket implements ZPNetwork.ZPPacket {
    private final int itemId;

    public ZPPlayerWantToPickUpItemPacket(int itemId) {
        this.itemId = itemId;
    }

    public ZPPlayerWantToPickUpItemPacket(FriendlyByteBuf buf) {
        this.itemId = buf.readInt();
    }

    public static Encoder<ZPPlayerWantToPickUpItemPacket> encoder() {
        return (packet, buf) -> {
            buf.writeInt(packet.itemId);
        };
    }

    public static Decoder<ZPPlayerWantToPickUpItemPacket> decoder() {
        return ZPPlayerWantToPickUpItemPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel level) {
        if (sender instanceof IZPPlayerMixinExt ext) {
            final ServerPlayer player = (ServerPlayer) sender;
            Entity entity = level.getEntity(this.itemId);
            final var syncer = ZombiePlague3.netServer().getNetStaticDataSyncer();
            final boolean flagPick = !syncer.check(player) || syncer.getPack(player).flatMap(pack -> pack.getVar(ZPNetPackModule.CtoS__PICK_UP_ON_KEY)).orElse(new ZPNetDataBoolean(ZPWorldConfig.ALLOW_ITEMS_PICKING_ON_KEY.getVar())).getValue();            if (flagPick && entity instanceof ItemEntity entity1) {
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