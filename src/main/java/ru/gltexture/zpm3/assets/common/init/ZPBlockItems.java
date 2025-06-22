package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.helpers.ZPDefaultModelsHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemBlockHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;

public class ZPBlockItems extends ZPRegistry<Item> {
    public static RegistryObject<BlockItem> block_lamp_item;

    public ZPBlockItems() {
        super(ForgeRegistries.ITEMS, ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPBlockItems.block_lamp_item = ZPItemBlockHelper.createBlockItem(regSupplier, ZPBlocks.block_lamp).postConsume(Dist.CLIENT, (e) -> {
            ZPItemTabAddHelper.matchTabItem(e, ZPTabs.zp_blocks_tab);
        }).registryObject();
    }

    @Override
    protected void postRegister(String name, RegistryObject<Item> object) {
        super.postRegister(name, object);
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public @NotNull String getID() {
        return this.getClass().getSimpleName();
    }
}