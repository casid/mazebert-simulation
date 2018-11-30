package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.traps.BearHunterTrap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class BearHunterTest extends SimTest {
    BearHunter bearHunter;

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();

        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        gameGateway.getGame().map = new BloodMoor();
        damageSystem = new DamageSystemTrainer();

        randomPlugin = randomPluginTrainer;

        bearHunter = new BearHunter();
        bearHunter.setX(17);
        bearHunter.setY(13);
        unitGateway.addUnit(bearHunter);
    }

    @Test
    void trapIsPlaced_possibleSpotOneOfTwo() {
        randomPluginTrainer.givenFloatAbs(0);

        whenTrapIsPlaced();

        BearHunterTrap trap = unitGateway.findUnitInRange(17, 14, 0, BearHunterTrap.class);
        assertThat(trap).isNotNull();
    }

    @Test
    void trapIsPlaced_possibleSpotTwoOfTwo() {
        randomPluginTrainer.givenFloatAbs(0.9f);

        whenTrapIsPlaced();

        BearHunterTrap trap = unitGateway.findUnitInRange(18, 14, 0, BearHunterTrap.class);
        assertThat(trap).isNotNull();
    }

    @Test
    void rangeIsIncreased() {
        randomPluginTrainer.givenFloatAbs(0);
        bearHunter.addRange(1);

        whenTrapIsPlaced();

        BearHunterTrap trap = unitGateway.findUnitInRange(15, 11, 0, BearHunterTrap.class);
        assertThat(trap).isNotNull();
    }

    @Test
    void trapsStack() {
        randomPluginTrainer.givenFloatAbs(0, 0);

        whenTrapIsPlaced();
        whenTrapIsPlaced();

        BearHunterTrap trap = unitGateway.findUnitInRange(17, 14, 0, BearHunterTrap.class);
        assertThat(trap.getStackCount()).isEqualTo(2);
    }

    @Test
    void damage() {
        randomPluginTrainer.givenFloatAbs(
                0,
                0
        );

        whenTrapIsPlaced();
        whenTrapIsPlaced();

        Creep creep = new Creep();
        creep.setX(17);
        creep.setY(14);
        unitGateway.addUnit(creep);

        BearHunterTrap trap = unitGateway.findUnitInRange(17, 14, 0, BearHunterTrap.class);
        trap.simulate(0.1f);

        assertThat(creep.getHealth()).isEqualTo(80);

        // No trap left for next creep
        Creep nextCreep = new Creep();
        nextCreep.setX(17);
        nextCreep.setY(14);
        unitGateway.addUnit(nextCreep);

        trap.simulate(0.1f);
        assertThat(nextCreep.getHealth()).isEqualTo(100);
    }

    @Test
    void trapsAreRemovedWithTower() {
        whenTrapIsPlaced();
        whenTrapIsPlaced();

        unitGateway.removeUnit(bearHunter);
        assertThat(unitGateway.hasUnits(BearHunterTrap.class)).isFalse();
    }

    private void whenTrapIsPlaced() {
        bearHunter.simulate(5.0f);
    }
}