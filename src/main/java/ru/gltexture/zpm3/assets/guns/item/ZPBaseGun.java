package ru.gltexture.zpm3.assets.guns.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.engine.instances.items.ZPItem;
import ru.gltexture.zpm3.engine.nbt.itemstack.ZPItemStackNBT;

import java.util.function.Supplier;

public abstract class ZPBaseGun extends ZPItem {
    private final GunProperties gunProperties;

    public ZPBaseGun(@NotNull Properties pProperties, @NotNull GunProperties gunProperties) {
        super(pProperties.stacksTo(1));
        this.gunProperties = gunProperties;
    }

    @Override
    public final void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        boolean offHand = false;
        if (pEntity instanceof Player player) {
            if (player.getItemInHand(InteractionHand.OFF_HAND).equals(pStack)) {
                offHand = true;
            }
        }
        if (!pLevel.isClientSide()) {
            if (this.getServerGunLogic() != null) {
                this.getServerGunLogic().onTickInventory(pStack, pLevel, this, pEntity, pSlotId, pIsSelected, offHand);
            }
        } else {
            if (this.getClientGunLogic() != null) {
                this.getClientGunLogic().onTickInventory(pStack, pLevel, this, pEntity, pSlotId, pIsSelected, offHand);
            }
        }
    }

    public @NotNull GunProperties getGunProperties() {
        return this.gunProperties;
    }

    @OnlyIn(Dist.CLIENT)
    public @NotNull ZPItemStackNBT getClientNBT(@NotNull ItemStack stack) {
        return new ZPItemStackNBT(stack);
    }

    public @NotNull ZPItemStackNBT getServerNBT(@NotNull ItemStack stack) {
        return new ZPItemStackNBT(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public abstract IGunLogicProcessor getClientGunLogic();

    public abstract IGunLogicProcessor getServerGunLogic();

    public static class GunProperties {
        private int shootCooldown;
        private int reloadTime;

        private int maxAmmo;

        private float inaccuracy;
        private float clientRecoil;

        private boolean isAuto;

        private @Nullable Supplier<@NotNull SoundEvent> fireSound;
        private @Nullable Supplier<@NotNull SoundEvent> reloadSound;

        public GunProperties() {
            this.setDefaults();
        }

        private void setDefaults() {
            this.shootCooldown = 4;
            this.reloadTime = 40;

            this.maxAmmo = 10;

            this.inaccuracy = 1.0f;
            this.clientRecoil = 1.0f;

            this.isAuto = false;
            this.fireSound = null;
        }

        public int getShootCooldown() {
            return this.shootCooldown;
        }

        public GunProperties setShootCooldown(int shootCooldown) {
            this.shootCooldown = shootCooldown;
            return this;
        }

        public int getReloadTime() {
            return this.reloadTime;
        }

        public GunProperties setReloadTime(int reloadTime) {
            this.reloadTime = reloadTime;
            return this;
        }

        public int getMaxAmmo() {
            return this.maxAmmo;
        }

        public GunProperties setMaxAmmo(int maxAmmo) {
            this.maxAmmo = maxAmmo;
            return this;
        }

        public float getInaccuracy() {
            return this.inaccuracy;
        }

        public GunProperties setInaccuracy(float inaccuracy) {
            this.inaccuracy = inaccuracy;
            return this;
        }

        public float getClientVerticalRecoil() {
            return this.clientRecoil;
        }

        public GunProperties setClientRecoil(float clientRecoil) {
            this.clientRecoil = clientRecoil;
            return this;
        }

        public boolean isAuto() {
            return this.isAuto;
        }

        public GunProperties setAuto(boolean auto) {
            isAuto = auto;
            return this;
        }

        public @Nullable Supplier<@NotNull SoundEvent> getFireSound() {
            return this.fireSound;
        }

        public GunProperties setFireSound(@Nullable Supplier<@NotNull SoundEvent> fireSound) {
            this.fireSound = fireSound;
            return this;
        }

        public @Nullable Supplier<@NotNull SoundEvent> getReloadSound() {
            return this.reloadSound;
        }

        public GunProperties setReloadSound(@Nullable Supplier<@NotNull SoundEvent> reloadSound) {
            this.reloadSound = reloadSound;
            return this;
        }
    }
}
