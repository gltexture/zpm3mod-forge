package ru.gltexture.zpm3.assets.guns.processing.logic.classic_rifle;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.processing.logic.IGunLogicProcessor;
import ru.gltexture.zpm3.assets.guns.processing.logic.ZPDefaultLogicFunctions;

public class ZPDefaultClassicRifleServerLogic implements IGunLogicProcessor {
    public ZPDefaultClassicRifleServerLogic() {
    }

    @Override
    public boolean tryToShoot(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean isRightHand) {
        return ZPDefaultLogicFunctions.SERVER_DEFAULT_SHUTTER_ANIMATED_GUN_SHOT(this, level, player, item, itemStack, isRightHand, 1);
    }

    @Override
    public boolean tryToReload(@NotNull Level level, @NotNull Player player, @NotNull ZPBaseGun item, @NotNull ItemStack itemStack, boolean unload, boolean isRightHand) {
        return ZPDefaultLogicFunctions.SERVER_DEFAULT_SHUTTER_ANIMATED_GUN_RELOAD(this, level, player, item, itemStack, unload, isRightHand);
    }

    @Override
    public void onTickInventory(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull ZPBaseGun item, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected, boolean offHand) {
        ZPDefaultLogicFunctions.SERVER_DEFAULT_SHUTTER_ANIMATED_GUN_TICK(this, pStack, pLevel, item, pEntity, pSlotId, pIsSelected, offHand);
    }
}