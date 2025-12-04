package ru.gltexture.zpm3.assets.common.events.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.common.keybind.ZPCommonKeyBindings;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPPlayerWantToPickUpItem;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

public class ZPRenderWorldEventWithPickUpCheck implements ZPEventClass {
    public static @Nullable ItemEntity entityToPickUp = null;

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

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null || mc.player == null) {
                return;
            }
            final float dist = 2.0f;
            final Vec3 targetPos = mc.player.getEyePosition().add(mc.player.getLookAngle().scale(dist));
            ZPRenderWorldEventWithPickUpCheck.entityToPickUp = null;
            BlockHitResult blockHit = mc.level.clip(new ClipContext(mc.player.getEyePosition(), targetPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, mc.player));
            Vec3 rayEnd = blockHit.getType() == HitResult.Type.BLOCK ? blockHit.getLocation() : targetPos;
            EntityHitResult ehr = ProjectileUtil.getEntityHitResult(mc.level, mc.player, mc.player.getEyePosition(), rayEnd, mc.player.getBoundingBox().expandTowards(mc.player.getLookAngle().scale(dist)).inflate(1.0),
                    e -> e instanceof ItemEntity entity && entity.isAlive() && entity.tickCount > 10
            , 0.3f);
            if (ehr != null && ehr.getEntity() instanceof ItemEntity entity) {
                ZPRenderWorldEventWithPickUpCheck.entityToPickUp = entity;
            }
            if (ZPRenderWorldEventWithPickUpCheck.entityToPickUp != null && ZPCommonKeyBindings.pickItem.isDown()) {
                ZombiePlague3.net().sendToServer(new ZPPlayerWantToPickUpItem(ZPRenderWorldEventWithPickUpCheck.entityToPickUp.getId()));
            }
        }
    }
}
