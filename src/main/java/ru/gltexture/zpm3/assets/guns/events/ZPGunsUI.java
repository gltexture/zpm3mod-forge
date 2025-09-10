package ru.gltexture.zpm3.assets.guns.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL46;
import ru.gltexture.zpm3.assets.guns.item.ZPBaseGun;
import ru.gltexture.zpm3.assets.guns.processing.input.ZPClientGunClientTickProcessing;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPSimpleEventClass;

public class ZPGunsUI implements ZPSimpleEventClass<RenderGuiOverlayEvent.Post> {
    private static final ResourceLocation mouse_left = ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/ui/mouse_left.png");
    private static final ResourceLocation mouse_right = ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/ui/mouse_right.png");
    private static final ResourceLocation pistol_ind = ResourceLocation.fromNamespaceAndPath(ZombiePlague3.MOD_ID, "textures/ui/pistol_ind.png");

    public ZPGunsUI() {
    }

    @Override
    public void exec(RenderGuiOverlayEvent.@NotNull Post event) {
        GuiGraphics graphics = event.getGuiGraphics();
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) {
            return;
        }
        @Nullable Player player = mc.player;

        if (event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type()) {
            int width = event.getGuiGraphics().guiWidth();
            int height = event.getGuiGraphics().guiHeight();
            Vector2i center = new Vector2i(width / 2, height / 2);

            if (ZPClientGunClientTickProcessing.showLeftMouseIndicator(mc)) {
                this.tip(graphics, ZPGunsUI.mouse_left, new Vector2i(center.x, 24).sub(21, 16), new Vector2i(16, 16), new Vector2i(16, 16));
            }
            if (ZPClientGunClientTickProcessing.showRightMouseIndicator(mc)) {
                this.tip(graphics, ZPGunsUI.mouse_right, new Vector2i(center.x, 24).sub(-8, 16), new Vector2i(16, 16), new Vector2i(16, 16));
            }
            if (ZPClientGunClientTickProcessing.showPistolIndicator(mc)) {
                this.tip(graphics, ZPGunsUI.pistol_ind, new Vector2i(center.x, 24).sub(8, 16), new Vector2i(16, 16), new Vector2i(16, 16));
            }

            @Nullable final ItemStack itemStackInRightHand = player.getMainHandItem();
            @Nullable final ItemStack itemStackInLeftHand = player.getOffhandItem();

            if (itemStackInRightHand.getItem() instanceof ZPBaseGun zpBaseGun) {
                this.renderDataGun(mc, graphics, itemStackInRightHand, player, zpBaseGun, width - mc.font.width(zpBaseGun.getName(itemStackInLeftHand)) - 60, 60, true);
            }

            if (itemStackInLeftHand.getItem() instanceof ZPBaseGun zpBaseGun) {
                this.renderDataGun(mc, graphics, itemStackInLeftHand, player, zpBaseGun, 60, 60, false);
            }
        }
    }

    private void renderDataGun(@NotNull Minecraft mc, @NotNull GuiGraphics graphics, @NotNull ItemStack stack, @NotNull Player player, @NotNull ZPBaseGun zpBaseGun, int x, int y, boolean rightAlign) {
        final String gunName = zpBaseGun.getName(stack).getString();
        String ammo = zpBaseGun.getCurrentAmmo(player, stack) + " / " + zpBaseGun.getGunProperties().getMaxAmmo();
        int color = 0xffffff;
        if (zpBaseGun.isReloading(player, stack) && zpBaseGun.isJammed(player, stack)) {
            ammo = "[UnJamming]";
            color = 0x00ff00;
        } else if (zpBaseGun.isReloading(player, stack)) {
            ammo = "[Reloading]";
            color = 0x00ff00;
        } else if (zpBaseGun.isUnloading(player, stack)) {
            ammo = "[Unloading]";
            color = 0x00ffff;
        } else if (zpBaseGun.isJammed(player, stack)) {
            ammo = "[Jam]";
            color = 0xff0000;
        }

        graphics.drawString(mc.font, gunName, x, y, 0xffffff, true);
        int textX = x;
        if (rightAlign) {
            x += mc.font.width(gunName) - mc.font.width(ammo);
        }
        graphics.drawString(mc.font, ammo, x, y + 10, color, true);
    }

    private void tip(@NotNull GuiGraphics graphics, @NotNull ResourceLocation location, @NotNull Vector2i pos, @NotNull Vector2i offset, @NotNull Vector2i size) {
        GL46.glEnable(GL46.GL_BLEND);
        GL46.glBlendFuncSeparate(GL46.GL_ONE_MINUS_DST_COLOR, GL46.GL_ONE_MINUS_SRC_COLOR, GL46.GL_ONE, GL46.GL_ZERO);
        graphics.blit(location, pos.x, pos.y, 0, 0, offset.x, offset.y, size.x, size.y);
        GL46.glDisable(GL46.GL_BLEND);
    }

    @Override
    public @NotNull Class<RenderGuiOverlayEvent.Post> getEventType() {
        return RenderGuiOverlayEvent.Post.class;
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public Mod.EventBusSubscriber.@NotNull Bus getBus() {
        return Mod.EventBusSubscriber.Bus.MOD;
    }
}
