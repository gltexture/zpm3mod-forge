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

package ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.values;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.nbt.ZPLootNbtValue;
import ru.gltexture.zpm3.modules.loot_cases.loot_tables.random.ZPRandomization;

public final class ZPLootNbtRandomInt extends ZPLootNbtValue {
    private final int min;
    private final int max;
    private final ZPRandomization randomization;

    public ZPLootNbtRandomInt(int min, int max, @NotNull ZPRandomization randomization) {
        super(ZPLootNbtValue.TYPE_RANDOM_INT);
        this.min = min;
        this.max = max;
        this.randomization = randomization;
    }

    @Override
    public void writeValue(@NotNull CompoundTag nbt, @NotNull String key) {
        nbt.putInt(key, this.randomization.random(this.min, this.max));
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public ZPRandomization getRandomization() {
        return this.randomization;
    }
}