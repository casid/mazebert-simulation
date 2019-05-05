package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.TestTower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class Tower_AverageDamageTest {
    Tower tower = new TestTower();

    @BeforeEach
    void setUp() {
        tower.setStrength(1.0f);
        tower.setDamageSpread(0.5f);
        tower.setBaseDamage(100);
    }

    @Test
    void noCrit() {
        tower.setCritChance(0);
        assertThat(tower.calculateAverageDamageForDisplay()).isEqualTo(100.0);
    }

    @Test
    void crit() {
        tower.setCritChance(0.1f);
        tower.setCritDamage(1.0f);
        assertThat(tower.calculateAverageDamageForDisplay()).isEqualTo(110.00000014901161);
    }
}