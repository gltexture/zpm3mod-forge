package ru.gltexture.zpm3.assets.guns.processing.logic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3i;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.processing.bullet.VirtualBullet;
import ru.gltexture.zpm3.assets.guns.processing.input.ZPClientGunClientTickProcessing;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPBulletBloodFXPacket;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPBulletHitPacket;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPBulletTracePacket;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPGunActionPacket;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.crosshair.ZPClientCrosshairRecoilManager;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.engine.sound.ZPPositionedSound;

public abstract class ZPDefaultGunLogicFunctions {
    // CLIENT
    @OnlyIn(Dist.CLIENT) public static boolean CLIENT_SHUTTER_ANIMATED_GUN_SHOT(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
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
                        ZPDefaultGunLogicFunctions.localSound(item.getGunProperties().getFireSound().get(), item);
                    }
                    float recoil = item.getGunProperties().getClientVerticalRecoil();
                    if (item.getGunProperties().getHeldType().equals(ZPBaseGun.GunProperties.HeldType.RIFLE)) {
                        if (!isRightHand || !player.getOffhandItem().isEmpty()) {
                            recoil *= 1.5f;
                        }
                    }
                    final float recoilStrength = ZPClientCrosshairRecoilManager.setVerticalRecoil(recoil);
                    item.setCurrentShootCooldown(player, itemStack, shootCooldownNominal);
                    ZPClientCallbacksManager.INSTANCE.triggerGunShots(player, item, itemStack, new ZPClientCallbacks.ZPGunShotCallback.GunFXData(isRightHand, recoilStrength, 0.25f));
                    item.setCurrentAmmo(player, itemStack, item.getCurrentAmmo(player, itemStack) - 1);
                    item.setClientSyncCooldown(itemStack, item.getCurrentAmmo(player, itemStack) <= 0 ? 0 : ZPClientGunClientTickProcessing.TICK_SYNC_INTERVAL);
                    item.setCurrentTimeBeforeReload(player, itemStack, 10);
                } else {
                    if (!item.isJammed(player, itemStack)) {
                        if (item.getGunProperties().getAmmo() != null && player.getInventory().countItem(item.getGunProperties().getAmmo()) > 0) {
                            if (item.getCurrentTimeBeforeReload(player, itemStack) > 0 || gunLogicProcessor.tryToReload(level, player, item, itemStack, false, isRightHand)) {
                                return false;
                            }
                        }
                    }
                    ZPDefaultGunLogicFunctions.localSound(item.emptyAmmoSound(), item);
                    ZPClientCallbacksManager.INSTANCE.triggerGunShots(player, item, itemStack, new ZPClientCallbacks.ZPGunShotCallback.GunFXData(isRightHand, -1.0f, -1.0f));
                    item.setCurrentShootCooldown(player, itemStack, 4);
                }
                return true;
            }
        } else {
            if (item.getGunProperties().getFireSound() != null) {
                ZPDefaultGunLogicFunctions.globalSound(item.getGunProperties().getFireSound().get(), item, player);
            }
            ZPClientCallbacksManager.INSTANCE.triggerGunShots(player, item, itemStack, new ZPClientCallbacks.ZPGunShotCallback.GunFXData(isRightHand, item.getGunProperties().getClientVerticalRecoil(), 0.25f));
            return true;
        }
        return false;
    }
    @OnlyIn(Dist.CLIENT) public static boolean CLIENT_DEFAULT_SHOT(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
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
                        ZPDefaultGunLogicFunctions.localSound(item.getGunProperties().getFireSound().get(), item);
                    }
                    float recoil = item.getGunProperties().getClientVerticalRecoil();
                    if (item.getGunProperties().getHeldType().equals(ZPBaseGun.GunProperties.HeldType.RIFLE)) {
                        if (!isRightHand || !player.getOffhandItem().isEmpty()) {
                            recoil *= 1.5f;
                        }
                    }
                    final float recoilStrength = ZPClientCrosshairRecoilManager.setVerticalRecoil(recoil);
                    item.setCurrentShootCooldown(player, itemStack, shootCooldownNominal);
                    ZPClientCallbacksManager.INSTANCE.triggerGunShots(player, item, itemStack, new ZPClientCallbacks.ZPGunShotCallback.GunFXData(isRightHand, recoilStrength, 0.25f));
                    item.setCurrentAmmo(player, itemStack, item.getCurrentAmmo(player, itemStack) - 1);
                    item.setClientSyncCooldown(itemStack, item.getCurrentAmmo(player, itemStack) <= 0 ? 0 : ZPClientGunClientTickProcessing.TICK_SYNC_INTERVAL);
                    item.setCurrentTimeBeforeReload(player, itemStack, 10);
                } else {
                    if (!item.isJammed(player, itemStack)) {
                        if (item.getGunProperties().getAmmo() != null && player.getInventory().countItem(item.getGunProperties().getAmmo()) > 0) {
                            if (item.getCurrentTimeBeforeReload(player, itemStack) > 0 || gunLogicProcessor.tryToReload(level, player, item, itemStack, false, isRightHand)) {
                                return false;
                            }
                        }
                    }
                    ZPDefaultGunLogicFunctions.localSound(item.emptyAmmoSound(), item);
                    ZPClientCallbacksManager.INSTANCE.triggerGunShots(player, item, itemStack, new ZPClientCallbacks.ZPGunShotCallback.GunFXData(isRightHand, -1.0f, -1.0f));
                    item.setCurrentShootCooldown(player, itemStack, 4);
                }
                return true;
            }
        } else {
            if (item.getGunProperties().getFireSound() != null) {
                ZPDefaultGunLogicFunctions.globalSound(item.getGunProperties().getFireSound().get(), item, player);
            }
            ZPClientCallbacksManager.INSTANCE.triggerGunShots(player, item, itemStack, new ZPClientCallbacks.ZPGunShotCallback.GunFXData(isRightHand, item.getGunProperties().getClientVerticalRecoil(), 0.25f));
            return true;
        }

        return false;
    }

    @OnlyIn(Dist.CLIENT) public static boolean CLIENT_SHUTTER_ANIMATED_GUN_RELOAD(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean unload, boolean isRightHand) {
        if (player.equals(Minecraft.getInstance().player)) {
            if (item.getCurrentTimeBeforeReload(player, itemStack) > 0) {
                return false;
            }
            final int currentAmmo = item.getCurrentAmmo(player, itemStack);
            final int ammoBeforeLoad = item.getAmmoBeforeReload(player, itemStack);
            if (!ZPDefaultGunLogicFunctions.isAnythingReloading(player)) {
                if (item.isJammed(player, itemStack) && unload) {
                    return false;
                }
                if (unload) {
                    if (currentAmmo <= 0) {
                        return false;
                    }
                } else {
                    if (currentAmmo >= item.getGunProperties().getMaxAmmo() || (item.getGunProperties().getAmmo() != null && player.getInventory().countItem(item.getGunProperties().getAmmo()) <= 0)) {
                        return false;
                    }
                }
                ZombiePlague3.net().sendToServer(new ZPGunActionPacket(player.getId(), unload ? ZPGunActionPacket.UNLOAD : ZPGunActionPacket.RELOAD, isRightHand));
                if (item.getGunProperties().getReloadSound() != null) {
                    ZPDefaultGunLogicFunctions.localSound(item.getGunProperties().getReloadSound().get(), item);
                }
                ZPClientCallbacksManager.INSTANCE.triggerReloadingStart(player, item, itemStack, new ZPClientCallbacks.ZPGunReloadStartCallback.GunFXData(isRightHand));
                if (unload) {
                    item.setUnloading(player, itemStack, true);
                } else {
                    item.setReloading(player, itemStack, true);
                }
                item.setAmmoBeforeReload(player, itemStack, currentAmmo);
                item.setClientSyncCooldown(itemStack, Math.max(ZPClientGunClientTickProcessing.TICK_SYNC_INTERVAL, item.getGunProperties().getReloadTime()));
                item.setCurrentReloadCooldown(player, itemStack, 1);
                return true;
            } else if (currentAmmo != ammoBeforeLoad) {
                item.setCurrentTimeBeforeReload(player, itemStack, 10);
                ZombiePlague3.net().sendToServer(new ZPGunActionPacket(player.getId(), ZPGunActionPacket.RELOAD_STOP, isRightHand));
                return true;
            }
        } else {
            if (item.getGunProperties().getReloadSound() != null) {
                ZPDefaultGunLogicFunctions.globalSound(item.getGunProperties().getReloadSound().get(), item, player);
            }
            ZPClientCallbacksManager.INSTANCE.triggerReloadingStart(player, item, itemStack, new ZPClientCallbacks.ZPGunReloadStartCallback.GunFXData(isRightHand));
            return true;
        }

        return false;
    }

    public static boolean isAnythingReloading(@NotNull Player player) {
        @Nullable final ItemStack itemStackInRightHand = player.getMainHandItem();
        @Nullable final ItemStack itemStackInLeftHand = player.getOffhandItem();

        if (itemStackInRightHand.getItem() instanceof ZPBaseGun baseGun) {
            if (baseGun.isUnloadingOrReloading(player, itemStackInRightHand)) {
                return true;
            }
        }

        if (itemStackInLeftHand.getItem() instanceof ZPBaseGun baseGun) {
            if (baseGun.isUnloadingOrReloading(player, itemStackInLeftHand)) {
                return true;
            }
        }

        return false;
    }

    @OnlyIn(Dist.CLIENT) public static boolean CLIENT_DEFAULT_RELOAD(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean unload, boolean isRightHand) {
        if (player.equals(Minecraft.getInstance().player)) {
            if (!ZPDefaultGunLogicFunctions.isAnythingReloading(player)) {
                if (item.getCurrentTimeBeforeReload(player, itemStack) > 0) {
                    return false;
                }
                if (item.isJammed(player, itemStack) && unload) {
                    return false;
                }
                if (unload) {
                    final int currentAmmo = item.getCurrentAmmo(player, itemStack);
                    if (currentAmmo <= 0) {
                        return false;
                    }
                } else {
                    if (item.getGunProperties().getAmmo() != null && player.getInventory().countItem(item.getGunProperties().getAmmo()) <= 0) {
                        return false;
                    }
                }
                ZombiePlague3.net().sendToServer(new ZPGunActionPacket(player.getId(), unload ? ZPGunActionPacket.UNLOAD : ZPGunActionPacket.RELOAD, isRightHand));
                if (item.getGunProperties().getReloadSound() != null) {
                    ZPDefaultGunLogicFunctions.localSound(item.getGunProperties().getReloadSound().get(), item);
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
                ZPDefaultGunLogicFunctions.globalSound(item.getGunProperties().getReloadSound().get(), item, player);
            }
            ZPClientCallbacksManager.INSTANCE.triggerReloadingStart(player, item, itemStack, new ZPClientCallbacks.ZPGunReloadStartCallback.GunFXData(isRightHand));
            return true;
        }

        return false;
    }

    @OnlyIn(Dist.CLIENT) public static void CLIENT_DEFAULT_SHUTTER_ANIMATED_GUN_TICK(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected, boolean offHand) {
        int shootCd = item.getCurrentShootCooldown(pEntity, pStack);
        if (shootCd > 0) {
            item.setCurrentShootCooldown(pEntity, pStack, shootCd - 1);
        }
        int timeBeforeReload = item.getCurrentTimeBeforeReload(pEntity, pStack);
        if (timeBeforeReload > 0) {
            item.setCurrentTimeBeforeReload(pEntity, pStack, timeBeforeReload - 1);
        }

        final ZPBaseGun.GunProperties props = item.getGunProperties();
        final int reloadCd = item.getCurrentReloadCooldown(pEntity, pStack);

        if (reloadCd > 0) {
            final int ammoBeforeReload = item.getAmmoBeforeReload(pEntity, pStack);
            final int currentAmmo = item.getCurrentAmmo(pEntity, pStack);

            if (!item.isJammed(pEntity, pStack) && ammoBeforeReload != currentAmmo) {
                ZPDefaultGunLogicFunctions.localSound(ZPSounds.shell_insert.get(), item);
            }
            item.setAmmoBeforeReload(pEntity, pStack, currentAmmo);

            if (!item.isUnloadingOrReloading(pEntity, pStack)) {
                if (props.getReloadSound() != null) {
                    ZPDefaultGunLogicFunctions.localSound(props.getReloadSound().get(), item);
                }
                item.setCurrentReloadCooldown(pEntity, pStack, 0);
            }
        }

        if (item.isUnloadingOrReloading(pEntity, pStack)) {
            item.setCurrentTimeBeforeShoot(pEntity, pStack, 10);
        } else {
            int currentShootTime = item.getCurrentTimeBeforeShoot(pEntity, pStack);
            if (currentShootTime > 0) {
                item.setCurrentTimeBeforeShoot(pEntity, pStack, currentShootTime - 1);
            }
        }

        if (!pIsSelected && !offHand) {
            item.setCurrentTimeBeforeShoot(pEntity, pStack, 10);
            item.setCurrentReloadCooldown(pEntity, pStack, 0);
            item.setReloading(pEntity, pStack, false);
            item.setUnloading(pEntity, pStack, false);
        }
    }
    @OnlyIn(Dist.CLIENT) public static void CLIENT_DEFAULT_TICK(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected, boolean offHand) {
        int shootCd = item.getCurrentShootCooldown(pEntity, pStack);
        if (shootCd > 0) {
            item.setCurrentShootCooldown(pEntity, pStack, shootCd - 1);
        }
        int timeBeforeReload = item.getCurrentTimeBeforeReload(pEntity, pStack);
        if (timeBeforeReload > 0) {
            item.setCurrentTimeBeforeReload(pEntity, pStack, timeBeforeReload - 1);
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

    // SERVER
    public static boolean SERVER_DEFAULT_SHOT(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
        final int shootCooldownNominal = item.getGunProperties().getShootCooldown();
        final int currentAmmo = item.getCurrentAmmo(player, itemStack);

        if (!item.isUnloadingOrReloading(player, itemStack) && item.getCurrentShootCooldown(player, itemStack) <= 0 && !item.isJammed(player, itemStack)) {
            Vector3f startPos = new Vector3f(player.position().toVector3f().add(0.0f, player.getEyeHeight(), 0.0f));
            float inaccuracy = item.getGunProperties().getInaccuracy();
            if (item.getGunProperties().getHeldType().equals(ZPBaseGun.GunProperties.HeldType.RIFLE)) {
                if (!isRightHand || !player.getOffhandItem().isEmpty()) {
                    inaccuracy *= 2.0f;
                }
            }
            VirtualBullet virtualBullet = new VirtualBullet(player, startPos, inaccuracy, item.getGunProperties().getDamage(), 256.0f);
            virtualBullet.simulate();
            VirtualBullet.VirtualBulletHitResult virtualBulletHitResult = virtualBullet.getVirtualBulletHitResult();
            if (virtualBulletHitResult != null) {
                Vector3f pos = virtualBulletHitResult.hitPoint();
                Vector3i blockPos = virtualBulletHitResult.blockPos() == null ? new Vector3i(0) : new Vector3i(virtualBulletHitResult.blockPos().x, virtualBulletHitResult.blockPos().y, virtualBulletHitResult.blockPos().z);
                ServerLevel serverLevel = (ServerLevel) player.level();
                final Vector3f motion = new Vector3f(startPos.x, startPos.y, startPos.z).sub(new Vector3f(pos.x, pos.y, pos.z)).normalize();
                ZombiePlague3.net().sendToDimensionRadius(new ZPBulletHitPacket(blockPos.x, blockPos.y, blockPos.z, motion.x, motion.y, motion.z, pos.x, pos.y, pos.z, virtualBulletHitResult.bulletHitType().getFlag()), serverLevel.dimension(), new Vec3(pos), ZPConstants.BULLET_HIT_PACKET_RANGE);
                ZombiePlague3.net().sendToPlayer(new ZPBulletTracePacket(pos.x, pos.y, pos.z, isRightHand), (ServerPlayer) player);
                if (ZPConstants.SEND_PACKET_ABOUT_BULLET_ENTITY_HIT && virtualBulletHitResult.bulletHitType().equals(VirtualBullet.VirtualBulletHitType.ENTITY)) {
                    if (virtualBulletHitResult.damagedEntity() != null && virtualBulletHitResult.damagedEntity().isAlive()) {
                        ZombiePlague3.net().sendToDimensionRadius(new ZPBulletBloodFXPacket(
                                virtualBulletHitResult.hitPoint().x, virtualBulletHitResult.hitPoint().y, virtualBulletHitResult.hitPoint().z,
                                -motion.x, -motion.y, -motion.z, virtualBulletHitResult.wasHeadshot()), serverLevel.dimension(), new Vec3(pos), 32.0f);
                    }
                }
            }
            if (!player.isCreative()) {
                item.setCurrentAmmo(player, itemStack, currentAmmo - 1);
            }
            itemStack.hurtAndBreak(1, player, e -> {
                e.broadcastBreakEvent(isRightHand ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            });
            final float durability = (float) itemStack.getDamageValue() / itemStack.getMaxDamage();
            final float jamChance = (float) Math.pow(durability, Math.E * 4.0f);
            if (ZPRandom.getRandom().nextFloat() <= jamChance) {
                item.setJammed(player, itemStack, true);
            }
            item.setCurrentShootCooldown(player, itemStack, Math.max(shootCooldownNominal - 1, 0));
            return true;
        }

        return false;
    }
    public static boolean SERVER_DEFAULT_SHUTTER_ANIMATED_GUN_SHOT(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand, int bullets) {
        final int shootCooldownNominal = item.getGunProperties().getShootCooldown();
        final int currentAmmo = item.getCurrentAmmo(player, itemStack);

        if (!item.isUnloadingOrReloading(player, itemStack) && item.getCurrentShootCooldown(player, itemStack) <= 0 && !item.isJammed(player, itemStack)) {
            Vector3f startPos = new Vector3f(player.position().toVector3f().add(0.0f, player.getEyeHeight(), 0.0f));
            for (int i = 0; i < bullets; i++) {
                VirtualBullet virtualBullet = new VirtualBullet(player, startPos, item.getGunProperties().getInaccuracy(), item.getGunProperties().getDamage(), bullets > 1 ? 32.0f : 256.0f);
                virtualBullet.simulate();
                VirtualBullet.VirtualBulletHitResult virtualBulletHitResult = virtualBullet.getVirtualBulletHitResult();
                if (virtualBulletHitResult != null) {
                    Vector3f pos = virtualBulletHitResult.hitPoint();
                    Vector3i blockPos = virtualBulletHitResult.blockPos() == null ? new Vector3i(0) : new Vector3i(virtualBulletHitResult.blockPos().x, virtualBulletHitResult.blockPos().y, virtualBulletHitResult.blockPos().z);
                    ServerLevel serverLevel = (ServerLevel) player.level();
                    final Vector3f motion = new Vector3f(startPos.x, startPos.y, startPos.z).sub(new Vector3f(pos.x, pos.y, pos.z)).normalize();
                    ZombiePlague3.net().sendToDimensionRadius(new ZPBulletHitPacket(blockPos.x, blockPos.y, blockPos.z, motion.x, motion.y, motion.z, pos.x, pos.y, pos.z, virtualBulletHitResult.bulletHitType().getFlag()), serverLevel.dimension(), new Vec3(pos), ZPConstants.BULLET_HIT_PACKET_RANGE);
                    ZombiePlague3.net().sendToPlayer(new ZPBulletTracePacket(pos.x, pos.y, pos.z, isRightHand), (ServerPlayer) player);
                    if (ZPConstants.SEND_PACKET_ABOUT_BULLET_ENTITY_HIT && virtualBulletHitResult.bulletHitType().equals(VirtualBullet.VirtualBulletHitType.ENTITY)) {
                        if (virtualBulletHitResult.damagedEntity() != null && virtualBulletHitResult.damagedEntity().isAlive()) {
                            ZombiePlague3.net().sendToDimensionRadius(new ZPBulletBloodFXPacket(
                                    virtualBulletHitResult.hitPoint().x, virtualBulletHitResult.hitPoint().y, virtualBulletHitResult.hitPoint().z,
                                    -motion.x, -motion.y, -motion.z, virtualBulletHitResult.wasHeadshot()), serverLevel.dimension(), new Vec3(pos), 32.0f);
                        }
                    }
                }
            }
            if (!player.isCreative()) {
                item.setCurrentAmmo(player, itemStack, currentAmmo - 1);
            }
            itemStack.hurtAndBreak(1, player, e -> {
                e.broadcastBreakEvent(isRightHand ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            });
            final float durability = (float) itemStack.getDamageValue() / itemStack.getMaxDamage();
            final float jamChance = (float) Math.pow(durability, Math.E * 4.0f);
            if (ZPRandom.getRandom().nextFloat() <= jamChance) {
                item.setJammed(player, itemStack, true);
            }
            item.setCurrentShootCooldown(player, itemStack, Math.max(shootCooldownNominal - 1, 0));
            return true;
        }

        return false;
    }

    public static boolean SERVER_DEFAULT_RELOAD(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean unload, boolean isRightHand) {
        final int reloadTimeNominal = item.getGunProperties().getReloadTime();

        if (!ZPDefaultGunLogicFunctions.isAnythingReloading(player)) {
            if (unload) {
                final int currentAmmo = item.getCurrentAmmo(player, itemStack);
                if (currentAmmo <= 0) {
                    return false;
                }
            } else {
                if (item.getGunProperties().getAmmo() != null && player.getInventory().countItem(item.getGunProperties().getAmmo()) <= 0) {
                    return false;
                }
            }
            item.setCurrentReloadCooldown(player, itemStack, reloadTimeNominal);
            if (unload) {
                item.setUnloading(player, itemStack, true);
            } else {
                item.setReloading(player, itemStack, true);
            }
            return true;
        }

        return false;
    }
    public static boolean SERVER_DEFAULT_SHUTTER_ANIMATED_GUN_RELOAD(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean unload, boolean isRightHand) {
        final int reloadTimeNominal = ZPDefaultGunLogicFunctions.reloadTimeNominalShutterAnimatedGun(item, player, itemStack, unload);
        final int currentAmmo = item.getCurrentAmmo(player, itemStack);
        final int ammoBeforeLoad = item.getAmmoBeforeReload(player, itemStack);

        if (!ZPDefaultGunLogicFunctions.isAnythingReloading(player)) {
            if (unload) {
                if (currentAmmo <= 0) {
                    return false;
                }
            } else {
                if (item.getGunProperties().getAmmo() != null && player.getInventory().countItem(item.getGunProperties().getAmmo()) <= 0) {
                    return false;
                }
            }
            item.setCurrentReloadCooldown(player, itemStack, reloadTimeNominal);
            if (unload) {
                item.setUnloading(player, itemStack, true);
            } else {
                item.setReloading(player, itemStack, true);
            }
            return true;
        } else if (currentAmmo != ammoBeforeLoad) {
            item.setUnloading(player, itemStack, false);
            item.setReloading(player, itemStack, false);
            item.setCurrentReloadCooldown(player, itemStack, 0);
        }

        return false;
    }

    public static void SERVER_DEFAULT_TICK(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected, boolean offHand) {
        if (item.getCurrentShootCooldown(pEntity, pStack) > 0) {
            item.setCurrentShootCooldown(pEntity, pStack, item.getCurrentShootCooldown(pEntity, pStack) - 1);
        }

        if (item.getCurrentReloadCooldown(pEntity, pStack) > 0) {
            item.setCurrentReloadCooldown(pEntity, pStack, item.getCurrentReloadCooldown(pEntity, pStack) - 1);
            if (item.getCurrentReloadCooldown(pEntity, pStack) == 0) {
                if (!item.isJammed(pEntity, pStack)) {
                    ZPDefaultGunLogicFunctions.reloadActionDefault(pStack, pLevel, item, pEntity);
                }
                item.setReloading(pEntity, pStack, false);
                item.setUnloading(pEntity, pStack, false);
                item.setJammed(pEntity, pStack, false);
            }
        }

        if (!pIsSelected && !offHand) {
            item.setCurrentReloadCooldown(pEntity, pStack, 0);
            item.setReloading(pEntity, pStack, false);
            item.setUnloading(pEntity, pStack, false);
        }
    }
    public static void SERVER_DEFAULT_SHUTTER_ANIMATED_GUN_TICK(@NotNull IGunLogicProcessor gunLogicProcessor, @NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected, boolean offHand) {
        if (item.getCurrentShootCooldown(pEntity, pStack) > 0) {
            item.setCurrentShootCooldown(pEntity, pStack, item.getCurrentShootCooldown(pEntity, pStack) - 1);
        }

        boolean isUnloading = item.isUnloading(pEntity, pStack);
        final int reloadTimeNominal = ZPDefaultGunLogicFunctions.reloadTimeNominalShutterAnimatedGun(item, pEntity, pStack, isUnloading);
        final int currentReloadCooldown = item.getCurrentReloadCooldown(pEntity, pStack);
        if (currentReloadCooldown > 0) {
            item.setCurrentReloadCooldown(pEntity, pStack, item.getCurrentReloadCooldown(pEntity, pStack) - 1);
            if (!item.isJammed(pEntity, pStack)) {
                if (currentReloadCooldown <= (reloadTimeNominal - item.getGunProperties().getReloadTime()) && currentReloadCooldown % item.getGunProperties().getReloadTime() == 0) {
                    if (!ZPDefaultGunLogicFunctions.reloadActionDefaultShutterAnimatedGun(pStack, pLevel, item, pEntity)) {
                        item.setCurrentReloadCooldown(pEntity, pStack, 0);
                    } else {
                        if (pEntity instanceof Player player) {
                            pLevel.playSound(player, player.getOnPos(), ZPSounds.shell_insert.get(), SoundSource.AMBIENT, 1.0f, 1.0f);
                        }
                    }
                }
            }

            if (item.getCurrentReloadCooldown(pEntity, pStack) == 0) {
                item.setReloading(pEntity, pStack, false);
                item.setUnloading(pEntity, pStack, false);
                item.setJammed(pEntity, pStack, false);
            }
        }

        if (!pIsSelected && !offHand) {
            item.setCurrentReloadCooldown(pEntity, pStack, 0);
            item.setReloading(pEntity, pStack, false);
            item.setUnloading(pEntity, pStack, false);
        }
    }







    private static int reloadTimeNominalShutterAnimatedGun(@NotNull ZPBaseGun item, @NotNull Entity player, @NotNull ItemStack itemStack, boolean unLoad) {
        int multiplier = unLoad ? item.getCurrentAmmo(player, itemStack) : (item.getGunProperties().getMaxAmmo() - item.getCurrentAmmo(player, itemStack));
        return (item.getGunProperties().getReloadTime()) * multiplier + item.getGunProperties().getReloadTime();
    }

    private static boolean reloadActionDefaultShutterAnimatedGun(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity) {
        final int ammoCurrent = item.getCurrentAmmo(pEntity, pStack);
        if (pEntity instanceof Player player) {
            final int maxAmmo = item.getGunProperties().getMaxAmmo();
            final Item ammoItem = item.getGunProperties().getAmmo();
            if (item.isReloading(pEntity, pStack)) {
                if (ammoCurrent >= maxAmmo || (ammoItem != null && player.getInventory().countItem(ammoItem) <= 0)) {
                    return false;
                }
                item.setCurrentAmmo(pEntity, pStack, ammoItem == null || player.isCreative() ? maxAmmo : (ammoCurrent + ZPUtility.entity().consumeItemFromInventory(player.getInventory(), ammoItem, 1)));
                return true;
            } else if (item.isUnloading(pEntity, pStack)) {
                if (ammoCurrent <= 0) {
                    return false;
                }
                if (ammoItem != null) {
                    final ItemStack stack = new ItemStack(ammoItem, 1);
                    if (!player.getInventory().add(stack)) {
                        player.drop(stack, true);
                    }
                }
                item.setCurrentAmmo(pEntity, pStack, ammoCurrent - 1);
                return true;
            }
        }
        return false;
    }

    private static void reloadActionDefault(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity) {
        if (pEntity instanceof Player player) {
            Item item1 = item.getGunProperties().getAmmo();
            if (item.isReloading(pEntity, pStack)) {
                item.setCurrentAmmo(pEntity, pStack, item1 == null || player.isCreative() ? item.getGunProperties().getMaxAmmo() : ZPUtility.entity().consumeItemFromInventory(player.getInventory(), item1, item.getGunProperties().getMaxAmmo()));
            } else if (item.isUnloading(pEntity, pStack)) {
                final int ammoCurrent = item.getCurrentAmmo(pEntity, pStack);
                if (item1 != null) {
                    final ItemStack stack = new ItemStack(item1, ammoCurrent);
                    if (!player.getInventory().add(stack)) {
                        player.drop(stack, true);
                    }
                }
                item.setCurrentAmmo(pEntity, pStack, 0);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void localSound(SoundEvent soundEvent, @NotNull ZPBaseGun item) {
        SimpleSoundInstance sound = SimpleSoundInstance.forLocalAmbience(soundEvent, 0.95F + ZPRandom.instance.randomFloat(0.075f), 1.0f);
        ZPUtility.sounds().play(sound);
    }

    @OnlyIn(Dist.CLIENT)
    private static void globalSound(SoundEvent soundEvent, @NotNull ZPBaseGun item, @NotNull Entity entity) {
        entity.level().playLocalSound(entity.getOnPos(), soundEvent, SoundSource.MASTER, (float) (double) item.getGunProperties().getDamage() * 16.0f, 0.95F + ZPRandom.instance.randomFloat(0.075f), true);
    }
}