package ru.gltexture.zpm3.engine.mixins.ext;

public interface IZPPlayerMixinExt {
    void zpm3forge$setEnabledPickUpOnF(boolean enabledPickUpOnF);
    boolean zpm3forge$enabledPickUpOnF();

    void zpm3forge$getResultFromServer();
    void zpm3forge$getResultFromClient();
    int zpm3forge$getPing();
}
