package ru.gltexture.zpm3.assets.player.events.client;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPPlayerWantToPickUpItem;
import ru.gltexture.zpm3.assets.player.client.ZPClientGlobalSettings;
import ru.gltexture.zpm3.assets.player.keybind.ZPPickUpKeyBindings;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

@OnlyIn(Dist.CLIENT)
public class ZPMenuPatchEvent implements ZPEventClass {
    public ZPMenuPatchEvent() {
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
    public static void onInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof TitleScreen screen)) {
            return;
        }
        int centerX = 10;
        int baseY = screen.height / 4 + 48;
        int w = 98;
        int h = 20;

        event.addListener(Button.builder(Component.literal("ZP3 CurseForge"), btn -> openUrl("https://www.curseforge.com/")).bounds(centerX, baseY, w, h).build());
        event.addListener(Button.builder(Component.literal("ZP3 Discord"), btn -> openUrl("https://discord.gg/bb6AaU6Taw")).bounds(centerX, baseY + 24, w, h).build());
    }

    private static void openUrl(String url) {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new ConfirmLinkScreen(open -> {
                    if (open) {
                        Util.getPlatform().openUri(url);
                    }
                    mc.setScreen(null);
                }, url, true
        ));
    }
}
