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

package ru.gltexture.zpm3.modules.net_pack.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.modules.fx.init.ZPParticles;
import ru.gltexture.zpm3.modules.fx.particles.options.ColoredDefaultParticleOptions;
import ru.gltexture.zpm3.engine.core.ZPLogger;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.network.ZPNetwork;

import java.util.Objects;

public class ZPBloodPainFXPacket implements ZPNetwork.ZPPacket {
    private final int entityHit;
    private final boolean bleeding;

    public ZPBloodPainFXPacket(int entityHit, boolean bleeding) {
        this.entityHit = entityHit;
        this.bleeding = bleeding;
    }

    public ZPBloodPainFXPacket(FriendlyByteBuf buf) {
        this.entityHit = buf.readInt();
        this.bleeding = buf.readBoolean();
    }

    public static Encoder<ZPBloodPainFXPacket> encoder() {
        return (packet, buf) -> {
            buf.writeInt(packet.entityHit);
            buf.writeBoolean(packet.bleeding);
        };
    }

    public static Decoder<ZPBloodPainFXPacket> decoder() {
        return ZPBloodPainFXPacket::new;
    }

    @Override
    public void onServer(@NotNull Player sender, @NotNull ServerLevel serverLevel) {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onClient(@NotNull Player localPlayer) {
        ClientLevel clientLevel = Objects.requireNonNull(Minecraft.getInstance().level);
        Entity entity = Objects.requireNonNull(Minecraft.getInstance().level).getEntity(this.entityHit);
        if (entity != null) {
            Vector3f pos = entity.position().toVector3f();
            pos.add((float) (entity.getLookAngle().x * 0.5f), entity.getEyeHeight() / 2.0f, (float) (entity.getLookAngle().z * 0.5f));
            for (int is = 0; is < (this.bleeding ? 3 : (6 + ZPRandom.getRandom().nextInt(5))); is++) {
                Vector3f motion = ZPRandom.instance.randomVector3f(0.05f, new Vector3f(0.1f));
                Objects.requireNonNull(clientLevel).addParticle(new ColoredDefaultParticleOptions(ZPParticles.blood_fx.get(), new Vector3f(0.6f + ZPRandom.getRandom().nextFloat(0.2f), 0.0f, 0.0f), this.bleeding ? 0.6f : 0.8f, this.bleeding ? 60 : 12, 0.9f), false, pos.x, pos.y, pos.z, motion.x(), motion.y() + 0.25f, motion.z());
            }
        } else {
            ZPLogger.warn("Received entity-id: " + this.entityHit + ", but entity is NULL");
        }
    }

    public static boolean hasBlood(@NotNull Entity entity) {
        if (entity instanceof IronGolem) {
            return false;
        }
        return true;
    }
}