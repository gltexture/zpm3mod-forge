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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.engine.keybind.ZPBaseKeyBindings;
import ru.gltexture.zpm3.engine.nbt.ZPTagID;

@OnlyIn(Dist.CLIENT)
public class ZPClientGunInputProcessing implements IZPGunInputProcessor {
    public static ZPClientGunInputProcessing INSTANCE = new ZPClientGunInputProcessing();
    private static boolean rightClickedFirst;
    private static boolean tryToShootOnlyRight;
    public static boolean shouldBlockRightHandAttack;

    private static boolean uiShouldBlockMouseScan; //BUG FIX
    private static int dualShootTickCounter = 0;
    private static boolean lastShotLeft = false;

    protected ZPClientGunInputProcessing() {
    }

    public void tick(@NotNull Minecraft minecraft) {
        if (ZPClientGunInputProcessing.dualShootTickCounter > 0) {
            ZPClientGunInputProcessing.dualShootTickCounter--;
        }
    }

    public static boolean showPistolIndicator(@NotNull Minecraft minecraft) {
        if (ZPClientGunInputProcessing.isAnythingReloading(Minecraft.getInstance())) {
            return false;
        }
        @Nullable Player player = minecraft.player;
        if (player != null) {
            @Nullable final ItemStack itemStackInRightHand = player.getMainHandItem();
            @Nullable final ItemStack itemStackInLeftHand = player.getOffhandItem();

            if (!(itemStackInRightHand.getItem() instanceof ZPBaseGun) && itemStackInLeftHand.getItem() instanceof ZPBaseGun) {
                return minecraft.mouseHandler.isRightPressed();
            }
        }
        return ZPBaseKeyBindings.reloadKey.isDown();
    }

    public static boolean showLeftMouseIndicator(@NotNull Minecraft minecraft) {
        if (ZPClientGunInputProcessing.showPistolIndicator(minecraft)) {
            return true;
        }
        if (ZPClientGunInputProcessing.isAnythingReloading(Minecraft.getInstance())) {
            return false;
        }
        if (!ZPClientGunInputProcessing.areBothItemsAreGuns(minecraft)) {
            return minecraft.mouseHandler.isRightPressed();
        }
        return ZPBaseKeyBindings.reloadKey.isDown();
    }

    public static boolean showRightMouseIndicator(@NotNull Minecraft minecraft) {
        if (ZPClientGunInputProcessing.isAnythingReloading(Minecraft.getInstance())) {
            return false;
        }
        return ZPBaseKeyBindings.reloadKey.isDown();
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
                if (baseGun.getClientNBT(itemStackInRightHand).getTagInt(ZPTagID.GUN_RELOAD_COOLDOWN_TAG) > 0) {
                    return true;
                }
            }

