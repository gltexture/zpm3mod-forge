package ru.gltexture.zpm3.assets.guns.processing.logic.shotgun;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.processing.input.ZPClientGunClientTickProcessing;
import ru.gltexture.zpm3.assets.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.assets.guns.processing.logic.ZPDefaultLogicFunctions;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPGunActionPacket;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.crosshair.ZPClientCrosshairRecoilManager;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.nbt.ZPTagID;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.engine.sound.ZPPositionedSound;

@OnlyIn(Dist.CLIENT)
public class ZPDefaultShotgunClientLogic implements IGunLogicProcessor {
    public ZPDefaultShotgunClientLogic() {
    }

    @Override
    public boolean tryToShoot(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
        return ZPDefaultLogicFunctions.CLIENT_SHUTTER_ANIMATED_GUN_SHOT(this, level, player, item, itemStack, isRightHand);
    }

    @Override
    public boolean tryToReload(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean unload, boolean isRightHand) {
        return ZPDefaultLogicFunctions.CLIENT_SHUTTER_ANIMATED_GUN_RELOAD(this, level, player, item, itemStack, unload, isRightHand);
    }

    @Override
    public void onTickInventory(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected, boolean offHand) {
        ZPDefaultLogicFunctions.CLIENT_DEFAULT_SHUTTER_ANIMATED_GUN_TICK(this, pStack, pLevel, item, pEntity, pSlotId, pIsSelected, offHand);
    }
}