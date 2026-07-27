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

package ru.gltexture.zpm3.engine.service;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Objects;

public final class ZPPath implements Serializable {
    @Serial
    private static final long serialVersionUID = 142L;
    private final String fullPath;

    public ZPPath(ZPPath path, String... other) {
        this(path.getFullPath(), other);
    }

    public ZPPath(String root, String... other) {
        this.fullPath = this.concatenate(root, other);
    }

    public ZPPath(Path path) {
        this(path.toString());
    }

    public ZPPath(String path) {
        this.fullPath = this.concatenate(path);
    }

    private String concatenate(String root, String... other) {
        StringBuilder stringBuilder = new StringBuilder(this.fixPath(root));
        if (other != null) {
            for (String s : other) {
                String string = this.fixPath(s);
                stringBuilder.append(string);
            }
        }
        String path = stringBuilder.toString();
        return path.replace("\\", "/").replace("//", "/");
    }

    private String fixPath(String path) {
        String trimmedPath = path.trim();
        String normalizedPath = trimmedPath.replace("\\", "/");
        if (!normalizedPath.startsWith("/")) {
            normalizedPath = "/" + normalizedPath;
        }
        return normalizedPath;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ZPPath zpPath)) {
            return false;
        }
        return Objects.equals(this.fullPath, zpPath.fullPath);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.fullPath);
    }

    public File toFile() {
        return new File(this.getFullPath());
    }

    public Path toPath() {
        return this.toFile().toPath();
    }

    public ZPPath getDirectory() {
        return new ZPPath(this.getFullPath().substring(0, this.getFullPath().lastIndexOf('/')));
    }

    public String getFullPath() {
        return this.fullPath;
    }

    @Override
    public String toString() {
        return this.getFullPath();
    }
}