package ru.gltexture.zpm3.assets.guns.processing.logic.pistol;

import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector3i;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.processing.bullet.VirtualBullet;
import ru.gltexture.zpm3.assets.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPBulletHitPacket;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPBulletTracePacket;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPGunActionPacket;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.random.ZPRandom;
import ru.gltexture.zpm3.engine.nbt.ZPTagID;
import ru.gltexture.zpm3.engine.service.Pair;
import ru.gltexture.zpm3.engine.service.ZPUtility;

public class ZPDefaultPistolServerLogic implements IGunLogicProcessor {
    public ZPDefaultPistolServerLogic() {
    }

    @Override
    public boolean tryToShoot(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
        final int shootCooldownNominal = item.getGunProperties().getShootCooldown();
        final int currentAmmo = item.getCurrentAmmo(player, itemStack);

        if (!item.isUnloadingOrReloading(player, itemStack) && item.getCurrentShootCooldown(player, itemStack) <= 0 && !item.isJammed(player, itemStack)) {
            Vector3f startPos = new Vector3f(player.position().toVector3f().add(0.0f, player.getEyeHeight(), 0.0f));
            VirtualBullet virtualBullet = new VirtualBullet(player, startPos, 1.0f, 1.0f, 256.0f);
            virtualBullet.simulate();
            VirtualBullet.VirtualBulletHitResult virtualBulletHitResult = virtualBullet.getVirtualBulletHitResult();
            if (virtualBulletHitResult != null) {
                Vector3f pos = virtualBulletHitResult.hitPoint();
                Vector3i blockPos = virtualBulletHitResult.blockPos() == null ? new Vector3i(0) : new Vector3i(virtualBulletHitResult.blockPos().x, virtualBulletHitResult.blockPos().y, virtualBulletHitResult.blockPos().z);
                ServerLevel serverLevel = (ServerLevel) player.level();
                final Vector3f motion = new Vector3f(startPos.x, startPos.y, startPos.z).sub(new Vector3f(pos.x, pos.y, pos.z)).normalize();
                ZombiePlague3.net().sendToDimensionRadius(new ZPBulletHitPacket(blockPos.x,blockPos.y, blockPos.z, motion.x, motion.y, motion.z, pos.x, pos.y, pos.z, virtualBulletHitResult.bulletHitType().getFlag()), serverLevel.dimension(), new Vec3(pos), ZPConstants.DEFAULT_GUN_ACTION_PACKET_RANGE);
                ZombiePlague3.net().sendToPlayer(new ZPBulletTracePacket(pos.x, pos.y, pos.z, isRightHand), (ServerPlayer) player);
         //       item.setCurrentAmmo(player, itemStack, currentAmmo - 1);
                itemStack.hurtAndBreak(1, player, e -> {
                    e.broadcastBreakEvent(isRightHand ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                });
                float durability = (float) itemStack.getDamageValue() / itemStack.getMaxDamage();
                float jamChance = (float) Math.pow(durability, Math.E * 4.0f);
                if (ZPRandom.getRandom().nextFloat() <= jamChance) {
                    item.setJammed(player, itemStack, true);
                }
            }
            item.setCurrentShootCooldown(player, itemStack, Math.max(shootCooldownNominal - 1, 0));
            return true;
        }

        return false;
    }

    @Override
    public boolean tryToReload(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean unload, boolean isRightHand) {
        final int reloadTimeNominal = item.getGunProperties().getReloadTime();

        if (!item.isUnloadingOrReloading(player, itemStack)) {
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

    @Override
    public void onTickInventory(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected, boolean offHand) {
        if (item.getCurrentShootCooldown(pEntity, pStack) > 0) {
            item.setCurrentShootCooldown(pEntity, pStack, item.getCurrentShootCooldown(pEntity, pStack) - 1);
        }

        if (item.getCurrentReloadCooldown(pEntity, pStack) > 0) {
            item.setCurrentReloadCooldown(pEntity, pStack, item.getCurrentReloadCooldown(pEntity, pStack) - 1);
            if (item.getCurrentReloadCooldown(pEntity, pStack) == 0) {
                if (!item.isJammed(pEntity, pStack)) {
                    this.reloadAction(pStack, pLevel, item, pEntity);
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

    private void reloadAction(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity) {
        if (pEntity instanceof Player player) {
            if (item.isReloading(pEntity, pStack)) {
                item.setCurrentAmmo(pEntity, pStack, player.isCreative() ? item.getGunProperties().getMaxAmmo() : ZPUtility.entity().consumeItemFromInventory(player.getInventory(), item.getGunProperties().getAmmo(), item.getGunProperties().getMaxAmmo()));
            } else if (item.isUnloading(pEntity, pStack)) {
                final int ammoCurrent = item.getCurrentAmmo(pEntity, pStack);
                final ItemStack stack = new ItemStack(item.getGunProperties().getAmmo(), ammoCurrent);
                if (!player.getInventory().add(stack)) {
                    player.drop(stack, true);
                }
                item.setCurrentAmmo(pEntity, pStack, 0);
            }
        }
    }
}
