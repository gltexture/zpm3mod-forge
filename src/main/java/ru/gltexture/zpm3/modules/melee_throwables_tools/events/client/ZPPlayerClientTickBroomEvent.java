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

package ru.gltexture.zpm3.modules.melee_throwables_tools.events.client;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;
import ru.gltexture.zpm3.modules.common.utils.ZPCommonClientUtils;
import ru.gltexture.zpm3.modules.melee_throwables_tools.instances.melee.ZPBroomSword;

@OnlyIn(Dist.CLIENT)
public class ZPPlayerClientTickBroomEvent implements ZPForgeEventHandlerClass {
    public ZPPlayerClientTickBroomEvent() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Player player = event.player;
        if (!player.level().isClientSide()) {
            return;
        }
        if (!(player.getUseItem().getItem() instanceof ZPBroomSword)) {
            return;
        }
        if (!player.isUsingItem()) {
            return;
        }
        if (player.tickCount % 12 != 0) {
            return;
        }
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getLookAngle().scale(4.0));
        BlockHitResult hit = player.level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (hit.getType() != HitResult.Type.BLOCK) {
            return;
        }
        Level level = player.level();
        BlockPos pos = hit.getBlockPos();
        Vec3 hitPos = hit.getLocation();
        level.playLocalSound(hitPos.x, hitPos.y, hitPos.z, SoundEvents.BRUSH_GENERIC, SoundSource.PLAYERS, 1.0F, 0.9F + level.random.nextFloat() * 0.2F, false);
        for (int i = 0; i < 8; i++) {
            Vector3f velocity = ZPRandom.instance.randomVector3f(new Vector3f(0.05f, 0.05f, 0.05f), new Vector3f(0.1f, 0.05f, 0.1f));
            Vector3f position = new Vector3f((float) hitPos.x + (level.random.nextFloat() - 0.5f) * 0.25f, (float) hitPos.y + 0.02f, (float) hitPos.z + (level.random.nextFloat() - 0.5f) * 0.25f);
            ZPCommonClientUtils.emmitDustParticle(false, 0.5f + ZPRandom.getRandom().nextFloat(1.5f), position, velocity);
        }
    }
}
