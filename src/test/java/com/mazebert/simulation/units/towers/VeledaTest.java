package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Wave;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VeledaTest extends ItemTest {

    @Override
    protected Tower createTower() {
        return new Veleda();
    }

    @BeforeEach
    void setUp() {
        tower.setLevel(1);
    }

    @Test
    void prophecyCreated() {
        randomPluginTrainer.givenFloatAbs(0);

        whenRoundIsStarted();

        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.WealthyBossProphecy);
    }

    @Test
    void prophecyNotCreated() {
        randomPluginTrainer.givenFloatAbs(0.9f);

        whenRoundIsStarted();

        assertThat(wizard.itemStash.size()).isEqualTo(0);
    }

    private void whenRoundIsStarted() {
        simulationListeners.onRoundStarted.dispatch(new Wave());
    }
}