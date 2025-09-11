package ru.gltexture.zpm3.assets.guns.processing.input;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.keybind.ZPBaseKeyBindings;

@OnlyIn(Dist.CLIENT)
public class ZPClientGunClientTickProcessing implements IZPGunInputProcessor {
    public static final int TICK_SYNC_INTERVAL = 10;

    public static ZPClientGunClientTickProcessing INSTANCE = new ZPClientGunClientTickProcessing();
    private static boolean rightClickedFirst;
    private static boolean tryToShootOnlyRight;
    public static boolean shouldBlockRightHandAttack;

    private static boolean uiShouldBlockMouseScan; //BUG FIX
    private static int dualShootTickCounter = 0;
    private static boolean lastShotLeft = false;

    private static boolean wasPressedRight = false;
    private static boolean wasPressedLeft = false;

    protected ZPClientGunClientTickProcessing() {
    }

    private static boolean unloadOrReloadIsPressed() {
        return ZPBaseKeyBindings.reloadKey.isDown() || ZPBaseKeyBindings.unloadKey.isDown();
    }

    public void tick(@NotNull Minecraft minecraft, TickEvent.Phase phase) {
        if (phase == TickEvent.Phase.START) {
            @Nullable Player player = minecraft.player;
            if (player != null) {
                if (ZPClientGunClientTickProcessing.dualShootTickCounter > 0) {
                    ZPClientGunClientTickProcessing.dualShootTickCounter--;
                }
            }
        }
    }

    public static boolean showPistolIndicator(@NotNull Minecraft minecraft) {
        return ZPClientGunClientTickProcessing.showLeftMouseIndicator(minecraft) || ZPClientGunClientTickProcessing.showRightMouseIndicator(minecraft);
    }

    public static boolean showLeftMouseIndicator(@NotNull Minecraft minecraft) {
        if (ZPClientGunClientTickProcessing.isAnythingReloading(Minecraft.getInstance())) {
            return false;
        }
        @Nullable Player player = minecraft.player;
        if (player != null) {
            @Nullable final ItemStack itemStackInRightHand = player.getMainHandItem();
            @Nullable final ItemStack itemStackInLeftHand = player.getOffhandItem();
            if (ZPClientGunClientTickProcessing.areBothItemsAreGuns(minecraft)) {
                return ZPClientGunClientTickProcessing.unloadOrReloadIsPressed();
            }
            return ZPClientGunClientTickProcessing.gun(itemStackInLeftHand) != null && minecraft.mouseHandler.isRightPressed();
        }
        return false;
    }

