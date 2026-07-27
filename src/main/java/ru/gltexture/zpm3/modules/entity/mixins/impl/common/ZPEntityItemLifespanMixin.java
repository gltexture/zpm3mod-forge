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

package ru.gltexture.zpm3.modules.entity.mixins.impl.common;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.gltexture.zpm3.engine.core.config.builtin.ZPWorldConfig;


@Mixin(ItemEntity.class)
public abstract class ZPEntityItemLifespanMixin {
    @Shadow
    public abstract void setNeverPickUp();

    @Shadow
    private int age;

    @Shadow
    public abstract ItemStack getItem();

    @Shadow(remap = false)
    public int lifespan;

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        this.lifespan = ZPWorldConfig.ENTITY_ITEM_LIFESPAN.getVar();
    }

    @Inject(method = "makeFakeItem", at = @At("HEAD"), cancellable = true)
    public void makeFakeItem(CallbackInfo ci) {
        this.setNeverPickUp();
        this.age = ZPWorldConfig.ENTITY_ITEM_LIFESPAN.getVar() - 1;
        ci.cancel();
    }
}
