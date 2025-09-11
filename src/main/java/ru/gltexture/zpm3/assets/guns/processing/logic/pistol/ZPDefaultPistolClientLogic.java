package ru.gltexture.zpm3.assets.guns.processing.logic.pistol;

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
import ru.gltexture.zpm3.assets.guns.processing.input.ZPClientGunClientTickProcessing;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPGunActionPacket;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.crosshair.ZPClientCrosshairRecoilManager;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.engine.sound.ZPPositionedSound;

@OnlyIn(Dist.CLIENT)
public class ZPDefaultPistolClientLogic implements IGunLogicProcessor {
    public ZPDefaultPistolClientLogic() {
    }

    @Override
    public boolean tryToShoot(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
        final int shootCooldownNominal = item.getGunProperties().getShootCooldown();
        final int currentAmmo = item.getCurrentAmmo(player, itemStack);

        if (player.equals(Minecraft.getInstance().player)) {
            if (item.getCurrentTimeBeforeShoot(player, itemStack) > 0) {
                return false;
            }
            if (!item.isUnloadingOrReloading(player, itemStack) && item.getCurrentShootCooldown(player, itemStack) <= 0) {
                ZombiePlague3.net().sendToServer(new ZPGunActionPacket(player.getId(), ZPGunActionPacket.SHOT, isRightHand));
                if (currentAmmo > 0 && !item.isJammed(player, itemStack)) {
                    if (item.getGunProperties().getFireSound() != null) {
                        this.localSound(item.getGunProperties().getFireSound().get(), item);
                    }
                    final float recoilStrength = ZPClientCrosshairRecoilManager.setVerticalRecoil(item.getGunProperties().getClientVerticalRecoil());
                    item.setCurrentShootCooldown(player, itemStack, shootCooldownNominal);
                    ZPClientCallbacksManager.INSTANCE.triggerGunShots(player, item, itemStack, new ZPClientCallbacks.ZPGunShotCallback.GunFXData(isRightHand, recoilStrength, 0.25f));
                    item.setCurrentAmmo(player, itemStack, item.getCurrentAmmo(player, itemStack) - 1);
                    item.setClientSyncCooldown(itemStack, item.getCurrentAmmo(player, itemStack) <= 0 ? 0 : ZPClientGunClientTickProcessing.TICK_SYNC_INTERVAL);
                } else {
                    if (!item.isJammed(player, itemStack)) {
                        if (player.getInventory().countItem(item.getGunProperties().getAmmo()) > 0) {
                            if (this.tryToReload(level, player, item, itemStack, false, isRightHand)) {
                                return false;
                            }
                        }
                    }
                    this.localSound(item.emptyAmmoSound(), item);
                    ZPClientCallbacksManager.INSTANCE.triggerGunShots(player, item, itemStack, new ZPClientCallbacks.ZPGunShotCallback.GunFXData(isRightHand, -1.0f, -1.0f));
                    item.setCurrentShootCooldown(player, itemStack, 4);
                }
                return true;
            }
        } else {
            if (item.getGunProperties().getFireSound() != null) {
                this.globalSound(item.getGunProperties().getFireSound().get(), item, player);
            }
            ZPClientCallbacksManager.INSTANCE.triggerGunShots(player, item, itemStack, new ZPClientCallbacks.ZPGunShotCallback.GunFXData(isRightHand, item.getGunProperties().getClientVerticalRecoil(), 0.25f));
            return true;
        }

        return false;
    }

    @Override
    public boolean tryToReload(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean unload, boolean isRightHand) {
        if (player.equals(Minecraft.getInstance().player)) {
            if (!item.isUnloadingOrReloading(player, itemStack)) {
                if (item.isJammed(player, itemStack) && unload) {
                    return false;
                }
                if (unload) {
                    final int currentAmmo = item.getCurrentAmmo(player, itemStack);
                    if (currentAmmo <= 0) {
                        return false;
                    }
                } else {
                    if (player.getInventory().countItem(item.getGunProperties().getAmmo()) <= 0) {
                        return false;
                    }
                }
                ZombiePlague3.net().sendToServer(new ZPGunActionPacket(player.getId(), unload ? ZPGunActionPacket.UNLOAD : ZPGunActionPacket.RELOAD, isRightHand));
                if (item.getGunProperties().getReloadSound() != null) {
                    this.localSound(item.getGunProperties().getReloadSound().get(), item);
                }
                ZPClientCallbacksManager.INSTANCE.triggerReloadingStart(player, item, itemStack, new ZPClientCallbacks.ZPGunReloadStartCallback.GunFXData(isRightHand));
                if (unload) {
                    item.setUnloading(player, itemStack, true);
                } else {
                    item.setReloading(player, itemStack, true);
                }
                item.setClientSyncCooldown(itemStack, Math.max(ZPClientGunClientTickProcessing.TICK_SYNC_INTERVAL, item.getGunProperties().getReloadTime()));
                return true;
            }
        } else {
            if (item.getGunProperties().getReloadSound() != null) {
                this.globalSound(item.getGunProperties().getReloadSound().get(), item, player);
            }
            ZPClientCallbacksManager.INSTANCE.triggerReloadingStart(player, item, itemStack, new ZPClientCallbacks.ZPGunReloadStartCallback.GunFXData(isRightHand));
            return true;
        }

        return false;
    }

    private void localSound(SoundEvent soundEvent, @NotNull ZPBaseGun item) {
        SimpleSoundInstance sound = SimpleSoundInstance.forLocalAmbience(soundEvent, 1.0f, 1.0F);
        ZPUtility.sounds().play(sound);
    }

    private void globalSound(SoundEvent soundEvent, @NotNull ZPBaseGun item, @NotNull Entity entity) {
        ZPPositionedSound sound = new ZPPositionedSound(soundEvent, SoundSource.MASTER, (float) (double) item.getGunProperties().getDamage() * 16.0f, 1.0f, entity.position().toVector3f(), 0L);
        ZPUtility.sounds().play(sound);
    }

    @Override
    public void onTickInventory(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected, boolean offHand) {
        if (item.getCurrentShootCooldown(pEntity, pStack) > 0) {
            item.setCurrentShootCooldown(pEntity, pStack, item.getCurrentShootCooldown(pEntity, pStack) - 1);
        }

        if (item.isUnloadingOrReloading(pEntity, pStack)) {
            item.setCurrentTimeBeforeShoot(pEntity, pStack, 10);
        } else {
            if (item.getCurrentTimeBeforeShoot(pEntity, pStack) > 0) {
                item.setCurrentTimeBeforeShoot(pEntity, pStack, item.getCurrentTimeBeforeShoot(pEntity, pStack) - 1);
            }
        }

        if (!pIsSelected && !offHand) {
            item.setCurrentTimeBeforeShoot(pEntity, pStack, 10);
            item.setCurrentReloadCooldown(pEntity, pStack, 0);
            item.setReloading(pEntity, pStack, false);
            item.setUnloading(pEntity, pStack, false);
        }
    }
}