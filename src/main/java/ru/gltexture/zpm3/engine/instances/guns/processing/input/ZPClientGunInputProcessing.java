package ru.gltexture.zpm3.engine.instances.guns.processing.input;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.engine.instances.guns.ZPBaseGun;

@OnlyIn(Dist.CLIENT)
public class ZPClientGunInputProcessing implements IZPGunInputProcessor {
    public static ZPClientGunInputProcessing INSTANCE = new ZPClientGunInputProcessing();

    protected ZPClientGunInputProcessing() {
    }

    @Override
    public void process(@NotNull Minecraft minecraft) {
        @Nullable Player player = minecraft.player;
        ClientLevel level = minecraft.level;
        if (level != null && player != null && minecraft.screen == null) {
            @Nullable final ItemStack itemStackInRightHand = player.getMainHandItem();
            @Nullable final ItemStack itemStackInLeftHand = player.getOffhandItem();

            final boolean rightIsGun = this.isGun(itemStackInRightHand);
            final boolean leftIsGun = this.isGun(itemStackInLeftHand);

            boolean rightPressed = minecraft.mouseHandler.isRightPressed();
            boolean leftPressed = minecraft.mouseHandler.isLeftPressed();

            boolean bothAreGuns = rightIsGun && leftIsGun;

            boolean rightFire = leftPressed;
            boolean leftFire = rightPressed;

            if (bothAreGuns) {
                final boolean temp = rightFire;
                rightFire = leftFire;
                leftFire = temp;
            }

            if (rightFire) {
                if (rightIsGun) {
                    ZPBaseGun baseGun = (ZPBaseGun) itemStackInRightHand.getItem();
                    baseGun.getClientGunLogic().tryToShoot(level, player, baseGun, itemStackInRightHand, true);
                }
            }
            if (leftFire) {
                if (leftIsGun) {
                    ZPBaseGun baseGun = (ZPBaseGun) itemStackInLeftHand.getItem();
                    baseGun.getClientGunLogic().tryToShoot(level, player, baseGun, itemStackInLeftHand, false);
                }
            }
        }
    }

    private boolean isGun(@Nullable ItemStack stack) {
        return stack != null && stack.getItem() instanceof ZPBaseGun;
    }
}
