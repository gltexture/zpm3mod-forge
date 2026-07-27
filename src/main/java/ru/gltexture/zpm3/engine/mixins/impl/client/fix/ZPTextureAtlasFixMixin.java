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

package ru.gltexture.zpm3.engine.mixins.impl.client.fix;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author https://github.com/MehVahdJukaar/modelfix-multi
 */
@Mixin(TextureAtlasSprite.class)
public abstract class ZPTextureAtlasFixMixin {
    @Unique
    private static final ResourceLocation BLOCK_ATLAS = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/atlas/blocks.png");

    @Shadow protected abstract float atlasSize();

    @Shadow
    public abstract ResourceLocation atlasLocation();

    /**
     * @author https://github.com/MehVahdJukaar/modelfix-multi
     */
    @Inject(method = "uvShrinkRatio", at = @At("RETURN"), cancellable = true)
    public void cancelShrink(CallbackInfoReturnable<Float> cir) {
       float shrinked = ZPTextureAtlasFixMixin.zpm3forge$shrinkRatio(new TextureAtlas(this.atlasLocation()), 4.0F / this.atlasSize(), cir.getReturnValueF());
       if (shrinked != -1.0f) {
           cir.setReturnValue(shrinked);
       }
    }
    @Unique
    private static float zpm3forge$shrinkRatio(TextureAtlas atlas, float defaultValue, float returnValue) {
        return atlas.location().equals(ZPTextureAtlasFixMixin.BLOCK_ATLAS) && defaultValue == returnValue ? 0.0f : -1.0f;
    }
}