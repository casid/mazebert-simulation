package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.potions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class TinkerTest extends SimTest {
    Tinker tinker;
    Tower otherTower;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        tinker = new Tinker();
        unitGateway.addUnit(tinker);

        otherTower = new TestTower();
        otherTower.setX(1);
        unitGateway.addUnit(otherTower);
    }

    @Test
    void luckAura() {
        assertThat(tinker.getLuck()).isEqualTo(1.1f);
        assertThat(otherTower.getLuck()).isEqualTo(1.1f);
    }

    @Test
    void luckAura_potionConsumed_common() {
        tinker.onPotionConsumed.dispatch(tinker, new CommonSpeed());

        assertThat(tinker.getLuck()).isEqualTo(1.102f);
        assertThat(otherTower.getLuck()).isEqualTo(1.102f);
    }

    @Test
    void luckAura_potionConsumed_uncommon() {
        tinker.onPotionConsumed.dispatch(tinker, new UncommonSpeed());

        assertThat(tinker.getLuck()).isEqualTo(1.104f);
        assertThat(otherTower.getLuck()).isEqualTo(1.104f);
    }

    @Test
    void luckAura_potionConsumed_rare() {
        tinker.onPotionConsumed.dispatch(tinker, new RareSpeed());

        assertThat(tinker.getLuck()).isEqualTo(1.108f);
        assertThat(otherTower.getLuck()).isEqualTo(1.108f);
    }

    @Test
    void luckAura_potionConsumed_unique() {
        tinker.onPotionConsumed.dispatch(tinker, new Tears());

        assertThat(tinker.getLuck()).isEqualTo(1.16f);
        assertThat(otherTower.getLuck()).isEqualTo(1.16f);
    }

    @Test
    void luckAura_potionConsumed_tutorial() {
        tinker.onPotionConsumed.dispatch(tinker, new DrinkAll());

        assertThat(tinker.getLuck()).isEqualTo(1.1f);
        assertThat(otherTower.getLuck()).isEqualTo(1.1f);
    }

    @Test
    void luckAura_potionConsumed_legendary() {
        tinker.onPotionConsumed.dispatch(tinker, new ChangeSex());

        assertThat(tinker.getLuck()).isEqualTo(1.1800001f);
        assertThat(otherTower.getLuck()).isEqualTo(1.1800001f);
    }

    @Test
    void luckAura_potionConsumed_supporterReward() {
        tinker.onPotionConsumed.dispatch(tinker, new WhiteRussian());

        assertThat(tinker.getLuck()).isEqualTo(1.1f);
        assertThat(otherTower.getLuck()).isEqualTo(1.1f);
    }
}