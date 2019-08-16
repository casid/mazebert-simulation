package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.TestTower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class Tower_LuckTest extends SimTest {

    RandomPluginTrainer randomPluginTrainer;

    Tower tower;

    @BeforeEach
    void setUp() {
        randomPlugin = randomPluginTrainer = new RandomPluginTrainer();

        tower = new TestTower();
    }

    @Test
    void multiLuck_chanceReducedEveryRoll_miss() {
        tower.addMultiluck(1);
        randomPluginTrainer.givenFloatAbs(0.2f, 0.09999f); // Second roll is not good enough

        assertThat(tower.isAbilityTriggered(0.1f)).isFalse();
    }

    @Test
    void multiLuck() {
        tower.addMultiluck(1);
        randomPluginTrainer.givenFloatAbs(0.2f, 0.079f); // Second roll is good enough

        assertThat(tower.isAbilityTriggered(0.1f)).isTrue();
    }
}