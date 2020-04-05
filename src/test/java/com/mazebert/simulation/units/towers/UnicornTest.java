package com.mazebert.simulation.units.towers;


import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.stash.StashEntry;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepBuilder;
import com.mazebert.simulation.units.items.HelmOfHades;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class UnicornTest extends SimTest {

    Wizard wizard;
    Unicorn unicorn;
    RandomPluginTrainer randomPluginTrainer;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPluginTrainer = new RandomPluginTrainer();
        randomPlugin = randomPluginTrainer;
        lootSystem = new LootSystemTrainer();
        experienceSystem = new ExperienceSystem();
        damageSystem = new DamageSystem();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);
        assertThat(wizard.interestBonus).isEqualTo(0);

        unicorn = new Unicorn();
        unicorn.setExperience(0);
        unicorn.setWizard(wizard);
        unitGateway.addUnit(unicorn);
    }

    @Test
    void gainsOneLevelEveryRound() {
        assertThat(unicorn.getLevel()).isEqualTo(1);

        simulationListeners.onRoundStarted.dispatch(null);
        assertThat(unicorn.getLevel()).isEqualTo(2);

        simulationListeners.onRoundStarted.dispatch(null);
        assertThat(unicorn.getLevel()).isEqualTo(3);

        simulationListeners.onRoundStarted.dispatch(null);
        assertThat(unicorn.getLevel()).isEqualTo(4);
    }

    @Test
    void gainsOneLevelEveryRound_maxLevel() {
        for (int i = 0; i < Balancing.MAX_TOWER_LEVEL - 2; ++i) {
            simulationListeners.onRoundStarted.dispatch(null);
        }
        assertThat(unicorn.getLevel()).isEqualTo(Balancing.MAX_TOWER_LEVEL - 1);

        simulationListeners.onRoundStarted.dispatch(null);
        assertThat(unicorn.getLevel()).isEqualTo(Balancing.MAX_TOWER_LEVEL);

        simulationListeners.onRoundStarted.dispatch(null);
        assertThat(unicorn.getLevel()).isEqualTo(Balancing.MAX_TOWER_LEVEL);
    }

    @Test
    void creepEntersRange_death() {
        randomPluginTrainer.givenFloatAbs(0.9f);
        unitGateway.addUnit(a(creep()));

        assertThat(unitGateway.hasUnit(unicorn)).isFalse();
        assertThat(wizard.gold).isEqualTo(0);
    }

    @Test
    void creepEntersRange_death_notForOtherWizard() {
        version = Sim.v19;
        randomPluginTrainer.givenFloatAbs(0.9f);
        unitGateway.addUnit(a(creep().withWizard(new Wizard())));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void creepEntersRange_death_tears() {
        unicorn.setExperience(Balancing.getTowerExperienceForLevel(10) + 1.0f);
        Tower tower = new TestTower();
        randomPluginTrainer.givenFloatAbs(0.9f);
        unitGateway.addUnit(a(creep()));

        StashEntry<Potion> tears = wizard.potionStash.get(PotionType.UnicornTears);
        assertThat(tears).isNotNull();

        tears.getCard().forEachAbility(tower::addAbility); // other tower drinks unicorn tears
        assertThat(tower.getLevel()).isEqualTo(5);
    }

    @Test
    void creepEntersRange_impale() {
        Creep creep = a(creep().boss());
        unitGateway.addUnit(creep);

        assertThat(creep.getHealth()).isEqualTo(80);
    }

    @Test
    void creepEntersRange_impale_kill() {
        Creep creep = a(creep().boss());
        creep.setHealth(19);
        unitGateway.addUnit(creep);

        assertThat(creep.isDead()).isTrue();
        assertThat(unitGateway.hasUnit(unicorn)).isTrue(); // Dead creeps cannot kill unicorn
    }

    @Test
    void creepEntersRange_impale_notForAir() {
        Creep creep = a(creep().air());
        unitGateway.addUnit(creep);

        assertThat(creep.getHealth()).isEqualTo(100);
    }

    @Test
    void creepEntersRange_impale_notForTimeLord() {
        Creep creep = a(creep().timeLord());
        unitGateway.addUnit(creep);

        assertThat(creep.getHealth()).isEqualTo(100);
    }

    @Test
    void creepEntersRange_lucky() {
        randomPluginTrainer.givenFloatAbs(0.5f);
        unitGateway.addUnit(a(creep()));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void creepEntersRange_death_notForChallenge() {
        randomPluginTrainer.givenFloatAbs(0.9f);
        unitGateway.addUnit(a(creep().challenge()));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void creepEntersRange_death_notForMassChallenge() {
        randomPluginTrainer.givenFloatAbs(0.9f);
        unitGateway.addUnit(a(creep().massChallenge()));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void creepEntersRange_death_forHorseman() {
        randomPluginTrainer.givenFloatAbs(0.9f);
        unitGateway.addUnit(a(creep().horseman()));

        assertThat(unitGateway.hasUnit(unicorn)).isFalse();
    }

    @Test
    void creepEntersRange_death_notForAir() {
        randomPluginTrainer.givenFloatAbs(0.9f);
        unitGateway.addUnit(a(creep().air()));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void creepEntersRange_death_notWhenInvisible() {
        version = Sim.vDoL;
        unicorn.setItem(0, new HelmOfHades());

        randomPluginTrainer.givenFloatAbs(0.9f);
        unitGateway.addUnit(a(creep()));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void creepEntersRange_death_notWhenInvisible_noLongerPossibleBecauseOfMultiplayer() {
        version = Sim.vDoLEndBeta1;
        unicorn.setItem(0, new HelmOfHades());

        randomPluginTrainer.givenFloatAbs(0.9f);
        unitGateway.addUnit(a(creep()));

        assertThat(unitGateway.hasUnit(unicorn)).isFalse();
    }

    @Test
    void interest() {
        assertThat(wizard.interestBonus).isEqualTo(0.02f);
    }

    @Test
    void interest_death() {
        randomPluginTrainer.givenFloatAbs(0.9f);
        unitGateway.addUnit(a(creep()));

        assertThat(wizard.interestBonus).isEqualTo(0.0f);
    }

    CreepBuilder creep() {
        return CreepBuilder.creep().withWizard(wizard);
    }
}