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

package ru.gltexture.zpm3.modules.guns.rendering.transforms;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

//@Deprecated(forRemoval = true)
public abstract class AbstractGunTransforms {
    @Nullable
    public Vector3f scalingGun1P() {
        return null;
    }

    @Nullable
    public Vector3f scalingGun3P() {
        return null;
    }

    @Nullable
    public Vector3f translationGunRight() {
        return null;
    }

    @Nullable
    public Vector3f rotationGunRight() {
        return null;
    }

    @Nullable
    public Vector3f translationArmRight() {
        return null;
    }

    @Nullable
    public Vector3f rotationArmRight() {
        return null;
    }

    @Nullable
    public Vector3f translationGunLeft() {
        return null;
    }

    @Nullable
    public Vector3f rotationGunLeft() {
        return null;
    }

    @Nullable
    public Vector3f translationArmLeft() {
        return null;
    }

    @Nullable
    public Vector3f rotationArmLeft() {
        return null;
    }

    @Nullable
    public Vector3f translationMuzzleflash3PRight() {
        return null;
    }

    @Nullable
    public Vector3f translationMuzzleflash3PLeft() {
        return null;
    }

    @Nullable
    public Vector3f translationMuzzleflash1PRight() {
        return null;
    }

    @Nullable
    public Vector3f translationMuzzleflash1PLeft() {
        return null;
    }

    @Nullable
    public Float muzzleflashScale() {
        return null;
    }

    @Nullable
    public Vector3f translationGunReloadingRight() {
        return null;
    }

    @Nullable
    public Vector3f rotationGunReloadingRight() {
        return null;
    }

    @Nullable
    public Vector3f translationGunReloadingLeft() {
        return null;
    }

    @Nullable
    public Vector3f rotationGunReloadingLeft() {
        return null;
    }

    @Nullable
    public Vector3f translationArmReloadingRight() {
        return null;
    }

    @Nullable
    public Vector3f rotationArmReloadingRight() {
        return null;
    }

    @Nullable
    public Vector3f translationArmReloadingLeft() {
        return null;
    }

    @Nullable
    public Vector3f rotationArmReloadingLeft() {
        return null;
    }

    @Nullable
    public Vector3f scaleArmLeft() {
        return null;
    }

    @Nullable
    public Vector3f scaleArmRight() {
        return null;
    }
}