package ru.gltexture.zpm3.modules.player.events.client;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.resource.PathPackResources;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

import java.nio.file.Path;

@OnlyIn(Dist.CLIENT)
public class ZPResourcePackEvent implements ZPEventClass {
    public ZPResourcePackEvent() {
    }

    @Override
    public @NotNull ZPSide getSide() {
        return ZPSide.CLIENT;
    }

    @Override
    public @NotNull Mod.EventBusSubscriber.Bus getBus() {
        return Mod.EventBusSubscriber.Bus.MOD;
    }

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) {
            return;
        }
        Path resourcePath = ModList.get().getModFileById(ZombiePlague3.MOD_ID()).getFile().findResource("builtin/zp_respack");
        try (PathPackResources packResources = new PathPackResources("zp_respack", true, resourcePath)) {
            Pack.Info info = new Pack.Info(
                    Component.literal("Compiled By: ")
                            .append(Component.literal("gltexture").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFAA00)).withBold(true)))
                            .append(Component.literal(" & "))
                            .append(Component.literal("AstroMaster").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00AACC)).withItalic(true))),
                    15, FeatureFlags.DEFAULT_FLAGS
            );
            event.addRepositorySource(packConsumer -> {
                packConsumer.accept(Pack.create("builtin/zp_respack", Component.literal("Zombie Plague 3 ResPack"), true, (e) -> packResources, info, PackType.CLIENT_RESOURCES, Pack.Position.TOP, false, PackSource.BUILT_IN));
            });
        }
    }
}
