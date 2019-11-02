package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.abilities.AttackAbility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RabbitMultishotTest extends SimTest {

    Tower tower;
    AttackAbility attackAbility;

    @BeforeEach
    void setUp() {
        tower = new TestTower();
        attackAbility = new AttackAbility();
        tower.addAbility(attackAbility);
        tower.addAbility(new RabbitMultishot());
    }

    @Test
    void initial() {
        assertThat(attackAbility.getTargets()).isEqualTo(2);
    }

    @Test
    void level1() {
        tower.setLevel(1);
        assertThat(attackAbility.getTargets()).isEqualTo(2);
    }

    @Test
    void level16() {
        tower.setLevel(16);
        assertThat(attackAbility.getTargets()).isEqualTo(3);
    }
}