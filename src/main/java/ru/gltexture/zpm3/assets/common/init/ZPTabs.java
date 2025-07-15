package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.helper.ZPRegBlockItems;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public class ZPTabs extends ZPRegistry<CreativeModeTab> {
    public static RegistryObject<CreativeModeTab> zp_items_tab;
    public static RegistryObject<CreativeModeTab> zp_melee_tab;
    public static RegistryObject<CreativeModeTab> zp_food_tab;
    public static RegistryObject<CreativeModeTab> zp_medicine_tab;
    public static RegistryObject<CreativeModeTab> zp_blocks_tab;

    public ZPTabs() {
        super(Registries.CREATIVE_MODE_TAB, ZPRegistryConveyor.Target.TAB);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<CreativeModeTab> regSupplier) {
        ZPTabs.zp_items_tab = regSupplier.register("zp_items_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.items")).icon(() -> new ItemStack(ZPItems.acid_bottle.get())).build()).registryObject();
        ZPTabs.zp_blocks_tab = regSupplier.register("zp_blocks_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.blocks")).icon(() -> new ItemStack(ZPRegBlockItems.getBlockItem(ZPBlocks.block_lamp).get())).build()).registryObject();
        ZPTabs.zp_melee_tab = regSupplier.register("zp_melee_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.melee")).icon(() -> new ItemStack(ZPItems.crowbar.get())).build()).registryObject();
        ZPTabs.zp_food_tab = regSupplier.register("zp_food_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.food")).icon(() -> new ItemStack(ZPItems.soda.get())).build()).registryObject();
        ZPTabs.zp_medicine_tab = regSupplier.register("zp_medicine_tab", () -> CreativeModeTab.builder().title(Component.translatable("tab.zpm3.medicine")).icon(() -> new ItemStack(ZPItems.adrenaline.get())).build()).registryObject();
    }

    @Override
    protected void postRegister(String name, RegistryObject<CreativeModeTab> object) {
        super.postRegister(name, object);
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
