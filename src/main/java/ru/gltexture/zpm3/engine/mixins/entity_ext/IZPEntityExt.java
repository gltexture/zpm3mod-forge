package ru.gltexture.zpm3.engine.mixins.entity_ext;

public interface IZPEntityExt {
    int getAcidLevel();
    int getIntoxicationLevel();

    void setAcidLevel(int acidLevel);
    void setIntoxicationLevel(int intoxicationLevel);

    void defineZPSyncData();

    boolean touchesAcidBlock();
    boolean touchesToxicBlock();

    default void addAcidLevel(int acidLevel) {
        this.setAcidLevel(this.getAcidLevel() + acidLevel);
    }

    default void addIntoxicationLevel(int intoxicationLevel) {
        this.setIntoxicationLevel(this.getIntoxicationLevel() + intoxicationLevel);
    }
}
