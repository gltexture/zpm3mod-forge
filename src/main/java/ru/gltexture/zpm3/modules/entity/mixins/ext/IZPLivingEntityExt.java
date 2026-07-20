package ru.gltexture.zpm3.modules.entity.mixins.ext;

public interface IZPLivingEntityExt {
    int zpm3forge$getRadiationLevel();
    void zpm3forge$setRadiationLevel(int radiationLevel);

    default void zpm3forge$addRadiationLevel(int amount) {
        this.zpm3forge$setRadiationLevel(this.zpm3forge$getRadiationLevel() + amount);
    }

    default void zpm3forge$decreaseRadiationLevel(int amount) {
        this.zpm3forge$setRadiationLevel(this.zpm3forge$getRadiationLevel() - amount);
    }

    int zpm3forge$getIntoxicationLevel();
    void zpm3forge$setIntoxicationLevel(int intoxicationLevel);
    void zpm3forge$setIntoxicationLevelForce(int intoxicationLevel);

    default void zpm3forge$addIntoxicationLevel(int intoxicationLevel) {
        this.zpm3forge$setIntoxicationLevel(this.zpm3forge$getIntoxicationLevel() + intoxicationLevel);
    }
  //  void zpm3forge$defineZPSyncData();
}