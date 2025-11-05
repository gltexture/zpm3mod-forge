package ru.gltexture.zpm3.engine.core.population;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class VanillaBiomePopulationManager {
    private boolean cancelVanilla_farmAnimals_Method;
    private boolean cancelVanilla_monsters_Method;
    private boolean cancelVanilla_caveSpawns_Method;
    private boolean cancelVanilla_commonSpawns_Method;
    private boolean cancelVanilla_oceanSpawns_Method;
    private boolean cancelVanilla_warmOceanSpawns_Method;
    private boolean cancelVanilla_plainsSpawns_Method;
    private boolean cancelVanilla_snowySpawns_Method;
    private boolean cancelVanilla_desertSpawns_Method;
    private boolean cancelVanilla_dripstoneCavesSpawns_Method;
    private boolean cancelVanilla_mooshroomSpawns_Method;
    private boolean cancelVanilla_baseJungleSpawns_Method;
    private boolean cancelVanilla_endSpawns_Method;

    private List<Consumer<MobSpawnSettings.Builder>> farmAnimals_Consumers;
    private List<Function5<MobSpawnSettings.Builder, Integer, Integer, Integer, Boolean, Void>> monsters_Consumers;
    private List<Consumer<MobSpawnSettings.Builder>> caveSpawns_Consumers;
    private List<Consumer<MobSpawnSettings.Builder>> commonSpawns_Consumers;
    private List<Function4<MobSpawnSettings.Builder, Integer, Integer, Integer, Void>> oceanSpawns_Consumers;
    private List<Function3<MobSpawnSettings.Builder, Integer, Integer, Void>> warmOceanSpawns_Consumers;
    private List<Consumer<MobSpawnSettings.Builder>> plainsSpawns_Consumers;
    private List<Consumer<MobSpawnSettings.Builder>> snowySpawns_Consumers;
    private List<Consumer<MobSpawnSettings.Builder>> desertSpawns_Consumers;
    private List<Consumer<MobSpawnSettings.Builder>> dripstoneCavesSpawns_Consumers;
    private List<Consumer<MobSpawnSettings.Builder>> mooshroomSpawns_Consumers;
    private List<Consumer<MobSpawnSettings.Builder>> baseJungleSpawns_Consumers;
    private List<Consumer<MobSpawnSettings.Builder>> endSpawns_Consumers;

    VanillaBiomePopulationManager() {
        this.cancelVanilla_farmAnimals_Method = false;
        this.cancelVanilla_monsters_Method = false;
        this.cancelVanilla_caveSpawns_Method = false;
        this.cancelVanilla_commonSpawns_Method = false;
        this.cancelVanilla_oceanSpawns_Method = false;
        this.cancelVanilla_warmOceanSpawns_Method = false;
        this.cancelVanilla_plainsSpawns_Method = false;
        this.cancelVanilla_snowySpawns_Method = false;
        this.cancelVanilla_desertSpawns_Method = false;
        this.cancelVanilla_dripstoneCavesSpawns_Method = false;
        this.cancelVanilla_mooshroomSpawns_Method = false;
        this.cancelVanilla_baseJungleSpawns_Method = false;
        this.cancelVanilla_endSpawns_Method = false;

        this.farmAnimals_Consumers = new ArrayList<>();
        this.monsters_Consumers = new ArrayList<>();
        this.caveSpawns_Consumers = new ArrayList<>();
        this.commonSpawns_Consumers = new ArrayList<>();
        this.oceanSpawns_Consumers = new ArrayList<>();
        this.warmOceanSpawns_Consumers = new ArrayList<>();
        this.plainsSpawns_Consumers = new ArrayList<>();
        this.snowySpawns_Consumers = new ArrayList<>();
        this.desertSpawns_Consumers = new ArrayList<>();
        this.dripstoneCavesSpawns_Consumers = new ArrayList<>();
        this.mooshroomSpawns_Consumers = new ArrayList<>();
        this.baseJungleSpawns_Consumers = new ArrayList<>();
        this.endSpawns_Consumers = new ArrayList<>();
    }

    void clear() {
        this.farmAnimals_Consumers.clear();
        this.monsters_Consumers.clear();
        this.caveSpawns_Consumers.clear();
        this.commonSpawns_Consumers.clear();
        this.oceanSpawns_Consumers.clear();
        this.warmOceanSpawns_Consumers.clear();
        this.plainsSpawns_Consumers.clear();
        this.snowySpawns_Consumers.clear();
        this.desertSpawns_Consumers.clear();
        this.dripstoneCavesSpawns_Consumers.clear();
        this.mooshroomSpawns_Consumers.clear();
        this.baseJungleSpawns_Consumers.clear();
        this.endSpawns_Consumers.clear();
    }

    public boolean farmAnimals(MobSpawnSettings.Builder pBuilder) {
        for (Consumer<MobSpawnSettings.Builder> c : this.farmAnimals_Consumers) {
            c.accept(pBuilder);
        }
        return this.cancelVanilla_farmAnimals_Method;
    }

    public boolean monsters(MobSpawnSettings.Builder pBuilder, int pZombieWeight, int pZombieVillageWeight, int pSkeletonWeight, boolean pIsUnderwater) {
        for (Function5<MobSpawnSettings.Builder, Integer, Integer, Integer, Boolean, Void> c : this.monsters_Consumers) {
            c.apply(pBuilder, pZombieWeight, pZombieVillageWeight, pSkeletonWeight, pIsUnderwater);
        }
        return this.cancelVanilla_monsters_Method;
    }

    public boolean caveSpawns(MobSpawnSettings.Builder pBuilder) {
        for (Consumer<MobSpawnSettings.Builder> c : this.caveSpawns_Consumers) {
            c.accept(pBuilder);
        }
        return this.cancelVanilla_caveSpawns_Method;
    }

    public boolean commonSpawns(MobSpawnSettings.Builder pBuilder) {
        for (Consumer<MobSpawnSettings.Builder> c : this.commonSpawns_Consumers) {
            c.accept(pBuilder);
        }
        return this.cancelVanilla_commonSpawns_Method;
    }

    public boolean oceanSpawns(MobSpawnSettings.Builder pBuilder, int pSquidWeight, int pSquidMaxCount, int pCodWeight) {
        for (Function4<MobSpawnSettings.Builder, Integer, Integer, Integer, Void> c : this.oceanSpawns_Consumers) {
            c.apply(pBuilder, pSquidWeight, pSquidMaxCount, pCodWeight);
        }
        return this.cancelVanilla_oceanSpawns_Method;
    }

    public boolean warmOceanSpawns(MobSpawnSettings.Builder pBuilder, int pSquidWeight, int pSquidMinCount) {
        for (Function3<MobSpawnSettings.Builder, Integer, Integer, Void> c : this.warmOceanSpawns_Consumers) {
            c.apply(pBuilder, pSquidWeight, pSquidMinCount);
        }
        return this.cancelVanilla_warmOceanSpawns_Method;
    }

    public boolean plainsSpawns(MobSpawnSettings.Builder pBuilder) {
        for (Consumer<MobSpawnSettings.Builder> c : this.plainsSpawns_Consumers) {
            c.accept(pBuilder);
        }
        return this.cancelVanilla_plainsSpawns_Method;
    }

    public boolean snowySpawns(MobSpawnSettings.Builder pBuilder) {
        for (Consumer<MobSpawnSettings.Builder> c : this.snowySpawns_Consumers) {
            c.accept(pBuilder);
        }
        return this.cancelVanilla_snowySpawns_Method;
    }

    public boolean desertSpawns(MobSpawnSettings.Builder pBuilder) {
        for (Consumer<MobSpawnSettings.Builder> c : this.desertSpawns_Consumers) {
            c.accept(pBuilder);
        }
        return this.cancelVanilla_desertSpawns_Method;
    }

    public boolean dripstoneCavesSpawns(MobSpawnSettings.Builder pBuilder) {
        for (Consumer<MobSpawnSettings.Builder> c : this.dripstoneCavesSpawns_Consumers) {
            c.accept(pBuilder);
        }
        return this.cancelVanilla_dripstoneCavesSpawns_Method;
    }

    public boolean mooshroomSpawns(MobSpawnSettings.Builder pBuilder) {
        for (Consumer<MobSpawnSettings.Builder> c : this.mooshroomSpawns_Consumers) {
            c.accept(pBuilder);
        }
        return this.cancelVanilla_mooshroomSpawns_Method;
    }

    public boolean baseJungleSpawns(MobSpawnSettings.Builder pBuilder) {
        for (Consumer<MobSpawnSettings.Builder> c : this.baseJungleSpawns_Consumers) {
            c.accept(pBuilder);
        }
        return this.cancelVanilla_baseJungleSpawns_Method;
    }

    public boolean endSpawns(MobSpawnSettings.Builder pBuilder) {
        for (Consumer<MobSpawnSettings.Builder> c : this.endSpawns_Consumers) {
            c.accept(pBuilder);
        }
        return this.cancelVanilla_endSpawns_Method;
    }

    //============================================================================================

    public List<Consumer<MobSpawnSettings.Builder>> getFarmAnimals_Consumers() {
        return this.farmAnimals_Consumers;
    }

    public List<Function5<MobSpawnSettings.Builder, Integer, Integer, Integer, Boolean, Void>> getMonsters_Consumers() {
        return this.monsters_Consumers;
    }

    public List<Consumer<MobSpawnSettings.Builder>> getCaveSpawns_Consumers() {
        return this.caveSpawns_Consumers;
    }

    public List<Consumer<MobSpawnSettings.Builder>> getCommonSpawns_Consumers() {
        return this.commonSpawns_Consumers;
    }

    public List<Function4<MobSpawnSettings.Builder, Integer, Integer, Integer, Void>> getOceanSpawns_Consumers() {
        return this.oceanSpawns_Consumers;
    }

    public List<Function3<MobSpawnSettings.Builder, Integer, Integer, Void>> getWarmOceanSpawns_Consumers() {
        return this.warmOceanSpawns_Consumers;
    }

    public List<Consumer<MobSpawnSettings.Builder>> getPlainsSpawns_Consumers() {
        return this.plainsSpawns_Consumers;
    }

    public List<Consumer<MobSpawnSettings.Builder>> getSnowySpawns_Consumers() {
        return this.snowySpawns_Consumers;
    }

    public List<Consumer<MobSpawnSettings.Builder>> getDesertSpawns_Consumers() {
        return this.desertSpawns_Consumers;
    }

    public List<Consumer<MobSpawnSettings.Builder>> getDripstoneCavesSpawns_Consumers() {
        return this.dripstoneCavesSpawns_Consumers;
    }

    public List<Consumer<MobSpawnSettings.Builder>> getMooshroomSpawns_Consumers() {
        return this.mooshroomSpawns_Consumers;
    }

    public List<Consumer<MobSpawnSettings.Builder>> getBaseJungleSpawns_Consumers() {
        return this.baseJungleSpawns_Consumers;
    }

    public List<Consumer<MobSpawnSettings.Builder>> getEndSpawns_Consumers() {
        return this.endSpawns_Consumers;
    }

    public VanillaBiomePopulationManager addFarmAnimal_Consumer(Consumer<MobSpawnSettings.Builder> consumer) {
        this.farmAnimals_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addMonster_Consumer(Function5<MobSpawnSettings.Builder, Integer, Integer, Integer, Boolean, Void> consumer) {
        this.monsters_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addCaveSpawn_Consumer(Consumer<MobSpawnSettings.Builder> consumer) {
        this.caveSpawns_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addCommonSpawn_Consumer(Consumer<MobSpawnSettings.Builder> consumer) {
        this.commonSpawns_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addOceanSpawn_Consumer(Function4<MobSpawnSettings.Builder, Integer, Integer, Integer, Void> consumer) {
        this.oceanSpawns_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addWarmOceanSpawn_Consumer(Function3<MobSpawnSettings.Builder, Integer, Integer, Void> consumer) {
        this.warmOceanSpawns_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addPlainsSpawn_Consumer(Consumer<MobSpawnSettings.Builder> consumer) {
        this.plainsSpawns_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addSnowySpawn_Consumer(Consumer<MobSpawnSettings.Builder> consumer) {
        this.snowySpawns_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addDesertSpawn_Consumer(Consumer<MobSpawnSettings.Builder> consumer) {
        this.desertSpawns_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addDripstoneCaveSpawn_Consumer(Consumer<MobSpawnSettings.Builder> consumer) {
        this.dripstoneCavesSpawns_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addMooshroomSpawn_Consumer(Consumer<MobSpawnSettings.Builder> consumer) {
        this.mooshroomSpawns_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addBaseJungleSpawn_Consumer(Consumer<MobSpawnSettings.Builder> consumer) {
        this.baseJungleSpawns_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager addEndSpawn_Consumer(Consumer<MobSpawnSettings.Builder> consumer) {
        this.endSpawns_Consumers.add(consumer);
        return this;
    }

    public VanillaBiomePopulationManager setFarmAnimals_Consumers(List<Consumer<MobSpawnSettings.Builder>> farmAnimals_Consumers) {
        this.farmAnimals_Consumers = farmAnimals_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setMonsters_Consumers(List<Function5<MobSpawnSettings.Builder, Integer, Integer, Integer, Boolean, Void>> monsters_Consumers) {
        this.monsters_Consumers = monsters_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setCaveSpawns_Consumers(List<Consumer<MobSpawnSettings.Builder>> caveSpawns_Consumers) {
        this.caveSpawns_Consumers = caveSpawns_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setCommonSpawns_Consumers(List<Consumer<MobSpawnSettings.Builder>> commonSpawns_Consumers) {
        this.commonSpawns_Consumers = commonSpawns_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setOceanSpawns_Consumers(List<Function4<MobSpawnSettings.Builder, Integer, Integer, Integer, Void>> oceanSpawns_Consumers) {
        this.oceanSpawns_Consumers = oceanSpawns_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setWarmOceanSpawns_Consumers(List<Function3<MobSpawnSettings.Builder, Integer, Integer, Void>> warmOceanSpawns_Consumers) {
        this.warmOceanSpawns_Consumers = warmOceanSpawns_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setPlainsSpawns_Consumers(List<Consumer<MobSpawnSettings.Builder>> plainsSpawns_Consumers) {
        this.plainsSpawns_Consumers = plainsSpawns_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setSnowySpawns_Consumers(List<Consumer<MobSpawnSettings.Builder>> snowySpawns_Consumers) {
        this.snowySpawns_Consumers = snowySpawns_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setDesertSpawns_Consumers(List<Consumer<MobSpawnSettings.Builder>> desertSpawns_Consumers) {
        this.desertSpawns_Consumers = desertSpawns_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setDripstoneCavesSpawns_Consumers(List<Consumer<MobSpawnSettings.Builder>> dripstoneCavesSpawns_Consumers) {
        this.dripstoneCavesSpawns_Consumers = dripstoneCavesSpawns_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setMooshroomSpawns_Consumers(List<Consumer<MobSpawnSettings.Builder>> mooshroomSpawns_Consumers) {
        this.mooshroomSpawns_Consumers = mooshroomSpawns_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setBaseJungleSpawns_Consumers(List<Consumer<MobSpawnSettings.Builder>> baseJungleSpawns_Consumers) {
        this.baseJungleSpawns_Consumers = baseJungleSpawns_Consumers;
        return this;
    }

    public VanillaBiomePopulationManager setEndSpawns_Consumers(List<Consumer<MobSpawnSettings.Builder>> endSpawns_Consumers) {
        this.endSpawns_Consumers = endSpawns_Consumers;
        return this;
    }

    public boolean isCancelVanilla_farmAnimals_Method() {
        return this.cancelVanilla_farmAnimals_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_farmAnimals_Method(boolean value) {
        this.cancelVanilla_farmAnimals_Method = value;
        return this;
    }

    public boolean isCancelVanilla_monsters_Method() {
        return this.cancelVanilla_monsters_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_monsters_Method(boolean value) {
        this.cancelVanilla_monsters_Method = value;
        return this;
    }

    public boolean isCancelVanilla_caveSpawns_Method() {
        return this.cancelVanilla_caveSpawns_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_caveSpawns_Method(boolean value) {
        this.cancelVanilla_caveSpawns_Method = value;
        return this;
    }

    public boolean isCancelVanilla_commonSpawns_Method() {
        return this.cancelVanilla_commonSpawns_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_commonSpawns_Method(boolean value) {
        this.cancelVanilla_commonSpawns_Method = value;
        return this;
    }

    public boolean isCancelVanilla_oceanSpawns_Method() {
        return this.cancelVanilla_oceanSpawns_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_oceanSpawns_Method(boolean value) {
        this.cancelVanilla_oceanSpawns_Method = value;
        return this;
    }

    public boolean isCancelVanilla_warmOceanSpawns_Method() {
        return this.cancelVanilla_warmOceanSpawns_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_warmOceanSpawns_Method(boolean value) {
        this.cancelVanilla_warmOceanSpawns_Method = value;
        return this;
    }

    public boolean isCancelVanilla_plainsSpawns_Method() {
        return this.cancelVanilla_plainsSpawns_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_plainsSpawns_Method(boolean value) {
        this.cancelVanilla_plainsSpawns_Method = value;
        return this;
    }

    public boolean isCancelVanilla_snowySpawns_Method() {
        return this.cancelVanilla_snowySpawns_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_snowySpawns_Method(boolean value) {
        this.cancelVanilla_snowySpawns_Method = value;
        return this;
    }

    public boolean isCancelVanilla_desertSpawns_Method() {
        return this.cancelVanilla_desertSpawns_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_desertSpawns_Method(boolean value) {
        this.cancelVanilla_desertSpawns_Method = value;
        return this;
    }

    public boolean isCancelVanilla_dripstoneCavesSpawns_Method() {
        return this.cancelVanilla_dripstoneCavesSpawns_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_dripstoneCavesSpawns_Method(boolean value) {
        this.cancelVanilla_dripstoneCavesSpawns_Method = value;
        return this;
    }

    public boolean isCancelVanilla_mooshroomSpawns_Method() {
        return this.cancelVanilla_mooshroomSpawns_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_mooshroomSpawns_Method(boolean value) {
        this.cancelVanilla_mooshroomSpawns_Method = value;
        return this;
    }

    public boolean isCancelVanilla_baseJungleSpawns_Method() {
        return this.cancelVanilla_baseJungleSpawns_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_baseJungleSpawns_Method(boolean value) {
        this.cancelVanilla_baseJungleSpawns_Method = value;
        return this;
    }

    public boolean isCancelVanilla_endSpawns_Method() {
        return this.cancelVanilla_endSpawns_Method;
    }

    public VanillaBiomePopulationManager setCancelVanilla_endSpawns_Method(boolean value) {
        this.cancelVanilla_endSpawns_Method = value;
        return this;
    }
}
