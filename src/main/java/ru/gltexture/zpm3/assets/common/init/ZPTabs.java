package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.helpers.ZPDefaultModelsHelper;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;

public class ZPTabs extends ZPRegistry<CreativeModeTab> {
    public static RegistryObject<CreativeModeTab> zp_items_tab;
    public static RegistryObject<CreativeModeTab> zp_blocks_tab;

    public ZPTabs() {
        super(Registries.CREATIVE_MODE_TAB, ZPRegistryConveyor.Target.TAB);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<CreativeModeTab> regSupplier) {
        ZPTabs.zp_items_tab = regSupplier.register("zp_items_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.items")).icon(() -> new ItemStack(ZPItems.acid_bottle.get())).build());
        ZPTabs.zp_blocks_tab = regSupplier.register("zp_blocks_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.blocks")).icon(() -> new ItemStack(ZPBlockItems.block_lamp_item.get())).build());
    }

    @Override
    protected void postRegister(String name, RegistryObject<CreativeModeTab> object) {
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}
