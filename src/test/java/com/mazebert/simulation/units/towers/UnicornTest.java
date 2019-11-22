package com.mazebert.simulation.units.towers;


import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.stash.StashEntry;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.HelmOfHades;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
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
        randomPluginTrainer.givenFloatAbs(0.0f);
        unitGateway.addUnit(a(creep()));

        assertThat(unitGateway.hasUnit(unicorn)).isFalse();
        assertThat(wizard.gold).isEqualTo(0);
    }

    @Test
    void creepEntersRange_death_tears() {
        unicorn.setExperience(Balancing.getTowerExperienceForLevel(10) + 1.0f);
        Tower tower = new TestTower();
        randomPluginTrainer.givenFloatAbs(0.0f);
        unitGateway.addUnit(a(creep()));

        StashEntry<Potion> tears = wizard.potionStash.get(PotionType.UnicornTears);
        assertThat(tears).isNotNull();

        tears.getCard().forEachAbility(tower::addAbility); // other tower drinks unicorn tears
        assertThat(tower.getLevel()).isEqualTo(5);
    }

    @Test
    void creepEntersRange_lucky() {
        randomPluginTrainer.givenFloatAbs(0.5f);
        unitGateway.addUnit(a(creep()));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void creepEntersRange_death_notForChallenge() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        unitGateway.addUnit(a(creep().challenge()));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void creepEntersRange_death_notForMassChallenge() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        unitGateway.addUnit(a(creep().massChallenge()));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void creepEntersRange_death_notForHorseman() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        unitGateway.addUnit(a(creep().horseman()));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void creepEntersRange_death_notForAir() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        unitGateway.addUnit(a(creep().air()));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void creepEntersRange_death_notWhenInvisible() {
        unicorn.setItem(0, new HelmOfHades());

        randomPluginTrainer.givenFloatAbs(0.0f);
        unitGateway.addUnit(a(creep()));

        assertThat(unitGateway.hasUnit(unicorn)).isTrue();
    }

    @Test
    void interest() {
        assertThat(wizard.interestBonus).isEqualTo(0.02f);
    }

    @Test
    void interest_death() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        unitGateway.addUnit(a(creep()));

        assertThat(wizard.interestBonus).isEqualTo(0.0f);
    }
}