package ru.gltexture.zpm3.engine.objects.items.harvest;

public enum ZPHarvestLevel {
    HARVEST_STONE(0),
    HARVEST_IRON(1),
    HARVEST_DIAMOND(2),
    HARVEST_OBSIDIAN(3),
    HARVEST_ALL(4);

    private final int level;

    ZPHarvestLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
}
