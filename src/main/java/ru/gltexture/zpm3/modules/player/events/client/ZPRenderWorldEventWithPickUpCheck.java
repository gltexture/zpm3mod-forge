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

package ru.gltexture.zpm3.modules.player.events.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ru.gltexture.zpm3.engine.core.config.builtin.ZPClientConfig;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.modules.net_pack.ZPNetPackModule;
import ru.gltexture.zpm3.modules.net_pack.data.vars.ZPNetDataBoolean;
import ru.gltexture.zpm3.modules.player.keybind.ZPPickUpKeyBindings;
import ru.gltexture.zpm3.modules.net_pack.packets.C2S.ZPPlayerWantToPickUpItemPacket;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPForgeEventHandlerClass;

@OnlyIn(Dist.CLIENT)
public class ZPRenderWorldEventWithPickUpCheck implements ZPForgeEventHandlerClass {
    public static @Nullable ItemEntity entityToPickUp = null;
    public static float pickUpCooldown;

    public ZPRenderWorldEventWithPickUpCheck() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    public static boolean canBePickedUp(@NotNull ItemEntity entity) {
        return entity.isAlive() && entity.tickCount > 20;
    }

    @SubscribeEvent
    public static void onTickWorld(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ZPRenderWorldEventWithPickUpCheck.pickUpCooldown -= 1;
        }
    }

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        final boolean pickUpOnKey = ZombiePlague3.netClient().getNetStaticDataSyncer().getVar(ZPNetPackModule.StoC__SERVER_PICK_UP_ON_KEY).orElse(new ZPNetDataBoolean(ZPClientConfig.PICK_UP_ON_KEY.getVar())).getValue();
        if (ZPClientConfig.PICK_UP_ON_KEY.getVar() && pickUpOnKey && event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null || mc.player == null) {
                return;
            }
            final float dist = 2.0f;
            final Vec3 targetPos = mc.player.getEyePosition().add(mc.player.getLookAngle().scale(dist));
            ZPRenderWorldEventWithPickUpCheck.entityToPickUp = null;
            BlockHitResult blockHit = mc.level.clip(new ClipContext(mc.player.getEyePosition(), targetPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mc.player));
            Vec3 rayEnd = blockHit.getType() == HitResult.Type.BLOCK ? blockHit.getLocation() : targetPos;
            EntityHitResult ehr = ProjectileUtil.getEntityHitResult(mc.level, mc.player, mc.player.getEyePosition(), rayEnd, mc.player.getBoundingBox().expandTowards(mc.player.getLookAngle().scale(dist)).inflate(1.0),
                    e -> e instanceof ItemEntity p && p.tickCount > 5
            , 0.3f);
            if (ehr != null && ehr.getEntity() instanceof ItemEntity entity) {
                ZPRenderWorldEventWithPickUpCheck.entityToPickUp = entity;
            }
            if (ZPRenderWorldEventWithPickUpCheck.entityToPickUp != null && ZPPickUpKeyBindings.pickItem.isDown()) {
                if (ZPRenderWorldEventWithPickUpCheck.pickUpCooldown < 0 && ZPRenderWorldEventWithPickUpCheck.canBePickedUp(ZPRenderWorldEventWithPickUpCheck.entityToPickUp)) {
                    ZombiePlague3.netClient().sendToServer(new ZPPlayerWantToPickUpItemPacket(ZPRenderWorldEventWithPickUpCheck.entityToPickUp.getId()));
                    ZPRenderWorldEventWithPickUpCheck.pickUpCooldown = 5;
                }
            }
        }
    }
}
