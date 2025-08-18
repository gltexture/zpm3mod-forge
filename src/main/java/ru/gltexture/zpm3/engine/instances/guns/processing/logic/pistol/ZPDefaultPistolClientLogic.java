package ru.gltexture.zpm3.engine.instances.guns.processing.logic.pistol;

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
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacksManager;
import ru.gltexture.zpm3.engine.client.rendering.crosshair.recoil.ZPClientCrosshairRecoilManager;
import ru.gltexture.zpm3.engine.instances.guns.ZPBaseGun;
import ru.gltexture.zpm3.engine.instances.guns.processing.logic.IGunLogicProcessor;
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

        if (shootCooldownCurrent == 0) {
            if (item.getGunProperties().getFireSound() != null) {
                @Nullable SoundEvent shootSound = item.getGunProperties().getFireSound().get();
                SimpleSoundInstance sound = SimpleSoundInstance.forLocalAmbience(shootSound, 1.0f, 1.0F);
                Minecraft.getInstance().getSoundManager().play(sound);
            }
            final float recoilStrength = ZPClientCrosshairRecoilManager.setVerticalRecoil(item.getGunProperties().getClientVerticalRecoil());
            System.out.println(recoilStrength);
            ZPClientCallbacksManager.INSTANCE.triggerGunShots(player, item, itemStack, new ZPClientCallbacks.ZPGunShotCallback.GunFXData(isRightHand, recoilStrength, 0.25f));
            item.getClientNBT(itemStack).putTagInt(Pair.of(ZPTagID.GUN_SHOOT_COOLDOWN_TAG, shootCooldownNominal));
            return true;
        }

        return false;
    }

    @Override
    public boolean tryToReload(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
        return false;
    }

    @Override
    public void onTickInventory(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        item.getClientNBT(pStack).decrementInt((e) -> e > 0, ZPTagID.GUN_SHOOT_COOLDOWN_TAG);
        item.getClientNBT(pStack).decrementInt((e) -> e > 0, ZPTagID.GUN_RELOAD_COOLDOWN_TAG);

        if (!pIsSelected) {
            item.getClientNBT(pStack).putTagInt(Pair.of(ZPTagID.GUN_RELOAD_COOLDOWN_TAG, 0));
            item.getClientNBT(pStack).putTagBoolean(Pair.of(ZPTagID.GUN_IS_RELOADING_TAG, false));
            item.getClientNBT(pStack).putTagBoolean(Pair.of(ZPTagID.GUN_IS_UNLOADING_TAG, false));
        }
    }
}