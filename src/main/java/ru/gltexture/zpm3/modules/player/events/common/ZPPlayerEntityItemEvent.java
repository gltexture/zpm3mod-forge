package ru.gltexture.zpm3.modules.player.events.common;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.modules.player.mixins.ext.IZPPlayerMixinExt;
import ru.gltexture.zpm3.modules.net_pack.data.DefaultDataKeys;

public class ZPPlayerEntityItemEvent implements ZPEventClass {
    @SubscribeEvent
    public static void exec(@NotNull EntityItemPickupEvent event) {
        if (event.getEntity() instanceof IZPPlayerMixinExt ext) {
            if (ZPWorldConfig.ALLOW_ITEMS_PICKING_ON_KEY.getVar() && ext.zpm3forge$zpNetDataPack_fromClient().getBoolean(DefaultDataKeys.StoC__SERVER_PICK_UP_ON_F, ZPWorldConfig.ALLOW_ITEMS_PICKING_ON_KEY.getVar())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDropItem(ItemTossEvent event) {
        Player player = event.getPlayer();
        ItemEntity droppedItem = event.getEntity();
        droppedItem.setPickUpDelay(10);
        float dot = (player.getLookAngle().toVector3f().dot(new Vector3f(0.0f, 1.0f, 0.0f)) + 1.0f) / 2.0f;
        dot = Math.max(dot, 0.25f);
        Vec3 vecMovement = new Vec3(new Vector3f(player.getLookAngle().toVector3f()).mul(0.75f * dot));
        droppedItem.setDeltaMovement(vecMovement);
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.COMMON;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }
}
