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

package ru.gltexture.zpm3.modules.blocks.mixins.ext;

import ru.gltexture.zpm3.modules.blocks.instances.block_entities.IFadingBlockEntity;

public interface ICampfireExt extends IFadingBlockEntity {
    int zpm3forge$fadeCooldown();
    void zpm3forge$incCooldown(int inc);
    void zpm3forge$setCooldown(int cooldown);

    long zpm3forge$getTimeLock();
    void zpm3forge$setTimeLock(long timeLock);
}
