package ru.gltexture.zpm3.assets.guns.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.common.init.ZPSounds;
import ru.gltexture.zpm3.assets.guns.mixins.ext.IZPItemStackClientDataExt;
import ru.gltexture.zpm3.assets.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunParticlesFX;
import ru.gltexture.zpm3.engine.instances.items.ZPItem;
import ru.gltexture.zpm3.engine.nbt.ZPAbstractNBTClass;
import ru.gltexture.zpm3.engine.nbt.ZPTagID;
import ru.gltexture.zpm3.engine.nbt.itemstack.ZPItemStackNBT;
import ru.gltexture.zpm3.engine.service.Pair;

import java.util.function.Supplier;

public abstract class ZPBaseGun extends ZPItem {
    private final GunProperties gunProperties;

    public ZPBaseGun(@NotNull Properties pProperties, @NotNull GunProperties gunProperties) {
        super(pProperties.durability(gunProperties.getDurability()));
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
                if (this.getGunClientSyncCooldown(pStack) <= 0) {
                    this.makeHardSync(pStack);
                } else {
                    this.subClientSync(pStack);
                }
                this.getClientGunLogic().onTickInventory(pStack, pLevel, this, pEntity, pSlotId, pIsSelected, offHand);
            }
        }
    }

    public @NotNull SoundEvent emptyAmmoSound() {
        return ZPSounds.empty.get();
    }

    public @NotNull GunProperties getGunProperties() {
        return this.gunProperties;
    }

    public boolean isGunAmmoFull(@NotNull Entity entity, @NotNull ItemStack itemStack) {
        return this.getCurrentAmmo(entity, itemStack) == this.getGunProperties().getMaxAmmo();
    }

    @OnlyIn(Dist.CLIENT)
    public @NotNull ZPAbstractNBTClass<ItemStack> getClientData(@NotNull ItemStack stack) {
        return ((IZPItemStackClientDataExt) (Object) stack).getClientData();
    }

    public @NotNull ZPAbstractNBTClass<ItemStack> getServerData(@NotNull ItemStack stack) {
        return new ZPItemStackNBT(stack, "zpgun_s");
    }

    public int getCurrentAmmo(@NotNull Entity entity, @NotNull ItemStack itemStack) {
        if (entity.level().isClientSide()) {
            return this.getClientData(itemStack).getTagInt(ZPTagID.GUN_AMMO_INSIDE_TAG);
        } else {
            return this.getServerData(itemStack).getTagInt(ZPTagID.GUN_AMMO_INSIDE_TAG);
        }
    }

    public int getCurrentShootCooldown(@NotNull Entity entity, @NotNull ItemStack itemStack) {
        if (entity.level().isClientSide()) {
            return this.getClientData(itemStack).getTagInt(ZPTagID.GUN_SHOOT_COOLDOWN_TAG);
        } else {
            return this.getServerData(itemStack).getTagInt(ZPTagID.GUN_SHOOT_COOLDOWN_TAG);
        }
    }

    public int getCurrentReloadCooldown(@NotNull Entity entity, @NotNull ItemStack itemStack) {
        if (entity.level().isClientSide()) {
            return this.getClientData(itemStack).getTagInt(ZPTagID.GUN_RELOAD_COOLDOWN_TAG);
        } else {
            return this.getServerData(itemStack).getTagInt(ZPTagID.GUN_RELOAD_COOLDOWN_TAG);
        }
    }

    public int getAmmoBeforeReload(@NotNull Entity entity, @NotNull ItemStack itemStack) {
        if (entity.level().isClientSide()) {
            return this.getClientData(itemStack).getTagInt(ZPTagID.GUN_AMMO_BEFORE_RELOAD);
        } else {
            return this.getServerData(itemStack).getTagInt(ZPTagID.GUN_AMMO_BEFORE_RELOAD);
        }
    }

    public boolean isReloading(@NotNull Entity entity, @NotNull ItemStack itemStack) {
        if (entity.level().isClientSide()) {
            return this.getClientData(itemStack).getTagBoolean(ZPTagID.GUN_IS_RELOADING_TAG);
        } else {
            return this.getServerData(itemStack).getTagBoolean(ZPTagID.GUN_IS_RELOADING_TAG);
        }
    }

    public boolean isUnloading(@NotNull Entity entity, @NotNull ItemStack itemStack) {
        if (entity.level().isClientSide()) {
            return this.getClientData(itemStack).getTagBoolean(ZPTagID.GUN_IS_UNLOADING_TAG);
        } else {
            return this.getServerData(itemStack).getTagBoolean(ZPTagID.GUN_IS_UNLOADING_TAG);
        }
    }

    public boolean isUnloadingOrReloading(@NotNull Entity entity, @NotNull ItemStack itemStack) {
        return this.isUnloading(entity, itemStack) || this.isReloading(entity, itemStack);
    }

    public void setCurrentAmmo(@NotNull Entity entity, @NotNull ItemStack itemStack, int value) {
        if (entity.level().isClientSide()) {
            this.getClientData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_AMMO_INSIDE_TAG, value));
        } else {
            this.getServerData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_AMMO_INSIDE_TAG, value));
        }
    }

    public void setCurrentShootCooldown(@NotNull Entity entity, @NotNull ItemStack itemStack, int value) {
        if (entity.level().isClientSide()) {
            this.getClientData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_SHOOT_COOLDOWN_TAG, value));
        } else {
            this.getServerData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_SHOOT_COOLDOWN_TAG, value));
        }
    }

    public void setCurrentReloadCooldown(@NotNull Entity entity, @NotNull ItemStack itemStack, int value) {
        if (entity.level().isClientSide()) {
            this.getClientData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_RELOAD_COOLDOWN_TAG, value));
        } else {
            this.getServerData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_RELOAD_COOLDOWN_TAG, value));
        }
    }

    public void setReloading(@NotNull Entity entity, @NotNull ItemStack itemStack, boolean value) {
        if (entity.level().isClientSide()) {
            this.getClientData(itemStack).putTagBoolean(Pair.of(ZPTagID.GUN_IS_RELOADING_TAG, value));
        } else {
            this.getServerData(itemStack).putTagBoolean(Pair.of(ZPTagID.GUN_IS_RELOADING_TAG, value));
        }
    }

    public void setUnloading(@NotNull Entity entity, @NotNull ItemStack itemStack, boolean value) {
        if (entity.level().isClientSide()) {
            this.getClientData(itemStack).putTagBoolean(Pair.of(ZPTagID.GUN_IS_UNLOADING_TAG, value));
        } else {
            this.getServerData(itemStack).putTagBoolean(Pair.of(ZPTagID.GUN_IS_UNLOADING_TAG, value));
        }
    }

    public void setAmmoBeforeReload(@NotNull Entity entity, @NotNull ItemStack itemStack, int value) {
        if (entity.level().isClientSide()) {
            this.getClientData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_AMMO_BEFORE_RELOAD, value));
        } else {
            this.getServerData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_AMMO_BEFORE_RELOAD, value));
        }
    }

    public boolean isJammed(@NotNull Entity entity, @NotNull ItemStack itemStack) {
        return this.getServerData(itemStack).getTagBoolean(ZPTagID.GUN_IS_JAMMED_TAG);
    }

    public void setJammed(@NotNull Entity entity, @NotNull ItemStack itemStack, boolean value) {
        this.getServerData(itemStack).putTagBoolean(Pair.of(ZPTagID.GUN_IS_JAMMED_TAG, value));
    }



    @OnlyIn(Dist.CLIENT)
    public void setCurrentTimeBeforeShoot(@NotNull Entity entity, @NotNull ItemStack itemStack, int value) {
        this.getClientData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_CL_TIME_BEFORE_SHOOT, value));
    }

    @OnlyIn(Dist.CLIENT)
    public void setCurrentTimeBeforeReload(@NotNull Entity entity, @NotNull ItemStack itemStack, int value) {
        this.getClientData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_CL_TIME_BEFORE_RELOAD, value));
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentTimeBeforeReload(@NotNull Entity entity, @NotNull ItemStack itemStack) {
        return this.getClientData(itemStack).getTagInt(ZPTagID.GUN_CL_TIME_BEFORE_RELOAD);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentTimeBeforeShoot(@NotNull Entity entity, @NotNull ItemStack itemStack) {
        return this.getClientData(itemStack).getTagInt(ZPTagID.GUN_CL_TIME_BEFORE_SHOOT);
    }

    @OnlyIn(Dist.CLIENT)
    public int getGunClientSyncCooldown(@NotNull ItemStack itemStack) {
        return this.getClientData(itemStack).getTagInt(ZPTagID.GUN_CL_SYNC_COOLDOWN);
    }



    @OnlyIn(Dist.CLIENT)
    public void setClientSyncCooldown(@NotNull ItemStack itemStack, int ticks) {
        this.getClientData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_CL_SYNC_COOLDOWN, ticks));
    }

    @OnlyIn(Dist.CLIENT)
    public void subClientSync(@NotNull ItemStack itemStack) {
        this.getClientData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_CL_SYNC_COOLDOWN, this.getGunClientSyncCooldown(itemStack) - 1));
    }

    @OnlyIn(Dist.CLIENT)
    public void makeHardSync(@NotNull ItemStack itemStack) {
        this.getClientData(itemStack).putTagInt(Pair.of(ZPTagID.GUN_AMMO_INSIDE_TAG, this.getServerData(itemStack).getTagInt(ZPTagID.GUN_AMMO_INSIDE_TAG)));
        this.getClientData(itemStack).putTagBoolean(Pair.of(ZPTagID.GUN_IS_UNLOADING_TAG, this.getServerData(itemStack).getTagBoolean(ZPTagID.GUN_IS_UNLOADING_TAG)));
        this.getClientData(itemStack).putTagBoolean(Pair.of(ZPTagID.GUN_IS_RELOADING_TAG, this.getServerData(itemStack).getTagBoolean(ZPTagID.GUN_IS_RELOADING_TAG)));
    }

    @Override
    public boolean shouldOverrideMultiplayerNbt() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract IGunLogicProcessor getClientGunLogic();
    public abstract IGunLogicProcessor getServerGunLogic();

    public static class GunProperties {
        private final @NotNull AnimationData animationData;
        private @Nullable IZPGunParticlesFX.ParticlesEmitterPack customShotParticlesEmitter;
        private int shootCooldown;
        private int reloadTime;
        private int damage;
        private HeldType heldType;

        private int maxAmmo;

        private float inaccuracy;
        private float clientRecoil;

        private boolean isAuto;
        private int durability;

        private @Nullable Supplier<@NotNull SoundEvent> fireSound;
        private @Nullable Supplier<@NotNull SoundEvent> reloadSound;
        private final @Nullable Item ammo;

        public GunProperties(@Nullable Item ammo, @NotNull HeldType heldType) {
            this.animationData = new AnimationData(false);
            this.setDefaults();
            this.heldType = heldType;
            this.ammo = ammo;
        }

        private void setDefaults() {
            this.shootCooldown = 4;
            this.reloadTime = 40;
            this.durability = 100;

            this.customShotParticlesEmitter = null;

            this.maxAmmo = 10;
            this.damage = 4;

            this.inaccuracy = 1.0f;
            this.clientRecoil = 1.0f;

            this.isAuto = false;
            this.fireSound = null;
        }

        public @NotNull AnimationData getAnimationData() {
            return this.animationData;
        }

        public HeldType getHeldType() {
            return this.heldType;
        }

        public GunProperties setHeldType(HeldType heldType) {
            this.heldType = heldType;
            return this;
        }

        public @Nullable IZPGunParticlesFX.ParticlesEmitterPack getCustomShotParticlesEmitter() {
            return this.customShotParticlesEmitter;
        }

        public GunProperties setCustomShotParticlesEmitter(@Nullable IZPGunParticlesFX.ParticlesEmitterPack customShotParticlesEmitter) {
            this.customShotParticlesEmitter = customShotParticlesEmitter;
            return this;
        }

        public float getClientRecoil() {
            return this.clientRecoil;
        }

        public int getDurability() {
            return this.durability;
        }

        public GunProperties setDurability(int durability) {
            this.durability = durability;
            return this;
        }

        public @Nullable Item getAmmo() {
            return this.ammo;
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

        public int getDamage() {
            return this.damage;
        }

        public GunProperties setDamage(int damage) {
            this.damage = damage;
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

        public enum HeldType {
            PISTOL,
            RIFLE
        }

        public static class AnimationData {
            private boolean hasShutterAnimation;
            private SoundEvent shutterSound;
            private ShutterAnimationType shutterAnimationType;
            private float shutterAnimationSpeed;

            public AnimationData(boolean hasShutterAnimation) {
                this.hasShutterAnimation = hasShutterAnimation;
                this.shutterAnimationType = null;
                this.shutterSound = ZPSounds.shotgun_shutter.get();
                this.shutterAnimationSpeed = 4.0f;
            }

            public float getShutterAnimationSpeed() {
                return this.shutterAnimationSpeed;
            }

            public AnimationData setShutterAnimationSpeed(float shutterAnimationSpeed) {
                this.shutterAnimationSpeed = shutterAnimationSpeed;
                return this;
            }

            public SoundEvent getShutterSound() {
                return this.shutterSound;
            }

            public AnimationData setShutterSound(SoundEvent shutterSound) {
                this.shutterSound = shutterSound;
                return this;
            }

            public boolean isHasShutterAnimation() {
                return this.hasShutterAnimation;
            }

            public ShutterAnimationType getShutterAnimationType() {
                return this.shutterAnimationType;
            }

            public AnimationData setHasShutterAnimation(boolean hasShutterAnimation, @Nullable ShutterAnimationType shutterAnimationType) {
                this.hasShutterAnimation = hasShutterAnimation;
                this.shutterAnimationType = shutterAnimationType;
                return this;
            }

            public enum ShutterAnimationType {
                SHOTGUN,
                CLASSIC
            }
        }
    }
}
