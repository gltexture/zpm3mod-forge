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

package ru.gltexture.zpm3.engine.network.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;
import ru.gltexture.zpm3.engine.network.ZPNetwork;
import ru.gltexture.zpm3.modules.net_pack.data.data_ent.ZPNetEntDataSyncer;
import ru.gltexture.zpm3.modules.net_pack.data.data_static.IZPNetStaticDataSyncer;
import ru.gltexture.zpm3.modules.net_pack.data.data_static.ZPNetStaticDataSyncerClient;
import ru.gltexture.zpm3.modules.net_pack.data.data_static.ZPNetStaticDataSyncerServer;

@OnlyIn(Dist.CLIENT)
public final class ZPNetworkHandlerClient extends ZPNetworkHandler {

    public static ZPNetworkHandlerClient instance = new ZPNetworkHandlerClient();

    public static void init() {
        ZPNetworkHandlerClient.instance = new ZPNetworkHandlerClient();
    }

    public static ZPNetworkHandlerClient get() {
        return ZPNetworkHandlerClient.instance;
    }

    ZPNetworkHandlerClient() {
        super(new ZPNetStaticDataSyncerClient(), new ZPNetEntDataSyncer(), Side.CLIENT);
    }

    @Override
    public ZPNetEntDataSyncer getNetEntDataSyncer() {
        return (ZPNetEntDataSyncer) super.getNetEntDataSyncer();
    }

    @Override
    public ZPNetStaticDataSyncerClient getNetStaticDataSyncer() {
        return (ZPNetStaticDataSyncerClient) super.getNetStaticDataSyncer();
    }

    @Override
    public boolean isServer() {
        return false;
    }

    public void sendToServer(@NotNull ZPNetwork.ZPPacket packet) {
        this.getNetwork().getMainChannel().sendToServer(packet);
    }
}
