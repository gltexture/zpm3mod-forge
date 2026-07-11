package ru.gltexture.zpm3.modules.worldgen;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPCombatConfig;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;
import ru.gltexture.zpm3.modules.armor.init.ZPArmorItems;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityUtil;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;
import ru.gltexture.zpm3.modules.player.events.client.*;
import ru.gltexture.zpm3.modules.player.events.common.*;
import ru.gltexture.zpm3.modules.player.events.server.ZPPlayerFillBucketEvent;
import ru.gltexture.zpm3.modules.player.keybind.ZPPickUpKeyBindings;

import java.util.Arrays;
import java.util.function.Supplier;

public class ZPWorldGenModule extends ZPModule {
    public ZPWorldGenModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPWorldGenModule() {
    }

    @Override
    public void fml_commonSetupEvent() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void fml_clientSetupEvent() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void clientShutDown() {

    }

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        ZPUtility.sides().onlyClient(() -> {
           // moduleEntry.addMinecraftEventClass(ZPRenderWorldEventWithPickUpCheck.class);
        });

      //  moduleEntry.addMinecraftEventClass(ZPPlayerGunCancelInterEvent.class);
    }

    @Override
    public void preInitialize() {
      //  ZPUtility.sides().onlyClient(() -> {
      //      ZombiePlague3.registerKeyBindings(new ZPPickUpKeyBindings());
      //  });
    }

    @Override
    public void postInitialize() {

    }
}
