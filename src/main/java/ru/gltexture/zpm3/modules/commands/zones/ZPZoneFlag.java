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

package ru.gltexture.zpm3.modules.commands.zones;

import java.util.*;

public final class ZPZoneFlag {
    private final String id;

    ZPZoneFlag(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object userObject) {
        if (!(userObject instanceof ZPZoneFlag that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return this.id();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String id() {
        return this.id;
    }
}
