/*
 *
 *  * zpm3forge
 *  * Copyright (C) 2026 gltexture
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package ru.gltexture.zpm3.modules.player;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPCombatConfig;
import ru.gltexture.zpm3.engine.core.module.ZPModule;
import ru.gltexture.zpm3.modules.armor.init.ZPArmorItems;
import ru.gltexture.zpm3.modules.armor.utils.ZPArmorUtil;
import ru.gltexture.zpm3.modules.entity.util.ZPEntityUtil;
import ru.gltexture.zpm3.modules.misc_items.init.ZPMiscItems;
import ru.gltexture.zpm3.modules.player.events.client.*;
import ru.gltexture.zpm3.modules.player.events.common.*;
import ru.gltexture.zpm3.modules.player.keybind.ZPPickUpKeyBindings;
import ru.gltexture.zpm3.modules.player.events.server.ZPPlayerFillBucketEvent;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.core.module.ZPModuleData;
import ru.gltexture.zpm3.engine.service.ZPUtility;

import java.util.Arrays;
import java.util.function.Supplier;

public class ZPPlayerModule extends ZPModule {
    public ZPPlayerModule(@NotNull ZPModuleData zpModuleData) {
        super(zpModuleData);
    }

    public ZPPlayerModule() {
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

    //@Override
    //public void initMixins(ZombiePlague3.@NotNull IMixinEntry mixinEntry) {
    //    mixinEntry.addMixinConfigData(new ZombiePlague3.IMixinEntry.MixinConfig("player", "ru.gltexture.zpm3.modules.player.mixins.impl"),
    //            new ZombiePlague3.IMixinEntry.MixinClass("common.ZPSPlayerFeaturesMixin", ZPSide.COMMON),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPCPlayerFeaturesMixin", ZPSide.CLIENT),
    //            new ZombiePlague3.IMixinEntry.MixinClass("client.ZPPlayerItemReanimateMixin", ZPSide.CLIENT));
    //}

    @Override
    public void initialize(ZombiePlague3.@NotNull IModuleEntry moduleEntry) {
        ZPUtility.sides().onlyClient(() -> {
            moduleEntry.addMinecraftEventClass(ZPRenderWorldEventWithPickUpCheck.class);
            moduleEntry.addMinecraftEventClass(ZPPlayerItemToolTipsEvent.class);
            moduleEntry.addMinecraftEventClass(ZPRenderGuiEvent.class);
            moduleEntry.addMinecraftEventClass(ZPResourcePackEvent.class);
            moduleEntry.addMinecraftEventClass(ZPPlayerLyingClientCheckEvent.class);
            moduleEntry.addMinecraftEventClass(ZPPlayerClientTickGeigerSoundEvent.class);
        });

        moduleEntry.addMinecraftEventClass(ZPPlayerGunCancelInterEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerEntityItemEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerTickEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerFillBucketEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlaceLiquidEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerEatFoodEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerJoinOrSpawnEvent.class);
        moduleEntry.addMinecraftEventClass(ZPPlayerTickedBreakEquipmentEvent.class);
    }

    @Override
    public void preInitialize() {
        ZPUtility.sides().onlyClient(() -> {
            ZombiePlague3.registerKeyBindings(new ZPPickUpKeyBindings());
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void postInitialize() {
        ZPPlayerTickedBreakEquipmentEvent.registerArmorBreakPerTickCondition(ZPArmorItems.night_vision_goggles::get, (entity, armorItem, slot, tick) -> {
            if (ZPCombatConfig.ZP_BREAK_NV_GOGGLES_PER_TICK.getVar() < 0) {
                return false;
            }
            final int tickRate = entity.isUnderWater() ? ZPCombatConfig.ZP_BREAK_NV_GOGGLES_PER_TICK.getVar() / 8 : ZPCombatConfig.ZP_BREAK_NV_GOGGLES_PER_TICK.getVar();
            return entity.tickCount % tickRate == 0;
        });
        Arrays.stream(new Supplier[]{ZPArmorItems.radiation_costume_helmet, ZPArmorItems.radiation_costume_chestplate, ZPArmorItems.radiation_costume_leggings, ZPArmorItems.radiation_costume_boots}).forEach(e -> {
            ZPPlayerTickedBreakEquipmentEvent.registerArmorBreakPerTickCondition(e, (entity, armorItem, slot, tick) -> {
                final int radReductionTick = ZPEntityUtil.getLivingEntityRadiationIncMultiplier((LivingEntity) entity);
                if (radReductionTick <= 0) {
                    return false;
                }
                if (ZPCombatConfig.ZP_BREAK_RADIATION_COSTUME_PER_TICK.getVar() < 0) {
                    return false;
                }
                return entity.tickCount % (ZPCombatConfig.ZP_BREAK_RADIATION_COSTUME_PER_TICK.getVar() / radReductionTick) == 0;
            });
        });
        Arrays.stream(new Supplier[]{ZPArmorItems.acid_costume_helmet, ZPArmorItems.acid_costume_chestplate, ZPArmorItems.acid_costume_leggings, ZPArmorItems.acid_costume_boots}).forEach(e -> {
            ZPPlayerTickedBreakEquipmentEvent.registerArmorBreakPerTickCondition(e, (entity, armorItem, slot, tick) -> {
                final int acidReductionTick = ZPEntityUtil.getEntityAcidIncMultiplier(entity);
                if (acidReductionTick <= 0) {
                    return false;
                }
                if (ZPCombatConfig.ZP_BREAK_ACID_COSTUME_PER_TICK.getVar() < 0) {
                    return false;
                }
                return entity.tickCount % (ZPCombatConfig.ZP_BREAK_ACID_COSTUME_PER_TICK.getVar() / acidReductionTick) == 0;
            });
        });
        Arrays.stream(new Supplier[]{ZPArmorItems.aqualung_costume_helmet, ZPArmorItems.aqualung_costume_chestplate, ZPArmorItems.aqualung_costume_leggings, ZPArmorItems.aqualung_costume_boots}).forEach(e -> {
            ZPPlayerTickedBreakEquipmentEvent.registerArmorBreakPerTickCondition(e, (entity, armorItem, slot, tick) -> {
                        if (!ZPArmorUtil.isFullAqualungBreathingRightNow((LivingEntity) entity)) {
                            return false;
                        }
                        int base = ZPCombatConfig.ZP_BREAK_ACID_COSTUME_PER_TICK.getVar();
                        if (base <= 0) {
                            return false;
                        }
                        return entity.tickCount % base == 0;
                    }
            );
        });
        ZPPlayerTickedBreakEquipmentEvent.registerItemBreakPerTickCondition(ZPMiscItems.oxygen::get,
                (entity, item, slot, tick) -> {
                    ItemStack stackInHan_oxygen = ZPEntityUtil.getOxygenStackInHand((LivingEntity) entity);
                    if (!item.equals(stackInHan_oxygen)) {
                        return false;
                    }
                    if (!ZPArmorUtil.isFullAqualungBreathingRightNow((LivingEntity) entity)) {
                        return false;
                    }
                    int base = ZPCombatConfig.ZP_BREAK_OXYGEN_ITEM_PER_TICK.getVar();
                    if (base <= 0) {
                        return false;
                    }
                    return entity.tickCount % base == 0;
                }
        );
    }
}
