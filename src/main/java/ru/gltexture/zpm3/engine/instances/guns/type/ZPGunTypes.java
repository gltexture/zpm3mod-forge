package ru.gltexture.zpm3.engine.instances.guns.type;

public enum ZPGunTypes implements IZPGunType {
    PISTOL(false);

    private final boolean fullAuto;

    ZPGunTypes(boolean fullAuto) {
        this.fullAuto = fullAuto;
    }

    @Override
    public boolean isFullAuto() {
        return this.fullAuto;
    }
}
