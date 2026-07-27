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

package ru.gltexture.zpm3.modules.guns.mixins.impl.client;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.gltexture.zpm3.modules.guns.mixins.ext.IZPPlayerClientDataExt;

@OnlyIn(Dist.CLIENT)
@Mixin(Player.class)
public abstract class ZPPlayerClientDataMuzzleflash3PMixin implements IZPPlayerClientDataExt {
    @Unique
    private float[] zpm3forge$scissors3Person;

    @Override
    public float[] zpm3forge$getPlayerMuzzleflashScissor3Person() {
        if (this.zpm3forge$scissors3Person == null) {
            this.zpm3forge$scissors3Person = new float[]{1.0f, 1.0f};
        }
        return this.zpm3forge$scissors3Person;
    }
}
