package ru.gltexture.zpm3.modules.ui.events.client;

import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPSide;
import ru.gltexture.zpm3.engine.events.ZPEventClass;
import ru.gltexture.zpm3.modules.ui.screen.ZPSettingsMenuScreen;

@OnlyIn(Dist.CLIENT)
public class ZPNewSettingsScreenEvent implements ZPEventClass {
    public ZPNewSettingsScreenEvent() {
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
    public static void onInit(ScreenEvent.Opening event) {
        if (event.getNewScreen() instanceof OptionsScreen optionsScreen) {
            if (!(optionsScreen.lastScreen instanceof ZPSettingsMenuScreen)) {
                event.setNewScreen(new ZPSettingsMenuScreen(event.getCurrentScreen()));
            }
        }
    }
}
