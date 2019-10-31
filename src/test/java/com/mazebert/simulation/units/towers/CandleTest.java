package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.ReviveEffect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class CandleTest extends SimTest {

    TestMap map;
    Candle candle;
    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        experienceSystem = new ExperienceSystem();
        lootSystem = new LootSystemTrainer();
        randomPlugin = new RandomPluginTrainer();

        map = new TestMap(6);
        map.givenEndWaypoint(5, 0);
        gameGateway.getGame().map = map;

        candle = new Candle();
        candle.setX(3);
        candle.setY(2);
        unitGateway.addUnit(candle);

        creep = a(creep().air());
        creep.setX(0);
        creep.setY(0);
        creep.setPath(new Path(0, 0, 5, 0));
        unitGateway.addUnit(creep);
    }

    @Test
    void creepPathChanges() {
        creep.simulate(1.0f); // enters candle
        candle.simulate(0.1f); // Candle needs a tick to recognize this
        assertThat(creep.getX()).isEqualTo(1);
        assertThat(creep.getY()).isEqualTo(0);

        creep.simulate(4); // creep flies to the candle
        assertThat(creep.getX()).isEqualTo(candle.getX());
        assertThat(creep.getY()).isEqualTo(candle.getY());

        creep.simulate(4); // creep flies to the end waypoint
        assertThat(creep.getX()).isEqualTo(5);
        assertThat(creep.getY()).isEqualTo(0);
    }

    @Test
    void creepPathChanges_onlyForAir() {
        creep.getWave().type = WaveType.Normal;
        creep.simulate(1.0f); // enters candle
        candle.simulate(0.1f); // Candle needs a tick to recognize this

        creep.simulate(4); // creep is not air and will not fly to the candle
        assertThat(creep.getX()).isEqualTo(5);
        assertThat(creep.getY()).isEqualTo(0);
    }

    @Test
    void onlyOneCandleCanChangeThePath() {
        Candle anotherCandle = new Candle();
        anotherCandle.setX(candle.getX() + 1);
        anotherCandle.setY(candle.getY());
        unitGateway.addUnit(anotherCandle);

        creep.simulate(1.0f); // enters first candle
        candle.simulate(0.1f); // Candle needs a tick to recognize this

        creep.simulate(1.0f); // enters second candle
        anotherCandle.simulate(0.1f); // Candle needs a tick to recognize this

        creep.simulate(3); // creep flies to the first candle
        assertThat(creep.getX()).isEqualTo(candle.getX());
        assertThat(creep.getY()).isEqualTo(candle.getY());
    }

    @Test
    void creepPathChanges_revive() {
        creep.addAbility(new ReviveEffect());
        creep.simulate(1.0f); // enters candle
        candle.simulate(0.1f); // Candle needs a tick to recognize this
        assertThat(creep.getX()).isEqualTo(1);
        assertThat(creep.getY()).isEqualTo(0);

        creep.simulate(4); // creep flies to the candle
        assertThat(creep.getX()).isEqualTo(candle.getX());
        assertThat(creep.getY()).isEqualTo(candle.getY());

        creep.simulate(2); // creep flies towards the end waypoint
        candle.kill(creep);
        creep.simulate(Creep.DEATH_TIME);

        creep.simulate(2); // creep flies towards the end waypoint?
        assertThat(creep.getX()).isEqualTo(5);
        assertThat(creep.getY()).isEqualTo(0);
    }

    @Test
    void damageAgainstAir() {
        assertThat(candle.getDamageAgainstAir()).isEqualTo(1.5f);
    }
}