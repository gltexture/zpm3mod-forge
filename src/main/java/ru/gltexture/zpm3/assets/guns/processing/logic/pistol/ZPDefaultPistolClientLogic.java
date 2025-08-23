package ru.gltexture.zpm3.assets.guns.processing.logic.pistol;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.crosshair.ZPClientCrosshairRecoilManager;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.engine.nbt.ZPTagID;
import ru.gltexture.zpm3.engine.service.Pair;

@OnlyIn(Dist.CLIENT)
public class ZPDefaultPistolClientLogic implements IGunLogicProcessor {
    public ZPDefaultPistolClientLogic() {
    }

    @Override
    public boolean tryToShoot(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
        final int shootCooldownNominal = item.getGunProperties().getShootCooldown();
        final int shootCooldownCurrent = item.getClientNBT(itemStack).getTagInt(ZPTagID.GUN_SHOOT_COOLDOWN_TAG);
        final int reloadTimeCurrent = item.getClientNBT(itemStack).getTagInt(ZPTagID.GUN_RELOAD_COOLDOWN_TAG);

        if (shootCooldownCurrent <= 0 && reloadTimeCurrent <= 0) {
            if (item.getGunProperties().getFireSound() != null) {
                this.localSound(item.getGunProperties().getFireSound().get());
            }
            final float recoilStrength = ZPClientCrosshairRecoilManager.setVerticalRecoil(item.getGunProperties().getClientVerticalRecoil());
            ZPClientCallbacksManager.INSTANCE.triggerGunShots(player, item, itemStack, new ZPClientCallbacks.ZPGunShotCallback.GunFXData(isRightHand, recoilStrength, 0.25f));
            item.getClientNBT(itemStack).putTagInt(Pair.of(ZPTagID.GUN_SHOOT_COOLDOWN_TAG, shootCooldownNominal));
            return true;
        }

        return false;
    }

    @Override
    public boolean tryToReload(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
        final int reloadTimeNominal = item.getGunProperties().getReloadTime();
        final int reloadTimeCurrent = item.getClientNBT(itemStack).getTagInt(ZPTagID.GUN_RELOAD_COOLDOWN_TAG);

        if (reloadTimeCurrent <= 0) {
            if (item.getGunProperties().getReloadSound() != null) {
                this.localSound(item.getGunProperties().getReloadSound().get());
            }
            item.getClientNBT(itemStack).putTagInt(Pair.of(ZPTagID.GUN_RELOAD_COOLDOWN_TAG, reloadTimeNominal));
            ZPClientCallbacksManager.INSTANCE.triggerReloadingStart(player, item, itemStack, new ZPClientCallbacks.ZPGunReloadStartCallback.GunFXData(isRightHand));
            return true;
        }

        return false;
    }

    private void localSound(SoundEvent soundEvent) {
        SimpleSoundInstance sound = SimpleSoundInstance.forLocalAmbience(soundEvent, 1.0f, 1.0F);
        Minecraft.getInstance().getSoundManager().play(sound);
    }

    @Override
    public void onTickInventory(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected, boolean offHand) {
        item.getClientNBT(pStack).decrementInt((e) -> e > 0, ZPTagID.GUN_SHOOT_COOLDOWN_TAG);
        item.getClientNBT(pStack).decrementInt((e) -> e > 0, ZPTagID.GUN_RELOAD_COOLDOWN_TAG);

        if (!pIsSelected && !offHand) {
            item.getClientNBT(pStack).putTagInt(Pair.of(ZPTagID.GUN_RELOAD_COOLDOWN_TAG, 0));
            item.getClientNBT(pStack).putTagBoolean(Pair.of(ZPTagID.GUN_IS_RELOADING_TAG, false));
            item.getClientNBT(pStack).putTagBoolean(Pair.of(ZPTagID.GUN_IS_UNLOADING_TAG, false));
        }
    }
}