package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class BearHunter_BestHitTest extends SimTest {
    BearHunter bearHunter;
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();

        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        gameGateway.getGame().map = new BloodMoor();

        randomPlugin = randomPluginTrainer;
        damageSystem = new DamageSystem(randomPlugin, simulationListeners, formatPlugin);

        bearHunter = new BearHunter();
        bearHunter.setBaseDamage(10);
        bearHunter.setX(17);
        bearHunter.setY(13);
        unitGateway.addUnit(bearHunter);
    }

    @Test
    void damage() {
        randomPluginTrainer.givenFloatAbs(0, 0, 0, 0, 0);

        whenTrapIsPlaced();

        Creep creep = new Creep();
        creep.setX(17);
        creep.setY(14);
        unitGateway.addUnit(creep);

        assertThat(bearHunter.getBestHit()).isGreaterThan(0);
    }

    private void whenTrapIsPlaced() {
        bearHunter.simulate(5.0f);
    }
}