package ru.gltexture.zpm3.assets.guns.processing.logic.shotgun;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector3i;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.processing.bullet.VirtualBullet;
import ru.gltexture.zpm3.assets.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.assets.guns.processing.logic.ZPDefaultLogicFunctions;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPBulletHitPacket;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPBulletTracePacket;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPDefaultShotgunServerLogic implements IGunLogicProcessor {
    public ZPDefaultShotgunServerLogic() {
    }

    @Override
    public boolean tryToShoot(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
        return ZPDefaultLogicFunctions.SERVER_DEFAULT_SHUTTER_ANIMATED_GUN_SHOT(this, level, player, item, itemStack, isRightHand, 9);
    }

    @Override
    public boolean tryToReload(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean unload, boolean isRightHand) {
        return ZPDefaultLogicFunctions.SERVER_DEFAULT_SHUTTER_ANIMATED_GUN_RELOAD(this, level, player, item, itemStack, unload, isRightHand);
    }

    @Override
    public void onTickInventory(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected, boolean offHand) {
        ZPDefaultLogicFunctions.SERVER_DEFAULT_SHUTTER_ANIMATED_GUN_TICK(this, pStack, pLevel, item, pEntity, pSlotId, pIsSelected, offHand);
    }
}
