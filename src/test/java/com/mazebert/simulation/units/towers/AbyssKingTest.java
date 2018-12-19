package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class AbyssKingTest extends SimTest {

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    AbyssKing abyssKing;
    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;

        abyssKing = new AbyssKing();
        unitGateway.addUnit(abyssKing);

        creep = a(creep());
        unitGateway.addUnit(creep);
    }

    @Test
    void creepJoinsArmy() {
        creep.setHealth(0);
        assertThat(abyssKing.getArmySize()).isEqualTo(1);
    }

    @Test
    void creepOutOfRangeDoesNotJoinArmy() {
        creep.setX(2);
        abyssKing.simulate(0.1f);
        creep.setHealth(0);

        assertThat(abyssKing.getArmySize()).isEqualTo(0);
    }

    @Test
    void badLuck_creepDoesNotJoin() {
        randomPluginTrainer.givenFloatAbs(0.2f);
        creep.setHealth(0);
        assertThat(abyssKing.getArmySize()).isEqualTo(0);
    }

    @Test
    void creepJoinsArmy_bonusForDarknessTowers() {
        ScareCrow scareCrow = new ScareCrow();
        scareCrow.setX(10);
        scareCrow.setY(10);
        unitGateway.addUnit(scareCrow);

        creep.setHealth(0);
        assertThat(abyssKing.getArmySize()).isEqualTo(1);

        assertThat(scareCrow.getAddedRelativeBaseDamage()).isEqualTo(AbyssKingAura.damagePerUndead);
        assertThat(scareCrow.getCritDamage()).isEqualTo(0.25f + AbyssKingAura.critDamagePerUndead);
        assertThat(scareCrow.getCritChance()).isEqualTo(0.05f + AbyssKingAura.critChancePerUndead);
    }

    @Test
    void abyssKingRemoved() {
        ScareCrow scareCrow = new ScareCrow();
        unitGateway.addUnit(scareCrow);
        creep.setHealth(0);
        unitGateway.removeUnit(abyssKing);

        assertThat(scareCrow.getAddedRelativeBaseDamage()).isEqualTo(0);
        assertThat(scareCrow.getCritDamage()).isEqualTo(0.24999999f);
        assertThat(scareCrow.getCritChance()).isEqualTo(0.05f);
    }
}