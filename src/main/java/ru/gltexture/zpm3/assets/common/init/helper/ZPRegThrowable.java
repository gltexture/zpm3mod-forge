package ru.gltexture.zpm3.assets.common.init.helper;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.init.ZPEntities;
import ru.gltexture.zpm3.assets.common.init.ZPItems;
import ru.gltexture.zpm3.assets.common.init.ZPTabs;
import ru.gltexture.zpm3.assets.common.instances.entities.throwables.ZPAcidBottleEntity;
import ru.gltexture.zpm3.assets.common.instances.entities.throwables.ZPPlateEntity;
import ru.gltexture.zpm3.assets.common.instances.entities.throwables.ZPRockEntity;
import ru.gltexture.zpm3.engine.helpers.gen.data.ZPGenTextureData;
import ru.gltexture.zpm3.engine.instances.items.ZPItemThrowable;
import ru.gltexture.zpm3.engine.helpers.gen.ZPDataGenHelper;
import ru.gltexture.zpm3.engine.helpers.ZPDispenseProjectileHelper;
import ru.gltexture.zpm3.engine.registry.ZPRegistry;

public abstract class ZPRegThrowable {
    public static void init(@NotNull ZPRegistry.ZPRegSupplier<Item> regSupplier) {
        ZPItems.acid_bottle = regSupplier.register("acid_bottle",
                () -> new ZPItemThrowable(((inaccuracy, velocity, itemstack, pLevel, pPlayer, pHand) -> {
                    ZPAcidBottleEntity throwable = new ZPAcidBottleEntity(ZPEntities.acid_bottle_entity.get(), pPlayer, pLevel);
                    throwable.setItem(itemstack);
                    throwable.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, velocity, inaccuracy);
                    return throwable;
                }), new Item.Properties().stacksTo(1))
        ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.ITEMS_ITEMS_DIRECTORY);
        }).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
            utils.items().addDispenserData(e, new ZPDispenseProjectileHelper.ProjectileData((pLevel, pPosition, pStack) -> new ZPAcidBottleEntity(ZPEntities.acid_bottle_entity.get(), pPosition.x(), pPosition.y(), pPosition.z(), pLevel), 0.5f, 1.5f));
        }).end();

        ZPItems.plate = regSupplier.register("plate",
                () -> new ZPItemThrowable(((inaccuracy, velocity, itemstack, pLevel, pPlayer, pHand) -> {
                    ZPPlateEntity throwable = new ZPPlateEntity(ZPEntities.plate_entity.get(), pPlayer, pLevel);
                    throwable.setItem(itemstack);
                    throwable.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, velocity, inaccuracy);
                    return throwable;
                }), new Item.Properties().stacksTo(16))
        ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.ITEMS_ITEMS_DIRECTORY);
        }).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
            utils.items().addDispenserData(e, new ZPDispenseProjectileHelper.ProjectileData((pLevel, pPosition, pStack) -> new ZPPlateEntity(ZPEntities.plate_entity.get(), pPosition.x(), pPosition.y(), pPosition.z(), pLevel), 0.5f, 1.5f));
        }).end();

        ZPItems.rock = regSupplier.register("rock",
                () -> new ZPItemThrowable(((inaccuracy, velocity, itemstack, pLevel, pPlayer, pHand) -> {
                    ZPRockEntity throwable = new ZPRockEntity(ZPEntities.rock_entity.get(), pPlayer, pLevel);
                    throwable.setItem(itemstack);
                    throwable.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, velocity, inaccuracy);
                    return throwable;
                }), new Item.Properties().stacksTo(3))
        ).afterObjectCreated(Dist.CLIENT, (e, utils) -> {
            utils.items().addItemInTab(e, ZPTabs.zp_items_tab);
            utils.items().addItemModel(e, ZPDataGenHelper.DEFAULT_ITEM, ZPGenTextureData.LAYER0_KEY, ZPDataGenHelper.ITEMS_ITEMS_DIRECTORY);
        }).afterObjectCreated(Dist.DEDICATED_SERVER, (e, utils) -> {
            utils.items().addDispenserData(e, new ZPDispenseProjectileHelper.ProjectileData((pLevel, pPosition, pStack) -> new ZPRockEntity(ZPEntities.rock_entity.get(), pPosition.x(), pPosition.y(), pPosition.z(), pLevel), 0.5f, 1.5f));
        }).end();
    }
}
