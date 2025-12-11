package ru.gltexture.zpm3.assets.entity.population;

import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.population.ZPPopulationController;

public abstract class ZPSetupPopulation {
    public abstract void setup(@NotNull ZPPopulationController controller);
}
