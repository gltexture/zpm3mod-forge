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

package ru.gltexture.zpm3.engine.client.rendering.gl.base;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL46;

@OnlyIn(Dist.CLIENT)
public interface ITextureProgram {
    default void bindSampler(int unit) {
        GL46.glBindSampler(unit, this.getSamplerId());
    }

    default void unBindSampler(int unit) {
        GL46.glBindSampler(unit, 0);
    }

    default void bindTexture() {
        GL46.glBindTexture(this.getTextureAttachment(), this.getTextureId());
    }

    default void unBindTexture() {
        GL46.glBindTexture(this.getTextureAttachment(), 0);
    }

    int getSamplerId();
    int getTextureId();
    int getTextureAttachment();

    default boolean isSamplerValid() {
        return this.getSamplerId() > 0;
    }

    default boolean isValid() {
        return this.getTextureId() > 0;
    }

    void clear();
}
