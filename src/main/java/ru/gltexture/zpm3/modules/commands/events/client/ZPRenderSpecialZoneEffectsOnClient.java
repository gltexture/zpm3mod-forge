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

package ru.gltexture.zpm3.modules.commands.events.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.zones.ZPZoneChecks;
import ru.gltexture.zpm3.engine.zones.ZPZoneFlag;
import ru.gltexture.zpm3.engine.zones.ZPZoneManager;
import ru.gltexture.zpm3.modules.common.utils.ZPCommonClientUtils;

import java.util.*;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ZPRenderSpecialZoneEffectsOnClient implements ZPForgeEventHandlerClass {
    private static final Map<ZPZoneFlag, RenderZoneEffect> zoneEffectMap = new HashMap<>();

    public ZPRenderSpecialZoneEffectsOnClient() {
    }

    public static void registerZoneEffect(@NotNull final ZPZoneFlag flag, @NotNull final RenderZoneEffect effect) {
        ZPRenderSpecialZoneEffectsOnClient.zoneEffectMap.put(flag, effect);
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null || mc.player == null || mc.isPaused()) {
            return;
        }

        ZPZoneManager.ZonesContainer container = ZPZoneManager.INSTANCE.ZONES_CONTAINER(mc.level);
        if (container == null) {
            return;
        }

        ChunkPos center = mc.player.chunkPosition();
        int viewDistance = mc.options.renderDistance().get();

        for (int chunkX = center.x - viewDistance; chunkX <= center.x + viewDistance; chunkX++) {
            for (int chunkZ = center.z - viewDistance; chunkZ <= center.z + viewDistance; chunkZ++) {
                if (mc.level.getChunkSource().getChunk(chunkX, chunkZ, false) == null) {
                    continue;
                }
                List<ZPZoneManager.Zone> zones = container.getFast_ChunkLookupTable().get(new Vector2i(chunkX, chunkZ));
                if (zones == null) {
                    continue;
                }
                for (ZPZoneManager.Zone zone : zones) {
                    for (ZPZoneFlag flag : zone.flags()) {
                        RenderZoneEffect effect = zoneEffectMap.get(flag);
                        if (effect != null) {
                            effect.render(zone, chunkX, chunkZ);
                        }
                    }
                }
            }
        }
    }

    public static void renderCloudDefaultFun(@NotNull ZPZoneManager.Zone zone, int chunkX, int chunkZ, boolean acid) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) {
            return;
        }

        Pair<Vector3f, Vector3f> pair = ZPZoneManager.Zone.min_max(zone.start(), zone.end());

        Vector3f min = pair.first();
        Vector3f max = pair.second();

        float chunkMinX = chunkX * 16.0f;
        float chunkMaxX = chunkMinX + 16.0f;
        float chunkMinZ = chunkZ * 16.0f;
        float chunkMaxZ = chunkMinZ + 16.0f;

        float minX = Math.max(min.x(), chunkMinX);
        float maxX = Math.min(max.x(), chunkMaxX);
        float minZ = Math.max(min.z(), chunkMinZ);
        float maxZ = Math.min(max.z(), chunkMaxZ);

        if (minX >= maxX || minZ >= maxZ) {
            return;
        }

        final int particles = 1;

        for (int i = 0; i < particles; i++) {
            if (minX == maxX || minZ == maxZ || min.y() == max.y()) {
                break;
            }
            Vector3f pos = new Vector3f(ZPRandom.getRandom().nextFloat(minX, maxX), ZPRandom.getRandom().nextFloat(min.y(), max.y()), ZPRandom.getRandom().nextFloat(minZ, maxZ));
            Vector3f vel = ZPRandom.instance.randomVector3f(new Vector3f(-0.005f), new Vector3f(0.005f, 0.02f, 0.005f));
            if (ZPRandom.getRandom().nextFloat() <= 0.01f) {
                float scale = 4.5f + ZPRandom.getRandom().nextFloat() * 2.5f;
                if (acid) {
                    if (!ZPZoneChecks.INSTANCE.isNoAcidAffection(Minecraft.getInstance().level, new BlockPos(new Vec3i((int) pos.x, (int) pos.y, (int) pos.z)))) {
                        ZPCommonClientUtils.emmitAcidParticle(true, scale, pos, vel);
                    }
                } else {
                    if (!ZPZoneChecks.INSTANCE.isNoToxicAffection(Minecraft.getInstance().level, new BlockPos(new Vec3i((int) pos.x, (int) pos.y, (int) pos.z)))) {
                        ZPCommonClientUtils.emmitToxicParticle(true, scale, pos, vel);
                    }
                }
            }
            {
                float scale = 1.5f + ZPRandom.getRandom().nextFloat() * 1.5f;
                if (acid) {
                    if (!ZPZoneChecks.INSTANCE.isNoAcidAffection(Minecraft.getInstance().level, new BlockPos(new Vec3i((int) pos.x, (int) pos.y, (int) pos.z)))) {
                        ZPCommonClientUtils.emmitAcidParticle(false, scale, pos, vel);
                    }
                } else {
                    if (!ZPZoneChecks.INSTANCE.isNoToxicAffection(Minecraft.getInstance().level, new BlockPos(new Vec3i((int) pos.x, (int) pos.y, (int) pos.z)))) {
                        ZPCommonClientUtils.emmitToxicParticle(false, scale, pos, vel);
                    }
                }
            }
        }
    }

    @FunctionalInterface
    public interface RenderZoneEffect {
        void render(ZPZoneManager.Zone zone, int chunkX, int chunkZ);
    }
}
