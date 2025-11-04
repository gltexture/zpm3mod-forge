package ru.gltexture.zpm3.assets.guns.rendering.basic;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import ru.gltexture.zpm3.assets.debug.imgui.DearUITRSInterface;
import ru.gltexture.zpm3.engine.client.callbacking.ZPClientCallbacks;
import ru.gltexture.zpm3.assets.guns.rendering.fx.IZPGunReloadingFX;
import ru.gltexture.zpm3.assets.guns.rendering.fx.ZPGunFXGlobalData;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;

public class ZPDefaultGunReloadingFX implements IZPGunReloadingFX {
    private final float[] reloadPrev;
    private final float[] reload;
    private final boolean[] reloadProgression;
    private static final Matrix4f IDENT_MAT = new Matrix4f().identity();

    protected ZPDefaultGunReloadingFX() {
        this.reloadPrev = new float[] {0.0f, 0.0f};
        this.reload = new float[] {0.0f, 0.0f};
        this.reloadProgression = new boolean[] {false, false};
    }

    public static ZPDefaultGunReloadingFX create() {
        return new ZPDefaultGunReloadingFX();
    }

    @Override
    public void onReloadStart(@NotNull Player player, @NotNull ZPBaseGun baseGun, @NotNull ItemStack itemStack, @NotNull ZPClientCallbacks.ZPGunReloadStartCallback.GunFXData gunFXData) {
        if (!player.equals(Minecraft.getInstance().player)) {
            return;
        }
        final int id = gunFXData.isRightHand() ? 1 : 0;
        this.reloadProgression[id] = true;
        this.reloadPrev[id] = 0;
        this.reload[id] = 0;
    }

    @Override
    public @Nullable Matrix4f getCurrentGunReloadingTransformation(boolean rightHand, float partialTicks) {
        final int id = rightHand ? 1 : 0;
        float reloadingStage = Mth.lerp(partialTicks, this.reloadPrev[id], this.reload[id]);
        reloadingStage = Math.max(reloadingStage, DearUITRSInterface.reloadProgression);

        Matrix4f matrix4f = ZPGunFXGlobalData.getGunData(rightHand).getGunReloadingTransformationTarget();
        return ZPDefaultGunReloadingFX.IDENT_MAT.lerp(matrix4f, reloadingStage, new Matrix4f());
    }

    @Override
    public @Nullable Matrix4f getCurrentArmReloadingTransformation(boolean rightHand, float partialTicks) {
        final int id = rightHand ? 1 : 0;
        float reloadingStage = Mth.lerp(partialTicks, this.reloadPrev[id], this.reload[id]);
        reloadingStage = Math.max(reloadingStage, DearUITRSInterface.reloadProgression);

        Matrix4f matrix4f = ZPGunFXGlobalData.getGunData(rightHand).getArmReloadingTransformationTarget();
        return ZPDefaultGunReloadingFX.IDENT_MAT.lerp(matrix4f, reloadingStage, new Matrix4f());
    }

    private void stopReloadingAnim(int id) {
        this.reloadProgression[id] = false;
    }

    @Override
    public void onTick(TickEvent.@NotNull Phase phase) {
        if (phase == TickEvent.Phase.START) {
            final float speed = 0.25f;
            for (int i = 0; i < 2; i++) {
                Minecraft minecraft = Minecraft.getInstance();
                Player player = minecraft.player;
                if (player != null) {
                    ItemStack itemStack = player.getItemInHand(i == 1 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
                    if (this.reloadProgression[i]) {
                        if (!(itemStack.getItem() instanceof ZPBaseGun zpBaseGun)) {
                            this.stopReloadingAnim(i);
                        } else {
                            if (!zpBaseGun.isUnloadingOrReloading(player, itemStack)) {
                                this.stopReloadingAnim(i);
                            }
                        }
                    } else if (itemStack.getItem() instanceof ZPBaseGun zpBaseGun) {
                        if (zpBaseGun.isUnloadingOrReloading(player, itemStack)) {
                            this.reloadProgression[i] = true;
                        }
                    }
                }
                this.reloadPrev[i] = this.reload[i];
                if (this.reloadProgression[i]) {
                    this.reload[i] += speed;
                    if (this.reload[i] >= 1.0f) {
                        this.reload[i] = 1.0f;
                    }
                } else {
                    this.reload[i] = Math.max(this.reload[i] - speed, 0.0f);
                }
            }
        }
    }
}
