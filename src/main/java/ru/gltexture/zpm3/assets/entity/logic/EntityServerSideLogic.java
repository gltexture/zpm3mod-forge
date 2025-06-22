package ru.gltexture.zpm3.assets.entity.logic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.assets.common.global.ZPConstants;
import ru.gltexture.zpm3.assets.common.utils.ZPCommonServerUtils;
import ru.gltexture.zpm3.assets.entity.nbt.ZPTagsList;
import ru.gltexture.zpm3.engine.nbt.ZPEntityNBT;

public class EntityServerSideLogic implements EntityTickEventLogic {
    @Override
    public void onTickEntity(@NotNull Entity entity) {
        ZPEntityNBT zpEntityTag = new ZPEntityNBT(entity);

        if (zpEntityTag.getTagInt(ZPTagsList.ACID_AFFECT_COOLDOWN) > 0) {
            this.damageItemsEveryTick(entity);
        }
    }

    private void damageItemsEveryTick(Entity entity) {
        if (entity.tickCount % ZPConstants.ACID_DAMAGE_TICK_RATE != 0) {
            return;
        }

        if (entity instanceof LivingEntity livingEntity) {
            for (ItemStack stack : livingEntity.getHandSlots()) {
                if (stack.isDamageableItem()) {
                    stack.hurtAndBreak(1, livingEntity, e -> {
                        e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                    });
                }
            }

            if (livingEntity instanceof Player player) {
                for (ItemStack stack : player.getInventory().items) {
                    if (stack.isDamageableItem()) {
                        stack.hurtAndBreak(1, player, e -> {
                            e.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                        });
                    }
                }

                for (int i = 0; i < player.getInventory().armor.size(); i++) {
                    ItemStack stack = player.getInventory().armor.get(i);
                    if (stack.isDamageableItem()) {
                        EquipmentSlot finalSlot = ZPCommonServerUtils.getEquipmentSlot(i);
                        stack.hurtAndBreak(1, player, e -> {
                            e.broadcastBreakEvent(finalSlot);
                        });
                    }
                }
            }
        }
    }
}
