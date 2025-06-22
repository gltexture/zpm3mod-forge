package ru.gltexture.zpm3.assets.common.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPAcidBottleEntity;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPPlateEntity;
import ru.gltexture.zpm3.assets.common.instances.entities.ZPRockEntity;
import ru.gltexture.zpm3.assets.common.instances.items.ZPItemThrowable;
import ru.gltexture.zpm3.engine.core.ZPRegistryConveyor;
import ru.gltexture.zpm3.engine.helpers.ZPDefaultModelsHelper;
import ru.gltexture.zpm3.engine.helpers.ZPDispenserHelper;
import ru.gltexture.zpm3.engine.helpers.ZPItemTabAddHelper;
import ru.gltexture.zpm3.engine.registry.base.ZPRegistry;

public class ZPItems extends ZPRegistry<Item> {
    public static RegistryObject<ZPItemThrowable> acid_bottle;
    public static RegistryObject<ZPItemThrowable> plate;
    public static RegistryObject<ZPItemThrowable> rock;

    public ZPItems() {
        super(ForgeRegistries.ITEMS, ZPRegistryConveyor.Target.ITEM);
    }

    @Override
    protected void runRegister(@NotNull ZPRegSupplier<Item> regSupplier) {
        ZPItems.acid_bottle = regSupplier.register("acid_bottle",
                () -> new ZPItemThrowable(((inaccuracy, velocity, itemstack, pLevel, pPlayer, pHand) -> {
                    ZPAcidBottleEntity throwable = new ZPAcidBottleEntity(ZPEntities.acid_bottle_entity.get(), pPlayer, pLevel);
                    throwable.setItem(itemstack);
                    throwable.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, velocity, inaccuracy);
                    return throwable;
                }), new Item.Properties().stacksTo(1))
        ).postConsume(Dist.CLIENT, (e) -> {
            ZPItemTabAddHelper.matchTabItem(e, ZPTabs.zp_items_tab);
            ZPDispenserHelper.addDispenserData(e, new ZPDispenserHelper.ProjectileData((pLevel, pPosition, pStack) -> new ZPAcidBottleEntity(ZPEntities.acid_bottle_entity.get(), pPosition.x(), pPosition.y(), pPosition.z(), pLevel), 0.5f, 1.5f));
        }).registryObject();

        ZPItems.plate = regSupplier.register("plate",
                () -> new ZPItemThrowable(((inaccuracy, velocity, itemstack, pLevel, pPlayer, pHand) -> {
                    ZPPlateEntity throwable = new ZPPlateEntity(ZPEntities.plate_entity.get(), pPlayer, pLevel);
                    throwable.setItem(itemstack);
                    throwable.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, velocity, inaccuracy);
                    return throwable;
                }), new Item.Properties().stacksTo(16))
        ).postConsume(Dist.CLIENT, (e) -> {
            ZPItemTabAddHelper.matchTabItem(e, ZPTabs.zp_items_tab);
            ZPDispenserHelper.addDispenserData(e, new ZPDispenserHelper.ProjectileData((pLevel, pPosition, pStack) -> new ZPPlateEntity(ZPEntities.plate_entity.get(), pPosition.x(), pPosition.y(), pPosition.z(), pLevel), 0.5f, 1.5f));
        }).registryObject();

        ZPItems.rock = regSupplier.register("rock",
                () -> new ZPItemThrowable(((inaccuracy, velocity, itemstack, pLevel, pPlayer, pHand) -> {
                    ZPRockEntity throwable = new ZPRockEntity(ZPEntities.rock_entity.get(), pPlayer, pLevel);
                    throwable.setItem(itemstack);
                    throwable.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, velocity, inaccuracy);
                    return throwable;
                }), new Item.Properties().stacksTo(3))
        ).postConsume(Dist.CLIENT, (e) -> {
            ZPItemTabAddHelper.matchTabItem(e, ZPTabs.zp_items_tab);
            ZPDispenserHelper.addDispenserData(e, new ZPDispenserHelper.ProjectileData((pLevel, pPosition, pStack) -> new ZPRockEntity(ZPEntities.rock_entity.get(), pPosition.x(), pPosition.y(), pPosition.z(), pLevel), 0.5f, 1.5f));
        }).registryObject();
    }

    @Override
    protected void postRegister(String name, RegistryObject<Item> object) {
        ZPDefaultModelsHelper.addNewItemWithDefaultModel(object, ZPDefaultModelsHelper.DEFAULT_ITEM);
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