            if (itemStackInLeftHand.getItem() instanceof ZPBaseGun baseGun) {
                if (baseGun.getClientNBT(itemStackInLeftHand).getTagInt(ZPTagID.GUN_RELOAD_COOLDOWN_TAG) > 0) {
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

                final boolean rightIsGun = this.isGun(itemStackInRightHand);
                final boolean leftIsGun = this.isGun(itemStackInLeftHand);
                ZPClientGunInputProcessing.tryToShootOnlyRight = leftIsGun && ZPClientGunInputProcessing.triesToShotOnlyRightGun(minecraft);

                boolean rightPressed = minecraft.mouseHandler.isRightPressed();
                boolean leftPressed = minecraft.mouseHandler.isLeftPressed();
                boolean bothAreGuns = rightIsGun && leftIsGun;
                ZPClientGunInputProcessing.shouldBlockRightHandAttack = bothAreGuns || (leftIsGun && rightPressed);

                if ((rightPressed || leftPressed) && ZPClientGunInputProcessing.uiShouldBlockMouseScan) {
                    rightPressed = false;
                    leftPressed = false;
                } else {
                    ZPClientGunInputProcessing.uiShouldBlockMouseScan = false;
                }

                boolean rightFire = leftPressed;
                boolean leftFire = rightPressed;
                boolean bothArePressed = rightFire && leftFire;

                if (bothAreGuns) {
                    final boolean temp = rightFire;
                    rightFire = leftFire;
                    leftFire = temp;
                } else {
                    if (leftIsGun && !ZPClientGunInputProcessing.tryToShootOnlyRight) {
                        leftFire = false;
                    }
                }

                if (bothArePressed && bothAreGuns) {
                    ZPBaseGun gunRight = (ZPBaseGun) itemStackInRightHand.getItem();
                    ZPBaseGun gunLeft = (ZPBaseGun) itemStackInLeftHand.getItem();

                    int cd = gunRight.getGunProperties().getShootCooldown();
                    if (cd == gunLeft.getGunProperties().getShootCooldown()) {

                        boolean success = false;
                        if (ZPClientGunInputProcessing.dualShootTickCounter <= 0) {
                            if (ZPClientGunInputProcessing.lastShotLeft) {
                                if (gunRight.getClientGunLogic().tryToShoot(level, player, gunRight, itemStackInRightHand, true)) {
                                    ZPClientGunInputProcessing.lastShotLeft = false;
                                    success = true;
                                }
                            } else {
                                if (gunLeft.getClientGunLogic().tryToShoot(level, player, gunLeft, itemStackInLeftHand, false)) {
                                    ZPClientGunInputProcessing.lastShotLeft = true;
                                    success = true;
                                }
                            }

                            if (success) {
                                ZPClientGunInputProcessing.dualShootTickCounter = Math.max(cd / 2, 2);
                            }
                        }
                        return;
                    }
                }

                if (ZPBaseKeyBindings.reloadKey.isDown() && !ZPClientGunInputProcessing.isAnythingReloading(minecraft)) {
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
                        ZPBaseGun baseGun = (ZPBaseGun) itemStackInRightHand.getItem();
                        baseGun.getClientGunLogic().tryToReload(level, player, baseGun, itemStackInRightHand, true);
                        return;
                    }
                    if (reloadLeftGun) {
                        ZPBaseGun baseGun = (ZPBaseGun) itemStackInLeftHand.getItem();
                        baseGun.getClientGunLogic().tryToReload(level, player, baseGun, itemStackInLeftHand, false);
                        return;
                    }
                }

                if (ZPClientGunInputProcessing.dualShootTickCounter <= 0) {
                    if (rightFire) {
                        if (rightIsGun) {
                            ZPBaseGun baseGun = (ZPBaseGun) itemStackInRightHand.getItem();
                            baseGun.getClientGunLogic().tryToShoot(level, player, baseGun, itemStackInRightHand, true);
                            ZPClientGunInputProcessing.dualShootTickCounter = Math.max(ZPClientGunInputProcessing.dualShootTickCounter, 1);
                        }
                    }
                }
                if (ZPClientGunInputProcessing.dualShootTickCounter <= 0) {
                    if (leftFire) {
                        if (leftIsGun) {
                            ZPBaseGun baseGun = (ZPBaseGun) itemStackInLeftHand.getItem();
                            baseGun.getClientGunLogic().tryToShoot(level, player, baseGun, itemStackInLeftHand, false);
                            ZPClientGunInputProcessing.dualShootTickCounter = Math.max(ZPClientGunInputProcessing.dualShootTickCounter, 1);
                        }
                    }
                }
            } else {
                ZPClientGunInputProcessing.uiShouldBlockMouseScan = true;
            }
        }
    }

    private static boolean triesToShotOnlyRightGun(@NotNull Minecraft minecraft) {
        boolean rightPressed = minecraft.mouseHandler.isRightPressed();
        boolean leftPressed = minecraft.mouseHandler.isLeftPressed();

        if (rightPressed) {
            if (!leftPressed) {
                ZPClientGunInputProcessing.rightClickedFirst = true;
            }
        } else {
            ZPClientGunInputProcessing.rightClickedFirst = false;
        }

        return ZPClientGunInputProcessing.rightClickedFirst && leftPressed;
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

    private boolean isGun(@Nullable ItemStack stack) {
        return stack != null && stack.getItem() instanceof ZPBaseGun;
    }
}
