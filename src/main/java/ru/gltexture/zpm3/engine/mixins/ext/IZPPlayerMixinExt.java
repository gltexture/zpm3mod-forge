package ru.gltexture.zpm3.engine.mixins.ext;

import ru.gltexture.zpm3.modules.net_pack.data.ZPNetSyncDataPack;

public interface IZPPlayerMixinExt {
    ZPNetSyncDataPack zpm3forge$zpNetDataPack_fromClient();

    void zpm3forge$getResponseNetCheckFromServer();
    void zpm3forge$getResponseNetCheckFromClient();
    int zpm3forge$getPing();
}