    public static boolean showRightMouseIndicator(@NotNull Minecraft minecraft) {
        if (ZPClientGunClientTickProcessing.isAnythingReloading(Minecraft.getInstance())) {
            return false;
        }
        @Nullable Player player = minecraft.player;
        if (player != null) {
            @Nullable final ItemStack itemStackInRightHand = player.getMainHandItem();
            @Nullable final ItemStack itemStackInLeftHand = player.getOffhandItem();
            if (ZPClientGunClientTickProcessing.unloadOrReloadIsPressed()) {
                if ((ZPClientGunClientTickProcessing.gun(itemStackInRightHand) != null && ZPClientGunClientTickProcessing.gun(itemStackInLeftHand) == null)) {
                    return false;
                }
                if ((ZPClientGunClientTickProcessing.gun(itemStackInRightHand) != null && ZPClientGunClientTickProcessing.gun(itemStackInLeftHand) != null)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean areBothItemsAreGuns(@NotNull Minecraft minecraft) {
        @Nullable Player player = minecraft.player;
        if (player != null) {
            @Nullable final ItemStack itemStackInRightHand = player.getMainHandItem();
            @Nullable final ItemStack itemStackInLeftHand = player.getOffhandItem();

            return itemStackInRightHand.getItem() instanceof ZPBaseGun && itemStackInLeftHand.getItem() instanceof ZPBaseGun;
        }

        return false;
    }

    public static boolean isAnythingReloading(@NotNull Minecraft minecraft) {
        @Nullable Player player = minecraft.player;
        if (player != null) {
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
        }

        return false;
    }

    @Override
    public void process(@NotNull Minecraft minecraft) {
        @Nullable Player player = minecraft.player;
        ClientLevel level = minecraft.level;

        if (level != null && player != null) {
            if (minecraft.screen == null && minecraft.getOverlay() == null) {
                @Nullable final ItemStack itemStackInRightHand = player.getMainHandItem();
                @Nullable final ItemStack itemStackInLeftHand = player.getOffhandItem();

                final @Nullable ZPBaseGun rightGun = ZPClientGunClientTickProcessing.gun(itemStackInRightHand);
                final @Nullable ZPBaseGun leftGun = ZPClientGunClientTickProcessing.gun(itemStackInLeftHand);
                final boolean rightIsGun = rightGun != null;
                final boolean leftIsGun = leftGun != null;

                ZPClientGunClientTickProcessing.tryToShootOnlyRight = leftIsGun && ZPClientGunClientTickProcessing.triesToShotOnlyRightGun(minecraft);
                boolean rightPressed = minecraft.mouseHandler.isRightPressed();
                boolean leftPressed = minecraft.mouseHandler.isLeftPressed();
                final boolean flagRightTemp = ZPClientGunClientTickProcessing.wasPressedRight;
                final boolean flagLeftTemp = ZPClientGunClientTickProcessing.wasPressedLeft;
                ZPClientGunClientTickProcessing.wasPressedRight = rightPressed;
                ZPClientGunClientTickProcessing.wasPressedLeft = leftPressed;
                boolean bothAreGuns = rightIsGun && leftIsGun;

                if (rightIsGun && !rightGun.getGunProperties().isAuto()) {
                    if (bothAreGuns) {
                        if (flagRightTemp) {
                            rightPressed = false;
                        }
                    } else {
                        if (flagLeftTemp) {
                            leftPressed = false;
                        }
                    }
                }
                if (leftIsGun && !leftGun.getGunProperties().isAuto()) {
                    if (bothAreGuns) {
                        if (flagLeftTemp) {
                            leftPressed = false;
                        }
                    } else {
                        if (flagRightTemp) {
                            rightPressed = false;
                        }
                    }
                }

                ZPClientGunClientTickProcessing.shouldBlockRightHandAttack = bothAreGuns || (leftIsGun && rightPressed);

                if ((rightPressed || leftPressed) && ZPClientGunClientTickProcessing.uiShouldBlockMouseScan) {
                    rightPressed = false;
                    leftPressed = false;
                } else {
                    ZPClientGunClientTickProcessing.uiShouldBlockMouseScan = false;
                }

                boolean rightFire = leftPressed;
                boolean leftFire = rightPressed;
                boolean bothArePressed = rightFire && leftFire;

                if (bothAreGuns) {
                    final boolean temp = rightFire;
                    rightFire = leftFire;
                    leftFire = temp;
                } else {
                    if (leftIsGun && !ZPClientGunClientTickProcessing.tryToShootOnlyRight) {
                        leftFire = false;
                    }
                }

                if (bothArePressed && bothAreGuns) {
                    if (rightGun.getGunProperties().isAuto() && leftGun.getGunProperties().isAuto()) {
                        int cd = rightGun.getGunProperties().getShootCooldown();
                        if (cd == leftGun.getGunProperties().getShootCooldown()) {
                            boolean success = false;
                            if (ZPClientGunClientTickProcessing.dualShootTickCounter <= 0) {
                                if (ZPClientGunClientTickProcessing.lastShotLeft) {
                                    if (rightGun.getClientGunLogic().tryToShoot(level, player, rightGun, itemStackInRightHand, true)) {
                                        ZPClientGunClientTickProcessing.lastShotLeft = false;
                                        success = true;
                                    }
                                } else {
                                    if (leftGun.getClientGunLogic().tryToShoot(level, player, leftGun, itemStackInLeftHand, false)) {
                                        ZPClientGunClientTickProcessing.lastShotLeft = true;
                                        success = true;
                                    }
                                }

                                if (success) {
                                    ZPClientGunClientTickProcessing.dualShootTickCounter = Math.max(cd / 2, 2);
                                }
                            }
                            return;
                        }
                    }
                }

                if (ZPClientGunClientTickProcessing.unloadOrReloadIsPressed() && !ZPClientGunClientTickProcessing.isAnythingReloading(minecraft)) {
                    boolean unLoad = !ZPBaseKeyBindings.reloadKey.isDown() && ZPBaseKeyBindings.unloadKey.isDown();
                    boolean reloadRightGun = false;
                    boolean reloadLeftGun = false;

                    if (bothAreGuns) {
                        if (rightPressed) {
                            reloadRightGun = true;
                        } else if (leftPressed) {
                            reloadLeftGun = true;
                        }
                    } else {
                        reloadRightGun = rightIsGun;
                        reloadLeftGun = leftIsGun;
                    }
                    if (reloadRightGun) {
                        rightGun.getClientGunLogic().tryToReload(level, player, rightGun, itemStackInRightHand, unLoad, true);
                        return;
                    }
                    if (reloadLeftGun) {
                        leftGun.getClientGunLogic().tryToReload(level, player, leftGun, itemStackInLeftHand, unLoad, false);
                        return;
                    }
                }
                {
                    boolean flag = false;
                    if (bothAreGuns) {
                        if (rightGun.getGunProperties().isAuto() && rightGun.equals(leftGun)) {
                            flag = true;
                        }
                    }
                    if (ZPClientGunClientTickProcessing.dualShootTickCounter <= 0) {
                        if (rightFire) {
                            if (rightIsGun) {
                                rightGun.getClientGunLogic().tryToShoot(level, player, rightGun, itemStackInRightHand, true);
                                ZPClientGunClientTickProcessing.dualShootTickCounter = flag ? rightGun.getGunProperties().getShootCooldown() : 0;
                                ZPClientGunClientTickProcessing.lastShotLeft = false;
                            }
                        }
                    }
                    if (ZPClientGunClientTickProcessing.dualShootTickCounter <= 0) {
                        if (leftFire) {
                            if (leftIsGun) {
                                leftGun.getClientGunLogic().tryToShoot(level, player, leftGun, itemStackInLeftHand, false);
                                ZPClientGunClientTickProcessing.dualShootTickCounter = flag ? leftGun.getGunProperties().getShootCooldown() : 0;
                                ZPClientGunClientTickProcessing.lastShotLeft = true;
                            }
                        }
                    }
                }
            } else {
                ZPClientGunClientTickProcessing.uiShouldBlockMouseScan = true;
            }
        }
    }

    private static boolean triesToShotOnlyRightGun(@NotNull Minecraft minecraft) {
        boolean rightPressed = minecraft.mouseHandler.isRightPressed();
        boolean leftPressed = minecraft.mouseHandler.isLeftPressed();

        if (rightPressed) {
            if (!leftPressed) {
                ZPClientGunClientTickProcessing.rightClickedFirst = true;
            }
        } else {
            ZPClientGunClientTickProcessing.rightClickedFirst = false;
        }

        return ZPClientGunClientTickProcessing.rightClickedFirst && leftPressed;
    }

    @SuppressWarnings("all")
    private static boolean castBlock(@NotNull Minecraft minecraft) {
        @Nullable Player player = minecraft.player;
        ClientLevel level = minecraft.level;

        if (player == null || level == null) {
            return false;
        }

        HitResult hit = player.pick(5.0D, 0.0F, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = ((BlockHitResult)hit).getBlockPos();
            BlockState state = level.getBlockState(pos);

            return state.getBlock().use(state, level, pos, player, InteractionHand.MAIN_HAND, (BlockHitResult) hit) != InteractionResult.PASS;
        }
        return false;
    }

    private static ZPBaseGun gun(@Nullable ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ZPBaseGun baseGun) {
            return baseGun;
        }
        return null;
    }
}
