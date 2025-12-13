package ru.gltexture.zpm3.assets.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.commands.zones.ZPFlagZones;
import ru.gltexture.zpm3.assets.commands.zones.events.ZPLevelSaveReadEvents;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.debug.events.ZPRenderStuffEvent;
import ru.gltexture.zpm3.assets.loot_cases.registry.ZPLootTablesReader;
import ru.gltexture.zpm3.assets.net_pack.packets.ZPSendGlobalSettings_StoC;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.asset.ZPAsset;
import ru.gltexture.zpm3.engine.core.asset.ZPAssetData;
import ru.gltexture.zpm3.engine.events.ZPEventClass;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ZPCommandsAsset extends ZPAsset {
    public ZPCommandsAsset(@NotNull ZPAssetData zpAssetData) {
        super(zpAssetData);
    }

    public ZPCommandsAsset() {
    }

    @Override
    public void commonSetup() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientSetup() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientDestroy() {

    }

    @Override
    public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
        //mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("debug", "ru.gltexture.zpm3.assets.debug.mixins.impl"),
        //        new ZombiePlague3.IMixinEntry.MixinClass("client.ZPCameraMixin", ZPSide.CLIENT),
        //        new ZombiePlague3.IMixinEntry.MixinClass("client.ZPInputMixin", ZPSide.CLIENT));
    }

    public static void addNewLineToDraw(@NotNull ZPRenderStuffEvent.LineRequest lineRequest) {
    }

    @Override
    public void initializeAsset(ZombiePlague3.@NotNull IAssetEntry assetEntry) {
        assetEntry.addEventClass(ZPCommandsEvent.class);
        assetEntry.addEventClass(ZPLevelSaveReadEvents.class);
    }

    @Override
    public void preCommonInitializeAsset() {

    }

    @Override
    public void postCommonInitializeAsset() {

    }

    public static class ZPCommandsEvent implements ZPEventClass {

        public ZPCommandsEvent() {
        }

        @Override
        public @NotNull ZPSide getSide() {
            return ZPSide.COMMON;
        }

        @Override
        public @NotNull Mod.EventBusSubscriber.Bus getBus() {
            return Mod.EventBusSubscriber.Bus.FORGE;
        }

        @SubscribeEvent
        public static void onCommand(RegisterCommandsEvent event) {
            event.getDispatcher().register(
                    Commands.literal("zp3")
                            .then(Commands.literal("refreshConfigs")
                                    .executes(ctx -> {
                                        ServerPlayer player = ctx.getSource().getPlayerOrException();
                                        if (player.hasPermissions(4)) {
                                            ZombiePlague3.processConfigurations();
                                            ZombiePlague3.net().sendToAll(ZPSendGlobalSettings_StoC.create());
                                            ctx.getSource().sendSuccess(() -> Component.literal("Success!"), false);
                                            return 1;
                                        }
                                        return 0;
                                    })
                            )
                            .then(Commands.literal("refreshLootTables")
                                    .executes(ctx -> {
                                        ServerPlayer player = ctx.getSource().getPlayerOrException();
                                        if (player.hasPermissions(4)) {
                                            ZPLootTablesReader.READ_FILES();
                                            ctx.getSource().sendSuccess(() -> Component.literal("Success!"), false);
                                            return 1;
                                        }
                                        return 0;
                                    })
                            )
                            .then(Commands.literal("zoneCreate")
                                    .then(Commands.argument("id", StringArgumentType.string())
                                            .then(Commands.argument("x1", IntegerArgumentType.integer())
                                                    .then(Commands.argument("y1", IntegerArgumentType.integer())
                                                            .then(Commands.argument("z1", IntegerArgumentType.integer())
                                                                    .then(Commands.argument("x2", IntegerArgumentType.integer())
                                                                            .then(Commands.argument("y2", IntegerArgumentType.integer())
                                                                                    .then(Commands.argument("z2", IntegerArgumentType.integer())
                                                                                            .executes(ctx -> {
                                                                                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                                                                ServerLevel level = (ServerLevel) player.level();
                                                                                                if (!player.hasPermissions(3)) {
                                                                                                    return 0;
                                                                                                }
                                                                                                String id = StringArgumentType.getString(ctx, "id");
                                                                                                int x1 = IntegerArgumentType.getInteger(ctx, "x1");
                                                                                                int y1 = IntegerArgumentType.getInteger(ctx, "y1");
                                                                                                int z1 = IntegerArgumentType.getInteger(ctx, "z1");
                                                                                                int x2 = IntegerArgumentType.getInteger(ctx, "x2");
                                                                                                int y2 = IntegerArgumentType.getInteger(ctx, "y2");
                                                                                                int z2 = IntegerArgumentType.getInteger(ctx, "z2");

                                                                                                ZPFlagZones.Zone zone = new ZPFlagZones.Zone(id, x1, y1, z1, x2, y2, z2, new HashSet<>());
                                                                                                ZPFlagZones.INSTANCE.addNewZone(level, zone);

                                                                                                ctx.getSource().sendSuccess(() -> Component.literal("Zone " + id + " created!"), false);
                                                                                                return 1;
                                                                                            })
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                            .then(Commands.literal("zoneRemove")
                                    .then(Commands.argument("id", StringArgumentType.string())
                                            .executes(ctx -> {
                                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                ServerLevel level = (ServerLevel) player.level();
                                                if (!player.hasPermissions(3)) {
                                                    return 0;
                                                }
                                                String id = StringArgumentType.getString(ctx, "id");

                                                boolean removed = ZPFlagZones.INSTANCE.removeZone(level, id);
                                                if (removed) {
                                                    ctx.getSource().sendSuccess(() -> Component.literal("Zone " + id + " Removed!"), false);
                                                    return 1;
                                                }
                                                ctx.getSource().sendFailure(Component.literal("Zone " + id + " not found!"));
                                                return 0;
                                            })
                                    )
                            )
                            .then(Commands.literal("zonesList")
                                    .executes(ctx -> {
                                        ServerPlayer player = ctx.getSource().getPlayerOrException();
                                        if (!player.hasPermissions(3)) {
                                            return 0;
                                        }
                                        ServerLevel level = (ServerLevel) player.level();
                                        Collection<ZPFlagZones.Zone> zones = ZPFlagZones.INSTANCE.getZonesInfo(level);
                                        if (zones == null || zones.isEmpty()) {
                                            ctx.getSource().sendSuccess(() -> Component.literal("Empty!"), false);
                                            return 0;
                                        }
                                        for (ZPFlagZones.Zone zone : zones) {
                                            ctx.getSource().sendSuccess(() -> Component.literal(zone.uniqueId() + " [" + zone.startX() + "," + zone.startY() + "," + zone.startZ() + "] - [" + zone.endX() + "," + zone.endY() + "," + zone.endZ() + "] Flags: " + zone.flags()), false);
                                        }
                                        return 1;
                                    })
                            )
                            .then(Commands.literal("zoneEraseFlags")
                                    .then(Commands.argument("id", StringArgumentType.string())
                                            .executes(ctx -> {
                                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                if (!player.hasPermissions(3)) {
                                                    return 0;
                                                }
                                                ServerLevel level = (ServerLevel) player.level();
                                                String id = StringArgumentType.getString(ctx, "id");
                                                boolean updated = ZPFlagZones.INSTANCE.replaceFlags(level, id, new HashSet<>());
                                                if (updated) {
                                                    ctx.getSource().sendSuccess(() -> Component.literal("Flags of " + id + " updated!"), false);
                                                    return 1;
                                                }
                                                ctx.getSource().sendFailure(Component.literal("Zone " + id + " not found!"));
                                                return 0;
                                            })
                                    )
                            )
                            .then(Commands.literal("zoneAddFlag")
                                    .then(Commands.argument("id", StringArgumentType.string())
                                            .then(Commands.argument("flag", StringArgumentType.string())
                                                    .suggests((ctx, builder) -> {
                                                        for (ZPFlagZones.Zone.AvailableFlags f : ZPFlagZones.Zone.AvailableFlags.values()) {
                                                            builder.suggest(f.name());
                                                        }
                                                        return builder.buildFuture();
                                                    })
                                                    .executes(ctx -> {
                                                        ServerPlayer player = ctx.getSource().getPlayerOrException();
                                                        if (!player.hasPermissions(3)) {
                                                            return 0;
                                                        }
                                                        ServerLevel level = (ServerLevel) player.level();
                                                        String id = StringArgumentType.getString(ctx, "id");
                                                        String flagStr = StringArgumentType.getString(ctx, "flag");
                                                        ZPFlagZones.Zone.AvailableFlags flag;
                                                        try {
                                                            flag = ZPFlagZones.Zone.AvailableFlags.valueOf(flagStr);
                                                        } catch (IllegalArgumentException e) {
                                                            ctx.getSource().sendFailure(Component.literal("The flag " + flagStr + " doesn't exist. Try /zp3 zoneFlagsList"));
                                                            return 0;
                                                        }
                                                        Set<ZPFlagZones.Zone.AvailableFlags> flags = ZPFlagZones.INSTANCE.getFlags(level, id);
                                                        if (flags == null) {
                                                            ctx.getSource().sendFailure(Component.literal("Zone " + id + " not found!"));
                                                            return 0;
                                                        }
                                                        flags = new HashSet<>(flags);
                                                        flags.add(flag);
                                                        if (!ZPFlagZones.INSTANCE.replaceFlags(level, id, flags)) {
                                                            ctx.getSource().sendSuccess(() -> Component.literal("Fail"), false);
                                                            return 0;
                                                        }
                                                        ctx.getSource().sendSuccess(() -> Component.literal("Flag " + flag.name() + " added to zone " + id + "!"), false);
                                                        return 1;
                                                    })
                                            )
                                    )
                            )
                            .then(Commands.literal("zoneFlagsList")
                                    .executes(ctx -> {
                                        ServerPlayer player = ctx.getSource().getPlayerOrException();
                                        if (!player.hasPermissions(3)) {
                                            return 0;
                                        }
                                        for (ZPFlagZones.Zone.AvailableFlags availableFlags : ZPFlagZones.Zone.AvailableFlags.values()) {
                                            ctx.getSource().sendSuccess(() -> Component.literal(availableFlags.name() + ", "), false);
                                        }
                                        return 1;
                                    })
                            )
            );
        }
    }
}
