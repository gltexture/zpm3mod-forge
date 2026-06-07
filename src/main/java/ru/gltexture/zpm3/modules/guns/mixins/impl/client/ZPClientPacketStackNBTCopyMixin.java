package ru.gltexture.zpm3.modules.guns.mixins.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.modules.guns.mixins.ext.IZPItemStackClientDataExt;

@OnlyIn(Dist.CLIENT)
@Mixin(ClientPacketListener.class)
public class ZPClientPacketStackNBTCopyMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "handleContainerSetSlot", at = @At("HEAD"), cancellable = true)
    private void onHandleContainerSetSlot(ClientboundContainerSetSlotPacket pPacket, CallbackInfo ci) {
        Player player = this.minecraft.player;
        if (player == null) {
            return;
        }
        ci.cancel();
        ItemStack newStack = pPacket.getItem();
        int slot = pPacket.getSlot();
        this.minecraft.getTutorial().onGetItem(newStack);

        if (pPacket.getContainerId() == -1) {
            if (!(this.minecraft.screen instanceof CreativeModeInventoryScreen)) {
                ItemStack oldStack = player.containerMenu.getCarried();
                this.zpm3forge$copyClientData(oldStack, newStack);
                player.containerMenu.setCarried(newStack);
            }
        } else if (pPacket.getContainerId() == -2) {
            ItemStack oldStack = player.getInventory().getItem(slot);
            this.zpm3forge$copyClientData(oldStack, newStack);
            player.getInventory().setItem(slot, newStack);
        } else {
            boolean flag = false;
            Screen screen = this.minecraft.screen;
            if (screen instanceof CreativeModeInventoryScreen creative) {
                flag = !creative.isInventoryOpen();
            }

            if (pPacket.getContainerId() == 0 && InventoryMenu.isHotbarSlot(slot)) {
                if (!newStack.isEmpty()) {
                    ItemStack oldStack = player.inventoryMenu.getSlot(slot).getItem();
                    if (oldStack.isEmpty() || oldStack.getCount() < newStack.getCount()) {
                        newStack.setPopTime(5);
                    }
                    this.zpm3forge$copyClientData(oldStack, newStack);
                }
                player.inventoryMenu.setItem(slot, pPacket.getStateId(), newStack);
            } else if (pPacket.getContainerId() == player.containerMenu.containerId && (pPacket.getContainerId() != 0 || !flag)) {
                ItemStack oldStack = player.containerMenu.getSlot(slot).getItem();
                this.zpm3forge$copyClientData(oldStack, newStack);
                player.containerMenu.setItem(slot, pPacket.getStateId(), newStack);
            }
        }
    }

    @Unique
    private void zpm3forge$copyClientData(ItemStack oldStack, ItemStack newStack) {
        if (!oldStack.isEmpty() && !newStack.isEmpty()) {
            IZPItemStackClientDataExt oldExt = (IZPItemStackClientDataExt) (Object) oldStack;
            IZPItemStackClientDataExt newExt = (IZPItemStackClientDataExt) (Object) newStack;
            newExt.zpm3forge$setClientData(oldExt.zpm3forge$getClientData());
        }
    }
}
