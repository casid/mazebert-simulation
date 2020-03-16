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

    @Test
    void negativeAbility() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        assertThat(tower.isNegativeAbilityTriggered(0.9f)).isFalse();
    }

    @Test
    void negativeAbility2() {
        randomPluginTrainer.givenFloatAbs(0.09f);
        assertThat(tower.isNegativeAbilityTriggered(0.9f)).isFalse();
    }

    @Test
    void negativeAbility3() {
        randomPluginTrainer.givenFloatAbs(0.11f);
        assertThat(tower.isNegativeAbilityTriggered(0.9f)).isTrue();
    }

    @Test
    void negativeAbility4() {
        randomPluginTrainer.givenFloatAbs(0.7f);
        assertThat(tower.isNegativeAbilityTriggered(0.9f)).isTrue();
    }

    @Test
    void negativeAbility_badLuck() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        tower.addLuck(-0.5f);
        assertThat(tower.isNegativeAbilityTriggered(0.5f)).isFalse();
    }

    @Test
    void negativeAbility_badLuck2() {
        randomPluginTrainer.givenFloatAbs(0.24f);
        tower.addLuck(-0.5f);
        assertThat(tower.isNegativeAbilityTriggered(0.5f)).isFalse();
    }

    @Test
    void negativeAbility_badLuck3() {
        randomPluginTrainer.givenFloatAbs(0.25f);
        tower.addLuck(-0.5f);
        assertThat(tower.isNegativeAbilityTriggered(0.5f)).isTrue();
    }

    @Test
    void negativeAbility_badLuck4() {
        randomPluginTrainer.givenFloatAbs(0.7f);
        tower.addLuck(-0.5f);
        assertThat(tower.isNegativeAbilityTriggered(0.5f)).isTrue();
    }

    @Test
    void negativeAbility_badLuck_multiLuck() {
        randomPluginTrainer.givenFloatAbs(0.7f, 0.0f);
        tower.addLuck(-0.5f);
        tower.addMultiluck(1);
        assertThat(tower.isNegativeAbilityTriggered(0.5f)).isFalse();
    }

    @Test
    void negativeAbility_superBadLuck() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        tower.addLuck(-10.0f);
        assertThat(tower.isNegativeAbilityTriggered(0.5f)).isTrue();
    }

    @Test
    void negativeAbility_superBadLuck2() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        tower.addLuck(-1.0f);
        assertThat(tower.isNegativeAbilityTriggered(0.5f)).isTrue();
    }

    @Test
    void negativeAbility_mead() {
        randomPluginTrainer.givenFloatAbs(0.9f);
        assertThat(tower.isNegativeAbilityTriggered(0.01f)).isFalse();
    }

    @Test
    void negativeAbility_mead2() {
        randomPluginTrainer.givenFloatAbs(0.99f);
        assertThat(tower.isNegativeAbilityTriggered(0.01f)).isTrue();
    }

    @Test
    void negativeAbility_mead3() {
        tower.addLuck(1.0f);
        randomPluginTrainer.givenFloatAbs(0.99f);
        assertThat(tower.isNegativeAbilityTriggered(0.01f)).isFalse();
    }

    @Test
    void negativeAbility_mead4() {
        tower.addLuck(0.1f);
        randomPluginTrainer.givenFloatAbs(0.5f);
        assertThat(tower.isNegativeAbilityTriggered(1.01f)).isTrue();
    }

    @Test
    void negativeAbility_mead5() {
        tower.addLuck(1.0f);
        randomPluginTrainer.givenFloatAbs(0.0f);
        assertThat(tower.isNegativeAbilityTriggered(1.01f)).isFalse();
    }

    @Test
    void negativeAbility_mead6() {
        tower.addLuck(1.0f);
        randomPluginTrainer.givenFloatAbs(0.0f);
        assertThat(tower.isNegativeAbilityTriggered(2.0f)).isTrue();
    }
}