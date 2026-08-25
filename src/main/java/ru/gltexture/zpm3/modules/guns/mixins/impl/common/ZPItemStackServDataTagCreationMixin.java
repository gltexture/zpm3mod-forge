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

package ru.gltexture.zpm3.modules.guns.mixins.impl.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gltexture.zpm3.engine.nbt.itemstack.ZPItemStackNBT;
import ru.gltexture.zpm3.modules.guns.item.ZPBaseGun;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public abstract class ZPItemStackServDataTagCreationMixin {
    @Shadow
    @Nullable
    public abstract CompoundTag getTag();

    @Shadow
    public abstract void setTag(@org.jetbrains.annotations.Nullable CompoundTag p_41752_);

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract CompoundTag getOrCreateTag();

    @Inject(method = "getOrCreateTag*", at = @At("HEAD"), cancellable = true)
    public void getOrCreateTag(CallbackInfoReturnable<CompoundTag> cir) {
        if (this.getTag() == null) {
            this.setTag(new CompoundTag());
        }
        if (this.getItem() instanceof ZPBaseGun baseGun) {
            cir.setReturnValue(((ZPItemStackNBT) baseGun.getServerData((ItemStack) (Object) this)).createTag(this.getTag()));
        }
        cir.setReturnValue(this.getTag());
    }
}