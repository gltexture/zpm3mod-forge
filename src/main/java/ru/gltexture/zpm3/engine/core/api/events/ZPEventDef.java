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

package ru.gltexture.zpm3.engine.core.api.events;

public interface ZPEventDef {
    enum State {
        START,
        END
    }

    enum Run {
        PRE,
        POST
    }

    interface IEvent {
        default boolean canBeCancelled() {
            return this instanceof ZPEventDef.Cancellable;
        }

        @SuppressWarnings("all")
        default boolean isCancelled() {
            return this.canBeCancelled() && ((ZPEventDef.Cancellable) this).isCancelled();
        }

        class EmptyEvent implements ZPEventDef.IEvent {
        }
    }

    abstract class Cancellable {
        private boolean isCancelled;

        public Cancellable() {
            this.isCancelled = false;
        }

        public boolean isCancelled() {
            return this.isCancelled;
        }

        public void setCancelled(boolean cancelled) {
            this.isCancelled = cancelled;
        }
    }
}
