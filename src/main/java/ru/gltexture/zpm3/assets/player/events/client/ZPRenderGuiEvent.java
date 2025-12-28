package ru.gltexture.zpm3.assets.player.events.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.engine.mixins.ext.IZPPlayerMixinExt;

@OnlyIn(Dist.CLIENT)
public class ZPRenderGuiEvent implements ZPEventClass {
    public ZPRenderGuiEvent() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.FORGE;
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics gg = event.getGuiGraphics();
        @Nullable Player player = Minecraft.getInstance().player;
        if (player instanceof IZPPlayerMixinExt ext && ZPConstants.SHOW_PING_ON_SCREEN) {
            final int ping = ext.getPing();
            String text = ext.getPing() + "ms";
            int screenWidth = event.getWindow().getGuiScaledWidth();
            int screenHeight = event.getWindow().getScreenHeight();
            int x = 2;
            int y = 2;
            int color = 0xFF00FF00;
            if (ping >= 120.0f) {
                color = 0xFFFF0000;
            } else if (ping >= 60.0f) {
                color = 0xFFFFFF00;
            }
            gg.drawString(mc.font, text, x, y, color, true);
        }
    }
}
